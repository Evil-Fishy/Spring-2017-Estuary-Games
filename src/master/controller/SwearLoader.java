package master.controller;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SwearLoader {

	private static String saveFile = "resources/swearWords.txt";
	
	/**
	 * This will try to load swear word list
	 * This will normally happen on the first run
	 * @return Arraylist of string of swear words
	 */
	public static List<String> loadSwear() {
		try {
			Path path = FileSystems.getDefault().getPath(saveFile);
			return Files.readAllLines(path);		
		} catch (IOException e) {
			System.err.println("Unable to load swear words from file, missing file...");
		}
		// Else return an empty score array
		System.err.println("Initializing with empty swear words");
		return new ArrayList<String>();		
	}	
	
}
