package GUI;

import java.io.File;
import application.Name;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RecordingOptionsController {
	public Button closeButton;
	public PracticeWindowController _practiceWindow;
	
	/** Method called when user wants to listen to their recording
	 *  (attempt.wav)
	 */
	public void listen() {
		Audio audio = new Audio();
		audio.playRecording("attempt.wav");
	}

	
	/** Method called when user wants re-do their recording for a name
	 *  (delete and re-make the file the file attempt.wav)
	 */
	public void redoRecording() {
		close();
		_practiceWindow.makeRecording();
	}

	
	/** Method called when the "recording options" window is closed by
	 *  the "close" button. Deletes the file attempt.wav
	 */
	public void close() {
		File file = new File("attempt.wav");
		file.delete();
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();

	}
	
	
	public void PWreference(PracticeWindowController pw) {
		_practiceWindow=pw;
	}


}
