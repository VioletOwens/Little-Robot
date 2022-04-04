package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import application.Brain;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ComGroupMPanelController extends Brain implements Initializable{
	

	@FXML
	AnchorPane ComGAnchorPane;
	@FXML
	ComboBox<String> CommandGroupGroupComboBox;
	@FXML
	ComboBox<String> CurrentCommandGroupComboBox;
	@FXML
	TextField CommandGTextField;
	@FXML
	ListView<String> ListOfTriggerPhrases;
	@FXML
	ComboBox<String> CommandGroupComComboBox;
	@FXML
	Text HoverInfoText;
	@FXML
	Label CurrentCommandLabel;
	
	@FXML
	void addPhrase() {
		/* adds the CommandGTextField.getText() as a phrase if the command isn't temp or empty
		 * to the list at bottom and to the list of trigger phrases. 
		 * it also needs to be added officially to:totalCommandGrouping hashmap
		 * 
		 * 
		 * x<totalCommandGrouping.get("Testing Command Group[0]").
            		get("testingCommand1").length
		 * totalCommandGrouping.get
            			("Testing Command Group[0]").get("testingCommand1")[x].toString()
            			need the group name and command name, get array,
            			and remake the array with the new trigger phrase
            			re-add the command name array combo back to totalCommandGrouping
            			of the same group name (command name obv stays the same too)
		 * 
		 * 
		 * 
		 */
		//CommandGroupGroupComboBox.getSelectionModel().getSelectedItem() is the groupName
		//
		//currentCommandLabel.getText() is the current command name
		System.out.println(
		Brain.totalCommandGrouping.get(CommandGroupGroupComboBox.
    			getSelectionModel().getSelectedItem()).get(CurrentCommandLabel.getText())
		);

	}
	
	@FXML
	void removePhrase() {
		/* removing a trigger phrase from a group as long as it exists, and officially from
		 * totalCommandGrouping
		 * 
		 * 
		 */
	}
	
	
	
	
	@FXML
	void addAsGroup() {
		/* adds the CommandGTextField.getText() to totalCommandGrouping if none else exist of
		 * the same name, but added empty with no actual command within. 
		 * may use tempCommand[0], tempCommand[1], etc.
		 * when this group is selected and no command is noticed (or tempCommand),
		 * prompt the user to add the command in the command requirement manager
		 * 
		 * 		Brain.totalCommandGrouping.get(CommandGroupGroupComboBox.
    			getSelectionModel().getSelectedItem()).
		 */
	}
	

	
	@FXML
	void removeAsGroup() {
		/* removing a group, even if its an empty/tempCommand 
		 * AS LONG AS IT HAS NO TRIGGER PHRASES!!
		 * 
		 * 
		 */
		
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
        	CommandGroupGroupComboBox.setOnAction(e->{
        		if(CommandGroupGroupComboBox.isFocused()) {
        			ListOfTriggerPhrases.getItems().clear();

                	Set<String> str = Brain.totalCommandGrouping.get(CommandGroupGroupComboBox.
                			getSelectionModel().getSelectedItem()).keySet();
                	CurrentCommandLabel.setText((String)str.toArray()[0]);
                	Brain.sopln(str);
                	
                	
                	String[] newArr = 
                        	Brain.totalCommandGrouping.get(CommandGroupGroupComboBox.getSelectionModel()
                            		.getSelectedItem()).get(CurrentCommandLabel.getText());
                        	if(newArr[newArr.length-1].contains("(")&&newArr[newArr.length-1].
                        			contains(")")) {
                        		ListOfTriggerPhrases.getItems().addAll(Brain.subArr(newArr, 0, newArr.length-1));
                        	}else {
                        		ListOfTriggerPhrases.getItems().addAll(Brain.subArr(newArr, 0, newArr.length));
                        	}
            	}
        		}
			);
        }
        
        if(CurrentCommandLabel!=null) {
        	Set<String> str = Brain.totalCommandGrouping.get(CommandGroupGroupComboBox.
        			getSelectionModel().getSelectedItem()).keySet();
        	CurrentCommandLabel.setText((String)str.toArray()[0]);
        	
        	Brain.sopln(str);
        	/*
        	 * when a group is selected with no command (or tempCommand),
        	 * prompt the user to add the command in the command requirement manager
        	 * 
        	 */
        	
        	
        }
        
        
        if(HoverInfoText!=null) {
        	Tooltip t = new Tooltip("Adding, Removing, and Editing of commands"+"\n"
        			+ "is done in the command requirement manager."+"\n"
        			+ "This manager is simply for organizations" + "\n"
        			+ "of trigger phrases and groups.");
        	//this combination of tooltip attributes allows it to operate as expected of it
        	t.setShowDuration(new Duration(50000.0));
        	t.setHideOnEscape(true);
    		t.setAutoHide(true);
    		Tooltip.install(HoverInfoText, t);
        }   

        if(ListOfTriggerPhrases!=null) {
        	
        	String[] newArr = 
        	Brain.totalCommandGrouping.get(CommandGroupGroupComboBox.getSelectionModel()
            		.getSelectedItem()).get(
            				CurrentCommandLabel.getText());
        	if(newArr[newArr.length-1].contains("(")) {
        		ListOfTriggerPhrases.getItems().addAll(Brain.subArr(newArr, 0, newArr.length-1));
        	}else {
        		ListOfTriggerPhrases.getItems().addAll(Brain.subArr(newArr, 0, newArr.length));
        	}
        	
        	
        }
	}
}
