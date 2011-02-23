package bgwumpus;
import java.util.*;
import java.awt.Point;

/**
 * This class keeps a record of where all the entities are, and has methods to define operations to move them and retrieve locations.
 *
 */
public class GameLogic {
	static Point location = new Point(-1,-1);
	static Point wumpus_location = new Point(0,1);
	static Point[] nearby_locations ={new Point(0,1),new Point(0,-1),new Point(1,0),new Point(-1,0)}; //north south east west from (0,0)
	static Point[] visible_locations = {new Point(0,1),new Point(0,0),new Point(0,-1),
	new Point(-1,-1),new Point(1,0),new Point(1,1),
	new Point(-1,0),new Point(-1,1), new Point(1,-1)};
	//static Point AI_location = new Point(0,0);

	public static boolean playable_mode = true;

	static ArrayList<Point> history = new ArrayList<Point>();

	static boolean map_revealed = false;
	static boolean wumpus_dead = false; //TODO put somewhere better?
	static boolean in_game = false;
	static boolean running = true;
	static Queue<String> message_queue = new LinkedList<String>();
	static int last_steps = -1;
	



	/** 
	 * Gets a 2 dimensional array of Entities in order to render on top
	 * Returns objects directly, gives flexibility to know which object is which - something not provided by just type information
	 * @return 2D array of Entity objects
	 * @deprecated

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
	}*/
	
	/**
	 * resets all variables to initial values for new game
	 */
	public static void reset(){
		
	}

	/**
	 * @return the queue of messages to be outputted
	 */
	public static Queue<String> getMessageQueue() {
		return message_queue;
	}


	/**
	 * Initialise starting locations and create map
	 * @parameter mode - which mode the game should be run in - AI or Player
	 */
	public static void init(EntityType mode) {
		
		

		Random rand = new Random();

		//reset variables
		history.clear();
		last_steps = -1;
		map_revealed = false;
		wumpus_dead = false;
		
		
		//Check what mode, and change co-ordinates appropriately
		if (mode==EntityType.PLAYER) {
			playable_mode = true;
		} else if (mode==EntityType.AI) {
			playable_mode = false;
		} else {
			System.err.println("Only available modes are EntityType.AI or EntityType.PLAYER.");
		}

		//Initialise player starting position
		do {
			location.setLocation(rand.nextInt(Map.MAP_DIMENSIONS),rand.nextInt(Map.MAP_DIMENSIONS));
		} while (Map.getTypeAt(location.x, location.y) == TileType.PIT);
		history.add(new Point(location));
		//Initialise wumpus starting position, make sure it isn't the same as the player location
		do {
			wumpus_location.setLocation(rand.nextInt(Map.MAP_DIMENSIONS),rand.nextInt(Map.MAP_DIMENSIONS));
		} while (wumpus_location.equals(location));

	}
	
	public static void switchMode(){
			playable_mode = !playable_mode;
	}


	/**
	 * Gets specified entity location.
	 * @param type EntityType to get location of
	 * @return the entity location as a point
	 */
	public static Point getEntityLocation(EntityType type) {
		if (type == EntityType.PLAYER || type == EntityType.AI)
			return location;
		else if (type == EntityType.WUMPUS)
			return wumpus_location;
		else
			return null;
	}


	/** set entity location
	 * @param the location expressed as a Point object and the type of entity as EntityType
	 */
	public static void setEntityLocation(Point entity_location, EntityType type) {
		if (type == EntityType.PLAYER || type == EntityType.AI) {
			location = entity_location;
			history.add(entity_location);
		} else if (type == EntityType.WUMPUS) {
			wumpus_location = entity_location;
		} /*else if (type == EntityType.AI) {
			AI_location = entity_location;
		}*/ else {
			System.err.println("Couldn't set entity location.");
			return;
		}

	}

	/** set entity location
	 * @param the location expressed as integers x and y and then the type of entity as EntityType
	 */
	public static void setEntityLocation(int x, int y, EntityType type) {
		if (type == EntityType.PLAYER || type == EntityType.AI) {
			location.setLocation(x,y);
			history.add(new Point(x,y));
		} else if (type == EntityType.WUMPUS) {
			wumpus_location.setLocation(x,y);
		} else {
			System.err.println("Couldn't set entity location.");
			return;
		}
	}

	/** set entity location
	 * @param the location expressed as integers x and y and then the type of entity as EntityType
	 */
	public static void setEntityLocation(Point entity_location) {
		location = entity_location;
		history.add(entity_location);
	}


	/**
	 * Translates entity  by amount specified
	 * @param dx integer amount to translate in x axis
	 * @param dy integer amount to translate in y axis
	 */
	public static void moveEntity(int dx,int dy){	

		//Point position = getEntityLocation(entity);
		location.translate(dx, dy);
		torusify(location);
		history.add(new Point(location));
		setEntityLocation(location,EntityType.AI);
		//player_steps++;
	}

	/**
	 * Translates entity  by amount specified
	 * @param dx integer amount to translate in x axis
	 * @param dy integer amount to translate in y axis
	 */
	public static void moveEntity(Point new_location){	

		history.add(new Point(new_location));
		setEntityLocation(new_location,EntityType.AI);
		//player_steps++;
	}


	/**
	 * move the wumpus random place next to square it's on
	 */
	public static void wumpusMove() {
		ArrayList<Point> movable_locations = new ArrayList<Point>();
		for (Point i : nearby_locations) {
			Point current_iter =  new Point();
			current_iter.setLocation(wumpus_location.getX()+i.getX(),wumpus_location.getY()+i.getY());
			torusify(current_iter);

			//If nothing dangerous, add to possible movable locations
			switch(Map.getTypeAt(current_iter)){
			case BAT: 
			case EXIT: 
			case PIT: 
				break;
			default: 
				movable_locations.add(current_iter);
				break;
			}

		}
		Random rand = new Random();
		//set the location, randomly picked from the list of possible options
		wumpus_location.setLocation(movable_locations.get(rand.nextInt(movable_locations.size())));

	}

	public static boolean shootWumpus(Point shotTo){

		//only move or set to dead if the wumpus is alive in the first place
		if(!wumpus_dead){

			if(shotTo.equals(wumpus_location)){
				wumpus_dead = true;
				message_queue.add("You killed the wumpus!");
				return true; //hit
			}

			wumpusMove(); //move the wumpus if the shot didn't hit 
			message_queue.add("The wumpus moved upon hearing your shot");


		}

		return false; //missed
	}

	/** Checks if the contents of the tile is visible to an entity or if it's too far away
	 * @param x the x position of the tile 
	 * @param y the y position of the tile
	 * @return true if visible for the entity, false if not.
	 */
	public static boolean checkVisibility(int x,int y){

		Point entity_pos = new Point(); //position of the entity which is trying to 'see' tiles
		entity_pos.setLocation(location); //set the location to that of the entity 

		//loop through the nearby locations to the entity
		for (Point i : visible_locations) {
			Point current_iter =  new Point();
			current_iter.setLocation(entity_pos.getX()+i.getX(),entity_pos.getY()+i.getY());
			torusify(current_iter);

			if(current_iter.equals(new Point(x,y))){ //if any of these locations equal the tile we are trying to test
				return true; //it's visible
			}
		}
		return false;

	}


	/** Gets the list of relevant perception messages
	 * @param the player object
	 * @return an array list of messages based on the perception of the surrounding tiles
	 */
	public static Queue<String> getPerceptionMessages(PlayableEntity player){
		Queue<String> perception_messages = new LinkedList<String>();
		
		
		if(player.getPlayerSteps() > last_steps){
			if(player.getPercept("pits")){
				perception_messages.add("You feel a breeze");
			}
			if(player.getPercept("bats")){
				perception_messages.add("You hear a flapping noise");				
			}
			if(player.getPercept("treasure")){
				perception_messages.add("You catch a sparkle in the corner of your eye");				
			}
			if(player.getPercept("wumpus")){
				perception_messages.add("Eurgh, what is that smell");		
			}
		
			last_steps = player.getPlayerSteps();
			
		}
		
		

		return perception_messages;


	}

	/** Gets the list of relevant perception codes
	 * @param the player object
	 * @return an array list of messages based on the perception of the surrounding tiles
	 */
	public static Queue<String> getPerceptionMessageCodes(PlayableEntity player){
		Queue<String> perception_messages = new LinkedList<String>();

		if(player.getPercept("pits")){
			perception_messages.add("P");
		}
		if(player.getPercept("bats")){
			perception_messages.add("B");				
		}
		if(player.getPercept("treasure")){
			perception_messages.add("T");				
		}
		if(player.getPercept("wumpus")){
			perception_messages.add("W");		
		}

		return perception_messages;


	}


	/** Sets the perception variables if appropriate
	 * @param player the player object
	 */
	public static void checkPercepts(PlayableEntity player){

		player.unsetAllPercepts();

		//loop through nearby locations e.g 1 tile North, South, East and West
		for(Point i : nearby_locations) {

			Point current_iter =  new Point();
			current_iter.setLocation(location.getX()+i.getX(),location.getY()+i.getY());
			torusify(current_iter);

			//check for different tile perceptions
			switch(Map.getTypeAt(current_iter)){

			case PIT: 
				player.setPercept("pits"); 
				break;
			case BAT: 
				player.setPercept("bats"); 
				break;
			case TREASURE: 
				//if the treasure has not been picked up
				if(!player.hasPickedUpTreasure()) 
					player.setPercept("treasure"); 
				break;
			default: 
				break;

			}

			//check for wumpus perception
			if(getTypeAt(current_iter) == EntityType.WUMPUS && !GameLogic.wumpusDead()){
				player.setPercept("wumpus"); 
			}

		}	

	}

	public static boolean wumpusDead() {

		return wumpus_dead;
	}


	/** Sets the perception variables if appropriate
	 * @param ai the ai object
	 */


	public static void doTile(PlayableEntity player){
		
		torusify(location);
		switch(Map.getTypeAt(location)){
		case PIT: 
			player.die(); 
			message_queue.add("You fell in a pit");
			break;
		case BAT: 
			movedBySuperbat();
			message_queue.add("A magical bat transported you");
			break;
		case TREASURE:
			if (!player.hasPickedUpTreasure()) {
				player.pickUpTreasure();
				message_queue.add("You found the treasure");
			}
			break;
		case EXIT:
			if (player.hasPickedUpTreasure()) {
				message_queue.add("You escaped the cave");
				player.winGame();
			}
			break;
		}

		if (getTypeAt(location) == EntityType.WUMPUS && !wumpus_dead) {
			message_queue.add("The wumpus devoured you!");
			player.die();
		}



	}

	/**
	 * Moves PlayableEntity to random location (but not a pit!)
	 */
	public static void movedBySuperbat(){
		Random rand = new Random();
		//randomly move
		do { //do while it has not generated a sensible location e.g on a pit or on the wumpus

			//set to a random x and y location
			location.setLocation(rand.nextInt(Map.MAP_DIMENSIONS),rand.nextInt(Map.MAP_DIMENSIONS));

		} while(Map.getTypeAt(location) == TileType.PIT && location != wumpus_location); 
		//if it chooses the same location, it'll choose a different random location

		//add the point
		history.add(new Point(location));

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

	/**
	 * Get entity type at certain point of the map
	 * @param x the x pos (where 0,0 is top left hand corner)
	 * @param y the y pos (where 0,0 is top left hand corner)
	 */
	public static EntityType getTypeAt(int x,int y){

		if(wumpus_location.getX() == x && wumpus_location.getY() == y){
			return EntityType.WUMPUS;
		}
		else if(location.getX() == x && location.getY() == y && playable_mode == true){
			return EntityType.PLAYER;
		}
		else if(location.getX() == x && location.getY() == y && playable_mode == false){
			return EntityType.AI;
		}


		return null;

	}

	/**
	 * Get entity type at certain point of the map
	 * @param p Point object with the co-ordinates to check for entity (0,0 is top left hand corner)
	 */
	public static EntityType getTypeAt(Point p){

		return getTypeAt((int)p.getX(),(int)p.getY());


	}


	public static boolean getRunning() {
		return running;
	}
	
	public static void setRunning(boolean true_or_false){
		running = true_or_false;
	}
	
	public static boolean getInGame(){
		return in_game;
	}
	
	public static void setInGame(boolean true_or_false){
		in_game = true_or_false;
	}


}
