package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.Brain;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

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
	Text hoverInfoText;
	@FXML
	TextField catSearchTextfield;
	@FXML
	TextField catGroupSearchTextfield;
	
	
	
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
           //below is the drag and drop feature
           ListOfCategories.setOnDragDetected(new EventHandler<MouseEvent>() {
               @Override
               public void handle(MouseEvent click) {
                   ListOfCategories.setCursor(Cursor.CLOSED_HAND);
               	}});
           ListOfCategories.setOnMouseReleased(new EventHandler<MouseEvent>() {
               @Override
               public void handle(MouseEvent click) {
            	   try {
                   ListOfCategories.setCursor(Cursor.DEFAULT);
                   if(!click.getPickResult().getIntersectedNode().getId().equals(null)) {
                	   if(click.getPickResult().getIntersectedNode().getId().equals(
                     		  UICategoryComboBox.getId())) {
                    	   UICategoryComboBox.getSelectionModel().select(
                    			   ListOfCategories.getSelectionModel().getSelectedItem());
                		   ListOfCategories.getSelectionModel().clearSelection();
                	   }
                	   if(click.getPickResult().getIntersectedNode().getId().equals(
                			   ResponseCategoryComboBox.getId())) {
                		   ResponseCategoryComboBox.getSelectionModel().select(
                    			   ListOfCategories.getSelectionModel().getSelectedItem());
                		   ListOfCategories.getSelectionModel().clearSelection();
                	   }   
                   }
               }catch(NullPointerException e){
            	//System.out.println("error caught."); 
       		   ListOfCategories.getSelectionModel().clearSelection();
               }
               	}
               
           });
        }
        
        
        if(ListOfConCatGroup!=null) {
        	ListOfConCatGroup.getItems().setAll(Brain.
            		getWholeFileToString("categorygrouping.txt").split("\n"));
        	
        	
        	
        	
        	//the double click feature is below.
        	ListOfConCatGroup.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent click) {
                	if(click.getClickCount() == 2&&!ListOfConCatGroup
                    		.getSelectionModel().getSelectedItem().equals("")) {
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
                    			Brain.tempString.indexOf("[")+1,Brain.tempString.indexOf("]")));}}
            });
        }
        
        if(CatGMAnchorPane!=null){
        	CatGMAnchorPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent click) {
                	CatGMAnchorPane.requestFocus();
                	ListOfConCatGroup.getSelectionModel().clearSelection();
                }
        	});
        }

        if(catSearchTextfield!=null) {
        	
        	
        	catSearchTextfield.setOnKeyTyped(e->{
        		ListView<String> searchCatList;
        		for(int x=0; x<ListOfCategories.getItems().size();x++) {
        			if(ListOfCategories.getItems().get(x).contains(
        					catSearchTextfield.getText())) {
        				//ListOfCategories.getItems().get(x).
        			}
        		}
        		
        	});
        	
        }
        
        
        if(hoverInfoText!=null) {
        	/*
        	 * You can double click a group of connected categories to instantly enter it.
        	 */
        	Tooltip t = new Tooltip("You can double click a group of connected" +"\n"
        			+ "categories to instantly enter it. You can also drag" + "\n"
        			+ "a category from the list of categories to an applicable" + "\n"
        			+ "drop-down to instantly enter it.");
        	
        	t.setShowDuration(new Duration(50000.0));
        	t.setHideOnEscape(true);
    		t.setAutoHide(true);
    		Tooltip.install(hoverInfoText, t);
        	
        }
	}

}
