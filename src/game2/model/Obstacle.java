package game2.model;

import game2.view.G2Image;

/**
 * Defines the Obstacle abstract class. Instances of obstacle are placed on sandblocks to
 * increase their health.
 * 
 * @author Christof Wittreich
 *
 */
public abstract class Obstacle {
	private int xLoc;
	private int yLoc;
	private int health = 0;
	
	/**
	 * Reduces the health of the obstacle.
	 * 
	 * @param amount - how much the health is reduced by
	 */
	public void reduceHealth(int amount) {
		this.health -= amount;
		if (this.health < 0) {
			this.health = 0;
		}
	}
	
	/**
	 * Sets the x and y location of the obstacle.
	 * 
	 * @param x - x location of the obstacle
	 * @param y - y location of the obstacle
	 */
	public void setXYLoc(int x, int y) {
		xLoc = x;
		yLoc = y;
	}
	
	public int getXLoc(){
		return xLoc;
	}
	
	public int getYLoc(){
		return yLoc;
	}
	
    public int getHealth() {
		return health;
	}
    
    public void setHealth(int h) {
    	this.health = h;
    }

	public abstract int getWidth();

	public abstract int getHeight();
	
	/**
	 * Returns a default width (needed in other classes if an instance of Obstacle is never defined
	 * but the width of an obstacle is needed).
	 * 
	 * @return default width equal to the width of a rock obstacle
	 */
	public static int getDefaultWidth() {
		return G2Image.ROCK_OBS.getWidth();
	}
	
	/**
	 * Returns a default height (needed in other classes if an instance of Obstacle is never defined
	 * but the height of an obstacle is needed).
	 * 
	 * @return default height equal to the height of a rock obstacle
	 */
	public static int getDefaultHeight() {
		return G2Image.ROCK_OBS.getHeight();
	}
}
