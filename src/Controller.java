import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
	private TextField textField;
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
	
	public void openCategoryManagerPanel(ActionEvent event) throws IOException{
        //currentStatusComboBox.getItems().addAll(statusList);
		String[] categoryList;
		int counter=0;
		Parent root = FXMLLoader.load(getClass().getResource("/CategoryManagerPanel.fxml"));
		Stage stage = new Stage();
		Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        //need to fill up content of page
        //need to search phrases txt files for categories
        
        File dir = new File(Brain.directory);
        String[] children = dir.list();
        System.out.println("List of files in phrases folder directory:");
        
        if (children == null) {//shouldn't be possible
           System.out.println("does not exist or is not a directory");
        } else {
           for (int i = 0; i < children.length; i++) {
              String filename = children[i];
              System.out.println(filename);
           }
        }//just got all file lists. just press run and type "control panel", click middle btn
        //idk im too mucked to continue for now we continue this later!
        
        
        
        
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {//this is ran every time a window is opened
        String[] statusList = {"Any","Normal", "WILD!"};
        if(this.textField!=null) {
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
        	@Override
            public void handle(KeyEvent k) {
                if (k.getCode().equals(KeyCode.ENTER)) {
                   	try {
						new Brain(textField.getText());
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}                
                   	}
            }
    });
	}
        if(this.currentStatusComboBox!=null) {
        currentStatusComboBox.getItems().addAll(statusList);
        }
	}//end of initialize


	/**useful links below
	 *https://stackoverflow.com/questions/15452768/consume-javafx-keytyped-event-from-textfield
	 * useful for preventing typing for some reason
	 *guy below also helps us out a lot :)
	 * https://www.youtube.com/watch?v=9XJicRt_FaI&ab_channel=BroCode
	 */
}
