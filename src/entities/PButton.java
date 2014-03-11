package entities;
import game.RAction;
import game.Res;
import game.Sidebar;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Point;


/*
 * Program buttons (bottom of Command buttons)
 * The command buttons to be executed
 */

public class PButton extends Button{
	
	int index;
	public RAction type;
	
	private boolean moveBack;
	public boolean colorRed;
	public boolean colorGreen;
	
	public Point newPos;
	public int function;
	
	ArrayList<RAction> notIdentifiers;

	public PButton(int i, RAction ra, float w, float h, float x, float y, int func) {
		super(w, h);
		
		function = func;
		
		index = i;
		setType(ra);
		pos = new Point(x, y);
		
		pos.setX(x);
		pos.setY(y);
		
		moveBack = false;		
		
		newPos = new Point(0, 0);
		
		inflateRate = 5f;
		
		inflateFromZero();
	}
	
	public void render(Graphics g, float x, float y, int imageAlpha){
		if(colorRed){
			g.setColor(new Color(255, 150, 150));
		}
		else if(colorGreen){
			g.setColor(new Color(160, 235, 140));
		}
		else if(isHovered){
			g.setColor(new Color(255, 255, 255, 255));
		}
		else{
			g.setColor(new Color(255, 255, 255, 255));
		}
		g.fill(getBounds());

		//border
		g.setLineWidth(2);
		
		g.setColor(new Color(240, 240, 240));
		g.fill(getBounds());
		
		// outer stroke
		g.setColor(new Color(200, 200, 200, 255));
		g.drawRect(getBounds().getX(), getBounds().getY(), getBounds().getWidth(), getBounds().getHeight());
		
		// inner glow
		g.setColor(Color.white);
		g.drawRect(getBounds().getX()+2, getBounds().getY()+2, getBounds().getWidth() - 4, getBounds().getHeight() - 4);
		
		hoverImage.setAlpha((float) imageAlpha/255f);
		
		if(type == RAction.number){
			String s;
			if(Robot.numbers.get(index) == null){
				s = "";
			}
			else{
				s = Robot.numbers.get(index) + "";
			}
			Res.futura24.drawString(pos.getX() + Sidebar.bSize/2 - Res.futura24.getWidth(s)/2, pos.getY() + 8, s, Color.black);
		}
		else if(type == RAction.startLoop){
			String s;
			if(Robot.loops.get(index) == null){
				s = "";
			}
			else{
				s = "LOOP";
				int i = Robot.iterateCounts.get(index);
				Res.futura10.drawString((int) pos.getX() + Sidebar.bSize/2 - Res.futura10.getWidth(s)/2, (int) pos.getY(), s, Color.black);
				Res.futura36.drawString((int) pos.getX() + Sidebar.bSize/2 - Res.futura36.getWidth(i + "")/2, (int) pos.getY() + 5, i + "", Color.black);
			}
		}
		else{

			g.scale(0.75f, 0.75f);
			g.drawImage(hoverImage, (int) pos.getX() * 1/0.75f + 9, (int) pos.getY() * 1/0.75f + 8);
			g.scale(1/0.75f, 1/0.75f);
			g.setLineWidth(1);
		}
	}
	
	@Override
	public void update(int delta, float x, float y, Input input){
		super.update(delta, x, y, input);
		
		if(moveBack){
			if(Math.round(pos.getX()) != Math.round(newPos.getX()) ||
					Math.round(pos.getY()) != Math.round(newPos.getY())){
				int speed = 7;
				
				pos.setY(pos.getY() + (newPos.getY() - pos.getY())/speed);
				pos.setX(pos.getX() + (newPos.getX() - pos.getX())/speed);
			}
			else{
				moveBack = false;
			}
		}
		
//		System.out.println(index + " " + moveBack + " " + pos.getX() + " " + newPos.getX());
	}
	
	public void deleteButton(){
		ArrayList<PButton> pb = getProgramButtons();
		
//		System.out.println("delete " + index);
		
		pb.remove(index);
		
		if(type == RAction.number){
			Robot.numbers.remove(index);
		}

		for(int i = index; i < pb.size(); i++){
			pb.get(i).moveBack();
		}
	}
	
	@Override
	public void isHovered(){
		super.isHovered();
		enlargeSize = 3;
		inflate();
	}
	
	@Override
	public void onClick(){
		Res.hover.play();
		if(!Sidebar.showCalculator){
			super.onClick();
			enlargeSize = 8;
			super.onClick();
			ArrayList<PButton> pb = getProgramButtons();
			if(!Robot.isRunning){
				deleteButton();
				
				// update positions
				for(int i = 0; i < pb.size(); i++){
					pb.get(i).index = i;
				}
			}			
		}
	}
	
	public void moveBack(){
		if(type == RAction.number){
			Robot.numbers.put(index-1, Robot.numbers.get(index));
			Robot.numbers.remove(index);
		}
		moveBack = true;
		
		switch(function){
		case 0:
			newPos = new Point(Sidebar.programUIButtons[index-1].getX(), Sidebar.programUIButtons[index-1].getY());
			break;
			
		case 1:
			newPos = new Point(Sidebar.fui1.programUIButtons[index-1].getX(), Sidebar.fui1.programUIButtons[index-1].getY());
			break;
			
		case 2:
			newPos = new Point(Sidebar.fui2.programUIButtons[index-1].getX(), Sidebar.fui2.programUIButtons[index-1].getY());
			break;
		}
		
	}
	
	public void setType(RAction t){
		type = t;
		image = Res.rActionSprites.get(t).copy();
		image.setAlpha(0.7f);
		hoverImage = Res.rActionSprites.get(t).copy();
	}
	
	public ArrayList<PButton> getProgramButtons(){
		switch(function){
		case 0:
			return Sidebar.programButtons;
			
		case 1:
			return Sidebar.fui1.programButtons;
			
		case 2:
			return Sidebar.fui2.programButtons;
		}
		
		return null;
	}
	
}
