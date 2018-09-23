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
		close();
		PracticeWindowController practiceWindow = new PracticeWindowController();
		practiceWindow.makeRecording();

	}


	public void saveRecording() {
		PracticeWindowController window = new PracticeWindowController();
		String name = window.getNameLabel();

		
		try {
			// need the cmd to be moving audio.wav into a file in current directory, attempts 
			//directory, name folder, save under name_1.wav?
			String cmd = "dir=$(pwd); mkdir "+name+";  mv \"audio.wav\" dir/ ";
			ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
			Process process = builder.start();
			process.waitFor();
		} catch (IOException E) {
			E.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

	}

	public void close() {
		try {
			String cmd = "rm \"audio.wav\"";
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
