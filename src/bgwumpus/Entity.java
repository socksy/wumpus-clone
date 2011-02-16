package bgwumpus;

/**
 * 
 *
 */
public abstract class Entity {
	 public EntityType type;
	//TODO: EVERYTHING
	//(what methods? does it move? what attributes?)

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
