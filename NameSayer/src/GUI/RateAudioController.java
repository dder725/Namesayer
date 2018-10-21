package GUI;


import java.util.List;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;

public class RateAudioController {

	@FXML private FlowPane rateButtonPane;
	
	/**
	 * Method that gets called when the user selects the rate audio button
	 */
	public void addButtons(List<JFXButton> buttons) {
			rateButtonPane.setAlignment(Pos.CENTER);
			rateButtonPane.getChildren().addAll(buttons);
	}
	
	
	
}
