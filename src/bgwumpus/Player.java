/**
 * 
 */
package bgwumpus;

import java.util.Set;

public class Player extends PlayableEntity {	
	public Player() {
		type = EntityType.PLAYER;
		alive = true;
		percepts.put("pits",false);
		percepts.put("bats",false);
		percepts.put("treasure", false);
		percepts.put("wumpus",false);
		// TODO Auto-generated constructor stub
	}
	

}
