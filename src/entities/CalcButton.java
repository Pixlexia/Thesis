package entities;

import game.Res;
import game.Sidebar;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class CalcButton extends Button{

	public int value;
	
	public CalcButton(int v, float x, float y, float w, float h) throws SlickException {
		super(x, y, w, h);
		value = v;
	}
	
	@Override
	public void isHovered(){
		super.isHovered();
		enlargeSize = 2;
		inflate();
	}
	
	@Override
	public void onClick(){
		enlargeSize = 8;
		inflate();
		if(Calculator.currentValue.length() < 2){			
			if(Calculator.currentValue == "0"){			
				Calculator.currentValue = value + "";
			}
			else
				Calculator.currentValue += value;
		}
	}
	
	@Override
	public void render(Graphics g){
		super.render(g);
		Res.futura24.drawString(pos.getX() + Sidebar.bSize/2 - Res.futura24.getWidth(value+"")/2, pos.getY() + Sidebar.bSize/2 - Res.futura24.getHeight(value+"")/2 - 3, value + "", Color.black);
	}

}
