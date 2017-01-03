package com.company.model;


import com.company.model.Position;

import java.util.HashMap;

/**
 * Created by Glorious Louis on 14/11/2016.
 */
public class Board {
    private static final int LENGTH = 4;
    private HashMap<Position, Mark> playedMarks;
    private HashMap<Position, EmptySpace> availablePositions;
    private Game game;

    public Board(Game game){
        playedMarks = new HashMap<Position, Mark>();
        availablePositions = new HashMap<Position, EmptySpace>();
        for(int i = 0; i < LENGTH; i++){
            for(int j = 0; j < LENGTH; j++){
                availablePositions.put(new Position(i, j, 0), new EmptySpace(i, j, 0));
            }
        }
        this.game = game;
    }

    //Getters
    public HashMap<Position, Mark> getPlayedMarks() {
        return playedMarks;
    }

    public HashMap<Position, EmptySpace> getAvailablePositions() {
        return availablePositions;
    }

    //Methods
    public void setMark(Mark mark) {
        int x = mark.getX();
        int y = mark.getY();
        int z = mark.getZ();

        playedMarks.put(new Position(x, y, z), mark);

        if (!playedMarks.containsKey(new Position(x, y, z + 1))) {
            availablePositions.put(new Position(x, y, z + 1), new EmptySpace(x, y, z + 1));
        }

        availablePositions.remove(new Position(x, y, z));
    }



}
