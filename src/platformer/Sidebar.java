package platformer;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import platformer.Button.Command;

public class Sidebar {
	static ArrayList<CButton> cButtons;
	static PButton[] pButtons;
	static Command[] commands;
	static Button runProgram;
	static int padding = 10;
	static float bSize;
	
	static float translateValue;

	int maxCommands = 12;
	int maxCommandsPerRow = 6;
	
	public Sidebar() throws SlickException{
		new Sprites();
		translateValue = Game.PWIDTH - Character.offsetX;
		
		cButtons = new ArrayList<CButton>();
		
		pButtons = new PButton[maxCommands];
		commands = new Command[maxCommands];

		bSize = (Game.GWIDTH - Game.PWIDTH - padding * 2 - (6-1)*padding)/6;
		
		// init command buttons
		cButtons.add(new CButton(Command.mLeft, bSize, bSize));
		cButtons.add(new CButton(Command.mRight, bSize, bSize));
		cButtons.add(new CButton(Command.interact, bSize, bSize));
		
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
				pButtons[index] = new PButton(index, bSize, bSize, padding*(j+1) + bSize*j, padding*(i+1) + bSize*i + 200);
			}
		}
		
		runProgram = new Button(padding, pButtons[pButtons.length-1].pos.getY() + 100, 200, 50); 
	}
	
	public static void render(Graphics g){
		g.translate(translateValue - Character.offsetX, 0);
		
		// commands panel
		g.setColor(new Color(65, 173, 232));
		g.fillRect(0, 0, Game.GWIDTH - Game.PWIDTH, Game.GHEIGHT);
		
		g.setColor(Color.white);
		g.drawString("COMMANDS", padding, 5);
		for(int i = 0 ; i < cButtons.size(); i++){
			cButtons.get(i).render(g);
		}
		
		// program panel
		for(int i = 0 ; i < pButtons.length; i++){
			pButtons[i].render(g);
		}
		
		runProgram.render(g);
	}
	
	public static void update(int delta, Input input){
		float mouseX = input.getMouseX() - translateValue;
		// button listeners
		for(int i = 0 ; i < cButtons.size(); i++){
			cButtons.get(i).update(mouseX, input.getMouseY(), input);
		}
		
		for(int i = 0 ; i < pButtons.length; i++){
			if(pButtons[i] != null)
				pButtons[i].update(mouseX, input.getMouseY(), input);
		}
		
		runProgram.update(mouseX, input.getMouseY(), input);
	}
	
	public static int addCommand(Command c){
		for(int i = 0 ; i < commands.length; i++){
			if(commands[i] == null){
				commands[i] = c;
				pButtons[i].setType(c);
				return 0;
			}
		}
		
		return 0;
	}
}
