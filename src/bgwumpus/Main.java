package bgwumpus;

/**
 * The Main class, contains the main() function
 *
 */
public class Main {

	/**
	 * @param args
	 */

	public static GUI window;
	public static PlayableEntity player = new Player();
	public static AI ai = new AI();

	public static void loadAI(){

		ai = new AI();

		Map.init();

		GameLogic.init(EntityType.AI);

		window = new GUI(ai);

	}

	public static void loadPlayer(){

		player = new Player();

		Map.init();


		GameLogic.init(EntityType.PLAYER);

		window = new GUI(player);

	}



	public static void main(String[] args) {


		boolean current_mode = GameLogic.playable_mode;
		ReactiveAI react = new ReactiveAI(ai); //replaced with genetic AI
		GeneticAI gen = new GeneticAI("00110000100110100110011000111000000110101100100100011111" +
									  "01100111101000000101010000101101010001110000000110011110" +
									  "101000000010010000101011011010011100110111000000", ai, 0.2);

		if(GameLogic.playable_mode){
			loadPlayer();
		}
		else {
			loadAI();
		}

		GameLogic.setRunning(true);

		//Program loop
		while (GameLogic.getRunning()) {

			if(current_mode != GameLogic.playable_mode){

				if(GameLogic.playable_mode){
					loadPlayer();
				}
				else {
					loadAI();
				}

				current_mode = GameLogic.playable_mode;
			}


			while(GameLogic.getInGame()){


				window.render();

				if(!GameLogic.playable_mode){
					gen.doSomething();

					GameLogic.doTile(ai);
					GameLogic.checkPercepts(ai);
					window.outputMessages(ai);
					window.render();	
					if(!ai.isAlive()) {
						GameLogic.setInGame(false);
					} else if (ai.hasWon()) {
						GameLogic.setInGame(false);
						GameLogic.getMessageQueue().add("Congratulations, you have won");
						
					}

					try {

						Thread.sleep(500);
					}
					catch(Exception e){

					}
				}
				else {

					GameLogic.doTile(player);
					GameLogic.checkPercepts(player);
					window.outputMessages(player);
					window.render();	
					if(!player.isAlive()) {
						GameLogic.setInGame(false);
					} else if (player.hasWon()) {
						GameLogic.setInGame(false);
						GameLogic.getMessageQueue().add("Congratulations, you have won");
					}


				}

				window.render();



			}


			window.render();

		}
	}


}