package master.model;

import master.model.Board;
import master.view.GMainImage;
import master.view.MiniChooser;

/**
 * Bird object for minichooser.
 * This has a position and a velocity.
 * This bird can be changed to any image needed.
 */
public class Bird {

	private Board board;
	private int xLoc, yLoc;
	private int xVel, yVel;
	private static int width, height;
	private int goalX, goalY;
	private int gameGoal;
	private boolean hasGoal, reachedGoal;
	private int picNum;
	
    /**
     * Constructor, this will create a bird at
	 * a specific x,y location on the board
	 * 
     * @param b Board instance that has a width and height
     * @param x X-axis location of the crab
     * @param y Y-axis location of the crab
     */
	public Bird(Board b, int x, int y){
		board = b;
		xLoc = x;
		yLoc = y;
		xVel = board.getWidth()/150;
		yVel = board.getHeight()/125;
		width = GMainImage.BIRD.getWidth()/MiniChooser.getNumHozBirdPics();
		height = GMainImage.BIRD.getHeight()/MiniChooser.getNumVertBirdPics();
		picNum = 0;
		hasGoal = false; 
		reachedGoal = false;
	}
	
	/**
	 * Takes in a new location for the bird
	 * and updates its current position.
	 * 
	 * @param x New x-axis location for the bird
	 */
	public void updateBird() {
		picNum = (picNum + 1) % MiniChooser.getNumTotalBirdPics();
		if(!hasGoal) {
			xLoc += xVel;
			if(xLoc >= board.getWidth() - width || xLoc < 0)
				xVel = -xVel;
		}
		else {
			if(goalX < xLoc && xVel > 0)
				xVel = -xVel;
			xLoc += xVel;
			yLoc += yVel;
	    	int xDiff = Math.abs(xLoc - goalX), yDiff = Math.abs(yLoc - goalY);
	    	if(xDiff < xVel * 2)
	    		xVel = xDiff/10;
	    	if(yDiff < yVel *2)
	    		yVel = yDiff/10;
			if(xDiff <= 10)
				xVel = 0;
			if(yDiff <= 10)
				yVel = 0;
			if(xVel == 0 && yVel == 0)
				reachedGoal = true;
		}
	}
	
	/*
	 * Getters for x and y location
	 */
	public int getXLoc(){
		return xLoc;
	}
	
	public int getYLoc(){
		return yLoc;
	}
	
	public int getPicNum() {
		return picNum;
	}
	
	/*
	 * Getters for size
	 */
	public static int getWidth(){
		return width;
	}
	
	public static int getHeight(){
		return height;
	}
	
	public String toString() {
		return "This bird is at X:" + xLoc + ", Y:" + yLoc +
				" with xVel:" + xVel + " and yVel:" + yVel;
	}

	public int getXVel() {
		return xVel;
	}
	
	public int getYVel() {
		return yVel;
	}
	
	public void setXYVel(int newXVel, int newYVel) {
		xVel = newXVel;
		yVel = newYVel;
	}
	
	public void setGoal(int goalX, int goalY) {
		this.goalX = goalX;
		this.goalY = goalY;
	}
	
	public boolean hasGoal() {
		return hasGoal;
	}
	
	public void setHasGoal(boolean b) {
		hasGoal = b;
	}
	
	public boolean reachedGoal() {
		return reachedGoal;
	}
	
	public void reachedGoal(boolean b) {
		reachedGoal = b;
	}
	
	public int getGameGoal() {
		return gameGoal;
	}
	
	public void setGameGoal(int i) {
		gameGoal = i;
	}

	public int getGoalX() {
		return goalX;
	}
	
	public int getGoalY() {
		return goalY;
	}
	
	/**
	 * Returns the bird to its default state but preserving its last xLoc.
	 */
	public void resetBirdSpecs() {
		yLoc = GMainImage.BIRD.getHeight()/MiniChooser.getNumVertBirdPics()/2;
		xVel = board.getWidth()/150;
		yVel = board.getWidth()/150;
		width = GMainImage.BIRD.getWidth()/MiniChooser.getNumHozBirdPics();
		height = GMainImage.BIRD.getHeight()/MiniChooser.getNumVertBirdPics();
		hasGoal = false; 
		reachedGoal = false;
	}
}
