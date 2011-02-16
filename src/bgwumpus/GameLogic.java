package bgwumpus;

public class GameLogic {
	static int[] PlayerLocation = {0,0};
	static int[] WumpusLocation = {1,0};
	static Player player1 = new Player();
	static Wumpus wumpus1 = new Wumpus();
	
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
				if (PlayerLocation[0]==j && PlayerLocation[1]==i) {					
					entity_map[i][j]=player1;
				}
				else if (WumpusLocation[0]==j && WumpusLocation[1]==i) {
					entity_map[i][j]=wumpus1;
				}
				else {
					entity_map[i][j]=null;
				}
			}
		}
		return entity_map;
	}
}
