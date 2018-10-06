package game2.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LargeBoatTest {

	
	@Test
	public void testConstructor() {
		LargeBoat c = new LargeBoat(10,20);
		assertEquals(c.getXLoc(),10);
		assertEquals(c.getYLoc(),20);
	}
	
	@Test
	public void testDamage() {
		LargeBoat c = new LargeBoat(10,20);
		assertEquals(c.getDamage(),3);
	}
	
}
