package GUI;

import java.util.ArrayList;
import java.util.List;
import com.jfoenix.controls.JFXButton;

import application.Name;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;

public class RateAudioController {

	@FXML
	private FlowPane rateButtonPane;
	ArrayList<Name> _names;
	ArrayList<String> _recordings;

	//Set up buttons for the ratings window
	public void addButtons(List<JFXButton> buttons) {
		rateButtonPane.setAlignment(Pos.CENTER);
		rateButtonPane.getChildren().addAll(buttons);
		for (Node buttonNode : rateButtonPane.getChildren()) {
			// Set up listeners for every Name button
			if (buttonNode instanceof JFXButton) {
				JFXButton button = (JFXButton) buttonNode;
				button.setOnAction((ActionEvent e) -> {
					String nameAsString = button.getText();
					for (Name name : _names) {
						if (name.getName().equals(nameAsString)) {
							// Toggle the quality of the recording as a Name button is pressed
							if (!name.isBadRecording()) {
								name.modifyBadTag(true);
								//Set a hint for the button
								button.setTooltip(new Tooltip("Bad"));
								button.setStyle("-fx-background-color: red");
							} else {
								name.modifyBadTag(false);
								//Set a hint for the button
								button.setTooltip(new Tooltip("Good"));
								button.setStyle("-fx-background-color: darkseagreen");
							}
						}
					}
				});
			}
		}
	}

	// Pass the names to the ratings window
	public void setNames(ArrayList<Name> names) {
		_names = names;
	}
}
