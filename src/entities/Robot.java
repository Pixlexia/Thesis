package entities;

import game.Level;
import game.LevelData;
import game.Play;
import game.RAction;
import game.RActionList;
import game.Res;
import game.Sidebar;
import game.User;

import java.util.HashMap;
import java.util.Stack;

import net.phys2d.raw.Body;
import net.phys2d.raw.shapes.Box;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class Robot extends Character{
	// click for instructions
	public static boolean hovered;
	public static float hoverTextY;
	
	public static boolean isRunning;
	static boolean isDoingCommand;
	public static boolean doneRun;
	public static int commandCount;
	public static int[] inventory;
	
	public RAction nextFuncCommand;
	public int commandDelay = 500;
	public static int commandDelayCount;
	public float moveSpeed = 1f;
	
	static float moveDistance;
	
	public static int YELLOW_SLOT = 1;
	public static int RED_SLOT = 0;
	public static int BLUE_SLOT = 2;
	
	// animate numbers
	static boolean isMovingNum;
	
	// assignment and operations commands
	static int arithLeftIndex, arithOp1, arithOp2;
	static char arithOperator;
	static boolean arithInExpression;
	static boolean arithOp1Exists, arithOp2Exists, arithOpExists, arithLeftIndexExists;
	static HashMap<Integer, Integer> numbers; // used for the # command. Map<program slot index, number value>
	
	// conditional branching
	public static int boolOp1, boolOp2;
	public static char boolOperator;
	static boolean boolInExpression;
	static boolean boolOp1Exists, boolOp2Exists, boolOpExists;
	public static boolean inIfBody, inElseBody, inIfElseBody, boolOpResult;
	static boolean skipCommands;
	
	// loop
	public static HashMap<Integer, Integer> iterateCounts; // used for UI in PButton. Map<program index of startloop, num of iterations left>
	public static Stack<Loop> runningLoops;
	public static HashMap<Integer, Loop> loops; // used for loops. Map<program slot index, num of iterations>

	// functions
	public static Stack<Function> functions;
	
	// animations
	Image sprite;
	
	public Robot(float x, float y) throws SlickException {
		super(x, y);
		
		isRunning = false;
		isDoingCommand = false;
		inventory = new int[3];
		
		speed = 0;
		commandDelayCount = 0;
		
		isMovingNum = false;
		numbers = new HashMap<Integer, Integer>();
		iterateCounts = new HashMap<Integer, Integer>();
		loops = new HashMap<Integer, Loop>();
		runningLoops = new Stack<Loop>();
		
		// end command list
		body = new Body(new Box(40, 40), 20);
		body.setPosition(x-20, y);
		body.setRotatable(false);
		body.setFriction(2f);
		body.setMaxVelocity(maxSpeed.x, maxSpeed.y);
		
		sprite = new Image("res/greenguy.png");
		
		doneRun = false;
		
		functions = new Stack<Function>();
	}

	public static void executeCommands(){
		if(!doneRun){
			doneRun = true;
			
			isRunning = true;
			
			commandCount = 0;
			moveDistance = 0;
			
			commandDelayCount = 0;
			
			skipCommands = false;
			
			// assign/operations
			resetArithExpression();
			
			// conditional branching
			resetBoolExpression();
			
			// loops
			resetFunctions();
			
			Play.gTexts.add(new GameText("EXECUTING COMMANDS.", new Point(Play.robot.getX() - Res.futura16.getWidth("EXECUTING COMMANDS.")/2, Play.robot.getY() - 40)));
		}
	}
	
	@Override
	public void render(Graphics g){
//		g.setColor(new Color(255, 0, 0, 255));
//		super.render(g);
		
		g.drawImage(sprite, getX() - sprite.getWidth()/2, getY() - sprite.getHeight()/2);
		
//		int x = isInComputer();
//		if(x != -1){
//			g.drawString("Computer ID: " + x, 100, 100);
//		}
		
		if(isRunning){
//			g.drawString("Executing commands.", body.getPosition().getX() - 20, body.getPosition().getY() - 50);
		}
		
		if(hovered){
			if(hoverTextY > hoverTextY - 40){
				hoverTextY += (getY() - 40 - hoverTextY)/5;
			}
			Res.futura10.drawString((int) getX() - Res.futura10.getWidth("NEED HELP?")/2, (int) hoverTextY, "NEED HELP?", Color.black);
		}
		else{
			hoverTextY = getY();
		}
	}

	public void update(Input input, int delta) throws SlickException{
		super.update(delta);
		
		if(!Sidebar.showCalculator && getBounds().contains(input.getMouseX() - Player.offsetX + getBounds().getWidth()/2, input.getMouseY() + getBounds().getHeight()/2)){
			hovered = true;
			if(input.isMousePressed(0)){
				Play.ddaReread++;
				// repeat instructions
				new HelpText();
				
				if(!User.doneTutorial[Play.world]){
					Level.initLevelData();
				}
				else{
					LevelData levelData = Level.levelData;
					for(String s : levelData.t){
						Level.addHelp(s);
					}
				}
				HelpText.counter = 0;
			}
		}
		else{
			hovered = false;
		}
		
		// end program run
		if(commandCount >= Sidebar.programButtons.size()){
			if(isRunning){		
				isRunning = false;
				System.out.println("Checking if answer is correct...");
			}
		}
		else if(isRunning){			
			if(commandDelayCount < commandDelay){
				commandDelayCount += delta;
			}
			else{
				RAction currentCommand = null;
				
				if(functions.isEmpty()){					
					currentCommand = Sidebar.programButtons.get(commandCount).type;					
				}
				else{
					currentCommand = nextFuncCommand;
				}
				
				if(!skipCommands){
					switch(currentCommand){
					case moveRight: moveRight(); break;
					case moveLeft: moveLeft(); break;				
					case interact: computer(); break;
					case blueSlot: slot(BLUE_SLOT); break;
					case redSlot: slot(RED_SLOT); break;
					case yellowSlot: slot(YELLOW_SLOT); break;
					case equals: equal(); break;
					case add: assignArithOp('+'); break;
					case subtract: assignArithOp('-'); break;
					case multiply: assignArithOp('*'); break;
					case divide: assignArithOp('/'); break;
					case number: number(); break;
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
					case func1: func1(); break;
					case func2: func2(); break;
					
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
	
	public static void resetFunctions(){
		if(Sidebar.fui1 != null)
			Sidebar.fui1.commandCount = 0;
		
		if(Sidebar.fui2 != null)
			Sidebar.fui2.commandCount = 0;
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
				programError("You are still inside an if body! Close your IF body before opening an ELSE.");
			}
			else{				
				System.out.println(inElseBody);
				if(boolOpResult){ // else body starts
					skipCommands = true;
				}
				else{					
					inElseBody = true;
					skipCommands = false;
				}
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
			runningLoops.push(new Loop(loops.get(commandCount).iterations, loops.get(commandCount).startIndex));
			runningLoops.peek().makeIteration();
			prepareNextMove();
		}
	}
	
	private void endLoop(){
		if(!runningLoops.isEmpty()){
			if(runningLoops.peek().isLooping){
				runningLoops.peek().makeIteration();

				if(!runningLoops.peek().isLooping){
					runningLoops.pop();
				}
				
//			if(loops.peek().isLooping){				
//				System.out.println("doing iteration");
//				loops.peek().makeIteration();				
//			}
//			else{
//				System.out.println("pop");
//				loops.pop();
//			}
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
	
	// Computer
	public void computer(){
		int x;
		if((x = isInComputer()) != -1){
			constant(x);
		}
		else
			programError("No computer detected");		
	}
	
	// Number
	public void number(){
		int x;
		x = numbers.get(commandCount);
		constant(x);
	}

	// Used by computer or number
	public void constant(int x){
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
			if(commandCount > 0){				
				if(RActionList.isIdentifier(Sidebar.programButtons.get(commandCount-1).type)){
					programError("You can't assign values to a computer or a constant!");				
				}
			}
			else{				
				programError("You can only use equals in an assignment statement!");
			}
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
		
		for(int i = commandCount+1 ; i < Sidebar.programButtons.size(); i++){
			PButton pb = Sidebar.programButtons.get(i);
			if(pb.type == RAction.endIf){
				if(Sidebar.programButtons.get(commandCount+1).type == RAction.startElse){
					for(int i2 = commandCount+1; i2 < Sidebar.programButtons.size(); i2++){
						PButton pb2 = Sidebar.programButtons.get(i2);
						if(pb2.type == RAction.endElse){
							break;
						}
						else{
							if(!boolOpResult){
								pb.colorGreen = true;
							}
							else{
								pb.colorRed = true;
							}
						}
					}
				}
				break;
			}
			else{
				if(!boolOpResult)
					pb.colorRed = true;
				else
					pb.colorGreen = true;
			}
		}
		
		Play.gTexts.add(new GameText(String.valueOf(boolOpResult).toUpperCase() + "", new Point(getX() - Res.futura24.getWidth(boolOpResult+"")/2, getY() - 20)));
		
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
			Play.gTexts.add(new GameTextSlot(arithOp1, new Point(getX(), getY() - 20), arithLeftIndex));
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
			Play.gTexts.add(new GameTextSlot(Robot.inventory[arithLeftIndex], new Point(getX(), getY() - 20), arithLeftIndex));
		}
		
		resetArithExpression();
	}
	
	public void func1(){
		Sidebar.fui1.commandCount = 0;
		
		// infinite loop recursion
		if(!functions.isEmpty() && functions.peek().equals(Sidebar.fui1))
			functions.peek().commandCount = 0;
		else if(!functions.isEmpty() && functions.peek().equals(Sidebar.fui2))
			Sidebar.fui2.commandCount = 0;
		
		functions.push(Sidebar.fui1);
		prepareNextMove();
	}
	
	public void func2(){
		// infinite loop recursion
		if(!functions.isEmpty() && functions.peek().equals(Sidebar.fui2))
			functions.peek().commandCount = 0;
		else if(!functions.isEmpty() && functions.peek().equals(Sidebar.fui1))
			Sidebar.fui1.commandCount = 0;
		
		functions.push(Sidebar.fui2);
		prepareNextMove();
	}
	
	// Current command is done, prepare next move command
	private void prepareNextMove() {
		System.out.println("" + commandCount + " =======");
		
		if(functions.isEmpty()){
			commandCount++;			
			
			if(commandCount < Sidebar.programButtons.size()){			
				speed = 0;
				commandDelayCount = 0;
				moveDistance = 0;
			}
			else{
				// end of program
				try {
					Level.exitLocked = !Level.checkWin();
				} catch (SlickException e) {
					e.printStackTrace();
				}
				isRunning = false;
			}		
			
		}
		else{
			Function f = functions.peek();
			System.out.println(f.toString() + " " + f.commandCount + " " + f.programButtons.size());
			if(f.commandCount < f.programButtons.size()){
				nextFuncCommand = f.programButtons.get(f.commandCount).type;
				functions.peek().commandCount++;
				speed = 0;
				commandDelayCount = 0;
				moveDistance = 0;
			}
			else{
				System.out.println("pop");
				speed = 0;
				commandDelayCount = 0;
				moveDistance = 0;
				commandCount++;
				functions.peek().commandCount++;
				
				System.out.println("size " + functions.size());
				functions.pop();
			}
		}
		
		
		System.out.println("\n" + commandCount + " =======");
	}
	
	public void programError(String s){
		Play.ddaErrors++;
		System.out.println(s);
		Play.gTexts.add(new GameText(s, new Point(Play.robot.getX(), Play.robot.getY())));
		isRunning = false;
	}

	
}
