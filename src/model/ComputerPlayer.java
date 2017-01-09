package model;

public class ComputerPlayer extends Player {

	private Strategy strategy;
	private Mark mark;
	
	public ComputerPlayer(Strategy s, Mark m) {
		super(s.getName() + "-" + m.getMarkChar(), m);
		this.strategy = s;
		this.mark = m;
	}
	
	@Override
	public Mark determineMove(Board board) {
		return strategy.determineMove(board, mark);
	}

}
