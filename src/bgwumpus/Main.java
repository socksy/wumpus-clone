/**
 * This is the namespace
 */
package bgwumpus;

/**
 * Creates shit and then starts the game loop.
 *
 */
//Note to self: lucid chart
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//TODO initialise everything
		Map.init();
		GameLogic.init();
		GUI Window = new GUI();		
		Window.render();
		
		//Game loop
		boolean running = true;
		while (running) {
			//TODO give feedback (where are we, what are we near?)
			//TODO take input
			//TODO act upon input
		}
		
	}

	
}
