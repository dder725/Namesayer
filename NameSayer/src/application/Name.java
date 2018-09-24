package application;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;

public class Name {
	public SimpleStringProperty nameProperty = new SimpleStringProperty();
	public SimpleStringProperty star = new SimpleStringProperty();
	private String _name;
	private HashMap<Integer, String> _recordings = new HashMap<Integer, String>();
	private HashMap<Integer, String> _recordingsDir = new HashMap<Integer, String>();
	
	public Name(String name) {
		this._name = name; 
		nameProperty.set(_name);
		star.set("*");
	}
	public void addRecordingFile(String dir) {
		char num = dir.charAt(dir.length()- 1); //Get the number of the recording
		_recordings.put(Character.getNumericValue(num), dir.substring(dir.lastIndexOf('/') + 1));
		_recordingsDir.put(Character.getNumericValue(num), dir);
	}
	public String getName() {
		return _name;
	}
	public String toString() {
		return nameProperty.getValue();
	}
	public Collection<String> getVersions(){
		return _recordings.values();
	}
	public String getRecordingDir(int num) {
		return _recordingsDir.get(num);
	}
	public void modifyBadTag(String dir, Boolean isBad, Integer recordingNum) {
		File oldRecordingName = new File(dir);
		File newRecordingName;
		
		//If isBad is true, mark recording with a (Bad) tag
		if(isBad) { 
			newRecordingName = new File(dir + "(Bad)");
			//Add it to the badRecordings.txt
			 DataBase.addABadRecording(oldRecordingName.getAbsolutePath());
		} else { //Remove the (Bad) tag if isBad is false
			newRecordingName = new File(dir.substring(0, (dir.lastIndexOf("("))));
		}
		
		oldRecordingName.renameTo(newRecordingName);
		_recordingsDir.put(recordingNum, newRecordingName.getAbsolutePath());
	}
}
