package game3.model;

import java.io.Serializable;

/**
 * Holds the name and the story to be used in the Game3 storyboard
 * It also can be sorted by the time that the story has
 * 
 * @author Habibullah Aslam
 */
public class Story implements Serializable {
	private static final long serialVersionUID = 185873525669555946L;
	private String name;
	private String story;
	private int[] cubePics;
	
	/**
	 * Constructs a story using the given name and time.
	 * 
	 * @param name The name of the person
	 * @param story The story of the person
	 * @param cubePics The cubePics of the person
	 */
	public Story(String name, String story, int[] cubePics) {
		this.name = name;
		this.story = story;
		this.cubePics = cubePics;
	}

	
	/*
	 * Getters for class properties
	 */
	public String getName() {
		return name;
	}
	
	public String getStory() {
		return story;
	}
	
	public int[] getCubePics() {
		return cubePics;
	}
}
