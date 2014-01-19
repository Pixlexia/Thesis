package entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class InventoryButton extends Button{
	public InventoryButton(float x, float y, float w, float h) throws SlickException {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}
	
	public void render(Graphics g, int i){
		Color c = null;
		switch(i){
			case 0: c = Color.red; break;
			case 1: c = Color.yellow; break;
			case 2: c = Color.blue; break;
		}

		g.setColor(c);
		
		g.fill(getBounds());
	}

}
