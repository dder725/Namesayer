package application;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ChoiceBox;

public class Name {
	public SimpleStringProperty nameProperty = new SimpleStringProperty();
	private String _name;
	private ChoiceBox _versions;
	private HashMap<Integer, String> _recordings = new HashMap<Integer, String>();
	private HashMap<Integer, String> _recordingsDir = new HashMap<Integer, String>();

	public Name(String name) {
		this._name = name;
		nameProperty.set(_name);
		_versions = new ChoiceBox();

	}

	public void addRecordingFile(String dir) {
		char num = dir.charAt(dir.lastIndexOf('_') + 1); // Get the number of the recording
		_recordings.put(Character.getNumericValue(num), dir.substring(dir.lastIndexOf('/') + 1));
		_recordingsDir.put(Character.getNumericValue(num), dir);
	}

	public String getName() {
		return _name;
	}

	public String toString() {
		return nameProperty.getValue();
	}

	public Collection<String> getVersions() {
		return _recordings.values();
	}

	public String getRecordingDir(int num) {
		return _recordingsDir.get(num);
	}

	private void updateRecordingDir(int num, String dir) {
		_recordingsDir.put(num, dir);
	}

	public Boolean isBadRecording(int num) {
		int tagIndex = getRecordingDir(num).lastIndexOf("(Bad)");
		if (tagIndex != -1) {
			return true;
		} else {
			return false;
		}
	}

	public void modifyBadTag(String dir, Boolean isBad, Integer recordingNum) {
		File oldRecordingName = new File(dir);
		File newRecordingName;

		// If isBad is true, mark recording with a (Bad) tag
		if (isBad) {
			newRecordingName = new File(dir + "(Bad)");
			// Add it to the badRecordings.txt
			DataBase.addABadRecording(oldRecordingName.getAbsolutePath(), true);
			updateRecordingDir(recordingNum, oldRecordingName.getAbsolutePath() + "\\(Bad\\)");
		} else { // Remove the (Bad) tag if isBad is false
			newRecordingName = new File(dir.substring(0, (dir.lastIndexOf("("))));
			DataBase.addABadRecording(oldRecordingName.getAbsolutePath(), false);
			updateRecordingDir(recordingNum, newRecordingName.getAbsolutePath());
		}

		oldRecordingName.renameTo(newRecordingName);
	}

	// Two names are the same if they their spelling is the same
	@Override
	public boolean equals(Object name) {
		Name nameToCompare = (Name) name;
		if (name instanceof Name && this.getName().equals(nameToCompare.getName())) {
			//System.out.println(nameToCompare.getName() + " Found to be equal to " + this.getName());
			return true;
		} else {
			return false;
		}
	}
	
	
	public ChoiceBox getChoiceBox() {
		return _versions;
	}

	public void setCheckBox() {
		_versions.getItems().clear();
		_versions.getItems().addAll(this.getVersions());
		_versions.getSelectionModel().selectFirst();
		
	}
	
	
	public String getSelectedRecordingDirectory() {
		if (getVersions().size() == 1) {
			return getRecordingDir(1);
		} else {
		String selectedVersion = _versions.getSelectionModel().getSelectedItem().toString();
		Integer numOfVersion = Character.getNumericValue(selectedVersion.charAt(selectedVersion.lastIndexOf('_') + 1));
		String versionDir = this.getRecordingDir(numOfVersion);
		return versionDir;
		}
	}
}
