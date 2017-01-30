package model;

import java.util.ArrayList;

/**
 * Created by jelle on 30/01/2017.
 */
public class Game {
    private Board board;
    private ArrayList<Player> players;

    public Game(ArrayList<Player> players) {
        board = new Board(4);
        this.players = players;
    }

    public Board getBoard() {
        return board;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
