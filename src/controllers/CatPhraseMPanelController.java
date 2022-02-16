package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Brain;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class CatPhraseMPanelController extends Brain implements Initializable{
	
	@FXML 
	ListView<String> ListOfCategories;
	@FXML 
	ListView<String> ListOfPhrases;//ListAtBottomOfCategoryManager now ListOfPhrases
	@FXML
	TextField CatManagerTextField;//formerly CategoryManagerPanelTextfield
	@FXML 
	Label CurrentCatLabel;//AltCategoryManagerPanelLabelTwo now CurrentCatLabel
	@FXML
	Label PhraseLabel;//CategoryManagerPanelLabelFour now PhraseLabel
	@FXML
	AnchorPane CatPhraseMPAnchorPane;
	@FXML
	Rectangle Rectangle;
	
	@FXML
	void deleteCategory() {
		
	}
	
	@FXML
	void addCategory() {
		
	}
	
	@FXML 
	void undo() {
		
	}
	
	@FXML
	void addPhrase() {
		
	}
	
	@FXML
	void removePhrase() {
		
	}
	
	@FXML
	void openCatGroupEditor() {
		
	}
	
	

	
	
	
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		
		
		
		
		
		
		
		
		if(CurrentCatLabel!=null) {
			CurrentCatLabel.setText("Commendation");
		}
		
        if(ListOfCategories!=null) {
           ListOfCategories.getItems().setAll(Brain.listOfCategories);
            ListOfCategories.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent click) {

                    if (click.getClickCount() == 2&&!ListOfCategories.getSelectionModel()
                            .getSelectedItem().equals("")) {
                    	//finding the phrases around the category 
                    	ListOfPhrases.getItems().clear();
                    	for(int x=0;x<Brain.listOfCategoriesAndTheirPhrases.length;x++) {
                    		if(Brain.listOfCategoriesAndTheirPhrases[x][0].equals(
                    				CurrentCatLabel.getText())) {
                    			//found the correct category and going through list
                    			for(int y=1; y<Brain.listOfCategoriesAndTheirPhrases[x].length;y++) {
                    				ListOfPhrases.getItems().add(
                    						Brain.listOfCategoriesAndTheirPhrases[x][y]);        				
                    			}
                    		}
                    	}	
                    }
                	}
            });
        }
        
        

        if(ListOfPhrases!=null) {
        	//assigning the initial list for ListOfPhrases
        	for(int x=0; x<listOfCategoriesAndTheirPhrases.length;x++) {
        		if(listOfCategoriesAndTheirPhrases[x][0].equals(CurrentCatLabel.getText())) {
        		for(int y=1; y<listOfCategoriesAndTheirPhrases[x].length;y++) {
       				ListOfPhrases.getItems().add(listOfCategoriesAndTheirPhrases[x][y]);
        		}
        		break;
        		}
        	}
        	
    
        	
        	ListOfPhrases.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent click) {
                    //case where we are looking at filenames and selecting where a category goes
                    if(ListOfPhrases.getSelectionModel().getSelectedItem()!=null
                    		&&click.getClickCount() == 2&&!ListOfPhrases
                    		.getSelectionModel().getSelectedItem().equals("")
                    		&&PhraseLabel.getText().equals(
            				"Select a source file for the category from the list below.")) {
                    	
                    	if(ListOfPhrases.getSelectionModel().getSelectedItem()
                    			.equals("Double click here to add it as a new file.")) {
                    		//case of category promoting into a file
                    		File newFile = new File("");
                    		if(CatManagerTextField.getText().contains(".txt")) {
                      	      newFile = new File(Brain.directory + 
                      	    		CatManagerTextField.getText());
                    		}else {
                      	      newFile = new File(Brain.directory +
                      	    		CatManagerTextField.getText() + ".txt");
                    		}
                    		try {
								newFile.createNewFile();
								Brain.inputNewCategoryManagerPanelLastActionInfo(
										"Create",newFile.getPath(),newFile.getName());
								System.out.println("Printing newfiles name:" +
										newFile.getName());
								System.out.println("Printing newfiles path:" + 
								newFile.getPath());
							      CatManagerTextField.setText("");
								//below here resetting the listatbottom
							      PhraseLabel.setText(
                            			"Phrases relating to the category are below.");
							      
							      
                	//removes the items in the list and reverts to the categories
					      ListOfPhrases.getItems().clear();
            	for(int x=0;x<Brain.listOfCategoriesAndTheirPhrases.length;x++) {
            		if(Brain.listOfCategoriesAndTheirPhrases[x][0].equals(
            				CurrentCatLabel.getText())) {
            			//found the correct category and going through list
            			for(int y=1; y<Brain.listOfCategoriesAndTheirPhrases[x].length;y++) {
            				ListOfPhrases.getItems().add(
            						Brain.listOfCategoriesAndTheirPhrases[x][y]);        				
            			}
            		}
            	}	
							} catch (IOException e) {
								e.printStackTrace();
							}
                    		
                    		
                    	}else {
                    		//case of a known file selected and adding category to it
                    		try {
                    			Brain.inputNewCategoryManagerPanelLastActionInfo("Add",
                    					ListOfPhrases.getSelectionModel()
                    					.getSelectedItem(),
                    					"|" + CatManagerTextField.getText());
                    			
                    			Brain.appendToFile(ListOfPhrases
                    					.getSelectionModel().getSelectedItem(),
									"|" + CatManagerTextField.getText());
						      ListOfCategories.getItems().add(
						    		  CatManagerTextField.getText());
						      Brain.updateLists();
						      CatManagerTextField.setText("");
			                	PhraseLabel.setText(
                          			"Phrases relating to the category are below.");
				//below here resetting the listatbottom
              	//removes the items in the list and reverts to the categories
          	ListOfPhrases.getItems().clear();
          	for(int x=0;x<Brain.listOfCategoriesAndTheirPhrases.length;x++) {
          		if(Brain.listOfCategoriesAndTheirPhrases[x][0].equals(
          				CurrentCatLabel.getText())) {
          			//found the correct category and going through list
          			for(int y=1; y<Brain.listOfCategoriesAndTheirPhrases[x].length;y++) {
          				ListOfPhrases.getItems().add(
          						Brain.listOfCategoriesAndTheirPhrases[x][y]);        				
          			}
          			break;
          		}
          	}
							} catch (IOException e) {
								e.printStackTrace();
							}
                    	}
                    		
                    	//else if the adding category part because we don't want the double
                    	//click feature to interfere with operations
                    }else if(click.getClickCount() == 2&&!ListOfPhrases
                    		.getSelectionModel().getSelectedItem().equals("")
                            &&CatManagerTextField.isVisible()) {
                    	//setting the textfield equal to the item that was double clicked
                    	CatManagerTextField.setText(
                    	ListOfPhrases.getSelectionModel().getSelectedItem());
                    }
                	}
            });
        }

        if(CatPhraseMPAnchorPane!=null){
        	CatPhraseMPAnchorPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent click) {
                	CatPhraseMPAnchorPane.requestFocus();
                }
        	});
        }
        
        if(Rectangle!=null) {
        	
        	Rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent click) {
                	Rectangle.setVisible(false);
                	PhraseLabel.setText(
                            			"Phrases relating to the category are below.");
                	//removes the items in the list and reverts to the categories
            	ListOfPhrases.getItems().clear();
            	for(int x=0;x<Brain.listOfCategoriesAndTheirPhrases.length;x++) {
            		if(Brain.listOfCategoriesAndTheirPhrases[x][0].equals(
            				CurrentCatLabel.getText())) {
            			//found the correct category and going through list
            			for(int y=1; y<Brain.listOfCategoriesAndTheirPhrases[x].length;y++) {
            				ListOfPhrases.getItems().add(
            						Brain.listOfCategoriesAndTheirPhrases[x][y]);        				
            			}
            		}
            	}
                }	
            });
        }		
	}

}
