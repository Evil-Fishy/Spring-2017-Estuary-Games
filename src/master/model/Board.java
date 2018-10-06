package master.model;

/**
 * Defines the board
 * As of right now, it just has a size
 */
public class Board {
	
    private int worldWidth;
    private int worldHeight;
    
    private final static int defaultWidth = 900;
    private final static int defaultHeight = 500;
    
    /**
     * Default constructor
     */
    public Board() {
    	this(defaultWidth, defaultHeight);
    }
    
    /**
     * Constructor that will make the board be
     * Size x and size y
     * @param x The desired width of the board
     * @param y The desired height of the board
     */
    public Board(int x, int y) {
    	this.worldWidth = x;
    	this.worldHeight = y;
    }
    
	public int getWidth() {
		return worldWidth;
	}

	public int getHeight() {
		return worldHeight;
	}  	
}
