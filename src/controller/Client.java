package controller;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import model.*;
import view.TUI;


public class Client  {
	
	private String clientName;
	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;
	private List<Integer> exts;
	private Game game;
	private ArrayList<String> allPlayers = new ArrayList<>();
	private TUI myTUI;
	private int myNumerInGame = 0;
	private final String MYEXTS = "";
	private char[] alphabet = "abcdefghjiklmnopqrstuvwxyz".toCharArray();

	public static void main(String[] args) {
		try {
			Client me = new Client();
			me.receiveMessage();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public Client() throws IOException {
		myTUI = new TUI();
		clientName = myTUI.askString("What username do you want to go by?");
		System.out.println("Your name: " + clientName + ".");
		String ipAddr = myTUI.askString("Please enter server IP address.");
		String port = myTUI.askString("Please enter server port number.");
		System.out.println("Attempting to connect to server at " + ipAddr + ":" + port + "...");
		socket = new Socket(InetAddress.getByName(ipAddr), Integer.parseInt(port));
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			System.out.print(e.getMessage());
			disconnect();
		}
		sendMessage(Protocol.CONNECT + " " + clientName + " " + MYEXTS);
	}

	public void sendMessage(String message) {
		try {
			out.write(message);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			disconnect();
		}
	}

	public void disconnect() {
		try {
			out.write(Protocol.DISCONNECT);
			out.newLine();
			out.flush();
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
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

	private int playerNumberInGame (String playerName){
		for (Player player : game.getPlayers()) {
			if (player.getName().equals(playerName)) {
				return game.getPlayers().indexOf(player);
			}
		}
		return 0;
	}

	/**
	 * Listen to server messages and parse them.
	 * @param message
	 */
	public void handleMessage(String message) {
		
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
			case Protocol.CONFIRM:
				handleConfirm(split);
				break;
			case Protocol.GAME_START:
				handleGameStart(split);
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
			case Protocol.LEADERBOARD_LIST:
				handleLeaderboardList(split);
				break;
			case Protocol.ERROR: 
				handleError(split);
				break;
			case Protocol.GAME_END:
				handleGameEnd(split);
			default:
				System.out.println("An invalid command came in: " + command);
				sendMessage(Protocol.ERROR + "010");
		}
	}

	private void handleConfirm(String[] command) {
		if (!(command.length == 1)) {
			if (command.length > 1) {
				System.out.println("This server supports " + command[1]);
			}
			if (command[1].contains("0")) {
				exts.add(1);
			}
			if (command[1].contains("1")) {
				exts.add(1);
			}
			if (command[1].contains("2")) {
				exts.add(2);
			}
		}
		myTUI.askString("Please type anything when you are ready to play a game.");
		sendMessage(Protocol.GAME_READY);
	}

	private void handleGameStart(String[] command) {
		ArrayList<Player> localPlayers = new ArrayList<>();
		Player player;
		for (int j = 2; j < command.length; j++) {
			if (command[j].equals(clientName)) {
				player = new HumanPlayer(command[j], new Mark(alphabet[j]));
				myNumerInGame = j - 2;
			} else {
				player = new RemotePlayer(command[j], new Mark(alphabet[j]));
			}
			localPlayers.add(player);
		}
		//TODO: remove debug next line
		System.out.println(localPlayers.toString());
		game = new Game(localPlayers);
		Mark m;
		if (command[2].equals(clientName)) {
			m = game.getPlayers().get(myNumerInGame).determineMove(game.getBoard());
			sendMessage(Protocol.GAME_MOVE + " " + m.getPosition().getX() + " " + m.getPosition().getY());
		}
	}

	private void handleGameMove(String[] command) {
		int currentNr = playerNumberInGame(command[2]);
		Player currentPlayer = game.getPlayers().get(currentNr);
		Mark m = new Mark(currentPlayer.getMark().getChar(), new Position(Integer.parseInt(command[3]), Integer.parseInt(command[4]), 0));
		game.getBoard().setMark(m);
		myTUI.updateGameState(game.getBoard());
		if (!command[5].isEmpty() && command[5].equals(clientName)) {
			m = game.getPlayers().get(myNumerInGame).determineMove(game.getBoard());
			System.out.println(game.getPlayers().get(myNumerInGame).getClass());
			sendMessage(Protocol.GAME_MOVE + " " + m.getPosition().getX() + " " + m.getPosition().getY());
		}
	}

	private void handleGameEnd(String[] command) {
		if (!command[2].equals("DRAW")) {
			System.out.println("The game ended in a draw.");
		}
		else {
			System.out.println(command[3] + " wins!");
		}
	}

	private void handlePlayers(String[] command) {
		if (command[0].equals("ALL")) {
			allPlayers = new ArrayList<>();
			allPlayers.addAll(Arrays.asList(command).subList(1, command.length - 1));
		}
//		TODO: Add support for command with certain extensions
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
		System.out.println(command);
	}
	


}
