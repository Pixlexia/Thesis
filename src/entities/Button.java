package entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

public class Button{
	public static boolean handCursor;
	
	public Image image, hoverImage;
	public Point pos = new Point(0, 0);
	public float w, h;
	
	public Color color, hoverColor;
	
	boolean isHovered;
	
	// used for wobbly effects
	public float enlargeSize = 5;
	public float inflateRate = 3.5f;
	public float defaultW, defaultH;
	protected boolean goBig;
	
	public int borderRadius;
	
	boolean playHover;
	
	public float hoverOpacity, defaultOpacity;
	
	public Button(float x, float y, float w, float h) throws SlickException{
		pos = new Point(x, y);
		this.w = w;
		this.h = h;
		isHovered = false;
		
		defaultW = w;
		defaultH = h;
		
		playHover = true;
		
		hoverOpacity = 1;
		defaultOpacity = 1;
		borderRadius = 0;	
		handCursor = false;
	}
	
	public Button(float w, float h){
		handCursor = false;
		isHovered = false;
		this.w = h;
		this.h = w;

		defaultW = w;
		defaultH = h;
	}
	
	public void inflateFromZero(){
		w = 0;
		h = 0;
		goBig = true;
	}
	
	public void inflate(){
		goBig = true;
	}
	
	public void onHover(){
		if(!isHovered)
			isHovered = true;
	}
	
	public void isHovered(){
	}
	
	public void render(Graphics g){
		if(color != null){			
			if(isHovered){
				if(hoverColor != null){
					hoverColor.a = hoverOpacity;
					g.setColor(hoverColor);					
				}
				else{
					color.a = hoverOpacity;
					g.setColor(color);
				}
			}
			else{
				color.a = defaultOpacity;
				g.setColor(color);
			}
		}
		else{			
			if(isHovered){
				g.setColor(Color.white);
			}
			else{
				g.setColor(new Color(255, 255, 255, 0.7f));
			}
		}
		
		g.fillRoundRect(getBounds().getX(), getBounds().getY(), w, h, borderRadius);
		
		g.setColor(Color.black);
	}
	
	public void update(int delta, float x, float y, Input input){
		// click/hover listener
		if(getBounds().contains(x, y)){
			if(!isHovered){
				onHover();
			}
			isHovered = true;
			isHovered();
//			handCursor = true;
		}
		else{
			handCursor = false;
			playHover = true;
			isHovered = false;
		}
		
		if(isHovered && input.isMousePressed(0)){
			onClick();
		}
		
		// inflate effects
		if(goBig){
			w = (w + (defaultW + enlargeSize * 2 - w)/inflateRate);
			h = (h + (defaultH + enlargeSize * 2  - h)/inflateRate);
			
			if(w > defaultW + enlargeSize){
				goBig = false;
			}
		}
		else{
			if(w > defaultW){
				w = (w - (w - defaultW)/inflateRate);
				h = (h - (h - defaultH)/inflateRate);
			}
		}
		
//		pos.setX(pos.getX() - 1);
		
//		pos.setX(getCenterX() - w/2);
//		pos.setY(getCenterY() - h/2);
	}
	
	public void onClick(){
	}
	
	public Rectangle getBounds(){
		return new Rectangle(getCenterX() - w/2, getCenterY() - h/2, w, h);
	}
	
	public float getCenterX(){
		return pos.getX() + defaultW/2;
	}
	
	public float getCenterY(){
		return pos.getY() + defaultH/2;
	}
}
