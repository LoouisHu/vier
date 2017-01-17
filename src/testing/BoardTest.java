package testing;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import model.Board;
import model.Player;
import model.Position;
import model.HumanPlayer;
import model.Mark;

public class BoardTest {
	
	private Board board;
	
	@Before
	public void setUp() {
		board = new Board();
	}
	
	@Test
	public void testInitialBoardState() {
		assertEquals(board.getAvailablePositions().size(), 16);
		assertEquals(board.getPlayedMarks().size(), 0);
	}
	
	@Test
	public void testZHeight() {
		assertEquals(board.getHighestZ(), 1);
		board.setMark(new Mark('a', new Position(1, 1, 1)));
		assertEquals(board.getHighestZ(), 1);
		board.setMark(new Mark('a', new Position(1, 1, 2)));
		assertEquals(board.getHighestZ(), 2);
		assertEquals(board.getHighestZfromXY(1, 1), 2);
		board.setMark(new Mark('a', new Position(2, 2, 3)));
		assertEquals(board.getHighestZ(), 3);
		assertEquals(board.getHighestZfromXY(1, 1), 2);
	}
	
	@Test
	public void testHasFour() {
		for (int i = 0; i < 4; i++) {
			board.setMark(new Mark('a', new Position(i + 1, 1, 1)));
		}
		assertTrue(board.hasFour(new Mark('a')));
		for (int j = 0; j < 3; j++) {
			board.setMark(new Mark('b', new Position(j + 1, 2, 1)));
		}
		assertFalse(board.hasFour(new Mark('b')));
	}
	
	@Test
	public void testIsCorner() {
		assertTrue(board.isCorner(new Position(1, 1, 1)));
		assertTrue(board.isCorner(new Position(1, 4, 2)));
		assertTrue(board.isCorner(new Position(1, 4, 4)));
		assertTrue(board.isCorner(new Position(4, 4, 3)));
		assertFalse(board.isCorner(new Position(2, 2, 1)));
		assertFalse(board.isCorner(new Position(2, 1, 3)));
	}
}
