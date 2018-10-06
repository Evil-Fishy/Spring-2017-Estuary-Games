package game2.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import master.view.DrawOps;

/**
 * This enum defines an easy, compact way to store and retrieve the images 
 * used in MiniTwo without requiring a long list of image definitions in
 * the MiniTwo class.
 * 
 * (currently under construction)
 * 
 * @author Habibullah Aslam
 * @editors Natalie Rubin, Tristan Morris, Christof Wittreich
 * 
 *
 *
 */
public enum G2Image {
	
	BACKGROUND("ocean.jpg"),
	POINTER("SmallPointer.png"),
	DOCK("dock.png"),
	ROCK_PICKUP("rock-pickup.png"),
	OYSTER_PICKUP("oyster-pickup.png"),
	GRASS_PICKUP("grass-pickup.png"),
	ROCK_OBS("rock-obstacle.jpg"),
	OYSTER_OBS("oyster-obstacle.jpg"),
	GRASS_OBS("grass-obstacle.jpg"),
	ROCK_OBS2("rock-obstacle2.jpg"),
	OYSTER_OBS2("oyster-obstacle2.jpg"),
	GRASS_OBS2("grass-obstacle2.jpg"),
	SANDBLOCK("sandblock.png"),
	PLACEMENT("placement-border.png"),
	SMALLBOAT("small_boat.png"),
	LARGEBOAT("cargo_ship.png"),
	WAKE("wake.png"),
	SMALLWAKE("SmallWake.png"),
	LARGEWAKE("LargeWake.png"),
	CRAB("crab.png"),
	BUSY("Busy.png");
	
	private BufferedImage origImg, scaledImg;
	private final String filePath;
	private final String directory = "resources/game2/";
	
	/**
	 * Constructor. Each enum type made represents an image used in the game. 
	 * These images are loaded immediately before any game action occurs.
	 * 
	 * @param filename The name of the image within the directory
	 */
	G2Image(String filename) {
		filePath = directory + filename;
		try {
			origImg = ImageIO.read(new File(filePath));
		} catch(IOException e) {
			origImg = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB); //default img
			System.err.println("Couldn't find " + filePath);
			e.printStackTrace();
		}
		
		scaledImg = origImg;
	}
	
	/**
	 * Returns the image stored in this enum type. If the image has been scaled through
	 * one of the scaling methods, that scaled image will be returned. The original image
	 * can be returned by calling getOrigImg().
	 * 
	 * @return the image stored in this enum (will be scaled if a scaling method was called)
	 */
	public BufferedImage getImg() {
		return scaledImg;
	}
	
	/**
	 * Returns the original image that was stored upon construction of the enum type. If the
	 * image was scaled and you want access to the scaled instance, call getImg()
	 * 
	 * @return the original image stored in this enum (will never be scaled)
	 */
	public BufferedImage getOrigImg() {
		return origImg;
	}
	
	/**
	 * Changes the image stored in this enum to the given image. The scaled image will be reset 
	 * to the stored image.
	 * 
	 * @param image the image to store in this enum type
	 */
	public void setImg(BufferedImage image) {
		origImg = image;
		scaledImg = origImg;
	}
	
	/**
	 * Returns the width of the scaled image stored in this enum
	 * 
	 * @return the width of the scaled image stored in the enum (will be same as origImg if no scaling has happened)
	 */
	public int getWidth() {
		return scaledImg.getWidth();
	}
	
	/**
	 * Returns the height of the scaled image stored in this enum
	 * 
	 * @return the height of the scaled image stored in the enum (will be same as origImg if no scaling has happened)
	 */
	public int getHeight() {
		return scaledImg.getHeight();
	}
	
	/**
	 * Scales the ORIGINAL image stored in this enum to the new width and height given.
	 * 
	 * Please note that calling this method on this enum that has already been scaled will NOT rescale the
	 * scaled image, but rather it will do a fresh rescale on the original image stored at construction.
	 * 
	 * @param newWidth the width to scale the image in this enum to
	 * @param newHeight the height to scale the image in this enum to
	 */
	public void scaleToSize(int newWidth, int newHeight) {
		scaledImg = DrawOps.scaleImgToSize(origImg, newWidth, newHeight);
	}
	
	/**
	 * Scales the ORIGINAL image stored in this enum by the factor given.
	 * 
	 * Please note that calling this method on this enum that has already been scaled will NOT rescale the
	 * scaled image, but rather it will do a fresh rescale on the original image stored at construction.
	 * 
	 * @param factor the factor to scale the image by
	 */
	public void scaleByFactor(double factor) {
		scaledImg = DrawOps.scaleImgByFactor(origImg, factor);
	}
	
	/**
	 * Resets the scaledImg to the origImg (ie undoes any scaling of the image).
	 */
	public void reset() {
		scaledImg = origImg;
	}
	
}





