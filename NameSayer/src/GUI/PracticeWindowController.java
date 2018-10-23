package GUI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import application.Name;
import application.VolumeControl;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PracticeWindowController {
	private ObservableList<ArrayList<Name>> _playlist;
	private Integer _index = 0;
	public int _numFiles;
	public ArrayList<String> _directories = new ArrayList<String>();
	private List<ChoiceBox> versionButtons = new ArrayList<>();

	@FXML JFXSlider volumeSlider;
	@FXML FontAwesomeIconView volumeGlyph;
	@FXML private Text nameLabel;
	@FXML private JFXButton makeRecording;
	@FXML private JFXButton nextName;
	@FXML private JFXButton listen;
	@FXML private JFXButton compare;
	@FXML private JFXButton versionsButton;
	@FXML private JFXButton rateAudioButton;

	
	public void sceneResize(Stage stage) {
		nameLabel.wrappingWidthProperty().bind(stage.widthProperty().subtract(15));
	}
	
	
	public void createRecording(String fileName) {
		_numFiles = _playlist.get(_index).size();
		_directories.clear();
		for (int i = 0; i < _numFiles; i++) {
			// Currently using version 1 but can change when we incorporate other versions
			String dir = _playlist.get(_index).get(i).getSelectedRecordingDirectory();
			_directories.add(dir);
		}
		Audio audio = new Audio();
		audio.setDirectories(_directories);
		audio.createRecording(fileName);
	}
	
	public void playRecording() {
		Audio audio = new Audio();
		audio.playRecording("fullName.wav");
	}

	public void makeRecording() {
		File file1 = new File("attempt.wav");
		File file2 = new File("compare.wav");
		file1.delete();
		file2.delete();
		Audio audio = new Audio();
		String fullName = _playlist.get(_index).toString();
		audio.setRecording(fullName, "RecordingOptionsWindow.fxml", this);
	}

	public void compare() {
		File file1 = new File("fullName.wav");
		String name = getNameLabel();
		name = name.replaceFirst("\\s+", "");
		name = name.replaceAll("\\s+", "_");
		File file2 = new File("UserAttempts/" + name + ".wav");
		if (!file2.exists()) {
			ErrorDialog.showError("There is no user attempt to compare with",
					"Make a recording to compare your name pronounciation!");
		} else {
			String fullNamePath = file1.getAbsolutePath();
			String attemptPath = file2.getAbsolutePath();
			_directories.clear();
			_directories.add(fullNamePath);
			_directories.add(attemptPath);
			// Merges official and attempt
			Audio audio = new Audio();
			audio.setDirectories(_directories);
			audio.playRecording("compare.wav");

		}
	}

	public void nextName() {
		reward();
		File file1 = new File("fullName.wav");
		File file2 = new File("attempt.wav");
		File file3 = new File("compare.wav");
		file1.delete();
		file2.delete();
		file3.delete();
		_index++;
		if (_index <= _playlist.size() - 1) {
			setLabel();
			createRecording("fullName.wav");
		} else {
			setNameLabel("Congratulations! \n You finished this practice!", 30);
			disableOptions();
		}
		;
	}

	public void setLabel() {
		String fullName = "";
		for (int i = 0; i < _playlist.get(_index).size(); i++) {
			String name = _playlist.get(_index).get(i).getName();
			fullName = fullName + " " + name;
		}
		int length = fullName.length(); 
		int size;
		if (length <=25 ) {
			size=56;
		}else if (length <= 35) {
			size = 50;
		}else {
			size = 45;
		}
		setNameLabel(fullName, size);
		setButtons();
	}

	public void setButtons() {
		versionButtons.clear();
		// create buttons for each name
		// need to change so that only names with multiple versions are added as buttons
		for (int i = 0; i < _playlist.get(_index).size(); i++) {
			Collection<String> versions = _playlist.get(_index).get(i).getVersions();
			if (versions.size() > 1) {
				_playlist.get(_index).get(i).setCheckBox();
				ChoiceBox versionChoices = _playlist.get(_index).get(i).getChoiceBox();
				versionButtons.add(versionChoices);
			}
		}
		if (versionButtons.isEmpty()) {
			versionsButton.setDisable(true);
		} else {
			versionsButton.setDisable(false);
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

	/**
	 * Method that gets called when the user selects the versions button
	 */
	public void versionsButton() {
		File file1 = new File("fullName.wav");
		File file2 = new File("compare.wav");
		file1.delete();
		file2.delete();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("VersionsWindow.fxml"));
			Parent content = (Parent) loader.load();
			VersionWindowController controller = loader.getController();
			controller.addChoiceBoxes(versionButtons);
			controller.PWreference(this);
			Stage stage = new Stage();
			stage.setScene(new Scene(content));
			stage.setResizable(false);
			stage.show();
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					createRecording("fullName.wav");
				}
			});
		} catch (IOException e) {
		}
	}

	/**
	 * Method that gets called when the user selects the rate audio button
	 */
	public void rateRecordingButton() {
		List<JFXButton> buttons = new ArrayList<>();

		// Create buttons for each name
		for (int i = 0; i < _playlist.get(_index).size(); i++) {
			String name = _playlist.get(_index).get(i).getName();
			JFXButton button = new JFXButton(name);
			if (_playlist.get(_index).get(i).isBadRecording()) {
				System.out.println(_playlist.get(_index).get(i).getName() + " is bad");
				button.setStyle("-fx-background-color: red");
			} else {
				button.setStyle("-fx-background-color: darkseagreen");
			}
			buttons.add(button);
		}
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("RateAudioWindow.fxml"));
			Parent content = (Parent) loader.load();
			RateAudioController controller = loader.getController();
			controller.addButtons(buttons);
			controller.setNames(_playlist.get(_index));
			Stage stage = new Stage();
			stage.setScene(new Scene(content));
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
		}
	}

	private void disableOptions() {
		nextName.setDisable(true);
		listen.setDisable(true);
		makeRecording.setDisable(true);
		compare.setDisable(true);
		versionsButton.setDisable(true);
		rateAudioButton.setDisable(true);
	}

	public void setPlaylist(ObservableList<ArrayList<Name>> playlist) {
		_playlist = playlist;
		setLabel();
	}

	// Set hints on buttons in the window
	public void setHints() {
		makeRecording.setTooltip(new Tooltip("Record your attempt of this name"));
		nextName.setTooltip(new Tooltip("Proceed to the next name in the playlist"));
		listen.setTooltip(new Tooltip("Listen to the recording of this name"));
		compare.setTooltip(new Tooltip("Play the original recording followed by your attempt"));
		versionsButton.setTooltip(new Tooltip("Select a different recording of this name"));
		rateAudioButton.setTooltip(new Tooltip("Rate the recording of this name"));
	}

	// Display a reward window every 5 practiced names
	public void reward() {
		if ((_index + 1) % 5 == 0 && _index != 0) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Well done!");
			alert.setHeaderText("Good job!");
			alert.setContentText("You have practiced " + (_index + 1) + " names so far!");
			alert.showAndWait();
		}
	}

	// Set up volume control slider
	public void setVolumeControl() {
		volumeSlider.setValue(VolumeControl.getMasterOutputVolume() * 100);
		volumeGlyph.setIcon(FontAwesomeIcon.VOLUME_UP);

		//Set up the listener which detects whether the slider was moved
		volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				if (new_val.floatValue() > 0) {
					if (VolumeControl.getMasterOutputMute()) {
						System.out.println("The system is mute");
						VolumeControl.setMasterOutputMute(false);
					}
					if (new_val.doubleValue() > old_val.doubleValue()) {
						volumeGlyph.setIcon(FontAwesomeIcon.VOLUME_UP);
						VolumeControl.setMasterOutputVolume((float) (new_val.floatValue() / 100));
						System.out.println("Volume up");
					} else {
						volumeGlyph.setIcon(FontAwesomeIcon.VOLUME_DOWN);
						VolumeControl.setMasterOutputVolume((float) (new_val.floatValue() / 100));
						System.out.println("Volume down");
					}
				} else {
					setMute();
				}
			}
		});
	}

	public void setMute() {
		//You cannot fully set the system mute as it does not allow to unmute itself
		if (VolumeControl.getMasterOutputVolume() > 0) {
			VolumeControl.setMasterOutputVolume(0);
			volumeGlyph.setIcon(FontAwesomeIcon.VOLUME_OFF);
			volumeSlider.setValue(0);
		} else {
			VolumeControl.setMasterOutputMute(false);
			volumeGlyph.setIcon(FontAwesomeIcon.VOLUME_UP);
		}
	}
}
