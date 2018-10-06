package game3.controller;

import java.util.TimerTask;

import game3.view.G3Image;
import game3.view.MiniThree;
import game3.view.TutPanel;
import master.controller.MainController;

/**
 * 
 * Timer class for the tutPanel. 
 * Updates the models involved in the tutorial.
 * @author Habibullah Aslam
 *
 */
public class TutTimer extends TimerTask {	
	private MiniThree game;
	private TutPanel tutPanel;
	private TutState tutState;
		
	private int fingerXLimit;
	
	private int fadeDelay;

	/**
	 * Default constructor
	 */
	public TutTimer(TutPanel tutPanel, TutState tutState) {	
		this.game = tutPanel.getGame();
		this.tutPanel = tutPanel;
		this.tutState = tutState;
		
		fingerXLimit = tutPanel.getWidth() - 2 * G3Image.FINGER.getWidth();
		fadeDelay = 0;
	}
	
	/**
	 * This will move the objects in the TutPanel.
	 * This happens at a constant rate.
	 */
	@Override
	public void run() {
		if(tutState == TutState.INTRO) {
			//do nothing
		}
		else if(tutState == TutState.SWIPE) {
			if(tutPanel.getFingerXPosn() < fingerXLimit) {
				tutPanel.incrFingerXPosn(G3Image.FINGER.getWidth()/5);
			}
			else {
				fadeDelay++;	
				if(fadeDelay >= 25) {
					tutPanel.shutdown();
				}
			}
		}
		else if(tutState == TutState.ARRANGE_DIE) {
			fadeDelay++;	
			if(fadeDelay >= 30) {
				tutPanel.shutdown();
			}
		}
	}
	
	/**
	 * Puts the buttons controlling the dice back into the panel.
	 */
	public void resetGameDieBtns() {
		MainController.execDelay(50);
		game.addDieButtons();
	}
}