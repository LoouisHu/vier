package model;

import exceptions.IllegalIntegerException;
import view.LocalTUI;

/**
 * Created by Glorious Louis on 14/11/2016.
 */
public class HumanPlayer extends Player {

	private LocalTUI localTui;
	
	public HumanPlayer(String playerName, Mark playerMark) {
		super(playerName, playerMark);
		localTui = new LocalTUI();
	}

	public Mark determineMove(Board board) {
		Position pAsk = new Position(0, 0);
		try {
			pAsk = localTui.askPosition(this, board);
		} catch (IllegalIntegerException e) {
			e.getMessage();
		}
		
		Position result = new Position(pAsk.getX(), pAsk.getY(), 
				board.getHighestZfromXY(pAsk.getX(), pAsk.getY()));
		Mark m = new Mark(this.getMark().getChar(), result);
		
		return m;
	}
}
