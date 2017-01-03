package model;


import Position;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Glorious Louis on 14/11/2016.
 */
public class Board {
    private static final int LENGTH = 4;
    private HashMap<Position, Mark> playedMarks;
    private HashMap<Position, EmptySpace> availablePositions;
    private int zAxis;
    private Game game;

    public Board(Game game){
    	this.reset();
        playedMarks = new HashMap<Position, Mark>();
        availablePositions = new HashMap<Position, EmptySpace>();
        for(int i = 0; i < LENGTH; i++){
            for(int j = 0; j < LENGTH; j++){
                availablePositions.put(new Position(i, j, 0), new EmptySpace(i, j, 0));
            }
        }
        this.game = game;
        this.zAxis = 1;
    }

  
    public HashMap<Position, Mark> getPlayedMarks() {
        return playedMarks;
    }

    public HashMap<Position, EmptySpace> getAvailablePositions() {
        return availablePositions;
    }
    
   
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
    
    public Board deepCopy(Game game){
    	Board b = new Board(game);
    	b.availablePositions = this.getAvailablePositions();
    	b.playedMarks = this.getPlayedMarks();
    	return b;
    }
    
    public void reset(){
    	playedMarks.clear();
    	availablePositions.clear();
    }
    
    public boolean hasFour(Mark m) {
    	boolean result = false;
    	char mark = m.getMark();
    	int x = m.getX();
    	int y = m.getY();
    	int z = m.getZ();
    	HashMap<Position, Mark> allMarks = this.getPlayedMarks();
    	HashMap<Position, Mark> specificMarks = new HashMap<Position, Mark>();
  
    	for(Mark setMark: allMarks.values()){
    		if(setMark.getMark() == mark){
    			specificMarks.put(new Position(x, y, z), new Mark(mark));
    		}
    	}
    	
    	//Check row
    	for(Position pos: specificMarks.keySet()){
    		
    	}
    	
    	return result;
    }

   
}
