package bgwumpus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import javax.swing.JFrame;

import java.awt.Toolkit;
import java.util.ArrayList;

public class GUI extends javax.swing.JFrame implements UserInterface {

	/**
	 * TODO work out what this is, eclipse complains with a warning if I do not have it
	 */
	private static final long serialVersionUID = 5111988071128070407L;
	private DrawingCanvas canvas = new DrawingCanvas();
	private boolean messagesUpdated = false;
	int messageListSize = 0;

	GUI(){

		UserInputHandler inputHandler = new UserInputHandler(); 
		addKeyListener(inputHandler);

		setSize(Map.MAP_DIMENSIONS*32,Map.MAP_DIMENSIONS*32+20);
		add(canvas);


		setTitle("Hunt the Wumpus!");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);

	}

	public void render(){

		canvas.repaint();

	}
	
	public void outputPerceptionMessages(Player player){
		
		
		ArrayList<String> messages = GameLogic.getPerceptionMessages(player);

		if(messages != null && messages.size() != messageListSize){
			for(int i=0; i<messages.size(); i++){
				
				System.out.println(messages.get(i));
			}
		}
		
		messageListSize = messages.size();
		


		
	}
	
	public void outputPerceptionMessages(AI ai){
		
		ArrayList<String> messages = GameLogic.getPerceptionMessages(ai);
		
		if(messages != null){
			for(int i=0; i<messages.size(); i++){
				
				System.out.println(messages.get(i));
				
			}
		}

		
	}

}
