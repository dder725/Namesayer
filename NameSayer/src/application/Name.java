package application;

import java.util.HashMap;

public class Name {
	private String _name;
	private HashMap<Integer, String> _recordings = new HashMap<Integer, String>();
	
	public Name(String name) {
		_name = name; 
	}
	public void addRecordingFile(String dir) {
		char num = dir.charAt(dir.length()- 1); //Get the number of the recording
		_recordings.put(Character.getNumericValue(num), "example");
	}
	public String getName() {
		return _name;
	}
}
