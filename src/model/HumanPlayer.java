package model;

import controller.ClientInput;
import exceptions.IllegalIntegerException;
import view.TUI;

/**
 * Created by Glorious Louis on 14/11/2016.
 */
public class HumanPlayer extends Player {

	private TUI tui;
	private int x = 1;
	private int y = 1;
	private boolean update = false;
	private ClientInput clientInput;

	public void setClientInput(ClientInput clientInput) {
		this.clientInput = clientInput;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public HumanPlayer(String playerName, Mark playerMark) {
		super(playerName, playerMark);
		tui = new TUI();
	}

	public Mark determineMove(Board board) {
		while (!update) {
			clientInput.getX();
			clientInput.getY();
			clientInput.getUpdate();
			clientInput.resume();
		}
		Position result = new Position(x, y, board.getHighestZfromXY(x, y));
		Mark m = new Mark(this.getMark().getChar(), result);
		x = 1;
		y = 1;
		update = false;
		return m;
	}
}