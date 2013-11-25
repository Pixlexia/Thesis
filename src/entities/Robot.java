package entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import platformer.RAction;
import platformer.Sidebar;

public class Robot extends Character{

	public Robot(float x, float y) {
		super(x, y);
	}

	public static void executeCommands(){
		RAction[] actions = Sidebar.commands;
		
		for(int i = 0 ; i < actions.length; i++){
			System.out.println(i + " " + actions[i]);
		}
	}
	
	@Override
	public void render(Graphics g){
		g.setColor(Color.red);		
		super.render(g);
	}

}
