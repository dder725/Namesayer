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
		audio.playRecording(1, "\"audio.wav\"");
	}

	public void redoRecording() {
		close();
		_practiceWindow.makeRecording();
	}


	public void saveRecording() {
		int num = new File("UserAttempts/"+_name.getName()+"_attempts/").list().length +1;
		try {
			// need the cmd to be moving audio.wav into a file in current directory, attempts 
			//directory, name folder, save under name_1.wav?
			String cmd = "dir=$(pwd); mv \"audio.wav\" $dir/UserAttempts/"+_name.getName()+"_attempts/"+_name.getName()+"_"+num+"";
			ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
			Process process = builder.start();
			process.waitFor();
		} catch (IOException E) {
			E.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("SavedWindow.fxml"));
			Parent content = (Parent) loader.load();
			SavedWindowController Saved = loader.getController();

			//Set name of new file
			Saved.setLabel("Your recording has been saved as "+_name.getName()+"_"+num+"", 24);
			Saved.ROwindowReference(closeButton);
			
			Stage stage = new Stage();
			stage.setScene(new Scene(content));
			stage.show();
		} catch (IOException e) {
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
	
	public void PWreference(PracticeWindowController pw) {
		_practiceWindow=pw;
	}


}
