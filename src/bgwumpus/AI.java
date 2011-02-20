package bgwumpus;

import java.util.HashMap;

public class AI extends Entity {

	private HashMap<String,Boolean> percepts = new HashMap<String,Boolean>();

	public AI() {
		type = EntityType.AI;
		percepts.put("pits",false);
		percepts.put("bats",false);
		percepts.put("treasure", false);
		percepts.put("wumpus",false);
		// TODO Auto-generated constructor stub
	}
	
	public void setPercept(String name){
		
		percepts.put(name, true);		
		
	}
	
	public boolean getPercept(String name){
		
		return percepts.get("name");
		
	}
	
	public void unsetPercept(String name){
		
		percepts.put(name, false);
		
	}
	
	public void unsetAllPercepts(){
		
		for(String key : percepts.keySet()){
			
			percepts.put(key,false);
			
		}
		
		
	}

}
