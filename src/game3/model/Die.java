package game3.model;

import java.util.Random;

import game3.view.DragButton;
import game3.view.G3Image;
import game3.view.MiniThree;
import master.model.*;

/**
 * Represents a die in Game3.
 * Has methods to update the status of the dice, 
 * whether they are rolling or not rolling,
 * and control movement of the die to the arrangement 
 * the user puts them in.
 * 
 */
public class Die {
    public static final Random rng = new Random(); //random number generator for all dicef
	
	private int startVelocity = 50;
	private DragButton button;
	private Board board;
	private int xLoc, yLoc;
	private int xVel, yVel;
	private static int width, height;
    private Direction dir;
    private boolean isStopped, isContained;
    private int facePic; //the final pic that will show on the face of this die
    private Marker contained;
	
    /**
     * 
     * Sets board and x and y location and velocity, as well
     * as direction of die movement. Also allows user to drag die
     * on board.
     * @param board a board that the die is thrown onto
     * @param int the x location the dice will be given
     * @param int the y location the dice will be given
     * 
     */    
	public Die(Board b, int x, int y) {
		board = b;
		xLoc = x;
		yLoc = y;
		xVel = startVelocity;
		yVel = startVelocity;
		this.setDir(Direction.getRandomDirection());
		width = G3Image.DIE.getWidth();
		height = G3Image.DIE.getHeight();
		button = new DragButton();
		button.setEnabled(false);
		button.makeInvisible();
		button.setBtnSize(width, height);
		facePic = rng.nextInt(MiniThree.getNumFacePics());
	}
	
	/**
	 * Updates the status of the dice, updating if the die has
	 * stopped rolling, as well as the direction it is rolling in.
	 * While the die is still rolling, direction is changed to
	 * make the die reflect that it is still rolling.
	 */	
	public void updateDie() {
		int dirChange = 0;
		if(!getIsStopped()) {
			if(isGoingUp()) {
				if(yLoc - yVel <= 0) {
					dirChange += 4;
					if(isGoingRight())
						dirChange -= 2;
					else if(isGoingLeft())
						dirChange += 2;
				}
				else
	    			yLoc -= yVel;
			}
			else if(isGoingDown()) {
	    		if(yLoc + yVel + height >= board.getHeight()) {
					dirChange += 4;
					if(isGoingRight())
						dirChange += 2;
					else if(isGoingLeft())
						dirChange -= 2;
				}
	    		else
	    			yLoc += yVel;
			}
			
			yVel -= 1;
			
			if(isGoingRight()) {
	    		if(xLoc + xVel + width >= board.getWidth()) {
					dirChange += 4;
					if(isGoingUp())
						dirChange += 2;
					else if(isGoingDown())
						dirChange -= 2;
				}
	    		else
	    			xLoc += xVel;
			}
			else if(isGoingLeft()) {
				if(xLoc - xVel <= 0) {
					dirChange += 4;
					if(isGoingUp())
						dirChange -= 2;
					else if(isGoingDown())
						dirChange += 2;
				}
				else
	    			xLoc -= xVel;
			}
			
			xVel -= 1;
		
			if(yVel < 0)
				yVel = 0;
			if(xVel < 0)
				xVel = 0;
			if(xVel == 0 && yVel == 0) {
				isStopped = true;
				button.setLocation(xLoc, yLoc);
			}
		}
		
		setDir(Direction.values()[(getDir().toInt() + dirChange) % Direction.values().length]);
	}
	
	/**
	 * Slowly moves die to given x/y location.
	 * 
	 * @param x int representing x location of die
	 * @param y int representing y location of die
	 */	
	public void slowMoveToLoc(int x, int y) {
    	int xDiff = Math.abs(xLoc - x), yDiff = Math.abs(yLoc - y);
    	int xIncr = xDiff > 10 ? xDiff/10 : 1;
    	int yIncr = yDiff > 10 ? yDiff/10 : 1;
		if(xDiff != 0)
			xLoc += (xLoc < x) ? xIncr : -xIncr;
		if(yDiff != 0)
			yLoc += (yLoc < y) ? yIncr : -yIncr;
	}
	
	/**
     * Returns whether the die is going North.
     * @return whether the die is going North
     */
	public boolean isGoingUp() {
		return getDir().contains('N');
	}

	/**
     * Returns whether the die is going East.
     * @return whether the die is going East
     */
	public boolean isGoingRight() {
		return getDir().contains('E'); 
	}
	
	/**
     * Returns whether the die is going South.
     * @return whether the die is going South
     */
	public boolean isGoingDown() {
		return getDir().contains('S');
	}
	
	/**
     * Returns whether the die is going West.
     * @return whether the die is going West
     */
	public boolean isGoingLeft() {
		return getDir().contains('W'); 
	}
	
	public int getXLoc() {
		return xLoc;
	}

	public int getYLoc() {
		return yLoc;
	}
	
	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}
	
	public void setLocation(int x, int y) {
		xLoc = x;
		yLoc = y;
	}
	
	public int getXVel() {
		return xVel;
	}
	
	public void setXVel(int i) {
		xVel = i;
	}
	
	public int getYVel() {
		return yVel;
	}
	
	public void setYVel(int i) {
		yVel = i;
	}
	
	public DragButton getButton() {
		return button;
	}
	
	public boolean getIsContained() {
		return isContained;
	}
	
	public void setContained(boolean b) {
		isContained = b;
	}

	public Marker getMarker() {
		return contained;
	}
	
	public void setMarker(Marker m) {
		contained = m;
	}
	
	public int getFacePic() {
		return facePic;
	}

	public boolean getIsStopped() {
		return isStopped;
	}

	public void setIsStopped(boolean isStopped) {
		this.isStopped = isStopped;
	}

	public Direction getDir() {
		return dir;
	}

	public void setDir(Direction dir) {
		this.dir = dir;
	}

	public void setFacePic(int i) {
		facePic = i;
	}
}
