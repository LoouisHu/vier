package controller;

import model.Mark;
import model.RemotePlayer;
import view.LocalTUI;
import view.TUI;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
	
	private static int port;
	private static List<ClientHandler> clients;
	private TUI myTUI;
	private static Server server;
	private final String MYEXTS = "";
	private static ServerSocket ssock;
	private static ArrayList<String> readyPlayers;
	private static ArrayList<String> players;
	private static ArrayList<PlayGame> games;

	public static void main(String[] args) {
		clients = new ArrayList<>();
		players = new ArrayList<>();
		readyPlayers = new ArrayList<>();
		games = new ArrayList<>();
		server = new Server();
		while (ssock == null) {
			String port = server.myTUI.askString("Enter desired port nr.");
			try {
				ssock = new ServerSocket(Integer.parseInt(port));
			} catch (IOException e) {
				System.out.println("Something went wrong, please retry!");
			}
		}
		server.start();
	}

	public static ArrayList<String> getReadyPlayers() {
		return readyPlayers;
	}

	public void updateReady(String name, boolean ready) {
		if (ready) {
			System.out.println("Setting player " + name + " to ready.");
			readyPlayers.add(name);
			if (readyPlayers.size() == 2) {
				RemotePlayer player1 = new RemotePlayer(readyPlayers.get(0), new Mark('a'));
				RemotePlayer player2 = new RemotePlayer(readyPlayers.get(1), new Mark('b'));
				readyPlayers.clear();
				int handler1 = 0;
				int handler2 = 0;
				for (ClientHandler handler : clients) {
					if (handler.getName().equals(player1.getName())) {
						handler1 = clients.indexOf(handler);
					} else if (handler.getName().equals(player2.getName())) {
						handler2 = clients.indexOf(handler);
					}
				}
				PlayGame currentGame = new PlayGame(player1, player2, this, clients.get(handler1), clients.get(handler2));
				games.add(currentGame);
				clients.get(handler1).setGame(currentGame);
				clients.get(handler1).setMyPlayer(player1);
				clients.get(handler2).setGame(currentGame);
				clients.get(handler2).setMyPlayer(player2);
				games.get(games.indexOf(currentGame)).startGame();
			}
		} else {
			if (readyPlayers.contains(name)) {
				System.out.println("Setting player " + name + " to unready.");
				readyPlayers.remove(name);
			}
		}
	}

	public void addPlayer(String player) {
		players.add(player);
	}

	public static ArrayList<String> getPlayers() {
		return players;
	}

	public List<ClientHandler> getClients() {
		return clients;
	}

	public String getMYEXTS() {
		return MYEXTS;
	}

	public Server() {
		myTUI = new TUI();

	}

	public void sendMessage(String message, ClientHandler client) {
		client.sendMessage(message);
	}

	public void broadcast(String message) {
		for (ClientHandler client : clients) {
			sendMessage(message, client);
		}
	}

	public void removeHandler(ClientHandler client) {
		clients.remove(client);

	}

	@Override
	public void run() {
		while (true) {
			Socket sock = null;
			try {
				sock = ssock.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Client connected!");
			ClientHandler handler = new ClientHandler(this, sock);
			clients.add(handler);
			handler.start();
		}
	}

	public boolean setName(ClientHandler theHandler, String name) {
		if (!players.contains(name)) {
			for (ClientHandler handler : clients) {
				if (handler.equals(theHandler)) {
					handler.setName(name);
					return true;
				}
			}
		}
		return false;
	}
	

}
