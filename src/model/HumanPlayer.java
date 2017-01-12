package model;

import java.util.Set;

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
		boolean valid = false;	
		Set<Position> availablePositions = board.getAvailablePositions();
		Position pAsk = new Position(-1, -1, 0);
		
		outer: while (!valid) {
			try {
				pAsk = tui.askPosition();
			} catch (IllegalIntegerException e) {
				e.printStackTrace();
			}
			for (Position pos : availablePositions) {
				if (pAsk.getX() == pos.getX() && pAsk.getY() == pos.getY()) {
					valid = true;
					break outer;
				}
			}
			System.out.println("ERROR: position (" + pAsk.getX() + ", " + pAsk.getY() +
					") is no valid choice.");
			
		}

		Mark m = new Mark(getMark().getMarkChar(), pAsk);
		
	//	m.getPosition().setXYZ(pAsk.getX(), pAsk.getY(), 0);
		
		return m;
	}
}
