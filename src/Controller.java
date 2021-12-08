import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Controller implements Initializable{
	
	@FXML 
	private TextField mainTextField;
	@FXML
	private Label label;
	@FXML
	private Button clearButton;
	@FXML
	private Button Category_Manager;
	@FXML
	private Button testButton;
	@FXML
	private Button Calculator;
	@FXML
	private ComboBox<String> UICategoryComboBox;
	@FXML
	private ComboBox<String> currentStatusComboBox;
	@FXML
	private ComboBox<String> responseCategoryComboBox;
	@FXML
	private Label listOfCategoryLabel;
	@FXML
	private Label connectedCategoryLabel;
	@FXML
	private Button AddCategoryManagerPanelBtn;
	@FXML
	private Button RemoveCategoryManagerPanelBtn;

	private Stage stage;
	private Scene scene;
	private Parent root;
	/*
	@FXML
	  private void handleKeyPressed(KeyEvent ke) throws FileNotFoundException{
		System.out.println("source of text is:" + ke.getSource());
	    if(ke.getCode()==KeyCode.ENTER) {
	    	System.out.println(textField.getText());
	    	new Brain(textField.getText());
	    }
	  }
	  */

	  

	public void clearAction(ActionEvent event) throws IOException{
		label.setText("");	
	}
	
	public void calculator(ActionEvent event) throws IOException{
		System.out.println("Calculator button pressed");
	}
	
	public void categoryManager(ActionEvent event) throws IOException{
		System.out.println("categorymanager button pressed");
	}
	
	public void testButton(ActionEvent event) throws IOException{
		System.out.println("testButton button pressed");
	}
	
	public void openControlPanel() throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("/ControlPanel.fxml"));
		Stage stage = new Stage();
		Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
	}
	
	public void openCategoryManagerPanel() throws IOException{
        //currentStatusComboBox.getItems().addAll(statusList);
		String[] categoryList = null;
		String[] fileNameList = null;
		String[] keywordPhraseFileNameList;//will contains list of keyword style phrase/filename combo
		int counterOne=0;
		int counterTwo=0;
		String tempString = "";
		String keywordSuperString = "";
		String categorySuperString = "Unknown";//set this equal to what categories we want to skip
		final int probableMaxLinesInFile = 500;
		FileWriter outputToFile = null;
        //need to fill up content of page
        //need to search phrases txt files for categories
        
        File dir = new File(Brain.directory);
        String[] children = dir.list();
        counterOne=0;
        if (children == null) {//shouldn't be possible
           System.out.println("SOMETHING REALLY BAD HAS HAPPENED!!!");
        } else {
        	for(int x=0; x<children.length;x++) {
        		if(!children[x].equals("keywords.txt")&&!children[x].equals("filelist.txt")
        				&&!children[x].equals("categorygrouping.txt")) {
        			counterOne++;//finding all valid files
        		}
        	}
        	fileNameList = new String[counterOne];
           for (int i = 0,y=0; i < children.length; i++) {
        	   if(!children[i].equals("keywords.txt")//
        			   &&!children[i].equals("filelist.txt")
        			   &&!children[i].equals("categorygrouping.txt")) {
                   fileNameList[y] = children[i];
                   y++;
        	   }
           }
        }
        //have all files listed now in list, need to quickly refine list excluding files we
        //want to avoid searching which are keywords.txt and commands.txt
        
        //look into a file, and do two things while looking line to line
        //first, add all lines to a compendium of keywords.txt format for keywords.txt check purposes
        //second, add all unique categories into a string[] list for combobox purposes
        //need to make checker that checks if keywords.txt contains words that the other
        //files didn't possess at all, if words or phrase check this condition, delete them from 
        //keywords.txt
        counterOne=0;
        File file;
        Scanner scanner = null;
		keywordPhraseFileNameList = new String[fileNameList.length*probableMaxLinesInFile];
		categoryList = new String[fileNameList.length*probableMaxLinesInFile];
        for(int x=0; x<fileNameList.length;x++) {
        	if(fileNameList[x]!=null) {//making sure that fileNameList is never null
        	file = new File (Brain.directory+fileNameList[x]);
    		scanner = new Scanner(file);
    		//now search through file in while
    		//need to be able to find exactly amount of lines in all files to set perfect length
    		//for the below array.
    		//in mean time, we assume each file has no more than 500 lines on average.
    		
    		while(scanner.hasNextLine()) {
    			tempString=scanner.nextLine();
    			if(tempString.contains("|")) {//covering edge case of file containing stray str
        			keywordPhraseFileNameList[counterOne]=tempString.substring(0
        					, tempString.indexOf("|"))+"//"+fileNameList[x];
        			counterOne++;
        			if(!categorySuperString.contains(tempString.substring(tempString.indexOf("|")+1
        					, tempString.length()))
        					&&!fileNameList[x].equals("commands.txt")) {
        				//if categorysuperstring doesn't contain, and it isnt a command 
        				//current category, then add it to both
        				categorySuperString = categorySuperString.concat(
        						tempString.substring(tempString.indexOf("|")+1
        	        					, tempString.length()));
            			categoryList[counterTwo] = 
        				tempString.substring(tempString.indexOf("|")+1
            					, tempString.length());
        				counterTwo++;
        			}
    			}//end of if tempstring contains |
    		}//end of while
    		scanner.close();
        	}
        }
        if(scanner!=null) scanner.close();
        keywordPhraseFileNameList = Brain.removeNullInArray(keywordPhraseFileNameList);
        categoryList=Brain.removeNullInArray(categoryList);
        Brain.listOfCategories = categoryList;
        /*//below is great for troubleshooting
        
        System.out.println("printing all of keywordPhraseFileNameList:");
        for(int x=0; x<keywordPhraseFileNameList.length;x++) {
            System.out.println(keywordPhraseFileNameList[x]);
        }
        System.out.println("printing all of categoryList:");
        for(int x=0; x<categoryList.length;x++) {
            System.out.println(categoryList[x]);
        }
        */
		Parent root = FXMLLoader.load(getClass().getResource("/CategoryManagerPanel.fxml"));
		Stage stage = new Stage();
		Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        
        
        //now have valid list of keywords.txt copy (or should be)
        //going to test against keywords.txt, preferring the info in this array
    	
        file = new File (Brain.keywordFileName);
    	scanner.close();
		scanner =  new  Scanner (file);
		tempString="";
		counterOne=0;
        while(scanner.hasNextLine()) {
        	tempString = scanner.nextLine();
        	
        	//can make a superstring for keywords.txt, and test each index of
        	//keywordPhraseFileNameList against the superstring (making sure the index exists in
        	//original file "keywords.txt")
        	//if it isnt found, then add it to the superstring and make note to write this
        	//new line to keywords.txt after full search.
        	if(scanner.hasNextLine()) {
            	keywordSuperString = keywordSuperString.concat(tempString) + "\n";
        	}
        	else {
            	keywordSuperString = keywordSuperString.concat(tempString);
        	}
        	counterOne++;
        }
        scanner.close();
        
        //first checking if keywordPhraseFileNameList contains anything that keywords needs
        //then going to check if keywords contains anything we dont know (cant tell in first
        //check)
        //reusing categoryList here as the extra for things we want to add to keywords.txt
        for(int x=0;x<keywordPhraseFileNameList.length; x++) {
        	if(!keywordSuperString.contains(keywordPhraseFileNameList[x])) {
    			keywordSuperString =keywordSuperString + 
    					"\n" + keywordPhraseFileNameList[x];
        	}
        }        
	      outputToFile = new FileWriter(Brain.keywordFileName);
	      outputToFile.write(keywordSuperString);
	      outputToFile.close();//applying first contribution to keywords.txt
	        file = new File (Brain.keywordFileName);
			scanner =  new  Scanner (file);
			tempString="";
			keywordSuperString="";//remaking file without oddities 
        	while(scanner.hasNextLine()) {
        		tempString = scanner.nextLine();
                for(int x=0; x<keywordPhraseFileNameList.length;x++) {
                	if(keywordPhraseFileNameList[x].equals(tempString)) {
                		if(!keywordSuperString.equals("")) {
                    		keywordSuperString=keywordSuperString+"\n"+
                    				keywordPhraseFileNameList[x];
                    		break;
                		}else {
                			keywordSuperString=keywordPhraseFileNameList[x];
                			break;
                		}
                	}	
                }
        	}
            	outputToFile = new FileWriter(Brain.keywordFileName);
  	      outputToFile.write(keywordSuperString);
  	      outputToFile.close();//applying second contribution to keywords.txt (removing oddities)
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {//this is ran every time a window is opened
        if(this.mainTextField!=null) {
        mainTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
        	@Override
            public void handle(KeyEvent k) {
                if (k.getCode().equals(KeyCode.ENTER)) {
                   	try {
						new Brain(mainTextField.getText());
					} catch (FileNotFoundException e) {
						e.printStackTrace();}}}});}
        if(this.currentStatusComboBox!=null) {
        currentStatusComboBox.getItems().addAll(Brain.statusList);
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
            	listOfCategoryLabel.setText(Brain.toWholeFile(Brain.listOfCategories,0));
        	}
        }
        if(this.connectedCategoryLabel!=null) {
    		File file = new File (Brain.directory + "categorygrouping.txt"); 
   		    FileWriter output = null;
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
            connectedCategoryLabel.setText(wholeFile);
            
            
        }
        if(this.AddCategoryManagerPanelBtn!=null) {
        	
        	AddCategoryManagerPanelBtn.setOnAction(e->{
        		String categoryGroup = "[" + UICategoryComboBox.getValue() + "]" +
		    			"[" + currentStatusComboBox.getValue() + "]" + 
	    				"[" + responseCategoryComboBox.getValue() + "]";
        		if(!connectedCategoryLabel.getText().contains(categoryGroup)) {
                	connectedCategoryLabel.setText(connectedCategoryLabel.getText()+"\n"
                			+ categoryGroup);
    				Brain.appendToFile("categorygrouping.txt",categoryGroup);
        		}
        	});
        	//need to set OnAction
        	//stopped here cant get the add button to work but wont be too hard
        }
        if(this.RemoveCategoryManagerPanelBtn!=null) {
        	RemoveCategoryManagerPanelBtn.setOnAction(e->{
        		String[] tempStringArr = connectedCategoryLabel.getText().split("\n");
        		String currentInput = "[" + UICategoryComboBox.getValue() + "]" +
            			"[" + currentStatusComboBox.getValue() + "]" + 
        				"[" + responseCategoryComboBox.getValue() + "]";
        		for(int x=0; x<tempStringArr.length;x++) {
        			if(tempStringArr[x].equals(currentInput)) {
        				tempStringArr[x]=null;
        				Brain.removeFromFile("categorygrouping.txt",currentInput);
        				break;
        			}
        		}
        		tempStringArr = Brain.removeNullInArray(tempStringArr);
        		currentInput = Brain.toWholeFile(tempStringArr,0);
        		connectedCategoryLabel.setText(currentInput);
        	});
        }
	}//end of initialize


	/**useful links below
	 *https://stackoverflow.com/questions/15452768/consume-javafx-keytyped-event-from-textfield
	 * useful for preventing typing for some reason
	 *guy below also helps us out a lot :)
	 * https://www.youtube.com/watch?v=9XJicRt_FaI&ab_channel=BroCode
	 */
}
