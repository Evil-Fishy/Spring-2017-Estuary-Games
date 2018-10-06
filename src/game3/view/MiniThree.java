package game3.view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import game3.controller.Mini3CollisionDetection;
import game3.controller.Mini3Timer;
import game3.controller.TutState;
import game3.model.Die;
import game3.model.Marker;
import game3.model.Mini3State;
import master.controller.MainController;
import master.controller.RenderTimer;
import master.model.Arrow;
import master.model.Board;
import master.view.DrawOps;
import master.view.ExitButtonUI;
import master.view.MiniGame;

/**
 * Defines the basic setup and painting for MiniGame3.
 * 
 * @author Habibullah Aslam
 *
 */
@SuppressWarnings("serial")
public class MiniThree extends MiniGame implements ActionListener {
	private static Board board;
	private StoryPanel storyPanel;
	private TutPanel tutPanel;
	private ViewStories viewStories;
	private JPanel cards = new JPanel(new CardLayout());
	public final static String STORYPANEL = "Story Panel";
	public final static String TUTPANEL = "Tut Panel";
	public final static String VIEWSTORIES = "View Stories";
	
	private JButton btnExit = new JButton();
	private JButton btnMakeStory = new JButton("I'm ready to make a story!");
	private JButton btnTut = new JButton();
	
	private SwipeButton btnSwipe;
	
	private static Random rng = new Random();
	private static GridBagLayout layout = new GridBagLayout();
    private static GridBagConstraints c = new GridBagConstraints();
	private int nOfGridCells = 7;
    
    private final static int frameCountForRolling = 1;
    
    // Data
    Mini3State state;
    private BufferedImage[] dieRollingPics;
    private BufferedImage[] dieFacePics;
    private int numFacePics = 12;
    
    private BufferedImage[] arrowPics;
    private final static int numArrowPics = 10;
    
    private int fontSize;
    
    private Timer timerMini3;
    private Timer timerRender;
    private Mini3Timer subTimer;

	/**
	 * Default constructor
	 */
    
	public MiniThree(MainController mainController) {
		// Call the super constructor
		super(mainController);	
		state = new Mini3State();
		state.setBoard(new Board(mainController.getWidth(), mainController.getHeight()));
		board = state.getBoard();
		
		scaleScreenItems();	
		loadAnimImages();		
		
		viewStories = new ViewStories(this, state);
		storyPanel = new StoryPanel(this, state);
		tutPanel = new TutPanel(this, state);
		tutPanel.setTutState(TutState.INTRO);
		
		cards.setOpaque(false);
		cards.add(storyPanel, STORYPANEL);
		cards.add(tutPanel, TUTPANEL);
		cards.add(viewStories, VIEWSTORIES);
		
		fontSize = board.getHeight()/25;
		
		//set dimensions of button to be 1/3 the width and the full height of the board
		btnSwipe = new SwipeButton(0, 0, board.getWidth()/3, 
				board.getHeight());
		btnSwipe.makeInvisible();	
		
		btnMakeStory.setVisible(false);
		btnMakeStory.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
		btnMakeStory.addActionListener(this);

		btnExit.setUI(new ExitButtonUI());
		btnExit.addActionListener(this);	
		
		btnTut.setIcon(new ImageIcon(G3Image.TUT_BOARD.getImg()));
		btnTut.setContentAreaFilled(false);
		btnTut.addActionListener(this);
		
        setLayout(layout);
        c.weightx = 0.1;
        c.weighty = 0.1;
        for(int i = 0; i < nOfGridCells; i++){
            for(int j = 0; j < nOfGridCells; j++) {    			
                c.gridx = i;
                c.gridy = j;
                if(i == 6 && j == 0) {
                	c.anchor = GridBagConstraints.FIRST_LINE_END;
                	add(btnExit, c);
                	c.anchor = GridBagConstraints.CENTER;
                }
                else if(i == 1 && j == 1) {
                	c.gridwidth = 5;
                	c.gridheight = 5;
        			c.fill = GridBagConstraints.BOTH;
        			add(cards, c);
        			c.fill = GridBagConstraints.NONE;
                	c.gridwidth = 1;
                	c.gridheight = 1;
                }
                else if (i == 6 && j == 1) {
                	add(btnTut, c);
                }
                else if(i == 2 && j == 5) {
                	c.gridwidth = 3;
        			c.fill = GridBagConstraints.HORIZONTAL;
        			add(btnMakeStory, c);
        			c.fill = GridBagConstraints.NONE;
                	c.gridwidth = 1;
                }
                else {
                	add(Box.createRigidArea(new Dimension(board.getWidth()/nOfGridCells, 
                			board.getHeight()/nOfGridCells)), c);
                }
            }
        }           
								
		state.arrow = new Arrow(board, 
				G3Image.ARROWS.getWidth(), 
				G3Image.ARROWS.getHeight()/numArrowPics);
	}
	
	/**
	 * Renders all the elements on this panel, including the background,
	 * dice, and initial arrow. 
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		DrawOps.drawTiledImage(g, G3Image.BACKGROUND.getImg(), this);
		
    	//paints placement markers for you to drag dice into
    	if(!storyPanel.getIsActive() && state.getAllDiceStopped()) {
    		for(Marker m : state.getMarkers())
    			if(m.getIsCollided())
    				g.drawImage(G3Image.MARKER_SET.getImg(), m.getXLoc(), m.getYLoc(), this);
    			else
    				g.drawImage(G3Image.MARKER.getImg(), m.getXLoc(), m.getYLoc(), this);
    	}
    	
    	//paints arrow
    	if(!state.getDiceRolled())
    		g.drawImage(arrowPics[state.arrow.getPicNum()], 
    				state.arrow.getXLoc(), state.arrow.getYLoc(), null);
	}
	
	/**
     * This will be called when this JPanel needs to repaint.
     * This is only needed to draw stuff over all other panels, buttons, etc.
     * on the screen.
     */
	public void paint(Graphics g) {		
    	// Call super
    	super.paint(g);
    	
    	BufferedImage img;
    	//paints the dice themselves
    	if(!(tutPanel.getIsActive() || viewStories.getIsActive()))
	    	if(state.getDiceRolled()) {
		    	for(Die d : state.getDice()) {
		    		img = dieFacePics[d.getFacePic()];
		    		if(d.getButton().getIsSelected())
		    			img = DrawOps.scaleImgByFactor(dieFacePics[d.getFacePic()], 1.25);
		    		g.drawImage(img, d.getXLoc(), d.getYLoc(), this);
		    		
		    	}
	    	}
    }	
	
	/**
	 * Starts up the game and sets up the initial state.
	 */
	@Override
	public void startup() {		
		// Add state.getDice()
    	state.setDice(new ArrayList<Die>());
    	Die d2;
    	for(int i = 0; i < Mini3State.numDice; i++) {
    		d2 = null;
			int xLoc = rng.nextInt(board.getWidth() - G3Image.DIE.getWidth());
			int yLoc = rng.nextInt(board.getHeight() - G3Image.DIE.getHeight());
	        Die d = new Die(board, xLoc, yLoc);
	        d2 = Mini3CollisionDetection.checkDieCollidedDice(d, state.getDice());
	        if(d2 == null)
				state.getDice().add(d);
	        else
	        	i--;
    	}
    	
    	int spacing = (board.getWidth() - 
    			Mini3State.numDice*Die.getWidth())/(Mini3State.numDice + 1);
		spacing += Die.getWidth();
		state.setMarkers(new ArrayList<Marker>());
		for(int i = 0; i < Mini3State.numDice; i++)
			state.getMarkers().add(new Marker((i+1)*spacing - Die.getWidth(), 
					(board.getHeight() - G3Image.MARKER.getHeight())/2));
    	
    	state.setDiceRolled(false);
    	state.setAllDiceStopped(false);
    	state.setAllDiceSet(false);

		tutPanel.startup();
		
		timerRender = new Timer();
		timerRender.scheduleAtFixedRate(new RenderTimer(this), 0, 50);
		subTimer = new Mini3Timer(this, state);
		timerMini3 = new Timer();
		timerMini3.scheduleAtFixedRate(subTimer, 0, 50);
				
	}

	/**
	 * Shuts down the game by resetting the game state.
	 */
	@Override
	public void shutdown() {		
		if(timerRender != null) 
			timerRender.cancel();
		if(timerMini3 != null) 
			timerMini3.cancel();
		
		removeDieButtons();
    	state.setDice(null);
		state.setMarkers(null);
		
		state.setDiceRolled(false);
		state.setAllDiceStopped(false);
    	state.setAllDiceSet(false);
    	storyPanel.shutdown();
		btnMakeStory.setVisible(false);

		tutPanel.setTutState(TutState.INTRO);
		tutPanel.shutdown();
		remove(btnSwipe);
	}
	
	/**
	 * Called when the exit button is pressed
	 * Should cleanup logic, and call the exit controller
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == btnExit){
			mainController.exitMini();
			storyPanel.resetInputArea();
		}
		else if(event.getSource() == btnMakeStory) {
			storyPanel.startup();
			storyPanel.setActive(true);
			btnMakeStory.setVisible(false);
			tutPanel.setTutState(TutState.MAKE_STORY);
		}
		else if(event.getSource() == btnTut) {
			tutPanel.startup();
		}
	}
	
	/**
	 * Scales the images in the game.
	 */
	private void scaleScreenItems() {
		int worldWidth = board.getWidth();
		//int worldHeight = board.getHeight();
		
		int newDieWidth = (worldWidth/(Mini3State.numDice * 2)); //die is screenWidth/(twice the numDice)
		double scaleFactor = (double) newDieWidth/G3Image.DIE.getWidth();
		
		G3Image.DIE.scaleByFactor(scaleFactor);
		G3Image.DIE_FRAMES.scaleByFactor(scaleFactor);
		G3Image.MARKER.scaleByFactor(scaleFactor);
		G3Image.MARKER_SET.scaleByFactor(scaleFactor);
		
		int newArrowWidth = worldWidth/3; //arrow is 1/3 screenwidth
		scaleFactor = (double) newArrowWidth/G3Image.ARROWS.getWidth();
		scaleFactor = (double) Math.ceil(scaleFactor * 10) / 10;
		G3Image.ARROWS.scaleByFactor(scaleFactor);
		
		int newTutWidth = worldWidth/(2 * nOfGridCells); //tut board is 1/(twice the nOfGridCells) screenwidth
		scaleFactor = (double) newTutWidth/G3Image.TUT_BOARD.getWidth();
		G3Image.TUT_BOARD.scaleByFactor(scaleFactor);
	}

	/**
	 * This will load the animation frames. All other images are loaded in the
	 * G3Image enum.
	 */
	private void loadAnimImages() {
		String directory = new String("resources/game3/");
    	String filePath = directory;
		BufferedImage bufImg;
		
		bufImg = G3Image.ARROWS.getImg();
		int arrowImgHeight = bufImg.getHeight()/numArrowPics;
		arrowPics = new BufferedImage[numArrowPics];
    	for(int i = 0; i < arrowPics.length; i++) {
    		arrowPics[i] = bufImg.getSubimage(0, i*arrowImgHeight, bufImg.getWidth(), arrowImgHeight);    			
    	}		
		
		bufImg = G3Image.DIE_FRAMES.getImg();  	
    	dieRollingPics = new BufferedImage[frameCountForRolling];
    	for(int i = 0; i < dieRollingPics.length; i++)
    		dieRollingPics[i] = bufImg.getSubimage(i*0, 0, G3Image.DIE.getWidth(), G3Image.DIE.getHeight());    			
	    	
	    try {
	    	directory += "die_faces/";
	    	dieFacePics = new BufferedImage[numFacePics];
	    	double facePicScaleFactor = 0.7;
	    	for(int i = 0; i < dieFacePics.length; i++) {
	    		filePath = directory + "face (" + (i+1) + ").png";
	    		bufImg = ImageIO.read(new File(filePath));
	    		bufImg = DrawOps.scaleImgToSize(bufImg, 
	    				(int) (G3Image.DIE.getWidth() * facePicScaleFactor), 
	    				(int) (G3Image.DIE.getHeight() * facePicScaleFactor));
	    		bufImg = DrawOps.overlayImgsInCenter(bufImg, G3Image.DIE.getImg());
	    		dieFacePics[i] = bufImg;
	    	}
	    } catch (IOException e) {
	    	System.err.println("Couldn't find " + filePath);
			e.printStackTrace();
		}
	}
		
	public StoryPanel getStoryPanel() {
		return storyPanel;
	}
	
	public TutPanel getTutPanel() {
		return tutPanel;
	}
	
	public ViewStories getViewStories() {
		return viewStories;
	}
	
	public JPanel getCards() {
		return cards;
	}
	
	public Mini3Timer getMini3Timer() {
		return subTimer;
	}
	
	public SwipeButton getBtnSwipe() {
		return btnSwipe;
	}
	
	public static int getNumArrowPics() {
		return numArrowPics;
	}
	
	public static int getNumFacePics() {
		return numArrowPics;
	}
	
	/**
	 * Removes the buttons associated with the dice from this panel.
	 */
	public void removeDieButtons() {
		for(Die d : state.getDice())
			remove(d.getButton());
	}

	/**
	 * Adds the buttons associated with the dice from this panel.
	 */
	public void addDieButtons() {
		for(Die d : state.getDice()) {
			add(d.getButton());
		}
	}
	
	/**
	 * Checks whether the given die's facePic is unique. 
	 * If not, it recurses until it has a unique facePic.
	 * 
	 * @param d the die to check
	 * @return the facePic of the die
	 */
	public int checkUniqueFacePic(Die d) {
		boolean isUnique = true;
		for(Die die : state.getDice()) {
			if(die.getFacePic() == d.getFacePic() && die != d)
				isUnique = false;
		}
		if(isUnique)
			return d.getFacePic();
		else {
			d.setFacePic((d.getFacePic() + 1)%numFacePics);
			return checkUniqueFacePic(d);
		}
	}		

	public JButton getBtnMakeStory() {
		return btnMakeStory;
	}
	
	public JButton getBtnTut() {
		return btnTut;
	}
	
	public BufferedImage[] getDieFacePics() {
		return dieFacePics;
	}

	public Mini3State getState() {
		return state;
	}
}
