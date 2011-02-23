package bgwumpus;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.util.Queue;

/**
 * The graphical interface which contains the window and overall output
 *
 */
public class GUI extends javax.swing.JFrame implements UserInterface {


	private static final long serialVersionUID = 5111988071128070407L;
	private DrawingCanvas canvas; 
	private JTextArea text_area;
	private JScrollPane scroll_pane;

	/** The constructor for the GUI, sets up the canvas, input listener and window attributes
	 * @param player the player so that the input can be given 
	 */
	GUI(PlayableEntity player){

			//Setup layout, drawing canvas and text area
		    setLayout(null);
		   
			text_area = new JTextArea(5, 20);
			JScrollPane scroll_pane = new JScrollPane(text_area); 
			canvas = new DrawingCanvas(player);

			
			text_area.setEditable(false);	  
			
			scroll_pane.setBounds(15,Map.MAP_DIMENSIONS*canvas.TILE_DIMENSIONS+canvas.STATUS_BAR_SIZE, Map.MAP_DIMENSIONS*canvas.TILE_DIMENSIONS-15, 100);
			canvas.setBounds(0,0,Map.MAP_DIMENSIONS*canvas.TILE_DIMENSIONS,Map.MAP_DIMENSIONS*canvas.TILE_DIMENSIONS+20+canvas.STATUS_BAR_SIZE);		
	      
	        add(canvas);
	        add(scroll_pane);

	        setSize(Map.MAP_DIMENSIONS*canvas.TILE_DIMENSIONS,Map.MAP_DIMENSIONS*canvas.TILE_DIMENSIONS+canvas.STATUS_BAR_SIZE + 130);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	        setFocusable(true);
			setTitle("Hunt the Wumpus!"); //set the window bar title
			
			
			//create a new user input handler to listen for keyboard input
			UserInputHandler inputHandler = new UserInputHandler(player); 
			addKeyListener(inputHandler); //add it to the frame to use it
			
			setVisible(true); //show the window
			
			//maintain focus
			requestFocus();

		
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
		Queue<String> messages = GameLogic.getPerceptionMessages(player);
		
		

		//if there are new messages
			for(int i=0; i<messages.size(); i++){ //loop through all messages
				text_area.setText(messages.element() + "\n" + text_area.getText()); //output to textbox 
				System.out.println(messages.remove()); //output a message to stdout
			}
		
	
	
	}
		
	

	
	public void outputMessages(PlayableEntity player){
		
		outputPerceptionMessages(player);
		
		Queue<String> messages = GameLogic.getMessageQueue();
		
		for(int i=0; i<messages.size(); i++){
			text_area.setText(messages.element() + "\n" + text_area.getText()); //output to textbox 
			System.out.println(messages.remove()); //output a message to stdout
		}
		
		
	}
	

}
