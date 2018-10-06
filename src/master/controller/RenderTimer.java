package master.controller;

import java.util.TimerTask;

import master.view.MiniGame;

/**
 * This is a timer class that has the simple function of rendering the screen
 * This will call the repaint method at the rate specified.
 * This allows for the game logic to not impact the rendering
 */
public class RenderTimer extends TimerTask {
	
	MiniGame game;

	/**
	 * Default constructor
	 */
	public RenderTimer(MiniGame game) {
		this.game = game;
	}

	/**
	 * This will repaint the jpanel
	 * Normally this would be at 30fps
	 */
	@Override
	public void run() {
		game.repaint();
	}	

}
