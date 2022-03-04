package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControlPanelController implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	
	@FXML
	void openCalculatorWindow(ActionEvent event) throws IOException{
		//System.out.println("openCalculatorWindow pressed! (The fxml isn't linked yet.)");
		URL url = new File("fxmls\\Calculator.fxml").toURI().toURL();
		root = FXMLLoader.load(url);
		stage = new Stage();//(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Calculator");
        stage.setResizable(false);
        stage.show();	
	}
	
	@FXML
	void openCaPControlPanel(ActionEvent event) throws IOException {
		//stands for categories and phrases
		URL url = new File("fxmls\\CaPControlPanel.fxml").toURI().toURL();
		root = FXMLLoader.load(url);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Categories and Phrases");
        stage.setResizable(false);
        stage.show();	
	}
	
	@FXML
	void openCaSControlPanel(ActionEvent event) throws IOException {
		//NOT WRITTEN YET!!
		//stands for commands and statuses
		URL url = new File("fxmls\\CaSControlPanel.fxml").toURI().toURL();
		root = FXMLLoader.load(url);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Commands and Statuses");
        stage.setResizable(false);
        stage.show();
	}
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		
	}

}
