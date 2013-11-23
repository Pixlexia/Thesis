/*
 * Platformer Skeleton
 * Author: AlfonzM
 * 
 * Features:
 * Movement, collision checking, jump, sidescrolling
 * 
 * Bugs:
 * Collision checking above head on jump is sometimes weird
 */

package platformer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Character {
	// jump
	static boolean onGround, canJump, isJumping;
	static int jumpFrames;
	
	// movement
	static Point pos;
	static boolean facingRight;
	static float speed;
	static float offsetX;
	
	// constant values
	static float maxSpeed = 4,
				 inertia = 0.2f, // less = more slippery
				 acceleration = 0.2f,
				 
				 gravity = 0.001f,
				 jumpingGravity = 0.0005f, // gravity while jumping (spacebar)
				 jumpSpeed = -0.30f; // velocity applied on jump
	
	static int jumpMaxFrames = 50;
	
	static Vector2f move; // movement velocity
	static Rectangle collisionBox;
	
	static int delta;
	
	public Character(float x, float y){
		// init position at center
		x = Game.PWIDTH/2;
		y = Game.GHEIGHT/2;
		
		pos = new Point(x, y);
		move = new Vector2f(0, 0);
		
		// edit size of collisionbox
		collisionBox = new Rectangle(pos.getX(), pos.getY(), 20, 40);
		facingRight = true;
		
		canJump = true;
		isJumping = false;
		jumpFrames = 0;
		
		offsetX = 0;
	}
	
	public static void render(Graphics g){
		g.setColor(Color.black);
		g.drawString(pos.getX() + " " + pos.getY(), 10, 10);

		// display collision box
		float left = Character.pos.getX() - Game.PWIDTH/2;
		float right = Character.pos.getX() + Game.PWIDTH/2;
		if(left <= 0){
			// manual
			g.fillRect(pos.getX(), pos.getY(), collisionBox.getWidth(), collisionBox.getHeight());
		}
		else if(right > Game.TS * Level.map.getWidth()){
			// manual
			g.fillRect(offsetX + pos.getX(), pos.getY(), collisionBox.getWidth(), collisionBox.getHeight());
		}
		else{
			// center
			offsetX = Game.PWIDTH/2 - Character.pos.getX();
			g.fillRect(Game.PWIDTH/2, pos.getY(), collisionBox.getWidth(), collisionBox.getHeight());
		}
	}
	
	public static void update(Input input, int delta2){
		
		delta = delta2;
		
		// movement left right
		if(input.isKeyDown(Input.KEY_D) || input.isKeyDown(Input.KEY_RIGHT)){
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
		else if(input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_LEFT)){
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
		else{ // slow down/idle
			if(speed != 0){
				speed -= inertia;
				if(speed <= 0)
					speed = 0;
			}
			
			if(facingRight)
				move.x = speed;
			else if(!facingRight)
				move.x = -speed;
			else
				move.x = 0;
		}
		// end movement left right
		
		// jump
		
		if(input.isKeyDown(Input.KEY_W) || input.isKeyDown(Input.KEY_SPACE)){
			if(!isJumping && onGround){
				if(isOpenUp()){
					// initialize jump
					move.y = jumpSpeed;
					isJumping = true;
					gravity = jumpingGravity;
					jumpFrames = 0;
				}
			}
		}
		
		if(isJumping || !onGround){
			int newY = (int) ((pos.getY()+move.y*delta)/Game.TS);
			if(Level.solid[(int) pos.getX() / Game.TS][newY] || Level.solid[(int) pos.getX()/Game.TS + 1][newY]){
				// head touches roof, stop jump
				pos.setY((newY + 1) * Game.TS);
				isJumping = false;
				gravity = jumpingGravity;
				move.y = 0;
			}
			else{
				if(input.isKeyDown(Input.KEY_W) || input.isKeyDown(Input.KEY_SPACE)){
					if(jumpFrames < jumpMaxFrames){
						jumpFrames++;
					}
					else{
						isJumping = false;
						gravity = 0.001f;
					}
				}
				else{
					isJumping = false;
					gravity = 0.001f;
				}
			}
		}
		// end jump
		
		gravity();
		move();
	}
	
	public static void gravity(){
		if(isOpenDown()){
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
	
	public static void move(){
		if(pos.getX() + getBounds().getWidth() + move.x < Game.TS * Level.map.getWidth() && pos.getX() + move.x > 0){
			pos.setX(pos.getX() + move.x);
		}
	}
	
	public static boolean isOpenUp(){
		int newY = (int) ((pos.getY() + move.y * delta)/Game.TS);
		
		return !Level.solid[(int) pos.getX()/Game.TS][newY] || !Level.solid[(int) pos.getX()/Game.TS + 1][newY];
	}
	
	public static boolean isOpenDown(){
		int newY = (int) ((pos.getY() + getBounds().getHeight() + move.y*delta)/Game.TS);
//		
//		if(newY >= Level.map.getHeight()){
//			return true;
//		}
//		else
			return !Level.solid[(int) pos.getX()/Game.TS][newY] && !Level.solid[(int) pos.getX()/Game.TS + 1][newY];
	}
	
	public static boolean isOpenRight(){
		int newX = (int) (pos.getX() + Game.TS + move.x)/Game.TS;
		int Y = (int) ((pos.getY())/Game.TS) + 1;
		
		if(newX < Level.map.getWidth())
			return !Level.solid[newX][Y] && !Level.solid[newX][Y+1];
		else
			return false;
	}
	
	public static boolean isOpenLeft(){
		int newX = (int) (pos.getX() + move.x)/Game.TS;
		int Y = (int) ((pos.getY())/Game.TS) + 1;
		
		return !Level.solid[newX][Y] && !Level.solid[newX][Y+1];
	}
	
	public static Rectangle getBounds(){
		return new Rectangle (pos.getX(), pos.getY(), collisionBox.getWidth(), collisionBox.getHeight());
	}
}
