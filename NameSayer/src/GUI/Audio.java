package GUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Audio {

	public int _numFiles;
	public ArrayList<String> _directories= new ArrayList<String>();
	Process process;


	/**Adds file paths to the ArrayList _directories, so that files can be merged
	 */
	public void setDirectories(ArrayList<String> paths) {
		_directories.clear();
		_directories.addAll(paths);
		_numFiles=_directories.size();
	}


	/** Method called when user wants to play a recording. Input is the name of the file to 
	 *  play, either audio from database (fullName.wav), user attempt recording (attempt.wav), 
	 *  or the audio comparing the database recording and user attempt (compare.wav) 
	 */
	public void playRecording(String fileName) {
		//If the audio file exists play the recording
		File file= new File(fileName);
		if (file.exists()) {
			playFile(fileName);
		} else {		//else create recording then play
			makeMeregedFile(fileName);
			playFile(fileName);
		}
	}


	/** Method that takes the file paths from the ArrayList _directories, and merges the 
	 *  the audio into one file (either fullName.wav or compare.wav) 
	 */
	public void makeMeregedFile(String fileName) {
		String cmd = "";
		if (_directories.size()==1) {
			cmd = "cp "+_directories.get(0) +" "+fileName;
		}else {

			String directories = "";
			for (int i=0; i<_numFiles; i++) {
				String dir = _directories.get(i);
				directories = directories +"file "+ dir +"\n";
			}

			byte[] content1 = directories.getBytes(Charset.forName("UTF-8"));
			FileOutputStream helper1;
			try {
				helper1 = new FileOutputStream("mylist.txt");
				helper1.write(content1);
				helper1.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String files = "";
			for (int i=0; i<_directories.size(); i++) {
				files = files +" -i "+_directories.get(i);
			}
			//for 2 files only!!!
			//String cmd = "ffmpeg -f concat -safe 0 -i mylist.txt -c copy "+fileName+ "\n";
			cmd = "ffmpeg"+ files +" -filter_complex '[0:0][1:0]concat=n=2:v=0:a=1[out]' -map '[out]' "+fileName+ "\n";
		}
		System.out.println(cmd);
		try {
			ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
			Process process = builder.start();
			process.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	/** Method to play the audio file (either fullName.wav, attempt.wav or compare.wav)
	 */
	public void playFile(String fileName) {
		try {
			String cmd = "ffplay -autoexit -nodisp "+fileName+ "";
			ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
			Process process = builder.start();
			process.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	/** Method called when user want to create new recording. Opens a new window 
	 *  (RecordingWindow.fxml) so that user can start recording
	 * @param _practiceWindow 
	 */
	public void setRecording(String name, String window, PracticeWindowController practiceWindow) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("RecordingWindow.fxml"));
			Parent content = (Parent) loader.load();
			RecordingWindowController controller = (RecordingWindowController) loader.getController();
			controller.PWreference(practiceWindow);
			Stage stage = new Stage();
			stage.setScene(new Scene(content));
			stage.show();
		} catch (IOException e) {
		}
	}


	/** This method records the users attempt and saves the audio as a file 
	 *  called attempt.wav
	 */
	public void startRecording() {
		Thread BackgroundThread = new Thread() {
			public void run() {
				try {
					String cmd = "ffmpeg -f alsa -i default -t 5 \"attempt.wav\"";
					ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
					process = builder.start();
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
	}


	public void stopRecording() {
		process.destroy();
	}

}
