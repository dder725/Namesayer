package GUI;

import java.io.File;
import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class RecordingWindowController {	
	@FXML JFXButton recordButton;
	@FXML JFXButton stopButton;
	@FXML ProgressBar progressBar;
	private IntegerProperty seconds = new SimpleIntegerProperty(500);
	Thread thread = new Thread();
	Timeline timeline = new Timeline();
	Audio audio;
	private PracticeWindowController _practiceWindow;


	/** Method to allow the user to test if their microphone is working
	 */
	public void micTest() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("MicTestWindow.fxml"));
			Parent content = (Parent) loader.load(); 
			Stage stage = new Stage();
			stage.setScene(new Scene(content));
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/** Method called when user pushes the "Start Recording" button. 
	 *  Calls the startRecording() method in the audio class and then 
	 *  calls listenWindow()
	 */
	public void startRecording() {
		stopButton.setDisable(false);
		audio = new Audio();
		audio.startRecording();

		progressBar.progressProperty().bind(seconds.divide(500.0).subtract(1).multiply(-1));

		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() {
				timeline = new Timeline();
				timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(5), new KeyValue(seconds, 0)));
				timeline.play();
				timeline.setOnFinished(e -> {
					recordingFinished();
				});
				return null;
			}
		};
		thread = new Thread(task);
		thread.start();
	}


	public void recordingFinished() {
		Stage stage = (Stage) progressBar.getScene().getWindow();
		listenWindow();
		stage.close();
		
	}


	/** Method called when user pushes the "Stop Recording" button. 
	 */
	public void stopRecording() {
		stopButton.setDisable(false);
		audio.stopRecording();
		timeline.stop();
		recordingFinished();
	}


	/** Method to open "Recording Options" window
	 */
	public void listenWindow() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("RecordingOptionsWindow.fxml"));
			Parent content = (Parent) loader.load();
			RecordingOptionsController controller = loader.getController();
			controller.PWreference(_practiceWindow);
			Stage stage = new Stage();
			stage.setScene(new Scene(content));
			stage.setResizable(false);
			stage.show(); 
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					controller.close();
				}
			});
		} catch (IOException e) {
		}
	}
	
	public void PWreference(PracticeWindowController pw) {
		_practiceWindow=pw;
	}
	
}