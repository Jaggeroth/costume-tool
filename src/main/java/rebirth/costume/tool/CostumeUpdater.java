package rebirth.costume.tool;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/*
 * 
 */

public class CostumeUpdater extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
		  Parent root = FXMLLoader.load(getClass().getResource("tool.fxml"));
	    Scene scene = new Scene(root);
	    primaryStage.setTitle("Rebirth Costume Updater");
	    primaryStage.setScene(scene);
	    primaryStage.setResizable(false);
	    primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
