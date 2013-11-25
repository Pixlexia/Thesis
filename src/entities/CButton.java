package entities;

import org.newdawn.slick.Graphics;

import platformer.RAction;
import platformer.Sidebar;
import platformer.Sprites;

/*
 *  Command buttons (top of sidebar commands panel)
 */

public class CButton extends Button{
	RAction type;

	public CButton(RAction t, float w, float h) {
		super(w, h);
		type = t;
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
	
	public void render(Graphics g){
		if(isHovered){
			g.drawImage(hoverImage, pos.getX(), pos.getY());
		}
		else{
			g.drawImage(image, pos.getX(), pos.getY());
		}
	}
	
	public void onClick(){
		Sidebar.addCommand(type);
	}

}
