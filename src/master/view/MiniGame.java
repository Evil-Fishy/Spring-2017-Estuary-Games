package master.view;

import javax.swing.JPanel;

import master.controller.MainController;

/**
 * This is the super class for all mini-games
 * All mini-games should be a view that render their own content
 * The startup and shutdown functions will be called when each view
 * is created or destroyed by the central system.
 */
public abstract class MiniGame extends JPanel {

	protected MainController mainController;
	
	/**
	 * Default constructor
	 */
	public MiniGame(MainController mainController) {
		this.mainController = mainController;
	}
	
	/**
	 * Startup and shutdown functions for each minigame
	 * This allows for minigames to have timers etc.
	 */
	public abstract void startup();
	public abstract void shutdown();
	
	
	/**
	 * Return the main controller
	 */
	public MainController getGameController() {
		return mainController;
	}
	
}
