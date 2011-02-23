package bgwumpus;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import sun.misc.Queue;


public class SmartAI {
	
	public static int map[][] = new int[Map.MAP_DIMENSIONS][Map.MAP_DIMENSIONS];
	public static PlayableEntity ai;
	
	public static void init(PlayableEntity an_ai){
				
		for(int i=0; i<Map.MAP_DIMENSIONS; i++){
			for(int j=0; j<Map.MAP_DIMENSIONS; j++){
				map[j][i] = 0;
			}
		}
		
		ai = an_ai;
		
		
	}
	
	public static void moveSensibleDirection(){
		
		Point pos = GameLogic.getEntityLocation(EntityType.AI);
		Point current_iter =  new Point();
		Point leastDangerous = new Point();
		int highestDanger = 0;
		
		//TODO this bit of code is replicated quite a lot, possibly abstract it further in GameLogic
		for(Point i : GameLogic.nearby_locations){
			
			current_iter.setLocation(pos.getX()+i.getX(),pos.getY()+i.getY());
			GameLogic.torusify(current_iter);
			
			if(highestDanger >= map[(int) current_iter.getX()][(int) current_iter.getY()]){
				leastDangerous = current_iter;
			}

			
		}
		
		if(highestDanger == 0){
			moveRandomDirection();
		}
		
		GameLogic.moveEntity(leastDangerous);
		
	}
	
	/*public static void reactToPercepts(){
		
		Queue<String> messages = GameLogic.getPerceptionMessageCodes(ai);
		Point pos = GameLogic.getEntityLocation(EntityType.AI);
		
		for(int i=0; i<messages.size(); i++){
			
			if(messages.get(i) == "P"){
				
				Point current_iter = new Point();
				
				for(Point point : GameLogic.nearby_locations){
					current_iter.setLocation(pos.getX()+point.getX(),pos.getY()+point.getY());
					GameLogic.torusify(current_iter);
					map[(int)current_iter.getX()][(int)current_iter.getY()] = map[(int)current_iter.getX()][(int)current_iter.getY()]++;
				}
				
			}
			
		}
		
		
	}*/
	
	public static void printHazardMap(){
		
		for(int i=0; i<Map.MAP_DIMENSIONS; i++){
			for(int j=0; j<Map.MAP_DIMENSIONS; j++){
				System.out.print(map[j][i]); 
			}
			System.out.print("\n");
		}
		
		System.out.print("\n");
		
		try {
			Thread.sleep(1000);
		} catch (Exception e){
			
		}
	}
	
	
	
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
