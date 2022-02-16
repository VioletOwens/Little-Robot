package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.Brain;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class CatGMPanelController extends Brain implements Initializable{
	


	@FXML
	AnchorPane CatGMAnchorPane;
	@FXML
	ComboBox<String> UICategoryComboBox;
	@FXML
	ComboBox<String> CurrentStatusComboBox;
	@FXML
	ComboBox<String> ResponseCategoryComboBox;
	@FXML
	ListView<String> ListOfConCatGroup;
	@FXML
	ListView<String> ListOfCategories;
	@FXML
	Rectangle rectangle;
	
	
	
	@FXML
	void addGroup() {
		
	}
	
	@FXML
	void removeGroup() {
		
	}
	
	
	@FXML
	void undo() {
		
	}
	@FXML
	void openPhraseEditor() {
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

        if(this.CurrentStatusComboBox!=null) {
        CurrentStatusComboBox.getItems().addAll(Brain.listOfStatuses);
        }
        if(this.UICategoryComboBox!=null) {
				UICategoryComboBox.getItems().addAll(Brain.listOfCategories);
        }
        if(this.ResponseCategoryComboBox!=null) {
        	ResponseCategoryComboBox.getItems().addAll(Brain.listOfCategories);
        	ResponseCategoryComboBox.getItems().add("Current Status");
        }
      

        if(ListOfCategories!=null) {
           ListOfCategories.getItems().setAll(Brain.listOfCategories);
           /*
            ListOfCategories.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent click) {
                    if (click.getClickCount() == 2&&!ListOfCategories.getSelectionModel()
                            .getSelectedItem().equals("")) {
                    	CategoryManagerPanelTextfield.setText(
                    			ListOfCategories.getSelectionModel().getSelectedItem());
                    }
                	}
            });
            */
        }
        
        
        if(ListOfConCatGroup!=null) {
        	ListOfConCatGroup.getItems().setAll(Brain.
            		getWholeFileToString("categorygrouping.txt").split("\n"));
        	ListOfConCatGroup.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent click) {
                	if(click.getClickCount() == 2&&!ListOfConCatGroup
                    		.getSelectionModel().getSelectedItem().equals("")) {
                	//case of wanting to fulfill in connectedcategorylist double click feature
                    	Brain.tempString = ListOfConCatGroup
                        		.getSelectionModel().getSelectedItem();
                    	
                    	UICategoryComboBox.setValue(Brain.tempString
                        		.substring(1, Brain.tempString.indexOf("]")));
                    	
                    	Brain.tempString = Brain.tempString.substring(
                    			Brain.tempString.indexOf("]")+1, Brain.tempString.length());
                    	
                    	CurrentStatusComboBox.setValue(Brain.tempString.substring(
                    			Brain.tempString.indexOf("[")+1,Brain.tempString.indexOf("]")		)
                    			);
                    	
                    	Brain.tempString = Brain.tempString.substring(
                    			Brain.tempString.indexOf("]")+1, Brain.tempString.length());
                    	
                    	ResponseCategoryComboBox.setValue(Brain.tempString.substring(
                    			Brain.tempString.indexOf("[")+1,Brain.tempString.indexOf("]")		)
                    			);
                    	
                    }
  
                	}
            });
        	
        	
        }
        
        if(CatGMAnchorPane!=null){
        	CatGMAnchorPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent click) {
                	CatGMAnchorPane.requestFocus();
                }
        	});
        }
        
        if(rectangle!=null) {
        	/*
        	rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent click) {
                	rectangle.setVisible(false);
                	CategoryManagerPanelLabelFour.setText(
                            			"Phrases relating to the category are below.");
                	//removes the items in the list and reverts to the categories
            	ListAtBottomOfCategoryManager.getItems().clear();
            	for(int x=0;x<Brain.listOfCategoriesAndTheirPhrases.length;x++) {
            		if(Brain.listOfCategoriesAndTheirPhrases[x][0].equals(
            				AltCategoryManagerPanelLabelTwo.getText())) {
            			//found the correct category and going through list
            			for(int y=1; y<Brain.listOfCategoriesAndTheirPhrases[x].length;y++) {
            				ListAtBottomOfCategoryManager.getItems().add(
            						Brain.listOfCategoriesAndTheirPhrases[x][y]);        				
            			}
            		}
            	}
            	
            	
                }
        	

                	
            });
        	*/
        	
        }		
	}

}
