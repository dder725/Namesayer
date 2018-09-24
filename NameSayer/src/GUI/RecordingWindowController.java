package GUI;

import java.io.IOException;

import application.Name;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class RecordingWindowController {	
	private Name _name;
	@FXML Text Label;
	@FXML Button recordButton;

	public void startRecording() {
		Audio audio = new Audio();
		audio.startRecording();
		Stage stage = (Stage) Label.getScene().getWindow();
		stage.close();
		recordingOptions();
	}

	public void recordingOptions() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("RecordingOptionsWindow.fxml"));
			Parent content = (Parent) loader.load();
			RecordingOptionsController Practice = (RecordingOptionsController) loader.getController();
			Practice.setName(_name);
			Stage stage = new Stage();
			stage.setScene(new Scene(content));
			stage.show();

	        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent we) {
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
	            }
	        });   
			
		} catch (IOException e) {
		}

	}
	public void setName(Name name) {
		_name = name;
	}
	

}