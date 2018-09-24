package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SavedWindowController {

	@FXML Button okButton;
	@FXML Button closeButton;
	@FXML Label fileNameLabel;
	
	
	public void setLabel(String name, Integer size) {
		fileNameLabel.setText(name);
		fileNameLabel.setFont(new Font("System", size));
	}
	
	public void ROwindowReference(Button button) {
		closeButton=button;
		
	}
	
	public void ok() {
		Stage saveStage = (Stage) okButton.getScene().getWindow();
		saveStage.close();
		Stage Recordingstage = (Stage) closeButton.getScene().getWindow();
		Recordingstage.close();
	}
	
}
