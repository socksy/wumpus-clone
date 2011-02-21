package bgwumpus;


public class AI extends PlayableEntity {

	public AI() {
		type = EntityType.AI;
		percepts.put("pits",false);
		percepts.put("bats",false);
		percepts.put("treasure", false);
		percepts.put("wumpus",false);
		// TODO Auto-generated constructor stub
	}

}
