package platformer;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Play extends BasicGameState {

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		new Character(0, 0);
		new Level();
		new Sidebar();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setBackground(Color.white);
		Character.render(g);
		g.translate(Character.offsetX, 0);
		Level.map.render(0, 0);
		Sidebar.render(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		Character.update(input, delta);
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
