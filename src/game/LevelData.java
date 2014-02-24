package game;

import java.util.ArrayList;
import java.util.HashMap;

import worldlevels.World1;

// data for the current dynamic level
public class LevelData {
	public HashMap<Slot, Integer> slotAns;
	public int maxRactions, maxCommands;
	public ArrayList<String> t;
	
	public LevelData(){
		t = new ArrayList<String>();
		maxRactions = 24;
		maxCommands = 24;
		
		slotAns = new HashMap<Slot, Integer>();
	}
	
	// get a level from World1, World2 etc depending on the current Play.world
	// specify the difficulty of next level depending on the user rating
	public static LevelData getLevel(){		
		// calculate current user rating using the challenge function
		Level.challengeFunction();
		
		switch(Play.world){
		case 1:	return World1.getLevel();
//		case 2:	return World2.getLevel();
//		case 3:	return World3.getLevel();
//		case 4:	return World4.getLevel();
		}
		
		return null;
	}
}
