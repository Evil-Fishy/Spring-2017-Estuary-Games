package game1.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import game1.controller.Mini1Timer;
import game1.controller.ScoreLoader;
import game1.model.Crab;
import game1.model.Mini1State;
import game1.model.Obstacle;
import game1.model.Score;
import master.controller.MainController;
import master.controller.RenderTimer;
import master.controller.SwearLoader;
import master.model.Arrow;
import master.model.Board;
import master.view.ExitButtonUI;
import master.view.MiniGame;

public class MiniOne extends MiniGame implements ActionListener, MouseMotionListener {
	
	// UI controls
	private JButton btnScore = new JButton("Scoreboard");
	private JButton btnExit = new JButton("Exit");
	private JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
    
    // Timers
	private Timer timerRender;
	private Timer timerMini1;
    
    // Our state
    private Mini1State state;
    protected Board board;
    private List<String> swearWords;
    
    // Our views
    private ViewGame viewGame;
    private ViewScoreboard viewScore;
    
    //Layout
	private static GridBagLayout layout = new GridBagLayout();
    private static GridBagConstraints c = new GridBagConstraints();
	private int nOfGridCells = 5;
    private int fontSize;
    
	/**
	 * Default constructor
	 * @param mainController The master controller of the system
	 */
	public MiniOne(MainController mainController) {
		// Call the super constructor
		super(mainController);
		
		// Create state
		state = new Mini1State();
		state.board = new Board(mainController.getWidth(), mainController.getHeight());
		board = state.board;
		viewGame = new ViewGame(this);
		viewScore = new ViewScoreboard(this);
		
		fontSize = board.getHeight()/25;

		// Load in swear words
		swearWords = SwearLoader.loadSwear();

		btnExit.setUI(new ExitButtonUI());
		btnScore.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
		
		btnExit.addActionListener(this);
		btnScore.addActionListener(this);
		this.addMouseMotionListener(this);

//		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		btnPanel.setOpaque(false);
		btnPanel.add(btnScore);
		btnPanel.add(btnExit);
		
        setLayout(layout);
        c.weightx = 0.1;
        c.weighty = 0.1;
        for(int i = 0; i < nOfGridCells; i++){
            for(int j = 0; j < nOfGridCells; j++) {    			
                c.gridx = i;
                c.gridy = j;
                if(i == 3 && j == 0) {
                	c.gridwidth = 2;
                	c.anchor = GridBagConstraints.FIRST_LINE_END;
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
	}
	
	/**
	 * Starts the timers for the obstacle movement and render
	 * Also records the start time
	 */
	@Override
	public void startup() {
		
		// TEMP: Set example scores
		state.scores = ScoreLoader.loadScores();
		Collections.sort(state.scores);		
		
		// Start a new game
		startNewGame();
		
		// Create the timers
		timerRender = new Timer();
		timerRender.scheduleAtFixedRate(new RenderTimer(this), 0, 5);
		timerMini1 = new Timer();
		timerMini1.scheduleAtFixedRate(new Mini1Timer(this), 0, 10);		
		
		// Add the game view
		this.remove(viewScore);
		this.add(viewGame);
		viewGame.setBounds(0, 0, mainController.getWidth(), mainController.getHeight());
		
	}

	/**
	 * Shuts down all timers for the obstacles and render
	 */
	@Override
	public void shutdown() {
		if(timerRender != null) timerRender.cancel();
		if(timerMini1 != null) timerMini1.cancel();
		state.backgroundPosn = 0;
	}
	
	
	/**
	 * Will reset the game state to the begining of time
	 * Can be called to start a game over without reseting scores
	 */
	private void startNewGame() {
		// Record start time
		state.startTime = System.currentTimeMillis();
		// Create crab object, note we add some space below it so players can see it
		state.crab = new Crab(state.board, 0, mainController.getHeight()-2*Crab.getImgHeight());
    	// Add obstacles to the array
		state.obstacles = new ArrayList<Obstacle>();
		// Reset the state values
		state.backgroundPosn = 0;
		state.isCollided = false;
		state.isEnd = false;
		state.isTutorial = true;
		state.secPenalty = 0;
		state.velocity = 1;	

		state.arrow = new Arrow(board, 
				G1Image.ARROWS.getWidth(), 
				G1Image.ARROWS.getHeight()/viewGame.getNumArrowPics());
	}
	
	/**
	 * This is called after the user has reached the end of the game
	 * This will stop the main screen and will ask the user for their name
	 * This will then show them their high scores
	 */
	public void endGame() {
		// Change the view
		if(timerMini1 != null) timerMini1.cancel();
		timerMini1 = null;
		this.remove(viewGame);
		this.add(viewScore);
		viewScore.setBounds(0, 0, mainController.getWidth(), mainController.getHeight());
		// Append our new score
		double score = ((double)System.currentTimeMillis() - state.startTime)/1000;
		// Get the person's name
		String name = "UNKNOWN";
		boolean nameSuccess = false;
		// Loop till success
		while(!nameSuccess) {
			// Get the person's name
			name = JOptionPane.showInputDialog(this, "Enter your name!");
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
		// Save to file
		ScoreLoader.saveScores(state.scores);
	}
	
	
	/**
	 * Called when the exit button is pressed
	 * Should cleanup logic, and call the exit controller
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		// Show scoreboard
		if(event.getSource() == btnScore) {
			// Stop the game
			if(timerMini1 != null) {
				timerMini1.cancel();
				timerMini1 = null;
				this.remove(viewGame);		
				this.add(viewScore);
				viewScore.setBounds(0, 0, mainController.getWidth(), mainController.getHeight());
			}
			// Else start the game again
			else {
				// Start a new game
				startNewGame();
				// Start timer
				timerMini1 = new Timer();
				timerMini1.scheduleAtFixedRate(new Mini1Timer(this), 0, 10);
				// Add the game view
				this.remove(viewScore);
				this.add(viewGame);
				viewGame.setBounds(0, 0, mainController.getWidth(), mainController.getHeight());
			}
		}
		// Exit game
		if(event.getSource() == btnExit) {
			mainController.exitMini();
		}
	}


	/**
	 * Calls the mouseMoved method.
	 * Doing this allows support for touch-screen hardware.
	 */
	@Override
	public void mouseDragged(MouseEvent event) {
		mouseMoved(event);
	}


	/**
	 * For minigame 1, we just use the x location of the mouse
	 * This will translate to the crab moving left and right
	 */
	@Override
	public void mouseMoved(MouseEvent event) {
		// Update the crab location
		int xloc = (event.getX() > mainController.getWidth()-Crab.getImgWidth()) ? mainController.getWidth() - Crab.getImgWidth() : event.getX();
		state.crab.updateCrab(xloc);
	}
	
	/**
	 * Returns the current state of the minigame
	 */
	public Mini1State getState() {
		return state;
	}

}
