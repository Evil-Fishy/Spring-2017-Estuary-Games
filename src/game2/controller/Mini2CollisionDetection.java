package game2.controller;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

import game2.model.Crab;
import game2.model.Grass;
import game2.model.Oyster;
import game2.model.Resource;
import game2.model.Rock;
import game2.model.SandBlock;
import game2.model.Wake;

/**
 * Defines Mini2CollisionDetection, a class used to detect collision of interacting objects in game 2.
 * 
 * @author Christof Wittreich
 *
 */
public class Mini2CollisionDetection {

	
	/**
	 * Collision logic for the resources and obstacles
	 * This checks if they are in the same pixel boundary
	 * Code adapted from:
	 * http://zetcode.com/tutorials/javagamestutorial/collision/
	 */
	public static Resource checkCollisionsResources(int x, int y, ArrayList<Resource> resources) {
		// Loop through all
		for (Resource re : resources) {
			Rectangle bdClick = new Rectangle(x, y, 1, 1);
            // Create bounding box
			Rectangle bdRe = new Rectangle(re.getXLoc(), re.getYLoc(), re.getWidth(), re.getHeight());
			// See if it intersects
            if (bdRe.intersects(bdClick)) {
            	return re;
            }
        }
		// Success, no collisions
		return null;
	}
	
	/**
	 * Checks to see if the given user click (mouseclick) collides with any part
	 * of the shore that can be placed on.
	 * 
	 * @param x			the x coordinate of the mouse click
	 * @param y			the y coordinate of the mouse click
	 * @param shoreline	all the SandBlocks in the game
	 * @return	the specific SandBlock that was clicked if it can be placed on, null otherwise
	 */
	public static SandBlock checkCollisionsPlacement(int x, int y, ArrayList<ArrayList<SandBlock>> shoreline) {
		// Loop through all
		for (ArrayList<SandBlock> ar : shoreline) {
			for (SandBlock s : ar) {
				Rectangle bdClick = new Rectangle(x, y, 1, 1);
	            // Create bounding box
				Rectangle bdRe = new Rectangle(s.getXLoc(), s.getYLoc(), SandBlock.getWidth(), SandBlock.getHeight());
				// See if it intersects
	            if (bdRe.intersects(bdClick) && s.isPlacement()) {
	            	return s;
	            }
	        }
		}
		// Success, no collisions
		return null;
	}
	
	/**
	 * Checks to see if a given Wake is close enough to collide with the shore.
	 * Damages the SandBlock it can collide with if there is one.
	 * 
	 * @param wake			the wake being checked for collision
	 * @param shoreline		all the SandBlocks in the game
	 * @return	true if it has collided with a SandBlock, false if not
	 */
	public static boolean checkCollisionsWakes(Wake wake, ArrayList<ArrayList<SandBlock>> shoreline) {
		for (ArrayList<SandBlock> ar : shoreline) {
			for (SandBlock s : ar) {
				Rectangle bdWake = new Rectangle(wake.getXLoc(), wake.getYLoc(), wake.getWidth(), wake.getHeight());
	            // Create bounding box
				Rectangle bdS = new Rectangle(s.getXLoc(), s.getYLoc(), SandBlock.getWidth(), SandBlock.getHeight());
				// See if it intersects
	            if (bdS.intersects(bdWake) && !s.getDestroyed()) {
	            	if (!s.getAffected()) {
	            		s.reduceHealth(wake.getDamage());
	            	}
	            	return true;
	            }
	        }
		}
		// Success, no collisions
		return false;
	}

	/**
	 * Checks to see if a crab is close enough to pick up a Resource.
	 * Picks it up if it can.
	 * 
	 * @param crab			the crab tested for picking up a resource
	 * @param resources		all the resources in the game
	 * @return		true if a resource is picked up, false otherwise
	 */
	public static boolean checkCollisionsCrabsResource(Crab crab, ArrayList<Resource> resources) {
		Iterator<Resource> it1 = resources.iterator();
		while (it1.hasNext()) {
			Resource re = it1.next();
			Rectangle bdCrab = new Rectangle(crab.getXLoc(), crab.getYLoc(), Crab.getWidth(), Crab.getHeight());
            // Create bounding box
			Rectangle bdRe = new Rectangle(re.getXLoc(), re.getYLoc(), re.getWidth(), re.getHeight());
			// See if it intersects
            if (bdRe.intersects(bdCrab) && (crab.getCarriedResource() != null) && (crab.getCarriedResource().equals(re))) {
            	it1.remove();
            	crab.sendHome();
            	return true;
            }
		}
		// Success, no collisions
		return false;
	}
	
	/**
	 * Checks to see if a given Crab is close enough to a SandBlock to place its Obstacle.
	 * Places the Obstacle if it can.
	 * 
	 * @param crab			the Crab being checked
	 * @param shoreline		all the SandBlocks in the game
	 * @return		true if the Crab has placed it's Obstacle, false if it hasn't
	 */
	public static boolean checkCollisionsCrabsSandBlock(Crab crab, ArrayList<ArrayList<SandBlock>> shoreline) {
		Iterator<ArrayList<SandBlock>> it1 = shoreline.iterator();
		while (it1.hasNext()) {
			Iterator<SandBlock> it2 = it1.next().iterator();
			while (it2.hasNext()) {
				SandBlock s = it2.next();
				Rectangle bdCrab = new Rectangle(crab.getXLoc(), crab.getYLoc(), Crab.getWidth(), Crab.getHeight());
	            // Create bounding box
				Rectangle bdS = new Rectangle(s.getXLoc(), s.getYLoc(), SandBlock.getWidth(), SandBlock.getHeight());
				// See if it intersects
	            if (bdS.intersects(bdCrab) && (s.getXLoc() == crab.getCoords()[0]) && (s.getYLoc() == crab.getCoords()[1])) {
	            	if(crab.getCarriedObstacle() != null){
	            		s.setObs(crab.getCarriedObstacle());
	            	}
	            	crab.setHasObstacle(false);
	            	crab.setCarriedObstacle(null);
	            	crab.setBusy(false);
	            	crab.sendHome();
	            	return true;
	            }
			}
		}
		// Success, no collisions
		return false;
	}
	
}
