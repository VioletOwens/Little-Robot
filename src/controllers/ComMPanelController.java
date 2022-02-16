package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;


public class ComMPanelController implements Initializable{

	@FXML
	AnchorPane ComAnchorPane;
	@FXML
	ComboBox<String> CommandComboBox;
	@FXML
	TextField CommandTextField;
	@FXML
	ListView<String> ListOfCommands;
	@FXML
	Rectangle Rectangle;
	
	
	
	@FXML
	void addCommand() {
		
	}
	
	@FXML
	void deleteCommand() {
		
	}
	
	@FXML
	void undo() {
		
	}
	
	@FXML
	void openAdvComMP() {
		
	}
	
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

        if(ComAnchorPane!=null){
        	ComAnchorPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent click) {
                	ComAnchorPane.requestFocus();
                }
        	});
        }		
	}

}
