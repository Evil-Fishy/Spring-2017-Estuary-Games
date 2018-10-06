package game1.model;

import java.util.ArrayList;

import master.model.Arrow;
import master.model.Board;

/**
 * A class to manage the state of game1. 
 * It stores data about the objects in the game and 
 * the game's current status.
 */
public class Mini1State {
	
	// Finals
	public static final double yVelDefault = 1;	
	
	// Objects in the world
	public Board board;
	public Crab crab;
    public Arrow arrow;
    public ArrayList<Obstacle> obstacles;
    public boolean isCollided;
    public boolean isEnd;
    public boolean isTutorial;
    
    // Scoreboard things
    public long startTime;
    public long endTime;
    public Score lastScore;
    public ArrayList<Score> scores;
    
    // Current penalty
    public int secPenalty;
    
    // Current velocity
 	public double velocity;
    
    // Position of the background
    public int backgroundPosn;
    public int endingPosn;
	
}
