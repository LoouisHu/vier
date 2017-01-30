package controller;

import model.Player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Objects;

public class ClientHandler extends Thread {
	
	private Server server;
	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;
	private String name;
	private PlayGame game;
	private Player myPlayer;

	public ClientHandler(Server server, Socket socket) {
		this.server = server;
		this.socket = socket;

		try {
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				String message = in.readLine();
				if (message != null) {
					handleMessage(message);
				} else {
					shutdown("Received empty string");
					break;
				}
			} catch (IOException e) {
				shutdown("DEBUG: ");
				e.printStackTrace();
				break;
			}
		}
	}

	public void setGame(PlayGame game) {
		this.game = game;
	}

	public Player getMyPlayer() {
		return myPlayer;
	}

	public void setMyPlayer(Player myPlayer) {
		this.myPlayer = myPlayer;
	}

	/**
	 * Listen to incoming messages from client and parse them.
	 * 
	 * @param message
	 */
	public void handleMessage(String message) {
		System.out.println("I just received a message");
		if (message.length() < 1) {
			return;
		}
		String[] split;
		if (message.contains(" ")) {
			split = message.split(" ");
		} else {
			split = new String[1];
			split[0] = message;
		}

		String command = split[0];
		if (Objects.equals(split[0], "GAME") || Objects.equals(split[0], "CHALLENGE") || Objects.equals(split[0], "CHAT")
				|| Objects.equals(split[0], "LEADERBOARD")) {
			command = command + " " + split[1];
		}
		
		switch (command) {
			case Protocol.CONNECT:
				handleConnect(split);
				break;
			case Protocol.DISCONNECT:
				handleDisconnect(split);
				break;
			case Protocol.GAME_READY:
				handleGameReady(split);
				break;
			case Protocol.GAME_UNREADY:
				handleGameUnready(split);
				break;
			case Protocol.GAME_MOVE:
				handleGameMove(split);
				break;
			case Protocol.PLAYERS:
				handlePlayers(split);
				break;
			case Protocol.CHALLENGE_REQUEST:
				handleChallengeRequest(split);
				break;
			case Protocol.CHALLENGE_DENY:
				handleChallengeDeny(split);
				break;
			case Protocol.CHAT_MESSAGE:
				handleChatMessage(split);
				break;
			case Protocol.CHAT_USER:
				handleChatUser(split);
				break;
			case Protocol.LEADERBOARD_ALL:
				handleLeaderboardAll(split);
				break;
			case Protocol.LEADERBOARD_USER:
				handleLeaderboardUser(split);
				break;
			case Protocol.ERROR:
				handleError(split);
				break;
		}
		
	}

	private void handleConnect(String[] command) {
		if (server.setName(this, command[1])) {
			server.addPlayer(command[1]);
			this.name = command[1];
			sendMessage(Protocol.CONFIRM + " " + server.getMYEXTS());
		} else sendMessage(Protocol.ERROR + 190);
	}

	private void handleDisconnect(String[] command) {
		shutdown("Remote side sent disconnect command.");
	}

	private void handleGameReady(String[] command) {
		System.out.println("test");
		server.updateReady(name, true);
	}

	private void handleGameUnready(String[] command) {
		server.updateReady(name, false);
	}

	private void handleGameMove(String[] command) {
		game.handleMove(command, getMyPlayer());
		
	}

	private void handlePlayers(String[] command) {
		String res = Protocol.PLAYERS;
		for (String string : server.getPlayers()) {
			res += " " + string;
		}
		sendMessage(res);
	}

	private void handleChallengeRequest(String[] command) {
		// TODO Auto-generated method stub
		
	}

	private void handleChallengeDeny(String[] command) {
		// TODO Auto-generated method stub
		
	}

	private void handleChatMessage(String[] command) {
		// TODO Auto-generated method stub
		
	}

	private void handleChatUser(String[] command) {
		// TODO Auto-generated method stub
		
	}

	private void handleLeaderboardAll(String[] command) {
		// TODO Auto-generated method stub
		
	}

	private void handleLeaderboardUser(String[] command) {
		// TODO Auto-generated method stub
		
	}

	private void handleError(String[] command) {
		System.out.println(command);
		
	}

	public void sendMessage(String message) {
		try {
			out.write(message);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			shutdown("Could not send message.");
		}
	}
	
	public void shutdown(String message) {
		System.out.println("Disconnect: ");
		System.out.println(message);
		try {
			in.close();
			out.close();
			socket.close();
			server.removeHandler(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
