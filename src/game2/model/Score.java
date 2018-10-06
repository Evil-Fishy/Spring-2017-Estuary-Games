package game2.model;

import java.io.Serializable;

/**
 * Holds the name and the score to be used in the Game1 scoreboard
 * It also can be sorted by the time that the score has
 */
public class Score implements Comparable<Score>, Serializable {
	
	private String name;
	private double time;
	
	/**
	 * Constructs a score using the given name and time.
	 * 
	 * @param name The name of the person
	 * @param time The time of the person
	 */
	public Score(String name, double time) {
		this.name = name;
		this.time = time;
	}
	
	/**
	 * Compares the scores based on the time they get.
	 * 
	 * @param o - the score to compare this Score to
	 * @return an int representing the comparison between this and the given score
	 */
	@Override
	public int compareTo(Score o) {
		return Double.compare(time, o.time);
	}

	
	/*
	 * Getters for class properties
	 */
	public String getName() {
		return name;
	}
	
	public double getTime() {
		return time;
	}

}
