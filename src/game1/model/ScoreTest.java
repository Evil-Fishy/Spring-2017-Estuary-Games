package game1.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

public class ScoreTest {

	@Test
	public void testConstructor() {
		Score sc = new Score("Patrick", 48.9);
		assertEquals(sc.getName(),"Patrick");
		assertEquals(sc.getTime(), 48.9, 0.001);
	}
	
	
	@Test
	public void testSort() {
		ArrayList<Score> board = new ArrayList<Score>();
		board.add(new Score("Patrick", 48.9));
		board.add(new Score("John", 20.99));
		// Sort
		Collections.sort(board);
		// Check order
		assertEquals(board.get(0).getName(), "John");
		assertEquals(board.get(1).getName(), "Patrick");
	}
	

}
