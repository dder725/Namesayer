package GUI;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import application.Name;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RecordingOptionsController {
	private Name _name;
	public Button closeButton;
	public PracticeWindowController _practiceWindow;
	

	public void listen() {
		Audio audio = new Audio();
		audio.playRecording("\"audio.wav\"");
	}

	public void redoRecording() {
		close();
		_practiceWindow.makeRecording();
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
	
	public void PWreference(PracticeWindowController pw) {
		_practiceWindow=pw;
	}


}
