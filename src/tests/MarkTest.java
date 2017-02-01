package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import model.Mark;
import model.Position;

public class MarkTest {
	
	Mark mark1;
	Mark mark2;

	@Before
	public void setUp() {
		mark1 = new Mark('a');
		mark2 = new Mark('b', new Position(1, 1, 1));
	}
	
	@Test
	public void testMark() {
		assertEquals(mark1.getChar(), new Mark('a').getChar());
		assertEquals(mark2.getPosition().getX(), new Position(1, 1, 1).getX());
	}

}
