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
	static Tile wumpus_start_location = new StandardTile();
	static Tile player_start_location = new StandardTile();
	
	
	/**
	 * Initialises starting locations, creates map, etc.
	 */
	public static void init(){
		
		for(int i=0; i<MAP_DIMENSIONS; i++){
			for(int j=0; j<MAP_DIMENSIONS; j++){
				map_array[i][j] = new StandardTile();
			}
		}
		
		generateMap();
		
		
	}
	
	/**
	 * Generates a random map
	 */
	private static void generateMap(){
		
		//TODO possibly make random generation ensure that the treasure is a sensible distance away from both player and exit
		
		//fill the map with standard tiles
		for(int i=0; i<MAP_DIMENSIONS; i++){
			for(int j=0; j<MAP_DIMENSIONS; j++){
			
				map_array[j][i] = new StandardTile();
			
			}
		}		
		
		int PitProbability = 20; //the probability of a pit tile being generated, out of 100
		int BatProbability = 5; //the probability of a tile being generated, out of 100

		Random random = new Random();
		
		
		//fill the map with pits and bats randomly
		for(int i=0; i<MAP_DIMENSIONS; i++){
			for(int j=0; j<MAP_DIMENSIONS; j++){
				
				int random_number =  random.nextInt(101); //generate a random number between 0 and 100
				
				if(random_number <= PitProbability){
					map_array[j][i] = new PitTile();
				}
				if(random_number <= BatProbability){
					map_array[j][i] = new BatTile();
				}
			}
		}
		
		
		
		
		
		map_array[(int)GameLogic.getEntityLocation(EntityType.WUMPUS).getX()][(int)GameLogic.getEntityLocation(EntityType.WUMPUS).getY()] = wumpus_start_location; //set the wumpus start location to the corresponding coordinates
		map_array[(int)GameLogic.getEntityLocation(EntityType.PLAYER).getX()][(int)GameLogic.getEntityLocation(EntityType.PLAYER).getY()] = player_start_location; //set the player start location to the corresponding coordinates
		
		int x_index = random.nextInt(Map.MAP_DIMENSIONS);
		int y_index = random.nextInt(Map.MAP_DIMENSIONS);

		
		//pick a location for the exit
		while(map_array[x_index][y_index].equals(wumpus_start_location) || map_array[x_index][y_index].equals(player_start_location)) {
			
			x_index = random.nextInt(Map.MAP_DIMENSIONS);
			y_index = random.nextInt(Map.MAP_DIMENSIONS);		
		} 
		
		map_array[x_index][y_index] = new ExitTile();
		
		//pick a location for the treasure
		while(map_array[x_index][y_index].equals(wumpus_start_location) || map_array[x_index][y_index].equals(player_start_location) || map_array[x_index][y_index].getType() == TileType.EXIT) {
			
			x_index = random.nextInt(Map.MAP_DIMENSIONS);
			y_index = random.nextInt(Map.MAP_DIMENSIONS);		
		} 
		
		map_array[x_index][y_index] = new TreasureTile();
		
		
		//TODO sort the tiles randomly?
		
		
		
		
		
		
	}
	
	
	
	
	/**
	 * Prints map state
	 * @deprecated
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
