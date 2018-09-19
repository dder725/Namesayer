package GUI;

import java.io.IOException;
import application.DataBase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWindowController {

	public void populateTableView() {
		DataBase.unzip(System.getProperty("user.dir") + "/names.zip", System.getProperty("user.dir") + "/names");
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
