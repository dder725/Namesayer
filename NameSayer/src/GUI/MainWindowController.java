package GUI;

import java.io.IOException;
import application.DataBase;
import application.Name;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.stage.Stage;

public class MainWindowController {
	
	@FXML
	private ListView<Name> practiceList = new ListView<Name>();
	
	@FXML
	private TreeTableColumn namesList = new TreeTableColumn();
	
	public void populateTableView() {
		DataBase.instantiateDataBase();
	}
	public void addToPractice() {

	}

	public void removeFromPractice() {

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
