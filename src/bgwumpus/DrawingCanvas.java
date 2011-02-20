package bgwumpus;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComponent;

public class DrawingCanvas extends JComponent {

	private HashMap<String, Image> images = new HashMap<String, Image>();


	DrawingCanvas(){


		//load images
		Toolkit t = Toolkit.getDefaultToolkit();
		images.put("dirt", t.getImage("dirt.png"));
		images.put("pit", t.getImage("pit.png"));
		images.put("treasure", t.getImage("treasure.png"));
		images.put("player", t.getImage("player.png"));
		images.put("dirt_fog",t.getImage("dirt_fog.png"));
		images.put("pit_fog",t.getImage("dirt_fog.png"));

		setSize(32*Map.MAP_DIMENSIONS,32*Map.MAP_DIMENSIONS);



	}

	public void paint(Graphics g){

		g.fillRect(0,0,Map.MAP_DIMENSIONS*32,Map.MAP_DIMENSIONS*32);

		for(int i=0; i<Map.MAP_DIMENSIONS; i++){
			for(int j=0; j<Map.MAP_DIMENSIONS; j++){

				//assume the tile has not been discovered
				boolean tileIsDiscovered = false;

				for(int k=0; k<GameLogic.history.size(); k++){

					if(GameLogic.history.get(k).x == j && GameLogic.history.get(k).y == i){
						tileIsDiscovered = true;
					}
				}
				
				
				//if tile is discovered by the player and is near enough to be visible then display normally
				if(tileIsDiscovered && GameLogic.checkVisibility(j,i,EntityType.PLAYER)){
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
				else if(tileIsDiscovered && !GameLogic.checkVisibility(j,i,EntityType.PLAYER)) {
					
					g.drawImage(images.get("dirt_fog"),j*32,i*32,32,32,null);	

				}

				//draw entities on top
				if(GameLogic.player_location.equals(new Point(j,i))){
					g.drawImage(images.get("player"), j*32, i*32, 32, 32, null); 
				}

			}
		}


	}

}