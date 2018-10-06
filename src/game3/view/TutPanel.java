package game3.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Timer;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import game3.controller.TutState;
import game3.controller.TutTimer;
import game3.model.Die;
import game3.model.Mini3State;
import master.view.DrawOps;

/**
 * Defines the tutorial panel that's displayed in Game3.
 * @author Habibullah Aslam
 *
 */
public class TutPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = -5898201063978188740L;
	private MiniThree game;
	private TutState tutState;
	
	//UI stuff
	private JButton btnQuitTut = new JButton("Okay got it!");
	private static GridBagLayout layout = new GridBagLayout();
    private static GridBagConstraints c = new GridBagConstraints();
	private int nOfGridCells = 3;
    private BufferedImage[] dieFacePics;
    
    private int fontSize;
	private boolean isActive;
	private Timer mainTutTimer;
	private TutTimer subTimer;
	
	//Individual tut stage stuff
	private int fingerXPosn;
	private final String introMsg1 = "Welcome to Story Cubes!";
	private final String introMsg2 = "Please consult the help chalkboard button";
	private final String introMsg3 = "on your right whenever you need help.";
	private final String swipeMsg = "Swipe across the screen to roll the story cubes!";
	private final String arrangeDieMsg = "Click and drag cubes into the green boxes!";
	private final String makeStoryMsg1 = "Come up with a story based on the cubes.";
	private final String makeStoryMsg2 = "Here is an example:";
	private final String makeStoryMsg3 = "My Story: The horseshoe crab went to the island.";
	
	/**
     * Default constructor. Sets up the panel and adds listeners.
     * 
     * @param game the game that this panel is in
     * @param state the state of the given game
     */
	public TutPanel(MiniThree game, Mini3State state) {
		setOpaque(false);
		this.game = game;
		dieFacePics = game.getDieFacePics();
		
		btnQuitTut.addActionListener(this);
	}
	
	/**
	 * Starts up the panel and sets up the initial state.
	 */
	public void startup() {
		isActive = true;
		btnQuitTut.setVisible(true);
		if(tutState == TutState.SWIPE || tutState == TutState.INTRO)
			game.remove(game.getBtnSwipe());
		else if(tutState == TutState.ARRANGE_DIE) {
			game.removeDieButtons();
			btnQuitTut.setVisible(false);
		}
		CardLayout clayout = (CardLayout) (game.getCards().getLayout());
		clayout.show(game.getCards(), MiniThree.TUTPANEL);
		game.getBtnTut().setEnabled(false);
		scaleImages();		
		fingerXPosn = 0;
		
		int nOfCells = 3;
        setLayout(layout);
        c.weightx = 0.1;
        c.weighty = 0.1;
        for(int i = 0; i < nOfCells; i++){
            for(int j = 0; j < nOfCells; j++) {    			
                c.gridx = i;
                c.gridy = j;
                if(i == 1 && j == 2) {
                	c.anchor = GridBagConstraints.SOUTH;
                	add(btnQuitTut, c);
                	c.anchor = GridBagConstraints.CENTER;
                }
                add(Box.createRigidArea(new Dimension(getWidth()/nOfGridCells, 
            			getHeight()/nOfGridCells)), c);
            }
        }    
        revalidate();

		fontSize = getHeight()/20;
		btnQuitTut.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
		fontSize = getHeight()/15;
		
		subTimer = new TutTimer(this, tutState);
		mainTutTimer = new Timer();
		mainTutTimer.scheduleAtFixedRate(subTimer, 0, 50);
	}

	/**
	 * Shuts down the panel by removing the buttons associated with the panel.
	 */
	public void shutdown() {
		CardLayout clayout = (CardLayout)(game.getCards().getLayout());
		clayout.show(game.getCards(), MiniThree.STORYPANEL);
		game.getBtnTut().setEnabled(true);
		if(tutState == TutState.SWIPE) {
			game.getMini3Timer().setGameStarted(false);
		}
		else if(tutState == TutState.ARRANGE_DIE) {
			btnQuitTut.setVisible(true);
			subTimer.resetGameDieBtns();
		}
		if(mainTutTimer != null) 
			mainTutTimer.cancel();
		isActive = false;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// Call the super
		super.paintComponent(g);
		if(isActive) {
			g.setFont(new Font("TimesRoman", Font.BOLD, fontSize));
			if(tutState == TutState.INTRO) {
				fontSize = getHeight()/15; //fontSize is 1/15 panelHeight
				g.setColor(Color.WHITE);
				DrawOps.drawScaledImage(g, G3Image.TUT_BOARD.getOrigImg(), this);
				DrawOps.drawHozCenteredString(g, introMsg1, this, getHeight()/4); //display starting 1/4 of the way down from the top
				DrawOps.drawHozCenteredString(g, introMsg2, this, getHeight()/4 + 2*fontSize);
				DrawOps.drawHozCenteredString(g, introMsg3, this, getHeight()/4 + 3*fontSize);
			}
			else if(tutState == TutState.SWIPE) {
				fontSize = getHeight()/15;
				g.setColor(Color.WHITE);
				DrawOps.drawHozCenteredString(g, swipeMsg, this, getHeight()/3);
				g.drawImage(G3Image.FINGER.getImg(), fingerXPosn, getHeight()/2, null);
			}
			else if(tutState == TutState.ARRANGE_DIE) {
				fontSize = getHeight()/15;
				g.setColor(Color.WHITE);
				DrawOps.drawHozCenteredString(g, arrangeDieMsg, this, getHeight()/4);
			}
			else if(tutState == TutState.MAKE_STORY) {
				fontSize = getHeight()/20; //fontSize is 1/20 panelHeight
				g.setColor(Color.WHITE);
				DrawOps.drawScaledImage(g, G3Image.TUT_BOARD.getOrigImg(), this);
				DrawOps.drawHozCenteredString(g, makeStoryMsg1, this, getHeight()/6);
				DrawOps.drawHozCenteredString(g, makeStoryMsg2, this, getHeight()/6 + fontSize);
				
				int numDiceToShow = 2;
				int pnlWidth = getWidth();
				int spacing = (pnlWidth - numDiceToShow*Die.getWidth())/(numDiceToShow + 1);
				spacing += Die.getWidth();
				for(int i = 0; i < numDiceToShow; i++)
					g.drawImage(dieFacePics[i], (i+1)*spacing - Die.getWidth(), getHeight()/6 + 2*fontSize, null);
				DrawOps.drawHozCenteredString(g, makeStoryMsg3, this, getHeight()/6 + 3*fontSize + Die.getHeight());
			}
		}
	}

	/**
	 * Changes the panel based on what button was clicked.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnQuitTut) {
			shutdown();
			if(tutState == TutState.INTRO) {
				tutState = TutState.SWIPE;
				startup();
			}
		}
	}
	
	/**
	 * Scales the images used in this panel.
	 */
	public void scaleImages() {
		int panelWidth = getWidth();
		getHeight();
		
		int newFingerWidth = panelWidth/5; //finger is 1/5 panelwidth
		double scaleFactor = (double) newFingerWidth/G3Image.FINGER.getOrigImg().getWidth();		
		G3Image.FINGER.scaleByFactor(scaleFactor); 
	}
	
	public void setActive(boolean b) {
		isActive = b;
	}

	public boolean getIsActive() {
		return isActive;
	}
	
	public void setTutState(TutState t) {
		tutState = t;
	}
	
	public TutState getTutState() {
		return tutState;
	}
	
	public int getFingerXPosn() {
		return fingerXPosn;
	}
	
	public void incrFingerXPosn(int incr) {
		fingerXPosn += incr;
	}

	public MiniThree getGame() {
		return game;
	}
	
	public TutTimer getTimer() {
		return subTimer;
	}
}
