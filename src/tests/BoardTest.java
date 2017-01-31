package tests;

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
		board = new Board(9);
	}
	
	@Test
	public void testInitialBoardState() {
		assertEquals(board.getAvailablePositions().size(), 
				board.getBoardLength() * board.getBoardLength());
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
		
		for (int k = 0; k < 4; k++) {
			board.setMark(new Mark('c', new Position(k + 1, 3, 1)));
		}
		assertTrue(board.hasFour(new Mark('c')));
		
		for (int l = 0; l < 4; l++) {
			board.setMark(new Mark('d', new Position(2, 2, l + 1)));
		}
		assertTrue(board.hasFour(new Mark('d')));
	} 
	
	@Test
	public void testDiagonalHasFour() {
		board.setMark(new Mark('a', new Position(1, 1, 1)));
		board.setMark(new Mark('a', new Position(2, 2, 1)));
		board.setMark(new Mark('a', new Position(3, 3, 1)));
		board.setMark(new Mark('a', new Position(4, 4, 1)));
		assertTrue(board.hasDiagonalFour(new Mark('a')));
		
		board.setMark(new Mark('b', new Position(9, 9, 4)));
		board.setMark(new Mark('b', new Position(9, 8, 5)));
		board.setMark(new Mark('b', new Position(9, 7, 6)));
		board.setMark(new Mark('b', new Position(9, 6, 7)));
		assertTrue(board.hasDiagonalFour(new Mark('b')));
		
		board.setMark(new Mark('c', new Position(6, 5, 4)));
		board.setMark(new Mark('c', new Position(5, 6, 3)));
		board.setMark(new Mark('c', new Position(4, 7, 2)));
		board.setMark(new Mark('c', new Position(3, 8, 1)));
		assertTrue(board.hasDiagonalFour(new Mark('c')));
		
		board.setMark(new Mark('d', new Position(3, 9, 2)));
		board.setMark(new Mark('d', new Position(3, 8, 3)));
		board.setMark(new Mark('d', new Position(3, 7, 4)));
		board.setMark(new Mark('d', new Position(3, 6, 5)));
		assertTrue(board.hasDiagonalFour(new Mark('d')));
		
		board.setMark(new Mark('e', new Position(1, 1, 1)));
		board.setMark(new Mark('e', new Position(2, 2, 2)));
		board.setMark(new Mark('e', new Position(3, 3, 3)));
		board.setMark(new Mark('e', new Position(4, 4, 4)));
		assertTrue(board.hasDiagonalFour(new Mark('e')));
		
		board.setMark(new Mark('f', new Position(9, 9, 9)));
		board.setMark(new Mark('f', new Position(8, 8, 8)));
		board.setMark(new Mark('f', new Position(7, 7, 7)));
		board.setMark(new Mark('f', new Position(6, 6, 6)));
		assertTrue(board.hasDiagonalFour(new Mark('f')));

	}

}
