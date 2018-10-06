package game2.model;

import game2.view.G2Image;

/**
 * Defines the OysterGabion obstacle used for defending the shore.
 * 
 * @author Christof Wittreich
 *
 */
public class OysterGabion extends Obstacle {
	private static int width;
	private static int height;
	
	/**
	 * Creates an instance of OysterGabion to be placed on a SandBlock.
	 * 
	 * @param x - x location of the OysterGabion
	 * @param y - y location of the OysterGabion
	 */
	public OysterGabion(int x, int y) {
		setXYLoc(x, y);
		this.setHealth(7);
		width = G2Image.OYSTER_OBS.getWidth();
		height = G2Image.OYSTER_OBS.getHeight();
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
