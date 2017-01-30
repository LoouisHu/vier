package view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Player;

public class LobbyController implements Initializable {
	@FXML private AnchorPane anchorPane;
	@FXML private ListView<String> listUsers;
	@FXML private TextField messageBox;
	@FXML private Text usernameText;
	@FXML private ListView<HBox> chatView;
	
	private double xOffset;
	private double yOffset;
	
	public void setUsersList(List<String> users) {
		List<String> players = new ArrayList<>();
		players.addAll(users);
		ObservableList<String> observableList = FXCollections.observableArrayList(users);
		listUsers = new ListView<String>(observableList);
		
		final ContextMenu contextMenu = new ContextMenu();
		MenuItem requestChallenge = new MenuItem("Request challenge");
		requestChallenge.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//TODO Send challenge to specified player.
			}
		});
		MenuItem copyName = new MenuItem("Copy name");
		copyName.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				final Clipboard clipboard = Clipboard.getSystemClipboard();
				final ClipboardContent content = new ClipboardContent();
				content.putString(listUsers.getSelectionModel().getSelectedItem());
				clipboard.setContent(content);
			}
		});
		
		contextMenu.getItems().addAll(requestChallenge, copyName);
		listUsers.setContextMenu(contextMenu);
		listUsers.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			@Override
			public void handle(ContextMenuEvent event) {
				contextMenu.show(listUsers, event.getScreenX(), event.getScreenY());
				event.consume();
			}
		});
	}
	
	public void pressEnter(KeyEvent event) throws IOException {
		if (event.getCode() == KeyCode.ENTER) {
			String msg = messageBox.getText();
			if (!msg.isEmpty()) {
				
				String[] split = msg.split("\\s+");
				
				if (msg.startsWith("/w")) {
					String player = split[1];
					String parsedMessage = Arrays.copyOfRange(split, 2, split.length).toString();
					//TODO send message to specific player
					addToChatView(usernameText.getText(), parsedMessage);
					messageBox.clear();
				} else {
					//TODO broadcast message to all players
					addToChatView(usernameText.getText(), msg);
					messageBox.clear();
				}
			}
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
				l.setText(usernameText + ": " + msg);
				HBox x = new HBox();
				x.getChildren().add(l);
				return x;
			} 
			
		};
		ownMessages.setOnSucceeded(event -> {
			chatView.getItems().add(ownMessages.getValue());
		});
		
		if (!(playerName == usernameText.getText())) {
			Thread t = new Thread(otherMessages);
			t.setDaemon(true);
			t.start();
		} else {
			Thread t2 = new Thread(ownMessages);
			t2.setDaemon(true);
			t2.start();
		}
	}
	
	public void removeFromChat(Player p) {
		if (chatView.getItems().contains(p.getName())) {
			chatView.getItems().remove(p.getName());
		}
	}
	
	void setUsernameLabel(String user) {
		usernameText.setText(user);	
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

}
