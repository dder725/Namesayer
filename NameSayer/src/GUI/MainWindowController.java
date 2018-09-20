package GUI;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

import application.DataBase;
import application.Name;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainWindowController {
	private ObservableList<Name> _namesList = FXCollections.observableArrayList();
	private ObservableList<Name> _playlist = FXCollections.observableArrayList();
	@FXML
	private ListView<Name> playlistView;

	@FXML
	private ListView<Name> namesListView;

	public void populateTableView() {
		namesListView.setItems(_namesList);
		playlistView.setItems(_playlist);
		_namesList.addAll(DataBase.getNamesList());

		FXCollections.sort(_namesList, new Comparator<Name>() {
			@Override
			public int compare(final Name object1, final Name object2) {
				return object1.getName().compareTo(object2.getName());
			}
		});
	}

	public void addToPractice() {

		// Cannot add a name which is already in the practice list
		if (_playlist.contains(namesListView.getSelectionModel().getSelectedItem())) {
			ErrorDialog.showError("This name is already in the list");
		} else {
			_playlist.add(namesListView.getSelectionModel().getSelectedItem());
		}
	}

	// Add a name from the database to the playlist on doubleclick
	public void setupDoubleClickAdd() {
		namesListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) {
					addToPractice();
				}
			}
		});
	}

	public void removeFromPractice() {
		_playlist.remove(playlistView.getSelectionModel().getSelectedIndex());
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
		_playlist.clear();
	}

	public void practice() {
	}

	public void randomiseList() {
		FXCollections.shuffle(_playlist);
	}
}
