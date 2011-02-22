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
	public static int STATUS_BAR_SIZE = 32;
	private static String IMAGE_FOLDER = "images";
	private static final long serialVersionUID = 5111988071128070407L;
	private PlayableEntity player;

	

	DrawingCanvas(PlayableEntity player){
		

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

		this.player = player;
	}
	
	public void drawStatusBar(Graphics g){
		
		String output = new String();
		
		output += "Score " + player.getScore();
		output += "\t Steps " + player.getPlayerSteps();
		if(player.hasPickedUpTreasure())
			output += " Treasure: Yes\t";
		else
			output += " Treasure: No\t";
		if(GameLogic.wumpus_dead)
			output += " Wumpus: Dead\t";
		else {
			output += " Wumpus: Alive";
		}
		
		g.setColor(Color.black);
		g.fillRect(0,0,TILE_DIMENSIONS*Map.MAP_DIMENSIONS,20);
		g.setColor(Color.white);
		g.drawString(output,10,20);
		
	}
	
	public void drawTile(Graphics g, String imagename,int x,int y){
		
		g.drawImage(images.get(imagename),x*TILE_DIMENSIONS,STATUS_BAR_SIZE + y*TILE_DIMENSIONS,TILE_DIMENSIONS,TILE_DIMENSIONS,null);
	
		
		
	}

	public void paint(Graphics g){

		g.fillRect(0,0,Map.MAP_DIMENSIONS*TILE_DIMENSIONS,Map.MAP_DIMENSIONS*TILE_DIMENSIONS+STATUS_BAR_SIZE);

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
				if( (tileIsDiscovered && GameLogic.checkVisibility(j,i)) || GameLogic.map_revealed){
					//draw tiles
					if(Map.getTypeAt(j,i) == TileType.PIT){ //if a pit, draw a TILE_DIMENSIONSxTILE_DIMENSIONS pit tile in that tile space
						drawTile(g,"standardTile",j,i);
						drawTile(g,"pit", j, i);
					} else if(Map.getTypeAt(j,i) == TileType.TREASURE){ //if the treasure is on this tile, draw a TILE_DIMENSIONSxTILE_DIMENSIONS treasure tile in that tile space
						drawTile(g,"standardTile",j,i); //draw the dirt underneath 
						drawTile(g,"treasure",j,i);	
					
					} else if(Map.getTypeAt(j,i) == TileType.EXIT){ //if an exit, draw the exit tile
						drawTile(g,"exit",j,i);
					} else if(Map.getTypeAt(j,i) == TileType.BAT){
						drawTile(g,"bat",j,i);
					} else { //if not a special tile
						drawTile(g,"standardTile",j,i); //draw the dirt standard tile
					}

				}
				else if(tileIsDiscovered && !GameLogic.checkVisibility(j,i)) {
					drawTile(g,"dirt_fog",j,i);
				}

				//draw entities on top				
				if(GameLogic.getTypeAt(j,i) == EntityType.PLAYER){
					drawTile(g,"player",j,i); 
				} else if(GameLogic.getTypeAt(j,i) == EntityType.WUMPUS && tileIsDiscovered){
					drawTile(g,"wumpus",j,i);
				}
				
				if(GameLogic.getTypeAt(j,i) == EntityType.AI){
					drawTile(g,"player",j,i); 
				} else if(GameLogic.getTypeAt(j,i) == EntityType.WUMPUS && tileIsDiscovered){
					drawTile(g,"wumpus",j,i);
				}
				

			}
		}
		
	drawStatusBar(g);


	}

}
