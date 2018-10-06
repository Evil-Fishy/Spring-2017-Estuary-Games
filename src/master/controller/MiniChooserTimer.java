package master.controller;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;

import game3.model.Die;
import game3.model.Marker;
import game3.model.Mini3State;
import game3.view.MiniThree;
import game3.view.StoryPanel;
import game3.view.SwipeButton;
import master.controller.MainController;
import master.model.Bird;
import master.model.Board;
import master.view.MiniChooser;

public class MiniChooserTimer extends TimerTask {	
	private MiniChooser game;
	private Board board;
	private JLabel title;
	private Bird bird;
	
	private int titleX, titleY, titleMaxY;
	private int yIncr;

	/**
	 * Default constructor
	 */
	public MiniChooserTimer(MiniChooser game, Board board) {
		this.game = game;
		this.board = board;
		bird = game.getBird();
		title = game.getTitle();
		titleX = 20;
		titleY = title.getFont().getSize();
		titleMaxY = board.getHeight()/3 - 2 * title.getFont().getSize();
		yIncr = 3;
	}
	
	/**
	 * This will move the label forward in time
	 * This happens at a constant rate
	 */
	@Override
	public void run() {
		titleY += yIncr;
		game.getTitle().setLocation(titleX, titleY);
		game.repaint();
		if(titleY > titleMaxY)
			yIncr = -yIncr;
		else if(titleY < title.getFont().getSize())
			yIncr = -yIncr;
		
		game.getCrab().updateCrab();
		bird.updateBird();
		if(bird.reachedGoal()) {
			game.getGameController().switchMini(bird.getGameGoal());
			MainController.execDelay(25);
			bird.resetBirdSpecs();
		}
    }
}
