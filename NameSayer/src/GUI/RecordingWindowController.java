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
		listenWindow();
	}

	public void listenWindow() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(_window));
			Parent content = (Parent) loader.load();
			if (_window.equals("RecordingOptionsWindow.fxml")) {
				RecordingOptionsController window = (RecordingOptionsController) loader.getController();
				window.setName(_name);
				window.PWreference(_practiceWindow);
			}else if (_window.equals("MicTestWindow.fxml")) {
				MicTestWindowController window = (MicTestWindowController) loader.getController();
				window.setName(_name);
				window.PWreference(_practiceWindow);
			}
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

	public void PWreference(PracticeWindowController pw) {
		_practiceWindow=pw;
	}

	public void setWindow(String window) {
		_window=window;
	}

}