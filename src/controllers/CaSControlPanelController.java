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

public class CaSControlPanelController implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	
	
	
	@FXML
	void openControlPanelWindow(ActionEvent event) throws IOException{
		URL url = new File("fxmls\\ControlPanel.fxml").toURI().toURL();
		root = FXMLLoader.load(url);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Control Panel");
        stage.show();
	}
	
	@FXML
	void openCommandManagerPanel(ActionEvent event) throws IOException{
		URL url = new File("fxmls\\CommandManagerPanel.fxml").toURI().toURL();
		root = FXMLLoader.load(url);
		stage = new Stage();//(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Command Manager");
        stage.show();	
        }
	
	@FXML
	void openStatusManagerPanel(ActionEvent event) throws IOException{
		URL url = new File("fxmls\\StatusManagerPanel.fxml").toURI().toURL();
		root = FXMLLoader.load(url);
		stage = new Stage();//(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Status Manager");
        stage.show();		
        }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		
	}

}
