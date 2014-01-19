package entities;

import game.RAction;
import game.RActionList;
import game.Sidebar;
import game.Sprites;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;


/*
 * Program buttons (bottom of Command buttons)
 * The command buttons to be executed
 */

public class PButton extends Button{
	
	int index;
	RAction type;
	
	ArrayList<RAction> notIdentifiers;

	public PButton(int i, RAction ra, float w, float h, float x, float y) {
		super(w, h);
		index = i;
		setType(ra);
		pos = new Point(x, y);
		
		pos.setX(x);
		collisionBox.setX(x);
		pos.setY(y);
		collisionBox.setY(y);
		
	}
	
	public void render(Graphics g, float x, float y){
		if(isHovered){
			g.drawImage(image, x, y);
		}
		else{
			g.drawImage(hoverImage, x, y);
		}
	}
	
	public void deleteButton(){
		ArrayList<PButton> pb = Sidebar.programButtons;
		System.out.println("delete " + index);
		pb.remove(index);

		CButton.buildProgram();
		
		if(CButton.inIfCond){
			if(RActionList.isStartCond(type)){
				CButton.inIfCond = false;
			}
		}
		
	}
	
	public void onClick(){
		ArrayList<PButton> pb = Sidebar.programButtons;
		
		if(!Robot.isRunning){
			deleteButton();
			
			// update positions
			for(int i = 0; i < pb.size(); i++){
				pb.get(i).index = i;
				pb.get(i).collisionBox.setX(Sidebar.programUIButtons[i].getX());
				pb.get(i).collisionBox.setY(Sidebar.programUIButtons[i].getY());
			}
		}
		
		CButton.buildProgram();
	}
	
	public void setType(RAction t){
		type = t;
		image = Sprites.rActionSprites.get(t).copy();
		image.setAlpha(0.7f);
		hoverImage = Sprites.rActionSprites.get(t).copy();
	}
	
}
