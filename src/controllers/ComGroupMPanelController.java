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

public class ComGroupMPanelController implements Initializable{
	

	@FXML
	AnchorPane ComGAnchorPane;
	@FXML
	ComboBox<String> CommandGroupGroupComboBox;
	@FXML
	TextField CommandGTextField;
	@FXML
	ListView<String> ListOfCommands;
	@FXML
	ComboBox<String> CommandGroupComComboBox;
	
	
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
        if(CommandGroupGroupComboBox!=null) {
        	CommandGroupGroupComboBox.getItems().addAll(Brain.totalCommandGrouping.keySet());
        	CommandGroupGroupComboBox.getSelectionModel().selectFirst();
        	//on new selection, clears list, inputs new commands, selects the first one,
        	//then refills the list with the new first one.
        	CommandGroupGroupComboBox.setOnAction(e->
        	{
        		if(CommandGroupGroupComboBox.isFocused()) {

            		ListOfCommands.getItems().clear();
            		//CommandGroupComComboBox.getItems().remove(0);
            		CommandGroupComComboBox.getItems().clear();
                	CommandGroupComComboBox.getItems().addAll(
                        	Brain.totalCommandGrouping.get(CommandGroupGroupComboBox.getSelectionModel()
                        		.getSelectedItem()).keySet());
                	
                	CommandGroupComComboBox.getSelectionModel().selectFirst();
                	
                	String[] newArr = 
                        	Brain.totalCommandGrouping.get(CommandGroupGroupComboBox.getSelectionModel()
                            		.getSelectedItem()).get(CommandGroupComComboBox.
                            				getSelectionModel().getSelectedItem());
                        	if(newArr[newArr.length-1].contains("(")&&newArr[newArr.length-1].
                        			contains(")")) {
                            	ListOfCommands.getItems().addAll(Brain.subArr(newArr, 0, newArr.length-1));
                        	}else {
                            	ListOfCommands.getItems().addAll(Brain.subArr(newArr, 0, newArr.length));
                        	}
            	}
        		}
        			
        			
        			);
        }
        
        if(CommandGroupComComboBox!=null) {
        	CommandGroupComComboBox.getItems().addAll(
        	Brain.totalCommandGrouping.get(CommandGroupGroupComboBox.getSelectionModel()
        		.getSelectedItem()).keySet());
        	CommandGroupComComboBox.getSelectionModel().selectFirst();
        	/*
        	CommandGroupComComboBox.setOnAction(e->
        	{
        		if(CommandGroupComComboBox.isFocused()&&
        				CommandGTextField!=null&&CommandGTextField.getText()!=null
        				&&CommandGTextField.getText().equals("")) {
        			//CommandGroupGroupComboBox.getItems().clear();
        			CommandGTextField.setText(CommandGroupComComboBox.getSelectionModel()
        					.getSelectedItem());
        		}
        	}
        	);
        	*/
        	//list of actual commands
        	CommandGroupComComboBox.setOnAction(e->
        	{
        		if(CommandGroupComComboBox.isFocused()&&
        				CommandGTextField!=null&&CommandGTextField.getText()!=null
        				&&CommandGTextField.getText().equals("")) {
        			//CommandGroupGroupComboBox.getItems().clear();
        			CommandGTextField.setText(CommandGroupComComboBox.getSelectionModel()
        					.getSelectedItem());
        		}
        	
        		
        	});


        	//Brain.indivComGrouping.get(arg1);
        }
        
        
        if(ListOfCommands!=null) {
        	String[] newArr = 
        	Brain.totalCommandGrouping.get(CommandGroupGroupComboBox.getSelectionModel()
            		.getSelectedItem()).get(CommandGroupComComboBox.
            				getSelectionModel().getSelectedItem());
        	if(newArr[newArr.length-1].contains("(")) {
            	ListOfCommands.getItems().addAll(Brain.subArr(newArr, 0, newArr.length-1));
        	}else {
            	ListOfCommands.getItems().addAll(Brain.subArr(newArr, 0, newArr.length));
        	}

        }
	}

}
