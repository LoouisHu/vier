package model;

public class ComputerPlayer extends Player {

	private Strategy strategy;
	private Mark mark;
	
	public ComputerPlayer(Strategy s, Mark m) {
		super(s.getName() + "-" + m.getChar(), m);
		this.strategy = s;
		this.mark = m;
	}
	
	@Override
	public Mark determineMove(Board board) {
		System.out.println(this.getName() + " has made its move!");
		return strategy.determineMove(board, mark);
	}

}
