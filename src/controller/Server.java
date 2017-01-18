package controller;


public interface Server {
	
	public static final int PORT = 4444;
	
	public static final String ADDRESS = "localhost";
	
	void sendMessage(String message, ClientHandler client);
	
	void broadcast(String message);
	
	void removeHandler(ClientHandler client);

}
