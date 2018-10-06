package game2.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import game2.view.G2Image;

public class CrabTest {
	
	@Test
	public void testConstructor() {
		Mini2State s = new Mini2State();
		Crab c = new Crab(10,20,s);
		assertEquals(c.getXLoc(),10);
		assertEquals(c.getYLoc(),20);
		assertEquals(c.getCoords()[0],10);
		assertEquals(c.getCoords()[1],20);
	}
	
	@Test
	public void testSize() {
		Mini2State s = new Mini2State();
		Crab c = new Crab(10,20,s);
		assertEquals(Crab.getWidth(),G2Image.CRAB.getWidth());
		assertEquals(Crab.getHeight(),G2Image.CRAB.getHeight());
	}
	
	
	@Test
	public void testBusy() {
		Mini2State s = new Mini2State();
		Crab c = new Crab(10,20,s);
		assertEquals(c.getBusy(),false);
		c.setBusy(true);
		assertEquals(c.getBusy(),true);
	}
	
	
	@Test
	public void testCoords() {
		Mini2State s = new Mini2State();
		Crab c = new Crab(10,20,s);
		c.setCoords(new int[]{50,40});
		assertEquals(c.getCoords()[0],50);
		assertEquals(c.getCoords()[1],40);
		c.setObCoords(new int[]{20,10});
		assertEquals(c.getObCoords()[0],20);
		assertEquals(c.getObCoords()[1],10);
		c.switchCoords();
		assertEquals(c.getObCoords()[0],10);
		assertEquals(c.getObCoords()[1],20);
		assertEquals(c.getCoords()[0],20);
		assertEquals(c.getCoords()[1],10);
	}
	
	@Test
	public void testObs() {
		Mini2State s = new Mini2State();
		Crab c = new Crab(10,20,s);
		assertEquals(c.getHasObstacle(),false);
		assertEquals(c.getCarriedObstacle(),null);
		Obstacle o = new OysterGabion(10, 20);
		c.setCarriedObstacle(o);
		c.setHasObstacle(true);
		assertEquals(c.getCarriedObstacle(),o);
		assertEquals(c.getHasObstacle(),true);
	}
	
	
	@Test
	public void testResources() {
		Mini2State s = new Mini2State();
		Crab c = new Crab(10,20,s);
		assertEquals(c.getHasResource(),false);
		assertEquals(c.getCarriedResource(),null);
		Resource r = new Oyster(10, 20);
		c.setCarriedResource(r);
		c.setHasResource(true);
		assertEquals(c.getCarriedResource(),r);
		assertEquals(c.getHasResource(),true);
	}
	
	
	@Test
	public void testHome() {
		Mini2State s = new Mini2State();
		Crab c = new Crab(10,20,s);
		c.sendHome();
		assertEquals(c.getCoords()[0],10);
		assertEquals(c.getCoords()[1],20);
		// Oyster
		c.setCarriedResource(new Oyster(10,20));
		c.sendHome();
		assertEquals(c.getCoords()[0],s.oysterX);
		assertEquals(c.getCoords()[1],s.oysterY);
		// Rock
		c.setCarriedResource(new Rock(10,20));
		c.sendHome();
		assertEquals(c.getCoords()[0],s.rockX);
		assertEquals(c.getCoords()[1],s.rockY);
		// Grass
		c.setCarriedResource(new Grass(10,20));
		c.sendHome();
		assertEquals(c.getCoords()[0],s.grassX);
		assertEquals(c.getCoords()[1],s.grassY);
		// RockWall
		c.setCarriedResource(null);
		c.setCarriedObstacle(new RockWall(10,20));
		c.sendHome();
		assertEquals(c.getCoords()[0],s.rockX);
		assertEquals(c.getCoords()[1],s.rockY);
		// CordGrass
		c.setCarriedObstacle(new CordGrass(10,20));
		c.sendHome();
		assertEquals(c.getCoords()[0],s.grassX);
		assertEquals(c.getCoords()[1],s.grassY);
		// OysterGabion
		c.setCarriedObstacle(new OysterGabion(10,20));
		c.sendHome();
		assertEquals(c.getCoords()[0],s.oysterX);
		assertEquals(c.getCoords()[1],s.oysterY);
	}
	
	
	@Test
	public void testMove() {
		Mini2State s = new Mini2State();
		Crab c = new Crab(50,50,s);
		c.setVelocity(2);
		// Still
		c.setCoords(new int[]{50,50});
		c.move();
		assertEquals(c.getXLoc(), 50);
		assertEquals(c.getYLoc(), 50);
		// DOWN
		c = new Crab(50,50,s);
		c.setCoords(new int[]{-50,-50});
		c.move();
		assertEquals(c.getXLoc(), (int)(50-c.getVelocity()));
		assertEquals(c.getYLoc(), (int)(50-c.getVelocity()));
		// UP
		c = new Crab(50,50,s);
		c.setCoords(new int[]{100,100});
		c.move();
		assertEquals(c.getXLoc(), (int)(50+c.getVelocity()));
		assertEquals(c.getYLoc(), (int)(50+c.getVelocity()));
		// UP-flip
		c = new Crab(50,50,s);
		c.setCoords(new int[]{49,49});
		c.move();
		assertEquals(c.getXLoc(), 49);
		assertEquals(c.getYLoc(), 49);
		// down-flip
		c = new Crab(50,50,s);
		c.setCoords(new int[]{51,51});
		c.move();
		assertEquals(c.getXLoc(), 51);
		assertEquals(c.getYLoc(), 51);
	}
	
}
