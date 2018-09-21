package GUI;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PracticeWindowController {

	
	public void playRecording() {
		Audio audio = new Audio();
		audio.playRecording();
	}
	
	public void makeRecording() {
		Audio audio = new Audio();
		audio.makeRecording();
	}
	
	public void pastAttempts() {

	}
	
	public void nextName() {

	}
	
	
}
