package game2.model;

import game2.view.G2Image;

/**
 * Defines a CordGrass obstacle. It is put on a SandBlock or held by a crab.
 * 
 * @author Tristan Morris, Christof Wittreich
 *
 */
public class CordGrass extends Obstacle {

	private static int width;
	private static int height;
	
	
	/**
	 * Creates an instance of CordGrass
	 * 
	 * @param x - x location of the CordGrass
	 * @param y - y location of the CordGrass
	 */
	public CordGrass(int x, int y) {
		setXYLoc(x, y);
		this.setHealth(1);
		width = G2Image.GRASS_OBS.getWidth();
		height = G2Image.GRASS_OBS.getHeight();
	}		

	@Override
	public int getWidth() {return width;}

	@Override
	public int getHeight() {return height;}	
}
