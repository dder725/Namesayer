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
	private ObservableList<Name> _playlist;
	private ObservableList<String> attempts;
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
		setListenerVersionChoice(false);
		setNameLabel(_playlist.get(0).getName(), 66);

		populateAttemptChoice();
		
		// Check if the first name in the playlist is a bad recording
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
		if (_index != 0) {
			versionChoice.getItems().clear();
		}
		versionChoice.getItems().addAll(_playlist.get(_index).getVersions());
		versionChoice.getSelectionModel().selectFirst();
	}

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

	private void populateAttemptChoice() {
		if (_index != 0 || !attemptChoice.getItems().isEmpty()) {
			attemptChoice.getItems().clear();
		}
		attempts = FXCollections.observableArrayList(getAttemptsFiles());
		attemptChoice.getItems().addAll(attempts);
		attemptChoice.getSelectionModel().selectFirst();
	}

	private ArrayList<String> getAttemptsFiles() {
		File attemptFolder = new File(
				System.getProperty("user.home") + "/NameSayer/UserAttempts/" + _playlist.get(_index).getName() + "_attempts");
		File[] attemptFiles = attemptFolder.listFiles();
		ArrayList<String> attemptPaths = new ArrayList<String>();
		for (int i = 0; i < attemptFiles.length; i++) {
			if (attemptFiles[i].isFile()) {
				attemptPaths.add(attemptFiles[i].getAbsolutePath()
						.substring(attemptFiles[i].getAbsolutePath().lastIndexOf('/') + 1));
			}
		}
		return attemptPaths;

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

	public void nextName() {
		_index++;
		if (_index <= _playlist.size() - 1) {
			populateVersionChoice();
			populateAttemptChoice();
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
		String dir = System.getProperty("user.home") + "/NameSayer/UserAttempts/" + _playlist.get(_index).getName()+"_attempts/"
				+ attemptChoice.getSelectionModel().getSelectedItem();
		return dir;
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
