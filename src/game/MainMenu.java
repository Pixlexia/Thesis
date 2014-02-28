package game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.Button;

public class MainMenu extends BasicGameState {

	Button enter;
	float gridx, gridy;
	
	@Override
	public void init(GameContainer gc, final StateBasedGame sbg) throws SlickException {
		new Res();

		gc.setDefaultFont(Res.futura16);
		enter = new Button(Game.GWIDTH/2 - 300/2, 450, 300, 50){
			@Override
			public void onClick(){
				sbg.enterState(1);
			}
			
			@Override
			public void isHovered(){
				inflate();
			}
		};
		
		enter.color = new Color(220, 220, 220);
		enter.hoverColor = new Color(220, 220, 220);
		enter.borderRadius = 5;
		enter.inflateRate = 2;
		
		gridx = 0;
		gridy = 0;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setBackground(Color.white);
		

		int boxSize = 40;
		
		gridx--;
		gridy++;
		
		gridx %= boxSize;
		gridy %= boxSize;
		
		g.setLineWidth(1);
		
		g.setColor(new Color(230, 230, 230));
		for(int i = 0 ; i < Game.GHEIGHT; i++){
			g.drawLine(gridx + 0, gridy + i*boxSize, boxSize + gridx + Game.GWIDTH, gridy + i*boxSize);
		}
		for(int i = 0 ; i < Game.GWIDTH; i++){
			g.drawLine(gridx + i*boxSize, gridy + 0 - boxSize, gridx + i*boxSize, boxSize + gridy + Game.GWIDTH);
		}
		
		g.setColor(new Color(0, 0, 0, 0.8f));
		
		Res.centerText(Res.futura72, "TEMPORARY TITLE", Game.GWIDTH/2, 120);

		enter.render(g);
		Res.centerText(Res.futura36, "PLAY", Game.GWIDTH/2, 455, new Color(50, 50, 50));
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Res.updateCursor(gc);
		Input input = gc.getInput();
		
		if(input.isKeyPressed(Input.KEY_ENTER)){
			sbg.enterState(1);
		}
		
		enter.update(delta, gc.getInput().getMouseX(), gc.getInput().getMouseY(), gc.getInput());
	}

	@Override
	public int getID() {
		
		return 0;
	}

}
