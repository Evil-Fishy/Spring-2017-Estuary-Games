package master.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ArrowTest {

	@Test
	public void testConstructor() {
		Board b = new Board(200,200);
		Arrow test = new Arrow(b, 200, 200);
		assertEquals(test.getPicNum(), 0);
		assertEquals(test.getXLoc(), (b.getWidth() - 200)/2);
		assertEquals(test.getYLoc(), (b.getWidth() - 200)/2);
	}
	
	@Test
	public void testIncrPicNum() {
		Board b = new Board(200,200);
		Arrow test = new Arrow(b, 200, 200);
		test.incrPicNum();
		assertEquals(0,test.getPicNum());
	}

}
