package model;

import exceptions.IllegalIntegerException;
import view.TUI;

/**
 * Created by Glorious Louis on 14/11/2016.
 */
public class HumanPlayer extends Player {

	private TUI tui;
	
	public HumanPlayer(String playerName, Mark playerMark) {
		super(playerName, playerMark);
		tui = new TUI();
	}

	public Mark determineMove(Board board) {
		Position pAsk = new Position(0, 0);
		try {
			pAsk = tui.askPosition(this, board);
		} catch (IllegalIntegerException e) {
			System.out.println("I'm afraid I cannot let you do that!");
			determineMove(board);
		}

		Position result = new Position(pAsk.getX(), pAsk.getY(), board.getHighestZfromXY(pAsk.getX(), pAsk.getY()));
		Mark m = new Mark(this.getMark().getChar(), result);
		
		return m;
	}
}