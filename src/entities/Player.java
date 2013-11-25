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

package entities;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import platformer.Game;
import platformer.Level;

public class Player extends Character{
	// jump
	boolean canJump;
	int jumpFrames;
	
	// constant values
	float maxSpeed = 4,
		  inertia = 0.2f, // less = more slippery
		  acceleration = 0.2f,
				 
	// jump
		  jumpingGravity = 0.0005f, // gravity while jumping (spacebar)
		  jumpSpeed = -0.30f; // velocity applied on jump
	
	int jumpMaxFrames = 50;
	
	public static float offsetX;
	
	public Player(float x, float y){
		super(x, y);
		
		// init position at center		
		pos.setX(Game.PWIDTH/2);
		pos.setY(Game.GHEIGHT/2);
		
		canJump = true;
		isJumping = false;
		jumpFrames = 0;
		offsetX = 0;
	}
	
	public void update(Input input, int delta){
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
				if(isOpenUp(delta)){
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
		
		super.update(delta);
	}
	
	@Override
	public void render(Graphics g){
		g.setColor(Color.black);

		// display collision box
		float left = pos.getX() - Game.PWIDTH/2;
		float right = pos.getX() + Game.PWIDTH/2;
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
			offsetX = Game.PWIDTH/2 - pos.getX();
			g.fillRect(Game.PWIDTH/2, pos.getY(), collisionBox.getWidth(), collisionBox.getHeight());
		}
	}
	
	public boolean isOpenUp(int delta){
		int newY = (int) ((pos.getY() + move.y * delta)/Game.TS);
		
		return !Level.solid[(int) pos.getX()/Game.TS][newY] || !Level.solid[(int) pos.getX()/Game.TS + 1][newY];
	}
}
