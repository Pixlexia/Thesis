package platformer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;

public class PButton extends Button{
	
	int index;
	Command type;

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
	
	public void click(){
		System.out.println("delete " + index);
		Sidebar.commands[index] = null;
		Sidebar.pButtons[index].type = null;
	}
	
	public void setType(Command c){
		type = c;
		
		switch(type){
		case mLeft:
			image = Sprites.left;
			hoverImage = Sprites.leftHover;
			break;
			
		case mRight:
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
