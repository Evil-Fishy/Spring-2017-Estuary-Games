package game3.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import game3.model.Story;

/**
 * Nice helper class that allows for loading of Storyboard information to and from file
 * This will allow for saving of the state to file
 * Original code from here: http://stackoverflow.com/a/16111797/7718197
 * 
 * Since the data files change, this is not testable.
 */
public class StoryLoader {

	private static String saveFile = "stories.dat";
	
	/**
	 * This will try to load the Story list from file
	 * If it is unable to load it, it will return an empty storylist for the system top use
	 * This will normally happen on the first run
	 * @return Arraylist of saved stories
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Story> loadStories() {
		try {
			FileInputStream fis = new FileInputStream(saveFile);
			ObjectInputStream ois = new ObjectInputStream(fis);
			ArrayList<Story> stories = (ArrayList<Story>) ois.readObject();
			ois.close();
			// Return our stories
			return stories;			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Unable to load from file, unknown cast type...");
		} catch (IOException e) {
			System.out.println("Unable to load from file, missing stories file...");
		}
		// Else return an empty Story array
		System.out.println("Initializing with empty Storyboard");
		return new ArrayList<Story>();		
	}
	
	/**
	 * This will save the arraylist of stories to file
	 * It will serialize the stories and write them to disk
	 * @param stories An array list of stories to be written to file
	 */
	public static void saveStories(ArrayList<Story> stories) {
		try {
			FileOutputStream fos = new FileOutputStream(saveFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(stories);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Unable to save to file, unknown file location...");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to save to file, unknown data type...");
		}		
	}
}
