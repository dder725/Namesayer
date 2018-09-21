package GUI;

import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RecordingOptionsController {

	public Button closeButton;
	
	public void listen() {
		
		
	}
	
	public void redoRecording() {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
		PracticeWindowController practiceWindow = new PracticeWindowController();
		practiceWindow.makeRecording();
		
	}
	
	
	public void saveRecording() {
		
		
	}
	
	public void close() {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
		
	}
	
}
