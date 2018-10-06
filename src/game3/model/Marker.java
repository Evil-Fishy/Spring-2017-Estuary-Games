package game3.model;

/**
 * Defines a marker in Game3.
 * This class controls the boxes that story cubes are to be dragged into
 * and aligned as a story. It checks for collision with the dice as well
 * as whether or not a marker is holding a die currently.
 * @author Habibullah Aslam, Natalie Rubin
 *
 */
public class Marker {
	private int xLoc;
	private int yLoc;
	private boolean containsDie, isCollided;
	private Die dieContained, dieCollided;
	
	/**
	 * Initializes board, x & y locations of markers, and sets collision
	 * detection and marker containing die detection to false.
	 * 
	 * @param x int that initializes the x location
	 * @param y int that initializes the y location
	 */
	
	public Marker(int x, int y) {
		xLoc = x;
		yLoc = y;
		containsDie = false;
		isCollided = false;
	}
	
	/**
	 * Returns the x location of the current marker.
	 * @return returns x location
	 */
	
	public int getXLoc() {
		return xLoc;
	}

	/**
	 * Returns the y location of the current marker.
	 * @return returns y location
	 */
	
	public int getYLoc() {
		return yLoc;
	}
	
	public void setLocation(int x, int y) {
		xLoc = x;
		yLoc = y;
	}

	public boolean getIsCollided() {
		return isCollided;
	}

	public void setIsCollided(boolean b) {
		isCollided = b;
	}
	
	public boolean getContainsDie() {
		return containsDie;
	}
	
	public void setContainsDie(boolean b) {
		containsDie = b;
	}
	
	public Die getDieCollided(){
		return dieCollided;
	}
	
	public void setDieCollided(Die d) {
		dieCollided = d;
		if(d == null) {
			isCollided = false;
			return;
		}		
		isCollided = true;
	}
	
	public Die getDieContained(){
		return dieContained;
	}
	
	public void setDieContained(Die d) {
		dieContained = d;
		if(d == null) {
			containsDie = false;
			return;
		}
		containsDie = true;	
	}
}