package bgwumpus;

import java.util.HashMap;

public abstract class PlayableEntity extends Entity {
	protected static int player_steps = 0;
	protected HashMap<String,Boolean> percepts = new HashMap<String,Boolean>();
	protected boolean alive;

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
	public static int getPlayer_steps() {
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
		alive = false;
	}

}