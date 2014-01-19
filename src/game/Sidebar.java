package game;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import entities.Button;
import entities.CButton;
import entities.InventoryButton;
import entities.PButton;
import entities.Player;
import entities.Robot;

public class Sidebar {
	public static Rectangle[] programUIButtons;
	public static ArrayList<CButton> cButtons;
	public static ArrayList<PButton> programButtons; // program buttons + actual command list (.type)
	public static InventoryButton[] robotInventory;
//	public static ArrayList<RAction> commands; // actual command list for Robot
	public static Button runProgram;
	public static int padding = 10;
	public static float bSize;
	
	static float translateValue;

	static int maxCommands = 30;
	int maxCommandsPerRow = 6;
	
	public Sidebar() throws SlickException{
		new Sprites();
		new RActionList();
		translateValue = Game.PWIDTH - Player.offsetX;
		
		cButtons = new ArrayList<CButton>();
		programUIButtons = new Rectangle[maxCommands];
		programButtons = new ArrayList<PButton>();
		robotInventory = new InventoryButton[Robot.inventory.length];

		bSize = (Game.GWIDTH - Game.PWIDTH - padding * 2 - (6-1)*padding)/6;
		
		// init command buttons
		int ypadding = 20;
		for(int i = 0 ; i < RActionList.list.length; i++){
			float x = padding*(i % maxCommandsPerRow) + bSize * (i % maxCommandsPerRow);
			float y = (i/maxCommandsPerRow) * padding + (i/maxCommandsPerRow) * bSize;
			System.out.println(RActionList.list[i].toString());
			cButtons.add(new CButton(RActionList.list[i], bSize, bSize));
			cButtons.get(i).setX(padding + x);
			cButtons.get(i).setY(y + ypadding + padding);
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
		
		ypadding = 220;
		// init program UI buttons
		for(int i = 0; i < maxCommands/maxCommandsPerRow; i++){
			for(int j = 0 ; j < maxCommands/ (maxCommands/maxCommandsPerRow); j++){
				int index = j + (i*maxCommandsPerRow);
				programUIButtons[index] = new Rectangle(padding*(j+1) + bSize*j, padding*(i+1) + bSize*i + ypadding, bSize, bSize);
			}
		}
		
		ypadding = 550;
		// inventory items
		for(int i = 0 ; i < robotInventory.length; i++){
			robotInventory[i] = new InventoryButton(padding * (i+1) + bSize * i, ypadding, bSize, bSize);
		}
		
		ypadding -= 70;
		runProgram = new Button(padding, ypadding, 200, 50){
			public void onClick(){
				Robot.executeCommands();
			}
		};

		CButton.buildProgram();
	}
	
	public static void render(Graphics g){
		g.translate(700, 0);
		
		// commands panel
		g.setColor(new Color(65, 173, 232));
		g.fillRect(0, 0, Game.GWIDTH - Game.PWIDTH, Game.GHEIGHT);
		
		g.setColor(Color.white);
		g.drawString("COMMANDS", padding, 5);
		for(int i = 0 ; i < cButtons.size(); i++){
			cButtons.get(i).render(g);
		}
		
		// program panel
		for(int i = 0 ; i < programUIButtons.length; i++){
			g.setColor(new Color(255, 255, 255, 0.5f));
			
			// slots
			g.fill(programUIButtons[i]);
			
			// program commands
			if(i < programButtons.size()){				
				programButtons.get(i).render(g, programUIButtons[i].getX(), programUIButtons[i].getY());
				if(Robot.isRunning && Robot.commandCount == i){
					g.setColor(Color.white);
					g.drawString("V", programUIButtons[i].getX() + 10, programUIButtons[i].getY() - 20);
				}
			}
		}
		
		// robot inventory
		for(int i = 0 ; i < robotInventory.length; i++){
			robotInventory[i].render(g, i);
			g.setColor(Color.black);
			g.drawString("" + Robot.inventory[i], robotInventory[i].pos.getX() + 5, robotInventory[i].pos.getY() + 5);
		}
		
		// run program button
		runProgram.render(g);
		
		g.translate(-700, 0);
		
		g.setColor(Color.red);
//		g.drawString("ASDASJDLKASJ", 200, 200);
	}
	
	public static void update(int delta, Input input){
		float mouseX = input.getMouseX() - translateValue;
		// button listeners
		for(int i = 0 ; i < cButtons.size(); i++){
			cButtons.get(i).update(mouseX, input.getMouseY(), input);
		}
		
		for(int i = 0 ; i < programButtons.size(); i++){
			if(programButtons.get(i) != null)
				programButtons.get(i).update(mouseX, input.getMouseY(), input);
		}
		
		runProgram.update(mouseX, input.getMouseY(), input);
	}
	
	public static void addCommand(RAction r){
		int i = programButtons.size();
		programButtons.add(new PButton(i, r, bSize, bSize, programUIButtons[i].getX(), programUIButtons[i].getY()));
	}
}
