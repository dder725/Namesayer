package GUI;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RecordingWindowController {	
	@FXML Button recordButton;
	@FXML ProgressBar progressBar;
	private IntegerProperty seconds = new SimpleIntegerProperty(500);


	/** Method to allow the user to test if their microphone is working
	 */
	public void micTest() {


	}


	/** Method called when user pushes the "Start Recording" button. 
	 *  Calls the startRecording() method in the audio class and then 
	 *  calls listenWindow()
	 */
	public void startRecording() {
		Audio audio = new Audio();
		audio.startRecording();

		progressBar.progressProperty().bind(seconds.divide(500.0).subtract(1).multiply(-1));

		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() {
				Timeline timeline = new Timeline();
				timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(5), new KeyValue(seconds, 0)));
				timeline.play();
				timeline.setOnFinished(e -> {
					recordingFinished();
				});
				return null;
			}
		};
		Thread thread = new Thread(task);
		thread.start();
	}


	public void recordingFinished() {
		Stage stage = (Stage) progressBar.getScene().getWindow();
		stage.close();
		listenWindow();
	}


	/** Method called when user pushes the "Stop Recording" button. 
	 */
	public void stopRecording() {


	}


	/** Method to open "Recording Options" window
	 */
	public void listenWindow() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("RecordingOptionsWindow.fxml"));
			Parent content = (Parent) loader.load();
			RecordingOptionsController window = loader.getController();
			Stage stage = new Stage();
			stage.setScene(new Scene(content));
			stage.show(); 
		} catch (IOException e) {
		}
	}

}