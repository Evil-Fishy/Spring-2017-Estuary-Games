package game2.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimerTask;

import game2.model.Boat;
import game2.model.CordGrass;
import game2.model.Crab;
import game2.model.Grass;
import game2.model.LargeBoat;
import game2.model.LargeWake;
import game2.model.Mini2State;
import game2.model.Oyster;
import game2.model.OysterGabion;
import game2.model.Rock;
import game2.model.RockWall;
import game2.model.SandBlock;
import game2.model.SmallBoat;
import game2.model.SmallWake;
import game2.model.Tutorial;
import game2.model.Wake;
import game2.view.G2Image;
import game2.view.MiniTwo;

/**
 * Defines a TimerTask for running through the game and updating the game state.
 * 
 * @author Tristan Morris, Christof Wittreich
 *
 */

public class Mini2Timer extends TimerTask {
	
	private MiniTwo game;
	private Mini2State state;
	private Tutorial tutorial;
	private long resourceTime;
	private long boatTime;
	private long healTime;
	private long tutorialTime;
	private long startTime;
	private int maxre;
	private boolean justHealed = false;

	/**
	 * Creates an instance of Mini2Timer.
	 * 
	 * @param game		the instance of MiniTwo being played
	 * @param state		the state of MiniTwo
	 * @param tutorial	the tutorial for MiniTwo
	 */
	public Mini2Timer(MiniTwo game, Mini2State state, Tutorial tutorial) {
		this.game = game;
		this.state = state;
		this.tutorial = tutorial;
		this.maxre = 0;
		this.startTime = -1;
	}

	/**
	 * Updates the game state (moves the game forward in time) at a constant rate
	 */
	@Override
	public void run() {
		this.game.repaint();
		
		if (tutorial.getIsDone() && (startTime == -1)) {
			startTime = System.currentTimeMillis();
		}
		//run through the tutorial
		if (!tutorial.getIsDone()){
			switch (tutorial.getStepNumber()){
			case 0://spawn small boat
				state.boats.add(new SmallBoat((-G2Image.SMALLBOAT.getWidth()), SmallBoat.getDefaultSpawnY()));
				System.out.println("finished " + tutorial.getStepNumber());
				tutorial.nextStep();
				tutorialTime = System.currentTimeMillis();
				break;
			case 1://spawn grass 3 grass
				if(System.currentTimeMillis() >= 2000 + tutorialTime){
					state.resources.add(new Grass((int) (Math.random() * 
							(state.board.getWidth() - G2Image.GRASS_PICKUP.getWidth())),
							(int) ((Math.random() *
							(state.shoreRowsNum * G2Image.SANDBLOCK.getHeight()
									- G2Image.GRASS_PICKUP.getHeight())
							+ state.board.getHeight()
							- state.shoreRowsNum * G2Image.SANDBLOCK.getHeight()
							- G2Image.DOCK.getHeight()))));
					state.resources.add(new Grass((int) (Math.random() * 
							(state.board.getWidth() - G2Image.GRASS_PICKUP.getWidth())),
							(int) ((Math.random() *
							(state.shoreRowsNum * G2Image.SANDBLOCK.getHeight()
									- G2Image.GRASS_PICKUP.getHeight())
							+ state.board.getHeight()
							- state.shoreRowsNum * G2Image.SANDBLOCK.getHeight()
							- G2Image.DOCK.getHeight()))));
					state.resources.add(new Grass((int) (Math.random() * 
							(state.board.getWidth() - G2Image.GRASS_PICKUP.getWidth())),
							(int) ((Math.random() *
							(state.shoreRowsNum * G2Image.SANDBLOCK.getHeight()
									- G2Image.GRASS_PICKUP.getHeight())
							+ state.board.getHeight()
							- state.shoreRowsNum * G2Image.SANDBLOCK.getHeight()
							- G2Image.DOCK.getHeight()))));
					System.out.println("finished " + tutorial.getStepNumber());
					tutorial.nextStep();
				}
				break;
			case 2://check to see if they've picked up grass
				if(state.grassNum >= 3){
					System.out.println("finished " + tutorial.getStepNumber());
					tutorial.nextStep();
				}
				break;
			case 3://check to see if they've placed any object (grass, if the tutorial actually works)
				for(int x = 0; x<state.shoreColsNum; x++){
					if(state.shoreline.get(x).get(3).getObs() instanceof CordGrass){
						System.out.println("finished " + tutorial.getStepNumber());
						tutorial.nextStep();
						break;
					}
				}
				break;
			case 4://check to see if a piece of the shore has regenerated
				for(int x = 0; x<state.shoreColsNum; x++){
					if(state.shoreline.get(0).get(x).getHealth() == 7){
						System.out.println("finished " + tutorial.getStepNumber());
						tutorial.nextStep();
						tutorialTime = System.currentTimeMillis();
						break;
					}
				}
				break;
			case 5:// wait 2 seconds
				if(System.currentTimeMillis() >= 2000 + tutorialTime){
					System.out.println("finished " + tutorial.getStepNumber());
					tutorial.nextStep();
				}
				break;
			case 6:// spawn an oyster pickup
				if(state.resources.isEmpty()){
				state.resources.add(new Oyster((int) (Math.random() * 
						(state.board.getWidth() - G2Image.OYSTER_PICKUP.getWidth())),
						(int) ((Math.random() *
						(state.shoreRowsNum * G2Image.SANDBLOCK.getHeight()
								- G2Image.OYSTER_PICKUP.getHeight())
						+ state.board.getHeight()
						- state.shoreRowsNum * G2Image.SANDBLOCK.getHeight()
						- G2Image.DOCK.getHeight()))));
				state.resources.add(new Oyster((int) (Math.random() * 
						(state.board.getWidth() - G2Image.OYSTER_PICKUP.getWidth())),
						(int) ((Math.random() *
						(state.shoreRowsNum * G2Image.SANDBLOCK.getHeight()
								- G2Image.OYSTER_PICKUP.getHeight())
						+ state.board.getHeight()
						- state.shoreRowsNum * G2Image.SANDBLOCK.getHeight()
						- G2Image.DOCK.getHeight()))));
				System.out.println("finished " + tutorial.getStepNumber());
				tutorial.nextStep();
				}
				break;
			case 7:// check to see if they've picked up the oysters
				if(state.oysterNum >= 2){
					System.out.println("finished " + tutorial.getStepNumber());
					tutorial.nextStep();
				}
				break;
			case 8:// check to see if they've placed the oyster gabion
				for(int x = 0; x<state.shoreColsNum; x++){
					if(state.shoreline.get(x).get(4).getObs() instanceof OysterGabion){
						System.out.println("finished " + tutorial.getStepNumber());
						tutorial.nextStep();
						tutorialTime = System.currentTimeMillis();
						break;
					}
				}
				break;
			case 9:// wait 2 seconds
				if(System.currentTimeMillis() >= 2000 + tutorialTime){
					System.out.println("finished " + tutorial.getStepNumber());
					tutorial.nextStep();
				}
				break;
			case 10:// spawn a rock pickup
				state.resources.add(new Rock((int) (Math.random() * 
						(state.board.getWidth() - G2Image.ROCK_PICKUP.getWidth())),
						(int) ((Math.random() *
						(state.shoreRowsNum * G2Image.SANDBLOCK.getHeight()
								- G2Image.ROCK_PICKUP.getHeight())
						+ state.board.getHeight()
						- state.shoreRowsNum * G2Image.SANDBLOCK.getHeight()
						- G2Image.DOCK.getHeight()))));
				System.out.println("finished " + tutorial.getStepNumber());
				tutorial.nextStep();
				break;
			case 11:// check to see if they've picked up the rocks
				if(state.rockNum >= 1){
					System.out.println("finished " + tutorial.getStepNumber());
					tutorial.nextStep();
				}
				break;
			case 12:// check to see if they've placed the rock wall
				for(int x = 0; x<state.shoreColsNum; x++){
					if(state.shoreline.get(x).get(4).getObs() instanceof RockWall){
						System.out.println("finished " + tutorial.getStepNumber());
						tutorial.nextStep();
						break;
					}
				}
				break;
			case 13:// spawn a large boat
				state.boats.add(new LargeBoat((-G2Image.LARGEBOAT.getWidth()), LargeBoat.getDefaultSpawnY()));
				System.out.println("finished " + tutorial.getStepNumber());
				tutorial.nextStep();
				break;
			case 14:// wait for the boat to pass
				if(state.boats.size() == 0){
					System.out.println("finished " + tutorial.getStepNumber());
					tutorial.nextStep();
					tutorialTime = System.currentTimeMillis();
				}
				break;
			case 15:// wait a second
				if(System.currentTimeMillis() >= 4000 + tutorialTime){
					System.out.println("finished " + tutorial.getStepNumber());
					tutorial.nextStep();
				}
				break;
			case 16://reset game
				for (ArrayList<SandBlock> ar: state.shoreline) {
			    	for (SandBlock block: ar) {
			    		block.setObs(null);
			    		block.reduceHealth(-10);
			    	}
				}
				state.rockNum = 0;
				state.oysterNum = 0;
				state.grassNum = 0;
				System.out.println("finished " + tutorial.getStepNumber());
				tutorial.nextStep();
				break;
			}
		}
		//Add ships here
		if (tutorial.getIsDone() && (System.currentTimeMillis() > 15000 + boatTime)) {
			switch ((int) (Math.random() + 0.2)) {
			case 0:
				state.boats.add(new SmallBoat((-G2Image.SMALLBOAT.getWidth()), SmallBoat.getDefaultSpawnY()));
				break;
			case 1:
				state.boats.add(new LargeBoat((-G2Image.LARGEBOAT.getWidth()), LargeBoat.getDefaultSpawnY()));
				break;
			}
			boatTime = System.currentTimeMillis();
		}
		//Moves the ships, adds their wakes, and prevents an overflow of boats
		Iterator<Boat> it = state.boats.iterator();
		while(it.hasNext()) {
			Boat b = it.next();
			b.move();
			if((b.getXLoc()%(SandBlock.getWidth()/20)) == 0) {//spawns 15 "wakes" per sandblock
				if(b instanceof SmallBoat) {
					state.smallWakes.add(new SmallWake((int) (b.getXLoc() + 0.9*b.getWidth() - 2 * G2Image.SMALLWAKE.getWidth()),
							b.getYLoc() + b.getHeight() - G2Image.SMALLWAKE.getHeight(), SmallBoat.getDamage(), 1));
					state.smallWakes.add(new SmallWake((int) (b.getXLoc() + 0.9*b.getWidth() - 2 * G2Image.SMALLWAKE.getWidth()),
							b.getYLoc(), SmallBoat.getDamage(), -1));
				} else if(b instanceof LargeBoat) {
					state.largeWakes.add(new LargeWake((int) (b.getXLoc() + 0.9*b.getWidth() - 2 * G2Image.LARGEWAKE.getWidth()),
							b.getYLoc() + b.getHeight() - G2Image.LARGEWAKE.getHeight(), LargeBoat.getDamage(), 1));
					state.largeWakes.add(new LargeWake((int) (b.getXLoc() + 0.9*b.getWidth() - 2 * G2Image.LARGEWAKE.getWidth()),
							b.getYLoc(), LargeBoat.getDamage(), -1));
				}
			}
			if(b.getXLoc() > state.board.getWidth()){
				it.remove();
			}
    	}
		//Moves the wakes
		Iterator<SmallWake> itS = state.smallWakes.iterator();
		while (itS.hasNext()) {
			SmallWake w = (SmallWake) itS.next();
			w.moveDown();
			if (Mini2CollisionDetection.checkCollisionsWakes(w, state.shoreline)) {
				itS.remove();
			} else if ((w.getYLoc() > state.board.getHeight()) || (w.getYLoc() < 0)) {
				itS.remove();
			}
    	}
		Iterator<LargeWake> itL = state.largeWakes.iterator();
		while (itL.hasNext()) {
			LargeWake w = (LargeWake) itL.next();
			w.moveDown();
			if (Mini2CollisionDetection.checkCollisionsWakes(w, state.shoreline)) {
				itL.remove();
			} else if (w.getYLoc() > state.board.getHeight()) {
				itL.remove();
			}
    	}
		
		Iterator<Crab> it2 = state.crabs.iterator();
		while (it2.hasNext()) {
			Crab crab = (Crab) it2.next();
			crab.move();
			Mini2CollisionDetection.checkCollisionsCrabsSandBlock(crab, state.shoreline);
			if (Mini2CollisionDetection.checkCollisionsCrabsResource(crab, state.resources)) {
				crab.setHasResource(true);
			} else if ((crab.getCoords()[0] == crab.getXLoc()) && (crab.getCoords()[1] == crab.getYLoc()) && crab.getBusy()) {
				if(crab.getCarriedResource() instanceof Rock) state.rockNum++;
	    		else if(crab.getCarriedResource() instanceof Oyster) state.oysterNum++;
	    		else if(crab.getCarriedResource() instanceof Grass) state.grassNum++;
				if (crab.getCarriedResource() != null) {
					crab.setCarriedResource(null);
					crab.setCarriedObstacle(null);
					crab.setHasResource(false);
					crab.setHasObstacle(false);
					crab.sendHome();
					crab.setBusy(false);
				}
				if (crab.getCarriedObstacle() != null) {
					if(crab.getCarriedObstacle() instanceof RockWall) {
						if (state.rockNum >= state.rockObstacleAmount) {
							state.rockNum-=state.rockObstacleAmount;
						} else {
							crab.setCarriedResource(null);
							crab.setCarriedObstacle(null);
							crab.setHasResource(false);
							crab.setHasObstacle(false);
							crab.sendHome();
							crab.setBusy(false);
						}
					}
		    		else if(crab.getCarriedObstacle() instanceof OysterGabion) {
						if (state.oysterNum >= state.oysterObstacleAmount) {
							state.oysterNum-=state.oysterObstacleAmount;
						} else {
							crab.setCarriedResource(null);
							crab.setCarriedObstacle(null);
							crab.setHasResource(false);
							crab.setHasObstacle(false);
							crab.sendHome();
							crab.setBusy(false);
						}
		    		}
		    		else if(crab.getCarriedObstacle() instanceof CordGrass) {
						if (state.grassNum >= state.grassObstacleAmount) {
							state.grassNum-=state.grassObstacleAmount;
						} else {
							crab.setCarriedResource(null);
							crab.setCarriedObstacle(null);
							crab.setHasResource(false);
							crab.setHasObstacle(false);
							crab.sendHome();
							crab.setBusy(false);
						}
		    		}
					if (crab.getBusy()) {
						crab.setHasObstacle(true);
						crab.switchCoords();
					}
				}
			}
    	}
		
		if(tutorial.getIsDone() && (state.resources.size() < maxre) && (System.currentTimeMillis() > 3000 + resourceTime)) {
				// Random pick between all 3 resources
			switch((int) (Math.random() * 3) + 1) {
			case 1: state.resources.add(new Rock((int) (Math.random() * 
					(state.board.getWidth() - G2Image.ROCK_PICKUP.getWidth())),
					(int) ((Math.random() *
					(state.shoreRowsNum * G2Image.SANDBLOCK.getHeight()
							- G2Image.ROCK_PICKUP.getHeight())
					+ state.board.getHeight()
					- state.shoreRowsNum * G2Image.SANDBLOCK.getHeight()
					- G2Image.DOCK.getHeight()))));
					break;
			case 2: state.resources.add(new Oyster((int) (Math.random() * 
					(state.board.getWidth() - G2Image.OYSTER_PICKUP.getWidth())),
					(int) ((Math.random() *
					(state.shoreRowsNum * G2Image.SANDBLOCK.getHeight()
							- G2Image.OYSTER_PICKUP.getHeight())
					+ state.board.getHeight()
					- state.shoreRowsNum * G2Image.SANDBLOCK.getHeight()
					- G2Image.DOCK.getHeight()))));
					break;
			case 3: state.resources.add(new Grass((int) (Math.random() * 
					(state.board.getWidth() - G2Image.GRASS_PICKUP.getWidth())),
					(int) ((Math.random() *
					(state.shoreRowsNum * G2Image.SANDBLOCK.getHeight()
							- G2Image.GRASS_PICKUP.getHeight())
					+ state.board.getHeight()
					- state.shoreRowsNum * G2Image.SANDBLOCK.getHeight()
					- G2Image.DOCK.getHeight()))));
					break;
			}
			resourceTime = System.currentTimeMillis();
		} else if ((maxre == 0) && (state.resources.size() != 10)) {
			maxre = 10;
			resourceTime = System.currentTimeMillis();
		} else if (state.resources.size() == 10) {
			maxre = 0;
		}
		
		for(int i = 0; i < state.shoreline.size(); i++) {
	    	for(int j = 0; j < state.shoreline.get(i).size(); j++) {
	    		SandBlock s = state.shoreline.get(i).get(j);
	    		if ((s.getHealth() == 0) && (!s.getDestroyed())) {
	    			state.blocksDestroyed.set(i, state.blocksDestroyed.get(i) + 1);
	    			s.setDestroyed(true);
	    		}
	    		if ((j != 0) && (state.shoreline.get(i).get(j - 1).getObs() instanceof CordGrass) && (System.currentTimeMillis() > 10000 + healTime)) {
	    			s.reduceHealth(-1);
	    			justHealed = true;
	    		}
	    		if (s.getAffected() && (s.getDisplayTime() == -1)) {
	    			s.setDisplayTime(System.currentTimeMillis());
	    		} else if (s.getAffected() && (System.currentTimeMillis() > 4000 + s.getDisplayTime())) {
	    			s.setAffected(false);
	    			s.setDisplayTime(-1);
	    		}
	    		

				if ((s.getObs() != null) || (s.getDestroyed())) {
					s.setPlacement(false);
				} else if ((state.placementMode == 1) && (j == (state.shoreRowsNum - 1 - state.blocksDestroyed.get(i)))) {
					s.setPlacement(true);
				} else if ((state.placementMode == 2) && (j == (state.shoreRowsNum - 1 - state.blocksDestroyed.get(i)))) {
					s.setPlacement(true);
				} else if ((state.placementMode == 3) && (j == (state.shoreRowsNum - 2 - state.blocksDestroyed.get(i)))) {
					s.setPlacement(true);
				} else if ((state.placementMode != 1) && (j == (state.shoreRowsNum - 1 - state.blocksDestroyed.get(i)))) {
					s.setPlacement(false);
				} else if ((state.placementMode != 2) && (j == (state.shoreRowsNum - 1 - state.blocksDestroyed.get(i)))) {
					s.setPlacement(false);
				} else if ((state.placementMode != 3) && (j == (state.shoreRowsNum - 2 - state.blocksDestroyed.get(i)))) {
					s.setPlacement(false);
				}
	    	}
		}
		
		if (justHealed) {
			healTime = System.currentTimeMillis();
			justHealed = false;
		}
		
		if ((System.currentTimeMillis() > 90000 + startTime) && (startTime != -1)) {
			game.endGame();
		}
		
	}

}
