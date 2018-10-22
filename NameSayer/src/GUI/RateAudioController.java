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
import javafx.scene.layout.FlowPane;

public class RateAudioController {

	@FXML private FlowPane rateButtonPane;
	ArrayList<Name> _names;
	ArrayList<String> _recordings;
	/**
	 * Method that gets called when the user selects the rate audio button
	 */
	public void addButtons(List<JFXButton> buttons) {
			rateButtonPane.setAlignment(Pos.CENTER);
			rateButtonPane.getChildren().addAll(buttons);
			for(Node buttonNode : rateButtonPane.getChildren()) {
				if(buttonNode instanceof JFXButton) {
					JFXButton button = (JFXButton) buttonNode;
					button.setOnAction((ActionEvent e) -> {
						String nameAsString = button.getText();
						for(Name name: _names) {
							if(name.getName().equals(nameAsString)) {
								if(!name.isBadRecording()) {
									System.out.println("Setting bad recording");
									name.modifyBadTag(true);
									button.setStyle("-fx-background-color: red");
								} else {
									System.out.println("Setting good recording");
									name.modifyBadTag(false);
									button.setStyle("-fx-background-color: darkseagreen");
								}
							}
						}
					});
					System.out.println("Found a button!");
				}
			}
	}
	
	//Pass the names to the ratings window
	public void setNames(ArrayList<Name> names) {
		_names = names;
		System.out.println(_names);
	}
	
	//Mark selected name as bad
	
	
	
}
