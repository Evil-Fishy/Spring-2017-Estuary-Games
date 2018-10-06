package game2.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TutorialTest {

	@Test
	public void testConstructor() {
		Tutorial t = new Tutorial();
		assertEquals(t.getIsDone(),false);
		assertEquals(t.getStepNumber(),0);
	}
	
	@Test
	public void testStep() {
		Tutorial t = new Tutorial();
		assertEquals(t.getStepNumber(),0);
		assertEquals(t.nextStep(),false);
		assertEquals(t.getStepNumber(),1);
		t.setStepNumber(50);
		assertEquals(t.getIsDone(),true);
		t.resetTutorial();
		assertEquals(t.getIsDone(),false);
		assertEquals(t.getStepNumber(),0);
		// Loop 16 times
		for(int i=0;i<16;i++)
			t.nextStep();
		// End the tutorial
		assertEquals(t.nextStep(),true);
		assertEquals(t.getIsDone(),true);
	}
	
	
}
