package game2.controller;

import java.util.Iterator;

import game2.model.CordGrass;
import game2.model.Crab;
import game2.model.Mini2State;
import game2.model.Obstacle;
import game2.model.OysterGabion;
import game2.model.Resource;
import game2.model.RockWall;

/**
 * Defines CrabManager, used to control crabs correctly.
 * 
 * @author Christof Wittreich
 *
 */
public class CrabManager {
	
	Mini2State state;
	
	/**
	 * Creates an instance of CrabManager.
	 * 
	 * @param state		used to extract information about the game
	 */
	public CrabManager(Mini2State state) {
		this.state = state;
	}
	
	/**
	 * Sets a crabs destination to a given resource for the crab to pick up.
	 * 
	 * @param re	the resource we want to pick up
	 */
	public void assignCrabResource(Resource re) {
		if (re != null) {
			int[] coords = new int[2];
			Iterator<Crab> it1 = state.crabs.iterator();
			while(it1.hasNext()) {
				Crab crab = it1.next();
	    		if(!crab.getBusy()) {
	    			crab.setBusy(true);
	    			crab.setCarriedResource(re);
	    			coords[0] = re.getXLoc();
	    			coords[1] = re.getYLoc();
	    			crab.setCoords(coords);
	    			return;
	    		}
	    	}
			re.setClickable(true);
		}
	}
	
	/**
	 * Finds a crab that isn't busy to build an obstacle.
	 * 
	 * @param ob	the obstacle for the crab to place
	 */
	public void assignCrabObstacle(Obstacle ob) {
		if (ob != null) {
			int[] coords = new int[2];
			Iterator<Crab> it1 = state.crabs.iterator();
			while(it1.hasNext()) {
				Crab crab = it1.next();
	    		if(!crab.getBusy()) {
	    			crab.setBusy(true);
	    			crab.setCarriedObstacle(ob);
	    			coords[0] = ob.getXLoc();
	    			coords[1] = ob.getYLoc();
	    			crab.setObCoords(coords);
	    			crab.sendHome();
	    			return;
	    		}
	    	}
		}
	}
}
