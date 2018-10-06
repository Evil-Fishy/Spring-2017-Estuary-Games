package game3.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import game3.controller.StoryLoader;
import game3.controller.TutState;
import game3.model.Die;
import game3.model.Mini3State;
import game3.model.Story;
import master.controller.SwearLoader;
import master.view.DrawOps;

/**
 * Defines the story panel where the user writes his story.
 * @author Habibullah Aslam, Natalie Rubin
 *
 */
public class StoryPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = -8849893372621068454L;
	private Mini3State state;
	private MiniThree game;	

	//UI stuff
	private JButton btnRollAgain = new JButton("Roll Again?");
	private JButton btnSaveStory = new JButton("Save my story!");
	private JButton btnShowStories = new JButton("See stories!");
	private JButton btnReorganizeDice = new JButton("I want to reorganize the cubes");
	private JTextArea inputArea = new JTextArea("Type your story here. Hit 'Enter' when done.", 4, 0);
	private JScrollPane scrollPane = new JScrollPane(inputArea);
	private JLabel label;
	private static GridBagLayout layout = new GridBagLayout();
    private static GridBagConstraints c = new GridBagConstraints();
	private int nOfGridCells = 3;
			
	private int fontSize;
	private boolean isActive;
	private boolean textAreaClicked;
	private boolean storySaved;
	private int dieYPosnOnStoryPanel;
    private List<String> swearWords;
	
    /**
     * Default constructor. Sets up the panel and adds listeners.
     * 
     * @param game the game that this panel is in
     * @param state the state of the given game
     */
	public StoryPanel(MiniThree game, Mini3State state) {
		setOpaque(false);
		this.game = game;
		this.state = state;
		textAreaClicked = false;
		storySaved = false;

		// Load in swear words
		swearWords = SwearLoader.loadSwear();

		btnRollAgain.addActionListener(this);
		btnReorganizeDice.addActionListener(this);
		btnSaveStory.addActionListener(this);
		btnShowStories.addActionListener(this);
		inputArea.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(!textAreaClicked) {
					inputArea.setText("");
					textAreaClicked = true;
				}
			 }
		});
		inputArea.setLineWrap(true);
		inputArea.setWrapStyleWord(true);
	}
	
	/**
	 * Starts up the panel and sets up the initial state.
	 */
	public void startup() {
		game.removeDieButtons();
		state.reorderDice();

		fontSize = getHeight()/30;
        setLayout(layout);
        c.weightx = 0.1;
        c.weighty = 0.1;
        for(int i = 0; i < nOfGridCells; i++){
            for(int j = 0; j < nOfGridCells; j++) {    			
                c.gridx = i;
                c.gridy = j;
                if(i == 0 && j == 0) {
                	c.insets = new Insets(0, 0, 0, 10);
                	c.anchor = GridBagConstraints.LINE_END;
                	add(btnRollAgain, c);
                	c.anchor = GridBagConstraints.CENTER;
                	c.insets = new Insets(0, 0, 0, 0);
                }
                else if(i == 1 && j == 0) {
                	c.insets = new Insets(fontSize, 0, 0, 0);
                	c.fill = GridBagConstraints.BOTH;
                    add(scrollPane, c);
                	c.fill = GridBagConstraints.NONE;
                	c.insets = new Insets(0, 0, 0, 0);
                }
                else if(i == 2 && j == 0) {
                	c.insets = new Insets(0, 10, 0, 0);
                	c.anchor = GridBagConstraints.WEST;
               		add(btnSaveStory, c);
                	c.anchor = GridBagConstraints.CENTER;
                	c.insets = new Insets(0, 0, 0, 0);
                }
                else if(i == 1 && j == 2) {
                	c.anchor = GridBagConstraints.SOUTH;
                	add(btnReorganizeDice, c);
                	c.anchor = GridBagConstraints.CENTER;
                }
                else {
                	add(Box.createRigidArea(new Dimension(getWidth()/nOfGridCells, 
                			getHeight()/nOfGridCells)), c);
                }
            }
        }    
		revalidate();		
		
		//sets up the position of the dice on the StoryPanel
		dieYPosnOnStoryPanel = game.getCards().getY() + getHeight()/2 - Die.getWidth()/2;
		state.storyPnlDiceXPosns = new int[Mini3State.numDice];
		int pnlWidth = getWidth();
		int spacing = (pnlWidth - Mini3State.numDice*Die.getWidth())/(Mini3State.numDice + 1);
		spacing += Die.getWidth();
		for(int i = 0; i < state.storyPnlDiceXPosns.length; i++) {
			state.storyPnlDiceXPosns[i] = (i+1)*spacing - Die.getWidth() + game.getCards().getX();
			state.getDice().get(i).getButton().setLocation(state.storyPnlDiceXPosns[i], dieYPosnOnStoryPanel);
		}			
		
		btnRollAgain.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
		btnSaveStory.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
		btnShowStories.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
		btnReorganizeDice.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
		inputArea.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
		state.stories = StoryLoader.loadStories();
		repaint();
	}

	/**
	 * Shuts down the panel by removing the buttons associated with the panel.
	 */
	public void shutdown() {
		isActive = false;
		remove(btnRollAgain);
		remove(scrollPane);
		remove(btnSaveStory);
		remove(btnShowStories);
		remove(btnReorganizeDice);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// Call the super
		super.paintComponent(g);
		if(isActive) {
			fontSize = getHeight()/10;
			DrawOps.drawScaledImage(g, G3Image.STORYPANEL_BG.getImg(), this);
					
	    	g.setFont(new Font("TimesRoman", Font.BOLD, fontSize));
			DrawOps.drawHozCenteredString(g, "Make a story and type it!", this, 
					(int) (getHeight() * 0.8));
		}
	}

	/**
	 * Changes the panel based on what button was clicked.
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == btnRollAgain) {
			game.shutdown();
			game.getTutPanel().setTutState(TutState.SWIPE);
			resetInputArea();
			game.startup();
		}
		else if(event.getSource() == btnSaveStory) {			
			fontSize = getHeight()/15;
			label = new JLabel("Your story: " + inputArea.getText());
			label.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));

			boolean hasBad = false;
			for(String word : label.getText().split(" "))
				if(swearWords.contains(word))
					hasBad = true;
			if(hasBad) {
				label.setText("Sorry, you have inappropriate words in your story. Please remove them.");	
				return;
			}			

			String name = "";
			name = JOptionPane.showInputDialog(this, "Enter your name");
			// Check if it has a bad word
			hasBad = false;
			for(String word : name.split(" "))
				if(swearWords.contains(word))
					hasBad = true;
			
			// Loop till success
			while(hasBad) {
				// Get the person's name
				name = JOptionPane.showInputDialog(this, "Enter your name (no inappropriate words please)");
				// Check if it has a bad word
				hasBad = false;
				for(String word : name.split(" "))
					if(swearWords.contains(word))
						hasBad = true;	
				
			}			

			// Load from file
			state.stories = StoryLoader.loadStories();
			
			state.stories.add(new Story(name, inputArea.getText(), state.getFacePicsFromDice()));

			game.getViewStories().startup();
			storySaved = true;
		}
		else if(event.getSource() == btnShowStories) {			
			// Load from file
			state.stories = StoryLoader.loadStories();
			game.getViewStories().startup();
		}
		else if(event.getSource() == btnReorganizeDice) {
			shutdown();
			game.getTutPanel().setTutState(TutState.ARRANGE_DIE);
		}
	}
	
	public void setActive(boolean b) {
		isActive = b;
	}

	public boolean getIsActive() {
		return isActive;
	}
	
	public int getDieYPosnOnStoryPanel() {
		return dieYPosnOnStoryPanel;
	}
	
	/**
	 * Swaps the "save my story" button with the "see stories" button once the user has saved his story.
	 */
	public void swapStoryBtns() {
		if(!storySaved) {
			GridBagConstraints gbc = layout.getConstraints(btnSaveStory);
			remove(btnSaveStory);
			add(btnShowStories, gbc);
			revalidate();
		}
	}
	
	/**
	 * Resets the input area to the default text.
	 */
	public void resetInputArea() {
		inputArea.setText("Type your story here. Hit 'Enter' when done.");
		textAreaClicked = false;
	}
}
