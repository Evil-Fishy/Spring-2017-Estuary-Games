package game3.model;

import java.util.ArrayList;
import java.util.List;

import master.model.Arrow;
import master.model.Board;

/**
 * A class to manage the state of game3. 
 * It stores data about the objects in the game and 
 * the game's current status with a few minor methods to 
 * manipulate them.
 * 
 * @author Habibullah Aslam
 */
public class Mini3State {
	
	// Objects in the world
	private Board board;
    public static final int numDice = 6;
    private List<Die> dice;
    private List<Marker> markers;
    public int[] storyPnlDiceXPosns;
    private boolean diceRolled, allDiceStopped, allDiceSet;
    public Arrow arrow;
    public ArrayList<Story> stories;
    	
    public Board getBoard() {
    	return board;
    }
        
    public void setBoard(Board b) {
    	board = b;
    }
    
	public boolean getDiceRolled(){
		return diceRolled;
	}
	
	public void setDiceRolled(boolean b){
		diceRolled = b;
	}
	
	public boolean getAllDiceStopped() {
		return allDiceStopped;
	}	
	
	public void setAllDiceStopped(boolean b){
		allDiceStopped = b;
	}
	
	public boolean getAllDiceSet() {
		return allDiceSet;
	}	
	
	public void setAllDiceSet(boolean b){
		allDiceSet = b;
	}
	
	public List<Die> getDice() {
		return dice;
	}	
	
	public void setDice(List<Die> dice){
		this.dice = dice;
	}
	
	public List<Marker> getMarkers() {
		return markers;
	}

	public void setMarkers(List<Marker> markers) {
		this.markers = markers;
	}

	/**
	 * Reorders the dice in the order that they are set in the markers.
	 */
	public void reorderDice() {
		dice = new ArrayList<Die>();
		for(int i = 0; i < getMarkers().size(); i++)
			dice.add(getMarkers().get(i).getDieContained());
	}
	
	/**
	 * Returns an array of the facePics on the dice present in the dice array.
	 * @return the array of facePics on the dice
	 */
	public int[] getFacePicsFromDice() {
		int[] pics = new int[numDice];
		for(int i = 0; i < numDice; i++)
			pics[i] = dice.get(i).getFacePic();
		return pics;
	}
	
}

