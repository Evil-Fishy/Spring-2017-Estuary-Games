package game1.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ObstacleTest {

	@Test
	public void testConstructor1() {
		Obstacle obs = new Obstacle();
		assertEquals(obs.getXLoc(),0,0.001);
		assertEquals(obs.getYLoc(),0,0.001);
		assertEquals(obs.getxVelocity(),0,0.001);
		assertEquals(obs.getyVelocity(),1,0.001);
	}
	
	@Test
	public void testMoveDown() {
		Obstacle obs = new Obstacle();
		assertEquals(obs.getYLoc(),0,0.001);
		obs.moveVertical();
		assertEquals(obs.getYLoc(),1,0.001);
	}

}
