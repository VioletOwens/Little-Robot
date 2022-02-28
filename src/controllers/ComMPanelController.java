package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.Brain;
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
	AnchorPane ComGAnchorPane;
	@FXML
	ComboBox<String> CommandGroupComboBox;
	@FXML
	TextField CommandGTextField;
	@FXML
	ListView<String> ListOfCommands;
	
	
	
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

        if(ComGAnchorPane!=null){
        	ComGAnchorPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent click) {
                	ComGAnchorPane.requestFocus();
                }
        	});
        }	
        if(CommandGroupComboBox!=null) {
        	ListOfCommands.getItems().addAll(Brain.getCommandGroupingKeys());
        	;
        }
        
        if(ListOfCommands!=null) {
        	String[] newArr = Brain.getCommandGroupingValue(
        			ListOfCommands.getSelectionModel().getSelectedItem());
        	System.out.println(newArr[0] + "WOWOWOOW");

        	ListOfCommands.getItems().addAll(
        			Brain.subArr(newArr,1,
					Brain.getCommandGroupingValue(
		        			ListOfCommands.getSelectionModel().
		        			getSelectedItem()).length));
        }
	}

}
