package game2.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import game2.view.G2Image;

public class OysterGabionTest {
	
	@Test
	public void testConstructor() {
		OysterGabion c = new OysterGabion(10,20);
		assertEquals(c.getXLoc(),10);
		assertEquals(c.getYLoc(),20);
	}
	
	@Test
	public void testSize() {
		OysterGabion c = new OysterGabion(10,20);
		assertEquals(c.getWidth(),G2Image.OYSTER_OBS.getWidth());
		assertEquals(c.getHeight(),G2Image.OYSTER_OBS.getHeight());
	}
	
	@Test
	public void testHealth() {
		OysterGabion c = new OysterGabion(10,20);
		assertEquals(c.getHealth(), 7);
		c.reduceHealth(10);
		assertEquals(c.getHealth(), 0);
		c.setHealth(10);
		assertEquals(c.getHealth(), 10);
		c.reduceHealth(4);
		assertEquals(c.getHealth(), 6);
	}

}
