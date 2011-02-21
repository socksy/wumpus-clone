/**
 * This is the namespace
 */
package bgwumpus;

/**
 * Creates shit and then starts the game loop.
 *TODO Make design good object oriented, do proper encapsulation
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
		GUI window = new GUI();	
		Player player1 = new Player();
		
		//Game loop
		boolean running = true;
		while (running) {
			
			window.outputPerceptionMessages(player1);
			window.render();
			//TODO give feedback (where are we, what are we near?)
			//TODO take input
			//TODO act upon input
		}
		
	}

	
}
