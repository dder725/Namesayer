package GUI;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PracticeWindowController {

	@FXML
	private Label nameLabel;
	
	public void testMethod() {
		System.out.println("Success");
	}
	public void playRecording() {

	}
	
	public void makeRecording() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("RecordingWindow.fxml"));
			Parent content = (Parent) loader.load();
			RecordingWindowController Practice = (RecordingWindowController) loader.getController();

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
	
	public void chooseVersion() {
		
	}
	
}
