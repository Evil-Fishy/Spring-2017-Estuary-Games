package game1.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import game1.model.Score;

/**
 * Nice helper class that allows for loading of scoreboard information to and from file
 * This will allow for saving of the state to file
 * Original code from here: http://stackoverflow.com/a/16111797/7718197
 */
public class ScoreLoader {

	private static String saveFile = "scores1.dat";
	
	/**
	 * This will try to load the score list from file
	 * If it is unable to load it, it will return an empty scoreboard for the system top use
	 * This will normally happen on the first run
	 * @return Arraylist of saves scores
	 */
	public static ArrayList<Score> loadScores() {
		try {
			FileInputStream fis = new FileInputStream(saveFile);
			ObjectInputStream ois = new ObjectInputStream(fis);
			ArrayList<Score> scores = (ArrayList<Score>) ois.readObject();
			ois.close();
			// Return our scores
			return scores;			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Unable to load from file, unknown cast type...");
		} catch (IOException e) {
			System.out.println("Unable to load from file, missing scores file...");
		}
		// Else return an empty score array
		System.out.println("Initializing with empty scoreboard");
		return new ArrayList<Score>();		
	}
	
	/**
	 * This will save the arraylist of scores to file
	 * It will serialize the scores and write them to disk
	 * @param scores An array list of scores to be written to file
	 */
	public static void saveScores(ArrayList<Score> scores) {
		try {
			FileOutputStream fos = new FileOutputStream(saveFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(scores);
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
