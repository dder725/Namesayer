package GUI;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Audio {

	public void makeRecording() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("RecordingWindow.fxml"));
			RecordingWindowController Practice = (RecordingWindowController) loader.getController();
			Parent content = (Parent) loader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(content));
			stage.show();
		} catch (IOException e) {
		}
	}
	
	
	public void playRecording() {
        try {
            String cmd = "ffplay -autoexit \"audio.mp3\"";
            ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
            Process process = builder.start();
            process.waitFor();
        } catch (IOException E) {
            E.printStackTrace();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
	}
	
}
