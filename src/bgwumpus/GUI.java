package bgwumpus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Toolkit;
import java.util.*;

public class GUI extends javax.swing.JFrame implements UserInterface {

	/**
	 * TODO work out what this is, eclipse complains with a warning if I do not have it
	 */
	private static final long serialVersionUID = 5111988071128070407L;
	private HashMap<String, Image> images = new HashMap<String, Image>();

	GUI(){

		UserInputHandler inputHandler = new UserInputHandler(); 
		addKeyListener(inputHandler);

		//load images
		Toolkit t = Toolkit.getDefaultToolkit();
		images.put("dirt", t.getImage("dirt.png"));
		images.put("pit", t.getImage("pit.png"));
		images.put("treasure", t.getImage("treasure.png"));
		images.put("player", t.getImage("player.png"));


		setSize(32*Map.MAP_DIMENSIONS,32*Map.MAP_DIMENSIONS);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	public void render(){
		repaint();
	}

	public void paint(Graphics g){

		g.fillRect(0,0,Map.MAP_DIMENSIONS*32,Map.MAP_DIMENSIONS*32);

		for(int i=0; i<Map.MAP_DIMENSIONS; i++){
			for(int j=0; j<Map.MAP_DIMENSIONS; j++){

				//assume the tile has not been discovered
				boolean tileIsVisible = false;

				for(int k=0; k<GameLogic.history.size(); k++){

					if(GameLogic.history.get(k).x == j && GameLogic.history.get(k).y == i){
						tileIsVisible = true;
					}
				}
				//if tile is discovered by the player
				if(tileIsVisible){
					//draw tiles
					if(Map.getTypeAt(j,i) == TileType.PIT){
						g.drawImage(images.get("dirt"),j*32,i*32,32,32,null);	
						g.drawImage(images.get("pit"), j*32, i*32, 32, 32, null);
					} else if(Map.getTypeAt(j,i) == TileType.TREASURE){
						g.drawImage(images.get("dirt"),j*32,i*32,32,32,null);	
						g.drawImage(images.get("treasure"), j*32, i*32, 32, 32, null);				
					} else {
						g.drawImage(images.get("dirt"),j*32,i*32,32,32,null);	
					}

				}
				else {


				}

				//draw entities on top
				if(GameLogic.player_location.equals(new Point(j,i))){
					g.drawImage(images.get("player"), j*32, i*32, 32, 32, null); 
				}

			}
		}

		render();


	}



}
