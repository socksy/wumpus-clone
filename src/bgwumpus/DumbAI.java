
package bgwumpus;

import java.util.Random;

public class DumbAI {
	
	public void moveRandomDirection() {
		Random rand = new Random();
		switch (rand.nextInt(4)) {
			case 0: //left
				GameLogic.moveEntity(-1,0,EntityType.AI);
				break;
			case 1: //up
				GameLogic.moveEntity(0,-1,EntityType.AI);
				break;
			case 2: //right
				GameLogic.moveEntity(1,0,EntityType.AI);
				break;
			case 4: //down
				GameLogic.moveEntity(0,1,EntityType.AI);
		}
	}
	
	public static void main(String[] args) {
		
	}
	
}
