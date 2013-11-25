package platformer;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.Player;
import entities.Robot;

public class Play extends BasicGameState {
	
	Player player;
	Robot robot;
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		robot = new Robot(10, 0);
		player = new Player(0, 0);
		new Level();
		new Sidebar();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setBackground(Color.white);
		
		player.render(g);
		
		g.translate(Player.offsetX, 0);

		robot.render(g);
		
		Level.map.render(0, 0);
		Sidebar.render(g);

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
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

}
