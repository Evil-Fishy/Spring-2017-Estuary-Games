package master.model;

import master.model.Board;
import master.view.GMainImage;
import master.view.MiniChooser;

/**
 * Crab object for minichooser.
 * This has a position and a velocity.
 * This crab can be changed to any image needed.
 */
public class Crab {

	private Board board;
	private int xLoc, yLoc;
	private int xVel, yVel;
	private static int width, height;
	private int picNum;
	
    /**
     * Constructor, this will create a crab at
	 * a specific x,y location on the board
	 * 
     * @param b Board instance that has a width and height
     * @param x X-axis location of the crab
     * @param y Y-axis location of the crab
     */
	public Crab(Board b, int x, int y){
		board = b;
		xLoc = x;
		yLoc = y;
		xVel = board.getWidth()/150;
		yVel = 0;
		width = GMainImage.CRAB.getWidth()/MiniChooser.getNumCrabPics();
		height = GMainImage.CRAB.getHeight();
		picNum = 0;
	}
	
	/**
	 * Takes in a new location for the crab
	 * and updates its current position.
	 * 
	 * @param x New x-axis location for the crab
	 */
	public void updateCrab() {
		picNum = (picNum + 1) % MiniChooser.getNumCrabPics();
		xLoc += xVel;
		if(xLoc >= board.getWidth() - width || xLoc < 0)
			xVel = -xVel;
//		yLoc += yVel;
//		if(yLoc >= board.getWorldWidth() - height)
//			xVel = -yVel;
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
		return "This crab is at X: " + xLoc + ", Y: " + yLoc +
				" with width:" + width + " and height:" + height;
	}
}
