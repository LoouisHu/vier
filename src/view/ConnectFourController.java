package view;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Board;
import model.Mark;
import model.Position;

public class ConnectFourController implements Initializable {

	@FXML private TextField messageBox;
	@FXML private ListView<HBox> chatView;
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
	
	public void updateFloor(Board board) {
		Map<Position, Mark> boardFloor = board.getFloor(Integer.parseInt(currentFloor.getText()));
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
		}
		
	}
	
	@FXML 
	public void actionButtonDown() {
		int floor = Integer.parseInt(currentFloor.getText());
		if (floor > 1) {
			floor--;
			currentFloor.setText(Integer.toString(floor));
		}
	}
	
	public void sendSystemMessage(String msg) {
		chatView.getItems().add(new HBox(new Label("[SYSTEM] " + msg)));
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		currentFloor.setText("1");
	}

}
