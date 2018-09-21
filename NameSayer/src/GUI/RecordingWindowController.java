package GUI;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RecordingWindowController {	
	
	@FXML Text Label;
	@FXML Button recordButton;
	
	public void startRecording() {
		recordButton.setDisable(true);
		for (int i=5; i>=0; i-- ) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
		}			

		Stage stage = (Stage) Label.getScene().getWindow();
		stage.close();
		recordingOptions();
	}

	public void recordingOptions() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("RecordingOptionsWindow.fxml"));
			RecordingOptionsController Practice = (RecordingOptionsController) loader.getController();
			Parent content = (Parent) loader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(content));
			stage.show();
		} catch (IOException e) {
		}
		
	}
	
	
}