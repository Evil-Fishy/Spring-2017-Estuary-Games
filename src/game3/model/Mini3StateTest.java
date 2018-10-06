package game3.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import master.model.Board;

public class Mini3StateTest {
	Board b = new Board(200, 200);
	Mini3State state = new Mini3State();
	
	@Test
	public void testBoard(){
		state.setBoard(b);
		assertEquals(b, state.getBoard());
	}
	
	@Test
	public void testDiceRolledStoppedSet(){
		state.setDiceRolled(true);
		assertTrue(state.getDiceRolled());
		state.setAllDiceStopped(true);
		assertTrue(state.getAllDiceStopped());
		state.setAllDiceSet(true);
		assertTrue(state.getAllDiceSet());
	}	
	
	@Test
	public void testDiceAndMarkerModifiers() {
		List<Die> dice = new ArrayList<Die>();
		state.setDice(dice);
		assertEquals(dice, state.getDice());		

		List<Marker> markers = new ArrayList<Marker>();
		state.setMarkers(markers);
		assertEquals(dice, state.getMarkers());
	}
	
	@Test
	public void testReorderDice() {
		Die d = new Die(b, 100, 100);
		List<Die> dice = new ArrayList<Die>();
		dice.add(d);
		state.setDice(dice);
		
		Marker m = new Marker(100, 100);
		List<Marker> markers = new ArrayList<Marker>();;
		markers.add(m);
		state.setMarkers(markers);
		
		m.setDieContained(d);
		state.reorderDice();
		assertEquals(d, state.getDice().get(0));
	}
	
	@Test
	public void testGetFacePicsFromDice() {
		Die d = new Die(b, 100, 100);
		d.setFacePic(10);
		List<Die> dice = new ArrayList<Die>();
		dice.add(d);
		for(int i = 1; i < Mini3State.numDice; i++)
			dice.add(new Die(b, 50, 50));
		state.setDice(dice);
		
		assertEquals(10, state.getFacePicsFromDice()[0]);	
	}
}
