package bgwumpus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import javax.swing.JFrame;

import java.awt.Toolkit;
import java.util.ArrayList;

/**
 * The graphical interface which contains the window and overall output
 *
 */
public class GUI extends javax.swing.JFrame implements UserInterface {

	/**
	 * TODO work out what this is, eclipse complains with a warning if I do not have it
	 */
	private static final long serialVersionUID = 5111988071128070407L;
	private DrawingCanvas canvas; 
	private boolean messagesUpdated = false;
	int messageListSize = 0;

	/** The constructor for the GUI, sets up the canvas, input listener and window attributes
	 * @param player the player so that the input can be given 
	 */
	GUI(PlayableEntity player){
		
		//create a new canvas where all drawing will occur
		canvas = new DrawingCanvas(player);

		//create a new user input handler to listen for keyboard input
		UserInputHandler inputHandler = new UserInputHandler(player); 
		addKeyListener(inputHandler); //add it to the frame to use it

		//set the map size to: the number of tiles * the tile size
		setSize(Map.MAP_DIMENSIONS*canvas.TILE_DIMENSIONS,Map.MAP_DIMENSIONS*canvas.TILE_DIMENSIONS+20);
		add(canvas); //add the canvas to the frame

		//window attributes
		setTitle("Hunt the Wumpus!"); //set the window bar title
		setDefaultCloseOperation(EXIT_ON_CLOSE); //enable exit upon closing the window
		setVisible(true); //show the window
		setResizable(false); //disable resizing of the window

	}

	/* (non-Javadoc) 
	 * @see bgwumpus.UserInterface#render()
	 */
	public void render(){

		//redraw the graphics
		canvas.repaint();

	}
	
	/* (non-Javadoc)
	 * @see bgwumpus.UserInterface#outputPerceptionMessages(bgwumpus.PlayableEntity)
	 */
	public void outputPerceptionMessages(PlayableEntity player){
		
		//get the perception messages based on location
		ArrayList<String> messages = GameLogic.getPerceptionMessages(player);

		//if there are messages and the number of messages has changed since the last time //TODO CHANGE THIS SO THAT IT ONLY OUTPUTS WHEN MESSAGES ARE DIFFERENT 
		if(messages != null && messages.size() != messageListSize){
			for(int i=0; i<messages.size(); i++){ //loop through all messages
				System.out.println(messages.get(i) + "\n"); //output a message
			}
		}
		
		//get the size of the message list
		messageListSize = messages.size();
			
	}
	

}
