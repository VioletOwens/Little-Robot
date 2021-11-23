import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

	public class Main extends Application{
		@Override
		   public void start(Stage stage) throws Exception {
				try {
				URL fxmlResource = getClass().getResource("/fxmls/Main.fxml");
				//URL fxmlURL = getClass().getResource("/Little Robot/fxmls/Main.fxml");
				Parent root = FXMLLoader.load(fxmlResource);
				//Parent root = FXMLLoader.load(getClass().getResource("C:\\Users\\chris\\Desktop\\CS\\CS Software\\Workspace\\Little Robot\\fxmls\\Main.fxml"));
		        Scene scene = new Scene(root);
		        stage.setScene(scene);
		        stage.show();
				
				} catch(Exception e) {
		    	e.printStackTrace();
		    }
		}
	
	

	public static void main(String[] args) throws Exception{
		launch(args);
	}
}
