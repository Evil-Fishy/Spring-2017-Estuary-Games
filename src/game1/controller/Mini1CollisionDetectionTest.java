package game1.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import game1.model.Crab;
import game1.model.Obstacle;
import game1.model.SaltPatch;
import master.model.Board;

public class Mini1CollisionDetectionTest {

	@Test
	public void testHit1() {
		// General objects
		Board board = new Board(300, 300);
		Crab crab = new Crab(board, 20, 20);
		// Obstacles
		ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
		obstacles.add(new SaltPatch(20,20));
		obstacles.add(new SaltPatch(100,20));
		// Check
		assertTrue(Mini1CollisionDetection.checkCollisions(crab, obstacles));
	}
	
	
	@Test
	public void testMiss() {
		// General objects
		Board board = new Board(300, 300);
		Crab crab = new Crab(board, 20, 20);
		// Obstacles
		ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
		obstacles.add(new SaltPatch(200,20));
		obstacles.add(new SaltPatch(100,100));
		// Check
		assertFalse(Mini1CollisionDetection.checkCollisions(crab, obstacles));
	}

}
