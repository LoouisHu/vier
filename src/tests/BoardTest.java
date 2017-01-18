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
		board = new Board(4, 4);
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
		
		board.setMark(new Mark('b', new Position(1, 1, 4)));
		board.setMark(new Mark('b', new Position(2, 1, 3)));
		board.setMark(new Mark('b', new Position(3, 1, 2)));
		board.setMark(new Mark('b', new Position(4, 1, 1)));
		assertTrue(board.hasDiagonalFour(new Mark('b')));
		
		board.setMark(new Mark('c', new Position(1, 1, 1)));
		board.setMark(new Mark('c', new Position(1, 2, 2)));
		board.setMark(new Mark('c', new Position(1, 3, 3)));
		board.setMark(new Mark('c', new Position(1, 4, 4)));
		assertTrue(board.hasDiagonalFour(new Mark('c')));
		
		board.setMark(new Mark('d', new Position(1, 1, 4)));
		board.setMark(new Mark('d', new Position(1, 2, 3)));
		board.setMark(new Mark('d', new Position(1, 3, 2)));
		board.setMark(new Mark('d', new Position(1, 4, 1)));
		assertTrue(board.hasDiagonalFour(new Mark('d')));
		
		board.setMark(new Mark('e', new Position(1, 4, 1)));
		board.setMark(new Mark('e', new Position(2, 4, 2)));
		board.setMark(new Mark('e', new Position(3, 4, 3)));
		board.setMark(new Mark('e', new Position(4, 4, 4)));
		assertTrue(board.hasDiagonalFour(new Mark('e')));
		
		board.setMark(new Mark('f', new Position(1, 4, 4)));
		board.setMark(new Mark('f', new Position(2, 4, 3)));
		board.setMark(new Mark('f', new Position(3, 4, 2)));
		board.setMark(new Mark('f', new Position(4, 4, 1)));
		assertTrue(board.hasDiagonalFour(new Mark('f')));
		
		board.setMark(new Mark('g', new Position(4, 1, 1)));
		board.setMark(new Mark('g', new Position(4, 2, 2)));
		board.setMark(new Mark('g', new Position(4, 3, 3)));
		board.setMark(new Mark('g', new Position(4, 4, 4)));
		assertTrue(board.hasDiagonalFour(new Mark('g')));
		
		board.setMark(new Mark('h', new Position(4, 1, 4)));
		board.setMark(new Mark('h', new Position(4, 2, 3)));
		board.setMark(new Mark('h', new Position(4, 3, 2)));
		board.setMark(new Mark('h', new Position(4, 4, 1)));
		assertTrue(board.hasDiagonalFour(new Mark('h')));
		
		board.setMark(new Mark('i', new Position(2, 2, 3)));
		assertFalse(board.hasDiagonalFour(new Mark('i')));
		
		board.setMark(new Mark('j', new Position(1, 1, 1)));
		assertFalse(board.hasDiagonalFour(new Mark('j')));
		
	}

}
