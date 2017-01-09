package model;

public interface Strategy {

	public String getName();
	
	public Mark determineMove(Board b, Mark m);
	
}
