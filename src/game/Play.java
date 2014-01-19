package game;

import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.CollisionListener;
import net.phys2d.raw.World;
import net.phys2d.raw.shapes.Box;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.Player;
import entities.Robot;

public class Play extends BasicGameState implements CollisionListener{
	
	public static Player player;
	public static Robot robot;
	
	public static World physWorld;
	public static int level, world;
	
	static CollisionListener cl;
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		level = 1;
		
		initLevel(level);
		cl = this;
		physWorld.addListener(cl);
	}
	
	public static void initLevel(int level) throws SlickException{
		physWorld = new World(new Vector2f(0.0f, 1000), 100);
		new Level(level);
		
		robot = new Robot(Level.rStart.getX(), Level.rStart.getY());
		player = new Player(Level.pStart.getX(), Level.pStart.getY());

		physWorld.add(player.body);
		physWorld.add(robot.body);
		
		new Sidebar();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setBackground(Color.white);

		
		// translate -----------
		
		g.translate(Player.offsetX, 0);

		// map
		Level.map.render(0, 0, 0); // layer 0 only (layer 1 is invi layer)
//		for(Body b : Level.solidBodies){
//			drawBody(g, b);
//		}
		
		robot.render(g);

		g.translate(-Player.offsetX, 0);

		player.render(g);
		Sidebar.render(g);		
		
		// level instruction/goal
		g.setColor(Color.black);
		g.drawString(Level.instructions, 10, 10);
		
//		g.setColor(Color.black);
//		Body body = player.body;
//		g.drawString(body.getPosition().getX() + " " + body.getPosition().getY(), 100, 100);
//		g.drawString("Render " + Player.renderX + " " + Player.renderY, 100, 120);
//		g.drawString("Offset " + Player.offsetX + " " + Player.renderY, 100, 140);

		// end translate -------------

		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		if(input.isKeyPressed(Input.KEY_F5)){
			init(gc, sbg);
		}
		
		physWorld.step();
		
		player.update(input, delta);
		robot.update(delta);
		
		Sidebar.update(delta, input);
		
	}

	@Override
	public int getID() {
		return 2;
	}

	@Override
	public void collisionOccured(CollisionEvent e) {
//		System.out.println(e);
	}
	
	public static void drawBody(Graphics g, Body b) {
		Vector2f[] pts = ((Box) b.getShape()).getPoints(b.getPosition(), b.getRotation());
		Vector2f v1 = pts[0];
		Vector2f v2 = pts[1];
		Vector2f v3 = pts[2];
		Vector2f v4 = pts[3];
		
		g.drawLine(v1.x, v1.y, v2.x, v2.y);
		g.drawLine(v2.x, v2.y, v3.x, v3.y);
		g.drawLine(v3.x, v3.y, v4.x, v4.y);
		g.drawLine(v4.x, v4.y, v1.x, v1.y);
	}

}
