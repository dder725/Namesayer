package application;

import java.util.HashMap;

import javafx.beans.property.SimpleStringProperty;

public class Name {
	public SimpleStringProperty nameProperty = new SimpleStringProperty();
	public SimpleStringProperty star = new SimpleStringProperty();
	private String _name;
	private HashMap<Integer, String> _recordings = new HashMap<Integer, String>();
	
	public Name(String name) {
		this._name = name; 
		nameProperty.set(_name);
		star.set("*");
	}
	public void addRecordingFile(String dir) {
		char num = dir.charAt(dir.length()- 1); //Get the number of the recording
		_recordings.put(Character.getNumericValue(num), "example");
	}
	public String getName() {
		return _name;
	}
	public String toString() {
		return nameProperty.getValue();
	}
}
