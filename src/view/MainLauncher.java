package view;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainLauncher extends Application {
	
	private static Stage primaryStage;
	

	@Override
	public void start(Stage firstStage) throws Exception {
		primaryStage = firstStage;
		Parent loginRoot = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene scene = new Scene(loginRoot);
		firstStage.initStyle(StageStyle.UNDECORATED);
		firstStage.setTitle("Connect Four 3D - Login");
		firstStage.setScene(scene);
		firstStage.show();
	}
	
	public static void main(String[] args) {
	    launch(args);
	}
	
	public static Stage getPrimaryStage() {
		return primaryStage;
	}

}
