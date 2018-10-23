package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

	// Check if selected recording is bad
	public Boolean isBadRecording() {
		// If the absolute path contains (Bad) tag, the recording is bad
		if (this.getSelectedRecordingDirectory().contains("(Bad)")) {
			return true;
		} else {
			return false;
		}
	}

	//Change (Bad) tag at the end of the selected recording file
	public void modifyBadTag(Boolean isBad) {
		int recordingNum = this.getSelectedRecordingNum();
		File recordingName = new File(this.getSelectedRecordingDirectory());
		
		if(!this.isBadRecording()) {
			File newName = new File(recordingName.getPath() + "(Bad)");
			recordingName.renameTo(newName);
			recordingName = newName;
			_recordingsDir.put(recordingNum, recordingName.getPath());
			
			//Add it to badRecordings.txt
			DataBase.addABadRecording(recordingName.getAbsolutePath(), true);
		} else { //Remove the (Bad) tag if isBad is false
			File newName = new File(recordingName.getPath().replace("(Bad)", ""));
			recordingName.renameTo(newName);
			recordingName = newName;
			_recordingsDir.put(recordingNum, recordingName.getPath());
			
			//Add it to badRecordings.txt
			DataBase.addABadRecording(recordingName.getAbsolutePath(), true);
		}
	}

	// Two names are the same if they their spelling is the same
	@Override
	public boolean equals(Object name) {
		Name nameToCompare = (Name) name;
		if (name instanceof Name && this.getName().equals(nameToCompare.getName())) {
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
			Integer numOfVersion = Character
					.getNumericValue(selectedVersion.charAt(selectedVersion.lastIndexOf('_') + 1));
			String versionDir = this.getRecordingDir(numOfVersion);

			return versionDir;
		}
	}

	public int getSelectedRecordingNum() {
		if (getVersions().size() == 1) {
			return 1;
		} else {
			String selectedVersion = _versions.getSelectionModel().getSelectedItem().toString();
			Integer numOfVersion = Character
					.getNumericValue(selectedVersion.charAt(selectedVersion.lastIndexOf('_') + 1));
			return numOfVersion;
		}
	}
}
