package bgwumpus;

public class GeneticPool {
	static GeneticAI[] pool;
	public static void createPool(int size, int iterations) {
		int[] scores = new int[size];
		pool = new GeneticAI[size];
		AI ai = new AI();
		//populate pool, make them all reference same player
		for (int i=0; i<size; i++) {
			pool[i] = new GeneticAI(ai);
		}
		for (int i=0; i<size; i++) {
			GameLogic.init(EntityType.AI);
			ai.reset();
			while (ai.isAlive()&&ai.hasWon()) {
				pool[i].doSomething();
				GameLogic.doTile(ai);
				GameLogic.checkPercepts(ai);
			}
			scores[i] = ai.getScore();
		}
		
		for (int i : scores) {
			System.out.println(i);
		}
		
	}
	
	public static void createPool(int iterations) {
		createPool(40, iterations);
	}
	
	
}
