package GUI;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

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
		System.out.print(_name.getName());
		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		
		try {
			// need the cmd to be moving audio.wav into a file in current directory, attempts 
			//directory, name folder, save under name_1.wav?
			String cmd = "dir=$(pwd); mv \"audio.wav\" $dir/UserAttempts/"+_name.getName()+"_attempts;"
					+ " cd UserAttempts/\"+name+\"_attempts; mv \"audio.wav\" "+_name.getName()+"_"+ts+"";
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
