package controller;

public interface Protocol {
	
	//Base methods
	//Client -> Server	
	public static final String CONNECT = "CONNECT";
	public static final String DISCONNECT = "DISCONNECT";
	public static final String GAME_READY = "GAME READY";
	public static final String GAME_UNREADY = "GAME UNREADY";
	
	//Server -> Client
	public static final String CONFIRM = "CONFIRM";
	public static final String GAME_START = "GAME START";
	public static final String GAME_END = "GAME END";
	
	//Both Client and Server
	public static final String GAME_MOVE = "GAME MOVE";
	public static final String PLAYERS = "PLAYERS";
	public static final String ERROR = "ERROR";
	
	//Extension 0 - CHALLENGES
	//Client -> Server
	public static final String CHALLENGE_CONFIRM = "CHALLENGE CONFIRM";
	
	//Both Client and Server
	public static final String CHALLENGE_REQUEST = "CHALLENGE REQUEST";
	public static final String CHALLENGE_DENY = "CHALLENGE DENY";
	
	//Extension 1 - CHAT
	//Both Client and Server
	public static final String CHAT_MESSAGE = "CHAT MESSAGE";
	public static final String CHAT_USER = "CHAT USER";
	
	//Extension 2 - LEADERBOARD
	//Client -> Server
	public static final String LEADERBOARD_USER = "LEADERBOARD USER";
	public static final String LEADERBOARD_ALL = "LEADERBOARD ALL";
	
	//Server -> Client
	public static final String LEADERBOARD_LIST = "LEADERBOARD LIST";
	
}
