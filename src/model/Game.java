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

    public Game() {
    	board = new Board(this);
    	manyPlayers = tui.askHowManyPlayers();
    	manyComputerPlayers = tui.askHowManyComputerPlayers();
    	manyPlayers = -manyComputerPlayers;
    	players = new ArrayList<Player>();
    	ArrayList<String> playerNames = tui.askNames(this);
    	for (int j = 0; j < playerNames.size(); j++) {
    		player = new HumanPlayer(playerNames.get(j), new Mark(alphabet[j]));
    		players.add(player);
    	}
    	current = 0;
    }
    
    public ArrayList<Player> getPlayers() {
    	return players;
    }
    
    public int getManyPlayers() {
    	return manyPlayers;
    }
    
    public char[] getAlphabet() {
    	return alphabet;
    }
    
    private void reset() {
    	current = 0;
    	board.reset();
    }
}
