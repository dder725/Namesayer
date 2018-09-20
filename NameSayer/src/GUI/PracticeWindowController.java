package GUI;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PracticeWindowController {

	
	public void playRecording() {

	}
	
	public void makeRecording() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("RecordingWindow.fxml"));
			RecordingWindowController Practice = (RecordingWindowController) loader.getController();
			Parent content = (Parent) loader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(content));
			stage.show();
		} catch (IOException e) {
		}
	}
	
	public void pastAttempts() {

	}
	
	public void nextName() {

	}
	
	
}
