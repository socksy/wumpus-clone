package bgwumpus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Toolkit;
import java.util.ArrayList;

public class GUI extends javax.swing.JFrame implements UserInterface {

	/**
	 * TODO work out what this is, eclipse complains with a warning if I do not have it
	 */
	private static final long serialVersionUID = 5111988071128070407L;
	private ArrayList<Image> img = new ArrayList<Image>();

	GUI(){

	UserInputHandler inputHandler = new UserInputHandler(); 
	addKeyListener(inputHandler);

	setSize(32*Map.MAP_DIMENSIONS,32*Map.MAP_DIMENSIONS);
	setResizable(false);
	setVisible(true);

	
	}
	
	public void render(){
		
	repaint();

	}
	
	public void paint(Graphics g){
	
		g.fillRect(0,0,Map.MAP_DIMENSIONS*32,Map.MAP_DIMENSIONS*32);
		
		Toolkit t = Toolkit.getDefaultToolkit();
		img.add(t.getImage("dirt.gif"));
		img.add(t.getImage("pit.gif"));
		img.add(t.getImage("treasure.png"));
		img.add(t.getImage("player.gif"));
		
		for(int i=0; i<Map.MAP_DIMENSIONS; i++){
			for(int j=0; j<Map.MAP_DIMENSIONS; j++){
				
				if(Map.getTypeAt(j,i) == TileType.PIT){
				g.drawImage(img.get(1), j*32, i*32, 32, 32, null);
				}
				else if(Map.getTypeAt(j,i) == TileType.TREASURE){
				g.drawImage(img.get(2), j*32, i*32, 32, 32, null);
				}
				else if(GameLogic.player_location.equals(new Point(j,i))){
				g.drawImage(img.get(3), j*32, i*32, 32, 32, null); 
				}
				else {
				g.drawImage(img.get(0),j*32,i*32,32,32,null);	
				}
			}
		}
		
		render();


	}
	
	
	
}
