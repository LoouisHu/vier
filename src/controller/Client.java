package controller;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

import model.Board;


public class Client  {
	
	private String clientName;
	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;
	private Board board;
	private List<Integer> exts;
	
	public Client() throws IOException {
		socket = new Socket(InetAddress.getLocalHost(), 25565);
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			System.out.print(e.getMessage());
		}
		board = new Board(4);
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
			split = Arrays.copyOfRange(split, 2, split.length);
		} else {
			split = Arrays.copyOfRange(split, 1, split.length);
		}
		
		switch (command) {
			case Protocol.CONFIRM:
				handleConfirm(split);
				break;
			case Protocol.GAME_START:
				handleGameStart(split);
				break;
			case Protocol.GAME_MOVE:
				handleGameMove(split);
				break;
			case Protocol.GAME_END: 
				handleGameUnready(split);
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
			case Protocol.LEADERBOARD_LIST:
				handleLeaderboardList(split);
				break;
			case Protocol.ERROR: 
				handleError(split);
				break;
		}
	}

	private void handleConfirm(String[] command) {
		if (command[0] == "") {
			return;
		}
		System.out.println("This server supports " + command[0]);
		if (command[0].contains("0")) {
			exts.add(0);
		}
		if (command[0].contains("1")) {
			exts.add(1);
		}
		if (command[0].contains("2")) {
			exts.add(2);
		}
		
	}

	private void handleGameStart(String[] command) {
		
	}

	private void handleGameMove(String[] command) {
		
	}

	private void handleGameUnready(String[] command) {
		// TODO Auto-generated method stub
		
	}

	private void handlePlayers(String[] command) {
		// TODO Auto-generated method stub
		
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

	private void handleLeaderboardList(String[] command) {
		// TODO Auto-generated method stub
		
	}

	private void handleError(String[] command) {
		// TODO Auto-generated method stub
		
	}
	


}
