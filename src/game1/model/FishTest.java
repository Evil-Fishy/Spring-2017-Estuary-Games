package game1.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FishTest {

	@Test
	public void testConstructor1() {
		Fish fish = new Fish();
		assertEquals(fish.getXLoc(),0, 0.001);
		assertEquals(fish.getYLoc(),0, 0.001);
	}
	
	@Test
	public void testConstructor2() {
		Fish fish = new Fish(10,20);
		assertEquals(fish.getXLoc(),10, 0.001);
		assertEquals(fish.getYLoc(),20, 0.001);
	}
	
	@Test
	public void testImageSize() {
		assertEquals(Fish.getImgHeight(), 100);
		assertEquals(Fish.getImgWidth(), 60);
	}

}
