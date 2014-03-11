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

		enlargeSize = 3f;
		inflateRate = 3f;
		timeBeforeInflate = r.nextInt(10*Sidebar.maxRactions);
		
		borderRadius = 4;
	}
	
	public void render(Graphics g){
		if(renderNow){
			
			// drop shadow
			g.setColor(new Color(190, 190, 180));
			g.fillRoundRect(getBounds().getX() - 1,getBounds().getY()+4, getBounds().getWidth() + 2, getBounds().getHeight(), borderRadius);

			g.setLineWidth(2);

			if(isHovered){
//				g.setColor(new Color(240, 240, 240));
			}
			else{
			}
			
			g.setColor(new Color(250, 250, 250));
			
			g.fillRoundRect(getBounds().getX(), getBounds().getY(), getBounds().getWidth(), getBounds().getHeight(), borderRadius);
			

			// outer stroke
			g.setColor(new Color(200, 200, 200, 255));
			g.draw(getBounds());
			
			// inner glow
			g.setColor(Color.white);
			g.drawRect(getBounds().getX()+2, getBounds().getY()+2, getBounds().getWidth() - 5, getBounds().getHeight() - 5);
			
			// shadow bottom half
			g.setColor(new Color(0, 0, 0, 0.05f));
			g.fillRect(getBounds().getX() + 3, getBounds().getY()+getBounds().getHeight()/2, getBounds().getWidth() - 6, getBounds().getHeight()/2 - 3);
			
			g.scale(0.75f, 0.75f);
			g.drawImage(hoverImage, (int) imagePos.getX() * 1/0.75f + 5, (int) imagePos.getY() * 1/0.75f + 6);
			g.scale(1/0.75f, 1/0.75f);

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
	public void onHover(){
		super.onHover();
		Res.hover2.play();
	}
	
	@Override
	public void isHovered(){
		super.isHovered();
		enlargeSize = 2f;
		inflate();
	}
	
	@Override
	public void onClick(){
		Res.click.play();
		if(!Sidebar.showCalculator){
			super.onClick();
			enlargeSize = 10f;
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
