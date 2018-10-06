package game2.model;

import game2.model.Resource;
import game2.view.G2Image;

/**
 * Defines a Grass pickup. Adds 1 to grass count when picked up by a crab.
 * 
 * @author Christof Wittreich
 *
 */
public class Grass extends Resource {

	private static int width;
	private static int height;
	
	/**
	 * Creates an instance of a Grass Resource pickup.
	 * 
	 * @param x		x location of the pickup
	 * @param y		y location of the pickup
	 */
	public Grass(int x, int y) {
		setXYLoc(x, y);
		width = G2Image.GRASS_PICKUP.getWidth();
		height = G2Image.GRASS_PICKUP.getHeight();
	}	

	@Override
	public int getWidth() {return width;}
	
	@Override
	public int getHeight() {return height;}	
}
