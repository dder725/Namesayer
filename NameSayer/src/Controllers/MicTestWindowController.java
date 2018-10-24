/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tooltip;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;

import application.InputRecorder;

public class MicTestWindowController implements Initializable {
    
	@FXML private JFXProgressBar level;
	@FXML private JFXButton startTest;
    @FXML 
    private void testMic() {
    	//Bind the results of the recorder to the ProgressBar
        new Thread(new InputRecorder(level)).start();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
 
	// Set help tooltips for the buttons
	public void setHints() {
		level.setTooltip(new Tooltip("This level represents sensitivity of your microphone"));
		startTest.setTooltip(new Tooltip("Press to start the mic test"));
	}
}
