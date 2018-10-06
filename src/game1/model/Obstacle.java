package game1.model;

/**
 * This is the top level class for all obstacles.
 * Each obstacle has a position and a velocity.
 */
public class Obstacle {
	
	private double xLoc = 0;
	private double yLoc = 0;
	private double xVelocity = 0;
	private double yVelocity = 1;
    
    /**
     * Default constructor
     */
    public Obstacle() {}
    
    /**
     * Constructor that places the obstacle in the world
     * 
     * @param xloc X-axis location of the obstacle
     * @param yloc y-axis location of the obstacle
     */
    public Obstacle(int xloc, int yloc) {
    	this.xLoc = xloc;
    	this.yLoc = yloc;
    }
    
    /**
     * Nice function that will move the current location
     * of the obstacle down by its current velocity
     */
    public void moveVertical(){
		yLoc+=yVelocity;
	}
    
    /**
     * Nice function that will move the current location
     * of the obstacle over left and right by its current velocity
     */
    public void moveHorizontal(){
		xLoc+=xVelocity;
	}    
	
    /*
     * Getters and setters for location
     */
	public double getYLoc(){
		return yLoc;
	}
	
	public void setYLoc(double yLoc){
		this.yLoc = yLoc;
	}

	public double getXLoc() {
		return xLoc;
	}

	public void setXLoc(double xLoc) {
		this.xLoc = xLoc;
	}
	
	/*
     * Getters and setters for velocity
     */
	public double getyVelocity() {
		return yVelocity;
	}

	public void setyVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}
	
	public double getxVelocity() {
		return xVelocity;
	}

	public void setxVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}
	
}
