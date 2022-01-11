import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	private ComboBox<String> currentStatusComboBox;
	@FXML
	private ComboBox<String> responseCategoryComboBox;
	@FXML
	private Label listOfCategoryLabel;
	@FXML
	private Button AddCategoryManagerPanelBtn;
	@FXML
	private Button RemoveCategoryManagerPanelBtn;
	@FXML
	private Button CategoryModeManagerPanelBtn;
	@FXML
	private ListView<String> connectedCategoryConnectedList;
	@FXML
	private ListView<String> CategoryListCategoryManager;
	
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
	
	public void categoryManager(ActionEvent event) throws IOException{
		System.out.println("categorymanager button pressed");
	}
	
	public void testButton(ActionEvent event) throws IOException{
		System.out.println("testButton button pressed");
	}
	
	public void customCategoryManagerPanelAddBtnMethod(ActionEvent event) throws IOException{
		if(customCategoryManagerPanelUITextField.getText().length()!=0&&
				customCategoryManagerPanelResponseTextField.getText().length()!=0
				&&customCategoryManagerPanelStatusComboBox.getValue()!=null){
		String UI = customCategoryManagerPanelUITextField.getText();
		String categoryGroup = "";
		String response = customCategoryManagerPanelResponseTextField.getText();
		if(Brain.count(UI, '[')!=Brain.count(UI, ']')
				||Brain.count(response, '[')!=Brain.count(response, ']')) {
			System.out.println("Invalid inputs, try again!");
		}else {
			if(!Brain.flexibleBracketTest(UI)) {
				//tests whether the string has brackets in the correct format around it
				UI = "[" + UI + "]";
				customCategoryManagerPanelUITextField.setText(UI);
			}
			if(!Brain.flexibleBracketTest(response)) {
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
		if(!Brain.flexibleBracketTest(UI)) {
			UI = "[" + UI + "]";
			customCategoryManagerPanelUITextField.setText(UI);
		}
		if(!Brain.flexibleBracketTest(response)) {
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

	public void openControlPanel() throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("/ControlPanel.fxml"));
		Stage stage = new Stage();
		Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
	}
	
	public void openCategoryManagerPanel() throws IOException{

		Parent root = FXMLLoader.load(getClass().getResource("/CategoryManagerPanel.fxml"));
		Stage stage = new Stage();
		Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
	}

	public void openCustomCategoryManagerPanel() throws IOException{
		System.out.println("openCustomCategoryManagerPanel is called");
		
		Parent root = FXMLLoader.load(getClass().getResource("/CustomCategoryManagerPanel.fxml"));
		Stage stage = new Stage();
		Scene scene = new Scene(root);
        stage.setScene(scene);
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
					} catch (FileNotFoundException e) {
						e.printStackTrace();}}}});}
        //end of main console panel
        //beginning of categoryManagerPanel
        if(this.currentStatusComboBox!=null) {
        currentStatusComboBox.getItems().addAll(Brain.listOfStatuses);
        }
        if(this.UICategoryComboBox!=null) {
				UICategoryComboBox.getItems().addAll(Brain.listOfCategories);
        }
        if(this.responseCategoryComboBox!=null) {
        	responseCategoryComboBox.getItems().addAll(Brain.listOfCategories);
        	responseCategoryComboBox.getItems().add("Current Status");
        }
        if(this.listOfCategoryLabel!=null) {
        	if(!Brain.listOfCategories.equals(new String[]{""})) {
            	listOfCategoryLabel.setText(Brain.stringArrToString(Brain.listOfCategories,0));
        	}
        }
        
        if(this.AddCategoryManagerPanelBtn!=null) {
        	AddCategoryManagerPanelBtn.setOnAction(e->{
        		if(UICategoryComboBox.getValue()!=null&&currentStatusComboBox.getValue()!=null
        				&&responseCategoryComboBox.getValue()!=null) {
        		String categoryGroup = "[" + UICategoryComboBox.getValue() + "]" +
		    			"[" + currentStatusComboBox.getValue() + "]" + 
	    				"[" + responseCategoryComboBox.getValue() + "]";
        		if(!connectedCategoryConnectedList.getItems().contains(categoryGroup)) {
        			connectedCategoryConnectedList.getItems().add(categoryGroup);
    				Brain.appendToFile("categorygrouping.txt",categoryGroup);
        		}
        		}
    			System.out.println("AddCategoryManagerPanelBtn pressed");
        	});
        }
        if(this.RemoveCategoryManagerPanelBtn!=null) {
        	RemoveCategoryManagerPanelBtn.setOnAction(e->{
        		String categoryGroup = "[" + UICategoryComboBox.getValue() + "]" +
		    			"[" + currentStatusComboBox.getValue() + "]" + 
	    				"[" + responseCategoryComboBox.getValue() + "]";
        		if(connectedCategoryConnectedList.getItems().contains(categoryGroup)) {
            		connectedCategoryConnectedList.getItems().remove(categoryGroup);
            		Brain.removeFromFile("categorygrouping.txt",categoryGroup);
        		}
        	});
        }
        if(CategoryModeManagerPanelBtn!=null) {
        	CategoryModeManagerPanelBtn.setOnAction(e-> {
        			if(AddCategoryManagerPanelBtn.isVisible()) {
        				AddCategoryManagerPanelBtn.setVisible(false);
        			}else {
        				AddCategoryManagerPanelBtn.setVisible(true);
        			}
        			System.out.println("CategoryModeManagerPanelBtn pressed");
        	});
        }
        if(connectedCategoryConnectedList!=null) {
        	String wholeFileString =
            		Brain.getWholeFileToString("categorygrouping.txt");
            ObservableList<String> listOfString = FXCollections.
            		observableArrayList(wholeFileString.split("\n"));
            connectedCategoryConnectedList.setItems(listOfString); 
        }
        if(CategoryListCategoryManager!=null) {
            ObservableList<String> listOfCategories = FXCollections.observableArrayList(
            		Brain.listOfCategories);
        	CategoryListCategoryManager.setItems(listOfCategories);
        	
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
        	String wholeFileString =
            		Brain.getWholeFileToString("customcategorygrouping.txt");
            ObservableList<String> listOfString = FXCollections.
            		observableArrayList(wholeFileString.split("\n"));
            customCategoryManagerCustomGroupListView.setItems(listOfString);       	
        }
        
        if(customCategoryManagerPanelSearchTextField!=null) {
        	customCategoryManagerPanelSearchTextField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            	@Override
        		public void handle(KeyEvent k) {
                	String wholeFileString =
            		Brain.getWholeFileToString("customcategorygrouping.txt");
                if(customCategoryManagerPanelSearchTextField.getText().equals("")) {
                	ObservableList<String> unsortedList = FXCollections.observableArrayList(
                			wholeFileString.split("\n"));
                    customCategoryManagerCustomGroupListView.setItems(unsortedList); 
                }else if(k.getCode().isDigitKey()||k.getCode().isLetterKey()||
                		k.getCode().isWhitespaceKey()){
                        ObservableList<String> sortedList = FXCollections.observableArrayList(
    						Brain.sortCustomGroupList(customCategoryManagerPanelSearchTextField
        								.getText(),wholeFileString.split("\n")));
                        customCategoryManagerCustomGroupListView.setItems(sortedList);  
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
	 */
}
