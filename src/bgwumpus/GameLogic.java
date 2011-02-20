package bgwumpus;
import java.util.*;
import java.awt.Point;

public class GameLogic {
	static Point player_location = new Point(0,0);
	static Point wumpus_location = new Point(0,1);
	static Point[] nearby_locations ={new Point(0,1),new Point(0,-1),new Point(1,0),new Point(-1,0)}; //north south east west from (0,0)
	//static Point AILocation = new Point(0,0);
	static Player player = new Player();
	static Wumpus wumpus = new Wumpus();
	
	
	
	/**
	 * Gets a 2 dimensional array of Entities in order to render on top
	 * Returns objects directly, gives flexibility to know which object is which - something not provided by just type information
	 * @return 2D array of Entity objects
	 */
	public static Entity[][] getEntityMap()
	{
		Entity[][] entity_map = new Entity[Map.MAP_DIMENSIONS][Map.MAP_DIMENSIONS];
		for (int i=0; i<Map.MAP_DIMENSIONS; i++) {
			for (int j=0; j<Map.MAP_DIMENSIONS; j++) {
				if (player_location.getX() == j && player_location.getY() == i) {					
					entity_map[i][j]=player;
				}
				else if (wumpus_location.getX() ==j && wumpus_location.getY() == i) {
					entity_map[i][j]=wumpus;
				}
				else {
					entity_map[i][j]=null;
				}
			}
		}
		return entity_map;
	}

	/**
	 * @return the entity location
	 */
	public static Point getEntityLocation(EntityType type) {
		if (type == EntityType.PLAYER)
			return player_location;
		/*else if (type = EntityType.AI)
			return AILocation;*/
		else if (type == EntityType.WUMPUS)
			return wumpus_location;
		else
			return null;
	}

	/** set entity location
	 * @param the location expressed as a Point object and the type of entity as EntityType
	 */
	public static void setEntityLocation(Point entity_location, EntityType type) {
		if (type == EntityType.PLAYER)
			player_location = entity_location;
		else if (type == EntityType.WUMPUS)
			wumpus_location = entity_location;
		/*else if (type == EntityType.AI)
			AILocation = entity_location;*/

	}

	/** set entity location
	 * @param the location expressed as integers x and y and then the type of entity as EntityType
	 */
	public static void setEntityLocation(int x, int y, EntityType type) {
		if (type == EntityType.PLAYER)
			player_location.setLocation(x,y);
		else if (type == EntityType.WUMPUS)
			wumpus_location.setLocation(x,y);
		/*else if (type == EntityType.AI)
			AILocation.setLocation(x,y)*/

	}

	/**
	 * Initialise starting locations and create map
	 */
	public static void init() {
		
		Random rand = new Random();
		//Initialise player starting position
		player_location.setLocation(rand.nextInt(Map.MAP_DIMENSIONS),rand.nextInt(Map.MAP_DIMENSIONS));
		//Initialise wumpus starting position, make sure it isn't the same as the player location
		do {
			wumpus_location.setLocation(rand.nextInt(Map.MAP_DIMENSIONS),rand.nextInt(Map.MAP_DIMENSIONS));
		} while (wumpus_location.equals(player_location));
	}
	
	//TODO: use this when an arrow is badly shot
	/**
	 * move the wumpus random place next to square it's on
	 */
	public static void wumpusMove() {
		ArrayList<Point> moveable_locations = new ArrayList<Point>();
		for (Point i : nearby_locations) {
			Point current_iter =  new Point();
			current_iter.setLocation(wumpus_location.getX()+i.getX(),wumpus_location.getY()+i.getY());
			torusify(current_iter);
			//If nothing dangerous, add to possible moveable locations
			switch(Map.getTypeAt(current_iter)){
			case BAT: 
			case EXIT: 
			case PIT: break;
			default: 
				moveable_locations.add(current_iter);
				break;
			}
			
		}
		Random rand = new Random();
		//set the location, randomly picked from the list of possible options
		wumpus_location.setLocation(moveable_locations.get(rand.nextInt(moveable_locations.size())));

	}
	
	public static void movePlayer(int dx,int dy){
		
		
		player_location.translate(dx,dy);
		torusify(player_location);
		
	}
	
	/**
	 * Helper method to convert everything to torus coordinates (wrap-around)
	 * @param coords int array to convert to torus coordinates 
	 * NB: Requires array of exactly two integers.
	 * @return changes array directly
	 * @deprecated
	 */
	private static void torusify(int[] coords) {
		//less than 0? Loop over however many times needed.
		while (coords[0]<0) {
			coords[0] = Map.MAP_DIMENSIONS - coords[0];
		} //Same for y co-ordinate.
		while (coords[1]<0) {
			coords[1] = Map.MAP_DIMENSIONS - coords[1];
		}
		//More than the map dimensions? Loop over!
		while (coords[0]>Map.MAP_DIMENSIONS) {
			coords[0] = coords[0] - Map.MAP_DIMENSIONS;
		}
		//And for y
		while (coords[1]>Map.MAP_DIMENSIONS) {
			coords[1] = coords[1] - Map.MAP_DIMENSIONS;
		}
	}
	
	/**
	 * Helper method to convert everything to torus coordinates (wrap-around)
	 * @param point Point array to convert to torus coordinates 
	 */
	public static void torusify(Point point) {
		//less than 0? Loop over however many times needed.
		while (point.getX()<0) {
			point.setLocation(Map.MAP_DIMENSIONS + point.getX(),point.getY());
		} //Same for y coordinate.
		while (point.getY()<0) {
			point.setLocation(point.getX(),Map.MAP_DIMENSIONS + point.getY());
		}
		//More than the map dimensions? Loop over!
		while (point.getX()>=Map.MAP_DIMENSIONS) {
			point.setLocation(point.getX() - Map.MAP_DIMENSIONS,point.getY());
		}
		//And for y
		while (point.getY()>=Map.MAP_DIMENSIONS) {
			point.setLocation(point.getX(),point.getY() - Map.MAP_DIMENSIONS);
		}
	}
	
	
	
}
