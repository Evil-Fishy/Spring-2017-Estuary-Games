package game2.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import game2.view.G2Image;

public class SmallBoatTest {

	@Test
	public void testConstructor() {
		SmallBoat c = new SmallBoat(10,20);
		assertEquals(c.getXLoc(),10);
		assertEquals(c.getYLoc(),20);
	}
	
	@Test
	public void testSize() {
		SmallBoat c = new SmallBoat(10,20);
		assertEquals(c.getWidth(),G2Image.SMALLBOAT.getWidth());
		assertEquals(c.getHeight(),G2Image.SMALLBOAT.getHeight());
	}
	
	@Test
	public void testDamage() {
		SmallBoat c = new SmallBoat(10,20);
		assertEquals(c.getDamage(),1);
	}
}
