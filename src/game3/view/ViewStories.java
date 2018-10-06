package game3.view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import game3.controller.StoryLoader;
import game3.model.Mini3State;
import game3.model.Story;
import master.view.DrawOps;

/**
 * Defines the view where the user can see the written stories.
 * @author Habibullah Aslam
 *
 */
public class ViewStories extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1972123908549301886L;
	private Mini3State state;
	private MiniThree game;
	
	//UI stuff
	private JButton btnQuitStories = new JButton("I'm finished");
	
	private JTextPane storyPane = new JTextPane();
	private JScrollPane jScrollPane = new JScrollPane(storyPane);
	private StyledDocument doc;
	
	private static GridBagLayout layout = new GridBagLayout();
    private static GridBagConstraints c = new GridBagConstraints();
	private int nOfGridCells = 3;
    private BufferedImage[] dieFacePics;
    
    private int fontSize;
	private boolean isActive;
	
	/**
     * Default constructor. Sets up the panel and adds listeners.
     * 
     * @param game the game that this panel is in
     * @param state the state of the given game
     */
	public ViewStories(MiniThree game, Mini3State state) {
		setOpaque(false);
		this.game = game;
		this.state = state;
		dieFacePics = game.getDieFacePics();

		storyPane.setEditable(false);
        jScrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        doc = storyPane.getStyledDocument();
		
		btnQuitStories.addActionListener(this);
	}
	
	/**
	 * Starts up the panel and sets up the initial state.
	 */
	public void startup() {
		isActive = true;
		game.getStoryPanel().swapStoryBtns();
		CardLayout clayout = (CardLayout) (game.getCards().getLayout());
		clayout.show(game.getCards(), MiniThree.VIEWSTORIES);
		game.getBtnTut().setEnabled(false);

		fontSize = getHeight()/30;
        setLayout(layout);
        c.weightx = 0.1;
        c.weighty = 0.1;
        for(int i = 0; i < nOfGridCells; i++){
            for(int j = 0; j < nOfGridCells; j++) {    			
                c.gridx = i;
                c.gridy = j;
                if(i == 0 && j == 0) {
                	c.insets = new Insets(fontSize*3, fontSize*3, 0, fontSize*3);
                	c.gridwidth = 3;
                	c.gridheight = 2;
                	c.fill = GridBagConstraints.BOTH;
                    add(jScrollPane, c);
                	c.fill = GridBagConstraints.NONE;
                	c.gridheight = 1;
                	c.gridwidth = 1;
                	c.insets = new Insets(0, 0, 0, 0);
                }
                else if(i == 1 && j == 2) {
                	c.anchor = GridBagConstraints.CENTER;
                	add(btnQuitStories, c);
                	c.anchor = GridBagConstraints.CENTER;
                }
                add(Box.createRigidArea(new Dimension(getWidth()/nOfGridCells, 
            			getHeight()/nOfGridCells)), c);
            }
        }    
        revalidate();

		fontSize = getHeight()/20;
		btnQuitStories.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
		fontSize = getHeight()/15;		

        addStylesToDocument(doc);
		
		try {
			doc.insertString(doc.getLength(), 
					"Recently Saved Stories", 
					doc.getStyle("title"));
			
			for(Story s : state.stories) {				
				doc.insertString(doc.getLength(), "\n\n" +
						s.getName() + "'s Story\n" + s.getStory() + "\n", 
						doc.getStyle("story"));
				
				for(int i : s.getCubePics())
					storyPane.insertIcon(new ImageIcon(DrawOps.scaleImgByFactor(dieFacePics[i], 0.7)));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}		

		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
	}

	/**
	 * Shuts down the panel by removing the buttons associated with the panel.
	 */
	public void shutdown() {
		CardLayout clayout = (CardLayout)(game.getCards().getLayout());
		clayout.show(game.getCards(), MiniThree.STORYPANEL);
		game.getBtnTut().setEnabled(true);
		// Save to file
		StoryLoader.saveStories(state.stories);
		storyPane.setText("");
		isActive = false;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// Call the super
		super.paintComponent(g);
		if(isActive) {
			g.setFont(new Font("TimesRoman", Font.BOLD, fontSize));
			DrawOps.drawScaledImage(g, G3Image.STORYPANEL_BG.getImg(), this);
		}
	}
	
	/**
	 * Changes the panel based on what button was clicked.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnQuitStories) {
			shutdown();
		}
	}
	
	public void setActive(boolean b) {
		isActive = b;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public MiniThree getGame() {
		return game;
	}
	
	/**
	 * Adds the styles needed to the textPane to adjust the text fonts.
	 * @param doc the doc belonging to the textPane whose styles will be adjusted
	 */
	protected void addStylesToDocument(StyledDocument doc) {
        Style def = StyleContext.getDefaultStyleContext().
                        getStyle(StyleContext.DEFAULT_STYLE);

        Style regular = doc.addStyle("regular", def);
        StyleConstants.setFontFamily(def, "TimesRoman");
        
        Style title = doc.addStyle("title", regular);
        StyleConstants.setFontSize(title, fontSize);
        
        Style storyText = doc.addStyle("story", regular);
        StyleConstants.setFontSize(storyText, fontSize/2);
	}
}
