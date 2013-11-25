package platformer;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import entities.Button;
import entities.CButton;
import entities.PButton;
import entities.Player;
import entities.Robot;

public class Sidebar {
	public static ArrayList<CButton> cButtons;
	public static PButton[] programSlots; // empty slots
	public static RAction[] commands; // actual command list for Robot
	public static Button runProgram;
	public static int padding = 10;
	public static float bSize;
	
	static float translateValue;

	int maxCommands = 12;
	int maxCommandsPerRow = 6;
	
	public Sidebar() throws SlickException{
		new Sprites();
		translateValue = Game.PWIDTH - Player.offsetX;
		
		cButtons = new ArrayList<CButton>();
		
		programSlots = new PButton[maxCommands];
		commands = new RAction[maxCommands];

		bSize = (Game.GWIDTH - Game.PWIDTH - padding * 2 - (6-1)*padding)/6;
		
		// init command buttons
		cButtons.add(new CButton(RAction.moveLeft, bSize, bSize));
		cButtons.add(new CButton(RAction.moveRight, bSize, bSize));
		cButtons.add(new CButton(RAction.interact, bSize, bSize));
		
		for(int i = 0 ; i < cButtons.size(); i++){
			float x = padding*(i+1) + bSize * i;
			float y = padding + 20;
			
			cButtons.get(i).setX(x);
			cButtons.get(i).setY(y);
		}
		
		// init program buttons
		for(int i = 0; i < commands.length/maxCommandsPerRow; i++){
			for(int j = 0 ; j < commands.length / (commands.length/maxCommandsPerRow); j++){
				int index = j + (i*maxCommandsPerRow);
				programSlots[index] = new PButton(index, bSize, bSize, padding*(j+1) + bSize*j, padding*(i+1) + bSize*i + 200);
			}
		}
		
		runProgram = new Button(padding, programSlots[programSlots.length-1].pos.getY() + 100, 200, 50){
			public void onClick(){
				Robot.executeCommands();
			}
		};
	}
	
	public static void render(Graphics g){
		g.translate(translateValue - Player.offsetX, 0);
		
		// commands panel
		g.setColor(new Color(65, 173, 232));
		g.fillRect(0, 0, Game.GWIDTH - Game.PWIDTH, Game.GHEIGHT);
		
		g.setColor(Color.white);
		g.drawString("COMMANDS", padding, 5);
		for(int i = 0 ; i < cButtons.size(); i++){
			cButtons.get(i).render(g);
		}
		
		// program panel
		for(int i = 0 ; i < programSlots.length; i++){
			programSlots[i].render(g);
		}
		
		runProgram.render(g);
	}
	
	public static void update(int delta, Input input){
		float mouseX = input.getMouseX() - translateValue;
		// button listeners
		for(int i = 0 ; i < cButtons.size(); i++){
			cButtons.get(i).update(mouseX, input.getMouseY(), input);
		}
		
		for(int i = 0 ; i < programSlots.length; i++){
			if(programSlots[i] != null)
				programSlots[i].update(mouseX, input.getMouseY(), input);
		}
		
		runProgram.update(mouseX, input.getMouseY(), input);
	}
	
	public static int addCommand(RAction r){
		for(int i = 0 ; i < commands.length; i++){
			if(commands[i] == null){
				commands[i] = r;
				programSlots[i].setType(r);
				return 0;
			}
		}
		
		return 0;
	}
}
