package worldlevels;

import game.LevelData;
import game.Operator;
import game.RelOp;
import game.Slot;

import java.util.ArrayList;

public class World2 extends WorldLevel{
	
	public static int maxRactions = 20;
	
	public World2(){
		
	}
	
	public void initEasy(){
		easy = new ArrayList<LevelData>();
		LevelData ld = new LevelData();
		Slot slot, slot2, slot3;
		Operator op;
		RelOp relop;
		int x, y, z;
		
		//1
		// if comp boolop #, store to slotA
		ld = new LevelData();
		relop = randRelOp();
		x = r.nextInt(99); //#
		y = r.nextInt(99); // comp
		slot = randSlot(ld);
		ld.compValues.add(y);
		
		if(evalRelOp(x, y, relop))
			ld.slotAns.put(slot, y);
		ld.t.add("STORE THE COMPUTER VALUE TO " + slot.toString() + " ONLY IF IT IS " + relOpString(relop) + " " + x);
		easy.add(ld);
		
		// 2
		// if comp boolop #, store to slotA else to slotB
		ld = new LevelData();
		relop = randRelOp();
		x = r.nextInt(99); // #
		y = r.nextInt(99); // comp
		slot = randSlot(ld);
		slot2 = randSlot(ld);
		ld.compValues.add(y);
		
		if(evalRelOp(x, y, relop)){
			ld.slotAns.put(slot, y);
		}
		else{
			ld.slotAns.put(slot2, y);
		}
		ld.t.add("IF THE COMPUTER IS " + relOpString(relop) + " " + x + ", STORE IT TO " + slot.toString() + ". OTHERWISE, IT SHOULD GO TO " + slot2.toString());
		easy.add(ld);
		
		// 3
		// if comp boolop #1, store #2 to slotA
		ld = new LevelData();
		relop = randRelOp();
		x = r.nextInt(99); // #1
		y = r.nextInt(99); // #2
		z = r.nextInt(99); // comp
		slot = randSlot(ld);
		ld.compValues.add(z);
		if(evalRelOp(z, x, relop)){
			ld.slotAns.put(slot, y);
		}
		ld.t.add("STORE " + y + " TO " + slot.toString() + " IF THE COMPUTER VALUE IS " + relOpString(relop) + " " + x);
		easy.add(ld);
		
		// 4
		// if comp boolop #, store #1 to slotA, else store #2 to slotB
		ld = new LevelData();
		relop = randRelOp();
		x = r.nextInt(99); // comp
		int n = r.nextInt(99); // #
		y = r.nextInt(99); // #1
		z = r.nextInt(99); // #2
		
		ld.compValues.add(x);
		
		slot = randSlot(ld);
		slot2 = randSlot(ld);
		
		if(evalRelOp(x, n, relop)){
			ld.slotAns.put(slot, y);
		}
		else{
			ld.slotAns.put(slot2, z);
		}
		ld.t.add("I NEED " + y + " ON MY " + slot.toString() + " SLOT IF IT IS " + relOpString(relop) + " " + y + ". OTHERWISE, PLACE A " + z + " TO " + slot2.toString());
		easy.add(ld);
	}
	
	public void initMedium(){
		medium = new ArrayList<LevelData>();
		LevelData ld = new LevelData();
		Slot slot, slot2, slot3;
		Operator op;
		RelOp relop;
		int x, y, z;
		
		// 1
		// store the larger computer value to slotA
		ld = new LevelData();
		x = r.nextInt(99); // comp1
		y = r.nextInt(99); // comp2
		slot = randSlot(ld);
		ld.compValues.add(x);
		ld.compValues.add(y);
		
		if(x > y){
			ld.slotAns.put(slot, x);
		}
		else{
			ld.slotAns.put(slot, y);
		}
		ld.t.add("I NEED THE LARGER COMPUTER VALUE TO MY " + slot.toString() + " SLOT.");
		medium.add(ld);
		
		// 2
		// if half of comp1 boolop#, store the comp value to slotA, else to slot
		ld = new LevelData();
		x = r.nextInt(99); // comp
		y = r.nextInt(99); // #
		relop = randRelOp();
		slot = randSlot(ld);
		slot2 = randSlot(ld);
		ld.compValues.add(x);
		if(evalRelOp(x/2, y, relop)){
			ld.slotAns.put(slot, x);
		}
		ld.t.add("IF HALF OF " + x + " IS " + relOpString(relop) + " " + y + ", STORE THE COMPUTER VALUE TO " + slot.toString() + ". IF NOT, PLACE IT ON " + slot2.toString());
		medium.add(ld);
		
//		// 3
//		// if comp1 is a multiple of #, store it to slotA
//		ld = new LevelData();
//		x = r.nextInt(10); // #
//		y = r.nextInt(99); // comp
//		slot = randSlot(ld);
//		if(y%x == 0){
//			
//		}
		
		// 4
		// if comp1 operation #1 relop #2, store to slotA
		ld = new LevelData();
		x = r.nextInt(99); // comp1
		y = r.nextInt(99); // #1
		z = r.nextInt(99); // #2
		op = randOp();
		relop = randRelOp();
		slot = randSlot(ld);
		ld.compValues.add(x);
		if(evalRelOp(evalOp(x,y,op), y, relop)){
			ld.slotAns.put(slot, evalOp(x,y,op));
		}
		ld.t.add("IF X * Y IS > Z, STORE IT TO SLOT A.");
		medium.add(ld);
		
	}
	
	public void initHard(){
		hard = new ArrayList<LevelData>();
		LevelData ld = new LevelData();
		Slot slot, slot2, slot3;
		Operator op;
		RelOp relop;
		int x, y, z;
		
		ld.maxRactions = 20;
		
		//1
		// store the larger computer value to slotA and the lesser to slot B
		ld = new LevelData();
		slot = randSlot(ld);
		slot2 = randSlot(ld);
		x = r.nextInt(99); // comp1
		y = r.nextInt(99); // comp2
		ld.compValues.add(x);
		ld.compValues.add(y);
		if(x > y){
			ld.slotAns.put(slot, x);
			ld.slotAns.put(slot2, y);
		}
		else{
			ld.slotAns.put(slot, y);
			ld.slotAns.put(slot2, x);
		}
		ld.t.add("STORE THE LARGER COMPUTER VALUE TO " + slot.toString() + " AND THE LESSER ONE TO " + slot2.toString());
		hard.add(ld);
		
		// 2
		// store the smallest computer value among the 3 to slotA
		ld = new LevelData();
		slot = randSlot(ld);
		x = r.nextInt(99); // comp1
		y = r.nextInt(99); // comp2
		z = r.nextInt(99); // comp3
		ld.compValues.add(x);
		ld.compValues.add(y);
		ld.compValues.add(z);
		if(x < y && x < z){
			ld.slotAns.put(slot, x);
		}
		else if(y < x && y < z){
			ld.slotAns.put(slot, y);
		}
		else if(z < x && z < y){
			ld.slotAns.put(slot, z);
		}
		ld.t.add("AMONG THE 3 COMPUTER DATA, STORE THE SMALLEST VALUE TO SLOT " + slot.toString());
		hard.add(ld);
		
		// 3
		// store the largest computer value among the 3 to slot A
		ld = new LevelData();
		slot = randSlot(ld);
		x = r.nextInt(99); // comp1
		y = r.nextInt(99); // comp2
		z = r.nextInt(99); // comp3
		ld.compValues.add(x);
		ld.compValues.add(y);
		ld.compValues.add(z);
		if(x > y && x > z){
			ld.slotAns.put(slot, x);
		}
		else if(y > x && y > z){
			ld.slotAns.put(slot, y);
		}
		else if(z > x && z > y){
			ld.slotAns.put(slot, z);
		}
		ld.t.add("AMONG THE 3 COMPUTER DATA, STORE THE LARGEST VALUE TO SLOT " + slot.toString());
		hard.add(ld);
		
		// 4
		// if avg of 2 comps rel op #, store to slotA
		ld = new LevelData();
		slot = randSlot(ld);
		x = r.nextInt(99); // comp1
		y = r.nextInt(99); // comp2
		z = r.nextInt(99); // #
		ld.compValues.add(x);		
		ld.compValues.add(y);
		relop = randRelOp();
		if(evalRelOp((x+y)/2, z, relop)){
			ld.slotAns.put(slot, (x+y)/2);
		}
		ld.t.add("GET THE AVERAGE OF THE 2 COMPUTERS AND IF IT IS " + relOpString(relop) + " " + z + ", STORE IT TO " + slot.toString() + " SLOT");
		hard.add(ld);
	}
}
