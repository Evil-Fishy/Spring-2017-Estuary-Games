package master.model;

import game3.view.MiniThree;
/**
 * 
 * @author natdh, Habibullah Aslam
 *
 *This class controls the arrow indicating to swipe and roll dice
 *in the third minigame. It sets the x and y locations of the arrow, 
 *as well as the current picture used to display the arrow.
 *
 */
public class Arrow {
	private static int picNum;
    private int xLoc, yLoc;
    private int width, height;
    
    /**
     * Initializes picture number and x and y locations of arrow.
     * 
     * @param b Board object that is used to initialize x/y locations
     * @param width int used to initialize x/y locations
     * @param height int used to initialize x/y locations
     * 
     */
    public Arrow(Board b, int width, int height) {
    	picNum = 0;
    	xLoc = (b.getWidth() - width)/2;
    	yLoc = (b.getHeight() - height)/2;
    	this.width = width;
    	this.height = height;
    }
    
    /**
     * Increments picture number used for arrow by moding through
     * the array of pictures using the current picture number being
     * used for the arrow.
     * 
     */
    public void incrPicNum() {
    	picNum = (picNum + 1) % MiniThree.getNumArrowPics();
    }
    
    public int getPicNum() {
    	return picNum;
    }
    
    public void setXYLoc(int x, int y) {
    	xLoc = x;
    	yLoc = y;
    }
    
    public int getXLoc() {
    	return xLoc;
    }

    public int getYLoc() {
    	return yLoc;
    }
    
    public int getWidth() {
    	return width;
    }

    public int getHeight() {
    	return height;
    }
}