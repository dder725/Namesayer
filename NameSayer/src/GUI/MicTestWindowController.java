package GUI;

import application.Name;

public class MicTestWindowController {
	
	public void listen() {
		Audio audio = new Audio();
		audio.playRecording(1, "\"audio.wav\"");
	}
	
	public void setName(Name name) {}
	
	public void PWreference(PracticeWindowController pw) {}
	
}
