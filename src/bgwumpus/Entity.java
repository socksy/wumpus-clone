package bgwumpus;

/**
 * Class from which to 
 *
 */
public abstract class Entity {
	 public EntityType type;

	/**
	 * @return the enum type of the entity
	 */
	public EntityType getType() {
		return type;
	}

	/**
	 * @param type sets the enum type of the entity
	 */
	public void setType(EntityType type) {
		this.type = type;
	}
}
