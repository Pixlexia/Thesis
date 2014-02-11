package entities;

import game.Res;

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
		String s = null;
		switch(i){
		case 0:
			c = new Color(255, 52, 52);
			s = "RED";
			break;
		case 1:
			c = new Color(255, 203, 0);
			s = "YELLOW";
			break;
		case 2:
			c = new Color(7, 147, 255);
			s = "BLUE";
			break;
		}

		g.setLineWidth(2);
		g.setAntiAlias(false);
		g.setColor(new Color(241, 241, 239));
		g.fill(getBounds());
		g.setColor(c);
		g.fillRect(pos.getX(), pos.getY(), getBounds().getWidth(), 11);
		Res.futura10.drawString(pos.getX() + getBounds().getWidth()/2 - Res.futura10.getWidth(s)/2 - 2, pos.getY() - 1, s);
		g.drawRect(pos.getX(), pos.getY(), getBounds().getWidth(), getBounds().getHeight());
		g.setLineWidth(1);
	}

}
