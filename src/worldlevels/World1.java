package worldlevels;

import game.LevelData;
import game.Operator;
import game.Res;
import game.Slot;
import game.User;

import java.util.ArrayList;
import java.util.Random;

// Variable levels
public class World1 {
	public static ArrayList<LevelData> easy, normal, hard;
	
	public static Random r = new Random();
	
	public World1(){
		normal = new ArrayList<LevelData>();
		hard = new ArrayList<LevelData>();
	}
	
	public static LevelData getLevel(){
		LevelData ld = new LevelData();
		
		switch(User.rating){
		case 0: // easy
			initEasy();
			ld.maxCommands = 6;
			ld = easy.get(r.nextInt(easy.size()));
			break;
			
		case 1: // normal
			ld = normal.get(r.nextInt(hard.size()));
			break;
			
		case 2: // hard
			ld = hard.get(r.nextInt(hard.size()));
			break;
		}

		ld.maxRactions = 12;
		return ld;
	}
	
	// EASY
	public static void initEasy(){
		easy = new ArrayList<LevelData>();
		LevelData ld = new LevelData();
		Slot slot, slot2, slot3;
		Operator op;
		int x, y;
		
		// store # to a slot
		slot = randSlot(ld);
		giveSlotAnswer(ld, slot);
		ld.t.add("STORE " + ld.slotAns.get(slot) + " TO " + slot.toString());
		easy.add(ld);
		
		// store 2 # to 2 slots
		ld = new LevelData();
		slot = randSlot(ld);
		giveSlotAnswer(ld, slot);
		slot2 = randSlot(ld);
		giveSlotAnswer(ld, slot2);
		ld.t.add("STORE " + ld.slotAns.get(slot) + " TO " + slot.toString() + " AND " + ld.slotAns.get(slot2) + " TO " + slot2.toString());
		easy.add(ld);
		
		// store a # to 3 slots
		ld = new LevelData();
		x = r.nextInt(99);
		ld.slotAns.put(Slot.RED, x);
		ld.slotAns.put(Slot.BLUE, x);
		ld.slotAns.put(Slot.YELLOW, x);
		ld.t.add("I NEED TO PUT " + x + " TO ALL MY 3 SLOTS.");
		easy.add(ld);
		
		// store # operation # to slot
		ld = new LevelData();
		slot = randSlot(ld);
		slot2 = null;
		x = (r.nextInt(30)+10) * 2 + 2;
		y = r.nextInt(10) * 2 + 2;
		op = randOp();
		ld.slotAns.put(slot, evalOp(x, y, op));
		String s;
		if(op == Operator.SUB){
			s = "SUBTRACT " + x + " FROM " + y + " AND STORE IT TO " + slot;
		}
		else{			
			s = slot + " NEEDS THE " + Res.operators.get(op) + " OF " + x + " AND " + y;
		}
		ld.t.add(s);
		easy.add(ld);
		
		// 4
	}
	
	// NORMAL
	public void initNormal(){
		
	}
	
	// HARD
	public void initHard(){
		
	}
	
	public static Slot randSlot(LevelData ld){
		// check if the resulting slot already contains a value
		// if so, get another slot
		Slot slot = null;
		
		switch(r.nextInt(3)){
		case 0: slot = Slot.RED; break;
		case 1: slot = Slot.YELLOW; break;
		case 2: slot = Slot.BLUE; break;
		}
		
		if(ld.slotAns.containsKey(slot)){
			slot = randSlot(ld);
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
