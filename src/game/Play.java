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
	
	Player player;
	Robot robot;
	
	public static World world;
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		robot = new Robot(20, 0);
		player = new Player(0, 0);

		world = new World(new Vector2f(0.0f, 2000), 100);
				
		world.add(player.body);
		world.addListener(this);
		
		new Level();
		new Sidebar();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setBackground(Color.white);
		
		player.render(g);
		
		// start phys2d test
		
        // end phys2d test
		
		g.translate(Player.offsetX, 0);

		Level.map.render(0, 0);
		
		robot.render(g);
		
		Sidebar.render(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		world.step();
		
		Input input = gc.getInput();
		player.update(input, delta);
		robot.update(delta);
		
		Sidebar.update(delta, input);
		
		if(input.isKeyPressed(Input.KEY_F5)){
			init(gc, sbg);
		}
	}

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public void collisionOccured(CollisionEvent e) {
		System.out.println(e);
	}
	
	public static void drawBody(Graphics g, Body b) {
		Vector2f[] pts = ((Box) b.getShape()).getPoints(b.getPosition(), b.getRotation());
		Vector2f v1 = pts[0];
		Vector2f v2 = pts[1];
		Vector2f v3 = pts[2];
		Vector2f v4 = pts[3];
		
		g.setColor(Color.black);
		g.drawLine(v1.x, v1.y, v2.x, v2.y);
		g.drawLine(v2.x, v2.y, v3.x, v3.y);
		g.drawLine(v3.x, v3.y, v4.x, v4.y);
		g.drawLine(v4.x, v4.y, v1.x, v1.y);
	}

}
