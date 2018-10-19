package GUI;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RecordingWindowController {	
	@FXML Text Label;
	@FXML Button recordButton;


	/** Method called when user pushes the "Start Recording" button. 
	 *  Calls the startRecording() method in the audio class and then 
	 *  calls listenWindow()
	 */
	public void startRecording() {
		Audio audio = new Audio();
		audio.startRecording();
		Stage stage = (Stage) Label.getScene().getWindow();
		stage.close();
		System.out.println("Recording window closes");
		listenWindow();
	}


	/** Method to open "Recording Options" window
	 */
	public void listenWindow() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("RecordingOptionsWindow.fxml"));
			Parent content = (Parent) loader.load();
			RecordingOptionsController window = loader.getController();
			Stage stage = new Stage();
			stage.setScene(new Scene(content));
			stage.show(); 
		} catch (IOException e) {
		}
	}

}