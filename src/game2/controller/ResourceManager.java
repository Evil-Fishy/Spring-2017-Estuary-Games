package game2.controller;

import java.util.ArrayList;
import java.util.Iterator;

import game2.model.CordGrass;
import game2.model.Mini2State;
import game2.model.Obstacle;
import game2.model.OysterGabion;
import game2.model.Resource;
import game2.model.RockWall;
import game2.model.SandBlock;

/**
 * Used to manage the Resource economy in game 2
 * 
 * @author Christof Wittreich
 *
 */
public class ResourceManager {
	
	Mini2State state;
	
	/**
	 * Creates an instance of ResourceManager
	 * 
	 * @param state		used to extract information about the state of game 2
	 */
	public ResourceManager(Mini2State state) {
		this.state = state;
	}
	
	//TODO unsure of this method
	/**
	 * Makes a resource unclickable if it is clicked, and a crab is able to pick it up.
	 * 
	 * @param x		x location of click
	 * @param y		y location of click
	 * @return	the clicked resource if clickable, otherwise null
	 */
	public Resource pickupEvent(int x, int y) {
		Resource r = Mini2CollisionDetection.checkCollisionsResources(x, y, state.resources);
		Iterator<Resource> it = state.resources.iterator();
		while(it.hasNext()) {
			Resource re = it.next();
			if(re.equals((Resource) r) && re.getClickable()) {
				re.setClickable(false);
				return re;
			}
    	}
		return null;
	}
	
	/**
	 * Determines if the player has enough Resources to place an Obstacle.
	 * 
	 * @param type	a String to determine which resource type the game should check
	 * @return		true if a resource of the specified type can be picked up, false otherwise
	 */
	public boolean btnEvent(String type) {
		switch (type) {
		case "Rock": return (state.rockNum >= state.rockObstacleAmount);
		case "Oyster": return (state.oysterNum >= state.oysterObstacleAmount);
		case "Grass": return (state.grassNum >= state.grassObstacleAmount);
		}
		return false;
	}
	
	//TODO I'm unsure how to define this method.
	/**
	 * Finds if the user is able to place down a particular obstacle on a sandblock
	 * 
	 * @param type		represents the type of obstacle being placed (1 = rockwall, 2 = oystergabion, 3 = cordgrass)
	 * @param x			x location of mouseclick
	 * @param y			y location of mouseclick
	 * @return	the obstacle being placed, otherwise null
	 */
	public Obstacle placeEvent(int type, int x, int y) {
		SandBlock s = Mini2CollisionDetection.checkCollisionsPlacement(x, y, state.shoreline);
		Iterator<ArrayList<SandBlock>> it1 = state.shoreline.iterator();
		while(it1.hasNext()) {
			ArrayList<SandBlock> ar = (ArrayList<SandBlock>) it1.next();
			Iterator<SandBlock>it2 = ar.iterator();
			while(it2.hasNext()) {
				SandBlock sh = (SandBlock) it2.next();
				if(sh.equals((SandBlock) s)) {
					switch (type) {
					case 1: state.placementMode = 0; return (new RockWall(s.getXLoc(), s.getYLoc()));
					case 2: state.placementMode = 0; return (new OysterGabion(s.getXLoc(), s.getYLoc()));
					case 3: state.placementMode = 0; return (new CordGrass(s.getXLoc(), s.getYLoc()));
					}
				}
			}
		}
		state.placementMode = 0;
		return null;
	}
}
