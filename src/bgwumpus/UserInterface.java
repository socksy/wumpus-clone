/**
 * 
 */
package bgwumpus;

/**
 * All user interface classes should have this as a template
 * It lets us abstract away which interface we're using
 */
public interface UserInterface {

	//TODO fill out interface with appropriate methods to implement
	public void render();
	public void outputPerceptionMessages(Player player);
	public void outputPerceptionMessages(AI ai);

	

}
