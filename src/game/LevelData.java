package game;

import java.util.ArrayList;
import java.util.HashMap;

import worldlevels.World1;

// data for the current dynamic level
public class LevelData {
	public HashMap<Slot, Integer> slotAns;
	public int maxRactions, maxCommands;
	public ArrayList<String> t;
	public ArrayList<Integer> compValues;
	
	public LevelData(){
		compValues = new ArrayList<Integer>();
		t = new ArrayList<String>();
		maxRactions = 24;
		maxCommands = 24;
		
		slotAns = new HashMap<Slot, Integer>();
	}
}
