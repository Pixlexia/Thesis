package game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.Player;

public class TheEnd extends BasicGameState{

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		
		Play.player.idleRight.draw(Game.GWIDTH/2 - Play.player.idleRight.getWidth()/2, 270);
		
		arg2.setBackground(Color.white);
		Res.futura60.drawString(Game.GWIDTH/2 - Res.futura60.getWidth("THE END")/2, 180, "THE END", new Color(20, 20, 20));
		Res.futura24.drawString(Game.GWIDTH/2 - Res.futura24.getWidth("Thank you for playing!")/2, 350, "Thank you for playing!", new Color(20, 20, 20));
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
	}

	@Override
	public int getID() {
		
		return 4;
	}

}
