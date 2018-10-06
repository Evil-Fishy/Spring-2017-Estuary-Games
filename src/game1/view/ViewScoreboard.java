package game1.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import game1.model.Score;
import master.view.DrawOps;


/**
 * This view displays the scoreboard for the game
 * This scoreboard has the top 5 players in it
 * It also shows the user's latest time compared to all others
 */
public class ViewScoreboard extends JPanel {

	MiniOne game;
	int fontSize;
	
	/**
	 * Default constructor
	 * @param game The minigame object
	 */
	public ViewScoreboard(MiniOne game) {
		// Set our game
		this.game = game;
		// TODO: Add all our text box and stats here
		this.setBackground(Color.BLACK);
		fontSize = game.board.getWidth()/20;
	}
	
	
	/**
	 * Add custom painting
	 * Should render the scoreboard
	 * Should also render the user's past run time
	 */
	@Override
	protected void paintComponent(Graphics g) {
		// Call the super
		super.paintComponent(g);
		DrawOps.drawScaledImage(g, G1Image.LEADERBOARD_BG.getImg(), this);
		
		// Set text details
		g.setColor(Color.WHITE);
    	g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize)); 
		
		// Loop through 5 scores if we have them
    	// Always display 5 high scores
    	Score s;
		for(int i=0; i<5; i++) {
			// If we have a score then display it
			if(i < game.getState().scores.size()) {
				s = game.getState().scores.get(i);
				DrawOps.drawHozCenteredString(g, s.getName() + " - " + s.getTime(), this, fontSize*i+this.getHeight()/3);
			}
			// Else display it as unknown user
			else {
				DrawOps.drawHozCenteredString(g, "UNKNOWN - ????", this, fontSize*i+this.getHeight()/3);
			}
		}
		
		// Set text details
		g.setColor(Color.RED);
    	g.setFont(new Font("TimesRoman", Font.BOLD, fontSize)); 
		
		// Finally display our score at the top
    	if(game.getState().lastScore != null) {
    		DrawOps.drawHozCenteredString(g, game.getState().lastScore.getName()+" - "+game.getState().lastScore.getTime(), this, (int)(1.5*fontSize));
    	}
	}
}
