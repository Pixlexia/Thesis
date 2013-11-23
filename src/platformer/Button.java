package platformer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

public class Button {
	
	public enum Command { mLeft, mRight, interact };
	
	Image image, hoverImage;
	Point pos = new Point(0, 0);
	Rectangle collisionBox;
	
	boolean isHovered;
	
	public Button(float x, float y, float w, float h) throws SlickException{
		pos = new Point(x, y);
		collisionBox = new Rectangle(pos.getX(), pos.getY(), w, h);
		isHovered = false;
	}
	
	public Button(float w, float h){
		collisionBox = new Rectangle(pos.getX(), pos.getY(), w, h);
		isHovered = false;
	}
	
	public void render(Graphics g){
		if(isHovered){
			g.setColor(Color.white);
		}
		else{
			g.setColor(new Color(255, 255, 255, 0.7f));
		}
		
		g.fill(getBounds());
	}
	
	public void update(float x, float y, Input input){
		if(getBounds().contains(x, y)){
			isHovered = true;
		}
		else
			isHovered = false;
		
		if(isHovered && input.isMousePressed(0)){
			click();
		}
	}
	
	public void click(){
		System.out.println("Clickity click");
	}
	
	public Rectangle getBounds(){
		return collisionBox;
	}
	
	public void setX(float x){
		pos.setX(x);
		collisionBox.setX(x);
	}
	
	public void setY(float y){
		pos.setY(y);
		collisionBox.setY(y);		
	}
}
