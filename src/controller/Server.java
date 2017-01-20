package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

public class Server implements Runnable {
	
	static int port = 25565;
	List<ClientHandler> clients;
	
	public Server() {
		try {
			ServerSocket socket = new ServerSocket(port);
			
			while(clients.size() < 1) {
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void sendMessage(String message, ClientHandler client) {

	}

	public void broadcast(String message) {

	}

	public void removeHandler(ClientHandler client) {

	}

	@Override
	public void run() {
		
	}
	

}
