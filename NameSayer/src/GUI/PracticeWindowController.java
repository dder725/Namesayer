package GUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import application.Name;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PracticeWindowController {
	private ObservableList<Name> _playlist;

	private Integer _index = 0;

	@FXML
	private Label nameLabel;

	@FXML
	private ChoiceBox<String> versionChoice, attemptChoice;

	@FXML
	private Button makeRecording;
	@FXML
	private Button nextName;
	@FXML
	private Button listen;
	@FXML
	private Button compare;
	@FXML
	private CheckBox badRecordingCheckBox;

	public void setPlaylist(ObservableList<Name> playlist) {
		_playlist = playlist;
		
		populateVersionChoice();
		setNameLabel(_playlist.get(0).getName(), 66);

		setBadRecordingCheckbox(_playlist.get(_index));
	}

	private void setBadRecordingCheckbox(Name name) {
		if (name.isBadRecording(getVersionNum())) {
			badRecordingCheckBox.setSelected(true);
		} else {
			badRecordingCheckBox.setSelected(false);
		}
	}

	private void populateVersionChoice() {
		versionChoice.getItems().clear();
		versionChoice.getItems().addAll(_playlist.get(_index).getVersions());
		versionChoice.getSelectionModel().selectFirst();
		versionChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		      @Override
		      public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
		    	  String version = versionChoice.getItems().get(observableValue.getValue().intValue());
		    	  int versionNum = Character.getNumericValue(version.charAt(version.lastIndexOf('_') + 1));

		    	  System.out.println("Version num is " + versionNum);
		    	  if (_playlist.get(_index).isBadRecording(versionNum)) {
		  			badRecordingCheckBox.setSelected(true);
		  		} else {
		  			badRecordingCheckBox.setSelected(false);
		  		}
		      }
		    });
	}

	private void setNameLabel(String name, Integer size) {
		nameLabel.setText(name);
		nameLabel.setFont(new Font("System", size));
	}

	public String getNameLabel() {
		String name = nameLabel.getText();
		return name;
	}

	public void playRecording() {
		String path = getSelectedRecordingDirectory();
		Audio audio = new Audio();
		audio.playRecording(path);
	}
	
	public void playAttempt() {
		String path = getSelectedAttemptDirectory();
		Audio audio = new Audio();
		audio.playRecording(path);
	}

	public void makeRecording() {
		Audio audio = new Audio();
		audio.PWreference(this);
		audio.setRecording(_playlist.get(_index), "RecordingOptionsWindow.fxml");
	}

	public void pastAttempts() {

	}

	public void nextName() {
		_index++;
		if (_index <= _playlist.size() - 1) {
			populateVersionChoice();
			setBadRecordingCheckbox(_playlist.get(_index));
			setNameLabel(_playlist.get(_index).getName(), 66);
		} else {
			setNameLabel("Congratulations! \n You finished this practice!", 30);
			disableOptions();
		}
		;
	}

	private void disableOptions() {
		versionChoice.setDisable(true);
		attemptChoice.setDisable(true);
		nextName.setDisable(true);
		listen.setDisable(true);
		makeRecording.setDisable(true);
		compare.setDisable(true);
		badRecordingCheckBox.setVisible(false);
	}

	public String getSelectedRecordingDirectory() {
		String selectedVersion = versionChoice.getSelectionModel().getSelectedItem().toString();
		Integer numOfVersion = Character.getNumericValue(selectedVersion.charAt(selectedVersion.lastIndexOf('_') + 1));
		String versionDir = _playlist.get(_index).getRecordingDir(numOfVersion);

		return versionDir;
	}
	
	public String getSelectedAttemptDirectory() {
		String selectedVersion = versionChoice.getSelectionModel().getSelectedItem().toString();
		Integer numOfVersion = Character.getNumericValue(selectedVersion.charAt(selectedVersion.length() - 1));
		String versionDir = _playlist.get(_index).getRecordingDir(numOfVersion);

		return versionDir;
	}
	

	private Integer getVersionNum() {
		String dir = getSelectedRecordingDirectory();
		String selectedVersion = versionChoice.getSelectionModel().getSelectedItem().toString();
		Integer numOfVersion = Character.getNumericValue(selectedVersion.charAt(selectedVersion.lastIndexOf('_') + 1));
		
		return numOfVersion;
	}

	public void markAsBad() {
		String dir = getSelectedRecordingDirectory();
		if (badRecordingCheckBox.isSelected()) {
			_playlist.get(_index).modifyBadTag(dir, true, getVersionNum());

		} else if (!badRecordingCheckBox.isSelected()) {
			_playlist.get(_index).modifyBadTag(dir, false, getVersionNum());
		}
		populateVersionChoice();
	}

	public String getCurrentVersion() {
		String version = versionChoice.getSelectionModel().getSelectedItem().toString();
		return version;
	}

	
	public void compare() {
		//Plays official recording from database
		playRecording();
		//Plays recording from attempts list
		playAttempt();
	}
	
}
