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
    private Set<Position> availablePositions;
    private int zAxis;

    public Board(Game game) {
        playedMarks = new HashMap<Position, Mark>();
        availablePositions = new HashSet<Position>();
        placeAvailablePositions();
        zAxis = 1;
    }
    
    public Board() {
        playedMarks = new HashMap<Position, Mark>();
        availablePositions = new HashSet<Position>();
        placeAvailablePositions();
        zAxis = 1;
    }
  
    public HashMap<Position, Mark> getPlayedMarks() {
        return playedMarks;
    }

    public Set<Position> getAvailablePositions() {
        return availablePositions;
    }
    
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
        System.out.println("setMark = (" + x + ", " + y + ", " + z + ")");
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
    	placeAvailablePositions();
    }
    
    public void placeAvailablePositions() {
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
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
    	int result = 1;
    	for (int i = 0; i < zAxis; i++) {
    		if (playMarks.containsKey(new Position(x, y, i + 1))) {
    			result++;
    		}
    	}
    	return result;
    	
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
    	char mark = m.getMarkChar();
    	int four = 0;
    	
    	Set<Position> possiblePosses = new HashSet<Position>();
    	
    	outer: for (int i = 0; i < zAxis; i++) {
    		for (int j = 0; i < LENGTH; j++) {
    			for (int k = 0; i < LENGTH; k++) {
    				Position searchPos = new Position(k + 1, j + 1, i + 1);
    				if (playedMarks.get(searchPos).getMarkChar() == mark) {
    					four++;
    					possiblePosses.add(searchPos);
    					if (four == 4) {
    			    		if (sameRow(possiblePosses) || sameColumn(possiblePosses)) {
    			    			result = true;
    			    			break outer;
    			    		}
    					}
    				} else {
						four = 0;
						possiblePosses.clear();
					}
    			}
    		}
    	}
    	
    	return result;
    }
    
    public boolean sameRow(Set<Position> positions) {
    	for (int i = 0; i < LENGTH; i++) {
    		for (int j = 0; i < zAxis; j++) {
    			if (positions.contains(new Position(1, i + 1, j + 1))
    					&& positions.contains(new Position(2, i + 1, j + 1))
    					&& positions.contains(new Position(3, i + 1, j + 1))
    					&& positions.contains(new Position(4, i + 1, j + 1))) {
    				return true;
    			}
    		}
    	}
    	
    	return false;
    	
    }
    
    public boolean sameColumn(Set<Position> positions) {
    	for (int i = 0; i < LENGTH; i++) {
    		for (int j = 0; j < zAxis; j++) {
    			if (positions.contains(new Position(i + 1, 1, j + 1))
    					&& positions.contains(new Position(i + 1, 2, j + 1))
    					&& positions.contains(new Position(i + 1, 3, j + 1))
    					&& positions.contains(new Position(i + 1, 4, j + 1))) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    
    public boolean hasDiagonalFour(Mark m) {
    	boolean result = false;
    	
    	char mark = m.getMarkChar();
    	int x = m.getPosition().getX();
    	int y = m.getPosition().getY();
    	int z = m.getPosition().getZ();
    	
    	if (zAxis >= 4) {
        	HashMap<Position, Mark> allMarks = this.getPlayedMarks();
        	HashMap<Position, Mark> specificMarks = new HashMap<Position, Mark>();
      
         	for (Mark setMark: allMarks.values()) {
        		if (setMark.getMarkChar() == mark) {
        			specificMarks.put(new Position(x, y, z), new Mark(mark));
        		}
        	}
         	
         	Set<Position> specificPosses = specificMarks.keySet();
         	
         	for (int i = 0; i < specificPosses.size(); i++) {
         		Position p = (Position) specificPosses.toArray()[i];
         		if (isCorner(p)) {
         			if (p.equals(new Position(0, 1, p.getZ() + 1))
         					&& p.equals(new Position(0, 2, p.getZ() + 2))
         					&& p.equals(new Position(0, 3, p.getZ() + 3))) {
         				result = true;
         				break;
         			}
         			if (p.equals(new Position(0, 1, p.getZ() - 3))
         					&& p.equals(new Position(0, 2, p.getZ() - 2))
         					&& p.equals(new Position(0, 3, p.getZ() - 1))) {
         				result = true;
         				break;
         			}
         			if (p.equals(new Position(1, 0, p.getZ() + 1))
         					&& p.equals(new Position(2, 0, p.getZ() + 2))
         					&& p.equals(new Position(3, 0, p.getZ() + 3))) {
         				result = true;
         				break;
         			}
         			if (p.equals(new Position(1, 0, p.getZ() - 3))
         					&& p.equals(new Position(2, 0, p.getZ() - 2))
         					&& p.equals(new Position(3, 0, p.getZ() - 1))) {
         				result = true;
         				break;
         			}
         			if (p.equals(new Position(1, 3, p.getZ() + 1))
         					&& p.equals(new Position(2, 3, p.getZ() + 2))
         					&& p.equals(new Position(3, 3, p.getZ() + 3))) {
         				result = true;
         				break;
         			}
         			if (p.equals(new Position(1, 3, p.getZ() - 3))
         					&& p.equals(new Position(2, 3, p.getZ() - 2))
         					&& p.equals(new Position(3, 3, p.getZ() - 1))) {
         				result = true;
         				break;
         			}
         			if (p.equals(new Position(3, 1, p.getZ() + 1))
         					&& p.equals(new Position(3, 2, p.getZ() + 2))
         					&& p.equals(new Position(3, 3, p.getZ() + 3))) {
         				result = true;
         				break;
         			}
         			if (p.equals(new Position(3, 1, p.getZ() - 3))
         					&& p.equals(new Position(3, 2, p.getZ() - 2))
         					&& p.equals(new Position(3, 3, p.getZ() - 1))) {
         				result = true;
         				break;
         			}
         			if (p.equals(new Position(1, 1, p.getZ() + 1))
         					&& p.equals(new Position(2, 2, p.getZ() + 2))
         					&& p.equals(new Position(3, 3, p.getZ() + 3))) {
         				result = true;
         				break;
         			}
         			if (p.equals(new Position(1, 1, p.getZ() - 3))
         					&& p.equals(new Position(2, 2, p.getZ() - 2))
         					&& p.equals(new Position(3, 3, p.getZ() - 1))) {
         				result = true;
         				break;
         			}
         			if (p.equals(new Position(2, 1, p.getZ() + 1))
         					&& p.equals(new Position(1, 2, p.getZ() + 2))
         					&& p.equals(new Position(0, 3, p.getZ() + 3))) {
         				result = true;
         				break;
         			}
         			if (p.equals(new Position(2, 1, p.getZ() - 3))
         					&& p.equals(new Position(1, 2, p.getZ() - 2))
         					&& p.equals(new Position(0, 3, p.getZ() - 1))) {
         				result = true;
         				break;
         			}
         		}
         	}
    	}
    	
    	return result;
    }

    
    public boolean isCorner(Position p) {
    	boolean result = false;
    	
    	if (p.getX() == 1 && p.getY() == 1) {
    		result = true;
    	}
    	
    	if (p.getX() == 4 && p.getY() == 4) {
    		result = true;
    	}
    	
    	if (p.getX() == 1 && p.getY() == 4) {
    		result = true;
    	}
    	
    	if (p.getX() == 4 && p.getY() == 1) {
    		result = true;
    	}
    	
    	return result;
    }
    
    public boolean isWinner(Game g) {
    	boolean result = false;
    	for (Player p : g.getPlayers()) {
    		if (p.getMark().getPosition() != null) {
    			if (hasDiagonalFour(p.getMark()) || hasFour(p.getMark())) {
    				result = true;
    			}
    		}
    	}
    	return result;
    }

    public boolean gameOver(Game g) {
    	return isWinner(g);
    }
    
    public String toString() {
    	StringBuilder result = new StringBuilder();
    	String begin = "y\\x|1|2|3|4|\n";
    	for (int i = 0; i < zAxis + 1; i++) {
    		if (zAxis == 0) {
    			result.append("The board is still empty! \n\n");
    		}
    		result.append(begin);
    		for (int j = 0; j < LENGTH; j++) {
    			result.append("  " + (j + 1) + "|");
    			for (int k = 0; k < LENGTH; k++) {
					Position checkPos = new Position(k + 1, j + 1, i + 1);
					boolean placed = false;
    				for (Position p : playedMarks.keySet()) {
    					if (p.equals(checkPos)) {
    						result.append(playedMarks
    								  .get(new Position(k + 1, j + 1, i + 1)).getMarkChar() + "|");
    						placed = true;
    						break;
    					}
    				}
    				if (!placed) {
        				result.append(" |");
    				}
    				if (k == LENGTH - 1) {
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
//    	Board board = new Board();
//    	Player p = new HumanPlayer("Louis", new Mark('a'));
//    	p.makeMove(board);
//    	p.makeMove(board);
//    	p.makeMove(board);
//    	p.makeMove(board);
//    	System.out.println(board.hasFour(p.getMark()));
    	
    	String s = "this is a test";
    	String[] split = s.split("\\s+");
    	System.out.println(split[0] + split[3]);
    }
}
