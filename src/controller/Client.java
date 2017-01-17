package controller;

import java.net.Socket;

public class Client {
	
	private String clientName;
	private ClientHandler ch;
	
	public Client(Socket sock, String clientName) {
		ch = new ClientHandler(sock);
		this.clientName = clientName;
	}
	
	/**
	 * Listen to server messages and parse them.
	 * @param message
	 */
	public void handleMessage(String message) {
		
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
