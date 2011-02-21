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
		GameLogic.init();
		Map.init();
		GUI window = new GUI();	
		Player player1 = new Player();
		
		boolean running = true;
		boolean in_game = true;
		
		//Program loop
		while (running) {
			
			//Game loop
			while(in_game){
			
			window.render();
			GameLogic.doTile(player1);
			GameLogic.checkPercepts(player1);
			window.outputPerceptionMessages(player1);
			
			window.render();

			if(!player1.isAlive()) running = false;
			//TODO give feedback (where are we, what are we near?)
			//TODO take input
			//TODO act upon input
			}
		}
	
	}
	

	
}
