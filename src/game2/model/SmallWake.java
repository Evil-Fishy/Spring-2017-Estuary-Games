package game2.model;

import game2.view.G2Image;

/**
 * Defines a SmallWake. Created based off an instance of SmallBoat. Used to do damage to the shoreline.
 * 
 * @author Tristan Morris, Christof Wittreich
 *
 */
public class SmallWake extends Wake{
	
	/**
	 * Creates an instance of SmallWake.
	 * 
	 * @param xLoc		x location of the SmallWake
	 * @param yLoc		y location of the SmallWake
	 * @param damage	how much damage the SmallWake does to a SandBlock
	 * @param speed		how fast the SmallWake moves
	 */
	public SmallWake(int xLoc, int yLoc, int damage, int speed) {
		super(xLoc, yLoc, G2Image.SMALLWAKE.getWidth(),
				G2Image.SMALLWAKE.getHeight(), damage, speed);
	}

}
