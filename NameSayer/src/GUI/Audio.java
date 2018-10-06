package GUI;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import application.Name;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Audio {

	public int _numFiles;
	public ArrayList<String> _directories= new ArrayList<String>();
	
	public PracticeWindowController _practiceWindow;
	public String _window;

	public void setRecording(String name, String window) {
		_window=window;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("RecordingWindow.fxml"));
			Parent content = (Parent) loader.load();
			RecordingWindowController recording = (RecordingWindowController) loader.getController();
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
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
		}	

	}

	public void playRecording(ArrayList<Name> name) {
		mergePlay();
		try {
			System.out.println(name);
			String cmd = "$(realpath mergePlay.sh)";
			ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
			Process process = builder.start();
			process.waitFor();
		} catch (IOException E) {
			E.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}		
	}

	public void playRecording(String path) {
		try {
			String cmd = "ffplay -autoexit audio.wav";
			ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
			Process process = builder.start();
			process.waitFor();
		} catch (IOException E) {
			E.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}		
	}
	
	public void setVariables(ArrayList<Name> names) {
		_numFiles = names.size();
		for (int i=0; i<_numFiles; i++) {
			//Currently using version 1 but can change when we incorporate other versions
			String dir = names.get(i).getRecordingDir(1);
			_directories.add(dir);
			System.out.println(dir);
		}
	}
	

		public void mergePlay() {
			
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
			
			String code = "#!/bin/bash\n" + 
					"\n" + 
					"ffmpeg -f concat -safe 0 -i mylist.txt -c copy output.wav\n" + 
					"\n" + 
					"ffplay -autoexit output.wav";

			byte[] content2 = code.getBytes(Charset.forName("UTF-8"));
			FileOutputStream helper2;
			try {
				helper2 = new FileOutputStream("mergePlay.sh");
				helper2.write(content2);
				helper2.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				String cmd = "chmod +x $(realpath mergePlay.sh)";
				ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
				Process process = builder.start();
				process.waitFor();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
	
	public void PWreference(PracticeWindowController pw) {
		_practiceWindow=pw;
	}

}
