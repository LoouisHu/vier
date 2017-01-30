package model;


import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import tests.Game;

/**
 * Created by Glorious Louis on 14/11/2016.
 */
public class Board {
    private HashMap<Position, Mark> playedMarks;
    private Set<Position> availablePositions;
    //@invariant zAxis > 1;
    private int zAxis;
    //@invariant boardLength >= 4 && boardLength <= 9;
    private int boardLength;
    //@invariant
    private int aiThinkTime;
    private int aiTimeLeft;
    
    //Testing purposes
    //@requires boardLength >4 && boardLength <= 9;
    public Board(int boardLength) {
    	this.boardLength = boardLength;
        playedMarks = new HashMap<Position, Mark>();
        availablePositions = new HashSet<Position>();
        placeAvailablePositions();
        zAxis = 1;
    }
    
    public int getAIThinkTime() {
    	return aiThinkTime;
    }

    //@pure
    public int getBoardLength() {
    	return boardLength;
    }
    
    //@pure
    public HashMap<Position, Mark> getPlayedMarks() {
        return playedMarks;
    }

    //@pure
    public Set<Position> getAvailablePositions() {
        return availablePositions;
    }
    
    //@requires mark.getPosition().getX() != null;
    //@requires mark.getPosition().getY() != null;
    //@requires mark.getPosition().getZ() != null;
    //@ensures getAvailable
    public void setMark(Mark mark) {
        int x = mark.getPosition().getX();
        int y = mark.getPosition().getY();
        int z = mark.getPosition().getZ();

        if (!playedMarks.containsKey(new Position(x, y, z))) {
        	playedMarks.put(new Position(x, y, z), mark);
        	availablePositions.add(new Position(x, y, z + 1));
        } else { 
        	z = getHighestZfromXY(x, y) + 1;
        	playedMarks.put(new Position(x, y, z), mark);
        	availablePositions.add(new Position(x, y, z + 1));
        }
        
        availablePositions.remove(new Position(x, y, z));
        zAxis = getHighestZ();
    }
    
    public Board deepCopy() {
    	Board b = new Board(boardLength);
    	b.availablePositions = this.getAvailablePositions();
    	b.playedMarks = this.getPlayedMarks();
    	return b;
    }
    
    //@ensures getPlayedMarks().size() == 0;
    //		&& getAvailablePositions().size() == getBoardLength() * getBoardLength();
    public void reset() {
    	playedMarks.clear();
    	availablePositions.clear();
    	placeAvailablePositions();
    }
    
    private void placeAvailablePositions() {
        for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardLength; j++) {
                availablePositions.add(new Position(i + 1, j + 1, 1));
            }
        }
    }
    
    
    public int getHighestZ() {
    	HashSet<Integer> zposes = new HashSet<Integer>();
    	
    	if (playedMarks.keySet().size() == 0) {
    		return 1;
    	}
    	
    	for (Position p : playedMarks.keySet()) {
    		zposes.add(p.getZ());
    	}
    	
    	if (Collections.max(zposes) == 0) {
    		return 1;
    	}
    	
    	return Collections.max(zposes);
    }
    
    public int getHighestZfromXY(int x, int y) {
    	HashMap<Position, Mark> playMarks = playedMarks;
    	int result = 0;
    	for (int i = 0; i < zAxis; i++) {
    		if (playMarks.containsKey(new Position(x, y, i + 1))) {
    			result++;
    		}
    	}
    	
    	if (result > 0) {
    		return result;
    	} else {
    		return 1;
    	}
    	
    }
    
    public HashMap<Position, Mark> getFloor(int z) {
    	HashMap<Position, Mark> result = playedMarks;
    	for (Position p : result.keySet()) {
    		if (p.getZ() == z) {
    			result.put(p, result.get(p));
    		}
    	}
    	return result;
    }
    
    public boolean hasFour(Mark m) {
    	boolean result = false;
    	char mark = m.getChar();
    	
    	for (int i = 0; i < playedMarks.size(); i++) {
    		Mark markie = (Mark) playedMarks.values().toArray()[i];
    		if (mark == markie.getChar()) {
    			Position p = (Position) playedMarks.keySet().toArray()[i];
    			int x = p.getX();
    			int y = p.getY();
    			int z = p.getZ();
    			
    			if (playedMarks.containsKey(new Position(x + 1, y, z))
    				&& playedMarks.get(new Position(x + 1, y, z)).getChar() == markie.getChar()
    				&& playedMarks.containsKey(new Position(x + 2, y, z))
    				&& playedMarks.get(new Position(x + 2, y, z)).getChar() == markie.getChar()
    				&& playedMarks.containsKey(new Position(x + 3, y, z))
    				&& playedMarks.get(new Position(x + 3, y, z)).getChar() == markie.getChar()) {
    				result = true;
    				break;
    			}
    			
    			if (playedMarks.containsKey(new Position(x, y + 1, z))
					&& playedMarks.get(new Position(x, y + 1, z)).getChar() == markie.getChar()	
	    			&& playedMarks.containsKey(new Position(x, y + 2, z))
	    			&& playedMarks.get(new Position(x, y + 2, z)).getChar() == markie.getChar()
	    			&& playedMarks.containsKey(new Position(x, y + 3, z))
	    			&& playedMarks.get(new Position(x, y + 3, z)).getChar() == markie.getChar()) {
        			result = true;
        			break;
        		}
    			
    			if (playedMarks.containsKey(new Position(x, y, z + 1))
    				&& playedMarks.get(new Position(x, y, z + 1)).getChar() == markie.getChar()
        			&& playedMarks.containsKey(new Position(x, y, z + 2))
        			&& playedMarks.get(new Position(x, y, z + 2)).getChar() == markie.getChar()
        			&& playedMarks.containsKey(new Position(x, y, z + 3))
        			&& playedMarks.get(new Position(x, y, z + 3)).getChar() == markie.getChar()) {
        			result = true;
        			break;
        		}
    			
    			if (playedMarks.containsKey(new Position(x - 1, y, z))
    				&& playedMarks.get(new Position(x - 1, y, z)).getChar() == markie.getChar()
        			&& playedMarks.containsKey(new Position(x - 2, y, z))
        			&& playedMarks.get(new Position(x - 2, y, z)).getChar() == markie.getChar()
        			&& playedMarks.containsKey(new Position(x - 3, y, z))
        			&& playedMarks.get(new Position(x - 3, y, z)).getChar() == markie.getChar()) {
        			result = true;
        			break;
        		}
    			
    			if (playedMarks.containsKey(new Position(x, y - 1, z))
    				&& playedMarks.get(new Position(x, y - 1, z)).getChar() == markie.getChar()
        			&& playedMarks.containsKey(new Position(x, y - 2, z))
        			&& playedMarks.get(new Position(x, y - 2, z)).getChar() == markie.getChar()
        			&& playedMarks.containsKey(new Position(x, y - 3, z))
        			&& playedMarks.get(new Position(x, y - 3, z)).getChar() == markie.getChar()) {
        			result = true;
        			break;
        		}
    			
    			if (playedMarks.containsKey(new Position(x, y, z + 1))
					&& playedMarks.get(new Position(x, y, z + 1)).getChar() == markie.getChar()
    				&& playedMarks.containsKey(new Position(x, y, z + 2))
    				&& playedMarks.get(new Position(x, y, z + 2)).getChar() == markie.getChar()
    				&& playedMarks.containsKey(new Position(x, y, z + 3))
    				&& playedMarks.get(new Position(x, y, z + 3)).getChar() == markie.getChar()) {
        			result = true;
        			break;
        		}
    		}
    	}
    	
    	return result;
    }
    
    public boolean hasDiagonalFour(Mark mark) {
    	boolean result = false;
    	char m = mark.getChar();
   		
		for (int i = 0; i < playedMarks.size(); i++) {
			Mark markie = (Mark) playedMarks.values().toArray()[i];
			if (markie.getChar() == m) {
				Position p = (Position) playedMarks.keySet().toArray()[i];
				int x = p.getX();
				int y = p.getY();
				int z = p.getZ();
				
				if (playedMarks.containsKey(new Position(x + 1, y, z - 1))
					&& playedMarks.get(new Position(x + 1, y, z - 1)).getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x + 2, y, z - 2))
					&& playedMarks.get(new Position(x + 2, y, z - 2)).getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x + 3, y, z - 3))
					&& playedMarks.get(new Position(x + 3, y, z - 3))
					.getChar() == markie.getChar()) {
					result = true;
					break;
				}
				
				if (playedMarks.containsKey(new Position(x + 1, y, z + 1))
					&& playedMarks.get(new Position(x + 1, y, z + 1)).getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x + 2, y, z + 2))
					&& playedMarks.get(new Position(x + 2, y, z + 2)).getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x + 3, y, z + 3))
					&& playedMarks.get(new Position(x + 3, y, z + 3))
					.getChar() == markie.getChar()) {
    				result = true;
    				break;
    			}
				
				if (playedMarks.containsKey(new Position(x + 1, y - 1, z - 1))
					&& playedMarks.get(new Position(x + 1, y - 1, z - 1))
					.getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x + 2, y - 2, z - 2))
					&& playedMarks.get(new Position(x + 2, y - 2, z - 2))
					.getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x + 3, y - 3, z - 3))
					&& playedMarks.get(new Position(x + 3, y - 3, z - 3))
					.getChar() == markie.getChar()) {
    				result = true;
    				break;
    			}
				
				if (playedMarks.containsKey(new Position(x + 1, y - 1, z + 1))
					&& playedMarks.get(new Position(x + 1, y - 1, z + 1))
					.getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x + 2, y - 2, z + 2))
					&& playedMarks.get(new Position(x + 2, y - 2, z + 2))
					.getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x + 3, y - 3, z + 3))
					&& playedMarks.get(new Position(x + 3, y - 3, z + 3))
					.getChar() == markie.getChar()) {
    				result = true;
    				break;
    			}
				
				if (playedMarks.containsKey(new Position(x, y - 1, z - 1))
					&& playedMarks.get(new Position(x, y - 1, z - 1)).getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x, y - 2, z - 2))
					&& playedMarks.get(new Position(x, y - 2, z - 2)).getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x, y - 3, z - 3))
					&& playedMarks.get(new Position(x, y - 3, z - 3))
					.getChar() == markie.getChar()) {
    				result = true;
    				break;
    			} 
				
				if (playedMarks.containsKey(new Position(x, y - 1, z + 1))
					&& playedMarks.get(new Position(x, y - 1, z + 1)).getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x, y - 2, z + 2))
					&& playedMarks.get(new Position(x, y - 2, z + 2)).getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x, y - 3, z + 3))
					&& playedMarks.get(new Position(x, y - 3, z + 3))
					.getChar() == markie.getChar()) {
    				result = true;
    				break;
    			}
				
				if (playedMarks.containsKey(new Position(x - 1, y - 1, z - 1))
					&& playedMarks.get(new Position(x - 1, y - 1, z - 1))
					.getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x - 2, y - 2, z - 2))
					&& playedMarks.get(new Position(x - 2, y - 2, z - 2))
					.getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x - 3, y - 3, z - 3))
					&& playedMarks.get(new Position(x - 3, y - 3, z - 3))
					.getChar() == markie.getChar()) {
    				result = true;
    				break;
    			}
				
				if (playedMarks.containsKey(new Position(x - 1, y - 1, z + 1))
					&& playedMarks.get(new Position(x - 1, y - 1, z + 1))
					.getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x - 2, y - 2, z + 2))
					&& playedMarks.get(new Position(x - 2, y - 2, z + 2))
					.getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x - 3, y - 3, z + 3))
					&& playedMarks.get(new Position(x - 3, y - 3, z + 3))
					.getChar() == markie.getChar()) {
    				result = true;
    				break;
    			}
				
				if (playedMarks.containsKey(new Position(x - 1, y, z - 1))
					&& playedMarks.get(new Position(x - 1, y, z - 1)).getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x - 2, y, z - 2))
					&& playedMarks.get(new Position(x - 2, y, z - 2)).getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x - 3, y, z - 3))
					&& playedMarks.get(new Position(x - 3, y, z - 3))
					.getChar() == markie.getChar()) {
    				result = true;
    				break;
    			}
				
				if (playedMarks.containsKey(new Position(x - 1, y, z + 1))
					&& playedMarks.get(new Position(x - 1, y, z + 1)).getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x - 2, y, z + 2))
					&& playedMarks.get(new Position(x - 2, y, z + 2)).getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x - 3, y, z + 3))
					&& playedMarks.get(new Position(x - 3, y, z + 3))
					.getChar() == markie.getChar()) {
    				result = true;
    				break;
    			}
				
				if (playedMarks.containsKey(new Position(x - 1, y + 1, z - 1))
					&& playedMarks.get(new Position(x - 1, y + 1, z - 1))
					.getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x - 2, y + 2, z - 2))
					&& playedMarks.get(new Position(x - 2, y + 2, z - 2))
					.getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x - 3, y + 3, z - 3))
					&& playedMarks.get(new Position(x - 3, y + 3, z - 3))
					.getChar() == markie.getChar()) {
    				result = true;
    				break;
    			}
				
				if (playedMarks.containsKey(new Position(x - 1, y + 1, z + 1))
					&& playedMarks.get(new Position(x - 1, y + 1, z + 1))
					.getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x - 2, y + 2, z + 2))
					&& playedMarks.get(new Position(x - 2, y + 2, z + 2))
					.getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x - 3, y + 3, z + 3))
					&& playedMarks.get(new Position(x - 3, y + 3, z + 3))
					.getChar() == markie.getChar()) {
    				result = true;
    				break;
    			}
				
				if (playedMarks.containsKey(new Position(x, y + 1, z - 1))
					&& playedMarks.get(new Position(x, y + 1, z - 1)).getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x, y + 2, z - 2))
					&& playedMarks.get(new Position(x, y + 2, z - 2)).getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x, y + 3, z - 3))
					&& playedMarks.get(new Position(x, y + 3, z - 3))
					.getChar() == markie.getChar()) {
    				result = true;
    				break;
    			}
				
				if (playedMarks.containsKey(new Position(x, y + 1, z + 1))
					&& playedMarks.get(new Position(x, y + 1, z + 1)).getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x, y + 2, z + 2))
					&& playedMarks.get(new Position(x, y + 2, z + 2)).getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x, y + 3, z + 3))
					&& playedMarks.get(new Position(x, y + 3, z + 3))
					.getChar() == markie.getChar()) {
    				result = true;
    				break;
    			}
				
				if (playedMarks.containsKey(new Position(x + 1, y + 1, z - 1))
					&& playedMarks.get(new Position(x + 1, y + 1, z - 1))
					.getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x + 2, y + 2, z - 2))
					&& playedMarks.get(new Position(x + 2, y + 2, z - 2))
					.getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x + 3, y + 3, z - 3))
					&& playedMarks.get(new Position(x + 3, y + 3, z - 3))
					.getChar() == markie.getChar()) {
    				result = true;
    				break;
    			}
				
				if (playedMarks.containsKey(new Position(x + 1, y + 1, z + 1))
					&& playedMarks.get(new Position(x + 1, y + 1, z + 1))
					.getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x + 2, y + 2, z + 2))
					&& playedMarks.get(new Position(x + 2, y + 2, z + 2))
					.getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x + 3, y + 3, z + 3))
					&& playedMarks.get(new Position(x + 3, y + 3, z + 3))
					.getChar() == markie.getChar()) {
    				result = true;
    				break;
    			}
				
				if (playedMarks.containsKey(new Position(x + 1, y - 1, z))
					&& playedMarks.get(new Position(x + 1, y - 1, z)).getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x + 2, y - 2, z))
					&& playedMarks.get(new Position(x + 2, y - 2, z)).getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x + 3, y - 3, z))
					&& playedMarks.get(new Position(x + 3, y - 3, z))
					.getChar() == markie.getChar()) {
    				result = true;
    				break;
    			}
				
				if (playedMarks.containsKey(new Position(x - 1, y - 1, z))
					&& playedMarks.get(new Position(x - 1, y - 1, z)).getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x - 2, y - 2, z))
					&& playedMarks.get(new Position(x - 2, y - 2, z)).getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x - 3, y - 3, z))
					&& playedMarks.get(new Position(x - 3, y - 3, z))
					.getChar() == markie.getChar()) {
    				result = true;
    				break;
    			}
				
				if (playedMarks.containsKey(new Position(x - 1, y + 1, z))
					&& playedMarks.get(new Position(x - 1, y + 1, z)).getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x - 2, y + 2, z))
					&& playedMarks.get(new Position(x - 2, y + 2, z)).getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x - 3, y + 3, z))
					&& playedMarks.get(new Position(x - 3, y + 3, z))
					.getChar() == markie.getChar()) {
    				result = true;
    				break;
    			}
				
				if (playedMarks.containsKey(new Position(x + 1, y + 1, z))
					&& playedMarks.get(new Position(x + 1, y + 1, z)).getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x + 2, y + 2, z))
					&& playedMarks.get(new Position(x + 2, y + 2, z)).getChar() == markie.getChar()
					&& playedMarks.containsKey(new Position(x + 3, y + 3, z))
					&& playedMarks.get(new Position(x + 3, y + 3, z))
					.getChar() == markie.getChar()) {
    				result = true;
    				break;
    			}
			}
    	}
 
    
    	
    	return result;
    	
    }
    
    public boolean hasDraw(Mark m) {
    	boolean result = false;
    	
    	return result;
    }
    
    public boolean greaterThanFour(Mark m) {
    	return m.getPosition().getZ() > 4;
    }
    
    
    public boolean gameOver(Player p) {
    	return hasDiagonalFour(p.getMark()) || hasFour(p.getMark());
    }
    
    public String toString() {
    	StringBuilder result = new StringBuilder();
    	result.append("y\\x|");
    	for (int l = 0; l < boardLength; l++) {
    		result.append(l + 1 + "|");
    	}
    	
    	result.append("\n");
    	
    	for (int i = 0; i < zAxis; i++) {
    		if (zAxis == 0) {
    			result.append("The board is still empty! \n\n");
    		}
    		for (int j = 0; j < boardLength; j++) {
    			result.append("  " + (j + 1) + "|");
    			for (int k = 0; k < boardLength; k++) {
					Position checkPos = new Position(k + 1, j + 1, i + 1);
					boolean placed = false;
    				for (Position p : playedMarks.keySet()) {
    					if (p.equals(checkPos)) {
    						result.append(playedMarks
    								  .get(new Position(k + 1, j + 1, i + 1)).getChar() + "|");
    						placed = true;
    						break;
    					}
    				}
    				if (!placed) {
        				result.append(" |");
    				}
    				if (k == boardLength - 1) {
    					result.append("\n");
    				}
    			}

    		}
    		result.append(" z=" + (i + 1));
    		result.append("\n");
    	}
    	
    	return result.toString();
    }
    
    public static void main(String[] args) {

    	
    }
}
