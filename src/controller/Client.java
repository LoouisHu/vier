package controller;

import org.json.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

import model.Player;

public class Client implements Runnable {
	
	private String clientName;
	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;
	private Player player;
	
	public Client() throws IOException {
		socket = new Socket(InetAddress.getLocalHost(), 8080);
		
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			System.out.print(e.getMessage());
		}
	}
	
	public void connect() {
		JSONObject obj = new JSONObject();
	}
	
	@Override
	public void run() {
		while (true) {
			receiveMessage();
		}
	}
	
	public void receiveMessage() {
		try {
			while (true) {
				while (in.ready()) {
					handleMessage(in.readLine());
				}
			}
		} catch (IOException e) {
			
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Listen to server messages and parse them.
	 * @param message
	 */
	public void handleMessage(String message) {
		
		if (message.length() < 1) {
			return;
		}
		
		String[] split = message.split("\\s+");
		
		String command = split[0];
		
		if (split[0] == "GAME" || split[0] == "CHALLENGE" || split[0] == "CHAT" 
			      || split[0] == "LEADERBOARD") {
			command = command + " " + split[1];
		}
		
		switch (command) {
			case Protocol.CONFIRM:
				handleConfirm(command);
				break;
			case Protocol.GAME_START:
				handleGameStart(command);
				break;
			case Protocol.GAME_MOVE:
				handleGameMove(command);
				break;
			case Protocol.GAME_END: 
				handleGameUnready(command);
				break;
			case Protocol.PLAYERS: 
				handlePlayers(command);
				break;
			case Protocol.CHALLENGE_REQUEST:
				handleChallengeRequest(command);
				break;
			case Protocol.CHALLENGE_DENY:
				handleChallengeDeny(command);
				break;
			case Protocol.CHAT_MESSAGE:
				handleChatMessage(command);
				break;
			case Protocol.CHAT_USER:
				handleChatUser(command);
				break;
			case Protocol.LEADERBOARD_LIST:
				handleLeaderboardList(command);
				break;
			case Protocol.ERROR: 
				handleError(command);
				break;
		}
	}

	private void handleConfirm(String command) {
		// TODO Auto-generated method stub
		
	}

	private void handleGameStart(String command) {
		// TODO Auto-generated method stub
		
	}

	private void handleGameMove(String command) {
		// TODO Auto-generated method stub
		
	}

	private void handleGameUnready(String command) {
		// TODO Auto-generated method stub
		
	}

	private void handlePlayers(String command) {
		// TODO Auto-generated method stub
		
	}

	private void handleChallengeRequest(String command) {
		// TODO Auto-generated method stub
		
	}

	private void handleChallengeDeny(String command) {
		// TODO Auto-generated method stub
		
	}

	private void handleChatMessage(String command) {
		// TODO Auto-generated method stub
		
	}

	private void handleChatUser(String command) {
		// TODO Auto-generated method stub
		
	}

	private void handleLeaderboardList(String command) {
		// TODO Auto-generated method stub
		
	}

	private void handleError(String command) {
		// TODO Auto-generated method stub
		
	}
	


}
