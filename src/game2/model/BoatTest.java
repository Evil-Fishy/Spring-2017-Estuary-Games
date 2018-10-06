package game2.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class BoatTest {

	
	@Test
	public void testConstructor() {
		Boat b = new Boat(10,20);
		assertEquals(b.getXLoc(),10);
		assertEquals(b.getYLoc(),20);
	}
	
	@Test
	public void testMove() {
		Boat b = new Boat(10,20);
		assertEquals(b.getXLoc(),10);
		b.move();
		assertEquals(b.getXLoc(),10+b.getSpeed());
	}
	
	
	@Test
	public void testSize() {
		Boat b = new Boat(10,20);
		assertEquals(b.getWidth(),10);
		assertEquals(b.getHeight(),10);
	}
	
}
