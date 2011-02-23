package bgwumpus;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class ReactiveAI {
	//0 for undiscovered, 1 for discovered and safe, subtract 1 every time a pit is perceived nearby
	int[][] map_representation = new int[Map.MAP_DIMENSIONS][Map.MAP_DIMENSIONS];
	Point[] nearby_locations ={new Point(0,1),new Point(0,-1),new Point(1,0),new Point(-1,0)}; //north south east west from (0,0)
	AI ai;
	
	public ReactiveAI(PlayableEntity ai) {
		this.ai = (AI)ai;
	}
	
	private void updateMap() {
		if (ai.isAlive()) {
			map_representation[ai.getLocation().y][ai.getLocation().x] = 1;
		}
		Point current_iter =  new Point();
				
		//iterate through nearby locations
		for (Point i : nearby_locations) {
			current_iter.setLocation(ai.getLocation().x+i.x,ai.getLocation().y+i.y);
			GameLogic.torusify(current_iter);
			//check for percepts
			if (map_representation[current_iter.y][current_iter.x]!=1) { //if square is undiscovered
				if (ai.getPercept("pits")) { 
					map_representation[current_iter.y][current_iter.x]--;
				}
				if (ai.getPercept("treasure")) {
					map_representation[current_iter.y][current_iter.x]++;
				}
				if (ai.getPercept("wumpus")) {
					map_representation[current_iter.y][current_iter.x]--; //dumb - it doesn't account for whe
				}
			}
		}
	}
	
	public void moveDirection() {
		updateMap();
		HashMap<String, Point> movable_directions= new HashMap<String, Point>();
		int[] values = new int[4];
		Point current_iter =  new Point();
		for (int i=0; i<nearby_locations.length; i++) {
			//iterate around nearby squares
			current_iter.setLocation(ai.getLocation().x+nearby_locations[i].x,ai.getLocation().y+nearby_locations[i].y);
			GameLogic.torusify(current_iter);
			//hashtable, safety value as string key, relative location as point value
			movable_directions.put(Integer.toString(map_representation[current_iter.getLocation().y][current_iter.getLocation().x]),nearby_locations[i]);
			//values, all the different safety values
			values[i] = map_representation[current_iter.getLocation().y][current_iter.getLocation().x];
		}
		Arrays.sort(values);
		int[] keys = reverseArrayOrder(values); //descending order sorted - most safe first
		Random rand = new Random();
		double dice_roll = rand.nextDouble();
		
		//probably move in safest direction
		if (dice_roll<0.8) {
			ai.move(movable_directions.get(Integer.toString(keys[0])));
		} else if (dice_roll<0.96) {
			ai.move(movable_directions.get(Integer.toString(keys[1])));
		} else if (dice_roll<0.99) {
			ai.move(movable_directions.get(Integer.toString(keys[2])));
		} else {
			ai.move(movable_directions.get(Integer.toString(keys[3])));
		}
	}
	
	//Array.sort doesn't do descending order?
	private int[] reverseArrayOrder(int[] array) {
		int[] temp = new int[array.length];
		for (int i=1; i<=array.length; i++) {
			temp[array.length-i]=array[i-1];
		}
		return temp;
	}
}
