 package game2.model;

import java.util.ArrayList;

import game2.model.Score;
import master.model.Board;

/**
 * TODO: CHANGE THIS CLASS!!!!
 * TODO: FIGURE OUT A BETTER WAY TO MANAGE THE STATE
 * TODO: REMOVE THE PUBLICS???
 * TODO: DON'T MAKE THIS LIKE A C STRUCT
 */
public class Mini2State {
	
	// Objects in the world
	public Board board;
    public ArrayList<Resource> resources;
	public ArrayList<ArrayList<SandBlock>> shoreline;
	public ArrayList<Boat> boats;
	public ArrayList<Wake> wakes;
	public ArrayList<SmallWake> smallWakes;
	public ArrayList<LargeWake> largeWakes;
	public ArrayList<Crab> crabs;
	public ArrayList<Integer> blocksDestroyed;
	
	public int placementMode = 0;
	
	public int shoreRowsNum = 5;
	public int shoreColsNum = 6;
    
    //Collected resource counts
    public int rockNum = 0;
    public int oysterNum = 0;
    public int grassNum = 0;
    
    //Number of resources required for obstacles
    public int rockObstacleAmount = 1;
    public int oysterObstacleAmount = 2;
    public int grassObstacleAmount = 3;
    
    public int rockX;
    public int rockY;
    public int oysterX;
    public int oysterY;
    public int grassX;
    public int grassY;
    
 // Scoreboard things
    public long startTime;
    public long endTime;
    public Score lastScore;
    public ArrayList<Score> scores;
}
