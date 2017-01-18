package model;

/**
 * Created by Glorious Louis on 14/11/2016.
 */
public abstract class Player {

	private String name;
	private Mark mark;
	
	public Player(String playerName, Mark playerMark) {
		this.name = playerName;
		this.mark = playerMark;
	} 
	
	public String getName() {
		return name;
	}
	
	public void setName(String newName) {
		name = newName;
	}
	
	public Mark getMark() {
		return mark;
	}
	
	public void setMark(Mark newMark) {
		mark = newMark;
	}
	
	public abstract Mark determineMove(Board board);
	
	public void makeMove(Board board) {
		Mark chosenMark = determineMove(board);
		board.setMark(chosenMark);
	}

}
