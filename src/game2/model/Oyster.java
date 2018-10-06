package game2.model;

import game2.model.Resource;
import game2.view.G2Image;

/**
 * Defines a Oyster pickup. Adds 1 to oyster count when picked up by a crab.
 * 
 * @author Christof Wittreich
 *
 */
public class Oyster extends Resource {

	private static int width;
	private static int height;
	
	/**
	 * Creates an instance of a Oyster Resource pickup.
	 * Sets the static width and height fields to the scaled
	 * oyster image width and height.
	 * 
	 * @param x - x location of the pickup
	 * @param y - y location of the pickup
	 */
	public Oyster(int x, int y) {
		setXYLoc(x, y);
		width = G2Image.OYSTER_PICKUP.getWidth();
		height = G2Image.OYSTER_PICKUP.getHeight();
	}

	@Override
	public int getWidth() {return width;}
	
	@Override
	public int getHeight() {return height;}		
}
