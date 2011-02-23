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

		boolean switched = false;
		
		Map.init();
		//UNCOMMENT FOR PLAYER MODE
		GameLogic.init(EntityType.PLAYER);
		//AND COMMENT THIS OUT
		//GameLogic.init(EntityType.AI);

		Player player1 = new Player();
		PlayableEntity ai = new AI();
		ReactiveAI react = new ReactiveAI(ai);
		GUI window = new GUI(player1);	

		GameLogic.setRunning(true);
		
		//Program loop
		while (GameLogic.getRunning()) {
			
			if(GameLogic.playable_mode != true && !switched){
				
				GameLogic.init(EntityType.AI);
				window = new GUI(ai);
				switched = !switched;
				
				System.out.println("switching to AI");
				
				
			}
			else if(switched) {
				
				GameLogic.init(EntityType.PLAYER);
				window = new GUI(player1);
				switched = !switched;
				
			}
			
			
			//Game loop
			while(GameLogic.getInGame()){

				window.render();
				if (GameLogic.playable_mode) {
					GameLogic.doTile(player1);
					GameLogic.checkPercepts(player1);
					window.outputMessages(player1);
					window.render();	
					if(!player1.isAlive()){ 
						GameLogic.setInGame(false);
						try {
							Thread.sleep(300);
							
						} catch (Exception e) {
							// TODO: handle exception
						}
					}else if (player1.hasWon()) {
						GameLogic.setInGame(false);
						System.out.println("You have won"); //TODO REMOVE
						try {
							Thread.sleep(300);
							
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				} else {
					SmartAI.init(ai);
					SmartAI.moveSensibleDirection();
					GameLogic.doTile(ai);
					GameLogic.checkPercepts(ai);
					window.outputMessages(ai);
					window.render();	
					if(!ai.isAlive()) {
						GameLogic.setInGame(false);
					} else if (ai.hasWon()) {
						GameLogic.setInGame(false);
						System.out.println("Congrats."); //TODO REMOVE
					}
					
					try {
						Thread.sleep(300);
						
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				
				//if it's about to return to menu
				if(GameLogic.getInGame() == false){
					//reset everything
					Map.init();
					if(GameLogic.playable_mode){ //TODO make getter
					GameLogic.init(EntityType.PLAYER);
					player1.reset();
					}else {
					GameLogic.init(EntityType.AI);
					ai.reset();
					}
				}
				
			}
			
			window.render();
			
		}
	}
	
	
}