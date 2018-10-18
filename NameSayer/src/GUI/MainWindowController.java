package GUI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
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
	private Text currentNameText;

	@FXML
	private JFXListView<Name> namesListView;

	@FXML
	private JFXListView<ArrayList<Name>> playlistView;

	@FXML
	private JFXButton add, clear;

	@FXML
	private JFXTextField searchBox;

	public void populateTableView() {
		namesListView.setItems(_filteredNamesList);
		playlistView.setItems(_playlist);
		_namesList.addAll(DataBase.getNamesList());

		// Filter the list
		_filteredNamesList.predicateProperty().bind(javafx.beans.binding.Bindings.createObjectBinding(() -> {
			String text = searchBox.getText();

			// searchBox.selectPositionCaret(text.length());
			// Check if it is a composite word
			if (text.contains(" ")) {
				String[] names = text.split(" ");
				text = names[names.length - 1];
			}

			// Do nothing for empty searchbox
			if (text == null || text.isEmpty()) {
				return null;
			} else { // Filter the database to the entered word
				final String uppercase = text.toUpperCase();
				System.out.println(uppercase);
				return (Name) -> Name.getName().toUpperCase().contains(uppercase);
			}
		}, searchBox.textProperty()));

		currentNameText.setText("Choose names from the database");
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

	// Reset the label
	public void clear() {
		_currentName.clear();
		currentNameText.setText("Choose names from the database");
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
			PracticeWindowController Practice = loader.getController();

			// CHANGE THIS
			// Pass the playlist to the practice window
			Practice.setPlaylist(_playlist);

			Stage stage = new Stage();
			stage.setScene(new Scene(content));
			stage.show();
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					File file1 = new File("fullName.wav");
					File file2 = new File("attempt.wav");
					File file3 = new File("compare.wav");
					file1.delete();
					file2.delete();
					file3.exists();
				}
			});
		} catch (IOException e) {
		}
	}

	public void randomiseList() {
		FXCollections.shuffle(_playlist);
	}

	public void micTest() {
		Audio audio = new Audio();
		audio.setRecording(_namesList.get(0).toString(), "MicTestWindow.fxml");
	}

	public void addToName() {
		_currentName.add(namesListView.getSelectionModel().getSelectedItem());
		String name = new String();
		for (Name item : _currentName) {
			name += item.getName() + " ";
		}
		searchBox.setText(name);
		currentNameText.setText(name);
	}

	public void addToPlaylist() {
		//Split a composite name
		String[] text = searchBox.getText().split(" ");
		
		//Find the names objects of the given names
		ArrayList<Name> namesFromText = new ArrayList<Name>() {
			
			@Override
			// Display the name in a formatted way in the table by overriding toString() method
			public String toString() {
				String nameString = new String();
				for (Name name : this) {
					nameString += name.getName() + " ";
				};
				return nameString;
			}
		};
		for (String word : text) {
			namesFromText.add(DataBase.findName(word.toUpperCase()));
			System.out.println("Found " + namesFromText.get(0).getName() + " name");
		}
		if (_playlist.contains(namesFromText)) {
			ErrorDialog.showError("This name is already in the playlist");
		} else if (!_currentName.isEmpty()) {
			_playlist.add(namesFromText);
			_currentName.clear();
			currentNameText.setText("Choose names from the database");
		}
	}


	public void upload() {
		// Restrict file search to .txt files only
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");

		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();

		// Set NameSayer directory as initial directory
		File file = new File(System.getProperty("user.home") + "/NameSayer");
		fileChooser.setInitialDirectory(file);

		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setTitle("Open Resource File");
		File nameList = fileChooser.showOpenDialog(stage);
		NamesListReader reader = new NamesListReader(nameList);
		ArrayList<ArrayList<Name>> names = reader.getListedNames(_namesList);
		System.out.println(names);
		_playlist.addAll(names);

	}

}
