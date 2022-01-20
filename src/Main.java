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
				URL fxmlResource = getClass().getResource("/Main.fxml");//formerly "/Main.fxml"
				Parent root = FXMLLoader.load(fxmlResource);
		        Scene scene = new Scene(root);
		        stage.setScene(scene);
		        stage.setTitle("Little Robot");
		        stage.show();		        
				} catch(Exception e) {
		    	e.printStackTrace();
		    }
		}
	
	

	public static void main(String[] args) throws Exception{
		launch(args);
	}
}
	
	//how do i open multiple windows at once/close one and open another (control panel keyword)
