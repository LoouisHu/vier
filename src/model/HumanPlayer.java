package model;

import java.util.Set;

import view.TUI;

/**
 * Created by Glorious Louis on 14/11/2016.
 */
public class HumanPlayer extends Player {

	private TUI tui;
	
	public HumanPlayer(String playerName, Mark playerMark) {
		super(playerName, playerMark);
	}

	public Mark determineMove(Board board) {
		boolean valid = false;
		Mark m = new Mark(getMark().getMarkChar());
		Position pAsk = tui.askPosition();
		Set<Position> availablePositions = board.getAvailablePositions();
		for (Position pos : availablePositions) {
			if (pAsk.getX() == pos.getX() && pAsk.getY() == pos.getY()) {
				valid = true;
				break;
			}
		}
		
		while (!valid) {
			System.out.println("ERROR: position (" + pAsk.getX() + ", " + pAsk.getY() +
					") is no valid choice.");
			pAsk = tui.askPosition();
		}
		
		m.setPosition(pAsk.getX(), pAsk.getY(), pAsk.getZ());
		
		return m;
	}
}
