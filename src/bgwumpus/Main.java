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

		
		
		Map.init();
		//UNCOMMENT FOR PLAYER MODE
		//GameLogic.init(EntityType.PLAYER);
		//AND COMMENT THIS OUT
		GameLogic.init(EntityType.AI);

		Player player1 = new Player();
		PlayableEntity ai = new AI();
		ReactiveAI react = new ReactiveAI(ai);
		
		
		GUI window = new GUI(ai);	

		
		
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
					window.outputMessages(player1);
					window.render();	
					if(!player1.isAlive()){ 
						in_game = false;
					}else if (player1.hasWon()) {
						in_game = false;
						System.out.println("Congrats."); //TODO REMOVE
					}
				} else {
					//SmartAI.init(ai);
					//SmartAI.reactToPercepts();
					//SmartAI.moveSensibleDirection();
					//react.moveDirection();
					
					GameLogic.doTile(ai);
					GameLogic.checkPercepts(ai);
					window.outputMessages(ai);
					window.render();	
					if(!ai.isAlive()) {
						in_game = false;
					} else if (ai.hasWon()) {
						in_game = false;
						System.out.println("Congrats."); //TODO REMOVE
					}
					
					try {
						Thread.sleep(500);
						
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				
				
			}
			
			window.render();
			running = false;
			
		}
	}
	
	
}