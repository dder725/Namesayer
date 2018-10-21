package GUI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.jfoenix.controls.JFXButton;
import application.Name;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PracticeWindowController {
	private ObservableList<ArrayList<Name>> _playlist;
	private Integer _index = 0;
	public int _numFiles;
	public ArrayList<String> _directories= new ArrayList<String>();
	@FXML private Text nameLabel;
	@FXML private JFXButton makeRecording;
	@FXML private JFXButton nextName;
	@FXML private JFXButton listen;
	@FXML private JFXButton compare;
	@FXML private CheckBox badRecordingCheckBox;

	
	public void playRecording() {
		_numFiles = _playlist.get(_index).size();
		_directories.clear();
		for (int i=0; i<_numFiles; i++) {
			//Currently using version 1 but can change when we incorporate other versions
			String dir = _playlist.get(_index).get(i).getRecordingDir(1);
			_directories.add(dir);
		}
		Audio audio = new Audio();
		audio.setDirectories(_directories);
		audio.playRecording("fullName.wav");
	}

	
	public void makeRecording() {
		File file = new File("attempt.wav");
		file.delete();
		Audio audio = new Audio();
		String fullName = _playlist.get(_index).toString();
		audio.setRecording(fullName, "RecordingOptionsWindow.fxml");
	}

	public void compare() {
		File file1 = new File("fullName.wav");
		File file2 = new File("attempt.wav");
		String fullNamePath = file1.getAbsolutePath();
		String attemptPath = file2.getAbsolutePath();
		_directories.clear();
		_directories.add(fullNamePath);
		_directories.add(attemptPath);
		// Merges official and attempt
		Audio audio = new Audio();
		audio.setDirectories(_directories);
		audio.playRecording("compare.wav");
		// Plays user recording
	}
	
	public void nextName() {
		File file1 = new File("fullName.wav");
		File file2 = new File("attempt.wav");
		File file3 = new File("compare.wav");
		file1.delete();
		file2.delete();
		file3.delete();
		_index++;
		if (_index <= _playlist.size() - 1) {
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

	
	private void setNameLabel(String name, Integer size) {
		nameLabel.setText(name);
		nameLabel.setFont(new Font("System", size));
	}

	
	public String getNameLabel() {
		String name = nameLabel.getText();
		return name;
	}
	
	/**
	 * Method that gets called when the user selects the versions button
	 */
	public void versionsButton() {
	       List<JFXButton> buttons = new ArrayList<>();

	        // create buttons for each name
	       // need to change so that only names with multiple versions are added as buttons
			for(int i=0; i<_playlist.get(_index).size(); i++) {
				String name = _playlist.get(_index).get(i).getName();
		        JFXButton button = new JFXButton(name);
		        buttons.add(button);
			}
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("VersionsWindow.fxml"));
					Parent content = (Parent) loader.load();
					VersionWindowController controller = loader.getController();
					controller.addButtons(buttons);
					Stage stage = new Stage();
					stage.setScene(new Scene(content));
					stage.show();
				} catch (IOException e) {
				}
	}
	
	
	/**
	 * Method that gets called when the user selects the rate audio button
	 */
	public void rateRecordingButton() {
	       List<JFXButton> buttons = new ArrayList<>();

	        // create buttons for each name
			for(int i=0; i<_playlist.get(_index).size(); i++) {
				String name = _playlist.get(_index).get(i).getName();
		        JFXButton button = new JFXButton(name);
		        buttons.add(button);
			}
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("RateAudioWindow.fxml"));
					Parent content = (Parent) loader.load();
					RateAudioController controller = loader.getController();
					controller.addButtons(buttons);
					Stage stage = new Stage();
					stage.setScene(new Scene(content));
					stage.show();
				} catch (IOException e) {
				}
	}
	
	
	private void disableOptions() {
		nextName.setDisable(true);
		listen.setDisable(true);
		makeRecording.setDisable(true);
		compare.setDisable(true);
	}
	
	
	public void setPlaylist(ObservableList<ArrayList<Name>> playlist) {
		_playlist = playlist;
		setLabel();
		//populateAttemptChoice();
		// Check if the first name in the play list is a bad recording
		//setBadRecordingCheckbox(_playlist.get(_index).get(0));
	}
	
	
//	private void setBadRecordingCheckbox(Name name) {
//		if (name.isBadRecording(getVersionNum())) {
//			badRecordingCheckBox.setSelected(true);
//		} else {
//			badRecordingCheckBox.setSelected(false);
//		}
//	}


//	private void setListenerVersionChoice(Boolean toRemove) {
//		ChangeListener<Number> listen = new ChangeListener<Number>() {
//			@Override
//			public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
//				// String version =
//				// versionChoice.getItems().get(observableValue.getValue().intValue()).toString();
//				// int versionNum =
//				// Character.getNumericValue(version.charAt(version.lastIndexOf('_') + 1));
//				int versionNum = observableValue.getValue().intValue() + 1;
//				if (versionNum <= 0) {
//					versionNum = 1;
//				}
//				if (_playlist.get(_index).isBadRecording(versionNum)) {
///					badRecordingCheckBox.setSelected(true);
//				} else {
//					badRecordingCheckBox.setSelected(false);
//				}
//			}
//		};
//		if (!toRemove) {
//			versionChoice.getSelectionModel().selectedIndexProperty().addListener(listen);
//		} else {
//			versionChoice.getSelectionModel().selectedIndexProperty().removeListener(listen);
//		}
//	}


//	public String getSelectedRecordingDirectory() {
//		String selectedVersion = versionChoice.getSelectionModel().getSelectedItem().toString();
//		Integer numOfVersion = Character.getNumericValue(selectedVersion.charAt(selectedVersion.lastIndexOf('_') + 1));
//		String versionDir = _playlist.get(_index).get(0).getRecordingDir(numOfVersion);
//
//		return versionDir;
//	}
//
//	private Integer getVersionNum() {
//		String dir = getSelectedRecordingDirectory();
//		String selectedVersion = versionChoice.getSelectionModel().getSelectedItem().toString();
//		Integer numOfVersion = Character.getNumericValue(selectedVersion.charAt(selectedVersion.lastIndexOf('_') + 1));
//
//		return numOfVersion;
//	}

//	public void markAsBad() {
//		String dir = getSelectedRecordingDirectory();
//
//		if (badRecordingCheckBox.isSelected()) {
//			_playlist.get(_index).modifyBadTag(dir, true, getVersionNum());
//
//		} else if (!badRecordingCheckBox.isSelected()) {
//			_playlist.get(_index).modifyBadTag(dir, false, getVersionNum());
//		}
//	}

//	public String getCurrentVersion() {
//		String version = versionChoice.getSelectionModel().getSelectedItem().toString();
//		return version;
//	}
	
}
