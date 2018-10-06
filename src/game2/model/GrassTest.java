package game2.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import game2.view.G2Image;

public class GrassTest {

	@Test
	public void testConstructor() {
		Grass c = new Grass(10,20);
		assertEquals(c.getXLoc(),10);
		assertEquals(c.getYLoc(),20);
	}
	
	@Test
	public void testSize() {
		Grass c = new Grass(10,20);
		assertEquals(c.getWidth(),G2Image.GRASS_PICKUP.getWidth());
		assertEquals(c.getHeight(),G2Image.GRASS_PICKUP.getHeight());
	}
	
	
}
