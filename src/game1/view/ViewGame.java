package game1.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import game1.model.Crab;
import game1.model.Fish;
import game1.model.Obstacle;
import game1.model.SaltPatch;
import game3.view.G3Image;
import master.model.Bird;
import master.view.DrawOps;
import master.view.GMainImage;


/**
 * View that displays the main game
 * This view displays the crab and the moving obstacles
 * It is important that the main timer is started while this is shown
 */
public class ViewGame extends JPanel {
		
	private MiniOne game;
	
	// Image data, will hold animation frames for crab movement
	private int frameCount = 0;
	private final int frameCountForCrabAnim = 6;
    private BufferedImage[] crabAnimFrames = new BufferedImage[frameCountForCrabAnim];

    private BufferedImage[] arrowPics;
    private final static int numArrowPics = 10;
    private int fontSize;
    
	/**
	 * Default constructor
	 * @param game The minigame object
	 */
	public ViewGame(MiniOne game) {
		// Set our game
		this.game = game;
		// Load all the images
		loadImages();
		scaleScreenItems();
	}
	
	
	/**
	 * Add custom painting, used to render background once for the panel
	 * Will render red when we just had a collision
     * Should draw the crab and other elements on the screen
	 */
	@Override
	protected void paintComponent(Graphics g) {
		
		// Call super
		super.paintComponent(g);
		
		// Change the background based on the state
		if(!game.getState().isCollided)
			DrawOps.drawCustomTiledImage(g, G1Image.BACKGROUND.getImg(), this, 0, 1, true, 0, -game.getState().board.getHeight() + game.getState().backgroundPosn);
		else
			DrawOps.drawCustomTiledImage(g, G1Image.BACKGROUND_DMG.getImg(), this, 0, 1, true, 0, -game.getState().board.getHeight() + game.getState().backgroundPosn);
		
		// Draw the current crab
		frameCount = (frameCount + 1) % frameCountForCrabAnim;
    	g.drawImage(crabAnimFrames[frameCount], game.getState().crab.getXLoc(), game.getState().crab.getYLoc(), this);
    	
    	// Check if we are in the end game mode
    	// If we are draw the animated background
		if(game.getState().isEnd) {
			// Draw background
			g.drawImage(G1Image.BACKGROUND_ESTUARY.getImg(), 0, -game.getState().board.getHeight() + game.getState().endingPosn, getSize().width, getSize().height, this);
			// Draw text here
    		String str = "You Made It To The Estuary!";
    		g.setColor(Color.WHITE);
    		g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
    		FontMetrics fm = g.getFontMetrics();
            Rectangle2D r = fm.getStringBounds(str, g);
            int x = (crabAnimFrames[frameCount].getWidth() - (int) r.getWidth()) / 2 + game.getState().crab.getXLoc();
            g.drawString(str, x, game.getState().crab.getYLoc() - 10);
		}
    	
    	// Draw tutorial things
    	if(game.getState().isTutorial) {
    		// Draw right arrow
    		g.drawImage(arrowPics[game.getState().arrow.getPicNum()], 
    				game.getState().arrow.getXLoc(), game.getState().arrow.getYLoc(), null);
    		// Draw left arrow
    		g.drawImage(arrowPics[game.getState().arrow.getPicNum()], 
    				game.getState().arrow.getXLoc() - Crab.getImgWidth(), 
    				game.getState().arrow.getYLoc(), -G1Image.ARROWS.getWidth(), G1Image.ARROWS.getHeight()/numArrowPics, null);
    		
    		// Draw help text here
    		String str = "Avoid the Salt Walls and Fish!";
    		g.setColor(Color.WHITE);
    		g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
    		FontMetrics fm = g.getFontMetrics();
            Rectangle2D r = fm.getStringBounds(str, g);
            int x = (crabAnimFrames[frameCount].getWidth() - (int) r.getWidth()) / 2 + game.getState().crab.getXLoc();
            g.drawString(str, x, game.getState().crab.getYLoc() - 10);
    	}
    	
    	
    	// Draw all obstacles
    	for(Obstacle ob : game.getState().obstacles) {
    		if(ob instanceof SaltPatch) g.drawImage(G1Image.SALT.getImg(), (int)ob.getXLoc(), (int)ob.getYLoc(), this);
    		else if(ob instanceof Fish) g.drawImage(G1Image.FISH.getImg(), (int)ob.getXLoc(), (int)ob.getYLoc(), this);
    	}
    	
    	// Get the current elapsed time
    	long elapsedTime = System.currentTimeMillis() - game.getState().startTime;
    	DecimalFormat roundedTime = new DecimalFormat("#.##");
    	String value = roundedTime.format((double)elapsedTime/1000);
    	g.setColor(Color.BLACK);
    	g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
    	g.drawImage(G1Image.TIMER.getImg(), 5, 5, null);
    	g.drawString(value, 6 + G1Image.TIMER.getWidth()/4, 6 + 2*G1Image.TIMER.getHeight()/3);
    	
    	// Show the current penalty
		g.setColor(Color.RED);
    	g.setFont(new Font("TimesRoman", Font.BOLD, fontSize - 5));
    	g.drawString("+"+game.getState().secPenalty, 12 + G1Image.TIMER.getWidth()*3/4, 6 + 2*G1Image.TIMER.getHeight()/3);
	
	}
	
    
    /**
	 * This will load all animation frames for the objects. 
	 * Static images are loaded in the G1Image enum and can be referenced easily from there.
	 */
	private void loadImages() {
		// Directory our crab animation frames are in
		String directory = new String("resources/game1/crab_animation/");
		BufferedImage bufImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		// Loop through the 6 animation frames
		for(int i=1; i<=6; i++) {
			try {
				crabAnimFrames[i-1] = ImageIO.read(new File(directory + "crab_0"+i+".png")); 		
			} catch(IOException e) {
				System.err.println("Couldn't find " + directory + "crab_0"+i+".png");
				e.printStackTrace();
				System.exit(ERROR);
			}
		}
		
		bufImg = G1Image.ARROWS.getImg();
		int arrowImgHeight = bufImg.getHeight()/numArrowPics;
		arrowPics = new BufferedImage[numArrowPics];
    	for(int i = 0; i < arrowPics.length; i++) {
    		arrowPics[i] = bufImg.getSubimage(0, i*arrowImgHeight, bufImg.getWidth(), arrowImgHeight);    			
    	}	
	}	

	/**
	 * This scales all the graphics to the screen size
	 * Right now this just scales the crab respective to the screen
	 */
	private void scaleScreenItems() {
		int worldWidth = game.getState().board.getWidth();
		int worldHeight = game.getState().board.getHeight();
		double crabScaleFactor = (double)(worldWidth/16) / crabAnimFrames[0].getWidth();
		// Debug
		//System.out.println("World fraction = " + worldWidth/10);
		//System.out.println("Crab width = " + crabAnimFrames[0].getWidth());
		//System.out.println("Scaling factor = " + crabScaleFactor);
		// Scale all the images
		for(int i=0; i<6; i++) {
			crabAnimFrames[i] = DrawOps.scaleImgByFactor(crabAnimFrames[i], crabScaleFactor);
		}
		// Save the size of the crab
		Crab.setImgSize(crabAnimFrames[0].getWidth(), crabAnimFrames[0].getHeight());
		// Scale the timer
		double newTimerWidth = game.getState().board.getWidth()/5;
		double scaleFactor = newTimerWidth/G1Image.TIMER.getWidth();
		G1Image.TIMER.scaleByFactor(scaleFactor);
		// Scale the arrow
		G1Image.ARROW.scaleByFactor(scaleFactor);
		// Scale the font size
		fontSize = game.getState().board.getHeight()/25;
		
		//scale arrow frames
		int newArrowWidth = worldWidth/10;
		scaleFactor = (double) newArrowWidth/(G1Image.ARROWS.getWidth());
		scaleFactor = (double) Math.ceil(scaleFactor * 10) / 10;
		G1Image.ARROWS.scaleByFactor(scaleFactor);
		for(int i = 0; i < arrowPics.length; i++) {
    		arrowPics[i] = DrawOps.scaleImgByFactor(arrowPics[i], scaleFactor);    			
    	}			
	}		
	
	public static int getNumArrowPics() {
		return numArrowPics;
	}
}
