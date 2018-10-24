package Controllers;

import javafx.scene.control.Button;
import javafx.stage.Stage;

public class NoNamesController {

	public Button okButton;
	
	public void okButton() {
		Stage stage = (Stage) okButton.getScene().getWindow();
		stage.close();
	}
	
}
