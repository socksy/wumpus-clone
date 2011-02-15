package bgwumpus;


public abstract class Tile {
	TileType type;

	/**
	 * @return returns the enum type of the tile
	 */
	public TileType getType() {
		return type;
	}

	/**
	 * @param type sets the enumerated type of the tile
	 */
	public void setType(TileType type) {
		this.type = type;
	}	
	
	
}
