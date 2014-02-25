package game;

import java.util.ArrayList;
import java.util.Random;

import net.phys2d.raw.Body;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.Box;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import entities.Function;
import entities.GameText;
import entities.HelpText;
import entities.Robot;

public class Level {
	public static final int TILE_LADDER = 6;
	public static final int TILE_EXIT = 5;
	public static final int TILE_RSTART = 24;
	public static final int TILE_PSTART = 25;
	public static final int TILE_COMPUTER = 3;

	// level map
	public static TiledMap map;
	public static boolean[][] solid;
	public static ArrayList<Point> ladders;
	public static ArrayList<Point> exit;
	public static ArrayList<Computer> computers;
	public static ArrayList<Body> solidBodies;
	public static boolean exitLocked;
	
	public static boolean showGreenguy;
	
	public static Point pStart, rStart;
	
	public static LevelData levelData; // contains questions, slot answers, etc
	
	/*
	 * NOTES:
	 * - Bug: Don't place ONE BLOCK ONLY on the right most tile (at least 2 if stick to the rightmost tile)
	 */
	
	public class Computer{
		public Point pos;
		public int value;
		
		public Computer(int x, int y){
			pos = new Point(x, y);
		}
		
		public boolean collideWith(Rectangle r){
			if(r.contains(new Point((pos.getX()) * Game.TS + Game.TS, (pos.getY()+1) * Game.TS)))
				return true;
			
			return false;
		}
	}
	
	public Level() throws SlickException{
		new HelpText();		
		exitLocked = true;
		
		// actual game levels depending on the world
		if(User.doneTutorial[Play.world]){
			Random r = new Random();
			int randomLvl = r.nextInt(5) + 1;

//			initLevelData();

			System.out.println(Play.world);
			levelData = getLevel();
			
			// sidebar ractions, commands
			Sidebar.maxRactions = levelData.maxRactions;
			Sidebar.maxCommands = levelData.maxCommands;
			
			// helptexts
			for(String s : levelData.t){
				addHelp(s);
			}
						
			System.out.println("# of comps in leveldata: " + levelData.compValues.size());
			
			System.out.println("Level layout: " + levelData.compValues.size() + "_" + randomLvl);
			map = new TiledMap("res/levels/layouts/"+levelData.compValues.size()+"_" + randomLvl + ".tmx"); // comp_lvl.tmx
			
		}
		// tutorial level
		else{		
			map = new TiledMap("res/levels/w" + Play.world + "/" + Play.level + ".tmx");
		}
		
		Game.TS = map.getTileWidth();
		ladders = new ArrayList<Point>();
		computers = new ArrayList<Computer>();
		exit = new ArrayList<Point>();
		solid = new boolean[map.getWidth()][map.getHeight()];
		
		solidBodies = new ArrayList<Body>();
		
		rStart = new Point(-100, -100);
		pStart = new Point(-100, -100);
		
		for(int y = 0 ; y < map.getHeight(); y++ ){
			for(int x = 0, width = 0, start = 0; x < map.getWidth(); x++){
				// Check player and robot initial point
				int tileId = map.getTileId(x, y, 1);
				if(tileId == TILE_PSTART){
					pStart = new Point(x * Game.TS + Game.TS/2, y * Game.TS + Game.TS/2);
				}
				else if(tileId == TILE_RSTART){
					rStart = new Point(x * Game.TS + Game.TS, y * Game.TS + Game.TS);					
				}
				
				tileId = map.getTileId(x, y, 0);
				
				if(tileId == TILE_LADDER){
					ladders.add(new Point((x+1) * Game.TS, (y) * Game.TS + Game.TS/2));
				}
				else if(tileId == TILE_EXIT){
					exit.add(new Point((x) * Game.TS + Game.TS, (y+1) * Game.TS + Game.TS/2));
				}
				else if(tileId == TILE_COMPUTER){
					computers.add(new Computer(x, y));
				}

				if(tileId == 1 && ((x+1) < map.getWidth())){
					if(width == 0){
						start = x;
					}
					
					width += map.getTileWidth();
					solid[x][y] = true;
					
				}
				else{
					if(tileId == 1 && (x+1) >= map.getWidth()){
						width += map.getTileWidth();
						solid[x][y] = true;
						
						if(width == 0){
							start = map.getWidth();
						}
					}
					
					if(width > 0){
						StaticBody body = new StaticBody("tile" + tileId, new Box(width, map.getTileHeight()));
						body.setPosition(start * map.getTileWidth() + (width/2), y * map.getTileHeight() + map.getTileHeight()/2);
						solidBodies.add(body);
						body.setFriction(0.5f);
						Play.physWorld.add(body);

						width = 0;
					}
				}
			}
		}
		
		if(User.doneTutorial[Play.world]){
			initLevelData();
		}
		else{			
			initTutorialLevelData();
		}

	}
	
	public static void challengeFunction(){
		Play.ddaTime = Play.timer/1000;
		int rating;
		rating = Play.ddaCommands + Play.ddaErrors + Play.ddaReread + Play.ddaRetries + Play.ddaTime;
		
		if(rating < 5){
			User.rating = 2;
		}
		else if(rating >= 5 && rating < 10){
			User.rating = 1;
		}
		else if(rating >= 10){
			User.rating = 0;
		}
	}
	
	// get a level from World1, World2 etc depending on the current Play.world
	// specify the difficulty of next level depending on the user rating
	public LevelData getLevel(){		
		// calculate current user rating using the challenge function
		Level.challengeFunction();
		
		switch(Play.world){
		case 1:	return Play.world1.getLevel();
//		case 2:	return World2.getLevel();
//		case 3:	return World3.getLevel();
//		case 4:	return World4.getLevel();
		}
		
		return null;
	}
	
	public static void initLevelData(){
		for(int i = 0 ; i < levelData.compValues.size(); i++){
			computers.get(i).value = levelData.compValues.get(i);
		}
	}
	
	public static void initTutorialLevelData() throws SlickException{
		
		challengeFunction();
		
		// If tutorial done for this world, get a LevelData from the list of dynamic levels:
		if(User.doneTutorial[Play.world] && Play.world != 0){
		}
		// Premade tutorial levels:
		else{
			switch(Play.world){
			// TUTORIAL WORLD
			case 0:
				switch(Play.level){
				case 1:
					exitLocked = false;
					Sidebar.maxRactions = 0;
					Sidebar.maxCommands = 0;
					addHelp("Welcome to our untitled thesis game.");
					addHelp("Use A and D to get to the exit.");
					break;
					
				case 2:
					exitLocked = false;
					addHelp("Press W to jump.");
					break;
					
				case 3:
					exitLocked = false;
					addHelp("Use W and S to go up and down ladders.");
					break;
					
				case 4:
					showGreenguy();
					Sidebar.maxRactions = 2;
					Sidebar.maxCommands = 12;
					addHelp("Hey there!");
					addHelp("The exit is locked. But I can unlock it for you if you could get me to that computer.");
					addHelp("Press the move commands on the sidebar to add them to the program.");
					addHelp("When you're done, press the play button below to run it.");
					
					break;
				
				case 5:
					Sidebar.maxRactions = 3;
					addHelpManual("These computers contain valuable data that\nI need in order to unlock the gates.");
					addHelp("If I am standing on a computer block, I can access it using this command: <computer>");
					addHelpManual("By the way, if you want me to repeat the\ninstructions, just click me.");
					break;
					
				case 6:
					addHelpManual("I couldn't jump or go up ladders, so be\ncareful!");
					addHelp("Also you can delete commands on the\nprogram by clicking on them.");
					Play.tutorialDone = true;
					break;
				} // end world == tutorial
				break;
				
			// VARIABLE WORLD
			case 1:
				showGreenguy();
				switch (Play.level) {
				case 1:
					Sidebar.maxRactions = 24;
					Sidebar.maxCommands = 24;
					addHelp("So far so good! This time, I'm gonna need to store some data to my slots.");
					addHelpManual("I have 3 slots, each of which can hold one\nnumber for later use:<showslots>");
					addHelpManual("To store a number to my red slot for\nexample, I can do this:\n<red equal num>");
					addHelpManual("The # command will open up an input box\nwhere you can enter your desired number.");
					addHelpManual("Go ahead and try placing '50' to the red\nslot using the new commands.");
					
//					addHelp("Now, these computers contain numbers. And I need to store some of them to my slots.");
//					addHelp("To store a computer value to my red slot for example, first I need to walk up to the computer block,");
//					addHelpManual("Then execute the following command:\n[RED] = [COMPUTER]");
//					addHelp("Get to that computer below and store its value to my red slot.");
					break;

				case 2:
					addHelp("For this level, I need any odd number on my yellow slot, and an even number on blue.");
					break;

				case 3:
					Sidebar.maxCommands = 6;
					computers.get(0).value = 14;
					addHelp("These computers contain numbers too, and just like with the # command, I can store their values to slots.");
					addHelp("I'm gonna need that computer's data to my red slot.");
					addHelp("First I need to walk up to the computer block, then do:<red=computer>");
//					addHelp("I could also do really cool things - like computing stuff!");
//					addHelp("Think of computer values as constants, and slots as variables. Now you can do algebraic computations!");
//					addHelp("Store the two computer values on red and yellow, then place their sum on blue using the following command: [BLUE] = [RED] + [YELLOW]");
					break;

				case 4:
					Sidebar.maxCommands = 12;
					computers.get(0).value = 5;
					addHelp("I could also copy slot values to another slot.");
					addHelp("First get that computer on my red slot, then copy it to yellow using this: <yellowequalred>");
					break;
					
				case 5:
					computers.get(0).value = 10;
					computers.get(1).value = 20;
					Sidebar.maxCommands = 18;
					addHelp("I need those two computers on red & yellow in any order, and '99' on my blue slot.");
					break;
				
				case 6:
					Sidebar.maxRactions = 12;
					addHelp("Good job on making this far! Now it's time to compute stuff!");
					addHelp("You can use numbers as operands for arithmetic expressions.");
					addHelp("To store the sum of two numbers to the red slot, you can do:<redsum>");
					addHelp("Try getting the sum of 27 and 43 using the operators, then place it on the red slot.");
					break;
				
				case 7:
					computers.get(0).value = 12;
					computers.get(1).value = 34;
					addHelp("You can also use the slot commands as operands.");
					addHelp("This time, I need the product of the two computers on my blue slot.");
					addHelp("(Hint: Try placing them on two other slots first.)");
					break;
					
				case 8:
					computers.get(0).value = 50;
					addHelp("If I am standing on a computer block, I could use the computer directly as an operand.");
					addHelp("Take me to that computer then do the following: <bluecomputer>");
					addHelp("This means 'take the value of the computer I'm currently on, divide it by five then store it to yellow.'");
					Play.tutorialDone = true;
					break;
					
				case 9:
					// last tutorial?
					addHelp("");
					break;
									
				default:
					// levels
					
					// SIMPLE ASSIGN
					// computer
					addHelp("For this level, I need to store the middle computer's data to yellow.");
					addHelp("Help me get the (?) to my red slot.");
					addHelp("My blue slot needs the data from that computer on the left.");
					addHelp("Cool. Now it looks like I need the top computer on my yellow slot, and the one on the bottom to my red slot.");
					addHelp("If you could put any computer data on red and another on blue that'd be awesome!");
					addHelp("I need two numbers from any computer. The left one on red, and the other on yellow.");
					addHelp("I need those 3 computers from left to right on slots red, yellow and blue respectively.");
					addHelp("The computer on the bottom should go to blue, and the top one to red.");
					
					// numbers
					addHelp("I need '10' on my red slot.");
					
					// OPERATORS
					//easy
					addHelp("Get that computer's value multiplied by 3. My blue slot requires it.");
					addHelp("I need to divide 100 with whatever is on that computer, then store the result to yellow slot.");
					addHelp("I have to get the sum of the two computers and place it on my red slot.");
					
					// normal
					addHelp("My blue slot requires the product of those two computers.");
					addHelp("I need to get the sum of those two computers to red.");
					addHelp("I have to divide the right computer by the left computer, and store the quotient to my yellow slot.");
					addHelp("On my red slot, I need the right computer's value divided by the left computer's value.");
					addHelp("");
					addHelp("On my yellow slot, I need the average of the two computers' values.");
					
					// hard
					addHelp("I need half the sum of the two on my red slot.");
					addHelp("I need 5 times the average of the two computers' values on my blue slot.");
					addHelp("Red slot needs half the product of the three computers.");
					addHelp("My yellow slot requires the sum of the two computers on the left multiplied by the computer on the right");
					addHelp("I need to get the sum of the left-most and right-most computers, divided by the one on the middle.");
					break;
				}
				break;// end variable world
			
			// CONDITIONAL WORLD
			case 2:
				showGreenguy();
				switch(Play.level){
				case 1:
					Sidebar.maxRactions = 20;
					Sidebar.maxCommands = 12;
					computers.get(0).value = 10;
					addHelp("Now for some new commands!");
					addHelp("The 'IF' command allows you to execute certain commands ONLY IF a given test condition is true.");
					addHelpManual("In this example, I will only move 2 steps\nright if a computer value is greater than 5:<ifcomputer5>");
					addHelp("If not, the program skips all the next commands until it hits a 'CLOSE IF', then continues like normal.");
					addHelp("For this level, check if the computer is equal (         ) to 10. If it is, move 2 steps left.<equalto10>");
					break;
					
				case 2:
					computers.get(0).value = 7;
					addHelp("Now, I need to store the computer value to blue if it is not equal ( != ) to 7.");
					addHelp("Remember to put a 'CLOSE IF' at the end!");
					break;
					
				case 3:
					Sidebar.maxCommands = 24;
					computers.get(0).value = 20;
					computers.get(1).value = 20;
					addHelp("For this level, if the two computer values are equal, store one of them to blue.");
					addHelp("Tip: I can use slots in making test conditions too.");
					break;
					
				case 4:
					Sidebar.maxCommands = 12;
					computers.get(0).value = 21;
					addHelp("Remember how the 'IF' statement allows you to do something if a test condition is true and just skips that part if it's false?");
					addHelp("Well this time, with the 'ELSE' command, you can do something else if the test condition is false.");
					addHelp("In this example, IF the value in red is > 5, move right. Otherwise, move left:<elseexample>");
					addHelp("Take note though that for An ELSE statement to work it should only be used after a 'CLOSE IF' command.");
					addHelp("For this level, check if the computer data is equal to 12. If it is, move 2 steps right. If not, move 2 steps left.");
					break;
				
				case 5:
					Play.tutorialDone = true;
					computers.get(0).value = 2;
					Sidebar.maxCommands = 18;
					addHelp("I need to get that computer's data. I need it on yellow if it's a 1-digit number. But if it's a 2-digit number, it should go to red.");				
					addHelp("Use your newly learned if/else statements!");				
					break;
				}
				
				break; // end conditional world
				
			// LOOP WORLD
			case 3:
				showGreenguy();
				switch(Play.level){
				case 1:
					Sidebar.maxRactions = 20;
					Sidebar.maxCommands = 24;
					computers.get(0).value = 10;
					addHelp("For this level, I need to store that computer's value to red.");
					break;

				case 2:
					Sidebar.maxRactions = 22;
					Sidebar.maxCommands = 6;
					computers.get(0).value = 36;
					addHelp("One thing that programming does best is doing repetitive tasks.");
					addHelp("Using 'LOOPS', you can tell the program to execute a set of commands for a specified number of times!");
					addHelp("For this level, I need to get that computer data on my blue slot.");
					addHelp("But instead of using the right move command 14 times, you could do:<loopex>");				
					addHelp("'START LOOP' will let you specify how many times to repeat your command/s. In this case, loop  'move right' 14 times.");
					
					break;

				case 3:
					Sidebar.maxCommands = 12;
					computers.get(0).value = 10;
					addHelp("This level is pretty similar to Level 1, but with loops I can now do it in 12 commands!");
					addHelp("Place the computer data to red slot.");
					break;
					
				case 4:
					Sidebar.maxCommands = 12;
					computers.get(0).value = 10;
					computers.get(1).value = 20;
					computers.get(2).value = 1;
					computers.get(3).value = 70;
					addHelp("In this level, one computer contains a value greater than 50. Help me find it and place it on my yellow slot.");
					addHelp("Tip: Loops + if conditions = win!");
					Play.tutorialDone = true;
					break;
				}
				break; // end loop world
				
			// FUNCTIONS WORLD
			case 4:
				showGreenguy();
				switch(Play.level){
				case 1:
					Sidebar.maxRactions = 23;
					Sidebar.maxCommands = 12;
					Sidebar.fui1 = new Function(12);
					Sidebar.fui2 = new Function(0);
					computers.get(0).value = 50;
					computers.get(1).value = 150;
					computers.get(2).value = 300;
					addHelp("Time to learn about functions! A function is a group of commands that you can execute multiple times at any part of the program.");
					addHelp("To make a function, open the Function 1 panel by clicking the F(x)1 button on the upper-right corner of the screen.");
					addHelp("Then use the F(x)1 command on your program anytime to run that function.");
					addHelp("I need the 3 computer data on all my 3 slots in order. But I have limited program slots!");
					addHelp("So instead of using lots of       commands on the program,<funcmoveright>");
					addHelp("you could make a function with 4 move right commands, then just call that F(X)1 on the program whenever I need to walk right.");
					break;
					
				case 2:
					addHelp("hi");
				}
				break;// end functions world
			}// end switch Play.world
			
		}
	}
	
	// CHECK WIN
	public static boolean checkWin() throws SlickException{
		boolean win = false;
		
		// dynamic level
		if(User.doneTutorial[Play.world]){
			win = true;
			
			if(levelData.slotAns.containsKey(Slot.RED) && Robot.inventory[0] != levelData.slotAns.get(Slot.RED)){
				win = false;
			}
			if(levelData.slotAns.containsKey(Slot.YELLOW) && Robot.inventory[1] != levelData.slotAns.get(Slot.YELLOW)){
				win = false;
			}
			if(levelData.slotAns.containsKey(Slot.BLUE) && Robot.inventory[2] != levelData.slotAns.get(Slot.BLUE)){
				win = false;
			}
		}
		// tutorial level
		else{
			switch(Play.world){
			// Tutorial
			case 0:
				switch(Play.level){
				case 4:
					if(Play.robot.isInComputer() != -1){
						win = true;
						new HelpText();
						HelpText.counter = 0;
						addHelp("Nice! I can only execute the program once per level though.");
						addHelpManual("You may restart the level by pressing the\nrestart button located on the upper left\ncorner.");
					}
					break;
					
				case 5:
					if(Play.robot.isInComputer() != -1){
						win = true;
					}
					break;
					
				case 6:
					if(Play.robot.isInComputer() != -1){
						win = true;
						new HelpText();
						HelpText.counter = 0;
						addHelpManual("Awesome! Now that you've learned the\nbasics, you're ready for some real\nprogramming shit!");
					}
					break;
				}
				
				break;
				
				// Variables
			case 1:
				switch(Play.level){
				case 1:
					if(50 == Robot.inventory[0]){
						win = true;
						new HelpText();
						HelpText.counter = 0;
						addHelp("Great! Now I have '50' stored on my red slot which I can use later.");
					}
					break;
					
				case 2:
					if(Robot.inventory[2] % 2 == 0 && Robot.inventory[1] % 2 == 1){
						win = true;
					}
					break;
					
				case 3:
					if(computers.get(0).value == Robot.inventory[0]){
						win = true;
					}
					break;
					
				case 4:
					if(Robot.inventory[0] == Robot.inventory[1] && Robot.inventory[0] == computers.get(0).value){
						win = true;
					}
					break;	
					
				case 5:
					if((computers.get(0).value == Robot.inventory[0] || computers.get(1).value == Robot.inventory[0]) &&
							(computers.get(0).value == Robot.inventory[1] || computers.get(1).value == Robot.inventory[1]) &&
							Robot.inventory[2] == 99){
						win = true;
					}
					break;
					
				case 6:
					if(Robot.inventory[0] == 70){
						win = true;
						new HelpText();
						HelpText.counter = 0;
						addHelp("Awesome! Take note though, I can only evaluate expressions with up to two operands at a time.");
					}
					break;
					
				case 7:
					if(Robot.inventory[2] == (computers.get(0).value * computers.get(1).value)){
						win = true;
					}
					break;
					
				case 8:
					if(Robot.inventory[1] == (computers.get(0).value / 5)){
						win = true;
						new HelpText();
						HelpText.counter = 0;
						addHelp("Nice! Remember: for arithmetic expressions, I can use slots, numbers, and computers (if I'm standing on one) as operands.");
					}
					break;
				}// end variables
				break;
				
				// Conditional branching
			case 2:
				switch(Play.level){
				case 1:
					if(Robot.boolOpResult && Play.robot.getX() == 340){
						for(int i = 0 ; win != true && i < Sidebar.programButtons.size(); i++){
							if(checkIfConditionExists(i, RAction.interact, RAction.isEqual, RAction.number)){							
								win = true;
								break;
							}
						}
					}
					break;
					
				case 2:
					System.out.println(Robot.boolOpResult);
					if(!Robot.boolOpResult){
						for(int i = 0 ; i < Sidebar.programButtons.size(); i++){
							if(checkIfConditionExists(i, RAction.interact, RAction.isNotEqual, RAction.number)){							
								win = true;
								new HelpText();
								HelpText.counter = 0;
								addHelp("Since the test condition was false, the program skipped the next commands until 'close if'.");
								break;
							}
						}
					}
					break;
					
				case 3:
					if(computers.get(0).value == computers.get(1).value && Robot.inventory[2] == computers.get(0).value){
						for(int i = 0 ; i < Sidebar.programButtons.size(); i++){
							if(checkIfConditionExistsWithSlot(i, RAction.interact, RAction.isEqual) || checkIfConditionExistsBothSlot(i, RAction.isEqual)){							
								win = true;
								break;
							}
						}
					}
					break;
					
				case 4:
					for(int i = 0 ; i < Sidebar.programButtons.size(); i++){
						if(checkIfConditionExists(i, RAction.interact, RAction.isEqual, RAction.number)){
							if((computers.get(0).value == 12 && Play.robot.getX() == 300) || (computers.get(0).value != 12 && Play.robot.getX() == 140))
								win = true;
						}
					}
					break;
					
				case 5:
					if(containsIf() && containsElse()){					
						if((computers.get(0).value > 9 && Robot.inventory[0] == computers.get(0).value) || (computers.get(0).value < 10 && Robot.inventory[1] == computers.get(0).value)){
							win = true;
						}
					}
					break;
				}
				break;
				
				// Loops
			case 3:
				switch(Play.level){
				case 1:
					if(Robot.inventory[0] == computers.get(0).value){
						win = true;
						new HelpText();
						HelpText.counter = 0;
						addHelp("Whew! That was a really long trip! I bet you had fun clicking all those buttons.");					
					}
					break;
					
				case 2:
					if(Robot.inventory[2] == computers.get(0).value){
						win = true;
						win = true;
						new HelpText();
						HelpText.counter = 0;
						addHelp("From 20 commands down to 6, loops are amazing!");
					}
					break;
					
				case 3:
					if(Robot.inventory[0] == computers.get(0).value){
						win = true;
					}
					break;
				case 4:
					if(Robot.inventory[1] > 50)
						win = true;
					break;
				}
				break;
				
			case 4:
				switch(Play.level){
				case 1:
					if(Robot.inventory[0] == computers.get(0).value && Robot.inventory[1] == computers.get(1).value && Robot.inventory[2] == computers.get(2).value){
						win = true;
					}
					break;
				}
				break;
			}
		}// end bigass switch
		
		
		if(win){
			String s = "EXIT UNLOCKED!";
			Play.ddaTime = Play.timer;
			Play.gTexts.add(new GameText(s, new Point(Level.exit.get(0).getX() - 70, Level.exit.get(0).getY() - 60)));
		}
		
		return win;
	}
	
	// can be interchangable
	public static boolean checkIfConditionExists(int i, RAction left, RAction boolOp, RAction right){
		if(Sidebar.programButtons.get(i).type == RAction.startIf &&
				((Sidebar.programButtons.get(i+1).type == left && Sidebar.programButtons.get(i+2).type == boolOp && Sidebar.programButtons.get(i+3).type == right)
						|| (Sidebar.programButtons.get(i+3).type == right && Sidebar.programButtons.get(i+2).type == boolOp && Sidebar.programButtons.get(i+1).type == left))){							
			return true;
		}
		
		return false;
	}
	
	// contains if
	public static boolean containsIf(){
		for(int i = 0;  i < Sidebar.programButtons.size(); i++){
			if(Sidebar.programButtons.get(i).type == RAction.startIf){
				return true;
			}
		}
		
		return false;
	}
	
	// contains else
	public static boolean containsElse(){
		for(int i = 0;  i < Sidebar.programButtons.size(); i++){
			if(Sidebar.programButtons.get(i).type == RAction.startElse){
				return true;
			}
		}
		
		return false;
	}
	
	// both sides are slots
	public static boolean checkIfConditionExistsBothSlot(int i, RAction boolOp){
		RAction t1 = Sidebar.programButtons.get(i+1).type;
		RAction t2 = Sidebar.programButtons.get(i+2).type;
		RAction t3 = Sidebar.programButtons.get(i+3).type;
		
		if(Sidebar.programButtons.get(i).type == RAction.startIf &&	(((t1 == RAction.redSlot || t1 == RAction.blueSlot || t1 == RAction.yellowSlot) && t2 == boolOp && (t3 == RAction.redSlot || t3 == RAction.blueSlot || t3 == RAction.yellowSlot))))							
			return true;
		
		return false;
	}
	
	// used if left/right is slot
	public static boolean checkIfConditionExistsWithSlot(int i, RAction other, RAction boolOp){
		RAction t1 = Sidebar.programButtons.get(i+1).type;
		RAction t2 = Sidebar.programButtons.get(i+2).type;
		RAction t3 = Sidebar.programButtons.get(i+3).type;
		
		if(Sidebar.programButtons.get(i).type == RAction.startIf &&
				((t1 == other && t2 == boolOp && (t3 == RAction.redSlot || t3 == RAction.blueSlot || t3 == RAction.yellowSlot))
						|| ((t1 == RAction.redSlot || t1 == RAction.blueSlot || t1 == RAction.yellowSlot) && t2 == boolOp && t3 == other))){							
			return true;
		}
		
		return false;
	}
	
	// add text help
	public static void addHelp(String s){
		HelpText.addHelp(s, true);
	}
	
	// add manually linebreaked text help
	public static void addHelpManual(String s){
		HelpText.addHelp(s, false);
	}
	
	// start showing green guy on help texts
	public static void showGreenguy(){
		showGreenguy = true;
		HelpText.modX = 85;
		HelpText.maxChar = 33;
	}
}
