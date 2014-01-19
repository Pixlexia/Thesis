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
	static int arithLeftIndex, arithOp1, arithOp2;
	static char arithOperator;
	static boolean arithInExpression;
	static boolean arithOp1Exists, arithOp2Exists, arithOpExists, arithLeftIndexExists;
	
	// conditional branching
	static int boolOp1, boolOp2;
	static char boolOperator;
	static boolean boolInExpression;
	static boolean boolOp1Exists, boolOp2Exists, boolOpExists;
	static boolean inIfBody, inElseBody, inIfElseBody, boolOpResult;
	static boolean skipCommands;
	
	// loop
	static boolean isLooping;
	static int loopStart, loopCount;
	
	public RAction[] commandList;

	public Robot(float x, float y) {
		super(x, y);
		
		isRunning = false;
		isDoingCommand = false;
		inventory = new int[3];
		
		speed = 0;
		commandDelayCount = 0;
		
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
		
		skipCommands = false;
		
		// assign/operations
		resetArithExpression();
		
		// conditional branching
		resetBoolExpression();
	}
	
	@Override
	public void render(Graphics g){
		g.setColor(Color.red);
		super.render(g);
		
		g.drawString("ROBOT", body.getPosition().getX()-22, body.getPosition().getY()-35);
		
		int x = isInComputer();
		if(x != -1){
			g.drawString("Computer ID: " + x, 100, 100);
		}
		
		if(isRunning){
			g.drawString("Executing commands.", body.getPosition().getX() - 20, body.getPosition().getY() - 50);
		}
	}

	@Override
	public void update(int delta){
		super.update(delta);
		
		if(isRunning){			
			if(commandDelayCount < commandDelay){
				commandDelayCount += delta;
			}
			else{
				RAction currentCommand = Sidebar.programButtons.get(commandCount).type;
				
				if(!skipCommands){
					switch(currentCommand){
					case moveRight: moveRight(); break;
					case moveLeft: moveLeft(); break;				
					case interact: interact(); break;
					case blueSlot: slot(BLUE_SLOT); break;
					case redSlot: slot(RED_SLOT); break;
					case yellowSlot: slot(YELLOW_SLOT); break;
					case equals: equal(); break;
					case add: assignArithOp('+'); break;
					case subtract: assignArithOp('-'); break;
					case multiply: assignArithOp('*'); break;
					case divide: assignArithOp('/'); break;
					case modulo: assignArithOp('%'); break;
					case startIf: openIf(); break;
					case startElse: openElse(); break;
					case isEqual: assignBoolOp('='); break;
					case isNotEqual: assignBoolOp('!'); break;
					case isGreaterThan: assignBoolOp('>'); break;
					case isLessThan: assignBoolOp('<'); break;
					case endIf: closeIf(); break;
					case endElse: closeElse(); break;
					case startLoop: startLoop(); break;
					case endLoop: endLoop(); break;
					
					default:
						break;
					}
				}
				else if(RActionList.isEndCond(currentCommand)){
					if(currentCommand == RAction.endIf)
						closeIf();
					else if(currentCommand == RAction.endElse)
						closeElse();
				}
				else{
					prepareNextMove();
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
	
	// RESET FLAGS
	public static void resetArithExpression(){
		arithOpExists = false;
		arithOp1Exists = false;
		arithOp2Exists = false;
		arithLeftIndexExists = false;
		arithInExpression = false;
		
		arithLeftIndex = 0;
		arithOp1 = 0;
		arithOp2 = 0;
		arithOperator = ' ';
	}
	
	public static void resetBoolExpression(){
		boolOpExists = false;
		boolOp1Exists = false;
		boolOp2Exists = false;
		boolInExpression = false;
		
		boolOp1 = 0;
		boolOp2 = 0;
		boolOperator = ' ';
	}
	
	// PROGRAM COMMAND FUNCTIONS
	
	private void openIf(){
		if(boolInExpression){
			programError("You are already in an if condition!");
		}
		else if(arithInExpression){
			programError("You are in the middle of an arithmetic statement!");			
		}
		else{
			boolInExpression = true;
		}
		prepareNextMove();
	}
	
	private void closeIf(){
		if(!inIfBody)
			programError("You need to be in an if body!");
		else{
			if(skipCommands)
				skipCommands = false;
		}
		inIfBody = false;
		
		prepareNextMove();
	}
	
	private void openElse(){
		if(boolInExpression){
			programError("You are still in an if condition!");
		}
		else if(!inIfElseBody){
			programError("You need to be inside an in/else body!");
		}
		else{
			if(inIfBody){
				programError("You are still inside an if body! Close your if body before opening else");
			}
			else{				
				if(boolOpResult){ // else body starts
					inElseBody = true;
					skipCommands = true;
				}
				else
					skipCommands = false;
			}
		}
		
		prepareNextMove();
	}

	private void closeElse(){
		if(!inElseBody)
			programError("You need to be in an else body!");
		else{
			if(skipCommands)
				skipCommands = false;
		}
		prepareNextMove();
	}
	
	private void startLoop(){
		if(arithInExpression){
			programError("You are in the middle of an arithmetic expression!");
		}
		else if(boolInExpression){
			programError("You are in the middle of a boolean expression!");			
		}
		else{
			isLooping = true;
			loopCount = 2;	
			loopStart = commandCount;
			prepareNextMove();			
		}
	}
	
	private void endLoop(){
		if(!isLooping){
			programError("You are not in a loop body! You can't use this.");
		}
		else if(isLooping){
			loopCount--;
			if(loopCount > 0){				
				commandCount = loopStart;
			}
			else if(loopCount <= 0){			
				isLooping = false;
			}			
		}
		
		prepareNextMove();
	}
	
	private void assignBoolOp(char b){
		if(boolInExpression){
			if(boolOpExists){				
				programError("You already have a boolean operator!");
			}
			else{
				if(boolOp1Exists)
					boolOperator = b;
				else
					programError("You need boolean operand 1 first");
			}
		}
		else{			
			programError("You need to be in a boolean expression first (if/else statement)");
		}
		prepareNextMove();
	}
	
	private void assignArithOp(char a){
		if(arithInExpression){
			if(arithOpExists){
				programError("You already have an arithemtic operator!");
			}
			else{
				if(arithOp1Exists){
					arithOperator = a;
					arithOpExists = true;
				}
				else
					programError("You need one arithmetic operand first!");
			}
		}
		else{
			programError("Error, you are not in an arithmetic expression!");
		}
		prepareNextMove();
	}

	// Interact - get the value from the computer
	public void interact(){
		int x;
		if((x = isInComputer()) != -1){
			if(boolInExpression){
				if(boolOp1Exists == false){ // assign x to boolOp1
					boolOp1 = x;
					boolOp1Exists = true;
				}
				else if(boolOp1Exists){
					if(boolOperator == ' '){
						programError("You need a boolean operator first!");
					}
					else{ // assign x to boolOp2
						boolOp2 = x;
						boolOp2Exists = true;
						evaluateBooleanExpression();
					}
				}
			}
			else{
				if(!arithInExpression){
					programError("You can only use this in an expression!");
				}
				else{
					if(arithOp1Exists == false){ // assign x to arithOp1
						arithOp1 = x;
						arithOp1Exists = true;
						arithOp1CheckExpressionEnd();
					}
					else if(arithOp1Exists == true){
						if(arithOpExists == true){ // assign x to arithOp2. arithexp end, compute 
							arithOp2 = x;
							arithOp2Exists = true;
							computeArithmeticExpression();
						}
						else{
							programError("You need an arithmetic operator first!");
						}
					}
				}
			}
		}
		else{
			programError("No computer detected");
		}
		
		prepareNextMove();
	}
	
	// Assign to a slot, or get value
	public void slot(int i){
		int slot = Robot.inventory[i];
		
		if(boolInExpression){
			if(boolOp1Exists == false){ // assign slot top boolOp1
				boolOp1 = slot;
				boolOp1Exists = true;
			}
			else if(boolOp1Exists == true){
				if(boolOperator == ' '){
					programError("You need a boolean operator first!");
				}
				else{
					boolOp2 = slot;
					boolOp2Exists = true;
					evaluateBooleanExpression();
				}
			}
		}
		else{
			if(!arithInExpression){
				arithLeftIndex = i;
				arithLeftIndexExists = true;
			}
			else{
				if(arithOp1Exists == false){ // assign slot to arithop1
					arithOp1 = slot;
					arithOp1Exists = true;
					arithOp1CheckExpressionEnd();
				}
				else if(arithOp1Exists == true){
					if(arithOperator == ' '){
						programError("You need an arithmetic operator first!");
					}
					else{ // assign slot top arithop2
						arithOp2 = slot;
						arithOp2Exists = true;
						computeArithmeticExpression();
					}
				}
			}
		}
		
		prepareNextMove();
	}
	
	// Move Right	
	public void moveRight(){
		if(arithInExpression){
			programError("You are in the middle of an arithmetic expression!");
		}
		else if(boolInExpression){
			programError("You are in the middle of a booleanexpression!");
		}
		
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
		if(arithInExpression){
			programError("You are in the middle of an arithmetic expression!");
		}
		else if(boolInExpression){
			programError("You are in the middle of a booleanexpression!");
		}
		
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
	
	// Equal operation
	private void equal() {
		if(arithInExpression){			
			programError("You are already in an assignment statement!");
		}
		else if(arithLeftIndexExists){				
			if(!arithOp1Exists)
				arithInExpression = true;
		}
		else{
			programError("You can only use equals in an assignment statement!");
		}
		
		prepareNextMove();
	}
	
	public void evaluateBooleanExpression(){
		boolOpResult = false;
		
		switch(boolOperator){
		case '=': boolOpResult = (boolOp1 == boolOp2); break;
		case '!': boolOpResult = (boolOp1 != boolOp2); break;
		case '>': boolOpResult = (boolOp1 > boolOp2); break;
		case '<': boolOpResult = (boolOp1 < boolOp2); break;
		
		default: System.out.println("default - switch at evaluateBooleanExpression()");
		}
		
		if(!boolOpResult){
			inElseBody = false;
			skipCommands = true;
		}
		
		inIfBody = true;
		inIfElseBody = true;
		resetBoolExpression();
	}
	
	public void arithOp1CheckExpressionEnd(){
		if(commandCount+1 >= Sidebar.programButtons.size()){ // end of program, assign
			computeArithmeticExpression();
		}
		else if(!RActionList.isArithmeticOperator(Sidebar.programButtons.get(commandCount+1).type)){
			computeArithmeticExpression();
		}
	}
	
	public void computeArithmeticExpression(){
		if(!arithOp2Exists){
			Robot.inventory[arithLeftIndex] = arithOp1;
		}
		else{
			switch(arithOperator){
				case '+': Robot.inventory[arithLeftIndex] = arithOp1 + arithOp2; break;
				case '-': Robot.inventory[arithLeftIndex] = arithOp1 - arithOp2; break;
				case '*': Robot.inventory[arithLeftIndex] = arithOp1 * arithOp2; break;
				case '/': Robot.inventory[arithLeftIndex] = arithOp1 / arithOp2; break;
				case '%': Robot.inventory[arithLeftIndex] = arithOp1 % arithOp2; break;
				
				default: System.out.println("default - switch at computeArithmeticExpression()");
			}
		}
		
		resetArithExpression();
	}
	
	// Current command is done, prepare next move command
	private void prepareNextMove() {

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
	
	public void programError(String s){
		System.out.println(s);
		isRunning = false;
	}

	
}
