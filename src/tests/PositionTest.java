package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Position;

public class PositionTest {
	
	Position p;
	Position p2;
	
	@Before
	public void setUp() {
		p = new Position(1, 2, 3);
		p2 = new Position(1, 2);
	}
	
	@Test
	public void testPosition() {
		assertEquals(p.getX(), 1);
		assertEquals(p.getY(), 2);
		assertEquals(p.getZ(), 3);
		assertEquals(p2.getX(), 1);
		assertEquals(p2.getY(), 1);
	}
	

}
