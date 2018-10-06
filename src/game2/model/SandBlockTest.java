package game2.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import game2.view.G2Image;

public class SandBlockTest {

	@Test
	public void testConstructor() {
		SandBlock s = new SandBlock(10,20);
		assertEquals(s.getXLoc(),10);
		assertEquals(s.getYLoc(),20);
	}
	
	@Test
	public void testSize() {
		SandBlock s = new SandBlock(10,20);
		assertEquals(SandBlock.getWidth(),G2Image.SANDBLOCK.getWidth());
		assertEquals(SandBlock.getHeight(),G2Image.SANDBLOCK.getHeight());
	}
	
	
	@Test
	public void testHealth() {
		SandBlock s = new SandBlock(10,20);
		assertEquals(s.getHealth(), 7);
		s.reduceHealth(10);
		assertEquals(s.getHealth(), 0);
		s.reduceHealth(-10);
		assertEquals(s.getHealth(), 7);
		s.setDestroyed(true);
		s.reduceHealth(1);
		assertEquals(s.getHealth(), 0);
		// Object
		RockWall obs = new RockWall(10,20);
		s.setObs(obs);
		assertEquals(s.getObs(), obs);
		s.setDestroyed(false);
		s.reduceHealth(-10);
		assertEquals(s.getHealth(), 7);
		s.reduceHealth(2);
		assertEquals(s.getHealth(), 7);
		s.reduceHealth(2);
		assertEquals(s.getHealth(), 7);
		assertEquals(s.getObs(), null);
		assertEquals(s.getDamage(), "-2");
		assertEquals(s.getAffected(), true);
		s.setAffected(false);
		assertEquals(s.getAffected(), false);
	}
	
	
	@Test
	public void testGets() {
		SandBlock s = new SandBlock(10,20);
		assertEquals(s.getDisplayTime(), -1);
		s.setDisplayTime(10);
		assertEquals(s.getDisplayTime(), 10);
		assertEquals(s.getDestroyed(), false);
		s.reduceHealth(50);
		s.setDestroyed(true);
		assertEquals(s.getDestroyed(), true);
		assertEquals(s.isPlacement(), false);
		s.setPlacement(true);
		assertEquals(s.isPlacement(), true);
		
	}
	
	
}
