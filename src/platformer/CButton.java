package platformer;

import org.newdawn.slick.Graphics;

public class CButton extends Button{
	Command type;

	public CButton(Command t, float w, float h) {
		super(w, h);
		type = t;
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
	
	public void render(Graphics g){
		if(isHovered){
			g.drawImage(hoverImage, pos.getX(), pos.getY());
		}
		else{
			g.drawImage(image, pos.getX(), pos.getY());
		}
	}
	
	public void click(){
		Sidebar.addCommand(type);
	}

}
