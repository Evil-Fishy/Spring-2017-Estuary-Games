package master.model;

import java.util.Random;

public enum Direction {
	N (0, "north"), 
	NE (1, "northeast"), 
	E (2, "east"),
	SE (3, "southeast"), 
	S (4, "south"), 
	SW (5, "southwest"), 
	W (6, "west"), 
	NW (7, "northwest");
	
	private static Random rng = new Random();
	private final int intRep;
	private final String strRep;
	
	Direction(int intRep, String strRep) {
		this.intRep = intRep;
		this.strRep = strRep;
	}
		
	/**
	 * Generates a random direction
	 * @return A new direction in a random direction
	 */
	public static Direction getRandomDirection() {
		return values()[rng.nextInt(values().length)];
	}
	
	/**
	 * This method will take a given compass direction and return one of the closest cardinal directions (North, South, East, or West) to the given direction.
	 * Note that if the given Direction is already East or West, then the function doesn't change it.
	 * @param d the given direction
	 * @return the closest cardinal direction to the given compass direction
	 */
	public static Direction switchToCardinalDirection(Direction d) {
		if(d.contains('N'))
			return N;
		else if(d.contains('S'))
			return S;
		else
			return d;
	}
	
	public static Direction flipDirection(Direction d) {
		return values()[(d.toInt() + 4) % values().length];
	}
	
	/**
	 * Checks to see if the current direction has
	 * Some component of the passed char
	 * I.E SE is moving in the S and the E directions
	 * 
	 * @param c The char of the direction we are going to check
	 * @return True if the current direction is built of that subdirection
	 */
	public boolean contains(char c) {
		for(char k : name().toCharArray())
			if(k == c)
				return true;
		return false;
	}
	
	public int toInt() {
		return intRep;
	}
	
	public String toString() {
		return strRep;
	}
}

