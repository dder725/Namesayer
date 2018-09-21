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
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PracticeWindowController {
	private ObservableList<Name> _playlist;
	
	@FXML
	private Label nameLabel;
	
	public void setPlaylist(ObservableList<Name> playlist) {
		_playlist = playlist;
		
		setNameLabel(_playlist.get(0).getName());
	}
	
	private void setNameLabel(String name) {
		nameLabel.setText(name);
	}
	public void playRecording() {
		Audio audio = new Audio();
		audio.playRecording();
	}
	
	public void makeRecording() {
		Audio audio = new Audio();
		audio.makeRecording();
	}
	
	public void pastAttempts() {

	}
	
	public void nextName() {

	}
	
	public void chooseVersion() {
		
	}
	
}
