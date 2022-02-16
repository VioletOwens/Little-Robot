import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class WindowController implements Initializable{
	//Main program
	@FXML 
	private TextField mainTextField;
	@FXML
	private Label label;
	private HashMap<String, Pane> map = new HashMap<>();
	private Scene main;
	
	public WindowController(Scene scene) {
		main = scene;
	}
	
	public void addScreen(String title, Pane pane) {
		
		map.put(title, pane);
	}
	
	public void removeScreen(String title) {
		map.remove(title);
	}
	public void activate(String title) {
		main.setRoot(map.get(title));
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		
		
	}
	
	
	
}
