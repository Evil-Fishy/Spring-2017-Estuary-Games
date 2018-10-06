package master.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import game3.controller.Mini3Timer;
import game3.view.G3Image;
import master.controller.MainController;
import master.controller.MiniChooserTimer;
import master.controller.RenderTimer;
import master.model.Bird;
import master.model.Board;
import master.model.Crab;

/**
 * This view displays three buttons that allow switching between different minigames.
 * This acts as the central hub for the game
 * When a minigame is exited, the master controller will always fallback to this screen
 * This view also has the nice feature that a user can click and drag to move the game around the screen
 */
public class MiniChooser extends MiniGame implements ActionListener, MouseListener, MouseMotionListener {

	public Board board;
	public JButton btnGame1 = new JButton();
	private JButton btnGame2 = new JButton();
	private JButton btnGame3 = new JButton();
	private JButton btnExit = new JButton();
	private JButton btnPlayAllGames = new JButton("Play All Games!");
	private JLabel title = new JLabel("A Day at the Estuary!");
	
	private Point initialClick;
	private static GridBagLayout layout = new GridBagLayout();
    private static GridBagConstraints c = new GridBagConstraints();
    Timer timerMiniChooser;
    Timer timerRender;
    
    private int fontSize;
    private Crab crab;
    private Bird bird;
    private BufferedImage[] crabPics, birdPics;
    private final static int numCrabPics = 4;
    private final static int numHozBirdPics = 5;
    private final static int numVertBirdPics = 4;
    private final static int numTotalBirdPics = numHozBirdPics*numVertBirdPics;
	
	/**
	 * Default constructor
	 * Will add all the buttons
	 */
	public MiniChooser(MainController mainController) {
		// Call the super constructor
		super(mainController);
		board = new Board(mainController.getWidth(), mainController.getHeight());
		
		// Rescale the button images
		rescaleImages();
		loadAnimImages();
		
		crab = new Crab(board, 0, board.getHeight() - GMainImage.CRAB.getHeight());
		bird = new Bird(board, 0, GMainImage.BIRD.getHeight()/numVertBirdPics/2);		
		
		// Add our buttons		
		fontSize = board.getHeight()/20;
		title.setFont(new Font("TimesRoman", Font.BOLD, fontSize));
		title.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		title.setBackground(Color.WHITE);
		title.setOpaque(true);
		
		btnExit.setUI(new ExitButtonUI());

		// Add listeners for each button
		btnGame1.addActionListener(this);
		btnGame2.addActionListener(this);
		btnGame3.addActionListener(this);
		btnExit.addActionListener(this);
		
		// Add mouse listeners for dragging
		this.addMouseListener(this);
		this.addMouseMotionListener(this);		
		
		// Add the button images
		btnGame1.setIcon(new ImageIcon(GMainImage.GAME1_PIC.getImg()));
		btnGame1.setContentAreaFilled(false);
		btnGame2.setIcon(new ImageIcon(GMainImage.GAME2_PIC.getImg()));
		btnGame2.setContentAreaFilled(false);
		btnGame3.setIcon(new ImageIcon(GMainImage.GAME3_PIC.getImg()));
		btnGame3.setContentAreaFilled(false);
	}
	
	/**
	 * Add custom painting, used to render background once for the panel.
	 * This should first call super to render all buttons, and then paint our own images.
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		DrawOps.drawScaledImage(g, GMainImage.BACKGROUND.getImg(), this);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
    	g.drawImage(crabPics[crab.getPicNum()], crab.getXLoc(), crab.getYLoc(), null);
		if(bird.getXVel() > 0)
    		g.drawImage(birdPics[bird.getPicNum()], 
    				bird.getXLoc() + Bird.getWidth(), bird.getYLoc(), -Bird.getWidth(), Bird.getHeight(), null);
    	else
    		g.drawImage(birdPics[bird.getPicNum()], 
    				bird.getXLoc(), bird.getYLoc(), null);
	}
		
	/**
	 * This will compute what size the buttons should be
	 * It will create a button for each of the 3 panels/minigames
	 */
	private void rescaleImages() {
		int worldWidth = mainController.getWidth();
		int worldHeight = mainController.getHeight();
		double newButtonWidth = worldWidth/mainController.getNumGames();
		double scaleFactor = newButtonWidth/GMainImage.GAME1_PIC.getWidth();
		GMainImage.GAME1_PIC.scaleByFactor(scaleFactor);
		GMainImage.GAME2_PIC.scaleByFactor(scaleFactor);
		GMainImage.GAME3_PIC.scaleByFactor(scaleFactor);
		
		int newCrabWidth = worldWidth/9; //crab is 1/9 the screenwidth
		scaleFactor = (double) newCrabWidth/(GMainImage.CRAB.getWidth()/numCrabPics);
		GMainImage.CRAB.scaleByFactor(scaleFactor);
		
		int newBirdWidth = worldWidth/12; //bird is 1/12 screenwidth
		scaleFactor = (double) newBirdWidth/(GMainImage.BIRD.getWidth()/numHozBirdPics);
		GMainImage.BIRD.scaleByFactor(scaleFactor);
		
		int exitBtnWidth = worldWidth/20; //btn is 1/20 screenwidth
		scaleFactor = (double) exitBtnWidth/(GMainImage.EXIT_DEFAULT.getWidth());
		GMainImage.EXIT_DEFAULT.scaleByFactor(scaleFactor);
		GMainImage.EXIT_CLICKED.scaleByFactor(scaleFactor);
		GMainImage.EXIT_HOVER.scaleByFactor(scaleFactor);
	}
	
	/**
	 * This will load the animation frames. All other images are loaded in the
	 * G3Image enum
	 */
	private void loadAnimImages() {
		BufferedImage bufImg;
		
		bufImg = GMainImage.CRAB.getImg();
		int crabPicWidth = bufImg.getWidth()/numCrabPics;
		crabPics = new BufferedImage[numCrabPics];
    	for(int i = 0; i < crabPics.length; i++) {
    		crabPics[i] = bufImg.getSubimage(i*crabPicWidth, 0, crabPicWidth, bufImg.getHeight());    			
    	}
    	
    	bufImg = GMainImage.BIRD.getImg();
		int birdPicWidth = bufImg.getWidth()/numHozBirdPics;
		int birdPicHeight = bufImg.getHeight()/numVertBirdPics;
		birdPics = new BufferedImage[numTotalBirdPics];
    	for(int i = 0; i < numVertBirdPics; i++)
    		for(int j = 0; j < numHozBirdPics; j++)
    			birdPics[j + i*numHozBirdPics] = bufImg.getSubimage(j*birdPicWidth, 
    					i*birdPicHeight, birdPicWidth, birdPicHeight);    
	}
	
	@Override
	public void startup() {
		btnGame1.setEnabled(true);
		btnGame2.setEnabled(true);
		btnGame3.setEnabled(true);
		
		int nOfCells = 3;
        setLayout(layout);
        c.weightx = 0.1;
        c.weighty = 0.1;
        for(int i = 0; i < nOfCells; i++){
            for(int j = 0; j < nOfCells; j++) {    			
                c.gridx = i;
                c.gridy = j;
                if(i == 0 && j == 0) {
                    c.gridwidth = 2;
                	add(title, c);
                    c.gridwidth = 1;
                }
                else if(i == 2 && j == 0) {
                	c.anchor = GridBagConstraints.FIRST_LINE_END;
                	add(btnExit, c);
                	c.anchor = GridBagConstraints.CENTER;
                }
                else if(i == 0 && j == 1) {
                    c.insets = new Insets(0, 10, 0, 10);
                    add(btnGame1, c);
                    c.insets = new Insets(0, 0, 0, 0);
                }
                else if(i == 1 && j == 1) {
                    c.insets = new Insets(0, 10, 0, 10);
                    add(btnGame2, c);
                    c.insets = new Insets(0, 0, 0, 0);
                } 
                else if(i == 2 && j == 1) {
                    c.insets = new Insets(0, 10, 0, 10);
                    add(btnGame3, c);
                    c.insets = new Insets(0, 0, 0, 0);
                }
//                else if(i == 1 && j == 2) {
//                    c.insets = new Insets(10, 10, 10, 10);
//                    c.fill = c.BOTH;
//                    add(btnPlayAllGames, c);
//                    c.fill = c.NONE;
//                    c.insets = new Insets(0, 0, 0, 0);
//                }
                else {
                	add(Box.createRigidArea(new Dimension((int) (board.getWidth()/nOfCells), 
                			(int) (board.getHeight()/nOfCells))), c);
                }
            }
        }      
        
		timerRender = new Timer();
		timerRender.scheduleAtFixedRate(new RenderTimer(this), 0, 50);
		timerMiniChooser = new Timer();
		timerMiniChooser.scheduleAtFixedRate(new MiniChooserTimer(this, board), 0, 50);
	}


	@Override
	public void shutdown() {		
		if(timerRender != null) 
			timerRender.cancel();
		if(timerMiniChooser != null) 
			timerMiniChooser.cancel();
	}
	
	/**
	 * Action perform function
	 * Based on what button is pressed this will call the main controller
	 * And cause it to switch the panels to the correct game
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == btnGame1) {
			btnGame1.setEnabled(false);
			remove(btnGame2);
			remove(btnGame3);
			bird.setGoal(btnGame1.getX() + btnGame1.getWidth()/4, 
					btnGame1.getY() + btnGame1.getHeight()/4);
			bird.setXYVel((bird.getGoalX() - bird.getXLoc())/10, 
					(bird.getGoalY() - bird.getYLoc())/10);
			bird.setHasGoal(true);
			bird.setGameGoal(1);
		}
		else if(event.getSource() == btnGame2) {
			btnGame2.setEnabled(false);
			remove(btnGame1);
			remove(btnGame3);
			bird.setGoal(btnGame2.getX() + btnGame2.getWidth()/4, 
					btnGame2.getY() + btnGame2.getHeight()/4);
			bird.setXYVel((bird.getGoalX() - bird.getXLoc())/10, 
					(bird.getGoalY() - bird.getYLoc())/10);
			bird.setHasGoal(true);
			bird.setGameGoal(2);
		}
		else if(event.getSource() == btnGame3) {
			btnGame3.setEnabled(false);
			remove(btnGame1);
			remove(btnGame2);
			bird.setGoal(btnGame3.getX() + btnGame3.getWidth()/4, 
					btnGame3.getY() + btnGame3.getHeight()/4);
			bird.setXYVel((bird.getGoalX() - bird.getXLoc())/10, 
					(bird.getGoalY() - bird.getYLoc())/10);
			bird.setHasGoal(true);
			bird.setGameGoal(3);
		}
		else if(event.getSource() == btnExit) {
			// Debug message
			System.out.println("System Shutdown Initializing...");
			
			// Shutdown the program
			// http://stackoverflow.com/a/5454119/7718197
			// This will make sure WindowListener.windowClosing() et al. will be called.
		    WindowEvent wev = new WindowEvent(mainController.getFrame(), WindowEvent.WINDOW_CLOSING);
		    Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);

		    // This will hide and dispose the frame, so that the application quits by
		    // Itself if there is nothing else around. 
		    setVisible(false);
		    mainController.getFrame().dispose();

		    // Finally, call this to really exit. 
		    // i/o libraries such as WiiRemoteJ need this. 
		    // Also, this is what swing does for JFrame.EXIT_ON_CLOSE
			System.out.println("System Shutdown Complete");
		    System.exit(0); 
		}
	}

	/**
	 * Allow for the user to drag the window around on this screen
	 * This is just for testing of the application
	 * http://stackoverflow.com/a/13171534/7718197
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
//		// get location of Window
//        int thisX = mainController.getFrame().getLocation().x;
//        int thisY = mainController.getFrame().getLocation().y;
//
//        // Determine how much the mouse moved since the initial click
//        int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
//        int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);
//
//        // Move window to this position
//        int X = thisX + xMoved;
//        int Y = thisY + yMoved;
//        mainController.getFrame().setLocation(X, Y);
	}

	/**
	 * Allow for the user to drag the window around on this screen
	 * This is just for testing of the application
	 * http://stackoverflow.com/a/13171534/7718197
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		initialClick = e.getPoint();
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
	public JLabel getTitle() {
		return title;
	}
	
	public Crab getCrab() {
		return crab;
	}
	
	public static int getNumCrabPics() {
		return numCrabPics;
	}

	public static int getNumHozBirdPics() {
		return numHozBirdPics;
	}

	public static int getNumVertBirdPics() {
		return numVertBirdPics;
	}

	public static int getNumTotalBirdPics() {
		return numTotalBirdPics;
	}

	public Bird getBird() {
		return bird;
	}
}
