import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Controller implements Initializable{
	
	//Main program
	@FXML 
	private TextField mainTextField;
	@FXML
	private Label label;
	
	//Control Panel
	@FXML
	private Button Category_Manager;
	@FXML
	private Button customCategoryManagerButton;
	@FXML
	private Button Calculator;
	
	//CategoryManagerPanel
	@FXML
	private ComboBox<String> UICategoryComboBox;
	@FXML
	private ComboBox<String> CurrentStatusComboBox;
	@FXML
	private ComboBox<String> ResponseCategoryComboBox;
	@FXML
	private Button AddCategoryManagerPanelBtn;
	@FXML
	private Button RemoveCategoryManagerPanelBtn;
	@FXML
	private Button CategoryEditorCategoryManagerPanelBtn;
	@FXML
	private Button AddAsCategoryCategoryManagerPanelBtn;
	@FXML
	private Button DeleteCategoryCategoryManagerPanelBtn;	
	@FXML
	private Button UndoCategoryManagerPanelBtn;
	@FXML
	private Label CategoryManagerPanelLabelOne;
	@FXML
	private Label CategoryManagerPanelLabelTwo;
	@FXML
	private Label CategoryManagerPanelLabelThree;
	@FXML
	private Label CategoryManagerPanelLabelFour;
	@FXML
	private Label AltCategoryManagerPanelLabelOne;
	@FXML
	private Label AltCategoryManagerPanelLabelTwo;
	@FXML
	private Label AltCategoryManagerPanelLabelThree;
	@FXML
	private TextField CategoryManagerPanelTextfield;
	@FXML
	private ListView<String> ListOfCategories;
	@FXML
	private ListView<String> ListAtBottomOfCategoryManager;
	@FXML
	private Rectangle clickBlockingCategoryManagerRectangle;
	@FXML
	private AnchorPane CategoryManagerAnchorPane;
	
	//CustomCategoryManagerPanel
	@FXML
	private ComboBox<String> customCategoryManagerPanelStatusComboBox;
	@FXML
	private TextField customCategoryManagerPanelUITextField;
	@FXML
	private TextField customCategoryManagerPanelResponseTextField;
	@FXML
	private TextField customCategoryManagerPanelSearchTextField;
	@FXML
	private Button customCategoryManagerPanelAddBtn;
	@FXML
	private Button customCategoryManagerPanelRemoveBtn;
	@FXML
	private Label customCategoryManagerCustomGroupLabel;
	@FXML
	private ListView<String> customCategoryManagerCustomGroupListView;

	  
	
	public void calculator(ActionEvent event) throws IOException{
		System.out.println("Calculator button pressed");
	}
	
	public void customCategoryManagerPanelAddBtnMethod(ActionEvent event) throws IOException{
		if(customCategoryManagerPanelUITextField.getText().length()!=0&&
				customCategoryManagerPanelResponseTextField.getText().length()!=0
				&&customCategoryManagerPanelStatusComboBox.getValue()!=null){
		String UI = customCategoryManagerPanelUITextField.getText();
		String categoryGroup = "";
		String response = customCategoryManagerPanelResponseTextField.getText();
		if(Brain.countChars(UI, '[')!=Brain.countChars(UI, ']')
				||Brain.countChars(response, '[')!=Brain.countChars(response, ']')) {
			System.out.println("Invalid inputs, try again!");
		}else {
			if(!Brain.isSurroundedByBrackets(UI)) {
				//tests whether the string has brackets in the correct format around it
				UI = "[" + UI + "]";
				customCategoryManagerPanelUITextField.setText(UI);
			}
			if(!Brain.isSurroundedByBrackets(response)) {
				//tests whether the string has brackets in the correct format around it
				response = "[" + response + "]";
				customCategoryManagerPanelResponseTextField.setText(response);
			}
			categoryGroup = UI + 
				"["	+ customCategoryManagerPanelStatusComboBox.getValue() + "]"
			+ response;
    		if(customCategoryManagerCustomGroupListView.getItems().contains(categoryGroup)) {
    			System.out.println("This has already been added, try again!");
    		}else {
    			customCategoryManagerCustomGroupListView.getItems().add(categoryGroup);
				Brain.appendToFile("customcategorygrouping.txt",categoryGroup);
    		}
		}//end of else
		}//end of if(
	}
	
	public void customCategoryManagerPanelRemoveBtnMethod(ActionEvent event) throws IOException{
		if(customCategoryManagerPanelUITextField.getText().length()!=0&&
				customCategoryManagerPanelResponseTextField.getText().length()!=0
				&&customCategoryManagerPanelStatusComboBox.getValue()!=null){
		String UI = customCategoryManagerPanelUITextField.getText();
		String response = customCategoryManagerPanelResponseTextField.getText();
		
		if(Brain.countChars(UI, '[')!=Brain.countChars(UI, ']')
				||Brain.countChars(response, '[')!=Brain.countChars(response, ']')) {
			System.out.println("Invalid inputs, try again!");
		}else {
		if(!Brain.isSurroundedByBrackets(UI)) {
			UI = "[" + UI + "]";
			customCategoryManagerPanelUITextField.setText(UI);
		}
		if(!Brain.isSurroundedByBrackets(response)) {
			response = "[" + response + "]";
			customCategoryManagerPanelResponseTextField.setText(response);
		}
		String categoryGroup = UI + 
				"["	+ customCategoryManagerPanelStatusComboBox.getValue() + "]"
			+ response;	
		customCategoryManagerCustomGroupListView.getItems().remove(categoryGroup);
		Brain.removeFromFile("customcategorygrouping.txt",categoryGroup);
		}
		}
		}

	public void CategoryEditorCategoryManagerPanelBtnMethod(ActionEvent event) throws IOException{
		//set visibility with .isVisible() and .setVisible(true) 
		//AddCategoryManagerPanelBtn.setLayoutX(0);
		if(CategoryManagerPanelLabelOne.isVisible()) {
			CategoryManagerPanelLabelOne.setVisible(false);
			CategoryManagerPanelLabelTwo.setVisible(false);
			CategoryManagerPanelLabelThree.setVisible(false);
			CategoryManagerPanelLabelFour.setText("Phrases relating to the category are below.");
			UICategoryComboBox.setVisible(false);
			CurrentStatusComboBox.setVisible(false);
			ResponseCategoryComboBox.setVisible(false);
			//changing ListAtBottomOfCategoryManager to phrases relating to the category
			ListAtBottomOfCategoryManager.getItems().clear();
	    	for(int x=0;x<Brain.listOfCategoriesAndTheirPhrases.length;x++) {
	    		if(Brain.listOfCategoriesAndTheirPhrases[x][0].equals(
	    				AltCategoryManagerPanelLabelTwo.getText())) {
	    			//changing phrases in list to category
	    			for(int y=1; y<Brain.listOfCategoriesAndTheirPhrases[x].length;y++) {
	    				ListAtBottomOfCategoryManager.getItems().add(
	    						Brain.listOfCategoriesAndTheirPhrases[x][y]);        				
	    			}
	    		}
	    	}
	    	RemoveCategoryManagerPanelBtn.setText("REMOVE PHRASE");
			AddCategoryManagerPanelBtn.setText("ADD PHRASE");
	    	AddAsCategoryCategoryManagerPanelBtn.setVisible(true);
	    	DeleteCategoryCategoryManagerPanelBtn.setVisible(true);
			AltCategoryManagerPanelLabelOne.setVisible(true);
			AltCategoryManagerPanelLabelTwo.setVisible(true);
			//AltCategoryManagerPanelLabelThree.setVisible(true);
			//You can double click a category to select it or a phrase to instantly enter it.
			AltCategoryManagerPanelLabelThree.setText(
				"You can double click a category to select it or a phrase to instantly enter it.");
			CategoryManagerPanelTextfield.setVisible(true);
			CategoryEditorCategoryManagerPanelBtn.setText("CONNECTED CATEGORIES");

		}else {
			CategoryManagerPanelLabelOne.setVisible(true);
			CategoryManagerPanelLabelTwo.setVisible(true);
			CategoryManagerPanelLabelThree.setVisible(true);
			CategoryManagerPanelLabelFour.setText("Groups of connected categories here.");
			UICategoryComboBox.setVisible(true);
			CurrentStatusComboBox.setVisible(true);
			ResponseCategoryComboBox.setVisible(true);
            ListAtBottomOfCategoryManager.getItems().setAll(Brain.
            		getWholeFileToString("categorygrouping.txt").split("\n"));	
			AddCategoryManagerPanelBtn.setText("ADD GROUP");
	    	RemoveCategoryManagerPanelBtn.setText("REMOVE GROUP");

			
            AddAsCategoryCategoryManagerPanelBtn.setVisible(false);
            DeleteCategoryCategoryManagerPanelBtn.setVisible(false);
			AltCategoryManagerPanelLabelOne.setVisible(false);
			AltCategoryManagerPanelLabelTwo.setVisible(false);
			//AltCategoryManagerPanelLabelThree.setVisible(false);
			AltCategoryManagerPanelLabelThree.setText(
					"You can double click a group of connected categories to instantly enter it.");
			CategoryManagerPanelTextfield.setVisible(false);
			CategoryEditorCategoryManagerPanelBtn.setText("CATEGORY EDITOR");
		}
	}
	
	public void AddCategoryManagerPanelBtnMethod(ActionEvent event) throws IOException{
		String file = "";
		String category = "";
		//String tempString = "";
		if(UICategoryComboBox.getValue()!=null&&CurrentStatusComboBox.getValue()!=null
				&&ResponseCategoryComboBox.getValue()!=null
				&&UICategoryComboBox.isVisible()&&ResponseCategoryComboBox.isVisible()) {
		String categoryGroup = "[" + UICategoryComboBox.getValue() + "]" +
    			"[" + CurrentStatusComboBox.getValue() + "]" + 
				"[" + ResponseCategoryComboBox.getValue() + "]";
		if(!ListAtBottomOfCategoryManager.getItems().contains(categoryGroup)) {
			ListAtBottomOfCategoryManager.getItems().add(categoryGroup);
			Brain.appendToFile("categorygrouping.txt",categoryGroup);
		}
		}
		if(CategoryManagerPanelTextfield.isVisible()) {
			//ensuring the textfield is a valid phrase
		if(!CategoryManagerPanelTextfield.getText()
				.equals("")&&CategoryManagerPanelTextfield.getText().length()>1
				&&Brain.isStringAvailable(CategoryManagerPanelTextfield.getText())) {
			ListAtBottomOfCategoryManager.getItems().add(
					CategoryManagerPanelTextfield.getText());
			//finding file name of file containing correct category
			for(int x=0;x<Brain.listOfCategoryFileNameCombo.length;x++) {
				file=Brain.listOfCategoryFileNameCombo[x].substring(
						Brain.listOfCategoryFileNameCombo[x].indexOf("|")+1, 
						Brain.listOfCategoryFileNameCombo[x].length());
				category = Brain.listOfCategoryFileNameCombo[x].substring(0,
						Brain.listOfCategoryFileNameCombo[x].indexOf("|"));
				if(category.equals(AltCategoryManagerPanelLabelTwo.getText())){
					//file contains category,now checking for if category needs an empty spot 
					//filled or if there's already a phrase under it.
					if(Brain.isStringInFile(file,"|"+AltCategoryManagerPanelLabelTwo.getText())) {
						//if filling empty category phrase spot
						Brain.removeFromFile(file, "|"+AltCategoryManagerPanelLabelTwo.getText());
						Brain.appendToFile(file, CategoryManagerPanelTextfield.getText() + "|" +
							AltCategoryManagerPanelLabelTwo.getText());
					}else {
						//if there are no empty category phrase spots
						Brain.appendToFile(file, CategoryManagerPanelTextfield.getText() + "|" +
								AltCategoryManagerPanelLabelTwo.getText());
					}
					Brain.updateLists();
					break;
				}
			}			
		}else {
			System.out.println("Invalid input for phrase!");			
		}
		}
		
		System.out.println("AddCategoryManagerPanelBtn pressed");
	}
	
	public void RemoveCategoryManagerPanelBtnMethod(ActionEvent event) throws IOException{
		if(UICategoryComboBox.getValue()!=null&&CurrentStatusComboBox.getValue()!=null
				&&ResponseCategoryComboBox.getValue()!=null
				&&UICategoryComboBox.isVisible()&&ResponseCategoryComboBox.isVisible()) {
		String categoryGroup = "[" + UICategoryComboBox.getValue() + "]" +
    			"[" + CurrentStatusComboBox.getValue() + "]" + 
				"[" + ResponseCategoryComboBox.getValue() + "]";
		if(ListAtBottomOfCategoryManager.getItems().contains(categoryGroup)) {
			ListAtBottomOfCategoryManager.getItems().remove(categoryGroup);
    		Brain.removeFromFile("categorygrouping.txt",categoryGroup);
		}
		}else if(UICategoryComboBox.isVisible()&&(
				UICategoryComboBox.getValue()==null
				||CurrentStatusComboBox.getValue()==null
				||ResponseCategoryComboBox.getValue()==null)){
			System.out.println("Invalid input!");
		}
		//things to do if removing a phrase:
		//
		if(CategoryManagerPanelTextfield!=null&&CategoryManagerPanelTextfield.getText().length()
				>1&&ListAtBottomOfCategoryManager.getItems().contains(
						CategoryManagerPanelTextfield.getText())){
			
			String file = "";
			String category = "";
			
			for(int x=0;x<Brain.listOfCategoryFileNameCombo.length;x++) {
				file=Brain.listOfCategoryFileNameCombo[x].substring(
						Brain.listOfCategoryFileNameCombo[x].indexOf("|")+1, 
						Brain.listOfCategoryFileNameCombo[x].length());
				category = Brain.listOfCategoryFileNameCombo[x].substring(0,
						Brain.listOfCategoryFileNameCombo[x].indexOf("|"));
				if(category.equals(AltCategoryManagerPanelLabelTwo.getText())) {
					if(ListAtBottomOfCategoryManager.getItems().size()>1) {
						//if the file has more than 1 phrase under that category
						Brain.removeFromFile(file, 
								CategoryManagerPanelTextfield.getText()+ "|" + category);
					}else{
						//if file has only 1 phrase under that category
						Brain.removeFromFile(file, 
								CategoryManagerPanelTextfield.getText()+ "|" + category);
						Brain.appendToFile(file, "|" + category);
					}
					ListAtBottomOfCategoryManager.getItems().remove(
							CategoryManagerPanelTextfield.getText());
					Brain.updateLists();
					break;
				}
				
			}
			
			
		}else if(CategoryManagerPanelTextfield.isVisible()&&
				(CategoryManagerPanelTextfield.getText().length()<1||
						!ListAtBottomOfCategoryManager.getItems().contains(
						CategoryManagerPanelTextfield.getText()))){
			System.out.println("Invalid input!");
		}
		
		
	}
	
	public void DeleteCategoryCategoryManagerPanelBtnMethod(ActionEvent event) throws IOException{
		if(CategoryManagerPanelLabelFour.getText().equals(
				"Phrases relating to the category are below.")&&
				ListAtBottomOfCategoryManager.getItems().isEmpty()) {
			String category = "";
			String newCategory = "";
			String file = "";
			int categoryIndex = -1;
			for(int x=0; x<Brain.listOfCategoryFileNameCombo.length;x++) {
				category = Brain.listOfCategoryFileNameCombo[x].substring(0, 
						Brain.listOfCategoryFileNameCombo[x].indexOf("|"));
				file = Brain.listOfCategoryFileNameCombo[x].substring(
						Brain.listOfCategoryFileNameCombo[x].indexOf("|")+1,
						Brain.listOfCategoryFileNameCombo[x].length());
				if(AltCategoryManagerPanelLabelTwo.getText().equals(category)) {
					for(int y=0; y<ListOfCategories.getItems().size();y++) {
						if(ListOfCategories.getItems().get(y)==
								AltCategoryManagerPanelLabelTwo.getText()) {
							categoryIndex = y;
							break;
						}
					}
					
					ListOfCategories.getItems().remove(AltCategoryManagerPanelLabelTwo.getText());
					Brain.removeFromFile(file,"|"+category);
					Brain.updateLists();
					//current category label needs to be changed and
					//list at bottom needs to be updated with new label chosen
					newCategory = ListOfCategories.getItems().get(categoryIndex-1);
					AltCategoryManagerPanelLabelTwo.setText(newCategory);
					for(int y=0; y<Brain.listOfCategoriesAndTheirPhrases.length;y++) {
						if(Brain.listOfCategoriesAndTheirPhrases[y][0].equals(newCategory)){
							//found correct new category so want to fill list with its phrases
							for(int z=1; z<Brain.listOfCategoriesAndTheirPhrases[y].length;z++) {
								ListAtBottomOfCategoryManager.getItems().add(
										Brain.listOfCategoriesAndTheirPhrases[y][z]);
							}
							break;
						}
					}
					break;
				}
				}
		}else {
			System.out.println("The phrase list must be empty!");
		}
	}
	
	public void AddAsCategoryCategoryManagerPanelBtnMethod(ActionEvent event) throws IOException{
		//must not be a category, a phrase, empty, or have a length 2 and below.
		//or previous filename
		if(!CategoryManagerPanelTextfield.getText().equals("")
				&&Brain.isStringAvailable(CategoryManagerPanelTextfield.getText())
				&&CategoryManagerPanelTextfield.getText().length()>2) {
			System.out.println("this should be applicable categories");

		//this method will also cover the swap of ListAtBottomOfCategoryManager
		CategoryManagerPanelLabelFour.setText(
				"Select a source file for the category from the list below.");
		clickBlockingCategoryManagerRectangle.setVisible(true);
		ListAtBottomOfCategoryManager.getItems().clear();
		for(int x=0; x<Brain.filesToIncludeInSearches.length;x++) {
			if(x==0) {
				ListAtBottomOfCategoryManager.getItems().add(
						"Double click here to add it as a new file instead.");
				}
			ListAtBottomOfCategoryManager.getItems().add(
					Brain.filesToIncludeInSearches[x]);   
		}
		
		
		}else {
			System.out.println("Invalid input!");
		}
		
	}
	
	public void UndoCategoryManagerPanelBtnMethod(ActionEvent event) throws IOException{
		
		System.out.println("Undo button pressed");
		
		
	}
	
	
	public void openControlPanel() throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("/ControlPanel.fxml"));
		Stage stage = new Stage();
		Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Control Panel");
        stage.show();
	}
	
	public void openCategoryManagerPanel() throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("/CategoryManagerPanel.fxml"));
		Stage stage = new Stage();
		Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Category Manager Panel");
        stage.show();
	}

	public void openCustomCategoryManagerPanel() throws IOException{		
		Parent root = FXMLLoader.load(getClass().getResource("/CustomCategoryManagerPanel.fxml"));
		Stage stage = new Stage();
		Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Custom Category Manager Panel");
        stage.show();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//this is ran every time a window is opened
        if(this.mainTextField!=null) {
        mainTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
        	@Override
            public void handle(KeyEvent k) {
                if (k.getCode().equals(KeyCode.ENTER)) {
                   	try {
						new Brain(mainTextField.getText());
					} catch (IOException e) {
						e.printStackTrace();}}}});
        }
        //end of main console panel
        //beginning of categoryManagerPanel
        //beginning of alt categoryManagerPanel
        if(AltCategoryManagerPanelLabelTwo!=null) {
        	//this is the alt category label
        	AltCategoryManagerPanelLabelTwo.setText("Commendation");
        }
        
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
      
        if(ListAtBottomOfCategoryManager!=null) {
            ListAtBottomOfCategoryManager.getItems().setAll(Brain.
            		getWholeFileToString("categorygrouping.txt").split("\n"));
        }
        if(ListOfCategories!=null) {
           ListOfCategories.getItems().setAll(Brain.listOfCategories);
            ListOfCategories.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent click) {

                    if (click.getClickCount() == 2&&!ListOfCategories.getSelectionModel()
                            .getSelectedItem().equals("")
                            &&CategoryManagerPanelTextfield.isVisible()) {
                    	AltCategoryManagerPanelLabelTwo.setText(
                    	ListOfCategories.getSelectionModel().getSelectedItem());
                    	//finding the phrases around the category 
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
                    
                	}
            });
        }
        
        
        if(ListAtBottomOfCategoryManager!=null) {
        	ListAtBottomOfCategoryManager.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent click) {
                    //case where we are looking at filenames and selecting where a category goes
                    if(ListAtBottomOfCategoryManager.getSelectionModel().getSelectedItem()!=null
                    		&&click.getClickCount() == 2&&!ListAtBottomOfCategoryManager
                    		.getSelectionModel().getSelectedItem().equals("")
                    		&&CategoryManagerPanelLabelFour.getText().equals(
            				"Select a source file for the category from the list below.")) {
                    	
                    	if(ListAtBottomOfCategoryManager.getSelectionModel().getSelectedItem()
                    			.equals("Double click here to add it as a new file.")) {
                    		//case of category promoting into a file
                    		File newFile = new File("");
                    		if(CategoryManagerPanelTextfield.getText().contains(".txt")) {
                      	      newFile = new File(Brain.directory + 
                      	    		CategoryManagerPanelTextfield.getText());
                    		}else {
                      	      newFile = new File(Brain.directory +
                      	    		CategoryManagerPanelTextfield.getText() + ".txt");
                    		}
                    		try {
								newFile.createNewFile();
								
								System.out.println("Printing newfiles name:" +
										newFile.getName());
								System.out.println("Printing newfiles path:" + 
								newFile.getPath());
							      CategoryManagerPanelTextfield.setText("");
								//below here resetting the listatbottom
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
							} catch (IOException e) {
								e.printStackTrace();
							}
                    		
                    		
                    	}else {
                    		//case of a known file selected and adding category to it
                    		try {
                    			Brain.appendToFile(ListAtBottomOfCategoryManager
                    					.getSelectionModel().getSelectedItem(),
									"|" + CategoryManagerPanelTextfield.getText());
						      ListOfCategories.getItems().add(
						    		  CategoryManagerPanelTextfield.getText());
						      Brain.updateLists();
						      CategoryManagerPanelTextfield.setText("");
			                	CategoryManagerPanelLabelFour.setText(
                          			"Phrases relating to the category are below.");
				//below here resetting the listatbottom
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
          			break;
          		}
          	}
							} catch (IOException e) {
								e.printStackTrace();
							}
                    	}
                    		
                    	//else if the adding category part because we don't want the double
                    	//click feature to interfere with operations
                    }else if(click.getClickCount() == 2&&!ListAtBottomOfCategoryManager
                    		.getSelectionModel().getSelectedItem().equals("")
                            &&CategoryManagerPanelTextfield.isVisible()) {
                    	//setting the textfield equal to the item that was double clicked
                    	CategoryManagerPanelTextfield.setText(
                    	ListAtBottomOfCategoryManager.getSelectionModel().getSelectedItem());
                    }else if(click.getClickCount() == 2&&!ListAtBottomOfCategoryManager
                    		.getSelectionModel().getSelectedItem().equals("")
                    		&&UICategoryComboBox.isVisible()) {
                	//case of wanting to fulfill in connectedcategorylist double click feature
                    	Brain.tempString = ListAtBottomOfCategoryManager
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
        
        if(CategoryManagerAnchorPane!=null){
        	CategoryManagerAnchorPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent click) {
                	//need to also fix separators in category manager not allowing focus
                	//to pass through them
                	CategoryManagerAnchorPane.requestFocus();
                }
        	});
        	//CategoryManagerAnchorPane.setFocusTraversable(true);
        }
        
        if(clickBlockingCategoryManagerRectangle!=null) {
        	clickBlockingCategoryManagerRectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent click) {
                	//CategoryManagerPanelLabelFour.setText();
                	clickBlockingCategoryManagerRectangle.setVisible(false);
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
        }
        
        if(CategoryManagerPanelTextfield!=null) {
        	
        	//CategoryManagerPanelTextfield.
        }
        
        //end of categoryManagerPanel
        //beginning of CustomCategoryManagerPanel
        if(customCategoryManagerPanelStatusComboBox!=null) {
        	customCategoryManagerPanelStatusComboBox.getItems().addAll(Brain.listOfStatuses);
        }
        if(customCategoryManagerCustomGroupLabel!=null) {
    		File file = new File (Brain.directory + "customcategorygrouping.txt"); 
        	Scanner scanner = null;
    		try {
    			scanner = new  Scanner (file);
    		} catch (FileNotFoundException e) {
    			e.printStackTrace();
    		}
    		String tempString = "";
    		String wholeFile = "";
            while(scanner.hasNextLine()) {
            	tempString = scanner.nextLine();
            	if(wholeFile.equals("")) {
            		wholeFile = tempString;
            	}else {
                	wholeFile = wholeFile + "\n" + tempString;
            	}
            }
            customCategoryManagerCustomGroupLabel.setText(wholeFile);
        }
        if(customCategoryManagerCustomGroupListView!=null) {
            customCategoryManagerCustomGroupListView.getItems().setAll(
            		Brain.getWholeFileToString("customcategorygrouping.txt").split("\n"));       	
        }
        
        if(customCategoryManagerPanelSearchTextField!=null) {
        	customCategoryManagerPanelSearchTextField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            	@Override
        		public void handle(KeyEvent k) {
                if(customCategoryManagerPanelSearchTextField.getText().equals("")) {
                    customCategoryManagerCustomGroupListView.getItems().setAll(
                    		Brain.getWholeFileToString("customcategorygrouping.txt")
                    		.split("\n"));
                }else if(k.getCode().isDigitKey()||k.getCode().isLetterKey()||
                		k.getCode().isWhitespaceKey()){  
                        customCategoryManagerCustomGroupListView.getItems().setAll(
                        		Brain.sortCustomGroupList(
                        				customCategoryManagerPanelSearchTextField.getText(),
        								Brain.getWholeFileToString("customcategorygrouping.txt")
        	                    		.split("\n")));
                    }
                
                }
            	});
        }
        //end of CustomCategoryManagerPanel
        
	}//end of initialize


	
	/**useful links below
	 *https://stackoverflow.com/questions/15452768/consume-javafx-keytyped-event-from-textfield
	 * useful for preventing typing for some reason
	 *guy below also helps us out a lot :)
	 * https://www.youtube.com/watch?v=9XJicRt_FaI&ab_channel=BroCode
	 * also able to select item selected in list with this: 
	 * listView.getSelectionModel().getSelectedItem();
	 */
}
