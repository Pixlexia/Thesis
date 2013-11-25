package entities;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import platformer.Game;
import platformer.Level;

public class Character {
	
	// Movement
	Point pos;
	Vector2f move; // movement velocity
	public boolean facingRight;
	public float speed;
	
	// AABB
	static Rectangle collisionBox;
	
	// Gravity
	float gravity = 0.001f;
	boolean onGround, isJumping;
	
	public Character(float x, float y){
		this.pos = new Point(x * Game.TS, y * Game.TS);
		move = new Vector2f(0, 0);
		
		facingRight = true;
		
		// size of collisionbox
		collisionBox = new Rectangle(pos.getX(), pos.getY(), 20, 40);
	}
	
	public void update(int delta){
		gravity(delta);
		move();
	}
	
	public void render(Graphics g){
		g.fill(getBounds());
	}
	
	public void move(){
		if(pos.getX() + getBounds().getWidth() + move.x < Game.TS * Level.map.getWidth() && pos.getX() + move.x > 0){
			pos.setX(pos.getX() + move.x);
		}
	}
	
	public void gravity(int delta){
		if(isOpenDown(delta)){
			pos.setY(pos.getY() + move.y * delta);
			
			move.y += gravity * delta;
			onGround = false;
		}
		else{
			move.y = 0;
			onGround = true;
			isJumping = false;
		}
	}
	
	public boolean isOpenRight(){
		int newX = (int) (pos.getX() + getBounds().getWidth() + move.x)/Game.TS;
		int Y = (int) ((pos.getY())/Game.TS) + 1;
		
		if(newX < Level.map.getWidth())
			return !Level.solid[newX][Y] && !Level.solid[newX][Y+1];
		else
			return false;
	}
	
	public boolean isOpenLeft(){
		int newX = (int) (pos.getX() + move.x)/Game.TS;
		int Y = (int) ((pos.getY())/Game.TS) + 1;
		
		return !Level.solid[newX][Y] && !Level.solid[newX][Y+1];
	}
	
	public boolean isOpenDown(int delta){
		int newY = (int) ((pos.getY() + getBounds().getHeight() + move.y*delta)/Game.TS);
//		
//		if(newY >= Level.map.getHeight()){
//			return true;
//		}
//		else
			return !Level.solid[(int) pos.getX()/Game.TS][newY] && !Level.solid[(int) pos.getX()/Game.TS + 1][newY];
	}
	
	public Rectangle getBounds(){
		return new Rectangle (pos.getX(), pos.getY(), collisionBox.getWidth(), collisionBox.getHeight());
	}

}
