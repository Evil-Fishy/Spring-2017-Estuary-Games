package game3.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.junit.Test;

import game3.model.Die;
import game3.model.Marker;
import game3.model.Mini3State;
import game3.view.MiniThree;
import master.controller.MainController;
import master.model.Board;
import master.model.Direction;

public class Mini3TimerTest {	
	JFrame frame = new JFrame();
	Board b = new Board(500, 500);
	MiniThree game;
	Mini3State state;
	Mini3Timer timer;
	MainController mainController;
	Die d;
	Marker m;
	
	/**
	 * Needed to setup the game state prior to testing.
	 */
	private void setup() {
		frame.setSize(b.getWidth(), b.getHeight());		
		mainController = new MainController(frame);
		game = new MiniThree(mainController);
		state = game.getState();
		timer = new Mini3Timer(game, state);
	}
	
	/**
	 * Needed to setup the game state and dice/markers prior to testing.
	 */
	private void setupWithDiceAndMarkers() {
		setup();
		d = new Die(b, 400, 400);
		List<Die> dice = new ArrayList<Die>();
		dice.add(d);
		for(int i = 1; i < Mini3State.numDice; i++)
			dice.add(new Die(b, 50, 50));
		state.setDice(dice);
		m = new Marker(200, 200);
		List<Marker> markers = new ArrayList<Marker>();;
		markers.add(m);
		for(int i = 1; i < Mini3State.numDice; i++)
			markers.add(new Marker(50, 50));
		state.setMarkers(markers);
	}

	@Test
	public void testConstructor() {
		setup();
		
		assertFalse(timer.getGameStarted());
	}
	
	@Test
	public void testRun() {
		setupWithDiceAndMarkers();		

		assertFalse(timer.getGameStarted());
		timer.run();
		assertTrue(timer.getGameStarted());
		timer.run();
		assertFalse(state.getDiceRolled());
		timer.getBtnSwipe().setLocation(500, 500);
		timer.run();
		assertTrue(state.getDiceRolled());
		
		for(Die die : state.getDice()) {
			die.setXVel(0);
			die.setYVel(0);
		}
		timer.run();
		assertTrue(state.getAllDiceStopped());
		
		d.setXVel(20);
		
		state.setDiceRolled(false);
		state.setAllDiceStopped(false);
		state.setAllDiceSet(true);
		game.getBtnSwipe().setLocation(0, 0);
		game.getBtnMakeStory().setVisible(false);
		timer.run();
		assertTrue(game.getBtnMakeStory().isVisible());		

		state.setAllDiceSet(false);
		game.getBtnMakeStory().setVisible(true);
		timer.run();
		assertFalse(game.getBtnMakeStory().isVisible());
		
		for(int i = 0; i < Mini3State.numDice; i++)
			state.getMarkers().get(i).setDieContained(state.getDice().get(i));		
		game.getStoryPanel().setActive(true);
		game.getStoryPanel().startup();
		timer.run();
		assertNotEquals(400, state.getDice().get(0).getXLoc());
	}
	
	@Test
	public void testUpdateDice() {
		setupWithDiceAndMarkers();

		d.setDir(Direction.E);
		d.setLocation(100, 100);
		timer.updateDice();
		assertNotEquals(60, d.getXLoc());

		for(Die die : state.getDice()) {
			die.setXVel(0);
			die.setYVel(0);
		}
		timer.updateDice();
		assertTrue(d.getButton().isEnabled());
		
		d.setLocation(60, 100);
		d.setXVel(0);
		d.setYVel(0);
		d.updateDie();
		state.setAllDiceStopped(true);
		d.getButton().setHasBeenSelectedOnce(true);
		timer.updateDice();
		assertEquals(150, d.getButton().getX());
	}

	@Test
	public void testUpdateMarkers() {
		setupWithDiceAndMarkers();
		d.setDir(Direction.E);
		d.setLocation(100, 100);
		timer.updateMarkers();
		assertFalse(m.getIsCollided());
		
		d.setLocation(200, 200);
		d.getButton().setHasBeenSelectedOnce(true);
		timer.updateMarkers();
		assertTrue(m.getContainsDie());
		
		m.setDieContained(null);
		d.getButton().setIsSelected(true);
		timer.updateMarkers();
		assertTrue(m.getIsCollided());	
		
		m.setDieContained(d);
		d.getButton().setIsSelected(false);
		timer.updateMarkers();
		assertEquals(20, d.getButton().getX());
		
		d.getButton().setIsSelected(true);
		timer.updateMarkers();
		assertFalse(m.getContainsDie());		
	}
	
	@Test
	public void testGameStarted() {
		setup();
		timer.setGameStarted(true);
		assertTrue(timer.getGameStarted());
	}		

	@Test
	public void testGetBtnSwipe() {
		setup();
		assertEquals(game.getBtnSwipe(), timer.getBtnSwipe());
	}
	
	@Test
	public void testCheckDiceSet() {
		setupWithDiceAndMarkers();
		for(int i = 0; i < Mini3State.numDice; i++)
			state.getMarkers().get(i).setDieContained(state.getDice().get(i));	
		timer.checkDiceSet();
		assertTrue(state.getAllDiceSet());	
		m.setDieContained(null);
		timer.checkDiceSet();
		assertFalse(state.getAllDiceSet());
	}
}



