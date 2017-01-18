package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
	
	private Server server;
	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;
	
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
					shutdown();
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
				shutdown();
				break;
			}
		}
	}
	
	
	/**
	 * Listen to incoming messages from client and parse them.
	 * 
	 * @param message
	 */
	public void handleMessage(String message) {
		String[] split = message.split("\\s+");
		
		String command = split[0];
		
		if (command == "GAME" || command == "CHALLENGE" || command == "CHAT"
				  || command == "LEADERBOARD") {
			command = command + " " + split[1];
		}
		
		switch (command) {
			case Protocol.CONNECT:
				handleConnect(command);
				break;
			case Protocol.DISCONNECT:
				handleDisconnect(command);
				break;
			case Protocol.GAME_READY:
				handleGameReady(command);
				break;
			case Protocol.GAME_UNREADY:
				handleGameUnready(command);
				break;
			case Protocol.GAME_MOVE:
				handleGameMove(command);
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
			case Protocol.LEADERBOARD_ALL:
				handleLeaderboardAll(command);
				break;
			case Protocol.LEADERBOARD_USER:
				handleLeaderboardUser(command);
				break;
			case Protocol.ERROR:
				handleError(command);
				break;
		}
		
	}

	private void handleConnect(String command) {
		// TODO Auto-generated method stub
		
	}

	private void handleDisconnect(String command) {
		// TODO Auto-generated method stub
		
	}

	private void handleGameReady(String command) {
		// TODO Auto-generated method stub
		
	}

	private void handleGameUnready(String command) {
		// TODO Auto-generated method stub
		
	}

	private void handleGameMove(String command) {
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

	private void handleLeaderboardAll(String command) {
		// TODO Auto-generated method stub
		
	}

	private void handleLeaderboardUser(String command) {
		// TODO Auto-generated method stub
		
	}

	private void handleError(String command) {
		// TODO Auto-generated method stub
		
	}

	private void sendMessage(String message) {
		try {
			out.write(message);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			shutdown();
		}
	}
	
	public void shutdown() {
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
