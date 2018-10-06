package GUI;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;

import application.Name;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
	private ObservableList<ArrayList<Name>> _playlist;
	private ObservableList<String> attempts;
	private Integer _index = 0;

	@FXML
	private Label nameLabel;

	@FXML
	private ChoiceBox<String> versionChoice;

	@FXML
	private JFXButton makeRecording;
	@FXML
	private JFXButton nextName;
	@FXML
	private JFXButton listen;
	@FXML
	private JFXButton compare;
	@FXML
	private CheckBox badRecordingCheckBox;

	public void setPlaylist(ObservableList<ArrayList<Name>> playlist) {
		_playlist = playlist;
		//populateVersionChoice();
		//setListenerVersionChoice(false);
		setLabel();
		//populateAttemptChoice();
		// Check if the first name in the playlist is a bad recording
		//setBadRecordingCheckbox(_playlist.get(_index).get(0));
	}
	
	public void playRecording() {
		Audio audio = new Audio();
		audio.setVariables(_playlist.get(_index));
		audio.playRecording(_playlist.get(_index));
	}

	public void makeRecording() {
		Audio audio = new Audio();
		audio.PWreference(this);
		String fullName = _playlist.get(_index).toString();
		System.out.println(fullName);
		//audio.setRecording(_playlist.get(_index), "RecordingOptionsWindow.fxml");
	}

	public void nextName() {
		File file = new File("output.wav");
		file.delete();
		_index++;
		if (_index <= _playlist.size() - 1) {
//			populateVersionChoice();
//			setBadRecordingCheckbox(_playlist.get(_index));
			setLabel();
		} else {
			setNameLabel("Congratulations! \n You finished this practice!", 30);
			disableOptions();
		};
	}

	public void setLabel() {
		String fullName = "";
		for(int i=0; i<_playlist.get(_index).size(); i++) {
			String name = _playlist.get(_index).get(i).getName();
			fullName= fullName +" "+ name; 
		}
		setNameLabel(fullName, 66);
	}

	private void setBadRecordingCheckbox(Name name) {
		if (name.isBadRecording(getVersionNum())) {
			badRecordingCheckBox.setSelected(true);
		} else {
			badRecordingCheckBox.setSelected(false);
		}
	}

//	private void populateVersionChoice() {
//		if (_index != 0) {
//			versionChoice.getItems().clear();
//		}
//		versionChoice.getItems().addAll(_playlist.get(_index).getVersions());
//		versionChoice.getSelectionModel().selectFirst();
//	}

	private void setListenerVersionChoice(Boolean toRemove) {
		ChangeListener<Number> listen = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
				// String version =
				// versionChoice.getItems().get(observableValue.getValue().intValue()).toString();
				// int versionNum =
				// Character.getNumericValue(version.charAt(version.lastIndexOf('_') + 1));
				int versionNum = observableValue.getValue().intValue() + 1;
				if (versionNum <= 0) {
					versionNum = 1;
				}
				if (_playlist.get(_index).isBadRecording(versionNum)) {
					badRecordingCheckBox.setSelected(true);
				} else {
					badRecordingCheckBox.setSelected(false);
				}
			}
		};
		if (!toRemove) {
			versionChoice.getSelectionModel().selectedIndexProperty().addListener(listen);
		} else {
			versionChoice.getSelectionModel().selectedIndexProperty().removeListener(listen);

		}
	}


	private void setNameLabel(String name, Integer size) {
		nameLabel.setText(name);
		nameLabel.setFont(new Font("System", size));
	}

	public String getNameLabel() {
		String name = nameLabel.getText();
		return name;
	}

	
	private void disableOptions() {
		versionChoice.setDisable(true);
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
	}

	public String getCurrentVersion() {
		String version = versionChoice.getSelectionModel().getSelectedItem().toString();
		return version;
	}

	public void compare() {
		// Plays official recording from database
		playRecording();
		// Plays recording from attempts list
		playAttempt();
	}

	public void refreshAttemptsChoiceBox() {
		populateAttemptChoice();
	}
	
}
