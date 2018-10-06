package game2.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import game2.view.G2Image;

public class RockTest {

	
	@Test
	public void testConstructor() {
		Rock c = new Rock(10,20);
		assertEquals(c.getXLoc(),10);
		assertEquals(c.getYLoc(),20);
	}
	
	@Test
	public void testSize() {
		Rock c = new Rock(10,20);
		assertEquals(c.getWidth(),G2Image.ROCK_PICKUP.getWidth());
		assertEquals(c.getHeight(),G2Image.ROCK_PICKUP.getHeight());
	}
	
}
