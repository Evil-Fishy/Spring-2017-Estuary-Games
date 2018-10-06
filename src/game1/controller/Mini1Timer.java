package game1.controller;

import java.util.Iterator;
import java.util.TimerTask;

import game1.model.Crab;
import game1.model.Fish;
import game1.model.Mini1State;
import game1.model.Obstacle;
import game1.model.SaltPatch;
import game1.view.G1Image;
import game1.view.MiniOne;
import game1.view.ViewGame;

/**
 * This is the master timer class for minigame one
 * It has all the logic needed to both end and start the game
 * On each loop the game is moved forward in time by one step
 */
public class Mini1Timer extends TimerTask {
	
	// Master objects
	MiniOne game;

	// Current state number
	int stateCount;
	
	// Counters
	int addCount = 250;
	int obsCount = 0;
	int endCount = 30;
	int wallCount = 0;
	
	// Physical times
	long collisionTime;
	long obsTime;	

	private int arrowFrameDelaySoFar = 0;
	private int arrowFrameDelayLength = 5;
	
	/**
	 * Default constructor
	 * @param game The main minigame object
	 */
	public Mini1Timer(MiniOne game) {
		this.game = game;
	}

	/**
	 * This will move the obstacles forward in time
	 * This happens at a constant rate
	 */
	@Override
	public void run() {
		
		// Increment the state
		stateCount++;
		
		// If has been more then 6 seconds remove the tutorial
		if(System.currentTimeMillis() > 6000 + game.getState().startTime) {
			game.getState().isTutorial = false;
		}
		
		// Move the obstacles forward in time
		// Also scale the downward velocity by some constant speed factor
		for (Obstacle obs : game.getState().obstacles) {
			if(obs instanceof SaltPatch) {
				obs.moveVertical();
				obs.setyVelocity(game.getState().velocity);
			}
			if(obs instanceof Fish) {
				obs.moveHorizontal();
				obs.moveVertical();
				obs.setyVelocity(game.getState().velocity);
			}			
    	}
		
		// Move the background down
		// This should provide some nice parallax
		game.getState().backgroundPosn = (int)(game.getState().backgroundPosn + game.getState().velocity) % game.getState().board.getHeight();

		// Check for collisions
		// Have about 500 milliseconds of I-frames
		if(Mini1CollisionDetection.checkCollisions(game.getState().crab, game.getState().obstacles) && System.currentTimeMillis() > 1000 + collisionTime) {
			// Record collision time, and change background
			collisionTime = System.currentTimeMillis();
			game.getState().isCollided = true;
			// Return the velocity to the default
			game.getState().velocity = Mini1State.yVelDefault;
			addCount = 200;
			// Add to penalty
			game.getState().secPenalty+=1;
		}
		
		// If has been a 1/2 second then remove red background
		if(System.currentTimeMillis() > 500 + collisionTime) {
			game.getState().isCollided = false;
		}
		
		
		// TODO: Remove obstacles if they are off the screen
		// TODO: Is this ok? might get concurrent modification errors
		Iterator<Obstacle> it = game.getState().obstacles.iterator();
		while(it.hasNext()) {
			Obstacle obs = (Obstacle)it.next();
			if(obs.getYLoc()>game.getGameController().getHeight())
				it.remove();
    	}
		
		// Handle fish objects going off the screen
		for (Obstacle obs : game.getState().obstacles) {
			// Skip if not fish
			if(!(obs instanceof Fish))
				continue;
			// If we are a fish, then check bounds
			// We just need to flip velocity if we are out of bounds
			if(obs.getXLoc() < 0 || (obs.getXLoc()+Fish.getImgWidth()) > game.getGameController().getWidth())
				obs.setxVelocity(-obs.getxVelocity());
		}
		
		
		// Add new obstacles here if we has passed the time and are not at the end
		if(stateCount > addCount + obsCount && !game.getState().isEnd) {
			
			// Random between fish and salt walls
			// Favor walls with a 70% chance
			if(Math.random() < 0.3)
				addSwimmingFish();
			else
				addSaltWall();
			
			// Update time and velocity
			obsCount = stateCount;
			obsTime = System.currentTimeMillis();
			game.getState().velocity = (game.getState().velocity  < 8)? game.getState().velocity + 0.3 : 8;
			System.out.println("velocity = " + game.getState().velocity);
			addCount = (addCount > 100)? (addCount - 8) : 100;
			wallCount++;
		}
		
		// Signal the end of the game here
		if(wallCount > endCount) {
			// We are ending
			game.getState().isEnd = true;
			// Return the velocity to the default
			game.getState().velocity = Mini1State.yVelDefault;
			// Animated the background down
			game.getState().endingPosn = (int)(game.getState().endingPosn + game.getState().velocity) % game.getState().board.getHeight();
			// End the game once the background has been animated all the way down
			if(game.getState().endingPosn == 0)
				game.endGame();
		}
		
		if(game.getState().isTutorial) {
			game.getState().arrow.setXYLoc(game.getState().crab.getXLoc() + Crab.getImgWidth(), 
					game.getState().crab.getYLoc() + G1Image.ARROWS.getHeight()/ViewGame.getNumArrowPics()/4);
			arrowFrameDelaySoFar = (arrowFrameDelaySoFar + 1) % arrowFrameDelayLength;
			if(arrowFrameDelaySoFar == 0)		
				game.getState().arrow.incrPicNum();
		}
	}
	
	/**
	 * This private function allows for adding of an entire salt wall
	 * This will calculate what the best gap should be right now
	 * This gap is at least two crabs wide
	 */
	private void addSaltWall() {
		
		// Gap start and total tile count
		// Note that gap size is a linear function of the progression through the game
		double gapSize = 4*((double)wallCount/endCount)*(2*Crab.getImgWidth()/SaltPatch.getImgWidth());
		int tileCount = game.getGameController().getWidth()/SaltPatch.getImgWidth();
		
		// Debug message
		System.out.println("gap size = " + (int)gapSize);
		
		// Set min gap size to 2 times the crab size
		if(gapSize < (2*Crab.getImgWidth()/SaltPatch.getImgWidth()))
			gapSize = (2*Crab.getImgWidth()/SaltPatch.getImgWidth());
		
		// Find the gap start, make sure that it isn't near the right size
		int gapStart = (int) (Math.random() * (tileCount - gapSize));
		
		// Add a new salt row
		for(int i=0; i<tileCount+1; i++) {
			if(i<gapStart || i>(gapStart+gapSize)) {
				SaltPatch salt = new SaltPatch(i*SaltPatch.getImgWidth(), 0);
				salt.setxVelocity(0);
				salt.setyVelocity(game.getState().velocity);
				game.getState().obstacles.add(salt);
			}
		}
		
	}
	
	/**
	 * This function will add a swimming fish the screen
	 * This fish will have a constant x velocity but have matching y velocity
	 */
	private void addSwimmingFish() {
		// Random location and velocity
		int xLoc1 = (int) (Math.random() * (game.getGameController().getWidth()-Fish.getImgWidth()));
		int xLoc2 = (int) (Math.random() * (game.getGameController().getWidth()-Fish.getImgWidth()));
		double xVel1 = (Math.random() * 5) - 2;
		double xVel2 = (Math.random() * 5) - 2;
		// Create fish 1
		Fish fish1 = new Fish(xLoc1, 0);
		fish1.setxVelocity(xVel1);
		fish1.setyVelocity(game.getState().velocity);
		game.getState().obstacles.add(fish1);
		// Create fish 2
		Fish fish2 = new Fish(xLoc2, 0);
		fish2.setxVelocity(xVel2);
		fish2.setyVelocity(game.getState().velocity);
		game.getState().obstacles.add(fish2);
	}
	

}
