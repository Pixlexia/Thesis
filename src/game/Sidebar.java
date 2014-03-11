package game;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import entities.Button;
import entities.CButton;
import entities.Calculator;
import entities.Function;
import entities.InventoryButton;
import entities.PButton;
import entities.Player;
import entities.Robot;

public class Sidebar {
	public static Rectangle[] programUIButtons;
	public static ArrayList<CButton> cButtons;
	public static ArrayList<PButton> programButtons; // program buttons + actual command list (.type)
	public static InventoryButton[] robotInventory;
	public static Rectangle programBg;
	public static boolean programBgHovered;
	public static Color programBgColor;
	public static Button runProgram;
	static Image runButton;
	
	public static int padding = 10;
	public static float bSize;
	
	public static float translateValue;

	public static int maxCommands = 30;
	public static int maxRactions;
	public static int maxCommandsPerRow = 6;
	
	// calculator tab. used for # command
	public static boolean showCalculator;
	
	// Function UIs
	public static Function fui1, fui2; // function UIs
	public static int activeFunction;
	public static int F1 = 1, F2 = 2, MAIN = 0;
	
	public Sidebar() throws SlickException{
		new Res();
		new RActionList();
		new Calculator();
		translateValue = Game.PWIDTH - Player.offsetX;
		
		cButtons = new ArrayList<CButton>();
		programUIButtons = new Rectangle[maxCommands];
		programButtons = new ArrayList<PButton>();
		robotInventory = new InventoryButton[Robot.inventory.length];

		bSize = (Game.GWIDTH - Game.PWIDTH - padding * 2 - (maxCommandsPerRow-1)*padding)/maxCommandsPerRow;
		
		// init command buttons
		int ypadding = 20;
		for(int i = 0 ; i < maxRactions; i++){
			float x = padding*(i % maxCommandsPerRow) + bSize * (i % maxCommandsPerRow);
			float y = (i/maxCommandsPerRow) * padding + (i/maxCommandsPerRow) * bSize;
			cButtons.add(new CButton(RActionList.list[i], padding + x, y + ypadding + padding, bSize-3, bSize-3));
			
//			cButtons.get(i).timeBeforeInflate = r.nextInt(1000);
		}
//		
//		for(int i = 0; i < (RActionList.list.length)/maxCommandsPerRow; i++){
//			for(int j = 0 ; j < (RActionList.list.length / (RActionList.list.length/maxCommandsPerRow)) - 1; j++){
//				float x = padding*(j+1) + bSize * j;
//				float y = padding*(i+1) + bSize * i;
//				int index = j + (i*maxCommandsPerRow);
//				cButtons.add(new CButton(RActionList.list[index], bSize, bSize));
//				cButtons.get(index).setX(x);
//				cButtons.get(index).setY(y + ypadding);
//			}
//		}
		
		ypadding = 260;
		// init program UI buttons
		for(int i = 0; i < maxCommands/maxCommandsPerRow; i++){
			for(int j = 0 ; j < maxCommands/ (maxCommands/maxCommandsPerRow); j++){
				int index = j + (i*maxCommandsPerRow);
				programUIButtons[index] = new Rectangle(padding*(j+1) + bSize*j, padding*(i+1) + bSize*i + ypadding, bSize, bSize);
			}
		}
		
		ypadding += 300;
		// inventory items
		for(int i = 0 ; i < robotInventory.length; i++){
			robotInventory[i] = new InventoryButton(padding * (i+1) + 93 * i, ypadding, 93, bSize);
		}
		
		ypadding -= 70;
		runProgram = new Button(padding, ypadding, Game.GWIDTH - Game.PWIDTH - padding*2, 60){
			public void onClick(){
				Res.key3.play();
				if(!Robot.doneRun)
					Robot.executeCommands();
				else if(Robot.isRunning){
					Robot.isRunning = false;
				}
				else if(Robot.doneRun && !Robot.isRunning){
					try { Play.restartLevel(); }
					catch (SlickException e) { e.printStackTrace(); }
				}
				inflate();
			}
		};
		runProgram.borderRadius = 8;
		runProgram.dropShadow = true;
		runProgram.halfShadow = true;
		runProgram.innerGlow = true;
		runProgram.stroke = true;
		
		runButton = new Image("res/playarrow.png");
		
		activeFunction = MAIN;
		programBg = new Rectangle(0, 230, Game.GWIDTH - Game.PWIDTH, 235);
		programBgHovered = false;
	}
	
	public static void render(Graphics g){
		g.translate(Game.PWIDTH, 0);
		int c = 235;
		g.setColor(new Color(c, c, c - 5, 230));
		g.fillRect(0, 0, Game.GWIDTH - Game.PWIDTH, Game.GHEIGHT);

		// commands panel
		
		Color darkGray = new Color(220, 220, 214);
		g.setColor(darkGray);
		g.fillRect(0, 0, Game.GWIDTH-Game.PWIDTH, 26);

		Res.futura16.drawString(padding, 5, "COMMANDS", new Color(90, 90, 90));
		for(int i = 0 ; i < cButtons.size(); i++){
			cButtons.get(i).render(g);
		}
		
		// program panel
		int opacity = 0;
		if(programBgHovered || activeFunction == MAIN || (!fui1.isDisplaying && !fui2.isDisplaying)){
			programBgColor = new Color(0, 0, 0, 0);
			opacity = 255;
		}
		else{
			programBgColor = new Color(0, 0, 0, 0);
			opacity = 80;
		}
		
		g.setColor(programBgColor);		
		g.fill(programBg);

		int y2 = 256;
		int y1 = y2 - 26; 
		g.setColor(darkGray);
		g.fillRect(0, y1, Game.GWIDTH-Game.PWIDTH, 26);
		Res.futura16.drawString(padding, 5 + 230, "PROGRAM", new Color(90, 90, 90));
		
		for(int i = 0 ; i < programUIButtons.length; i++){
			g.setColor(new Color(255 ,255, 255, 100));
			
			// programUI Buttons
			g.fill(programUIButtons[i]);

			//border
			g.setLineWidth(2);

			// outer stroke
			g.setColor(new Color(210, 210, 210, 100));
			g.drawRect(programUIButtons[i].getX(), programUIButtons[i].getY()-1, programUIButtons[i].getWidth(), programUIButtons[i].getHeight());
			
			// inner glow
			g.setColor(Color.white);
//			g.drawRect(programUIButtons[i].getX()+2, programUIButtons[i].getY()+1, programUIButtons[i].getWidth() - 4, programUIButtons[i].getHeight() - 4);
			
			
		}
		
		// program commands
		for(int i = 0 ; i < programButtons.size(); i++){
			programButtons.get(i).render(g, programButtons.get(i).pos.getX(), programButtons.get(i).pos.getY(), opacity);
			if(Robot.isRunning && Robot.commandCount == i){
				g.setColor(new Color(100, 100, 80));
				g.setLineWidth(4);
				g.drawRect(programUIButtons[i].getX() - 2, programUIButtons[i].getY() - 3, programUIButtons[i].getWidth()+4, programUIButtons[i].getHeight()+4);
				g.setLineWidth(1);
			}			
		}
		
		// robot inventory
		for(int i = 0 ; i < robotInventory.length; i++){
			robotInventory[i].render(g, i);
			String s = "" + Robot.inventory[i];
			g.setColor(Color.black);
			Res.futura24.drawString(robotInventory[i].pos.getX() + robotInventory[i].getBounds().getWidth()/2 - Res.futura24.getWidth(s)/2 - 2,
					robotInventory[i].pos.getY() + 13, s, new Color(40, 40, 40));
		}
		
		
		
		// functions
		int ypad = 215;
		if(fui1 != null && fui1.isDisplaying){
			g.translate(-fui1.bg.getWidth(), ypad);
			fui1.render(g, 1);
			g.translate(fui1.bg.getWidth(), -ypad);
		}
		
		if(fui2 != null && fui2.isDisplaying){
			ypad += fui1.bg.getHeight() + padding;
			g.translate(-fui1.bg.getWidth(), ypad);
			fui2.render(g, 2);
			g.translate(fui1.bg.getWidth(), -ypad);
		}

		// calculator
		if(showCalculator){
			Calculator.render(g);
		}
		
		// run program button
		runProgram.render(g);
		if(!Robot.doneRun)
			runButton.draw(padding + (Game.GWIDTH - Game.PWIDTH)/2 - runButton.getWidth()/2,
				runProgram.pos.getY() + runProgram.getBounds().getHeight()/2 - runButton.getHeight()/2);
		else if(Robot.isRunning){
			Res.futura36.drawString((Game.GWIDTH-Game.PWIDTH)/2 - Res.futura36.getWidth("STOP")/2, runProgram.pos.getY() + 10, "STOP", Color.black);
		}
		else if(!Robot.isRunning && Robot.doneRun){
			Play.restart.draw((Game.GWIDTH - Game.PWIDTH)/2 - Play.restart.getWidth()/2,
					runProgram.pos.getY() + runProgram.getBounds().getHeight()/2 - Play.restart.getHeight()/2);
		}
		
		Color gray = new Color(199, 199, 189, 230);
		Color white = new Color(240, 240, 240);
				
		g.setColor(gray);
		g.drawLine(0, 0, 0, Game.GHEIGHT);
		
		g.setColor(white);
		g.drawLine(1, 0, 1, Game.GHEIGHT);
		
		// commands title bar
		horizontalLine(g, white, 1, 26);
		horizontalLine(g, gray, 0, 27);
		
		// programs title bar
		horizontalLine(g, white, 1, y1+1);
		horizontalLine(g, gray, 0, y1);
		horizontalLine(g, white, 1, y2);
		horizontalLine(g, gray, 0, y2+1);
		
		g.translate(-Game.PWIDTH, 0);
		
		g.setColor(new Color(0, 0, 0, 255));
	}
	
	public static void horizontalLine(Graphics g, Color c, float x, float y){
		g.setColor(c);
		g.drawLine(x, y, Game.GWIDTH - Game.PWIDTH, y);
	}
	
	public static void update(int delta, Input input){
		float mouseX = input.getMouseX() - translateValue;
		
		
		// button listeners
		for(int i = 0 ; i < cButtons.size(); i++){
			cButtons.get(i).update(delta, mouseX, input.getMouseY(), input);
		}
		
		for(int i = 0 ; i < programButtons.size(); i++){
			if(programButtons.get(i) != null)
				programButtons.get(i).update(delta, mouseX, input.getMouseY(), input);
		}
		
		if(fui1 != null)
			fui1.update(F1, delta, mouseX + fui1.bg.getWidth(), input.getMouseY() - 215, input);
		
		if(fui2 != null)
			fui2.update(F2, delta, mouseX + fui1.bg.getWidth(), input.getMouseY() - (215 + fui1.bg.getHeight() + padding), input);
		
		if(showCalculator){
			Calculator.update(delta, input);
			runProgram.update(delta, 0, 0, input);
		}
		else{			
			runProgram.update(delta, mouseX, input.getMouseY(), input);
		}

		if(programBg.contains(mouseX, input.getMouseY())){			
			if(input.isMouseButtonDown(0)){
				activeFunction = MAIN;
			}
			
			programBgHovered = true;
		}
		else{
			programBgHovered = false;
		}
	}
	
	public static void addCommand(RAction r){
		if(activeFunction == 1){
			int i = fui1.programButtons.size();
			fui1.programButtons.add(new PButton(i, r, bSize, bSize, fui1.programUIButtons[i].getX(), fui1.programUIButtons[i].getY(), F1));
		}
		else if(activeFunction == 2){
			int i = fui2.programButtons.size();
			fui2.programButtons.add(new PButton(i, r, bSize, bSize, fui2.programUIButtons[i].getX(), fui2.programUIButtons[i].getY(), F2));
		}
		else if(activeFunction == 0){	
			int i = programButtons.size();
			programButtons.add(new PButton(i, r, bSize, bSize, programUIButtons[i].getX(), programUIButtons[i].getY(), MAIN));
		}
	}
}
