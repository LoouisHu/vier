package tests;

import java.util.ArrayList;
import java.util.List;

import exceptions.IllegalIntegerException;
import exceptions.IllegalStringException;
import model.Board;
import model.ComputerPlayer;
import model.HumanPlayer;
import model.Mark;
import model.Player;
import model.Strategy;
import view.LocalTUI;

/**
 * Created by Glorious Louis on 14/11/2016.
 * This is actually a controller, but this is for testing purposes.
 */
public class LocalGameClass extends Thread {
    private ArrayList<Player> players;
    private Player player;
    private char[] alphabet = "abcdefghjiklmnopqrstuvwxyz".toCharArray();
    private Board board;
    private int current;
    private int manyPlayers;
    private int manyComputerPlayers;
    private LocalTUI tui;
    private int usedChars;
 
    public LocalGameClass() throws IllegalIntegerException, IllegalStringException {
    	tui = new LocalTUI();
    	board = new Board(tui.askBoardSize());
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
    			players.add(player);
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
    	boolean gameOver = false;
    	while (!gameOver) {
    		players.get(current).makeMove(board);
    		update();
    		gameOver = board.gameOver(players.get(current));
    		if (gameOver == true) {
    			break;
    		}
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
