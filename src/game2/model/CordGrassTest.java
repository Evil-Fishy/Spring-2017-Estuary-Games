package game2.model;

import static org.junit.Assert.*;

import org.junit.Test;

import game2.view.G2Image;


public class CordGrassTest {

	@Test
	public void testConstructor() {
		CordGrass c = new CordGrass(10,20);
		assertEquals(c.getXLoc(),10);
		assertEquals(c.getYLoc(),20);
	}
	
	@Test
	public void testSize() {
		CordGrass c = new CordGrass(10,20);
		assertEquals(c.getWidth(),G2Image.GRASS_OBS.getWidth());
		assertEquals(c.getHeight(),G2Image.GRASS_OBS.getHeight());
	}
	
	
}
