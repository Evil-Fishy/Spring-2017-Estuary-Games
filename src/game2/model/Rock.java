package game2.model;

import game2.model.Resource;
import game2.view.G2Image;

/**
 * Defines a Rock pickup. Adds 1 to rock count when picked up by a crab.
 * 
 * @author Christof Wittreich
 *
 */
public class Rock extends Resource {

	private static int width;
	private static int height;
	
	/**
	 * Creates an instance of a Rock Resource pickup.
	 * Sets the static width and height fields to the scaled
	 * rock image width and height.
	 * 
	 * @param x - x location of the pickup
	 * @param y - y location of the pickup
	 */
	public Rock(int x, int y) {
		setXYLoc(x, y);
		width = G2Image.ROCK_PICKUP.getWidth();
		height = G2Image.ROCK_PICKUP.getHeight();
	}

	@Override
	public int getWidth() {return width;}
	
	@Override
	public int getHeight() {return height;}		
}