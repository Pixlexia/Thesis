package worldlevels;

import game.LevelData;
import game.Operator;
import game.RelOp;
import game.Slot;

import java.util.ArrayList;

public class World3 extends WorldLevel{
	public void initEasy(){
		easy = new ArrayList<LevelData>();
		LevelData ld = new LevelData();
		Slot slot, slot2, slot3;
		Operator op;
		int x, y, z, a, b, c;
		
		// MAKE 2 LEVELS WITH 1 COMPUTER THAT USE LOOP MOVES
		// MAKE 2 LEVELS WITH 3 COMPUTERS IN STRAIGHT LINE
		// MAKE 2 LEVELS WITH 5 COMPUTERS IN STRAIGHT LINE
		
		// 1 - store comp to slot
		ld = new LevelData();
		slot = randSlot(ld);
		x = r.nextInt(99); // comp
		ld.compValues.add(x);
		ld.slotAns.put(slot, x);
		ld.t.add("GET TO THE COMPUTER AND STORE IT'S DATA TO " + slot.toString());
		easy.add(ld);
				
		// 2 - store comp to slot, diff text
		ld = new LevelData();
		slot = randSlot(ld);
		x = r.nextInt(99); // comp
		ld.compValues.add(x);
		ld.slotAns.put(slot, x);
		ld.t.add("FOR THIS LEVEL, MY " + slot.toString() + " SLOT REQUIRES THAT COMPUTER'S DATA.");
		easy.add(ld);
		
		// 4 - product of 3 comps
		ld = new LevelData();
		slot = randSlot(ld);
		x = r.nextInt(99); // comp1
		y = r.nextInt(99); // comp2
		z = r.nextInt(99); // comp3
		ld.compValues.add(x);
		ld.compValues.add(y);
		ld.compValues.add(z);
		ld.slotAns.put(slot, x*y*z);
		ld.t.add("MY " + slot.toString() + " SLOT REQUIRES THE PRODUCT OF ALL THE COMPUTERS");
		easy.add(ld);
		
		// 3 - sum of 5 comps
		ld = new LevelData();
		slot = randSlot(ld);
		x = r.nextInt(99); // comp1
		y = r.nextInt(99); // comp2
		z = r.nextInt(99); // comp3
		a = r.nextInt(99); // comp4
		b = r.nextInt(99); // comp5
		ld.compValues.add(x);
		ld.compValues.add(y);
		ld.compValues.add(z);
		ld.compValues.add(a);
		ld.compValues.add(b);
		ld.slotAns.put(slot, x+y+z+a+b);
		ld.t.add("GET THE SUM OF ALL THE COMPUTERS AND PLACE THE RESULT ON MY " + slot.toString() + " SLOT");
		easy.add(ld);
	}
	
	public void initMedium(){
		medium = new ArrayList<LevelData>();
		LevelData ld = new LevelData();
		Slot slot, slot2, slot3;
		Operator op;
		int x, y, z, a, b;
		
		// 1 - get avg of 3 comps
		ld = new LevelData();
		x = r.nextInt(99);
		y = r.nextInt(99);
		z = r.nextInt(99);
		ld.compValues.add(x);
		ld.compValues.add(y);
		ld.compValues.add(z);
		slot = randSlot(ld);
		ld.slotAns.put(slot, (x+y+z)/3);
		ld.t.add("TAKE THE AVERAGE OF ALL THE COMPUTERS AND PLACE IT ON " + slot.toString());
		medium.add(ld);
		
		// 2 - look for # among 3 comps
		ld = new LevelData();
		a = r.nextInt(99); // #
		x = a; // comp1
		y = a; // comp2
		z = r.nextInt(99); // comp3
		
		ld.compValues.add(x);
		ld.compValues.add(y);
		ld.compValues.add(z);
		slot = randSlot(ld);
		ld.slotAns.put(slot, z);
		ld.t.add("ONE OF THESE COMPUTERS HAS A VALUE NOT EQUAL TO " + a + ". FIND THAT COMPUTER AND STORE IT TO " + slot.toString());
		medium.add(ld);
	}
	
	public void initHard(){
		hard = new ArrayList<LevelData>();
		LevelData ld = new LevelData();
		Slot slot, slot2, slot3;
		Operator op;
		int x, y, z, a, b;
		
		// 1 - add all the 2 digit computer values store to slotA
		ld = new LevelData();
		slot = randSlot(ld);
		x = r.nextInt(99); // comp1
		y = r.nextInt(20); // comp2
		z = r.nextInt(9); // comp3
		ld.compValues.add(x);
		ld.compValues.add(y);
		ld.compValues.add(z);
		int sum = 0;
		if(x < 10)
			sum += x;
		if(y < 10)
			sum += y;
		
		sum+=z;
		ld.slotAns.put(slot, sum);
		ld.t.add("ADD ALL THE ONE-DIGIT COMPUTER VALUES AND STORE THE SUM TO " + slot.toString() + " SLOT");
		hard.add(ld);
		
		// 2 - get avg of 5 comps
		ld = new LevelData();
		x = r.nextInt(99);
		y = r.nextInt(99);
		z = r.nextInt(99);
		a = r.nextInt(99);
		b = r.nextInt(99);
		ld.compValues.add(x);
		ld.compValues.add(y);
		ld.compValues.add(z);
		ld.compValues.add(a);
		ld.compValues.add(b);
		slot = randSlot(ld);
		ld.slotAns.put(slot, (a+b+x+y+z)/3);
		ld.t.add("I NEED THE AVERAGE OF THE COMPUTER VALUES STORED ON MY " + slot.toString() + " SLOT");
		hard.add(ld);
		
		
		// 3 - add all the computer values if relop #, store to slotA
		ld = new LevelData();
		slot = randSlot(ld);
		RelOp relop = randRelOp();
		a = r.nextInt(50); // #
		x = r.nextInt(99); // comp1
		y = r.nextInt(99); // comp2
		z = r.nextInt(99); // comp3
		ld.compValues.add(x);
		ld.compValues.add(y);
		ld.compValues.add(z);
		sum = 0;
		if(evalRelOp(x, a, relop))
			sum += x;
		if(evalRelOp(y, a, relop))
			sum += y;
		if(evalRelOp(z, a, relop))
			sum+=z;
		
		ld.slotAns.put(slot, sum);
		ld.t.add("MY " + slot.toString() + " SLOT REQUIRES THE SUM OF ALL THE COMPUTER VALUES THAT ARE " + relOpString(relop) + " " + a);
		hard.add(ld);
	}
}
