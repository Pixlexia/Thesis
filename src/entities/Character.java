package entities;
import game.Game;
import game.Level;

import net.phys2d.raw.Body;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;


public class Character {
	//=============
	// PHYS2D
	
	public Body body;
	
	//=============
	
	// Movement
	Point pos;
	Vector2f move; // movement velocity
	public boolean facingRight;
	public float speed;
	
	// constant values
	float maxSpeed = 4,
		  inertia = 0.2f, // less = more slippery
		  acceleration = 0.2f;
	
	// AABB
	static Rectangle collisionBox;
	
	// Gravity
	static float defaultGravity = 0.00155f;
	float gravity = defaultGravity;
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
//		if(pos.getX() + getBounds().getWidth() + move.x < Game.TS * Level.map.getWidth() && pos.getX() + move.x > 0){
//			pos.setX(pos.getX() + move.x);
//		}
	}
	
	public void moveRight(){
		if(!facingRight) // stop when turn around
			speed /= 4;
		
		move.x = speed;
		
		if(isOpenRight()){
			if(speed <= maxSpeed)
				speed += acceleration;
		}
		else{
			speed = 0;
			move.x = 0;
		}
			
		facingRight = true;
	}
	
	public void moveLeft(){
		if(facingRight){
			speed /= 4;
		}
		
		move.x = -speed;
		
		if(isOpenLeft()){
			if(speed <= maxSpeed)
				speed += acceleration;
		}
		else{
			speed = 0;
			move.x = 0;
		}
			
		facingRight = false;
	}
	
	public void moveUpLadder(){
		pos.setY(pos.getY() - 3);
	}
	
	public void moveDownLadder(){
		pos.setY(pos.getY() + 3);
	}
	
	public void gravity(int delta){
		// apply gravity
		if(isOpenDown(delta) && !isInLadder()){
			pos.setY(pos.getY() + move.y * delta);
			
			move.y += gravity * delta;
			onGround = false;
		}
		// dont apply gravity
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
	
	// return pos.getXY, not tile num
	public static boolean isOpen(float x, float y){
		int newX = (int) x/Game.TS;
		int Y = (int) y/Game.TS + 1;
		
		if(newX < Level.map.getWidth())
			return !Level.solid[newX][Y] && !Level.solid[newX][Y+1];
		else{
			System.out.println(newX);
			return false;	
		}
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
			return !Level.solid[(int) pos.getX()/Game.TS][newY] && !Level.solid[(int) pos.getX()/Game.TS + (int) move.x][newY];
	}
	
	public Rectangle getBounds(){
		return new Rectangle (pos.getX(), pos.getY(), collisionBox.getWidth(), collisionBox.getHeight());
	}
	
	public boolean isInLadder(){;
		for(Point p : Level.ladders){
			if(getBounds().contains(p)){
				return true;
			}
		}
		
		return false;
	}

}
