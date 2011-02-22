package bgwumpus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComponent;

public class DrawingCanvas extends JComponent {

	private HashMap<String, Image> images = new HashMap<String, Image>();
	public static int TILE_DIMENSIONS = 64;
	private static String IMAGE_FOLDER = "images";


	DrawingCanvas(){
		

		//load images
		Toolkit t = Toolkit.getDefaultToolkit();
		images.put("standardTile", t.getImage(IMAGE_FOLDER + "/standardTile.png"));
		images.put("pit", t.getImage(IMAGE_FOLDER + "/pit.png"));
		images.put("treasure", t.getImage(IMAGE_FOLDER + "/treasure.png"));
		images.put("exit", t.getImage(IMAGE_FOLDER + "/exitTile.png"));
		images.put("player", t.getImage(IMAGE_FOLDER + "/player.png"));
		images.put("bat", t.getImage(IMAGE_FOLDER + "/batTile.png"));
		images.put("wumpus", t.getImage(IMAGE_FOLDER + "/wumpus.png"));
		images.put("dirt_fog",t.getImage(IMAGE_FOLDER + "/dirt_fog.png"));

		setSize(TILE_DIMENSIONS*Map.MAP_DIMENSIONS,TILE_DIMENSIONS*Map.MAP_DIMENSIONS);



	}
	
	public void drawStatusBar(Graphics g){
		
		g.setColor(Color.white);
		g.drawString("Hunt the Wumpus! \t Steps " + GameLogic.player_steps,10,10);
		
	}

	public void paint(Graphics g){

		g.fillRect(0,0,Map.MAP_DIMENSIONS*TILE_DIMENSIONS,Map.MAP_DIMENSIONS*TILE_DIMENSIONS);

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
				if( (tileIsDiscovered && GameLogic.checkVisibility(j,i,EntityType.PLAYER)) || GameLogic.map_revealed){
					//draw tiles
					if(Map.getTypeAt(j,i) == TileType.PIT){ //if a pit, draw a TILE_DIMENSIONSxTILE_DIMENSIONS pit tile in that tile space
						g.drawImage(images.get("standardTile"),j*TILE_DIMENSIONS,i*TILE_DIMENSIONS,TILE_DIMENSIONS,TILE_DIMENSIONS,null);//draw the dirt underneath 
						g.drawImage(images.get("pit"), j*TILE_DIMENSIONS, i*TILE_DIMENSIONS, TILE_DIMENSIONS, TILE_DIMENSIONS, null);
					} else if(Map.getTypeAt(j,i) == TileType.TREASURE){ //if the treasure is on this tile, draw a TILE_DIMENSIONSxTILE_DIMENSIONS treasure tile in that tile space
						g.drawImage(images.get("standardTile"),j*TILE_DIMENSIONS,i*TILE_DIMENSIONS,TILE_DIMENSIONS,TILE_DIMENSIONS,null); //draw the dirt underneath 
						g.drawImage(images.get("treasure"), j*TILE_DIMENSIONS, i*TILE_DIMENSIONS, TILE_DIMENSIONS, TILE_DIMENSIONS, null);	
					
					} else if(Map.getTypeAt(j,i) == TileType.EXIT){ //if an exit, draw the exit tile
						g.drawImage(images.get("exit"),j*TILE_DIMENSIONS,i*TILE_DIMENSIONS,TILE_DIMENSIONS,TILE_DIMENSIONS,null);
					} else if(Map.getTypeAt(j,i) == TileType.BAT){
						g.drawImage(images.get("bat"),j*TILE_DIMENSIONS,i*TILE_DIMENSIONS,TILE_DIMENSIONS,TILE_DIMENSIONS,null);
					}
					else { //if not a special tile
						g.drawImage(images.get("standardTile"),j*TILE_DIMENSIONS,i*TILE_DIMENSIONS,TILE_DIMENSIONS,TILE_DIMENSIONS,null); //draw the dirt standard tile
					}

				}
				else if(tileIsDiscovered && !GameLogic.checkVisibility(j,i,EntityType.PLAYER)) {
					
					g.drawImage(images.get("dirt_fog"),j*TILE_DIMENSIONS,i*TILE_DIMENSIONS,TILE_DIMENSIONS,TILE_DIMENSIONS,null);
				}

				//draw entities on top				
				if(GameLogic.getTypeAt(j,i) == EntityType.PLAYER){
					g.drawImage(images.get("player"), j*TILE_DIMENSIONS, i*TILE_DIMENSIONS, TILE_DIMENSIONS, TILE_DIMENSIONS, null); 
				}
				else if(GameLogic.getTypeAt(j,i) == EntityType.WUMPUS && GameLogic.checkVisibility(j,i,EntityType.PLAYER) && tileIsDiscovered){
					g.drawImage(images.get("wumpus"),j*TILE_DIMENSIONS,i*TILE_DIMENSIONS,TILE_DIMENSIONS,TILE_DIMENSIONS,null);
				}
				

			}
		}
		
	drawStatusBar(g);


	}

}
