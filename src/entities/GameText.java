package entities;

import game.Res;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;

public class GameText {
	Point pos;
	String text;
	
	Point target;
	
	public boolean isAlive;
	
	AngelCodeFont font;
	
	public void init(String text, Point p){
		this.text = text;
		pos = p;
		
		isAlive = true;
		
		target = new Point(pos.getX(), pos.getY() - 30);
		font = Res.futura16;
	}
	
	// constructor
	public GameText(String text, Point p){
		init(text, p);
	}
	
	public void update(int delta){
		int speed = 20;
		if(Math.round(pos.getY()) != Math.round(target.getY()) || Math.round(pos.getX()) != Math.round(target.getX())){
			pos.setY(pos.getY() + (target.getY() - pos.getY())/speed);
			pos.setX(pos.getX() + (target.getX() - pos.getX())/speed);
		}
		else{
			isAlive = false;	
		}
	}
	
	public void render(Graphics g){
		g.translate(Player.offsetX, Player.offsetY);
		font.drawString( pos.getX(),  pos.getY(), text, new Color(30, 30, 30));
		g.translate(-Player.offsetX, -Player.offsetY);
	}
}
