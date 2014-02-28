package entities;

import game.Game;
import game.Res;
import game.Sidebar;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;

public class GameTextSlot extends GameText{
	
	Point targetSlot;
	public static boolean moving;
	int speed = 20;
	
	// store computer value to slots
	public GameTextSlot(int value, Point p, int targetSlot){
		super(value+"", p);
		
		moving = true;
		
		target.setY(target.getY()-20);
		
		font = Res.futura24;
		this.targetSlot = new Point(Sidebar.robotInventory[targetSlot].pos.getX() + Game.PWIDTH + 45 - font.getWidth(value+"")/2, Sidebar.robotInventory[targetSlot].pos.getY() + 13);
	}
	
	@Override
	public void render(Graphics g){
		if(targetSlot != null){
			super.render(g);
		}
		else
			font.drawString(pos.getX(), pos.getY(), text, new Color(30, 30, 30));
	}
	
	@Override
	public void update(int delta){
		if(Math.round(pos.getY()) != Math.round(target.getY()) || Math.round(pos.getX()) != Math.round(target.getX())){
			pos.setY(pos.getY() + (target.getY() - pos.getY())/speed);
			pos.setX(pos.getX() + (target.getX() - pos.getX())/speed);
		}
		else{
			if(targetSlot == null){
				isAlive = false;
				moving = false;
			}
			else{
				target = targetSlot;			
				targetSlot = null;
			}
		}
	}

}
