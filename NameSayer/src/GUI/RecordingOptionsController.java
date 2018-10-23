package GUI;

import java.io.File;
import java.io.IOException;

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
		saveRecording();
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();

	}
	
	public void saveRecording() {
		String name = _practiceWindow.getNameLabel();
		name = name.replaceFirst("\\s+","");
		name = name.replaceAll("\\s+","_");
		File file = new File("UserAttempts/"+name+".wav");
		if (file.exists()) {
			file.delete();
		}
		try {
			// need the cmd to be moving audio.wav into a file in current directory, attempts 
			//directory, name folder, save under name.wav
			String cmd = "dir=$(pwd); mv \"attempt.wav\" $dir/UserAttempts/"+name+".wav";
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
