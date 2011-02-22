
package bgwumpus;

import java.util.Random;

public class DumbAI {
	
	public static void moveRandomDirection() {
		Random rand = new Random();
		switch (rand.nextInt(4)) {
			case 0: //left
				GameLogic.moveEntity(-1,0);
				break;
			case 1: //up
				GameLogic.moveEntity(0,-1);
				break;
			case 2: //right
				GameLogic.moveEntity(1,0);
				break;
			case 4: //down
				GameLogic.moveEntity(0,1);
		}
		
	}
	
}
