package game2.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import game2.view.G2Image;

public class RockWallTest {
	
	@Test
	public void testConstructor() {
		RockWall c = new RockWall(10,20);
		assertEquals(c.getXLoc(),10);
		assertEquals(c.getYLoc(),20);
	}
	
	@Test
	public void testSize() {
		RockWall c = new RockWall(10,20);
		assertEquals(c.getWidth(),G2Image.ROCK_OBS.getWidth());
		assertEquals(c.getHeight(),G2Image.ROCK_OBS.getHeight());
	}
}
