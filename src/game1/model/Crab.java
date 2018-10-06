package game1.model;

import master.model.Board;

/**
 * Crab object for minigame 1.
 * This has a position and a velocity.
 * This crab can be changed to any image needed.
 */
public class Crab {
	
	private int xLoc;
	private int yLoc;
	private int xvelocity;
	private int yvelocity;
	private Board board;
	
    private static int imgWidth = 66;
    private static int imgHeight = 33;
	
    /**
     * Constructor, this will create a crab at
	 * a specific x,y location in the board
	 * 
     * @param b Board instance that has a width and height
     * @param x X-axis location of the crab
     * @param y Y-axis location of the crab
     */
	public Crab(Board b, int x, int y){
		board = b;
		xLoc = x;
		yLoc = y;
	}
	
	/**
	 * Takes in a new location for the crab
	 * And updates my current position
	 * 
	 * @param x New x-axis location for the crab
	 */
	public void updateCrab(int x) {
		this.xLoc = x;
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
	
	/*
	 * Getters for image size
	 */
	public static int getImgWidth(){
		return imgWidth;
	}
	
	public static int getImgHeight(){
		return imgHeight;
	}
	
	/**
	 * Setter for the size of the crab image
	 * 
	 * @param x Width of the image
	 * @param y Height of the image
	 */
	public static void setImgSize(int x, int y) {
		imgWidth = x;
		imgHeight = y;
	}
	
}
