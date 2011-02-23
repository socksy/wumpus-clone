package bgwumpus;

import java.util.Arrays;
import java.util.Random;

public class GeneticAI {
	AI ai;
	static int chromo_length = 40;
	String chromosone = "";
	double[] prob_of_movement = new double[chromo_length/2]; //5 sets of four actions (does something when has no percepts too)
	double[] prob_of_shooting = new double[chromo_length/2]; //4 sets of five actions (not shooting)
	double probability_of_shooting = 0.2;
	double mutation_rate = 0.1;

	
	public GeneticAI(String chromosone, AI ai, double shootingprob) {
		if (chromosone.length()!=(chromo_length*4)) {
			System.err.println("Chromosone wrong size, should be "+chromo_length*4+" characters long.");
		}
		this.chromosone = chromosone;
		this.ai = ai;
		probability_of_shooting = shootingprob;
		
		decodeChromosone();
	}
	
	//default to test
	public GeneticAI(AI ai) {
		this(generateRandomChromosone(), ai, 0.1);
	}
	
	private static String generateRandomChromosone() {
		String temp = "";
		Random rand = new Random();
		for (int i=0; i<chromo_length; i++) {
			String bin_temps = "";
			String bin_end = Integer.toBinaryString(rand.nextInt(16));
			int fill = 4 - bin_end.length(); //to make sure we get strings in the form eg. 0010 
			for (int j=0; j<fill; j++) {
				bin_temps+='0';
			}
			bin_temps += bin_end;
			temp += bin_temps;
		}
		return temp;
	}
	
	/**
	 * 
	 * @param percept integer
	 * 0 - pits
	 * 1 - bats
	 * 2 - treasure
	 * 3 - wumpus
	 * 4 - none
	 */
	public void moveDirection (int percept) {
		Random rand = new Random();
		double possibilities[] = {prob_of_movement[percept*4], prob_of_movement[(percept*4)+1], prob_of_movement[(percept*4)+2], prob_of_movement[(percept*4)+3]};
		double randomnumber = rand.nextDouble(); //anything between 0 and 1.0
		Arrays.sort(possibilities);
		if (randomnumber < possibilities[3]) {
			ai.moveLeft();
		} else if (randomnumber < possibilities[2]) {
			ai.moveUp();
		} else if (randomnumber < possibilities[1]) {
			ai.moveRight();
		} else {
			ai.moveDown();
		}
	}
	
	/**
	 * Shoot direction - don't shoot when no percept!
	 * Do not pass 4!
	 * @param percept
	 * 0 - pits
	 * 1 - bats
	 * 2 - treasure
	 * 3 - wumpus
	 */
	public void shootDirection (int percept) {
		Random rand = new Random();
		double possibilities[] = {prob_of_shooting[percept*5], prob_of_shooting[(percept*5)+1], prob_of_shooting[(percept*5)+2], prob_of_shooting[(percept*5)+3], prob_of_shooting[(percept*5)+4]};
		double randomnumber = rand.nextDouble(); //anything between 0 and 1.0
		Arrays.sort(possibilities);
		if (randomnumber < possibilities[4]) {
			ai.shootLeft();
		} else if (randomnumber < possibilities[3]) {
			ai.shootUp();
		} else if (randomnumber < possibilities[2]) {
			ai.shootRight();
		} else if (randomnumber < possibilities[1]) {
			ai.shootDown();
		} else {
			//nothing
		}
	}
	Random rand = new Random();
	public void doSomething () {
		if(ai.getPercept("pits")) {
			if (rand.nextDouble()>probability_of_shooting) {
				moveDirection(0);
			} else {
				shootDirection(0);
			}
		} else if(ai.getPercept("bats")) {
			if (rand.nextDouble()>probability_of_shooting) {
				moveDirection(1);
			} else {
				shootDirection(1);
			}
		} if(ai.getPercept("treasure")) {
			if (rand.nextDouble()>probability_of_shooting) {
				moveDirection(2);
			} else {
				shootDirection(2);
			}
		} else if(ai.getPercept("wumpus")) {
			if (rand.nextDouble()>probability_of_shooting) {
				moveDirection(3);
			} else {
				shootDirection(3);
			}
		} else {
			moveDirection(4);
		}
	}
	
	
	/**
	 * Decodes string chromosone into arrays
	 */
	private void decodeChromosone() {
		int[] probabilities_of_movement = new int[chromo_length]; //one for every percept + no_percept
		int[] probabilities_of_shooting = new int[chromo_length]; //one for every percept + no shoot (no percepts, no shoot)
		int totals_movement[] = new int[chromo_length/8];

		int totals_shooting[] = new int[chromo_length/10];
		
		//Get numbers out from binary
		for (int i=0; i<(chromo_length*4)/2; i+=4) {
			probabilities_of_movement[i/4] = Integer.parseInt(chromosone.substring(i,i+4), 2); //read binary
		}
		for (int i=0; i<(chromo_length*4)/2; i+=4) {
			probabilities_of_shooting[i/4] = Integer.parseInt(chromosone.substring(i+(chromo_length/2),i+4+(chromo_length/2)), 2);
		}
		
		//convert them to doubles less than 1
		for (int i=0; i<chromo_length/2; i+=4) {
			for (int j=0; j<4; j++) {
				totals_movement[i/4] += probabilities_of_movement[i+j];
			}
			for (int j=0; j<4; j++) {
				prob_of_movement[i+j] = (double)(probabilities_of_movement[i+j])/(totals_movement[i/4]);
			}
		}
		for (int i=0; i<chromo_length/2; i+=5) {
			for (int j=0; j<5; j++) {
				totals_shooting[i/5] += probabilities_of_shooting[i+j];
			}
			for (int j=0; j<5; j++) {
				prob_of_shooting[i+j] = (double)probabilities_of_shooting[i+j]/totals_shooting[i/5];
			}
		}
	}
	

	/**
	 * @return the chromo_length
	 */
	public int getChromoLength() {
		return chromo_length;
	}
	
	/**
	 * @return the chromosone
	 */
	public String getChromosone() {
		return chromosone;
	}

	public String mate(GeneticAI other) {
		Random rand = new Random();
		int crossover_pos = rand.nextInt(chromo_length*4);
		
		String temporary_chromo = "";
		temporary_chromo += chromosone.substring(0, crossover_pos);
		temporary_chromo += other.getChromosone().substring(crossover_pos, chromo_length*4);
		
		//TODO MOVE TO POOL
		//double shootingprob = (probability_of_shooting+other.getProbabilityOfShooting())/2;
		
		return mutate(chromosone);
	}
	
	
	/**
	 * @return the probability_of_shooting
	 */
	public double getProbabilityOfShooting() {
		return probability_of_shooting;
	}
	
	/**
	 * Given a chromosone, it mutates it according to mutation_rate
	 * @param chromosone String
	 * @return chromoson String mutated
	 */
	private String mutate (String chromosone) {
		String temp = "";
		Random rand = new Random();
		for (int i=0; i<chromo_length*4; i++) {
			if (rand.nextDouble()>mutation_rate) {
				temp += chromosone.charAt(i);
			} else {
				temp += (chromosone.charAt(i)=='0' ? '1' : '0');
			}
		}
		return temp;
	}
	
	
}
