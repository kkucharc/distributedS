package network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CoordinatorQueues {
	Map<Integer, ArrayList<String>> map;
	
	public CoordinatorQueues(){
		map = new HashMap<Integer, ArrayList<String>>();
	}
	
	public void addToList(int ID, String message){
		
	}
	
	public void createNewList(int ID, String message){
		
	}

	public void addList(int id) {
		map.put(id, new ArrayList<String>());
		System.out.println("mapka "+map.isEmpty());
	}
	
}
