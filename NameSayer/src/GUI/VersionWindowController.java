package GUI;

import java.util.List;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class VersionWindowController {
	
	@FXML private FlowPane versionButtonPane;
	
	public void addChoiceBoxes(List<ChoiceBox> versions) {
		versionButtonPane.setAlignment(Pos.CENTER);
		versionButtonPane.getChildren().addAll(versions);
	}

	public void setButton() {
		Stage stage = (Stage) versionButtonPane.getScene().getWindow();
		stage.close();
	}
	
}
