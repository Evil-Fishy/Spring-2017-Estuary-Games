package master.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class DirectionTest {

	@Test
	public void testConstructor1() {
		Direction dir1 = Direction.N;
		assertEquals(dir1.toInt(),0);
		assertEquals(Direction.S.toInt(),4);
	}
	
	@Test
	public void testCardinalDirection() {
		assertEquals(Direction.switchToCardinalDirection(Direction.NW), Direction.N);
		assertEquals(Direction.switchToCardinalDirection(Direction.NE), Direction.N);
		assertEquals(Direction.switchToCardinalDirection(Direction.SW), Direction.S);
		assertEquals(Direction.switchToCardinalDirection(Direction.SE), Direction.S);
		assertEquals(Direction.switchToCardinalDirection(Direction.W), Direction.W);
		assertEquals(Direction.switchToCardinalDirection(Direction.E), Direction.E);
		assertEquals(Direction.switchToCardinalDirection(Direction.N), Direction.N);
		assertEquals(Direction.switchToCardinalDirection(Direction.S), Direction.S);
	}
	
	@Test
	public void testToString() {
		assertEquals(Direction.N.toString(), "north");
		assertNotEquals(Direction.NE.toString(), "northwest");
	}

}
