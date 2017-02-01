package model;

import java.util.Random;
import java.util.Set;

public class TournamentStrategy implements Strategy {
	
	@Override
	public String getName() {
		return "Tournament";
	}

	@Override
	public Mark determineMove(Board b, Mark m) {
		Set<Position> positions = b.getAvailablePositions();
		boolean found = false;
		for (Position p : positions) {
			Mark ownMark = new Mark(m.getChar(), new Position(p.getX(), p.getY(), p.getZ()));
			if (b.hasFour(ownMark) || b.hasDiagonalFour(ownMark)) {
				found = true;
				return ownMark;
			}

		}
		
		if (!found) {
			int randomInt = new Random().nextInt(positions.size());
			int i = 0;
			for (Position p : positions) {
				if (i == randomInt) {
					Mark mark = new Mark(m.getChar(), new Position(p.getX(), p.getY(), p.getZ())); 
					return mark;
				}
				i++;
			} 
		}
		
		
		
		return null;
	}

}
