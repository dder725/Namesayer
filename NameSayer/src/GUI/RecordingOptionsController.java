package GUI;

import java.io.IOException;

import application.Name;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RecordingOptionsController {
	private Name _name;
	public Button closeButton;

	public void listen() {
		Audio audio = new Audio();
		audio.playRecording(1, "\"audio.wav\"");
	}

	public void redoRecording() {
		close();
		PracticeWindowController practiceWindow = new PracticeWindowController();
		practiceWindow.makeRecording();
	}


	public void saveRecording() {
		String name = _name.getName();

		try {
			// need the cmd to be moving audio.wav into a file in current directory, attempts 
			//directory, name folder, save under name_1.wav?
			String cmd = "cd UserAttempts; dir=$(pwd);  mv \"audio.wav\" dir/ ";
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
	
	public void setName(Name name) {
		_name = name;
	}
	


}
