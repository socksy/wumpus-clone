package bgwumpus;

import java.util.ArrayList;

public class GeneticPool {
	static ArrayList<GeneticAI> pool = new ArrayList<GeneticAI>();
	public static void createPool(int size, int iterations) {
		int[] scores = new int[size];
		
		AI ai = new AI();
		//populate pool, make them all reference same player
		for (int i=0; i<size; i++) {
			pool.add(new GeneticAI(ai));
		}
		playGame(size, scores, ai);
		
		//already done 1 iteration
		for (int i=1; i<iterations; i++) {
			ArrayList<GeneticAI> temp = new ArrayList<GeneticAI>();
			temp.add(pool.get(maximumValueInArray(scores)));
			pool.remove(maximumValueInArray(scores)); //doing it twice - don't really need to loop
			temp.add(pool.get(maximumValueInArray(scores)));
			pool.clear();
			
			for(int j=0; j<size; j++) {
				pool.add(new GeneticAI(temp.get(0).mate(temp.get(1)), ai, temp.get(0).getProbabilityOfShooting())); //mate! mate little fishes mate!
			}
			
			playGame(size, scores, ai);
			
		}
		System.out.print(maximumValueInArray(scores)+" - ");
		System.out.println(pool.get(maximumValueInArray(scores)).getChromosone());
	}

	/**
	 * @param size
	 * @param scores
	 * @param ai
	 */
	private static void playGame(int size, int[] scores, AI ai) {
		for (int i=0; i<size; i++) {
			GameLogic.init(EntityType.AI);
			ai.reset();
			while (ai.isAlive()&&!ai.hasWon()) {
				pool.get(i).doSomething();
				GameLogic.doTile(ai);
				GameLogic.checkPercepts(ai);
			}
			scores[i] = ai.getScore();
			//System.out.println(scores[i]);

		}
	}
	
	public static void createPool(int iterations) {
		createPool(400, iterations);
	}
	
	
	public static void main(String[] args) {
		Map.init();
		for (int i=0; i<5; i++){
		createPool(500);}
	}
	
	/**
	 * 
	 * @param array
	 * @return index of maximum value
	 */
	private static int maximumValueInArray(int[] a) {
		int max=-1000;
		int index=0;
		for (int i=0;i<a.length;i++) {
			if (a[i] > max) {
				max = a[i];
				index = i;
			}
		}
		return index;
	}
	
}
