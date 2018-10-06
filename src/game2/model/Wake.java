package game2.model;

import game2.view.G2Image;
/**
 * Defines a wake that is generated at a boats location
 * and moves toward the shore, damaging it.
 * 
 * @author Tristan Morris, Christof Wittreich
 *
 */
public class Wake {
	private int xLoc;
	private int yLoc;
	private int width, height;
	private int damage;
	private int speed;
	
	/**
	 * Creates an instance of wake.
	 * 
	 * @param xLoc		The horizontal location of the wake
	 * @param yLoc		The vertical location of the wake
	 * @param width		The width of the image
	 * @param height	The height of the image
	 * @param damage	How much damage the wake deals to the shore
	 * @param speed		How fast the wake moves down the screen
	 */
	public Wake(int xLoc,int yLoc,int width,int height,int damage,int speed){
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		this.damage = damage;
		this.speed = speed;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Moves the wake down, incrementing the yLoc of the wake
	 * by the speed of the wake.
	 */
	public void moveDown(){
		yLoc+=speed;
	}
	
	//getters
	public int getXLoc(){
		return xLoc;
	}
	
	public int getYLoc(){
		return yLoc;
	}
	
	public int getDamage(){
		return damage;
	}
	
	public int getSpeed(){
		return speed;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
