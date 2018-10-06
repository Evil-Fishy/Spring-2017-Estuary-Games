package game2.model;

/**
 * This class handles tracking of the tutorial
 * This will keep track of the current state the tutorial is in
 * Defines a Tutorial used to teach players how to play the game in a set amount of instructions.
 * 
 * @author Tristan Morris
 *
 */
public class Tutorial {
	private boolean isDone;
	private int stepNumber;
	private int maxSteps;
	
	/**
	 * Creates an instance of Tutorial.
	 */
	public Tutorial(){
		isDone = false;
		stepNumber = 0;
		maxSteps = 16;
	}
	
	/**
	 * Increments stepNumber. If it reaches maxSteps, isDone is set to true.
	 * 
	 * @return a boolean determining whether the tutorial is done or not.
	 */
	public boolean nextStep(){
		if(stepNumber < maxSteps){
			stepNumber++;
		}
		else {
			isDone = true;
		}
		return isDone;
	}
	
	/**
	 * Starts the tutorial from the beginning.
	 */
	public void resetTutorial(){
		isDone = false;
		stepNumber = 0;
	}
	
	// Getters and setters
	public boolean getIsDone(){
		return isDone;
	}
	
	public int getStepNumber(){
		return stepNumber;
	}
	
	/**
	 * Sets the tutorial to a specific step. Recalculates isDone based on the new stepNumber.
	 * @param step
	 */
	public void setStepNumber(int step){
		this.stepNumber = step;
		if(stepNumber>=maxSteps){
			isDone = true;
		}
		else{
			isDone = false;
		}
	}

}
