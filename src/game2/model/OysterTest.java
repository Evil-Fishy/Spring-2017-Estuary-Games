package game2.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import game2.view.G2Image;

public class OysterTest {

	@Test
	public void testConstructor() {
		Oyster c = new Oyster(10,20);
		assertEquals(c.getXLoc(),10);
		assertEquals(c.getYLoc(),20);
	}
	
	@Test
	public void testSize() {
		Oyster c = new Oyster(10,20);
		assertEquals(c.getWidth(),G2Image.OYSTER_PICKUP.getWidth());
		assertEquals(c.getHeight(),G2Image.OYSTER_PICKUP.getHeight());
	}
	
	@Test
	public void testClickable() {
		Oyster c = new Oyster(10,20);
		assertEquals(c.getClickable(), true);
		c.setClickable(false);
		assertEquals(c.getClickable(), false);
	}
	
}
