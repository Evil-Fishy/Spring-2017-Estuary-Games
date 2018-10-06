package game2.model;

import game2.view.G2Image;

/**
 * Defines a pickup that objects of class Crab collect to increase
 * the count of the type of pickup collected.
 * 
 * @author Christof Wittreich
 *
 */
public abstract class Resource {
	
	private int xLoc;
	private int yLoc;
	private boolean isClickable = true;
	
	/**
	 * Sets the x and y location of the resource
	 * 
	 * @param x - x (horizontal) location
	 * @param y - y (vertical) location
	 */
	public void setXYLoc(int x, int y) {
		xLoc = x;
		yLoc = y;
	}
	
	public int getXLoc(){return xLoc;}
	
	public int getYLoc(){return yLoc;}
	
	/**
	 * Sets the isClickable field to the boolean input
	 * 
	 * @param b		A boolean input that determines whether or not the resource is clickable.
	 */
	public void setClickable(boolean b) {
		this.isClickable = b;
	}
	
	public boolean getClickable() {return isClickable;}

	public abstract int getWidth();

	public abstract int getHeight();
	
	/**
	 * Returns a default width (needed in other classes if an instance of Resource is never defined
	 * but the width of a resource is needed).
	 * 
	 * @return default width equal to the width of a rock pickup
	 */
	public static int getDefaultWidth() {
		return G2Image.ROCK_PICKUP.getWidth();
	}
	
	/**
	 * Returns a default height (needed in other classes if an instance of Resource is never defined
	 * but the height of a resource is needed).
	 * 
	 * @return default height equal to the height of a rock pickup
	 */
	public static int getDefaultHeight() {
		return G2Image.ROCK_PICKUP.getHeight();
	}
}
