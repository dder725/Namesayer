package GUI;

import java.util.concurrent.TimeUnit;

import javafx.scene.control.ProgressBar;

public class RecordingWindowController {

	public ProgressBar recordingProgress;
	
	public void startRecording() {
		
		Thread thread = new Thread( new Timing());
		thread.start();
		
	}
	
	class Timing implements Runnable{

		@Override
		public void run() {
			
			for (int i=0; i<=100; i++ ) {
				recordingProgress.setProgress(i/100.0);
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
}
