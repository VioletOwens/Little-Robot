package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		URL url = new File("fxmls\\MainWindow.fxml").toURI().toURL();
		Parent root = FXMLLoader.load(url);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Little Robot (Directory fix!)");
		stage.show();
	}

	public static void main(String[] args) throws Exception {
		launch(args);
	}
}