package bgwumpus;
import java.awt.Point;
import java.util.*;


/**
 * Map class includes the map data and model
 *
 */
public class Map {
	
	final static public int MAP_DIMENSIONS = 9; //FOR NOW
	static Tile[][] map_array = new Tile[MAP_DIMENSIONS][MAP_DIMENSIONS];
	
	//generateMap();
	public static void init(){
		
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				map_array[i][j] = new StandardTile();
			}
		}
		
		
	}
	
	/**
	 * Generates a random map
	 */
	private static void generateMap(){
		
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
	
	/**
	 * @deprecated
	 * Prints map state
	 */
	public static void printMapState(){
		
		
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
	
	/**
	 * Get Tile Type at set coordinates
	 * 0,0 is top left
	 * @param x integer
	 * @param y integer
	 * 
	 * @return TileType enum (currently PIT, STANDARD, EXIT or BAT)
	 */
	public static TileType getTypeAt(int x,int y){
		
		return map_array[y][x].getType();
		
		
	}
	
	/**
	 * Get Tile Type at set coordinates from Point
	 * 0,0 is top left
	 * @param p Point object containing coordinates
	 * @return TileType enum (currently PIT, STANDARD, EXIT or BAT)
	 */
	public static TileType getTypeAt(Point p){
		return map_array[(int)p.getY()][(int)p.getX()].getType();
		
	}
	
	/**
	 * Get current map layout, as internally represented by this class.
	 * @return 2D array of Tiles
	 */
	public static Tile[][] getMap(){
		
		return map_array;
		
	}
	
	
	
	
}
