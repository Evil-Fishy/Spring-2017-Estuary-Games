package master.model;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.junit.Test;

public class BoardTest {

	@Test
	public void testConstructor1() {
		Board board = new Board();
		assertEquals(board.getHeight(),500);
		assertEquals(board.getWidth(),900);
	}
	
	@Test
	public void testConstructor2() {
		Board board = new Board(20,25);
		assertEquals(board.getHeight(),25);
		assertEquals(board.getWidth(),20);
	}

}
