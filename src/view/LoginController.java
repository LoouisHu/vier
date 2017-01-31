package view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import controller.Client;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import model.Player;

public class LoginController implements Initializable {
	
	@FXML private TextField usernameTextField;
	@FXML private TextField hostTextField;
	@FXML private TextField portTextField;
	@FXML private AnchorPane anchorPane;
	
	private double xOffset;
	private double yOffset;
	private Client client;
	
	
	public void loginButtonAction() throws IOException {
		
		String username = usernameTextField.getText();
		String host = hostTextField.getText();
		int port = Integer.parseInt(portTextField.getText());
		
		if (!username.isEmpty() && !host.isEmpty()) {
		
			//TODO Send port and host
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Lobby.fxml"));
			Parent window = (Parent) loader.load();
			
			LobbyController controller = loader.<LobbyController>getController();
			controller.setUsernameLabel(username);
			List<String> ownUsername = new ArrayList<String>();
			controller.setUsersList(ownUsername);
			
			Stage stage = new Stage();
			stage.setTitle("Connect Four 3D - Lobby");
			stage.setScene(new Scene(window));
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					//TODO Send DISCONNECT to server
					Platform.exit();
				}
			});
			stage.show();
			MainLauncher.getPrimaryStage().close();
			
		}
		
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		anchorPane.setOnMousePressed(event -> {
	        xOffset = MainLauncher.getPrimaryStage().getX() - event.getScreenX();
	        yOffset = MainLauncher.getPrimaryStage().getY() - event.getScreenY();
	        anchorPane.setCursor(Cursor.CLOSED_HAND);
	    });

	    anchorPane.setOnMouseDragged(event -> {
	        MainLauncher.getPrimaryStage().setX(event.getScreenX() + xOffset);
	        MainLauncher.getPrimaryStage().setY(event.getScreenY() + yOffset);

	    });

	    anchorPane.setOnMouseReleased(event -> {
	        anchorPane.setCursor(Cursor.DEFAULT);
	    });
	    
	    int numberOfSquares = 30;
	    while (numberOfSquares > 0) {
            generateAnimation();
            numberOfSquares--;
        }
	}
	
	public void generateAnimation() {
     	Random rand = new Random();
        int sizeOfSquare = rand.nextInt(50) + 1;
        int speedOfSqaure = rand.nextInt(10) + 5;
        int startXPoint = rand.nextInt(420);
        int startYPoint = rand.nextInt(350);
        int direction = rand.nextInt(5) + 1;

        KeyValue moveXAxis = null;
        KeyValue moveYAxis = null;
        Rectangle r1 = null;

        switch (direction) {
            case 1 :
                // MOVE LEFT TO RIGHT
                r1 = new Rectangle(0, startYPoint, sizeOfSquare, sizeOfSquare);
                moveXAxis = new KeyValue(r1.xProperty(), 350 -  sizeOfSquare);
                break;
            case 2 :
                // MOVE TOP TO BOTTOM
                r1 = new Rectangle(startXPoint, 0, sizeOfSquare, sizeOfSquare);
                moveYAxis = new KeyValue(r1.yProperty(), 420 - sizeOfSquare);
                break;
            case 3 :
                // MOVE LEFT TO RIGHT, TOP TO BOTTOM
                r1 = new Rectangle(startXPoint, 0, sizeOfSquare, sizeOfSquare);
                moveXAxis = new KeyValue(r1.xProperty(), 350 -  sizeOfSquare);
                moveYAxis = new KeyValue(r1.yProperty(), 420 - sizeOfSquare);
                break;
            case 4 :
                // MOVE BOTTOM TO TOP
                r1 = new Rectangle(startXPoint, 420 - sizeOfSquare, sizeOfSquare, sizeOfSquare);
                moveYAxis = new KeyValue(r1.xProperty(), 0);
                break;
            case 5 :
                // MOVE RIGHT TO LEFT
                r1 = new Rectangle(420 - sizeOfSquare, startYPoint, sizeOfSquare, sizeOfSquare);
                moveXAxis = new KeyValue(r1.xProperty(), 0);
                break;
            case 6 :
                //MOVE RIGHT TO LEFT, BOTTOM TO TOP
                r1 = new Rectangle(startXPoint, 0, sizeOfSquare, sizeOfSquare);
                moveXAxis = new KeyValue(r1.xProperty(), 350 -  sizeOfSquare);
                moveYAxis = new KeyValue(r1.yProperty(), 420 - sizeOfSquare);
                break;

            default:
                System.out.println("default");
        }

        r1.setFill(Color.web("#F89406"));
        r1.setOpacity(0.1);

        KeyFrame keyFrame = new KeyFrame(Duration.millis(speedOfSqaure * 1000),
        		moveXAxis, moveYAxis);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
        anchorPane.getChildren().add(anchorPane.getChildren().size() - 1, r1);
	}

}
