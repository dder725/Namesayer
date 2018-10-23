/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXProgressBar;

public class MicTestWindowController implements Initializable {
    
	@FXML private JFXProgressBar level;
    @FXML 
    private void testMic() {
    	//Bind the results of the recorder to the ProgressBar
        new Thread(new Recorder(level)).start();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
 
 
}
