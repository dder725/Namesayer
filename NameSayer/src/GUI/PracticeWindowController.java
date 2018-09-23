package GUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import application.Name;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PracticeWindowController {
	private ObservableList<Name> _playlist;

	private Integer _index = 0;

	@FXML private Label nameLabel;
	
	@FXML private ChoiceBox versionChoice, attemptChoice; 
	
	@FXML private Button makeRecording;
	@FXML private Button nextName;
	@FXML private Button listen;
	
	public void setPlaylist(ObservableList<Name> playlist) {
		_playlist = playlist;
		
		populateVersionChoice();
		setNameLabel(_playlist.get(0).getName(), 66);
	}

	private void populateVersionChoice() {
		versionChoice.getItems().clear();
		versionChoice.getItems().addAll(_playlist.get(_index).getVersions());
		versionChoice.getSelectionModel().selectFirst();
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
		getSelectedRecordingDirectory();
		
		Audio audio = new Audio();
		audio.playRecording(2);
	}

	public void makeRecording() {
		Audio audio = new Audio();
		audio.setRecording(_playlist.get(_index));
	}
	

	public void pastAttempts() {

	}

	public void nextName() {
		_index++;
		if(_index < _playlist.size() - 1) {
		setNameLabel(_playlist.get(_index).getName(), 66);}
		else {
			setNameLabel("Congratulations! \n You finished this practice!", 30);
			disableOptions();
	} ;
		populateVersionChoice();
		
	}

	private void disableOptions() {
		versionChoice.setDisable(true);
		attemptChoice.setDisable(true);
		nextName.setDisable(true);
		listen.setDisable(true);
		makeRecording.setDisable(true);
	}
	
	public String getSelectedRecordingDirectory() {
		String selectedVersion = versionChoice.getSelectionModel().getSelectedItem().toString();
		Integer numOfVersion = Character.getNumericValue(selectedVersion.charAt(selectedVersion.length() - 1));
		String versionDir = _playlist.get(_index).getRecordingDir(numOfVersion);
		
		return versionDir;
	}
	public void chooseVersion() {

	}

}
