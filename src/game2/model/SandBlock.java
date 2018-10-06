package game2.model;

import game2.view.G2Image;

/**
 * Defines an instance of SandBlock. SandBlocks are generated in a grid to represent a shoreline.
 * They can hold obstacles on them to help defned them from wakes, which damage them.
 * 
 * @author Tristan Morris, Christof Wittreich
 * 
 */
public class SandBlock {
	private int xLoc;
	private int yLoc;
	private static int width = G2Image.SANDBLOCK.getWidth();
	private static int height = G2Image.SANDBLOCK.getHeight();
	
	private int health;
	private Obstacle obs;
	private boolean isPlacement;
	private boolean isDestroyed;
	private String damage;
	private boolean isAffected;
	private long displayTime;
	
	/**
	 * Creates an instance of SandBlock.
	 * 
	 * @param xLoc - the x location of the SandBlock
	 * @param yLoc - the y location of the SandBlock
	 */
	public SandBlock(int xLoc, int yLoc){
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		this.health = 7;
		this.obs = null;
		this.isAffected = false;
		this.displayTime = -1;
	}
	
	/**
	 * Damages the SandBlock based on the damage of the wake that hits it.
	 * If the SandBlock runs out of health, isDestroyed is set to true.
	 * 
	 * @param amount - the amount of damage the SanbBlock is taking
	 */
	public void reduceHealth(int amount) {
		if (this.obs == null) {
			this.health -= amount;
			if (this.isDestroyed) {
				this.health = 0;
				return;
			} else if (this.health < 0) {
				this.health = 0;
			} else if (this.health > 7) {
				this.health = 7;
				return;
			}
		} else {
			if (amount > 0) {
				this.obs.reduceHealth(amount);
			} else {
				this.health -= amount;
			}
			if (this.obs.getHealth() <= 0) {
				this.obs = null;
			} else if (this.health > 7) {
				this.health = 7;
				return;
			}
		}
		this.damage = Integer.toString(-amount);
		this.isAffected = true;
	}

	public int getXLoc() {
		return xLoc;
	}

	public int getYLoc() {
		return yLoc;
	}
	
	public int getHealth(){
		return health;
	}
	
	public Obstacle getObs() {
		return obs;
	}
	
	public void setObs(Obstacle o) {
		this.obs = o;
	}
	
	public boolean isPlacement() {
		return isPlacement;
	}

	public void setPlacement(boolean isPlacement) {
		this.isPlacement = isPlacement;
	}
	
	public boolean getDestroyed() {
		return isDestroyed;
	}

	public void setDestroyed(boolean b) {
		this.isDestroyed = b;
	}
	
	public String getDamage() {
		return damage;
	}

	public boolean getAffected() {
		return isAffected;
	}

	public void setAffected(boolean isAffected) {
		this.isAffected = isAffected;
	}

	public long getDisplayTime() {
		return displayTime;
	}

	public void setDisplayTime(long displayTime) {
		this.displayTime = displayTime;
	}
	
	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}
}