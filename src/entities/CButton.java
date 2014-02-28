package entities;

import game.RAction;
import game.Res;
import game.Sidebar;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

/*
 *  Command buttons (top of sidebar commands panel)
 */

public class CButton extends Button{
	RAction type;
	public int timeBeforeInflate;
	public boolean renderNow;
	
	Point imagePos;
	
	public CButton(RAction t, float x, float y, float w, float h) throws SlickException {
		super(x, y, w, h);
		type = t;
		image = Res.rActionSprites.get(t).copy();
		image.setAlpha(0.6f);
		imagePos = new Point(x + w/2 - image.getWidth()/2, y + h/2 - image.getHeight()/2);
		
		hoverImage = Res.rActionSprites.get(t).copy();
		renderNow = false;
		Random r = new Random();

		enlargeSize = 7f;
		inflateRate = 3f;
		timeBeforeInflate = r.nextInt(10*Sidebar.maxRactions);
	}
	
	public void render(Graphics g){
		if(isHovered){
			g.setColor(new Color(255, 255, 255, 255));
		}
		else{
			g.setColor(new Color(255, 255, 255, 200));
		}
		
		if(renderNow){
			g.fill(getBounds());
			g.drawImage(hoverImage, (int) imagePos.getX(), (int) imagePos.getY());			
		}
		
//		if(!HelpText.isAlive)
	}
	
	@Override
	public void update(int delta, float x, float y, Input input){
		super.update(delta, x, y, input);
		
		if(timeBeforeInflate > 0){
			timeBeforeInflate -= delta;
		}
		else if(!renderNow){
			inflateFromZero();
			renderNow = true;
		}
	}
	
	@Override
	public void isHovered(){
		super.isHovered();
		enlargeSize = 2;
		inflate();
	}
	
	@Override
	public void onClick(){
		if(!Sidebar.showCalculator){
			super.onClick();
			enlargeSize = 7;
			inflate();
			
			int size = 0, max = 0;
			
			switch(Sidebar.activeFunction){
			case 0:
				size = Sidebar.programButtons.size();
				max = Sidebar.maxCommands;
				break;
				
			case 1:
				size = Sidebar.fui1.programButtons.size();
				max = Sidebar.fui1.maxCommands;
				break;
				
			case 2:
				size = Sidebar.fui2.programButtons.size();
				max = Sidebar.fui2.maxCommands;
				break;
			}
			
			if(size < max){
				if(type == RAction.number){
					Sidebar.showCalculator = true;
					Calculator.wherePutNum = "number";
				}
				else if(type == RAction.startLoop){
					Sidebar.showCalculator = true;
					Calculator.wherePutNum = "loop";
				}
				Sidebar.addCommand(type);
				inflate();
			}			
		}
	}

}
