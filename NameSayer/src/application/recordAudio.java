package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javafx.concurrent.Task;

public class recordAudio {
	private String _homeDir = System.getProperty("user.dir");

	private Task recordTask() {
		Task task = new Task<Void>() {
			@Override
			public Void call() {
				String[] record = new String[] { "bash", _homeDir + "/recordTheVoice.sh", "micTest" };
				ProcessBuilder pr = new ProcessBuilder(record);
				Process setupPr;
				try {
					setupPr = pr.start();
					setupPr.waitFor();
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
				return null;
			}
		};
		return task;
	}
	private void createRecordScript() {
		PrintWriter writer;
		try {
			writer = new PrintWriter(_homeDir + "/recordTheVoice.sh", "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}}