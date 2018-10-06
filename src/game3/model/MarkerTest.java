package game3.model;

import static org.junit.Assert.*;

import org.junit.Test;

import master.model.Board;

/**
 * Tests the marker class.
 * @author Habibullah Aslam, Natalie Rubin
 *
 */
public class MarkerTest {

	@Test
	public void testConstructor() {
		Marker m = new Marker(200, 200);
		assertEquals(m.getXLoc(),200);
		assertEquals(m.getYLoc(),200);
		assertFalse(m.getContainsDie());
		assertFalse(m.getIsCollided());
	}
	
	@Test
	public void testLoc() {
		Marker m = new Marker(200, 200);
		m.setLocation(13, 14);
		assertEquals(13, m.getXLoc());
		assertEquals(14, m.getYLoc());
	}
	
	@Test
	public void testCollided() {
		Board b = new Board(200,200);
		Marker m = new Marker(200, 200);
		m.setIsCollided(true);
		assertTrue(m.getIsCollided());
		
		Die d = new Die(b, 100, 100);
		m.setDieCollided(d);
		assertEquals(d, m.getDieCollided());
		
		m.setDieCollided(null);
		assertFalse(m.getIsCollided());
	}
	
	@Test
	public void testContainsDie() {
		Board b = new Board(200,200);
		Marker m = new Marker(200, 200);
		m.setContainsDie(true);
		assertTrue(m.getContainsDie());
		
		Die d = new Die(b, 100, 100);
		m.setDieContained(d);
		assertEquals(d, m.getDieContained());
		
		m.setDieContained(null);
		assertFalse(m.getContainsDie());
	}
}
