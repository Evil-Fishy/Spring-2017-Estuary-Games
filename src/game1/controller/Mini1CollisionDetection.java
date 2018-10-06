package game1.controller;

import java.awt.Rectangle;
import java.util.ArrayList;

import game1.model.Crab;
import game1.model.Fish;
import game1.model.Obstacle;
import game1.model.SaltPatch;

/**
 * This is a utility class
 * This has a function that will check for collisions
 * In game one, we only need to check for collision of
 * The crab and the obstacles coming down
 */
public class Mini1CollisionDetection {
	
	/**
	 * Collision logic for the crab and obstacles
	 * This checks if they are in the same pixel boundary
	 * Code adapted from:
	 * http://zetcode.com/tutorials/javagamestutorial/collision/
	 * @param crab The crab object we will check
	 * @param obstacles Arraylist of obstacles to compare against
	 * @return True if there is a collision between the crab and obstacles
	 */
	public static boolean checkCollisions(Crab crab, ArrayList<Obstacle> obstacles) {
		// Create crab box
		Rectangle bdCrab = new Rectangle((int)crab.getXLoc(), (int)crab.getYLoc(), Crab.getImgWidth(), Crab.getImgHeight());
		// Loop through all
		for (Obstacle obs : obstacles) {
            // Create bounding box
			Rectangle bdObs;
			if(obs instanceof Fish)
				bdObs = new Rectangle((int)obs.getXLoc(), (int)obs.getYLoc(), Fish.getImgWidth(), Fish.getImgHeight());
			else
				bdObs = new Rectangle((int)obs.getXLoc(), (int)obs.getYLoc(), SaltPatch.getImgWidth(), SaltPatch.getImgHeight());
			// See if it intersects
            if (bdObs.intersects(bdCrab)) {
            	return true;
            }
        }
		// Success, no collisions
		return false;
	}
	
	
}
