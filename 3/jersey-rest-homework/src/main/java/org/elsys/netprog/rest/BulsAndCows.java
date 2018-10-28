package org.elsys.netprog.rest;

import org.json.simple.JSONObject;

public class BulsAndCows {
	private String Id;
	private String RealNumber;
	private boolean success = false;
	private int Cows = 0;
	private int Bulls = 0;
	private int turn  = 0;
	
	public BulsAndCows(String id,String realnumber){
		Id = id;
		RealNumber = realnumber;
	}
	
	public String getId(){
		return Id;
	}
	
	public void checkGame(String guess){
		turn++;
		if(guess.equals(RealNumber)) success = true;
		Bulls = 0;
		Cows = 0;
		for(int z = 0;z < 4;z++){
			if(RealNumber.toCharArray()[z] == guess.toCharArray()[z]) Bulls++;
		}
		for(int i = 0; i < 4; i++) {
			for(int z = 0; z < 4;z++){
				if(RealNumber.toCharArray()[i] == guess.toCharArray()[z]) Cows++;	
			}		
		}
		if(Bulls == 4) {
			Cows = 0;
			success = true;
		}
	}
	
	public JSONObject returnjobj() {
		JSONObject obj = new JSONObject();
		obj.put("gameId",Id);
		obj.put("cowsNumber", Cows);
		obj.put("bullsNumber", Bulls);
		obj.put("turnsCount",turn);
		obj.put("success", success );
		return obj;
	}

}
