/**
 * 
 */
package game3.view;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;

import game3.model.Die;

/**
 * Defines a button that can be dragged with the mouse.
 * Code obtained from http://stackoverflow.com/questions/9036730/java-move-components-with-mouse
 *
 */
public class DragButton extends JButton {
	private static final long serialVersionUID = -4567563716535643900L;
	private volatile int draggedAtX, draggedAtY;
    private boolean hasBeenSelectedOnce, isSelected;
    
    /**
     * Default constructor. Sets the initial properties of the DragButton.
     */
    public DragButton() {
        setProperties();
    }
    
    /**
     * Sets the size of the DragButton and its initial state. Adds mouselistener as well.
     */
    private void setProperties() {
    	hasBeenSelectedOnce = false;
    	isSelected = false;
        setDoubleBuffered(false);
        setMargin(new Insets(0, 0, 0, 0));
        setSize(Die.getWidth(), Die.getHeight());
        setPreferredSize(new Dimension(Die.getWidth(), Die.getHeight()));

        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
            	hasBeenSelectedOnce = true;
            	isSelected = true;
                draggedAtX = e.getX();
                draggedAtY = e.getY();
            }
            
            public void mouseReleased(MouseEvent e) {
            	isSelected = false;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseDragged(MouseEvent e){
                setLocation(e.getX() - draggedAtX + getLocation().x,
                        e.getY() - draggedAtY + getLocation().y);
            }
        });
    }
    
    /**
     * Makes the button invisible.
     */
    public void makeInvisible() {
    	setBorderPainted(false);
    	setBorder(null);
    	setContentAreaFilled(false);
    }
    
    /**
     * Sets the button size to the given width and height.
     * @param width width of the button
     * @param height height of the button
     */
    public void setBtnSize(int width, int height) {
    	setSize(width, height);
        setPreferredSize(new Dimension(width, height));
    }
    
    public boolean getHasBeenSelectedOnce() {
    	return hasBeenSelectedOnce;
    }
    
    public void setHasBeenSelectedOnce(boolean b) {
    	hasBeenSelectedOnce = b;
    }
    
    public boolean getIsSelected() {
    	return isSelected;
    }
    
    public void setIsSelected(boolean b) {
    	isSelected = b;
    }
    
    /**
     * Incrementally moves the button to the given location.
     * 
     * @param x destination x location
     * @param y destination y location
     */
    public void slowMoveToLoc(int x, int y) {
    	int xLoc = getX(), yLoc = getY();
    	int xDiff = Math.abs(xLoc - x), yDiff = Math.abs(yLoc - y);
    	int xIncr = xDiff > 10 ? xDiff/10 : 1;
    	int yIncr = yDiff > 10 ? yDiff/10 : 1;
		if(xDiff != 0)
			xLoc += (xLoc < x) ? xIncr : -xIncr;
		if(yDiff != 0)
			yLoc += (yLoc < y) ? yIncr : -yIncr;
		setLocation(xLoc, yLoc);
	}
}