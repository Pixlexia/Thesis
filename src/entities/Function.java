package entities;

import game.Res;
import game.Sidebar;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Function {
	public int maxCommands, commandCount;
	public boolean isDisplaying, isHighlight;
	public Rectangle bg;
	
	public Rectangle[] programUIButtons;
	public ArrayList<PButton> programButtons; // program buttons + actual command list (.type)
	
	public Function(int max) throws SlickException{
		maxCommands = max;
		isDisplaying = false;
		bg = new Rectangle(0,0, (Sidebar.maxCommandsPerRow + 1) * Sidebar.padding + Sidebar.maxCommandsPerRow * Sidebar.bSize,  (maxCommands/Sidebar.maxCommandsPerRow + 3) * Sidebar.padding + maxCommands/Sidebar.maxCommandsPerRow * Sidebar.bSize);
		
		programUIButtons = new Rectangle[maxCommands];
		programButtons = new ArrayList<PButton>();
		
		int ypadding = 20;
		// init program UI buttons
		for(int i = 0; i < maxCommands/Sidebar.maxCommandsPerRow; i++){
			for(int j = 0 ; j < maxCommands/ (maxCommands/Sidebar.maxCommandsPerRow); j++){
				int index = j + (i*Sidebar.maxCommandsPerRow);
				programUIButtons[index] = new Rectangle(Sidebar.padding*(j+1) + Sidebar.bSize*j, Sidebar.padding*(i+1) + Sidebar.bSize*i + ypadding, Sidebar.bSize, Sidebar.bSize);
			}
		}
		
		isHighlight = false;
		commandCount = 0;
	}
	
	public void update(int funcNo, int delta, float mouseX, float mouseY, Input input){
		if(isDisplaying && bg.contains(mouseX, mouseY) && !Sidebar.showCalculator){
			isHighlight = true;
			if(input.isMouseButtonDown(0)){
				Sidebar.activeFunction = funcNo;
			}
		}
		else{
			isHighlight = false;
		}
		
		for(int i = 0 ; i < programButtons.size(); i++){
			if(programButtons.get(i) != null)
				programButtons.get(i).update(delta, mouseX, mouseY, input);
		}
	}
	
	public void render(Graphics g, int funcNo){
		int opacity = 0;
		if(isHighlight || funcNo == Sidebar.activeFunction && !Sidebar.showCalculator){
			opacity = 230;
		}
		else{
			opacity = 120;
		}
		
		// background
		g.setColor(new Color(225, 225, 215, opacity));
		g.fill(bg);
		
		// program panel
		for(int i = 0 ; i < programUIButtons.length; i++){
			g.setColor(new Color(255, 255, 255, 0.5f));
			
			// slots
			g.fill(programUIButtons[i]);
			
			// program commands
			if(i < programButtons.size()){
				programButtons.get(i).render(g, programButtons.get(i).pos.getX(), programButtons.get(i).pos.getY(), opacity);
				if(Robot.isRunning && commandCount-1 == i){
					g.setColor(new Color(100, 100, 80));
					g.setLineWidth(4);
					g.drawRect(programUIButtons[i].getX() - 2, programUIButtons[i].getY() - 3, programUIButtons[i].getWidth()+4, programUIButtons[i].getHeight()+4);
					g.setLineWidth(1);
				}
			}
		}
		
		Res.futura16.drawString(Sidebar.padding, Sidebar.padding - 4, "FUNCTION " + funcNo, new Color(50, 50, 50, opacity));
	}
	
	public void toggleDisplay(){
		isDisplaying = !isDisplaying;
		if(!isDisplaying){
			Sidebar.activeFunction = Sidebar.MAIN;
		}
	}
}
