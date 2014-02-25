package worldlevels;

import game.LevelData;
import game.Operator;
import game.Slot;
import game.User;

import java.util.ArrayList;
import java.util.Random;

public class WorldLevel {
	public static ArrayList<LevelData> easy, medium, hard;
	
	public static Random r = new Random();
	
	public LevelData getLevel(){
		LevelData ld = new LevelData();
		
		switch(User.rating){
		case 0: // easy
			initEasy();
			ld.maxCommands = 6;
			ld = easy.get(r.nextInt(easy.size()));
			break;
			
		case 1: // normal
			initMedium();
			ld = medium.get(r.nextInt(medium.size()));
			break;
			
		case 2: // hard
			initHard();
			ld = hard.get(r.nextInt(hard.size()));
			break;
		}

		ld.maxRactions = 12;
		return ld;
	}
	
	public void initEasy(){};
	public void initMedium(){};
	public void initHard(){};
	
	public static Slot randSlot(LevelData ld){
		// check if the resulting slot already contains a value
		// if so, get another slot
		Slot slot = null;
		boolean done = false;
		
		while(!done){
			switch(r.nextInt(3)){
			case 0: slot = Slot.RED; break;
			case 1: slot = Slot.YELLOW; break;
			case 2: slot = Slot.BLUE; break;
			}
			
			if(!ld.slotAns.containsKey(slot)){
				done = true;
			}
		}
		
		
		return slot;
	}
	
	public static Operator randOp(){
		switch(r.nextInt(4)){
		case 0: return Operator.ADD;
		case 1: return Operator.SUB;
		case 2: return Operator.MUL;
		case 3: return Operator.DIV;
		}
		return null;
	}
	
	public static int evalOp(int x, int y, Operator op){
		switch(op){
		case ADD: return x + y;
		case SUB: return x - y;
		case MUL: return x * y;
		case DIV: return x / y;
		}
		
		return 0;
	}
	
	public static void giveSlotAnswer(LevelData ld, Slot slot){
		int i = r.nextInt(99);
		
		ld.slotAns.put(slot, i);
	}

}
