import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Controller {
	
	@FXML 
	private TextField textField;
	@FXML
	private Label label;
	@FXML
	private Button clearButton;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	 @FXML
	  private void handleKeyPressed(KeyEvent ke) throws FileNotFoundException{
//	    System.out.println("The text in the textBox is:" + textField.getText()); //good for debugging
	    if(ke.getCode()==KeyCode.ENTER) {
	    	System.out.println(textField.getText());
	    	new Brain(textField.getText());
	    }
	  }

	
	
	
	public void clearAction(ActionEvent event) throws IOException{
		label.setText("");	
	}
	
	
	
	
	
	/**useful links below
	 *https://stackoverflow.com/questions/15452768/consume-javafx-keytyped-event-from-textfield
	 * useful for preventing typing for some reason
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	
}
