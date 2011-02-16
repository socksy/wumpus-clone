package bgwumpus;
import java.util.*;


/**
 * Map class includes the map data and model
 *
 */
public class Map {
	
	Tile[][] map_array = new Tile[9][9]; //FOR NOW
	
	Map(){
		
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				map_array[i][j] = new StandardTile();
			}
		}
		
		
	}
	
	public void generateMap(){
		
		Random random = new Random();
		boolean treasureSet = false;
		boolean exitSet = false;
		
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				
				int random_number = Math.abs(random.nextInt() % 7)+1;
				
				switch(random_number){
				
				case 1:
				case 2:
				case 3:
					//leave it as a standard tile
					break;
				case 4: 
					map_array[i][j] = new BatTile();
					break;
				case 5:
					map_array[i][j] = new PitTile();
					break;
				case 6:
					if(!treasureSet){
					map_array[i][j] = new TreasureTile(); treasureSet = true;
					}
				case 7:
					if(!exitSet){
					map_array[i][j] = new ExitTile(); exitSet = true;
					}
				}
							
			}
		}
		
	}
	
	public void printMapState(){
		
		
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				
				switch(map_array[i][j].type){
				case STANDARD:
					System.out.print(" ? ");
				break;
				case PIT:
					System.out.print(" O ");
				break;
				case BAT:
					System.out.print(" B ");
				break;
				case TREASURE:
					System.out.print(" T ");
				break;
				case EXIT:
					System.out.print(" E ");
				break;
				default:
					System.out.print("  ");
				break;
				}
			}
			System.out.print("\n");
		}	
		
		
		
	}
	
	public TileType getTypeAt(int x,int y){
		
		return map_array[y][x].getType();
		
		
	}
	
	public Tile[][] getMap(){
		
		return map_array;
		
	}
	
	
	
}
