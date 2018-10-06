package game1.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import master.model.Board;

public class CrabTest {

	@Test
	public void testConstructor() {
		Board board = new Board(200,200);
		Crab cr1 = new Crab(board,0,10);
		assertEquals(cr1.getXLoc(),0);
		assertEquals(cr1.getYLoc(),10);
	}
	
	@Test
	public void testUpdate() {
		Board board = new Board(200,200);
		Crab cr1 = new Crab(board,0,10);
		cr1.updateCrab(30);
		assertEquals(cr1.getXLoc(),30);
		assertEquals(cr1.getYLoc(),10);	
	}
	
	@Test
	public void testImageSize() {
		Board board = new Board(200,200);
		Crab cr1 = new Crab(board,0,10);
		assertEquals(Crab.getImgHeight(), 21);
		assertEquals(Crab.getImgWidth(), 31);
	}

}
