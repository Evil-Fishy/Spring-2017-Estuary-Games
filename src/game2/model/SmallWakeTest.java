package game2.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SmallWakeTest {

	
	@Test
	public void testConstructor() {
		SmallWake c = new SmallWake(10,20,3,2);
		assertEquals(c.getXLoc(),10);
		assertEquals(c.getYLoc(),20);
		assertEquals(c.getDamage(),3);
		assertEquals(c.getSpeed(),2);
	}
	
}
