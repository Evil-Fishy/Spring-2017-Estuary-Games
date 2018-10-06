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

/**
 * @author Habibullah Aslam
 */
public class SwipeButton extends JButton {
	private static final long serialVersionUID = 4205115064149554798L;
	private int prevX;
    private boolean isSelected;
    private int homeX, homeY, width, height;    
	private int leftInteractBound;
	private int rightInteractBound;
    
	/**
	 * Default constructor
	 * 
	 * @param homeX default x-posn of the button
	 * @param homeY default y-posn of the button
	 * @param width default width of the button
	 * @param height default height of the button
	 */
    public SwipeButton(int homeX, int homeY, int width, int height) {
    	setBtnSize(width, height);
        setProperties();
        this.homeX = homeX;
        this.homeY = homeY;
        this.width = width;
        this.height = height;
        leftInteractBound = width/2 - width/3;
    	rightInteractBound = width/2 + width/3;
    }
    
    /**
     * Sets the size of the button and its initial state. Adds mouselistener as well.
     */
    private void setProperties() {
    	setEnabled(false);
    	isSelected = false;
        setDoubleBuffered(false);
        setMargin(new Insets(0, 0, 0, 0));

        addMouseListener(new MouseAdapter() {        	        	
        	public void mouseExited(MouseEvent e) {
        		resetBtnLoc();
            	isSelected = false;
            }        	
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {        	
        	@Override
        	public void mouseMoved(MouseEvent e) {
        		if(e.getX() >= leftInteractBound && e.getX() <= rightInteractBound) 
        			isSelected = true;
                if(isSelected) {
            		if(prevX > getLocation().x) {            			
            			isSelected = false;
            			resetBtnLoc();
            		}
            		prevX = getLocation().x;
                    setLocation(e.getX() - width/2 + prevX, getLocation().y);
                }
            }
        	        
        	/**
        	 * This method is included in order to support touch screen swiping.
        	 */
        	@Override
            public void mouseDragged(MouseEvent e){
        		mouseMoved(e);
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
     * 
     * @param width width of the button
     * @param height height of the button
     */
    public void setBtnSize(int width, int height) {
    	setSize(width, height);
        setPreferredSize(new Dimension(width, height));
    }

    /**
     * Resets this button's location to the default.
     */
    public void resetBtnLoc() {
    	setLocation(homeX, homeY);
    }
}
