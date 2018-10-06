package game2.model;


import game2.view.G2Image;
/**
 * Defines a large Boat that sends out wakes with high damage.
 * 
 * @author Tristan Morris, Christof Wittreich
 *
 */
public class LargeBoat extends Boat{
	private final static int damage = 3;
	
	/**
	 * Creates an instance of LargeBoat.
	 * 
	 * @param xLoc	The horizontal location of the boat
	 * @param yLoc	The vertical location of the boat
	 * @param speed	The speed at which the boat moves horizontally
	 * @see Boat
	 */
	public LargeBoat(int xLoc, int yLoc){
		super(xLoc, yLoc);
		width = G2Image.LARGEBOAT.getWidth();
		height = G2Image.LARGEBOAT.getHeight();
	}
	
	public static int getDamage(){
		return damage;
	}
}
