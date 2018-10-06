package game2.model;

import game2.view.G2Image;

//The smallest boat that attacks the shore with wakes.
/**
 * Defines a small boat that sends out wakes with small damage.
 * 
 * @author Tristan Morris, Christof Wittreich
 *
 */
public class SmallBoat extends Boat {
	private final static int damage = 1;
	
	/**
	 * Creates an instance of SmallBoat.
	 * 
	 * @param xLoc		The horizontal location of the boat
	 * @param yLoc		The vertical location of the boat
	 * @param speed		The speed at which the boat moves horizontally
	 * @see Boat
	 */
	public SmallBoat(int xLoc, int yLoc){
		super(xLoc, yLoc);
		width = G2Image.SMALLBOAT.getWidth();
		height = G2Image.SMALLBOAT.getHeight();
	}
	
	public static int getDamage(){
		return damage;
	}
}
