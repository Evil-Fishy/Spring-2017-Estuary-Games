package game2.view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import game1.view.G1Image;
import game2.controller.ScoreLoader;
import game2.model.Score;
import game2.controller.CrabManager;
import game2.controller.Mini2Timer;
import game2.controller.ResourceManager;
import game2.model.Boat;
import game2.model.CordGrass;
import game2.model.Crab;
import game2.model.Grass;
import game2.model.LargeBoat;
import game2.model.LargeWake;
import game2.model.Mini2State;
import game2.model.Obstacle;
import game2.model.Oyster;
import game2.model.OysterGabion;
import game2.model.Resource;
import game2.model.Rock;
import game2.model.RockWall;
import game2.model.SandBlock;
import game2.model.SmallBoat;
import game2.model.SmallWake;
import game2.model.Tutorial;
import game2.model.Wake;
import master.controller.MainController;
import master.controller.SwearLoader;
import master.model.Board;
import master.view.DrawOps;
import master.view.ExitButtonUI;
import master.view.MiniGame;

/**
 * Defines MiniTwo, an extension of MiniGame that starts and paints game2.
 *
 */
public class MiniTwo extends MiniGame implements ActionListener, MouseListener {
	
	//UI controls
	private JButton btnExit = new JButton();
	private JButton btnRock = new JButton();
	private JButton btnOyster = new JButton();
	private JButton btnGrass = new JButton();
	private JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private static GridBagLayout layout = new GridBagLayout();
    private static GridBagConstraints c = new GridBagConstraints();
	private int nOfGridCells = 3;
	private boolean gameOver;
	private Mini2State state;
	private Board board;
	private Tutorial tutorial;
	private ResourceManager resourceM;
	private CrabManager crabM;
	private Timer timerMini2;
	private int fontSize;
	private int btnWidth = 64;
	private int btnHeight = 32;
	
	private List<String> swearWords;

	/**
	 * Creates an instance of MiniTwo
	 */
	public MiniTwo(MainController mainController) {
		// Call the super constructor
		super(mainController);
		
		state = new Mini2State();
    	state.board = new Board(mainController.getWidth(), mainController.getHeight());
    	board = state.board;
    	tutorial = new Tutorial();    	
		resourceM = new ResourceManager(state);
		crabM = new CrabManager(state);
		
		// Load in swear words
		swearWords = SwearLoader.loadSwear();
		
		state.scores = ScoreLoader.loadScores();
		Collections.sort(state.scores);
		
		// Layout and scale
		this.setLayout(null);
		scaleScreenItems();
				
		//set Image Icons prior to scaling
		btnRock.setIcon(new ImageIcon(G2Image.ROCK_OBS2.getImg()));
		btnOyster.setIcon(new ImageIcon(G2Image.OYSTER_OBS2.getImg()));
		btnGrass.setIcon(new ImageIcon(G2Image.GRASS_OBS2.getImg()));
		
		btnRock.setPreferredSize(new Dimension(G2Image.ROCK_OBS2.getWidth()/2, G2Image.ROCK_OBS2.getHeight()));
		btnOyster.setPreferredSize(new Dimension(G2Image.OYSTER_OBS2.getWidth()/2, G2Image.OYSTER_OBS2.getHeight()));
		btnGrass.setPreferredSize(new Dimension(G2Image.GRASS_OBS2.getWidth()/2, G2Image.GRASS_OBS2.getHeight()));
		
		btnWidth = G2Image.ROCK_OBS2.getWidth()/2;
		btnHeight = G2Image.ROCK_OBS2.getHeight();
		
		// Set the font color and size
		// Also center to text
		btnRock.setForeground(Color.WHITE);
		btnRock.setFont(new Font("TimesRoman", Font.BOLD, fontSize));
		btnRock.setHorizontalTextPosition(JButton.CENTER);
		btnRock.setVerticalTextPosition(JButton.CENTER);
		btnRock.setText("0");
		
		btnOyster.setForeground(Color.WHITE);
		btnOyster.setFont(new Font("TimesRoman", Font.BOLD, fontSize));
		btnOyster.setHorizontalTextPosition(JButton.CENTER);
		btnOyster.setVerticalTextPosition(JButton.CENTER);
		btnOyster.setText("0");
		
		btnGrass.setForeground(Color.WHITE);
		btnGrass.setFont(new Font("TimesRoman", Font.BOLD, fontSize));
		btnGrass.setHorizontalTextPosition(JButton.CENTER);
		btnGrass.setVerticalTextPosition(JButton.CENTER);
		btnGrass.setText("0");
		
		// Add the buttons to the screen
		btnPanel.add(btnRock);
		btnPanel.add(btnOyster);
		btnPanel.add(btnGrass);		
		btnPanel.setOpaque(false);

        setLayout(layout);
        c.weightx = 0.1;
        c.weighty = 0.1;
        for(int i = 0; i < nOfGridCells; i++){
            for(int j = 0; j < nOfGridCells; j++) {    			
                c.gridx = i;
                c.gridy = j;
                if(i == 2 && j == 0) {
                	c.anchor = GridBagConstraints.NORTHEAST;
                	add(btnExit, c);
                	c.anchor = GridBagConstraints.CENTER;
                }
                else if(i == 0 && j == 2) {
                	c.gridwidth = 2;
                	c.anchor = GridBagConstraints.SOUTHWEST;
                	add(btnPanel, c);
                	c.anchor = GridBagConstraints.CENTER;
                	c.gridwidth = 0;
                }
                else {
                	add(Box.createRigidArea(new Dimension(board.getWidth()/nOfGridCells, 
                			board.getHeight()/nOfGridCells)), c);
                }
            }
        }           
		
        btnExit.setUI(new ExitButtonUI());
		btnExit.addActionListener(this);
		btnRock.addActionListener(this);
		btnOyster.addActionListener(this);
		btnGrass.addActionListener(this);
		//Add mouse listener
		this.addMouseListener(this);
	}
	
	/**
	 * Sets the state of minigame 2 to the starting conditions.
	 */
	@Override
	public void startup() {
		
		gameOver = false;
		
		btnRock.setVisible(true);
		btnOyster.setVisible(true);
		btnGrass.setVisible(true);
		
    	// Add obstacles to the array
    	state.resources = new ArrayList<Resource>();
    	createShoreline();
    	
    	state.boats = new ArrayList<Boat>();
    	state.wakes = new ArrayList<Wake>();
    	state.smallWakes = new ArrayList<SmallWake>();
    	state.largeWakes = new ArrayList<LargeWake>();
    	
    	state.crabs = new ArrayList<Crab>();
    	state.crabs.add(new Crab((int) (state.board.getWidth() / 2 - 1.5 * Crab.getWidth()), 
    			(state.board.getHeight() - Crab.getHeight()), state));
    	state.crabs.add(new Crab((int) (state.board.getWidth() / 2 - .5 * Crab.getWidth()), 
    			(state.board.getHeight() - Crab.getHeight()), state));
    	state.crabs.add(new Crab((int) (state.board.getWidth() / 2 + .5 * Crab.getWidth()), 
    			(state.board.getHeight() - Crab.getHeight()), state));
    	
		timerMini2 = new Timer();
		timerMini2.scheduleAtFixedRate(new Mini2Timer(this, state, tutorial), 0, 10);
		
		tutorial.resetTutorial();
		
		state.grassNum = 0;
		state.rockNum = 0;
		state.oysterNum = 0;
		

	}

	/**
	 * Shuts game 2 down.
	 */
	@Override
	public void shutdown() {
		if(timerMini2 != null) timerMini2.cancel();
	}
	
	/**
	 * Creates a shore with a grid of SandBlocks
	 */
	public void createShoreline() {
		BufferedImage img = G2Image.SANDBLOCK.getImg();
		state.shoreline = new ArrayList<ArrayList<SandBlock>>();
		state.blocksDestroyed = new ArrayList<Integer>();
		for(int x=0; x<=state.shoreRowsNum; x++) {
			state.shoreline.add(new ArrayList<SandBlock>());
			state.blocksDestroyed.add(0);
			for(int y=1;y<state.shoreColsNum;y++){
				state.shoreline.get(x).add(new SandBlock(x*img.getWidth(),(state.board.getHeight()-(y+1)*img.getHeight())));
			}
		}
	}
	

	/**
	 *  Add custom painting, used to render background once for the panel
	 *  Paints everything needed in the game
	 */
	@Override
	protected void paintComponent(Graphics g) {
		
		// Call the super class
		super.paintComponent(g);
		//super.paint(g);
		
		// Handle game over logic
		if (!gameOver) {
			
	    	//Draw the dock, and background
			DrawOps.drawScaledImage(g, G2Image.BACKGROUND.getImg(), this);
			g.drawImage(G2Image.DOCK.getImg(), 0, state.board.getHeight()-G2Image.DOCK.getHeight(), null);
		
			// Next lets draw our other components
			Graphics2D g2 = (Graphics2D) g;
			fontSize = 36;
	    	g.setFont(new Font("default", Font.BOLD, fontSize));
	   	
	    	//Draw all small wakes
			Iterator<SmallWake> itS = state.smallWakes.iterator();
			SmallWake smallWake;
			while(itS.hasNext()) {
				try {
					smallWake = itS.next();
				} catch (ConcurrentModificationException e) {
					break;
				}
	    		g.drawImage(G2Image.SMALLWAKE.getImg(), smallWake.getXLoc(), smallWake.getYLoc(), null);
			}
			
			// Draw all large wakes
			Iterator<LargeWake> itL = state.largeWakes.iterator();
			LargeWake largeWake;
			while(itL.hasNext()) {
				try {
					largeWake = itL.next();
				} catch (ConcurrentModificationException e) {
					break;
				}
	    		g.drawImage(G2Image.LARGEWAKE.getImg(), largeWake.getXLoc(), largeWake.getYLoc(), null);
			}
			
	    	// Draw all the Boats
			Iterator<Boat> it1 = state.boats.iterator();
			while(it1.hasNext()) {
				Boat boat = it1.next();
	    		if(boat instanceof SmallBoat) {
	    			g.drawImage(G2Image.SMALLBOAT.getImg(), boat.getXLoc(), boat.getYLoc(), null);
	    		} else if(boat instanceof LargeBoat) {
	    			g.drawImage(G2Image.LARGEBOAT.getImg(), boat.getXLoc(), boat.getYLoc(), null);
	    		}
	    	}
			
			// Next we are drawing obstacles
			Obstacle o;
			BufferedImage img = null;
			
	    	// Draw all the obstacle that are on the shoreline
			// Note we also draw the sandblocks here
	    	for (ArrayList<SandBlock> ar: state.shoreline) {
		    	for (SandBlock block: ar){
		    		int obsHealth = 0, obsWidth = Obstacle.getDefaultWidth(), obsHeight = Obstacle.getDefaultHeight();
		    		if (block.getHealth() != 0) {
			    		o = block.getObs();
		    			g.drawImage(G2Image.SANDBLOCK.getImg(), block.getXLoc(), block.getYLoc(), null);
		    			
		    			if (o != null) {
				    		if (o instanceof RockWall)
				    			img = G2Image.ROCK_OBS.getImg();
				    		else if (o instanceof OysterGabion)
				    			img = G2Image.OYSTER_OBS.getImg();
				    		else if (o instanceof CordGrass)
				    			img = G2Image.GRASS_OBS.getImg();
				    		
				    		g.drawImage(img,
			    					block.getXLoc() + (G2Image.SANDBLOCK.getWidth() - o.getWidth())/2, 
			    					block.getYLoc() + (G2Image.SANDBLOCK.getHeight() - o.getHeight())/2,
			    					null);
				    		obsHealth = o.getHealth();
				    		obsWidth = o.getWidth();
				    		obsHeight = o.getHeight();
			    		}
	 			
			    		if (block.isPlacement()) {
			    			g.drawImage(G2Image.PLACEMENT.getImg(),
			    					block.getXLoc() + (G2Image.SANDBLOCK.getWidth() - obsWidth)/2, 
			    					block.getYLoc() + (G2Image.SANDBLOCK.getHeight() - obsHeight)/2,
			    					null);
			    			if (!tutorial.getIsDone()) {
			    				g.drawImage(G2Image.POINTER.getImg(), block.getXLoc(), block.getYLoc(),null);
			    			}
			    		}
	
		    			g2.setColor(Color.WHITE);
			    		g2.setFont(new Font("TimesRoman", Font.PLAIN, (int)(0.75*fontSize)));
		    			g2.drawString("HP:" + (block.getHealth() + obsHealth), block.getXLoc() + 105, block.getYLoc() + 45); //TEMPORARY
		    			
			    		if (block.getAffected()) {
				    		if (block.getDamage().equals("1")) {
								g2.setColor(Color.GREEN);
					    		g2.setFont(new Font("TimesRoman", Font.BOLD, fontSize));
								g2.drawString("+" + block.getDamage(), block.getXLoc() + 115, block.getYLoc() - 10);
							} else if (!block.getDamage().equals("0")) {
								g2.setColor(Color.RED);
					    		g2.setFont(new Font("TimesRoman", Font.BOLD, fontSize));
								g2.drawString(block.getDamage(), block.getXLoc() + 115, block.getYLoc() - 10);
							}
			    		}
			    	}		    	
		    	}
	    	}
	    	
	    	// Draw all resources
	    	for (Resource re : state.resources) {
	    		if (re instanceof Rock && re.getClickable()) g.drawImage(G2Image.ROCK_PICKUP.getImg(), re.getXLoc(), re.getYLoc(), this);
	    		else if (re instanceof Oyster && re.getClickable()) g.drawImage(G2Image.OYSTER_PICKUP.getImg(), re.getXLoc(), re.getYLoc(), this);
	    		else if (re instanceof Grass && re.getClickable()) g.drawImage(G2Image.GRASS_PICKUP.getImg(), re.getXLoc(), re.getYLoc(), this);
	    		if(!tutorial.getIsDone()){
	    			g.drawImage(G2Image.POINTER.getImg(), re.getXLoc() - G2Image.POINTER.getWidth(), re.getYLoc(), null);
	    		}
	    	}
	    	
	    	// Make all 2d graphics 30% opaque after this point
	    	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.3));
	    	for (Resource re : state.resources) {
	    		if (re instanceof Rock && !re.getClickable()) g2.drawImage(G2Image.ROCK_PICKUP.getImg(), re.getXLoc(), re.getYLoc(), this);
	    		else if (re instanceof Oyster && !re.getClickable()) g2.drawImage(G2Image.OYSTER_PICKUP.getImg(), re.getXLoc(), re.getYLoc(), this);
	    		else if (re instanceof Grass && !re.getClickable()) g2.drawImage(G2Image.GRASS_PICKUP.getImg(), re.getXLoc(), re.getYLoc(), this);
	    	}
	    	
	    	// Make all 2d graphics 100% opaque again after this point
	    	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 1.0));
	    	
	      	// Draw all the Crabs
			Iterator<Crab> it2 = state.crabs.iterator();
			while(it2.hasNext()) {
				Crab crab = it2.next();
	    		g.drawImage(G2Image.CRAB.getImg(), crab.getXLoc(), crab.getYLoc(), null);
	    		if(crab.getBusy()){
	    			g.drawImage(G2Image.BUSY.getImg(), (int) (crab.getXLoc() + crab.getBusyImgXOffset()/1.4), crab.getYLoc() + crab.getBusyImgYOffset() - 11, null);
	    		}
	    	}
			
			// Calculate where buttons go
			int btnSpacing = 4;
			g.setColor(Color.BLACK);
			double scaleFactor = (double) state.board.getWidth()/state.shoreColsNum/G2Image.SANDBLOCK.getWidth();//Can we not do this every frame?
			
			//Draw arrows on obstacle stockpiles
			if(!tutorial.getIsDone()){
				switch(tutorial.getStepNumber()){
				case 1://start
					g.setFont(new Font("TimesRoman", Font.PLAIN, (int)(4*fontSize)));
					DrawOps.drawHozCenteredString(g, "Tutorial", this, this.board.getHeight()/2);
					break;
				case 3://grass
					// Draw left arrow
		    		AffineTransform trans1 = new AffineTransform();
		    		trans1.setToTranslation((int) (3*btnSpacing + 2.5*scaleFactor*btnWidth),(int) (state.board.getHeight() - G2Image.DOCK.getHeight() - G2Image.POINTER.getHeight()));
		    		trans1.rotate(Math.toRadians(90));
		    		// Display
		    		((Graphics2D)g).drawImage(G2Image.POINTER.getImg(), trans1, this);
					break;
				case 8://oyster gabion
					// Draw left arrow
		    		AffineTransform trans2 = new AffineTransform();
		    		trans2.setToTranslation((int) (3*btnSpacing + 1.5*scaleFactor*btnWidth),(int) (state.board.getHeight() - G2Image.DOCK.getHeight() - G2Image.POINTER.getHeight()));
		    		trans2.rotate(Math.toRadians(90));
		    		// Display
		    		((Graphics2D)g).drawImage(G2Image.POINTER.getImg(), trans2, this);
					break;
				case 12://rock wall
					// Draw left arrow
		    		AffineTransform trans3 = new AffineTransform();
		    		trans3.setToTranslation((int) (3*btnSpacing + 0.5*scaleFactor*btnWidth),(int) (state.board.getHeight() - G2Image.DOCK.getHeight() - G2Image.POINTER.getHeight()));
		    		trans3.rotate(Math.toRadians(90));
		    		// Display
		    		((Graphics2D)g).drawImage(G2Image.POINTER.getImg(), trans3, this);
					break;
				case 15://start
					g.setFont(new Font("TimesRoman", Font.PLAIN, (int)(4*fontSize)));
					DrawOps.drawHozCenteredString(g, "Game Start", this, this.board.getHeight()/2);
					g.setFont(new Font("TimesRoman", Font.PLAIN, (int)(2*fontSize)));
					DrawOps.drawHozCenteredString(g, "Defend the Shoreline!", this, this.board.getHeight()/2 + 75);
					break;
				}
			}			
	    	
			// Update resource amount
			btnRock.setText("" + state.rockNum);
			btnOyster.setText("" + state.oysterNum);
			btnGrass.setText("" + state.grassNum);
    	
    	}
		// Show the scoreboard to the user
		else {
			// Draw the nice background for them
			DrawOps.drawScaledImage(g, G1Image.LEADERBOARD_BG.getImg(), this);
			fontSize = board.getWidth()/20;
    		// Set text details
    		g.setColor(Color.WHITE);
        	g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize)); 
    		
    		// Loop through 5 scores if we have them
        	// Always display 5 high scores
        	Score s;
    		for(int i=0; i<5; i++) {
    			// If we have a score then display it
    			if(i < state.scores.size()) {
    				s = state.scores.get(i);
    				DrawOps.drawHozCenteredString(g, s.getName() + " - " + s.getTime(), this, fontSize*i+this.getHeight()/3);
    			}
    			// Else display it as unknown user
    			else {
    				DrawOps.drawHozCenteredString(g, "EMPTY - ????", this, fontSize*i+this.getHeight()/3);
    			}
    		}
    		
    		// Set text details
    		g.setColor(Color.RED);
        	g.setFont(new Font("TimesRoman", Font.BOLD, fontSize)); 
    		
    		// Finally display our score at the top
        	if(state.lastScore != null) {
        		DrawOps.drawHozCenteredString(g, state.lastScore.getName()+" - "+state.lastScore.getTime(), this, (int)(1.5*fontSize));
        	}
    	}
		
		// Change the button color based on if we have enough resources
		// If we do, display the colored version
		if(state.grassNum>=state.grassObstacleAmount){
			btnGrass.setIcon(new ImageIcon(G2Image.GRASS_OBS.getImg()));
		}
		else{
			btnGrass.setIcon(new ImageIcon(G2Image.GRASS_OBS2.getImg()));
		}
		
		if(state.oysterNum>=state.oysterObstacleAmount){
			btnOyster.setIcon(new ImageIcon(G2Image.OYSTER_OBS.getImg()));
		}
		else{
			btnOyster.setIcon(new ImageIcon(G2Image.OYSTER_OBS2.getImg()));
		}
		
		if(state.rockNum>=state.rockObstacleAmount){
			btnRock.setIcon(new ImageIcon(G2Image.ROCK_OBS.getImg()));
		}
		else{
			btnRock.setIcon(new ImageIcon(G2Image.ROCK_OBS2.getImg()));
		}
		
	}
	
	
	/**
	 * Scales image files based on the size of the screen the game is being played on.
	 */
	private void scaleScreenItems() {
		int worldWidth = state.board.getWidth();
		
		// Calculate the font size
		fontSize = board.getHeight()/25;
		
		int newSmallBoatWidth = worldWidth/15; //smallboat is 1/15 the screenwidth 
		double scaleFactor = (double) newSmallBoatWidth/G2Image.SMALLBOAT.getWidth();		
		G2Image.SMALLBOAT.scaleByFactor(scaleFactor);
		
		int newLargeBoatWidth = worldWidth/3; //largeboat is 1/3 screenwidth
		scaleFactor = (double) newLargeBoatWidth/G2Image.LARGEBOAT.getWidth();
		G2Image.LARGEBOAT.scaleByFactor(scaleFactor);
		
		int newSandblockWidth = worldWidth/state.shoreColsNum;
		scaleFactor = (double) newSandblockWidth/G2Image.SANDBLOCK.getWidth();
		
		G2Image.SANDBLOCK.scaleByFactor(scaleFactor);
		G2Image.PLACEMENT.scaleByFactor(scaleFactor);
		G2Image.CRAB.scaleByFactor(scaleFactor);
		G2Image.BUSY.scaleByFactor(scaleFactor);
		
		G2Image.SMALLWAKE.scaleByFactor(scaleFactor);
		G2Image.LARGEWAKE.scaleByFactor(scaleFactor);
		
		G2Image.DOCK.scaleByFactor(scaleFactor);
		
		G2Image.ROCK_OBS.scaleByFactor(scaleFactor);
		G2Image.OYSTER_OBS.scaleByFactor(scaleFactor);
		G2Image.GRASS_OBS.scaleByFactor(scaleFactor);
		
		G2Image.ROCK_OBS2.scaleByFactor(scaleFactor);
		G2Image.OYSTER_OBS2.scaleByFactor(scaleFactor);
		G2Image.GRASS_OBS2.scaleByFactor(scaleFactor);
		
		btnWidth *= scaleFactor;
 		btnHeight *= scaleFactor;
 		
 		btnRock.setBounds(4, state.board.getHeight() - G2Image.ROCK_OBS.getHeight(), btnWidth, btnHeight);
 		btnOyster.setBounds(btnWidth + 8, state.board.getHeight() - G2Image.OYSTER_OBS.getHeight(), btnWidth, btnHeight);
 		btnGrass.setBounds(2*btnWidth + 12, state.board.getHeight() - G2Image.GRASS_OBS.getHeight(), btnWidth, btnHeight);
	
		state.rockX = btnRock.getX();
		state.rockY = btnRock.getY();
		state.oysterX = btnOyster.getX();
		state.oysterY = btnOyster.getY();
		state.grassX = btnGrass.getX();
		state.grassY = btnGrass.getY();
		
		Crab.setVelocity( (int) (Crab.getVelocity() * 1.8 * scaleFactor));
		Boat.setSpeed((int) (Boat.getSpeed() * 1.5 * scaleFactor));
		Boat.setDefaultSpawnY((int) (Boat.getDefaultSpawnY() * scaleFactor));
	}
	
	/**
	 * Calculates the score the player achieved in the game.
	 * Based on how many SandBLocks are left, the total health of the remaining SandBLocks,
	 * 	and how many resources the player collected.
	 * @return
	 */
	private double calcScore() {
		double score = 0;
		for (ArrayList<SandBlock> ar: state.shoreline) {
	    	for (SandBlock block: ar){
	    		if (!block.getDestroyed()) {
	    			score += 100;
	    			score += (block.getHealth() * 10);
	    			if (block.getObs() != null) {
	    				score += 10;
			    		if (block.getObs() instanceof RockWall)
			    			score += 5;
			    		else if (block.getObs() instanceof OysterGabion)
			    			score += 10;
			    		else if (block.getObs() instanceof CordGrass)
			    			score += 15;
	    			}
	    		}
	    	}
		}
		score += (state.rockNum * 5);
		score += (state.oysterNum * 2);
		score += (state.grassNum * 1);
		return score;
	}
	
	/**
	 * Ends the game, displays the endgame screen, and saves the player's score.
	 */
	public void endGame() {
		// Change the view
		if(timerMini2 != null) timerMini2.cancel();
		timerMini2 = null;
		gameOver = true;
		btnRock.setVisible(false);
		btnOyster.setVisible(false);
		btnGrass.setVisible(false);
		repaint();
		// Append our new score
		double score = calcScore();
		// Get the person's name
		String name = "UNKNOWN";
		boolean nameSuccess = false;
		// Loop till success
		while(!nameSuccess) {
			// Get the person's name
			name = JOptionPane.showInputDialog(this, "What's your name?");
			// Check if it has a bad word
			boolean hasBad = false;
			for(String word : name.split(" "))
				if(swearWords.contains(word))
					hasBad = true;
			// We have a success if we do not have a bad word
			nameSuccess = !hasBad;
		}
		
		state.lastScore = new Score(name,score);
		state.scores.add(state.lastScore);
		Collections.sort(state.scores);
		repaint();
		// Save to file
		ScoreLoader.saveScores(state.scores);
	}
	
	/**
	 * Determines what button the player clicked, and adjusts the game accordingly.
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == btnExit) {
			mainController.exitMini();
		}
		else if(event.getSource() == btnRock) {
			if (resourceM.btnEvent("Rock")) {
				state.placementMode = 1;
			}
		} else if(event.getSource() == btnOyster) {
			if (resourceM.btnEvent("Oyster")) {
				state.placementMode = 2;
			}
		} else if(event.getSource() == btnGrass) {
			if (resourceM.btnEvent("Grass")) {
				state.placementMode = 3;
			}
		}
	}
	
	/**
	 * Depending on what has been clicked, assignes the crabs to either pick up a Resource, or go place an Obstacle.
	 */
	@Override
	public void mouseClicked(MouseEvent event) {
		switch (state.placementMode) {
		case 0: crabM.assignCrabResource(resourceM.pickupEvent(event.getX(), event.getY())); break;
		case 1: crabM.assignCrabObstacle(resourceM.placeEvent(state.placementMode, event.getX(), event.getY())); break;
		case 2: crabM.assignCrabObstacle(resourceM.placeEvent(state.placementMode, event.getX(), event.getY())); break;
		case 3: crabM.assignCrabObstacle(resourceM.placeEvent(state.placementMode, event.getX(), event.getY())); break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

}

























