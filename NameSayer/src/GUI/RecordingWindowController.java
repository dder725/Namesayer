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
	public PracticeWindowController _practiceWindow;
	public String _window;


	public void startRecording() {
		Audio audio = new Audio();
		audio.startRecording();
		Stage stage = (Stage) Label.getScene().getWindow();
		stage.close();
		System.out.println("Recording window closes");
		listenWindow();
	}

	public void listenWindow() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("RecordingOptionsWindow.fxml"));
			System.out.println("Next window should open now");
			Parent content = (Parent) loader.load();
			
			System.out.println("New window open");
			if (_window.equals("RecordingOptionsWindow.fxml")) {
				RecordingOptionsController window = loader.getController();
				window.setName(_name);
				window.PWreference(_practiceWindow);
			}else if (_window.equals("MicTestWindow.fxml")) {
				MicTestWindowController window = loader.getController();
				window.setName(_name);
				window.PWreference(_practiceWindow);
			}
			Stage stage = new Stage();
			stage.setScene(new Scene(content));
			stage.show(); 
			System.out.println("After stage.show");
		} catch (IOException e) {
		}

	}

	public void PWreference(PracticeWindowController pw) {
		_practiceWindow=pw;
	}

	public void setWindow(String window) {
		_window=window;
	}

}