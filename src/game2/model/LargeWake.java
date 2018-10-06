package game2.model;

import game2.view.G2Image;

/**
 * Defines a large wake, created by large boats to damage the shore.
 * 
 * @author Tristan Morris, Christof Wittreich
 *
 */
public class LargeWake extends Wake{
	
	/**
	 * Creates an instance of LargeWake.
	 * 
	 * @param xLoc - x location of the wake
	 * @param yLoc - y location of the wake
	 * @param damage - how much damage the wake does to the shore
	 * @param speed - how fast the wake moves
	 */
	public LargeWake(int xLoc, int yLoc, int damage, int speed) {
		super(xLoc, yLoc, G2Image.LARGEWAKE.getWidth(), G2Image.LARGEWAKE.getHeight(), damage, speed);
	}

}