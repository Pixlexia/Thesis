package entities;

import game.Game;
import game.Level;
import game.RAction;
import game.Res;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class HelpText {
	public static ArrayList<String> texts;
	public static boolean isAlive, display, close;
	static int index;
	public static int counter;
	public static float textScale;
	
	public static boolean goBig;
	public static float rectScale;
	public static Rectangle r;
	
	static float w = 600, h = 120;
	static float x = (Game.PWIDTH - w)/2, y = (Game.GHEIGHT - h)/2 - 10;
	public static float modX;
	static float modY;
	
	static Image sprite;
	public static int maxChar;
	
	// specific tutorial command images
	public static String tutorialImage;
	
	public HelpText() throws SlickException{
		texts = new ArrayList<String>();
		index = 0;
		isAlive = true;
		counter = 300;
		display = false;
		close = false;
		
		goBig = true;

		textScale = 0;
		rectScale = 0;
		
		r = new Rectangle(0, 0, 0, 0);
		
		sprite = new Image("res/greenguybig.png");
		
		maxChar = 43;
		
		tutorialImage = "";
	}

	// With image
	public static void addHelp(String s, boolean autoFit){
		if(autoFit){
			StringBuilder sb = new StringBuilder(s);
	
			int i = 0;
			while ((i = sb.indexOf(" ", i + maxChar)) != -1) {
			    sb.replace(i, i + 1, "\n");
			}
			texts.add(sb.toString());
		}
		else{
			texts.add(s);
		}
	}
	
	public static void update(int delta, Input input){
		if(display){
			if(index >= texts.size()){
				close = true;
			}
			if(!goBig && input.isMousePressed(0)){
				index++;
				input.clearKeyPressedRecord();
				textScale = 0;
				if(index < texts.size()){
					String t = texts.get(index);
					if(t.contains("<computer>")){
						int cutIndex = texts.get(index).indexOf("<");
						texts.set(index, texts.get(index).substring(0, cutIndex));
						tutorialImage = "computer";
					}
					else if(t.contains("<yellowequalred>")){
						int cutIndex = texts.get(index).indexOf("<");
						texts.set(index, texts.get(index).substring(0, cutIndex));
						tutorialImage = "yellowequalred";
					}
					else if(t.contains("<red equal")){
						int cutIndex = texts.get(index).indexOf("<");
						texts.set(index, texts.get(index).substring(0, cutIndex));
						tutorialImage = "red equal num";
					}
					else if(t.contains("<showslots")){
						int cutIndex = texts.get(index).indexOf("<");
						texts.set(index, texts.get(index).substring(0, cutIndex));
						tutorialImage = "showslots";
					}
					else if(t.contains("<red=computer>")){
						int cutIndex = texts.get(index).indexOf("<");
						texts.set(index, texts.get(index).substring(0, cutIndex));
						tutorialImage = "red=comp";
					}
					else if(t.contains("<redsum>")){
						int cutIndex = texts.get(index).indexOf("<");
						texts.set(index, texts.get(index).substring(0, cutIndex));
						tutorialImage = "redsum";
					}
					else if(t.contains("<bluecomputer>")){
						int cutIndex = texts.get(index).indexOf("<");
						texts.set(index, texts.get(index).substring(0, cutIndex));
						tutorialImage = "bluecomp";
					}
					else if(t.contains("<ifcomputer5>")){
						int cutIndex = texts.get(index).indexOf("<");
						texts.set(index, texts.get(index).substring(0, cutIndex));
						tutorialImage = "ifcompgreater5";
					}
					else if(t.contains("<equalto10>")){
						int cutIndex = texts.get(index).indexOf("<");
						texts.set(index, texts.get(index).substring(0, cutIndex));
						tutorialImage = "equalto10";
					}
					else if(t.contains("<elseexample>")){
						int cutIndex = texts.get(index).indexOf("<");
						texts.set(index, texts.get(index).substring(0, cutIndex));
						tutorialImage = "elseexample";
					}
					else if(t.contains("<loopex>")){
						int cutIndex = texts.get(index).indexOf("<");
						texts.set(index, texts.get(index).substring(0, cutIndex));
						tutorialImage = "loopex";
					}
					else if(t.contains("<funcmoveright>")){
						int cutIndex = texts.get(index).indexOf("<");
						texts.set(index, texts.get(index).substring(0, cutIndex));
						tutorialImage = "funcmoveright";
					}
					else if(t.contains("<funcsum>")){
						int cutIndex = texts.get(index).indexOf("<");
						texts.set(index, texts.get(index).substring(0, cutIndex));
						tutorialImage = "funcsum";
					}
					else{
						tutorialImage = "";
					}
				}
			}
		}
		
		if(counter > 0){
			counter-=delta;
		}
		else{
			display = true;
		}
	}
	
	public static void render(Graphics g){
//		float enlargeSize = 10;
		float inflateRate = 3;
		
		
		if(display){
			if(Math.round(100 * textScale) <= 99){
				textScale = textScale + (1-textScale)/inflateRate;
			}
			else{
				textScale = 1;
			}
			
			if(goBig){
				r.setWidth(r.getWidth() + (w - r.getWidth())/inflateRate);
				r.setHeight(r.getHeight() + (h - r.getHeight())/inflateRate);
				
				if(Math.round(r.getWidth()) >= Math.round(w)){
					goBig = false;
				}
			}
			
			if(close){
				inflateRate = 3;
				r.setWidth(r.getWidth() + (0 - r.getWidth())/inflateRate);
				r.setHeight(r.getHeight() + (0 - r.getHeight())/inflateRate);
				
				if(Math.round(r.getWidth()) <= 5){
					isAlive = false;
					display = false;
				}
			}
//			}
//			float enlargeSize = 10, inflateRate = 20;
//			if(goBig){
//				r.setWidth(r.getWidth() + (w - r.getWidth() + enlargeSize)/inflateRate);
//				r.setHeight(r.getHeight() + (h - r.getHeight() + enlargeSize*2)/inflateRate);
//				
//				if(r.getWidth() > w + enlargeSize){
//					goBig = false;
//				}
//			}
//			else{
//				if(r.getWidth() >= w){					
//					r.setWidth(r.getWidth() - (r.getWidth() - w)/inflateRate);
//					r.setHeight(r.getHeight() - (r.getHeight() - h)/inflateRate);
//				}
//			}
			
			r.setX(x + w/2 - r.getWidth()/2);
			r.setY(y + h/2 - r.getHeight()/2);
			
			g.setColor(new Color(255, 255, 255, 0.6f));
			g.fillRect(0, 0, Game.GWIDTH, Game.GHEIGHT);
			
			g.setLineWidth(2);
			g.setColor(new Color(255, 255, 255));
			g.fill(r);
			g.setColor(new Color(70, 70, 70));
			g.drawLine(r.getX(), r.getY(), r.getWidth()+r.getX(), r.getY()); // upleft to right
			g.drawLine(r.getX(), r.getY()+r.getHeight(), r.getWidth()+r.getX(), r.getY()+r.getHeight()); // downleft to right
			g.drawLine(r.getX(), r.getY(), r.getX(), r.getY()+r.getHeight()); // upleft to down
			g.drawLine(r.getWidth()+r.getX(), r.getY(), r.getWidth()+r.getX(), r.getY()+r.getHeight()); // upright to down
			g.setLineWidth(1);
			
			float defaultX = x+20, defaultY = y+15;
			
			// draw green guy
			if(Level.showGreenguy){
				g.drawImage(sprite, x/textScale + 12, y+10);
			}
			
			// Draw text inside box
			if(index < texts.size()){
				Res.futura16.drawString(x+w - 8 - Res.futura16.getWidth("CLICK ANYWHERE TO CONTINUE"), y+h - 20, "CLICK ANYWHERE TO CONTINUE", new Color(200, 200, 200));			

				g.scale(textScale, textScale);
				String t = texts.get(index).toUpperCase();
				float drawX = defaultX + Res.futura24.getWidth(t)/2 - (Res.futura24.getWidth(t) * textScale)/2;
				float drawY = defaultY + Res.futura24.getHeight(t)/2 - (Res.futura24.getHeight(t) * textScale)/2;
				
				Res.futura24.drawString((int) drawX / textScale + modX, (int) drawY / textScale, t, new Color(50, 50, 50));
				
				float x;
				float y;
				
				switch(tutorialImage){
				case "red equal num":
					y = 25;
					x = 265;
					Res.rActionSprites.get(RAction.redSlot).draw(drawX/textScale + modX + x, drawY/textScale + y);
					Res.rActionSprites.get(RAction.equals).draw(drawX/textScale + modX + x + 40, drawY/textScale + y);
					Res.rActionSprites.get(RAction.number).draw(drawX/textScale + modX + x + 70, drawY/textScale + y);
					break;
					
				case "computer":
					x = 340;
					y = 30;
					Res.rActionSprites.get(RAction.interact).draw((int) drawX/textScale + modX + x, drawY/textScale + y);
					break;
					
				case "showslots":
					x = 240;
					y = 20;
					Res.rActionSprites.get(RAction.redSlot).draw(drawX/textScale + modX + x, drawY/textScale + y);
					Res.rActionSprites.get(RAction.yellowSlot).draw(drawX/textScale + modX + x+40, drawY/textScale + y);
					Res.rActionSprites.get(RAction.blueSlot).draw(drawX/textScale + modX + x+80, drawY/textScale + y);
					break;
				
				case "yellowequalred":
					x = 60;
					y = 45;
					Res.rActionSprites.get(RAction.yellowSlot).draw(drawX/textScale + modX + x, drawY/textScale + y);
					Res.rActionSprites.get(RAction.equals).draw(drawX/textScale + modX + x + 40, drawY/textScale + y);
					Res.rActionSprites.get(RAction.redSlot).draw(drawX/textScale + modX + x + 80, drawY/textScale + y);
					break;
					
				case "red=comp":
					x = 170;
					y = 22;
					Res.rActionSprites.get(RAction.redSlot).draw(drawX/textScale + modX + x, drawY/textScale + y);
					Res.rActionSprites.get(RAction.equals).draw(drawX/textScale + modX + x + 40, drawY/textScale + y);
					Res.rActionSprites.get(RAction.interact).draw(drawX/textScale + modX + x + 80, drawY/textScale + y);
					break;
					
				case "redsum":
					x = 280;
					y = 22;
					Res.rActionSprites.get(RAction.redSlot).draw(drawX/textScale + modX + x, drawY/textScale + y);
					Res.rActionSprites.get(RAction.equals).draw(drawX/textScale + modX + x + 40, drawY/textScale + y);
					Res.rActionSprites.get(RAction.number).draw(drawX/textScale + modX + x + 70, drawY/textScale + y);
					Res.rActionSprites.get(RAction.add).draw(drawX/textScale + modX + x + 100, drawY/textScale + y);
					Res.rActionSprites.get(RAction.number).draw(drawX/textScale + modX + x + 130, drawY/textScale + y);
					break;					
					
				case "bluecomp":
					x = 130;
					y = 22;
					Res.rActionSprites.get(RAction.yellowSlot).draw(drawX/textScale + modX + x, drawY/textScale + y);
					Res.rActionSprites.get(RAction.equals).draw(drawX/textScale + modX + x + 40, drawY/textScale + y);
					Res.rActionSprites.get(RAction.interact).draw(drawX/textScale + modX + x + 80, drawY/textScale + y);
					Res.rActionSprites.get(RAction.divide).draw(drawX/textScale + modX + x + 120, drawY/textScale + y);
					Res.futura36.drawString(drawX/textScale + modX + x + 160, drawY/textScale + y - 2, "5", Color.black);
					break;
					
				case "ifcompgreater5":
					x = 5;
					y = 47;
					Res.rActionSprites.get(RAction.startIf).draw((int) drawX/textScale + modX + x, (int) drawY/textScale + y);
					Res.rActionSprites.get(RAction.interact).draw((int) drawX/textScale + modX + x + 40,(int)  drawY/textScale + y);
					Res.rActionSprites.get(RAction.isGreaterThan).draw((int) drawX/textScale + modX + x + 80,(int)  drawY/textScale + y);
					Res.futura36.drawString((int) drawX/textScale + modX + x + 120,(int)  drawY/textScale + y, "5", Color.black);
					Res.rActionSprites.get(RAction.moveRight).draw((int) drawX/textScale + modX + x + 150,(int)  drawY/textScale + y);
					Res.rActionSprites.get(RAction.moveRight).draw((int) drawX/textScale + modX + x + 200, (int) drawY/textScale + y);
					Res.rActionSprites.get(RAction.endIf).draw((int)  drawX/textScale + modX + x + 240, (int) drawY/textScale + y);
					break;
					
				case "equalto10":
					x = 105;
					y = 17;
					Res.rActionSprites.get(RAction.isEqual).draw(drawX/textScale + modX + x, drawY/textScale + y);
					break;
					
				case "elseexample":
					x = 50;
					y = 46;
					Res.rActionSprites.get(RAction.startIf).draw(drawX/textScale + modX + x, drawY/textScale + y);
					Res.rActionSprites.get(RAction.redSlot).draw((int) drawX/textScale + modX + x + 40, drawY/textScale + y);
					Res.rActionSprites.get(RAction.isGreaterThan).draw(drawX/textScale + modX + x + 80, drawY/textScale + y);
					Res.futura36.drawString(drawX/textScale + modX + x + 120, drawY/textScale + y, "5", Color.black);
					Res.rActionSprites.get(RAction.moveRight).draw(drawX/textScale + modX + x + 150, drawY/textScale + y);
					Res.rActionSprites.get(RAction.endIf).draw(drawX/textScale + modX + x + 190, drawY/textScale + y);
					Res.rActionSprites.get(RAction.startElse).draw(drawX/textScale + modX + x + 235, drawY/textScale + y);
					Res.rActionSprites.get(RAction.moveLeft).draw(drawX/textScale + modX + x + 280, drawY/textScale + y);
					Res.rActionSprites.get(RAction.endElse).draw(drawX/textScale + modX + x + 330, drawY/textScale + y);
					break;
					
				case "loopex":
					x = 120;
					y = 48;
					Res.rActionSprites.get(RAction.startLoop).draw(drawX/textScale + modX + x, drawY/textScale + y);
					Res.rActionSprites.get(RAction.moveRight).draw((int) drawX/textScale + modX + x + 50, drawY/textScale + y);
					Res.rActionSprites.get(RAction.endLoop).draw(drawX/textScale + modX + x + 90, drawY/textScale + y);
					break;
					
				case "funcmoveright":
					x = 315;
					y = -5;
					Res.rActionSprites.get(RAction.moveRight).draw(drawX/textScale + modX + x, drawY/textScale + y);
					break;
					
				case "funcsum":
					x = 185;
					y = 45;
					Res.rActionSprites.get(RAction.redSlot).draw(drawX/textScale + modX + x, drawY/textScale + y);
					Res.rActionSprites.get(RAction.equals).draw(drawX/textScale + modX + x + 40, drawY/textScale + y);
					Res.rActionSprites.get(RAction.redSlot).draw(drawX/textScale + modX + x + 80, drawY/textScale + y);
					Res.rActionSprites.get(RAction.add).draw(drawX/textScale + modX + x + 120, drawY/textScale + y);
					Res.rActionSprites.get(RAction.interact).draw(drawX/textScale + modX + x + 160, drawY/textScale + y);
					break;
				}
			}
		}
	}
}
