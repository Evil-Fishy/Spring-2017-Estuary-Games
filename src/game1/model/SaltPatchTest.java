package game1.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SaltPatchTest {

	@Test
	public void testConstructor1() {
		SaltPatch salt = new SaltPatch();
		assertEquals(salt.getXLoc(),0, 0.001);
		assertEquals(salt.getYLoc(),0, 0.001);
	}
	
	@Test
	public void testConstructor2() {
		SaltPatch salt = new SaltPatch(10,20);
		assertEquals(salt.getXLoc(),10, 0.001);
		assertEquals(salt.getYLoc(),20, 0.001);
	}
	
	@Test
	public void testImageSize() {
		assertEquals(SaltPatch.getImgHeight(), 40);
		assertEquals(SaltPatch.getImgWidth(), 40);
	}

}
