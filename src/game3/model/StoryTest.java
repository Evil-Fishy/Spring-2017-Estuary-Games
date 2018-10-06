package game3.model;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the story class.
 * @author Habibullah Aslam
 *
 */
public class StoryTest {

	@Test
	public void testConstructorAndGetters() {
		Story s = new Story("Bob", "Hi", new int[] {4});
		assertEquals("Bob", s.getName());
		assertEquals("Hi", s.getStory());
		assertEquals(4, s.getCubePics()[0]);
	}
}