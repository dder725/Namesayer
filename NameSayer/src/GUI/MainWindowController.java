package GUI;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

import application.DataBase;
import application.Name;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.Stage;

public class MainWindowController {
	private ObservableList<Name> _namesList = FXCollections.observableArrayList();
	private ObservableList<Name> _practiceList = FXCollections.observableArrayList();
	@FXML
	private ListView<Name> practiceListView;

	@FXML
	private ListView<Name> namesListView;

	@FXML
	private TableColumn nameCol = new TableColumn();

	@FXML
	private TableColumn ratingCol;

	public void populateTableView() {
		DataBase.instantiateDataBase();
		namesListView.setItems(_namesList);
		practiceListView.setItems(_practiceList);
		for (String name : DataBase.getNamesDatabase().keySet()) {
			_namesList.add(DataBase.getNamesDatabase().get(name));
		}

		FXCollections.sort(_namesList, new Comparator<Name>() {
			@Override
			public int compare(final Name object1, final Name object2) {
				return object1.getName().compareTo(object2.getName());
			}
		});
	}

	public void addToPractice() {
		_practiceList.add(namesListView.getSelectionModel().getSelectedItem());
	}

	public void removeFromPractice() {
		_practiceList.remove(practiceListView.getSelectionModel().getSelectedIndex());
	}

	public void playRecording() {

	}

	public void viewRecordings() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("ViewRecordingsWindow.fxml"));
			ViewRecordingsController viewRecordings = (ViewRecordingsController) loader.getController();
			Parent content = (Parent) loader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(content));
			stage.show();
		} catch (IOException e) {
		}
	}

	public void markBad() {

	}

	public void addNewName() {

	}

	public void practice() {

	}

	public void randomiseList() {

	}
}
