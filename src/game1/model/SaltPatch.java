package game1.model;

/**
 * An obstacle that the player needs to avoid the most. 
 * This is a single SaltPatch which moves
 * down towards the player as the game progresses.
 */
public class SaltPatch extends Obstacle {
	
	private static int imgWidth = 40;
    private static int imgHeight = 40;
	
    /**
	 * Default constructor for a SaltPatch.
	 * Places the SaltPatch at the top of the screen.
	 */
	public SaltPatch() {
		super(0,0);
		setyVelocity(10); 
	}
	
	/**
	 * This will create a SaltPatch at a specified location.
	 * 
	 * @param xloc X-axis location of the salt
	 * @param yloc Y-axis location of the salt
	 */
	public SaltPatch(int x, int y) {
		super(x,y);
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
