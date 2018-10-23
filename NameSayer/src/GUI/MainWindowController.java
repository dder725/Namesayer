package GUI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.scene.control.Tooltip;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import application.DataBase;
import application.Name;
import application.NamesListReader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;

public class MainWindowController {
	private ObservableList<Name> _namesList = FXCollections.observableArrayList();
	private ObservableList<ArrayList<Name>> _playlist = FXCollections.observableArrayList();
	private ObservableList<Name> _currentName = FXCollections.observableArrayList();
	FilteredList<Name> _filteredNamesList = new FilteredList<>(_namesList, data -> true);

	@FXML
	private JFXListView<Name> namesListView;

	@FXML
	private JFXListView<ArrayList<Name>> playlistView;

	@FXML
	private JFXButton add;
	@FXML
	private JFXButton clear;
	@FXML
	private JFXButton practice;
	@FXML
	private JFXButton uploadPlaylist;
	@FXML
	private JFXButton uploadDatabase;
	@FXML
	private JFXButton shuffle;

	@FXML
	private JFXTextField searchBox;

	public void populateTableView() {
		setHints();
		_namesList.clear();
		namesListView.setItems(_filteredNamesList);
		playlistView.setItems(_playlist);
		_namesList.addAll(DataBase.getNamesList());

		// Filter the list
		_filteredNamesList.predicateProperty().bind(javafx.beans.binding.Bindings.createObjectBinding(() -> {
			String text = searchBox.getText();

			// Check if it is a composite word by checking for non-word characters
			if (text.matches(".*[^A-Za-z].*")) {
				String[] names = text.split("[\\W]");
				text = names[names.length - 1];
				System.out.println("Non-word char is found");
			}

			// Do nothing for empty searchbox
			if (text == null || text.isEmpty()) {
				return null;
			} else if (!Character.isLetter(searchBox.getText().charAt(searchBox.getText().length() - 1))) {
				// Refresh the database to the original view if a non-word character is entered
				return (Name) -> !(Name.getName().toUpperCase().isEmpty());
			} else { // Filter the database to the entered word
				final String uppercase = text.toUpperCase();
				return (Name) -> Name.getName().toUpperCase().contains(uppercase);
			}
		}, searchBox.textProperty()));
	}

	// Set help tooltips for the buttons
	private void setHints() {
		add.setTooltip(new Tooltip("Add the entered name to the playlist"));
		clear.setTooltip(new Tooltip("Clear the current playlist"));
		practice.setTooltip(new Tooltip("Practice the current playlist"));
		uploadPlaylist.setTooltip(new Tooltip("Upload a .txt playlist"));
		uploadDatabase.setTooltip(new Tooltip("Upload your own .zip database of names"));
		namesListView.setTooltip(new Tooltip("Double click to add to the searchbox"));
		playlistView.setTooltip(new Tooltip("Double click to remove from the playlist"));
		shuffle.setTooltip(new Tooltip("Randomise the playlist"));
	}

	// Add a name from the database to the searchbox on doubleclick
	public void setupDoubleClickAdd() {
		namesListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) {
					addToName();
				}
			}
		});
	}

	public void removeFromPractice() {
		if (!_playlist.isEmpty()) {
			_playlist.remove(playlistView.getSelectionModel().getSelectedIndex());
		}
		;
	}

	// Remove a name from the playlist on double click
	public void setupDoubleClickRemove() {
		playlistView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) {
					removeFromPractice();
				}
			}
		});
	}

	public void practiceButton() {
		if (_playlist.isEmpty()) {
			noNames();
		} else {
			practiceWindow();
		}
	}

	// Pop-up error window, in case of an empty playlist
	public void noNames() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("noNamesWindow.fxml"));
			Parent content = (Parent) loader.load();
			NoNamesController Practice = (NoNamesController) loader.getController();

			Stage stage = new Stage();
			stage.setScene(new Scene(content));
			stage.show();
		} catch (IOException e) {
		}
	}

	public void practiceWindow() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("PracticeWindow.fxml"));
			Parent content = (Parent) loader.load();
			PracticeWindowController controller = loader.getController();
			// CHANGE THIS
			// Pass the playlist to the practice window
			controller.setPlaylist(_playlist);
			controller.setHints();
			controller.setVolumeControl();
			controller.createRecording("fullName.wav");

			Stage stage = new Stage();
			stage.setScene(new Scene(content));
			controller.sceneResize(stage);
			stage.show();
			controller.setHints();
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					File file1 = new File("fullName.wav");
					File file2 = new File("attempt.wav");
					File file3 = new File("compare.wav");
					file1.delete();
					file2.delete();
					file3.delete();
				}
			});
		} catch (IOException e) {
		}
	}

	public void randomiseList() {
		FXCollections.shuffle(_playlist);
	}

	public void addToName() {
		// Update the searchbox with the selected database name
		if (!searchBox.getText().isEmpty() && searchBox.getText().contains(" ")) {
			searchBox.setText(searchBox.getText().substring(0, searchBox.getText().lastIndexOf(" ")) + " "
					+ namesListView.getSelectionModel().getSelectedItem().getName());
		} else {
			searchBox.setText(namesListView.getSelectionModel().getSelectedItem().getName());
		}
	}

	public void addToPlaylist() {
		// Remove any extra spaces and split a composite name
		String[] text = searchBox.getText().trim().replaceAll(" +", " ").split("[\\W]");

		// Create an array of the names not in the database
		List<String> namesNotInDatabase = new ArrayList<String>();
		// Find the names objects of the given names
		ArrayList<Name> namesFromText = new ArrayList<Name>() {
			@Override
			// Display the name in a formatted way in the table by overriding toString()
			// method
			public String toString() {
				String nameString = new String();
				for (Name name : this) {
					nameString += name.getName() + " ";
				}
				;
				return nameString;
			}
		};
		for (String word : text) {
			// Check if the name exists in the database
			Name name = DataBase.findName(word.toUpperCase());
			if (name != null) {
				namesFromText.add(name);
			} else {
				namesNotInDatabase.add(word);
				name = new Name(word);
				namesFromText.add(name);
			}
			;
		}

		// Check if the name is already in the playlist
		if (_playlist.contains(namesFromText)) {
			ErrorDialog.showError("This name is already in the playlist");
		} else if (!namesFromText.isEmpty() && namesNotInDatabase.isEmpty() && searchBox.getText().length() <= 50) {
			// Add a name to a playlist if a searchbox is not empty and there are no unknown
			// names
			_playlist.add(namesFromText);
		}

		// Display a warning window if a name is not found
		if (!namesNotInDatabase.isEmpty() || searchBox.getText().length() > 50) {

			// Select the error message depending on the type of error
			String extraMessage, explanation;
			if (searchBox.getText().isEmpty()) {
				extraMessage = "The searchbox is empty";
				explanation = "There is no name to add!";
			} else if (namesNotInDatabase.size() == 1) {
				extraMessage = " does not exist in the database!";
				explanation = "You cannot add unknown names to the playlist";
			} else if (namesNotInDatabase.size() > 1) {
				extraMessage = " do not exist in the database!";
				explanation = "You cannot add unknown names to the playlist";
			} else {
				extraMessage = "The names cannot be longer than 50 characters";
				explanation = "Please shorten the name to add it to the playlist";
			}

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText(
					namesNotInDatabase.stream().map(Object::toString).collect(Collectors.joining(", ")) + extraMessage);
			alert.setContentText(explanation);

			alert.showAndWait();
		} else {
			// Refresh searchbox
			searchBox.setText("");
		}
	}

	// Remove all entries in the playlist
	public void clearPlaylist() {
		_playlist.clear();
	}

	public void uploadPlaylist() {
		// Restrict file search to .txt files only
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");

		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();

		// Set NameSayer directory as initial directory
		File file = new File(System.getProperty("user.dir"));
		fileChooser.setInitialDirectory(file);

		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setTitle("Open Resource File");
		File nameList = fileChooser.showOpenDialog(stage);
		NamesListReader reader = new NamesListReader(nameList);

		ArrayList<ArrayList<Name>> names = reader.getListedNames(_namesList);
		System.out.println(names);

		// Add a name if it is not empty
		for (ArrayList<Name> name : names) {
			if (!name.isEmpty() && !_playlist.contains(name)) {
				_playlist.add(name);
			}
		}
	}

	public void uploadDatabase() {
		// Restrict file search to .zip files only
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("ZIP files (*.zip)", "*.zip");

		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();

		// Set NameSayer directory as initial directory
		File file = new File(System.getProperty("user.dir"));
		fileChooser.setInitialDirectory(file);

		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setTitle("Open Resource File");
		File databaseFile = fileChooser.showOpenDialog(stage);

		// Reinstantiate database
		DataBase.reinstantiateDataBase(databaseFile);
		clearPlaylist();
		populateTableView();

	}

}
