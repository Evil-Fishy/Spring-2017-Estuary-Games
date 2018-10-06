package game3.controller;

import java.awt.Rectangle;
import java.util.List;

import game3.model.Die;
import game3.model.Marker;
import game3.view.G3Image;

/**
 * 
 * This class is responsible for detecting collisions in minigame 3
 * between the dice and other objects onscreen.
 * 
 * @author Habibullah Aslam, Natalie Rubin
 *
 */
public class Mini3CollisionDetection {
	
	/**
	 * Collision logic for the crab and obstacles
	 * This checks if they are in the same pixel boundary
	 * Code adapted from:
	 * http://zetcode.com/tutorials/javagamestutorial/collision/
	 */
	public static Die checkMarkerCollidedDice(Marker m, List<Die> dice) {
		// Create marker box
		Rectangle boxMarker = new Rectangle((int)m.getXLoc(), (int)m.getYLoc(), Die.getWidth(), Die.getHeight());
		// Loop through all
		for (Die d : dice) {
            // Create bounding box
			Rectangle boxDie = new Rectangle((int)d.getXLoc(), (int)d.getYLoc(), Die.getWidth(), Die.getHeight());
			// See if it intersects but the player is holding the die
			if(boxDie.intersects(boxMarker)) {
				return d;
			}
        }
		// Success, no collisions
		return null;
	}
	
	/**
	 * Checks for collisions between a die and the markers.
	 * 
	 * @param d the die to check for collisions
	 * @param markers the markers to check collisions against
	 * @return the marker that the given die collided with. If none, returns null
	 */
	public static Marker checkDieCollidedMarkers(Die d, List<Marker> markers) {
		// Create die box
		Rectangle boxDie = new Rectangle((int)d.getXLoc(), (int)d.getYLoc(), Die.getWidth(), Die.getHeight());
		// Loop through all
		for (Marker m : markers) {
            // Create bounding box
			Rectangle boxMarker = new Rectangle((int)m.getXLoc(), (int)m.getYLoc(), G3Image.MARKER.getWidth(), G3Image.MARKER.getHeight());
			// See if it intersects but the player is holding the die
			if(boxDie.intersects(boxMarker)) {
				return m;
			}
        }
		// Success, no collisions
		return null;
	}
	
	/**
	 * Checks for collisions between a die and a list of dice.
	 * 
	 * @param d the die to check for collisions
	 * @param dice the dice to check collisions against
	 * @return the die that the given die collided with. If none, returns null
	 */
	public static Die checkDieCollidedDice(Die die, List<Die> dice) {
		// Create die box
		Rectangle boxDie = new Rectangle((int)die.getXLoc(), (int)die.getYLoc(), Die.getWidth(), Die.getHeight());
		// Loop through all
		for (Die d : dice) {
			if(d != die) {
	            // Create bounding box
				Rectangle boxD = new Rectangle((int)d.getXLoc(), (int)d.getYLoc(), G3Image.MARKER.getWidth(), G3Image.MARKER.getHeight());
				// See if it intersects but the player is holding the die
				if(boxDie.intersects(boxD)) {
					return d;
				}
			}
        }
		// Success, no collisions
		return null;
	}
}
