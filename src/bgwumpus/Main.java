/**
 * This is the namespace
 */
package bgwumpus;

/**
 * Creates shit and then starts the game loop.
 *TODO Make design good object oriented, do proper encapsulation
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//TODO initialise everything
		GUI window = new GUI();	
		
		
		//UNCOMMENT FOR PLAYER MODE
		//GameLogic.init(EntityType.PLAYER);
		//AND COMMENT THIS OUT
		GameLogic.init(EntityType.AI);
		Player player1 = new Player();
		PlayableEntity ai = new AI();
		
		Map.init();
		
		boolean running = true;
		boolean in_game = true;
		
		//Program loop
		while (running) {
			
			//Game loop
			while(in_game){

				window.render();
				if (GameLogic.playable_mode) {
					GameLogic.doTile(player1);
					GameLogic.checkPercepts(player1);
					window.outputPerceptionMessages(player1);
					window.render();	
					if(!player1.isAlive()){ 
						running = false;
					}
				} else {
					GameLogic.doTile(ai);
					GameLogic.checkPercepts(ai);
					window.outputPerceptionMessages(ai);
					window.render();	
					if(!ai.isAlive()) {
						running = false;
					}
				}
				
				
			}
		}
	}
}