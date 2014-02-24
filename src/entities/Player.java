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
import game.Play;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.shapes.Box;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;


public class Player extends Character{
	//=============
	// PHYS2D
	//=============
	
	// camera
	public static float camX, camY;
	Vector2f camMove;
	float camAcc, camSpeed, camMaxSpeed;
	
	// jump
	boolean canJump;
	int jumpFrames;
				 
	// jump
	float jumpSpeed = -28000; // velocity applied on jump
	int jumpMaxFrames = 50; // max frames while holding spacebar
	
	public static float offsetX, offsetY;
	public static float renderX, renderY;
	
	public Player(float x, float y){
		super(x, y);
		
		offsetX = 0;
		// PHYS2D
		body = new Body("player", new Box(25, 40), 1);
		body.setPosition(x, y);
		body.setRotatable(false);
		body.setMaxVelocity(maxSpeed.x, maxSpeed.y);
		body.setCanRest(true);
		body.setRestitution(10);
		
		camX = offsetX;
		camY = 0;
		camMaxSpeed = 3;
		camSpeed = 0;
		camMove = new Vector2f(0,0);
		camAcc = 0.2f;
	}
	
	public void update(Input input, int delta){
		
		// controls move right
		if(input.isKeyDown(Input.KEY_D) || input.isKeyDown(Input.KEY_RIGHT)){
			body.setForce(speed, 0);
		}
		// controls move left
		else if(input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_LEFT)){
			body.setForce(-speed, 0);
		}
		// jump
		
		if(body.getVelocity().getY() == 0 && !isInLadder() && (input.isKeyPressed(Input.KEY_W) || input.isKeyPressed(Input.KEY_SPACE) || input.isKeyPressed(Input.KEY_UP))){
			body.addForce(new Vector2f(0, jumpSpeed));
		}
		input.clearKeyPressedRecord();
		
		// ladder controls
		if(isInLadder()){
			body.setDamping(0.9f);
			body.setMaxVelocity(1400, maxSpeed.y);
			int xmod = 6000;
			if(input.isKeyDown(Input.KEY_W) || input.isKeyDown(Input.KEY_UP)){
//				moveUpLadder();
				body.setForce(body.getForce().getX()*xmod, -70000);
			}
			else if(input.isKeyDown(Input.KEY_S) || input.isKeyDown(Input.KEY_DOWN)){
//				moveDownLadder();
				body.setForce(body.getForce().getX()*xmod, 70000);
			}
			else{
				body.setForce(body.getForce().getX()*xmod, -1000);
			}
		}
		else{
			body.setMaxVelocity(maxSpeed.x, maxSpeed.y);
			body.setDamping(0);
		}
		
		// check if exit
		if(isInExit() && !Level.exitLocked && !Play.levelWin){
			if(Play.tutorialDone){
//				Play.nextWorld();
			}
				Play.levelWin = true;
		}
		
		super.update(delta);
	}
	
	public boolean isInLadder() {
		for (Point p : Level.ladders) {
			if (getBounds().contains(p)) {
				return true;
			}
		}

		return false;
	}
	
	public boolean isInExit(){
		for(Point p : Level.exit){
			if (getBounds().contains(p)){
				return true;
			}
		}
		
		return false;
	}
	
//	public void moveUpLadder(){
//		System.out.println("moveing up");
//		body.setPosition(body.getPosition().getX(), body.getPosition().getY()-2);
//	}
//	
//	public void moveDownLadder(){
//		body.setForce(0, 10);
//	}

	@Override
	public void render(Graphics g){
		g.setColor(Color.black);

		Vector2f[] pts = ((Box) body.getShape()).getPoints(body.getPosition(), body.getRotation());
		Vector2f v1 = pts[0];
		Vector2f v2 = pts[1];
		Vector2f v3 = pts[2];
				
		g.setColor(Color.black);
		Rectangle r = new Rectangle(renderX, renderY, v2.getX() - v1.getX(), v3.getY() - v1.getY());
		
		// adjust offsetX
		if(body.getPosition().getX() - Game.PWIDTH/2 <= 0){
			// manual left side
			offsetX = 0;
			renderX = body.getPosition().getX();
		}
		else if(body.getPosition().getX() + Game.PWIDTH/2 > Level.map.getWidth() * Level.map.getTileWidth()){
			// manual right side
			offsetX = Game.PWIDTH/2 - (Level.map.getWidth() * Level.map.getTileWidth() - Game.PWIDTH/2);
			renderX = offsetX + body.getPosition().getX();
		}
		else{
			//center
			offsetX = Game.PWIDTH/2 - body.getPosition().getX();
			renderX = Game.PWIDTH/2;
		}
		
		// adjust offsetY
		if(body.getPosition().getY() - Game.PHEIGHT/2 <= 0){
			// manual upper
			offsetY = 0;
			renderY = body.getPosition().getY();
		}
		else if(body.getPosition().getY() + Game.PHEIGHT/2 > Level.map.getHeight() * Level.map.getTileWidth()){
			// manual down side
			renderY = offsetY + body.getPosition().getY();
		}
		else{
			//center
			offsetY = Game.PHEIGHT/2 - body.getPosition().getY();
			renderY = Game.PHEIGHT/2;
		}
		
		renderX -= r.getWidth()/2;
		renderY -= r.getHeight()/2;

		r.setX(renderX);
		r.setY(renderY);
		
		g.fill(r);		
	}
}
