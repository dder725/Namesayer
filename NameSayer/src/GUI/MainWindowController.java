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
import application.DataBase;
import application.Name;

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
			if (text == null || text.isEmpty()) {
				return null;
			} else {
				final String uppercase = text.toUpperCase();
				return (Name) -> Name.getName().toUpperCase().contains(uppercase);
			}
		}, searchBox.textProperty()));

		currentNameText.setText("Choose names from the database");
		// Implement sort method here
		// FXCollections.sort(_namesList, new Comparator<Name>() {
		// @Override
		// public int compare(final Name object1, final Name object2) {
		// return object1.getName().compareTo(object2.getName());
		// }
		// });
	}

	public void addToName() {
		_currentName.add(namesListView.getSelectionModel().getSelectedItem());
		String name = new String();
		for (Name item : _currentName) {
			name += item.getName() + " ";
		}
		currentNameText.setText(name);
	}

	// Add a name from the database to the playlist on doubleclick
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

	public void clear() {
		_currentName.clear();
	}

	public void practiceButton() {
		if (_playlist.isEmpty()) {
			noNames();
		} else {
			practiceWindow();
		}
	}

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

			//CHANGE THIS
			//Pass the playlist to the practice window
			Practice.setPlaylist(_playlist);

			Stage stage = new Stage();
			stage.setScene(new Scene(content));
			stage.show();
		} catch (IOException e) {
		}
	}

	public void randomiseList() {
		FXCollections.shuffle(_playlist);
	}

	public void micTest() {
		Audio audio = new Audio();
		audio.setRecording(_namesList.get(0), "MicTestWindow.fxml");
	}

	public void addToPlaylist() {
		if (_playlist.contains(_currentName)) {
			ErrorDialog.showError("This name is already in the playlist");
		} else {

			// Display the name in a formatted way in the table by overriding toString()
			// method
			ArrayList<Name> currentNames = new ArrayList<Name>() {
				@Override
				public String toString() {
					String nameString = new String();
					for (Name name : this) {
						nameString += name.getName() + " ";
					}
					;
					return nameString;
				}
			};
			currentNames.addAll(_currentName);
			_playlist.add(currentNames);
		}
		_currentName.clear();
		currentNameText.setText("Choose names from the database");
	}
	
	public void upload() {
		//Restrict file search to .txt files only
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		
		//Set NameSayer directory as initial directory
		File file = new File(System.getProperty("user.home") + "/NameSayer");
		fileChooser.setInitialDirectory(file);
		
		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setTitle("Open Resource File");
		File nameList = fileChooser.showOpenDialog(stage);
		System.out.println(nameList.getAbsolutePath());
	}

}
