package game2.model;

import game2.view.G2Image;

/**
 * Defines a crab in-game used to pick up resources or build obstacles.
 * 
 * @author Christof Wittreich
 *
 */
public class Crab {
	private Mini2State state;
	private int xLoc;
	private int yLoc;
	private static int width = G2Image.CRAB.getWidth();
	private static int height = G2Image.CRAB.getHeight();
	private boolean isBusy;
	private int[] coords;
	private Resource carriedResource;
	private Obstacle carriedObstacle;
	private boolean hasResource;
	private boolean hasObstacle;
	private int[] home;
	private int[] rock;
	private int[] oyster;
	private int[] grass;
	private int[] obCoords;
	private static double velocity = 1.4;
	private int busyImgXOffset = 50;
	private int busyImgYOffset = -30;
	
	/**
	 * Creates an instance of Crab.
	 * 
	 * @param xLoc - x location of the home of the crab
	 * @param yLoc - y location of the home of the crab
	 * @param s - the Mini2State class, used to get information about the location of the resource piles
	 */
	public Crab(int xLoc, int yLoc, Mini2State s){
		state = s;
		home = new int[2];
		home[0] = xLoc;
		home[1] = yLoc;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		coords = new int[2];
		coords[0] = xLoc;
		coords[1] = yLoc;
		carriedResource = null;
		carriedObstacle = null;
		rock = new int[2];
		rock[0] = state.rockX;
		rock[1] = state.rockY;
		oyster = new int[2];
		oyster[0] = state.oysterX;
		oyster[1] = state.oysterY;
		grass = new int[2];
		grass[0] = state.grassX;
		grass[1] = state.grassY;
		obCoords = new int[2];
	}
	
	/**
	 * Moves the crab towards it's destination defined by coords.
	 */
	public void move() {
		if (xLoc < coords[0]) {
			xLoc += velocity;
			if (xLoc > coords[0]) {
				xLoc = coords[0];
			}
		} else if (xLoc > coords[0]) {
			xLoc -= velocity;
			if (xLoc < coords[0]) {
				xLoc = coords[0];
			}
		}
		if (yLoc < coords[1]) {
			yLoc += velocity;
			if (yLoc > coords[1]) {
				yLoc = coords[1];
			}
		} else if (yLoc > coords[1]) {
			yLoc -= velocity;
			if (yLoc < coords[1]) {
				yLoc = coords[1];
			}
		}
	}
	
	/**
	 * Sends the crab back to it's home location.
	 * Home location is based on the coords if it is carrying an obstacle or resource
	 */
	public void sendHome() {
		if (carriedResource != null) {
			if (carriedResource instanceof Rock) {
				coords[0] = rock[0];
				coords[1] = rock[1];
			} else if (carriedResource instanceof Oyster) {
				coords[0] = oyster[0];
				coords[1] = oyster[1];
			} else if (carriedResource instanceof Grass) {
				coords[0] = grass[0];
				coords[1] = grass[1];
			}
		} else if (carriedObstacle != null) {
			if (carriedObstacle instanceof RockWall) {
				coords[0] = rock[0];
				coords[1] = rock[1];
			} else if (carriedObstacle instanceof OysterGabion) {
				coords[0] = oyster[0];
				coords[1] = oyster[1];
			} else if (carriedObstacle instanceof CordGrass) {
				coords[0] = grass[0];
				coords[1] = grass[1];
			}
		} else {
			coords[0] = home[0];
			coords[1] = home[1];
		}
	}
	
	/**
	 * Changes the current coordinates to the location where the sandblock
	 * it needs to place an obstacle on
	 */
	public void switchCoords() {
		coords[0] = obCoords[0];
		coords[1] = obCoords[1];
		obCoords[0] = home[0];
		obCoords[1] = home[1];
	}
	
	public int getXLoc() {
		return xLoc;
	}

	public int getYLoc() {
		return yLoc;
	}

	public boolean getBusy() {
		return isBusy;
	}
	
	public void setBusy(boolean b) {
		isBusy = b;
	}
	
	public int[] getCoords() {
		return coords;
	}
	
	public void setCoords(int[] coords) {
		this.coords = coords;
	}

	public Resource getCarriedResource() {
		return carriedResource;
	}
	
	public int[] getObCoords() {
		return obCoords;
	}
	
	public void setObCoords(int[] coords) {
		this.obCoords = coords;
	}

	public void setCarriedResource(Resource re) {
		carriedResource = re;
	}

	public boolean getHasResource() {
		return hasResource;
	}

	public void setHasResource(boolean b) {
		this.hasResource = b;
	}	

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	public Obstacle getCarriedObstacle() {
		return carriedObstacle;
	}

	public void setCarriedObstacle(Obstacle ob) {
		this.carriedObstacle = ob;
	}
	
	public static double getVelocity() {
		return velocity;
	}

	public static void setVelocity(int i) {
		velocity = i;
	}

	public boolean getHasObstacle() {
		return hasObstacle;
	}

	public void setHasObstacle(boolean b) {
		this.hasObstacle = b;
	}
	
	public int getBusyImgXOffset(){return busyImgXOffset;}
	
	public int getBusyImgYOffset(){return busyImgYOffset;}

}