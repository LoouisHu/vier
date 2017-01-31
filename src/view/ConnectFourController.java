package view;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Board;
import model.Mark;
import model.Position;

public class ConnectFourController implements Initializable {

	@FXML private TextField messageBox;
	@FXML private ListView<HBox> chatView;
	@FXML private Label username;
	@FXML private Label currentFloor;
	@FXML private Rectangle square11;
	@FXML private Rectangle square21;
	@FXML private Rectangle square31;
	@FXML private Rectangle square41;
	@FXML private Rectangle square12;
	@FXML private Rectangle square22;
	@FXML private Rectangle square32;
	@FXML private Rectangle square42;
	@FXML private Rectangle square13;
	@FXML private Rectangle square23;
	@FXML private Rectangle square33;
	@FXML private Rectangle square43;
	@FXML private Rectangle square14;
	@FXML private Rectangle square24;
	@FXML private Rectangle square34;
	@FXML private Rectangle square44;
	
	private Board board;
	
	public void updateFloor(Board aBoard) {
		Map<Position, Mark> boardFloor = aBoard.getFloor(Integer.parseInt(currentFloor.getText()));
		for (Position p : boardFloor.keySet()) {
			int x = p.getX();
			int y = p.getY();
			Mark mark = boardFloor.get(p);
			if (x == 1 && y == 1) {
				square11 = chooseColour(square11, mark);
			} else if (x == 2 && y == 1) {
				square21 = chooseColour(square21, mark);
			} else if (x == 3 && y == 1) {
				square31 = chooseColour(square31, mark);
			} else if (x == 4 && y == 1) {
				square41 = chooseColour(square41, mark);
			} else if (x == 1 && y == 2) {
				square12 = chooseColour(square41, mark);
			} else if (x == 2 && y == 2) {
				square22 = chooseColour(square41, mark);
			} else if (x == 3 && y == 2) {
				square32 = chooseColour(square41, mark);
			} else if (x == 4 && y == 2) {
				square42 = chooseColour(square41, mark);
			} else if (x == 1 && y == 3) {
				square13 = chooseColour(square41, mark);
			} else if (x == 2 && y == 3) {
				square23 = chooseColour(square41, mark);
			} else if (x == 3 && y == 3) {
				square33 = chooseColour(square41, mark);
			} else if (x == 4 && y == 3) {
				square43 = chooseColour(square41, mark);
			} else if (x == 1 && y == 4) {
				square14 = chooseColour(square41, mark);
			} else if (x == 1 && y == 4) {
				square24 = chooseColour(square41, mark);
			} else if (x == 1 && y == 4) {
				square34 = chooseColour(square41, mark);
			} else if (x == 1 && y == 4) {
				square44 = chooseColour(square41, mark);
			}
		}
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}
	
	private Rectangle chooseColour(Rectangle r, Mark m) {
		if (m.getChar() == 'a') {
			r.setFill(Color.RED);
		} else if (m.getChar() == 'b') {
			r.setFill(Color.BLUE);
		} else {
			r.setFill(Color.WHITE);
		}
		return r;
	}
	
	@FXML
	public void actionButtonUp() {
		int floor = Integer.parseInt(currentFloor.getText());
		if (floor < 4) {
			floor++;	
			currentFloor.setText(Integer.toString(floor));
			updateFloor(board);
		}
		
	}
	
	@FXML 
	public void actionButtonDown() {
		int floor = Integer.parseInt(currentFloor.getText());
		if (floor > 1) {
			floor--;
			currentFloor.setText(Integer.toString(floor));
			updateFloor(board);
		}
	}
	
	public synchronized void addToChatView(String playerName, String msg) {
		Task<HBox> otherMessages = new Task<HBox>() {

			@Override
			protected HBox call() throws Exception {
				Label l = new Label();

				//Put private conversation here? CHAT extension.
				l.setText(playerName + ": " + msg);
				l.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

				
				HBox x = new HBox();
				x.getChildren().add(l);
				return x;
			}
			
		};
		
		otherMessages.setOnSucceeded(event -> {
			chatView.getItems().add(otherMessages.getValue());
		});
		
		Task<HBox> ownMessages = new Task<HBox>() {

			@Override
			protected HBox call() throws Exception {
				Label l = new Label();
				l.setText(username.getText() + ": " + msg);
				HBox x = new HBox();
				x.getChildren().add(l);
				return x;
			} 
			
		};
		ownMessages.setOnSucceeded(event -> {
			chatView.getItems().add(ownMessages.getValue());
		});
		
		if (!(playerName == username.getText())) {
			Thread t = new Thread(otherMessages);
			t.setDaemon(true);
			t.start();
		} else {
			Thread t2 = new Thread(ownMessages);
			t2.setDaemon(true);
			t2.start();
		}
	}
	
	public void sendSystemMessage(String msg) {
		chatView.getItems().add(new HBox(new Label("[SYSTEM] " + msg)));
	}
	
	public void setUsername(String s) {
		username.setText(s);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		currentFloor.setText("1");
		messageBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					String msg = messageBox.getText();
					if (!msg.isEmpty()) {
						
//						String[] split = msg.split("\\s+");
//						
//						if (msg.startsWith("/w")) {
//							String player = split[1];
//							String parsedMessage = Arrays.copyOfRange(split, 2, split.length).toString();
//							//TODO send message to specific player
//							addToChatView(usernameText.getText(), parsedMessage);
//							messageBox.clear();
//						} else {
//							//TODO broadcast message to all players
//							addToChatView(usernameText.getText(), msg);
//							messageBox.clear();
//						}
						
						addToChatView(username.getText(), msg);
						chatView.scrollTo(chatView.getItems().size() - 1);
						messageBox.clear();	
					}
				}
			}
		});
		chatView.getItems().add(new HBox(new Label("Hey, welcome!")));
	}

}
