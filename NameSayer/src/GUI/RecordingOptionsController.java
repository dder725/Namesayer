package GUI;

import java.io.IOException;

import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RecordingOptionsController {

	public Button closeButton;
	
	public void listen() {
		Audio audio = new Audio();
		audio.playRecording();
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
        try {
            String cmd = "rm \"audio.mp3\"";
            ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
            Process process = builder.start();
            process.waitFor();
        } catch (IOException E) {
            E.printStackTrace();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
		
	}
	
}
