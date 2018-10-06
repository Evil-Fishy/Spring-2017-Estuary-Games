package game3.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;

import java.util.List;

import org.junit.Test;

import game3.model.Die;
import game3.model.Marker;
import master.model.Board;

/**
 * Tests the Mini3CollisionDetection class.
 * @author Habibullah Aslam, Natalie Rubin
 *
 */
public class Mini3CollisionDetectionTest {
	Mini3CollisionDetection m = new Mini3CollisionDetection();
	
	@Test
	public void testCheckMarkerCollidedDice() {
		Board b = new Board(200,200);
		Marker m = new Marker(100, 100);
		List<Die> dice = new ArrayList<Die>();
		Die a = new Die(b, 20, 20);
		dice.add(a);
		assertEquals(a, Mini3CollisionDetection.checkMarkerCollidedDice(m, dice));		
		Marker m2 = new Marker(200, 200);
		assertEquals(null, Mini3CollisionDetection.checkMarkerCollidedDice(m2, dice));
	}	
	
	@Test
	public void testCheckDieCollidedMarkers() {
		Board b = new Board(200,200);
		Marker m = new Marker(20, 20);
		List<Marker> markers = new ArrayList<Marker>();
		markers.add(m);
		Die d = new Die(b, 20, 20);
		assertEquals(m, Mini3CollisionDetection.checkDieCollidedMarkers(d, markers));
		Die d2 = new Die(b, 200, 200);
		assertEquals(null, Mini3CollisionDetection.checkDieCollidedMarkers(d2, markers));
	}
	
	@Test
	public void testCheckDieCollidedDice() {
		Board b = new Board(200,200);
		List<Die> dice = new ArrayList<Die>();
		Die d1 = new Die(b, 20, 20);
		Die d2 = new Die(b, 21, 20);
		Die d3 = new Die(b, 200, 200);
		dice.add(d1);
		dice.add(d2);
		dice.add(d3);
		assertEquals(d2, Mini3CollisionDetection.checkDieCollidedDice(d1, dice));
		assertEquals(null, Mini3CollisionDetection.checkDieCollidedDice(d3, dice));
	}
	
}




