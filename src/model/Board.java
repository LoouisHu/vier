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
    private HashSet<Position> availablePositions;
    private int zAxis;

    public Board(Game game) {
    	this.reset();
        playedMarks = new HashMap<Position, Mark>();
        availablePositions = new HashSet<Position>();
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                availablePositions.add(new Position(i, j, 0));
            }
        }
        this.zAxis = 1;
    }

  
    public HashMap<Position, Mark> getPlayedMarks() {
        return playedMarks;
    }

    public HashSet<Position> getAvailablePositions() {
        return availablePositions;
    }
    
   
    public void setMark(Mark mark) {
        int x = mark.getPosition().getX();
        int y = mark.getPosition().getY();
        int z = mark.getPosition().getZ();


        if (!playedMarks.containsKey(new Position(x, y, z + 1))) {
        	for (Position p : getPlayedMarks().keySet()) {
        		if (p.getX() == x && p.getY() == y) {
        			if (z <= p.getZ()) {
        				z = p.getZ() + 1;
        				playedMarks.put(new Position(x, y, z), mark);
        				break;
        			} else {
        				playedMarks.put(new Position(x, y, z), mark);
        				break;
        			}
        		}
        	}
            availablePositions.add(new Position(x, y, z + 1));
        }
        zAxis = getHighestZ();

        availablePositions.remove(new Position(x, y, z));
    }
    
    public Board deepCopy(Game g) {
    	Board b = new Board(g);
    	b.availablePositions = g.getBoard().getAvailablePositions();
    	b.playedMarks = g.getBoard().getPlayedMarks();
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
    
    public boolean hasAdjacentFour(Mark m) {
    	boolean result = false;
    	char mark = m.getMarkChar();
    	int x = m.getPosition().getX();
    	int y = m.getPosition().getY();
    	int z = m.getPosition().getZ();
    	HashMap<Position, Mark> allMarks = this.getPlayedMarks();
    	HashMap<Position, Mark> specificMarks = new HashMap<Position, Mark>();
  
    	for (Mark setMark: allMarks.values()) {
    		if (setMark.getMarkChar() == mark) {
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
    	return result;
    }
    
    public boolean hasDiagonalFour(Mark m) {
    	boolean result = false;
    	
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
    
    public boolean isWinner(Game g) {
    	boolean result = false;
    	for (Player p : g.getPlayers()) {
    		if (hasDiagonalFour(p.getMark())) {
    			result = true;
    		}
    	}
    	return result;
    }

    public boolean gameOver() {
    	return false; //TODO
    }
    
    public String toString() {
    	StringBuilder result = new StringBuilder();
    	for (int i = 0; i < zAxis; i++) {	
    		for (int j = 0; j < (LENGTH * LENGTH); j++) {
    			//TODO
    		}
    		result.append("\n");
    	}
    	return result.toString();
    }
   
}
