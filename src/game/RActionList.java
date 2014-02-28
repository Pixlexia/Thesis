package game;

import java.util.ArrayList;

public class RActionList {
	public static RAction[] list = {
			RAction.moveLeft,
			RAction.moveRight,
			RAction.interact,
			RAction.redSlot,
			RAction.yellowSlot,
			RAction.blueSlot,
			RAction.equals,
			RAction.number,
			RAction.add,
			RAction.subtract,
			RAction.multiply,
			RAction.divide,
			RAction.startIf,
			RAction.startElse,
			RAction.isEqual,
			RAction.isNotEqual,
			RAction.isGreaterThan,
			RAction.isLessThan,
			RAction.endIf,
			RAction.endElse,
			RAction.startLoop,
			RAction.endLoop,
			RAction.func1,
			RAction.func2
	};
	
	// Keyword types
	public static ArrayList<RAction> identifiers = new ArrayList<RAction>();
	public static ArrayList<RAction> arithmeticOperators = new ArrayList<RAction>();
	public static ArrayList<RAction> slot = new ArrayList<RAction>();
	public static ArrayList<RAction> startIfElse = new ArrayList<RAction>();
	public static ArrayList<RAction> endIfElse = new ArrayList<RAction>();
	public static ArrayList<RAction> booleanOperator = new ArrayList<RAction>();
	
	public RActionList(){
		identifiers.add(RAction.blueSlot);
		identifiers.add(RAction.redSlot);
		identifiers.add(RAction.yellowSlot);
		identifiers.add(RAction.interact);
		identifiers.add(RAction.number);
				
		arithmeticOperators.add(RAction.add);
		arithmeticOperators.add(RAction.subtract);
		arithmeticOperators.add(RAction.multiply);
		arithmeticOperators.add(RAction.divide);
		
		slot.add(RAction.blueSlot);
		slot.add(RAction.redSlot);
		slot.add(RAction.yellowSlot);
		
		startIfElse.add(RAction.startIf);
		startIfElse.add(RAction.startElse);
		
		endIfElse.add(RAction.endIf);
		endIfElse.add(RAction.endElse);
		
		booleanOperator.add(RAction.isEqual);
		booleanOperator.add(RAction.isGreaterThan);
		booleanOperator.add(RAction.isLessThan);
		booleanOperator.add(RAction.isNotEqual);
	}
	
	public static boolean isEndCond(RAction r){
		if(endIfElse.contains(r))
			return true;
		
		return false;
	}
	
	public static boolean isStartCond(RAction r){
		if(startIfElse.contains(r))
			return true;
		
		return false;
	}
	
	public static boolean isBooleanOperator(RAction r){
		if(booleanOperator.contains(r))
			return true;
		
		return false;
	}

	public static boolean isIdentifier(RAction r){
		if(identifiers.contains(r))
			return true;
		
		return false;
	}
	
	public static boolean isArithmeticOperator(RAction r){
		if(arithmeticOperators.contains(r))
			return true;
		
		return false;
	}
	
	public static boolean isSlot(RAction r){
		if(slot.contains(r))
			return true;
		
		return false;
	}
}
