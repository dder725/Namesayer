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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PracticeWindowController {
	private ObservableList<Name> _playlist;
<<<<<<< HEAD
	private Integer _index = 0;
=======
	private Integer _index=0;
	
>>>>>>> master
	@FXML
	private Label nameLabel;
	
	@FXML
	private ChoiceBox versionChoice; 
	
	@FXML
	private ChoiceBox attemptChoice;
	
	public void setPlaylist(ObservableList<Name> playlist) {
		_playlist = playlist;
		
		populateVersionChoice();
		setNameLabel(_playlist.get(0).getName(), 66);
	}

<<<<<<< HEAD
=======
	private void setNameLabel(String name) {

	}
>>>>>>> master
	
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
		Audio audio = new Audio();
		audio.playRecording(2);
	}

	public void makeRecording() {
		Audio audio = new Audio();
		audio.setRecording();
	}

	public void pastAttempts() {

	}

	public void nextName() {
		_index++;
		if(_index < _playlist.size()) {
		setNameLabel(_playlist.get(_index).getName(), 66);}
		else {
			setNameLabel("Congratulations! \n You finished this practice!", 30);
		}
		populateVersionChoice();
		
	}

	public void chooseVersion() {

	}

}
