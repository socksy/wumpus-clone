package bgwumpus;

import java.awt.Point;
import java.util.HashMap;

public abstract class PlayableEntity extends Entity {
	protected static int player_steps = 0;
	protected HashMap<String,Boolean> percepts = new HashMap<String,Boolean>();
	protected boolean alive = true;
	protected boolean got_treasure = false;
	protected int score = 0;
	protected boolean won_game = false;

	public PlayableEntity() {
		super();
	}

	public void setPercept(String name) {
		percepts.put(name, true);		
	}

	/**
	 * Get whether or not a percept is true for the entity.
	 * @param name of the percept to check
	 * @return boolean
	 */
	public boolean getPercept(String name) {
		return percepts.get(name);
	}

	/**
	 * Unsets all perceptions
	 * @param name
	 */
	public void unsetPercept(String name) {
		percepts.put(name, false);
	}

	/**
	 * Sets all current percepts (current perceptions) to false
	 */
	public void unsetAllPercepts() {
		for(String key : percepts.keySet()){
			percepts.put(key,false);
		}
	}

	/**
	 * @return the player_steps
	 */
	public static int getPlayerSteps() {
		return player_steps;
	}

	/**
	 * Take a step
	 */
	public void step() {
		player_steps++;
	}
	
	/**
	 * Check if the entity is alive
	 * @return boolean
	 */
	public boolean isAlive() {
		return alive;
	}
	
	/**
	 * Kills it off.
	 */
	public void die() {
		if(isAlive()) {			
			alive = false;
			score -= 50;
		}
	}
	
	/**
	 * Picked up treasure, can now leave the exit
	 */
	public void pickUpTreasure() {
		if(!got_treasure) {
			got_treasure = true;
			score += 30;			
		}
	}
	
	/**
	 * Check if treasure has been picked up yet
	 * @return boolean
	 */
	public boolean hasPickedUpTreasure() {
		return got_treasure;
	}
	
	/**
	 * Won the game!
	 */
	public void winGame() {
		if (!hasWon()) {			
			score +=50;
			won_game = true;
		}
	}
	
	/**
	 * Has it won the game yet?
	 * @return boolean
	 */
	public boolean hasWon() {
		return won_game;
	}
	
	/**
	 * Shoot arrow into the darkness
	 * @param direction as an int
	 * 0 - left
	 * 1 - up
	 * 2 - right
	 * 3 - down
	 */
	public void shoot(int direction) {
		
		Point x = new Point(GameLogic.getEntityLocation(EntityType.PLAYER));
		
		switch (direction) {
			case 0: //left!
				x.translate(-1,0);
				break;
			case 1: //up!
				x.translate(0,-1);
				break;
			case 2: //right!
				x.translate(1,0);
				break;
			case 3: //down!
				x.translate(0,1);
				break;		
		}
		
		if (!GameLogic.wumpus_dead) {
			if(GameLogic.shootWumpus(x))
			score += 30; //if it has successfully shot it
		} else {
			score -=3; //missed
		}
		
		
	}

	public int getScore() {
		// TODO Auto-generated method stub
		
		return score;
	}
	
	public void moveLeft() {
		GameLogic.moveEntity(-1, 0);
		step();
	}
	public void moveRight() {
		GameLogic.moveEntity(1, 0);
		step();
	}
	public void moveUp() {
		GameLogic.moveEntity(0, -1);
		step();
	}
	public void moveDown() {
		GameLogic.moveEntity(0, 1);
		step();
	}
}