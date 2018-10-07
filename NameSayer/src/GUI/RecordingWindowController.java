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
		System.out.println(_window);
		Audio audio = new Audio();
		audio.startRecording();
		Stage stage = (Stage) Label.getScene().getWindow();
		stage.close();
		listenWindow();
	}

	public void listenWindow() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(_window));
			Parent content = (Parent) loader.load();
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