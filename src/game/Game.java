package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import entities.SplashScreen;

public class Game extends StateBasedGame{

	public static int GWIDTH = 1000, GHEIGHT = 600;
	public static int PWIDTH = 680, PHEIGHT = GHEIGHT; // width/height of game screen, width of sidebar is GWIDTH - PWIDTH
	public static int TS = 20;
	
	/*
	 * This is a skeleton for a side scrolling platformer.
	 * For levels use TileD editor (.tmx)
	 */
	
	public Game() {
		super("Seumas & Ritchie");
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new MainMenu()); // 0
		this.addState(new WorldMenu()); // 1
		this.addState(new Play()); // 2
		this.addState(new SplashScreen()); // 3
		this.addState(new TheEnd()); // 4
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
