package game3.controller;

import static org.junit.Assert.*;

import javax.swing.JFrame;

import org.junit.Test;

import game3.model.Mini3State;
import game3.view.G3Image;
import game3.view.MiniThree;
import game3.view.TutPanel;
import master.controller.MainController;
import master.model.Board;

public class TutTimerTest {	
	MainController mainController;
	JFrame frame = new JFrame();
	Board b = new Board(500, 500);
	MiniThree game;
	Mini3State state;
	TutTimer timer;
	TutPanel tutPanel;
	
	/**
	 * Needed to setup the game state prior to testing.
	 */
	private void setup() {
		frame.setSize(b.getWidth(), b.getHeight());		
		mainController = new MainController(frame);
		game = new MiniThree(mainController);
		game.startup();
		state = game.getState();
		tutPanel = game.getTutPanel();
	}

	@Test
	public void testRun() {
		setup();
		tutPanel.setTutState(TutState.SWIPE);
		tutPanel.startup();
		timer = tutPanel.getTimer();
		tutPanel.incrFingerXPosn(-3 * G3Image.FINGER.getWidth());
		timer.run();
		assertTrue(tutPanel.getIsActive());
		tutPanel.incrFingerXPosn(3 * G3Image.FINGER.getWidth());
		for(int i = 0; i < 25; i++)
			timer.run();
		assertFalse(tutPanel.getIsActive());		

		tutPanel.shutdown();
		tutPanel.setTutState(TutState.ARRANGE_DIE);
		tutPanel.startup();
		timer = tutPanel.getTimer();
		timer.run();
		assertTrue(tutPanel.getIsActive());
		for(int i = 0; i < 30; i++)
			timer.run();
		assertFalse(tutPanel.getIsActive());
	}
}