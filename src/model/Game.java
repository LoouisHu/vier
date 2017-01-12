package model;

import java.util.ArrayList;

import view.TUI;

/**
 * Created by Glorious Louis on 14/11/2016.
 */
public class Game extends Thread {
    private ArrayList<Player> players;
    private Player player;
    private char[] alphabet = "abcdefghjiklmnopqrstuvwxyz".toCharArray();
    private Board board;
    private int current;
    private int manyPlayers;
    private int manyComputerPlayers;
    private TUI tui;
    private int usedChars;

    public Game() {
    	board = new Board(this);
    	tui = new TUI();
    	manyPlayers = tui.askHowManyPlayers();
    	manyComputerPlayers = tui.askHowManyComputerPlayers(this);
    	manyPlayers = manyPlayers - manyComputerPlayers;
    	players = new ArrayList<Player>();
    	ArrayList<String> playerNames = tui.askNames(this);
    	for (int j = 0; j < playerNames.size(); j++) {
    		player = new HumanPlayer(playerNames.get(j), new Mark(alphabet[j]));
    		players.add(player);
    		usedChars++;
    	}
    	
    	if (manyComputerPlayers > 0) {
    		ArrayList<Strategy> strategies = tui.askStrategies(this);
    		for (int i = 0; i < strategies.size(); i++) {
    			player = new ComputerPlayer(strategies.get(i), new Mark(alphabet[usedChars + i]));
    		}
    	}
    	current = 0;
    }
    
    public void start() {
    	//TODO
    	boolean running = true;
    	while (running) {
    		reset();
    		play();
    	}
 
    }
    
    private void play() {
    	update();
    	while (!board.gameOver(this)) {
    		players.get(current).makeMove(board);
    		update();
    		current = (current + 1) % players.size();
    	}
    	printResult();
    }
    
    private void update() {
    	System.out.println("\nCurrent game situation: \n\n" + board.toString());
    }
    
    private void reset() {
    	current = 0;
    	usedChars = 0;
    	board.reset();
    }
    
    private void printResult() {
    	//TODO
    }
    
    public ArrayList<Player> getPlayers() {
    	return players;
    }
    
    public int getManyPlayers() {
    	return manyPlayers;
    }
    
    public int getManyComputerPlayers() {
    	return manyComputerPlayers;
    }
    
//    public char[] getAlphabet() {
//    	return alphabet;
//    }
//    
    public Board getBoard() {
    	return board;
    }
    

}
