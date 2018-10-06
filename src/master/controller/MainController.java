package master.controller;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;

import game1.view.MiniOne;
import game2.view.MiniTwo;
import game3.view.MiniThree;
import master.view.MiniChooser;
import master.view.MiniGame;

/**
 * This is the master controller of the system
 * It will handle the startup and shutdown of the other minigames
 * It also handles displaying of the main menu switcher
 */
public class MainController {

	JFrame frame;
	int currentPanel;
	
	MiniGame chooser;
	ArrayList<MiniGame> panels;
	
	private final static int numGames = 3;
	
	/**
	 * Default constructor
	 */
	public MainController(JFrame frame) {
		// Store our jframe
		this.frame = frame;
		// Add the chooser
		chooser = new MiniChooser(this);
		// Add the mini games here
		panels = new ArrayList<MiniGame>();
		panels.add(new MiniOne(this));
		panels.add(new MiniTwo(this));
		panels.add(new MiniThree(this));
		
        frame.setLocationRelativeTo(null);
	}	
	
	/**
	 * This function takes in the ID of the minigame
	 * That we want to go to. For our game it should be
	 * Between 1 and 3
	 */
	public void switchMini(int i) {
		chooser.shutdown();
		// Check that we are in bounds
		if(i > panels.size() && i > 0) {
			throw new RuntimeException("MiniGame Out of Bounds");
		}
		// Else we good to go
		// http://stackoverflow.com/a/5077773/7718197
		frame.getContentPane().removeAll();
		frame.getContentPane().add(panels.get(i-1));
		frame.getContentPane().doLayout();
		frame.revalidate();
		frame.repaint();
		// Call the startup function
		panels.get(i-1).startup();
		currentPanel = i-1;
	}
	
	
	/**
	 * This is called by the minigames when they want to exit
	 * When the game is done, they should exit back the chooser
	 */
	public void exitMini() {
		// Switch frame
		frame.getContentPane().removeAll();
		chooser.startup();
		frame.getContentPane().add(chooser, BorderLayout.CENTER);
		frame.getContentPane().doLayout();
		frame.revalidate();
		frame.repaint();
		// Shutdown last panel
		panels.get(currentPanel).shutdown();
	}

	
	public JFrame getFrame() {
		return frame;
	}
	
	public int getHeight() {
		return frame.getHeight();
	}
	
	public int getWidth() {
		return frame.getWidth();
	}
	
	public int getNumGames() {
		return numGames;
	}
	
	/**
	 * Will execute a delay for the given number of milliseconds.
	 * (Usually used to allow time for panel revalidation prior to adding a special component.)
	 * 
	 * @param ms milliseconds of delay to execute immediately
	 */
	public static void execDelay(int ms) {
		try {
			Thread.sleep(ms);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
