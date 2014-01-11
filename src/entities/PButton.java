package entities;

import game.RAction;
import game.Sidebar;
import game.Sprites;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;


/*
 * Program buttons (bottom of Command buttons)
 * The command buttons to be executed
 */

public class PButton extends Button{
	
	int index;
	RAction type;

	public PButton(int i, float w, float h, float x, float y) {
		super(w, h);
		type = null;
		index = i;
		pos = new Point(x, y);
		
		pos.setX(x);
		collisionBox.setX(x);
		pos.setY(y);
		collisionBox.setY(y);
	}
	
	public void render(Graphics g){
		if(type == null){
			g.setColor(new Color(255, 255, 255, 0.5f));
			g.fill(getBounds());
		}
		else{
			if(isHovered){
				g.drawImage(image, pos.getX(), pos.getY());
			}
			else{
				g.drawImage(hoverImage, pos.getX(), pos.getY());
			}
		}
	}
	
	public void onClick(){
		System.out.println("delete " + index);
		Sidebar.commands[index] = null;
		Sidebar.programSlots[index].type = null;
	}
	
	public void setType(RAction c){
		type = c;
		
		switch(type){
		case moveLeft:
			image = Sprites.left;
			hoverImage = Sprites.leftHover;
			break;
			
		case moveRight:
			image = Sprites.right;
			hoverImage = Sprites.rightHover;
			break;
			
		case interact:
			image = Sprites.interact;
			hoverImage = Sprites.interactHover;
			break;
		}
	}
	
}
