package game1.model;

/**
 * This class is an obstacle that is an animated fish.
 * This fish should swim left to right on the board.
 */
public class Fish extends Obstacle {
	
	private static int imgWidth = 60;
    private static int imgHeight = 100;
	
	/**
	 * Default constructor for a fish.
	 * Will be placed at the top off screen.
	 */
	public Fish() {
		super(0,0);
		setyVelocity(10);
	}	
	
	/**
	 * This will create a fish in a specific location
	 * 
	 * @param xloc X-axis location of the fish
	 * @param yloc Y-axis location of the fish
	 */
	public Fish(int xloc, int yloc) {
    	super(xloc,yloc);
    }
	
	/*
	 * Getters for image size
	 */
	public static int getImgWidth(){
		return imgWidth;
	}
	
	public static int getImgHeight(){
		return imgHeight;
	}	
}