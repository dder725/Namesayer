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
		if(_practiceList.contains(namesListView.getSelectionModel().getSelectedItem())) { //Cannot add a name which is already in the practice list
			ErrorDialog.showError("This name is already in the list");
		} else {
		_practiceList.add(namesListView.getSelectionModel().getSelectedItem()); }
	}

	public void removeFromPractice() {
		_practiceList.remove(practiceListView.getSelectionModel().getSelectedIndex());
	}
	

	public void clear() {
		_practiceList.clear();
	}

	public void practice() {

	}

	public void randomiseList() {
		FXCollections.shuffle(_practiceList);
	}
}
