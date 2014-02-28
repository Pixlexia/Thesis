// Robot.java backup
// incase lexical analyzer doesnt work


package entities;

import game.RAction;
import game.RActionList;
import game.Sidebar;

import net.phys2d.raw.Body;
import net.phys2d.raw.shapes.Box;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Robot extends Character{
	
	public static boolean isRunning;
	static boolean isDoingCommand;
	public static int commandCount;
	public static int[] inventory;
	
	public int commandDelay = 500;
	public static int commandDelayCount;
	public float moveSpeed = 1f;
	
	static float moveDistance;
	
	public int YELLOW_SLOT = 1;
	public int RED_SLOT = 0;
	public int BLUE_SLOT = 2;
	
	// assignment and operations commands
	int leftVariableIndex, operand1, operand2;
	char arithmeticOp;
	static boolean isInExpression;
	static boolean operand1Exists, operand2Exists, leftVariableExists;
	
	// conditional branching
	static boolean inIfCond, inIfBody;
	int boolOp1, boolOp2;
	static boolean boolOp1Exists, boolOp2Exists, ifCondResult;
	char boolOp;
	
	public RAction[] commandList;

	public Robot(float x, float y) {
		super(x, y);
		
		isRunning = false;
		isDoingCommand = false;
		inventory = new int[3];
		
		speed = 0;
		commandDelayCount = 0;
		
		// assign/operations
		leftVariableIndex = -1;
		operand1 = 0;
		operand2 = 0;
		operand1Exists = false;
		operand2Exists = false;
		leftVariableExists = false;	
		arithmeticOp = ' ';
		
		// conditional branching
		boolOp1 = 0;
		boolOp2 = 0;
		boolOp1Exists = false;
		boolOp2Exists = false;
		boolOp = ' ';
		
		// end command list
		body = new Body(new Box(40, 40), 20);
		body.setPosition(x, y);
		body.setRotatable(false);
		body.setFriction(2f);
		body.setMaxVelocity(maxSpeed.x, maxSpeed.y);
	}

	public static void executeCommands(){
		isRunning = true;
		
		commandCount = 0;
		moveDistance = 0;
		
		commandDelayCount = 0;
		
		isInExpression = false;
		inIfCond = false;
		inIfBody = false;
		ifCondResult = true;
	}
	
	@Override
	public void render(Graphics g){
		g.setColor(Color.red);
		super.render(g);
		
		g.drawString(body.getPosition().getX() +" ", 100, 80);
		
		int x = isInComputer();
		if(x != -1){
			g.drawString("Computer ID: " + x, 100, 100);
		}
		
		if(isRunning){
			g.drawString("Executing commands.", body.getPosition().getX() - 20, body.getPosition().getY() - 50);
		}
		
//		g.drawString(isInExpression+ "", 100, 200);
		
		g.drawString(leftVariableIndex + " " + operand1 + " " + arithmeticOp + " " + operand2, 100, 150);
		
		g.drawString(commandDelayCount + "", 100, 200);
	}

	@Override
	public void update(int delta){
		super.update(delta);
		
		if(isRunning){			
			if(commandDelayCount < commandDelay){
				commandDelayCount += delta;
			}
			else{
				if(ifCondResult){
					switch(Sidebar.programButtons.get(commandCount).type){
					case moveRight: moveRight(); break;
					case moveLeft: moveLeft(); break;				
					case interact: interact(); break;
					case blueSlot: slot(BLUE_SLOT); break;
					case redSlot: slot(RED_SLOT); break;
					case yellowSlot: slot(YELLOW_SLOT); break;
					case equals: equal(); break;
					case add: add(); break;
					case subtract: subtract(); break;
					case multiply: multiply(); break;
					case divide: divide(); break;
					case startIf: openIf(); break;
					case startElse: openElse(); break;
					case isEqual: assignBoolOp('='); break;
					case isNotEqual: assignBoolOp('!'); break;
					case isGreaterThan: assignBoolOp('>'); break;
					case isLessThan: assignBoolOp('<'); break;
					case endIf: closeIf(); break;
					case endElse: closeElse(); break;
					
					default:
						break;
					}
				}
				else{
					if(RActionList.isEndCond(Sidebar.programButtons.get(commandCount).type))
						ifCondResult = true;
					
					commandDelayCount = 0;
					commandCount++;
				}
			}
		}
		// end program run
		else if(commandCount >= Sidebar.programButtons.size()){
			if(isRunning){				
				isRunning = false;
				System.out.println("check win condition");
			}
		}
	}
	
	// PROGRAM COMMAND FUNCTIONS
	
	private void openIf(){
		inIfCond = true;
		prepareNextMove();
	}
	
	private void closeIf(){
		inIfBody = false;
		prepareNextMove();
	}
	
	private void openElse(){

		prepareNextMove();
	}

	private void closeElse(){
		inIfCond = false;
		prepareNextMove();
	}
	
	private void assignBoolOp(char b){
		boolOp = b;
		System.out.println("Bool op: " + b);
		prepareNextMove();
	}

	// Interact - get the value from the computer
	public void interact(){
		int x;
		if((x = isInComputer()) != -1){
			System.out.println("iteract");
			// x holds computer value
			if(inIfCond){
				if(!boolOp1Exists){		
					System.out.println("Assigning comp to boolop1");		
					boolOp1 = x;
					boolOp1Exists = true;
				}
				else if(boolOp1Exists){
					System.out.println("Assigning comp to boolop2");
					boolOp2 = x;
					boolOp2Exists = true;
				}
			}
			
			if(isInExpression){
				if(!operand1Exists){
					System.out.println("Assign computer to operand1");
					operand1 = x;
					operand1Exists = true;
				}
				else if(operand1Exists){
					System.out.println("Assign computer to operand2");
					operand2 = x;
					operand2Exists = true;
				}
			}
		}
		else{
			System.out.println("No computer detected");
		}
		
		prepareNextMove();
	}
	
	// Assign to a slot, or get value
	public void slot(int i){
		System.out.println("slot " + i);
		if(inIfCond){
			if(!boolOp1Exists){				
				System.out.println("Assigning slot " + i + " to boolop1");
				boolOp1 = Robot.inventory[i];
				boolOp1Exists = true;
			}
			else{
				System.out.println("Assigning slot " + i + " to boolop2");
				boolOp2 = Robot.inventory[i];
				boolOp2Exists = true;
			}
		}
		else{
			if(!isInExpression){ // left value
				leftVariableIndex = i;
				System.out.println("assigning slot " + i + " as left variable");
			}
			else if(!operand1Exists){ // used as identifier, store value to operand1
				System.out.println("assigning slot " + i + " to operand1");
				operand1 = Robot.inventory[i];
				operand1Exists = true;
			}
			else if(operand1Exists){ // used as identifier, store value to operand2
				System.out.println("assigning slot " + i + " to operand1");
				operand2 = Robot.inventory[i];
				operand2Exists = true;
			}
		}
		
		prepareNextMove();
	}
	
	// Move Right	
	public void moveRight(){
		if(moveDistance < 40){
			speed = moveSpeed;
			body.setPosition(body.getPosition().getX() + speed, body.getPosition().getY());
			moveDistance += Math.abs(speed);
		}
		else{
			System.out.println("move right");
			prepareNextMove();
		}
	}
	
	// Move left
	public void moveLeft(){
		if(moveDistance < 40){
			speed = moveSpeed;
			body.setPosition(body.getPosition().getX() - speed, body.getPosition().getY());
			moveDistance += Math.abs(speed);
		}
		else{
			System.out.println("move left");
			prepareNextMove();
		}
	}
	
	private void computeExpression(){
		System.out.println("Computing");
		System.out.println("slot " + leftVariableIndex + " = " + operand1 + " " + arithmeticOp + " " + operand2);
		
		// e.g. x = identifier + identifier
		if(operand2Exists == true){
			switch(arithmeticOp){
			case '+':
				Robot.inventory[leftVariableIndex] = operand1 + operand2;
				break;
				
			case '-':
				Robot.inventory[leftVariableIndex] = operand1 - operand2;
				break;
				
			case '*':
				Robot.inventory[leftVariableIndex] = operand1 * operand2;
				break;
				
			case '/':
				if(operand2Exists == false){
					System.out.println("Cannot divide by zero");
					isRunning = false;
				}
				Robot.inventory[leftVariableIndex] = operand1 / operand2;
				break;
			}
		}
		// e.g. x = identifier
		else{
			Robot.inventory[leftVariableIndex] = operand1;
		}
		
		isInExpression = false;
		operand1 = 0;
		operand2 = 0;
		operand1Exists = false;
		operand2Exists = false;
		arithmeticOp = ' ';
	}
	
	private void checkIfExpressionEnd(){
		// end of program
		if(commandCount+1 >= Sidebar.programButtons.size()){
			computeExpression();
			System.out.println("End of program. Computing expression.");
		}
		else{
			RAction nextCommand = Sidebar.programButtons.get(commandCount+1).type;
			
			if(operand2Exists){
				System.out.println("Operand 2 found. Computing expression.");
				computeExpression();
			}
			else if(operand1Exists == true && arithmeticOp == ' ' && RActionList.isSlot(nextCommand)){
				System.out.println("Expression end, slot command is next. Computing expression");
				computeExpression();
			}
			else if(operand1Exists == true && !RActionList.isArithmeticOperator(nextCommand)){
				if(!RActionList.isIdentifier(nextCommand)){
					System.out.println("Expression end. Computing expression");
					computeExpression();
				}
			}
		}	
	}
	
	private void checkIfCondEnd(){
		System.out.println("Checking if if-condition end");
		if(boolOp2Exists){
			inIfCond = false;
			
			ifCondResult = false;
			
			System.out.println("Evaluating boolean operation");
			System.out.println(boolOp1 + " " + boolOp + " " + boolOp2);
			// evaluate boolean operation
			switch(boolOp){
			case '=':
				if(boolOp1 == boolOp2){
					ifCondResult = true;
				}
				break;
				
			case '!':
				if(boolOp1 != boolOp2){
					ifCondResult = true;
				}
				break;
				
			case '>':
				if(boolOp1 > boolOp2){
					ifCondResult = true;
				}
				break;
				
			case '<':
				if(boolOp1 < boolOp2){
					ifCondResult = true;
				}
				break;
				
			default:
				System.out.println("default checkifcondend()");
				break;
			}
		}
		
		System.out.println("Result: " + ifCondResult);
	}
	
	// Equal operation
	private void equal() {
		isInExpression = true;
		System.out.println("equal");
		prepareNextMove();
	}

	// Add
	private void add() {
		arithmeticOp = '+';
		prepareNextMove();
		System.out.println("plus");
	}
	
	// Subtract
	private void subtract() {
		arithmeticOp = '-';
		prepareNextMove();
	}
	
	// Multiply
	private void multiply() {
		arithmeticOp = '*';
		prepareNextMove();
	}
	
	// Divide
	private void divide() {
		arithmeticOp = '/';
		prepareNextMove();
	}
	
	
	// Current command is done, prepare next move command
	private void prepareNextMove() {
		if(isInExpression){
			checkIfExpressionEnd();
		}
		else if(inIfCond){
			checkIfCondEnd();
		}
		
		System.out.println("" + commandCount + " =======");

		commandCount++;
			
		if(commandCount < Sidebar.programButtons.size()){			
			speed = 0;
			commandDelayCount = 0;
			moveDistance = 0;
		}
		else{
			isRunning = false;
		}
		
		System.out.println("\n" + commandCount + " =======");
	}

	
}
