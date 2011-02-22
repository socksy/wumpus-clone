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
	
	public void moveLeft() {
		if (GameLogic.playable_mode) {
			GameLogic.moveEntity(-1, 0);
		}
	}
	public void moveRight() {
		if (GameLogic.playable_mode) {
			GameLogic.moveEntity(1, 0);
		}
	}
	public void moveUp() {
		if (GameLogic.playable_mode) {
			GameLogic.moveEntity(0, -1);
		}
	}
	public void moveDown() {
		if (GameLogic.playable_mode) {
			GameLogic.moveEntity(0, 1);
		}
	}

}
