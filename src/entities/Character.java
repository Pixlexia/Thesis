package entities;

import game.Level;
import game.Play;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

public class Character {
	public Body body;
	float speed;
	Vector2f maxSpeed;
	
	public Character(float x, float y){
		speed = 1000;
		maxSpeed = new Vector2f(200, 3000);
	}
	
	public void update(int delta){
		
	}
	
	public void render(Graphics g){
		Play.drawBody(g, body);
	}
	
	public Rectangle getBounds(){
		return new Rectangle(body.getPosition().getX()+1, body.getPosition().getY(), body.getShape().getBounds().getWidth()-1, body.getShape().getBounds().getHeight());
	}
	
	public int isInComputer(){
		for (Level.Computer c : Level.computers) {
			if (c.collideWith(getBounds())){
				return c.value;
			}
		}

		return -1;
	}
	
	public Point getPos(){
		return new Point(body.getPosition().getX(), body.getPosition().getY());
	}
	
	public float getX(){
		return body.getPosition().getX();
	}
	
	public float getY(){
		return body.getPosition().getY();
	}
}
