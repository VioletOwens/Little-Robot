package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;


public class StatusManager implements Initializable{

	@FXML
	AnchorPane CommandAnchorPane;
	@FXML
	Label ListOfConditionsLabel;
	@FXML
	TextField StatusTextField;
	@FXML
	ListView<String> ListOfCommands;
	@FXML
	ListView<String> ListOfConditions;
	@FXML
	Rectangle Rectangle;
	
	@FXML
	void undo() {
		
	}
	
	@FXML
	void addStatus() {
		
	}
	
	@FXML
	void removeStatus() {
		
	}
	
	@FXML
	void addCondition() {
		
	}
	
	@FXML
	void deleteCondition() {
		
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {


		
	}
	
	
	
	

}
