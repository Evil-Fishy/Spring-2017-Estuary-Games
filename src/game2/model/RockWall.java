package game2.model;

import game2.view.G2Image;

/**
 * Defines a RockWall Obstacle to be placed on a SandBlock to defend it.
 * 
 * @author Christof Wittreich
 *
 */
public class RockWall extends Obstacle {	
	private static int width;
	private static int height;
	
	/**
	 * Creates an instance of RockWall.
	 * 
	 * @param x - x location of the RockWall
	 * @param y - y location of the RockWall
	 */
	public RockWall(int x, int y) {
		setXYLoc(x, y);
		this.setHealth(3);
		width = G2Image.ROCK_OBS.getWidth();
		height = G2Image.ROCK_OBS.getHeight();
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}	
}
