package model;


import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Glorious Louis on 14/11/2016.
 */
public class Board {
    private static final int LENGTH = 4;
    private HashMap<Position, Mark> playedMarks;
    private HashMap<Position, EmptySpace> availablePositions;
    private int zAxis;
    private Game game;

    public Board(Game game) {
    	this.reset();
        playedMarks = new HashMap<Position, Mark>();
        availablePositions = new HashMap<Position, EmptySpace>();
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
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
        
        zAxis = getHighestZ();

        availablePositions.remove(new Position(x, y, z));
    }
    
    public Board deepCopy(Game g) {
    	Board b = new Board(g);
    	b.availablePositions = this.getAvailablePositions();
    	b.playedMarks = this.getPlayedMarks();
    	return b;
    }
    
    public void reset() {
    	playedMarks.clear();
    	availablePositions.clear();
    }
    
    public int getHighestZ() {
    	HashSet<Integer> zposes = new HashSet<Integer>();
    	for (Position p : playedMarks.keySet()) {
    		zposes.add(p.getZ());
    	}
    	return Collections.max(zposes);
    }
    
    public boolean hasFour(Mark m) {
    	boolean result = false;
    	char mark = m.getMark();
    	int x = m.getX();
    	int y = m.getY();
    	int z = m.getZ();
    	HashMap<Position, Mark> allMarks = this.getPlayedMarks();
    	HashMap<Position, Mark> specificMarks = new HashMap<Position, Mark>();
  
    	for (Mark setMark: allMarks.values()) {
    		if (setMark.getMark() == mark) {
    			specificMarks.put(new Position(x, y, z), new Mark(mark));
    		}
    	}
    	
    	Set<Position> positions = specificMarks.keySet();
    	
    	for (Position specificPos: positions) {
    		Set<Position> adjacentPosses = getDirectlyAdjacentPositions(specificPos);
    		for (Position adjacentPos: adjacentPosses) {
    			if (positions.contains(adjacentPos)) {
    				int adjacentX = adjacentPos.getX();
    				int adjacentY = adjacentPos.getY();
    				int adjacentZ = adjacentPos.getZ();
    				
    				int specificX = specificPos.getX();
    				int specificY = specificPos.getY();
    				int specificZ = specificPos.getZ();
    				
    				Position thirdpos;
    				Position fourthpos;
    				
    				if (adjacentX < specificX) {
    					thirdpos = new Position(specificX - 2, y, z);
    					fourthpos = new Position(specificX - 3, y, z);
    					result = check3rd4th(positions, thirdpos, fourthpos);
    				}
    				
    				if (adjacentY < specificY) {
    					thirdpos = new Position(x, specificY - 2, z);
    					fourthpos = new Position(x, specificY - 3, z);
    					result = check3rd4th(positions, thirdpos, fourthpos);
    				}
    				
    				if (adjacentZ < specificZ) {
    					thirdpos = new Position(x, y, specificZ - 2);
    					fourthpos = new Position(x, y, specificZ - 3);
    					result = check3rd4th(positions, thirdpos, fourthpos);
    				}
    				
    				if (adjacentX > specificX) {
    					thirdpos = new Position(specificX + 2, y, z);
    					fourthpos = new Position(specificX + 3, y, z);
    					result = check3rd4th(positions, thirdpos, fourthpos);
    				} 
    				
    				if (adjacentY > specificY) {
    					thirdpos = new Position(x, specificY + 2, z);
    					fourthpos = new Position(x, specificY + 3, z);
    					result = check3rd4th(positions, thirdpos, fourthpos);
    				} 
    				if (adjacentZ > specificZ) {
    					thirdpos = new Position(x, y, specificZ + 2);
    					fourthpos = new Position(x, y, specificY + 3);
    					result = check3rd4th(positions, thirdpos, fourthpos);
    				}
    			}
    		}
    	}
    	//TODO diagonal check
    	return result;
    }
    
    public boolean check3rd4th(Set<Position> positions, Position p3, Position p4) {
    	boolean result = false;
    	if (positions.contains(p3) && positions.contains(p4)) {
    		result = true;
    	}
    	return result;
    }
    
    public Set<Position> getDirectlyAdjacentPositions(Position p) {
    	Set<Position> result = new HashSet<Position>();
    	int x = p.getX();
    	int y = p.getY();
    	int z = p.getZ();
    	
    	//Directly adjacent
    	result.add(new Position(x - 1, y, z));
    	result.add(new Position(x, y - 1, z));
    	result.add(new Position(x, y, z - 1));
    	result.add(new Position(x + 1, y, z));
    	result.add(new Position(x, y + 1, z));
    	result.add(new Position(x, y, z + 1));
    	
    	return result;
    }

   
}
