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
	private final String MYEXTS = "1";
	private static ClientInput clientInput;
	private char[] alphabet = "abcdefghjiklmnopqrstuvwxyz".toCharArray();
	private boolean isComputer = false;

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
		exts = new ArrayList<>();
		clientInput = new ClientInput(this);
		clientName = myTUI.askString("What username do you want to go by? (no spaces, only letters; if you want a computerplayer type '[name] Computer')");
		String[] split = clientName.split(" ");
		if (split.length > 1) {
			clientName = split[0];
			if (split[1].equals("Computer")) {
				isComputer = true;
			}
		}
		System.out.println("Your name: " + clientName + ".");
		String ipAddr = myTUI.askString("Please enter server IP address.");
		String port = myTUI.askString("Please enter server port number.");
		System.out.println("Attempting to connect to server at " + ipAddr + ":" + port + "...");
		socket = new Socket(InetAddress.getByName(ipAddr), Integer.parseInt(port));
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			System.out.println("Something went wrong, please try again!");
		}
		sendMessage(Protocol.CONNECT + " " + clientName + " " + MYEXTS);
		clientInput.start();
	}

	public TUI getMyTUI() {
		return myTUI;
	}

	public void sendMessage(String message) {
		try {
			System.out.println("Sending message:" + message);
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
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getClientName() {
		return clientName;
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
				sendMessage(Protocol.ERROR + " " + "010");
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
		askReady();
	}

	private void handleGameStart(String[] command) {
		ArrayList<Player> localPlayers = new ArrayList<>();
		Player player;
		for (int j = 2; j < command.length; j++) {
			if (command[j].equals(clientName)) {
				if (isComputer) {
					player = new ComputerPlayer(new NaiveStrategy(command[j]), new Mark(alphabet[j]));
				} else {
					player = new HumanPlayer(command[j], new Mark(alphabet[j]));
				}
				myNumerInGame = j - 2;
			} else {
				player = new RemotePlayer(command[j], new Mark(alphabet[j]));
			}
			localPlayers.add(player);
		}
		game = new Game(localPlayers);
		myTUI.updateGameState(game.getBoard());
		Mark m;
		if (command[2].equals(clientName)) {
			m = game.getPlayers().get(myNumerInGame).determineMove(game.getBoard());
			int localx = m.getPosition().getX();
			int localy = m.getPosition().getY();
			sendMessage(Protocol.GAME_MOVE + " " + --localx + " " + --localy);
		}
	}

	private void handleGameMove(String[] command) {
		int currentNr = playerNumberInGame(command[2]);
		Player currentPlayer = game.getPlayers().get(currentNr);
		int x = Integer.parseInt(command[3]);
		int y = Integer.parseInt(command[4]);
		x++;
		y++;
		Mark m = new Mark(currentPlayer.getMark().getChar(), new Position(x, y, game.getBoard().getHighestZfromXY(x, y)));
		game.getBoard().setMark(m);
		myTUI.updateGameState(game.getBoard());
		if (!(command.length < 6) && command[5].equals(clientName)) {
			m = game.getPlayers().get(myNumerInGame).determineMove(game.getBoard());
			int localx = m.getPosition().getX();
			int localy = m.getPosition().getY();
			sendMessage(Protocol.GAME_MOVE + " " + --localx + " " + --localy);
		}
	}

	private void handleGameEnd(String[] command) {
		if (command.length > 2) {
			if (command[2].equals("DRAW")) {
				System.out.println("The game ended in a draw.");
			} else if (command.length > 3){
				System.out.println(command[3] + " wins!");
			}
		}
		askReady();
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
		sendMessage(Protocol.ERROR + " 010");
	}

	private void handleChallengeDeny(String[] command) {
		// TODO Auto-generated method stub
		sendMessage(Protocol.ERROR + " 010");
	}

	private void handleChatMessage(String[] command) {
		// TODO Auto-generated method stub
		if (command.length > 4) {
			if (command[2].equals("GLOBAL")) {
				System.out.println("A global chat message came in from " + command[3] + ":");
				System.out.println(command[4]);
			} else if (command[2].equals("GAME")) {
				System.out.println("A gamewide chat message came in from " + command[3] + ":");
				System.out.println(command[4]);
			} else if (command[2].equals("PRIVATE")) {
				System.out.println("A private chat message came in from " + command[3] + ":");
				System.out.println(command[4]);
			} else sendMessage(Protocol.ERROR + "011");
		}
	}

	private void handleChatUser(String[] command) {
		// TODO Auto-generated method stub
		sendMessage(Protocol.ERROR + " 010");
	}

	private void handleLeaderboardList(String[] command) {
		// TODO Auto-generated method stub
		sendMessage(Protocol.ERROR + " 010");
	}

	private void handleError(String[] command) {
		// TODO Auto-generated method stub
		System.out.println(command);
		if (command[1].equals("120")) {
			myTUI.updateGameState(game.getBoard());
			Mark m;
			System.out.println("Your last move was not accepted by the server, please try a new move.");
			m = game.getPlayers().get(myNumerInGame).determineMove(game.getBoard());
			int localx = m.getPosition().getX();
			int localy = m.getPosition().getY();
			sendMessage(Protocol.GAME_MOVE + " " + --localx + " " + --localy);
		}
		if (command[1].equals("110")) {
			System.out.println("A player disconnected, game ended in a draw.");
			askReady();
		}
		if (command[1].equals("111")) {
			System.out.println("Received error 111.");
		}
		if (command[1].equals("190")) {
			System.out.println("Name already exists on server.");
			clientName = myTUI.askString("What username do you want to go by?");
			System.out.println("Your name: " + clientName + ".");
			sendMessage(Protocol.CONNECT + " " + clientName + " " + MYEXTS);
		}
	}

	private void askReady() {
		myTUI.askString("Please type anything when you are ready to play a game.");
		sendMessage(Protocol.GAME_READY);
	}


	public void chat(String[] command) {
		if (command.length > 1) {
			if (command[1].equals("global")) {
				String res = "";
				for (int i = 2; i < command.length; i++) {
					res += command[i];
				}
				sendMessage(Protocol.CHAT_MESSAGE + " GLOBAL " + res);
			} else if (command[1].equals("private")) {
				String res = "";
				for (int i = 3; i < command.length; i++) {
					res += command[i];
				}
				sendMessage(Protocol.CHAT_MESSAGE + " PRIVATE " + command[2] + " " + res);
			} else if (command[1].equals("game")) {
				String res = "";
				for (int i = 2; i < command.length; i++) {
					res += command[i];
				}
				sendMessage(Protocol.CHAT_MESSAGE + " GAME " + res);
			}
		}
	}

	public void players(String[] command) {
		if((command.length > 1) && command[1].equals("chat")) {
			sendMessage(Protocol.PLAYERS + " " + "ALL");
		} else {
			sendMessage(Protocol.PLAYERS + " " + 1);
		}
	}


}
