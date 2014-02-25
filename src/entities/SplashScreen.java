package entities;

import game.Game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;

public class SplashScreen extends BasicGameState{
	Image img, img2, pixlexia;
	int timer;
	Point square, target;
	float opacity, opacity2;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		img = new Image("res/logo.png");
		img = img.getScaledCopy(0.75f);
		img2 = new Image("res/bloop.png");
		img2 = img2.getScaledCopy(0.75f);
		pixlexia = new Image("res/pixlexia.png");
		pixlexia = pixlexia.getScaledCopy(0.75f);
		
		square = new Point(Game.GWIDTH/2 + img.getWidth()/2 - (24*0.75f), 200);
		target = new Point(square.getX() + 12, square.getY() - 12);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setBackground(Color.white);
		g.setAntiAlias(false);
		
		img.setAlpha(opacity);
		img2.setAlpha(opacity);
		pixlexia.setAlpha(opacity2);
		g.drawImage(img, Game.GWIDTH/2 - img.getWidth()/2, 200);
		g.drawImage(img2, square.getX(), square.getY());
		g.drawImage(pixlexia, Game.GWIDTH/2 - pixlexia.getWidth()/2, 380);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		timer += delta;
		
		if(timer < 2000){
			if(opacity <= 1)
				opacity+= 0.009f;
		}
		else if(timer < 6000){
			int speed = 20;
			square.setX(square.getX() + (target.getX() - square.getX())/speed);
			square.setY(square.getY() + (target.getY() - square.getY())/speed);
			
			if(opacity2 <= 1)
				opacity2+=0.009f;
		}
		else if(timer < 8000){
			opacity -= 0.01f;
			opacity2 -= 0.01f;
		}
		else{
			sbg.enterState(0, new FadeInTransition(), new HorizontalSplitTransition());
//			sbg.enterState(0);						
		}
			
	}

	@Override
	public int getID() {
		
		return 3;
	}

}
