package GUI;

import java.io.IOException;

import application.Name;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Audio {

	public PracticeWindowController _practiceWindow;
	public String _window;

	public void setRecording(Name name, String window) {
		_window=window;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("RecordingWindow.fxml"));
			Parent content = (Parent) loader.load();
			RecordingWindowController recording = (RecordingWindowController) loader.getController();
			recording.setName(name);
			recording.PWreference(_practiceWindow);
			recording.setWindow(window);
			Stage stage = new Stage();
			stage.setScene(new Scene(content));
			stage.show();
		} catch (IOException e) {
		}
	}


	public void startRecording() {

		//This section is supposed to record and save the audio as a file called audio.wav
		Thread BackgroundThread = new Thread() {
			public void run() {
				try {
					String cmd = "ffmpeg -f alsa -i default -t 5 \"audio.wav\"";
					ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
					Process process = builder.start();
					process.waitFor();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		};
		BackgroundThread.start();

		//end of recording section

		for (int i=5; i>=0; i-- ) {
			try {
				System.out.println(i);
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
		}	

	}


	public void playRecording(int option, String file) {
		try {
			String cmd = "ffplay -autoexit "+file+"";
			ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
			Process process = builder.start();
			process.waitFor();
		} catch (IOException E) {
			E.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}


	public void PWreference(PracticeWindowController pw) {
		_practiceWindow=pw;
	}

}
