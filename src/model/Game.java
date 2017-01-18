package model;

import java.util.ArrayList;
import java.util.List;

import exceptions.IllegalIntegerException;
import exceptions.IllegalStringException;
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
    private List<Integer> boardSize;

    public Game() throws IllegalIntegerException, IllegalStringException {
    	tui = new TUI();
    	boardSize = new ArrayList<Integer>();
    	boardSize.addAll(tui.askBoardSize(this));
    	board = new Board(boardSize.get(0), boardSize.get(1));
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
    	boolean running = true;
    	while (running) {
    		play();
    		running = false;
    		reset();
    	}
 
    }
    
    private void play() {
    	update();
    	while (!board.gameOver(players.get(current))) {
    		players.get(current).makeMove(board);
    		update();
    		current = (current + 1) % players.size();
    	}
    	printResult();
    }
    
    private void update() {
    	tui.updateGameState(board);
    }
    
    private void reset() {
    	current = 0;
    	usedChars = 0;
    	board.reset();
    }
    
    private void printResult() {
    	System.out.println("The winner is " + players.get(current).getName() + "! "
    			+ "Congratulations!");
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
    
    public Board getBoard() {
    	return board;
    }
    

}
