package entities;

import game.Game;
import game.RAction;
import game.Sidebar;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;


public class Robot extends Character{
	
	static boolean isRunning, isDoingCommand;
	static int commandCount;
	static RAction[] actions;
	
	static float moveDistance;
	
	public Robot(float x, float y) {
		super(x, y);
		
		isRunning = false;
		isDoingCommand = false;
	}

	public static void executeCommands(){
		actions = Sidebar.commands;
		isRunning = true;
		
		commandCount = 0;
		moveDistance = 0;
	}
	
	@Override
	public void moveRight(){
		if(moveDistance < Game.TS){
			move.x = 1;
			moveDistance += move.x;
		}
		else{
			resetMove();
		}
	}
	
	@Override
	public void moveLeft(){
		if(moveDistance < Game.TS){
			move.x = -1;
			moveDistance += Math.abs(move.x);
		}
		else{
			resetMove();
		}
	}
	
	public void resetMove(){
		moveDistance = 0;
		commandCount++;
		move.x = 0;
		speed = 0;		
	}
	
	@Override
	public void render(Graphics g){
		g.setColor(Color.red);		
		super.render(g);
	}
	
	@Override
	public void update(int delta){
		super.update(delta);
		
		if(isRunning && actions[commandCount] != null){			
			switch(actions[commandCount]){
			case moveRight:
				
				moveRight();
				break;
				
			case moveLeft:
				moveLeft();
				break;				
			
			default:
				break;
			}
		}
		else{
			isRunning = false;
		}
	}

}
