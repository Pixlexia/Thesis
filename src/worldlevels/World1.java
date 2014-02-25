package worldlevels;

import game.Level;
import game.LevelData;
import game.Operator;
import game.Res;
import game.Slot;
import game.User;

import java.util.ArrayList;
import java.util.Random;

// Variable levels
public class World1 extends WorldLevel{
	
	public World1(){
	}
	
	// EASY
	public void initEasy(){
		easy = new ArrayList<LevelData>();
		LevelData ld = new LevelData();
		Slot slot, slot2, slot3;
		Operator op;
		int x, y;
		
		// 1
		// store # to a slot
		slot = randSlot(ld);
		giveSlotAnswer(ld, slot);
		ld.t.add("STORE " + ld.slotAns.get(slot) + " TO " + slot.toString());
		easy.add(ld);
		
		// 2
		// store 2 # to 2 slots
		ld = new LevelData();
		slot = randSlot(ld);
		giveSlotAnswer(ld, slot);
		slot2 = randSlot(ld);
		giveSlotAnswer(ld, slot2);
		ld.t.add("STORE " + ld.slotAns.get(slot) + " TO " + slot.toString() + " AND " + ld.slotAns.get(slot2) + " TO " + slot2.toString());
		easy.add(ld);
		
		// 3
		// store a # to 2 slots
		ld = new LevelData();
		x = r.nextInt(99);
		slot = randSlot(ld);
		ld.slotAns.put(slot, x);
		slot2 = randSlot(ld);
		ld.slotAns.put(slot2, x);
		ld.t.add("MY " + slot.toString() + " AND " + slot2.toString() + " SLOTS BOTH REQUIRE " + x);
		easy.add(ld);
		
		// 4
		// store a # to 3 slots
		ld = new LevelData();
		x = r.nextInt(99);
		ld.slotAns.put(Slot.RED, x);
		ld.slotAns.put(Slot.BLUE, x);
		ld.slotAns.put(Slot.YELLOW, x);
		ld.t.add("I NEED TO PUT " + x + " TO ALL MY 3 SLOTS.");
		easy.add(ld);
		
		// 4
	}
	
	// NORMAL
	public void initMedium(){
		medium = new ArrayList<LevelData>();
		LevelData ld = new LevelData();
		Slot slot, slot2, slot3;
		Operator op;
		int x, y;
		
		// 1
		// store # operation comp1 to slot
		ld = new LevelData();
		slot = randSlot(ld);
		slot2 = null;
		x = (r.nextInt(30)+10) * 2 + 2;
		y = r.nextInt(40) + 50;
		ld.compValues.add(y);
		op = randOp();
		ld.slotAns.put(slot, evalOp(x, y, op));
		String s;
		if(op == Operator.SUB){
			s = "SUBTRACT " + x + " FROM THAT COMPUTER'S VALUE AND STORE IT TO " + slot;
		}
		else{			
			s = slot + " NEEDS THE " + Res.operators.get(op) + " OF " + x + " AND THAT COMPUTER'S VALUE.";
		}
		ld.t.add(s);
		medium.add(ld);
		
		// 2
		// store 3 # to 3 slots
		ld = new LevelData();
		slot = randSlot(ld);
		giveSlotAnswer(ld, slot);
		slot2 = randSlot(ld);
		giveSlotAnswer(ld, slot2);
		slot3 = randSlot(ld);
		giveSlotAnswer(ld, slot3);
		ld.t.add("STORE " + ld.slotAns.get(slot) + " TO " + slot.toString() + ", " + ld.slotAns.get(slot2) + " TO " + slot2.toString() + " AND " + ld.slotAns.get(slot3) + " TO " + slot3.toString());
		medium.add(ld);
		
		// 3
		// store comp1 to slotA, comp2 to slotB
		ld = new LevelData();
		x = r.nextInt(99) / 10 + 10;
		y = r.nextInt(99) / 10 + 10;
		ld.compValues.add(x);
		ld.compValues.add(y);

		slot = randSlot(ld);
		ld.slotAns.put(slot, x);
		
		slot2 = randSlot(ld);
		ld.slotAns.put(slot2, y);
		
		ld.t.add("STORE THE FIRST COMPUTER TO " + slot.toString() + " AND THE OTHER ONE TO " + slot2.toString());
		medium.add(ld);
		
		// 4
		// store comp1 operation comp2 to slotA
		ld = new LevelData();
		x = r.nextInt(99) / 10 + 10;
		y = r.nextInt(99) / 10 + 10;
		ld.compValues.add(x);
		ld.compValues.add(y);
		
		op = randOp();
		slot = randSlot(ld);
		ld.slotAns.put(slot, evalOp(x, y, op));
		if(op == Operator.SUB){
			s = "SUBTRACT THE COMPUTER ON THE LEFT FROM THE VALUE OF THE OTHER COMPUTER AND STORE IT TO " + slot;
		}
		else{			
			s = slot + " NEEDS THE " + Res.operators.get(op) + " OF THE COMPUTER VALUES.";
		}
		ld.t.add(s);
		medium.add(ld);
	}
	
	// HARD
	public void initHard(){
		hard = new ArrayList<LevelData>();
		LevelData ld = new LevelData();
		Slot slot, slot2, slot3;
		Operator op;
		int x, y, z;
		
		// 1
		// store the sum of 3 comps to slot
		ld = new LevelData();
		slot = randSlot(ld);
		x = r.nextInt(99);
		y = r.nextInt(99);
		z = r.nextInt(99);
		
		ld.compValues.add(x);
		ld.compValues.add(y);
		ld.compValues.add(z);
		
		ld.slotAns.put(slot, x+y+z);
		ld.t.add("FOR THIS LEVEL, GET THE SUM OF ALL THE COMPUTERS AND STORE IT TO SLOT " + slot);
		hard.add(ld);
	}
	
	
}
