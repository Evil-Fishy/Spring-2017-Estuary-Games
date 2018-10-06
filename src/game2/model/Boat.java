package game2.model;
/**
 * Defines a boat in-game that moves across the screen.
 * 
 * @author Tristan Morris, Christof Wittreich
 *
 */
public class Boat {
	private int xLoc;
	private int yLoc;
	protected int width, height;
	private static int speed = 1;
	private static int defaultSpawnY = 50;

	/**
	 * Creates an instance of Boat, setting the classes fields to the parameters
	 * given. Sets width and height to 10.
	 * 
	 * @param xLoc - The Boat's horizontal location
	 * @param yLoc - The boat's vertical location
	 * @param speed - How fast the boat moves
	 */
	public Boat(int xLoc, int yLoc) {
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		width = 10;
		height = 10;
	}
	
	/**
	 * Increments the boats xLoc parameter by its speed parameter.
	 */
	public void move(){
		xLoc+=speed;
	}
	
	public int getXLoc(){
		return xLoc;
	}
	
	public int getYLoc(){
		return yLoc;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public static int getSpeed() {
		return speed;
	}
	
	public static void setSpeed(int i) {
		speed = i;
	}

	public static int getDefaultSpawnY() {
		return defaultSpawnY;
	}

	public static void setDefaultSpawnY(int y) {
		Boat.defaultSpawnY = y;
	}
}
