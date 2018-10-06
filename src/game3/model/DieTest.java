package game3.model;

import static org.junit.Assert.*;

import org.junit.Test;

import game3.view.G3Image;
import master.model.Board;
import master.model.Direction;

/**
 * Tests the Die model.
 * @author Habibullah Aslam, Natalie Rubin
 *
 */
public class DieTest {
	Board b = new Board(200, 200);

	@Test
	public void testConstructor() {		
		Die d = new Die(b, b.getWidth(), b.getHeight());
		assertEquals(d.getYVel(), 50);
		assertEquals(d.getXVel(), 50);
		assertEquals(d.getXLoc(), 200);
		assertEquals(d.getYLoc(), 200);
	}

	@Test
	public void testUpdateDieAndVelModifiers() {	
		G3Image.DIE.scaleToSize(50, 50);	
		Die d = new Die(b, 99, 99);
		d.setDir(Direction.S);	
		d.updateDie();
		assertEquals(Direction.S, d.getDir());
		d.updateDie();
		assertEquals(Direction.N, d.getDir());
		d.updateDie();
		assertEquals(Direction.N, d.getDir());		
		d.setLocation(10, 10);
		d.updateDie();
		assertEquals(Direction.S, d.getDir());
		
		
		d = new Die(b, 99, 99);
		d.setDir(Direction.E);	
		d.updateDie();
		assertEquals(Direction.E, d.getDir());
		d.updateDie();
		assertEquals(Direction.W, d.getDir());
		d.updateDie();
		assertEquals(Direction.W, d.getDir());	
		d.setLocation(25, 25);
		d.updateDie();
		assertEquals(Direction.E, d.getDir());
		
		d = new Die(b, 40, 60);
		d.setDir(Direction.NE);	
		d.updateDie();
		assertEquals(Direction.NE, d.getDir());
		d.updateDie();
		assertEquals(Direction.SE, d.getDir());
		d.updateDie();
		assertEquals(Direction.SW, d.getDir());	
		d.setLocation(50, 200);
		d.updateDie();
		assertEquals(Direction.NW, d.getDir());
		
		d = new Die(b, 120, 60);
		d.setDir(Direction.NW);	
		d.updateDie();
		assertEquals(Direction.NW, d.getDir());
		d.updateDie();
		assertEquals(Direction.SW, d.getDir());
		d.updateDie();
		assertEquals(Direction.SE, d.getDir());	
		d.setLocation(10, 150);
		d.updateDie();
		assertEquals(Direction.NE, d.getDir());
		
		d = new Die(b, 40, 120);
		d.setDir(Direction.NW);	
		d.updateDie();
		assertEquals(Direction.NE, d.getDir());
		
		d = new Die(b, 120, 120);
		d.setDir(Direction.NE);	
		d.updateDie();
		assertEquals(Direction.NW, d.getDir());
		
		d.setLocation(10, 20);
		d.setXVel(0);
		d.setYVel(0);
		d.updateDie();
		assertEquals(0, d.getXVel());
		assertEquals(0, d.getYVel());
		assertTrue(d.getIsStopped());
		assertEquals(10, d.getButton().getX());
		
		d.updateDie();
		assertTrue(d.getIsStopped());
		
	}
	
	@Test
	public void testIsGoingInADir() {		
		Die d = new Die(b, b.getWidth(), b.getHeight());
		d.setDir(Direction.N);
		assertTrue(d.isGoingUp());
		d.setDir(Direction.E);
		assertTrue(d.isGoingRight());
		d.setDir(Direction.S);
		assertTrue(d.isGoingDown());
		d.setDir(Direction.W);
		assertTrue(d.isGoingLeft());
	}
	
	@Test
	public void testLoc() {		
		Die d = new Die(b, 0, 0);
		d.setLocation(100, 200);
		assertEquals(100, d.getXLoc());
		assertEquals(200, d.getYLoc());
	}
	
	@Test
	public void testDimensionGetters() {
		assertEquals(G3Image.DIE.getWidth(), Die.getWidth());
		assertEquals(G3Image.DIE.getWidth(), Die.getHeight());
	}
	
	@Test
	public void testStopped() {		
		Die d = new Die(b, b.getWidth(), b.getHeight());
		d.setIsStopped(false);
		assertFalse(d.getIsStopped());
	}
	
	@Test
	public void testContained() {		
		Die d = new Die(b, b.getWidth(), b.getHeight());
		d.setContained(false);
		assertFalse(d.getIsContained());
	}
	
	@Test
	public void testMarker() {		
		Die d = new Die(b, b.getWidth(), b.getHeight());
		Marker m = new Marker(20, 20);
		d.setMarker(m);
		assertEquals(m, d.getMarker());
	}
	
	@Test
	public void testFacePic() {		
		Die d = new Die(b, b.getWidth(), b.getHeight());
		int i = 0;
		d.setFacePic(i);
		assertEquals(i, d.getFacePic());
	}
	
	@Test
	public void testSlowMoveToLoc() {		
		Die d = new Die(b, 50, 50);
		d.slowMoveToLoc(0, 0);
		assertEquals(45, d.getXLoc());
		assertEquals(45, d.getYLoc());
		d.slowMoveToLoc(46, 46);
		assertEquals(46, d.getXLoc());
		assertEquals(46, d.getYLoc());
		d.slowMoveToLoc(46, 46);
		assertEquals(46, d.getXLoc());
		assertEquals(46, d.getYLoc());
	}
	
	@Test
	public void testDir() {		
		Die d = new Die(b, b.getWidth(), b.getHeight());
		Direction r = Direction.E;
		d.setDir(r);
		assertEquals(r, d.getDir());
	}
}
