/**
 * 
 */
package bgwumpus;

/**
 * All user interface classes should have this as a template
 * It lets us abstract away which interface we're using
 */
public interface UserInterface {

	public void render();
	public void outputPerceptionMessages(PlayableEntity player);
	public void outputMessages(PlayableEntity player);
	
}
