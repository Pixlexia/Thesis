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
import game.Game;
import game.Level;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.shapes.Box;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;


public class Player extends Character{
	//=============
	// PHYS2D
	
	//=============
	
	// jump
	boolean canJump;
	int jumpFrames;
				 
	// jump
	float jumpingGravity = Character.defaultGravity / 2; // gravity while jumping (spacebar)
	float jumpSpeed = -0.45f; // velocity applied on jump
	
	int jumpMaxFrames = 50; // max frames while holding spacebar
	
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
		
		// PHYS2D
		body = new Body("player", new Box(20, 40), 1);
		body.setPosition(pos.getX(), pos.getY());
		body.setRotatable(false);
		body.setFriction(2f);
		body.setMaxVelocity(400, 500);
	}
	
	public void update(Input input, int delta){
		// controls move right
		if(input.isKeyDown(Input.KEY_D) || input.isKeyDown(Input.KEY_RIGHT)){
			moveRight();
//			body.addForce(new Vector2f(1000, 0));
			body.setForce(1500, 0);
		}
		// controls move left
		else if(input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_LEFT)){
			moveLeft();
//			body.addForce(new Vector2f(-1000, 0));
			body.setForce(-1500, 0);
		}
//		else{ // slow down/idle
//			if(speed != 0){
//				speed -= inertia;
//				if(speed <= 0)
//					speed = 0;
//			}
//			
//			if(facingRight)
//				move.x = speed;
//			else if(!facingRight)
//				move.x = -speed;
//			else
//				move.x = 0;
//		}
		// end movement left right
		
		// jump
		
		if(input.isKeyDown(Input.KEY_W) || input.isKeyDown(Input.KEY_SPACE) || input.isKeyDown(Input.KEY_UP)){
			body.addForce(new Vector2f(0, -5000));
			
//			if(!isInLadder() && !isJumping && onGround){
//				if(isOpenUp(delta)){
//					// initialize jump
//					move.y = jumpSpeed;
//					isJumping = true;
//					gravity = jumpingGravity;
//					jumpFrames = 0;
//				}
//			}
		}
		
		if(isJumping || !onGround){
			int newY = (int) ((pos.getY()+move.y*delta)/Game.TS);
			if(Level.solid[(int) pos.getX() / Game.TS][newY] || Level.solid[(int) pos.getX()/Game.TS + 1][newY] || pos.getY() <= 0){
				// head touches roof, stop jump
				pos.setY((newY + 1) * Game.TS);
				isJumping = false;
				gravity = jumpingGravity;
				move.y = 0;
			}
			else{
				if(input.isKeyDown(Input.KEY_W) || input.isKeyDown(Input.KEY_SPACE) || input.isKeyDown(Input.KEY_UP)){
					if(jumpFrames < jumpMaxFrames){
						jumpFrames++;
					}
					else{
						isJumping = false;
						gravity = Character.defaultGravity;
					}
				}
				else{
					isJumping = false;
					gravity = Character.defaultGravity;
				}
			}
		}
		// end jump
		
		// ladder controls
		if(isInLadder()){
			if(input.isKeyDown(Input.KEY_W) || input.isKeyDown(Input.KEY_UP)){
				moveUpLadder();
			}
			else if(input.isKeyDown(Input.KEY_S) || input.isKeyDown(Input.KEY_DOWN)){
				moveDownLadder();
			}
		}
		
		super.update(delta);
	}
	
	@Override
	public void render(Graphics g){
		g.setColor(Color.black);

		Vector2f[] pts = ((Box) body.getShape()).getPoints(body.getPosition(), body.getRotation());
		Vector2f v1 = pts[0];
		Vector2f v2 = pts[1];
		Vector2f v3 = pts[2];
		
		g.setColor(Color.black);
		Rectangle r = new Rectangle(body.getPosition().getX(), body.getPosition().getY(), v2.getX() - v1.getX(), v3.getY() - v1.getY());
		
		// display collision box
		float left = r.getX() - Game.PWIDTH/2;
		float right = r.getX() + Game.PWIDTH/2;
		if(left <= 0){
			// manual
			g.fillRect(r.getX() - r.getWidth()/2, r.getY() - r.getHeight()/2, r.getWidth(), r.getHeight());
		}
		else if(right > Game.TS * Level.map.getWidth()){
			// manual
			g.fillRect(offsetX + r.getX() - r.getWidth()/2, r.getY() - r.getHeight()/2, r.getWidth(), r.getHeight());
		}
		else{
			// center
			offsetX = Game.PWIDTH/2 - r.getX();
			g.fillRect(Game.PWIDTH/2 - r.getWidth()/2, r.getY() - r.getHeight()/2, r.getWidth(), r.getHeight());
		}
	}
	
	public boolean isOpenUp(int delta){
		int newY = (int) ((pos.getY() + move.y * delta)/Game.TS);
		
		return !Level.solid[(int) pos.getX()/Game.TS][newY] || !Level.solid[(int) pos.getX()/Game.TS + 1][newY];
	}
}
