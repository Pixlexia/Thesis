package platformer;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame{

	public static int GWIDTH = 1000, GHEIGHT = 600;
	public static int PWIDTH = 700;
	public static int TS = 20;
	
	/*
	 * This is a skeleton for a side scrolling platformer.
	 * For levels use TileD editor (.tmx)
	 */
	
	public Game() {
		super("Platformer Game");
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		this.addState(new Play());
		this.enterState(0);
	}
	
	public static void main(String args[]) throws SlickException{
		AppGameContainer appgc = new AppGameContainer(new Game());
		appgc.setDisplayMode(GWIDTH, GHEIGHT, false);
		appgc.setTargetFrameRate(60);
		appgc.setShowFPS(false);
		appgc.start();
	}
	
}
