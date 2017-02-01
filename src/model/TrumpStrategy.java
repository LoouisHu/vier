package model;

import java.util.Random;
import java.util.Set;

public class TrumpStrategy implements Strategy {

	private String name;
	
	public TrumpStrategy(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Mark determineMove(Board b, Mark m) {
		Set<Position> availablePosses = b.getAvailablePositions();
		Mark mark = new Mark(m.getChar());
		int randomInt = new Random().nextInt(availablePosses.size());
		int i = 0;
		for (Position p : availablePosses) {
			if (i == randomInt) {
				mark = new Mark(m.getChar(), new Position(p.getX(), p.getY(), p.getZ())); 
			}
			i++;
		} 
		
		return mark;
	}

}
