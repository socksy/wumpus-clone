package bgwumpus;

import java.awt.Point;


public class AI extends PlayableEntity {

	public AI() {
		type = EntityType.AI;
		percepts.put("pits",false);
		percepts.put("bats",false);
		percepts.put("treasure", false);
		percepts.put("wumpus",false);
	}

	public Point getLocation() {
		return GameLogic.getEntityLocation(EntityType.AI);
	}
	
	public void move(Point relative_location) {
		GameLogic.moveEntity(relative_location.x, relative_location.y);
		step();
	}
}
