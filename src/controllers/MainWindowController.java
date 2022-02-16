package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Brain;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MainWindowController implements Initializable{

	@FXML
	TextField mainTextField;
	@FXML
	Label mainLabel;
	

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        if(this.mainTextField!=null) {
        mainTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
        	@Override
            public void handle(KeyEvent k) {
                if (k.getCode().equals(KeyCode.ENTER)) {
                	try {
						Brain.startUp(mainTextField.getText());
					} catch (IOException e) {
						e.printStackTrace();
					}
                   	}
                }
        	});
        }		
	}

}
