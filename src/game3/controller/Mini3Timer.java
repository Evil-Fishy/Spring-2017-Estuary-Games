package game3.controller;

import java.util.TimerTask;

import javax.swing.JButton;

import game3.model.Die;
import game3.model.Marker;
import game3.model.Mini3State;
import game3.view.MiniThree;
import game3.view.StoryPanel;
import game3.view.SwipeButton;
import game3.view.TutPanel;
import master.controller.MainController;
import master.model.Board;

/**
 * 
 * The timer for Minigame 3 that updates the models at a fixed rate.
 * 
 * @author Habibullah Aslam
 *
 */
public class Mini3Timer extends TimerTask {
	
	private MiniThree game;
	private Mini3State state;
	private Board board;
	private StoryPanel storyPanel;
	private TutPanel tutPanel;
	
    private final int faceSwapDelayLength = 4;
    private int faceSwapDelaySoFar = 0;
	
	private SwipeButton btnSwipe;
	private JButton btnMakeStory;
	public static int btnSwipeGoalX; //where the btnSwipe needs to be to roll the dice
	
	private int arrowFrameDelaySoFar = 0;
	private int arrowFrameDelayLength = 1;
	private boolean gameStarted;

	/**
	 * Default constructor
	 */
	public Mini3Timer(MiniThree game, Mini3State state) {
		this.game = game;
		this.state = state;
		board = state.getBoard();
		gameStarted = false;
		btnSwipe = game.getBtnSwipe();
		btnSwipeGoalX = 4 * board.getWidth()/5 - btnSwipe.getWidth()/2;
		storyPanel = game.getStoryPanel();
		tutPanel = game.getTutPanel();
		btnMakeStory = game.getBtnMakeStory();		
	}
	
	/**
	 * Manages updates of all models in Game3.
	 */
	@Override
	public void run() {
		if(!tutPanel.getIsActive()) {
			if(!gameStarted) {
				//delay needed to allow component validation prior to adding btn at custom loc
				MainController.execDelay(50);
				game.add(btnSwipe);
				btnSwipe.setLocation(0,0);
				gameStarted = true;
			}
			else if(btnSwipe.getLocation().x > btnSwipeGoalX){
				state.setDiceRolled(true);
				game.remove(btnSwipe);
			}
		}

		if(!storyPanel.getIsActive()) {
			if(state.getDiceRolled()) {
	    		game.getTutPanel().setTutState(TutState.ARRANGE_DIE);
				updateDice();
			}
			else {
				arrowFrameDelaySoFar = (arrowFrameDelaySoFar + 1) % arrowFrameDelayLength;
				if(arrowFrameDelaySoFar == 0)		
					state.arrow.incrPicNum();
			}
			
			if(state.getAllDiceStopped()) {
				updateMarkers();
				checkDiceSet();
			}
			
			if(state.getAllDiceSet() && !btnMakeStory.isVisible()) {
				game.removeDieButtons();
				btnMakeStory.setVisible(true);
				MainController.execDelay(50);
				game.addDieButtons();
			}
			else if(!state.getAllDiceSet() && btnMakeStory.isVisible()) {
				game.removeDieButtons();
				btnMakeStory.setVisible(false);
				MainController.execDelay(50);
				game.addDieButtons();
			}
		}
		else
			for(int i = 0; i < state.getDice().size(); i++)
				state.getDice().get(i).slowMoveToLoc(state.storyPnlDiceXPosns[i], storyPanel.getDieYPosnOnStoryPanel());
	}	
	
	/**
	 * Updates the dice in Game3 by moving them and swapping the dice faces when appropriate.
	 */
	public void updateDice() {
		Marker m;
		//prevent inital die-marker collision
		for(Die d : state.getDice()) {
			m = Mini3CollisionDetection.checkDieCollidedMarkers(d, state.getMarkers());
			if(m != null && !d.getButton().getHasBeenSelectedOnce()) {
				if(d.getYLoc() < m.getYLoc())
					d.getButton().slowMoveToLoc(d.getXLoc(), m.getYLoc() - 2 * Die.getHeight());
				else
					d.getButton().slowMoveToLoc(d.getXLoc(), m.getYLoc() + 2 * Die.getWidth());
			}	
		}
		
		if(!state.getAllDiceStopped()) {
			faceSwapDelaySoFar = (faceSwapDelaySoFar + 1) % faceSwapDelayLength;
			
			state.setAllDiceStopped(true);
	    	for(Die d : state.getDice()) {
	    		d.updateDie();    			
	    		if(!d.getIsStopped()) {
	    			if(faceSwapDelaySoFar == 0)	{
	    				d.setFacePic(Die.rng.nextInt(MiniThree.getNumFacePics()));
	    				d.setFacePic(game.checkUniqueFacePic(d));
	    			}
	    			state.setAllDiceStopped(false);
	    		}
	    	}
	    	if(state.getAllDiceStopped()) {
	    		//this loop is called once after all die have stopped rolling. 
	    		//This will activate the "click-draggability" of the dice.
	    		//delay needed to allow component validation prior to adding btn at custom loc
	    		MainController.execDelay(50);
	    		for(Die d : state.getDice()) {
	        		game.add(d.getButton());
	    			d.getButton().setEnabled(true);
	    		}
	    	}
		}
		else {
	    		for(Die d : state.getDice()) {
	    			d.setLocation(d.getButton().getX(), d.getButton().getY());
	    		}
    	}    	
    }
	
	/**
	 * Updates the state of the markers based on whether a die has hit it or not.
	 */
	public void updateMarkers() {		
		Die d;
		for(Marker m : state.getMarkers()) {		
			d = null;
			if(!m.getContainsDie()) {
				d = Mini3CollisionDetection.checkMarkerCollidedDice(m, state.getDice());
				if(d != null) {
					if(d.getButton().getIsSelected()) {
						d.setContained(false);
						m.setIsCollided(true);
					}
					// If we reach here, then the die has been dropped by the player into a marker. 
					// Now we check to make sure it didn't land on given Marker m randomly 
					// (ie the player has picked up this die before dropping it) 
					else if(d.getButton().getHasBeenSelectedOnce()) {
						if(!d.getIsContained()) {
							d.setContained(true);
							m.setDieContained(d);
						}
					}
				}
				else
					m.setIsCollided(false);
			}
			else if(!m.getDieContained().getButton().getIsSelected())
				m.getDieContained().getButton().slowMoveToLoc(m.getXLoc(), m.getYLoc());
			else
				m.setDieContained(null);
		}
	}
	
	/**
	 * Checks whether all the dice have been placed into all the markers.
	 */
	public void checkDiceSet() {
		for(Marker m : state.getMarkers())
			if(!m.getContainsDie()) {
				state.setAllDiceSet(false);
				return;
			}
		state.setAllDiceSet(true);
	}
	
	public boolean getGameStarted() {
		return gameStarted;
	}
	
	public void setGameStarted(boolean b) {
		gameStarted = b;
	}		

	public SwipeButton getBtnSwipe() {
		return btnSwipe;
	}
}




