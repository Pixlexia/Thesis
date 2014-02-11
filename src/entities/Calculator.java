package entities;

import game.Game;
import game.Res;
import game.Sidebar;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Calculator {
	public static String wherePutNum;
	
	public static CalcButton[] buttons;
	public static Button calcClear;
	public static Button calcOk;
	public static String currentValue;
	
	private static int calcTranslateX = -100;
	private static int calcTranslateY = 150;
	
	private static Rectangle bg, screen;
	
	public Calculator() throws SlickException{
		float bSize = Sidebar.bSize;
		
		currentValue = "0";
		
		// calculator buttons
		buttons = new CalcButton[10];
		for(int i = 0 ; i < 10; i++){
			buttons[i] = new CalcButton(i, Sidebar.padding + i%3 * bSize, Sidebar.padding + i/3 * bSize, bSize, bSize);
		}
		
		float x, y;
		
		x = Sidebar.padding;
		y = Sidebar.padding * 2 + bSize;
		buttons[7].pos.setX(x);
		buttons[7].pos.setY(y);
		buttons[8].pos.setX(x*2 + bSize);
		buttons[8].pos.setY(y);
		buttons[9].pos.setX(x*3 + bSize * 2);
		buttons[9].pos.setY(y);

		x = Sidebar.padding;
		y = Sidebar.padding * 3 + bSize * 2;
		buttons[4].pos.setX(x);
		buttons[4].pos.setY(y);
		buttons[5].pos.setX(x*2 + bSize);
		buttons[5].pos.setY(y);
		buttons[6].pos.setX(x*3 + bSize * 2);
		buttons[6].pos.setY(y);
		
		x = Sidebar.padding;
		y = Sidebar.padding * 4 + bSize * 3;
		buttons[3].pos.setX(x);
		buttons[3].pos.setY(y);
		buttons[2].pos.setX(x*2 + bSize);
		buttons[2].pos.setY(y);
		buttons[1].pos.setX(x*3 + bSize * 2);
		buttons[1].pos.setY(y);
		
		x = Sidebar.padding;
		y = Sidebar.padding * 5 + bSize * 4;
		buttons[0].pos.setX(x);
		buttons[0].pos.setY(y);
		
		calcOk = new Button(x*3 + bSize * 2, y, bSize, bSize){
			@Override
			public void onClick(){
				Sidebar.showCalculator = false;
				if(wherePutNum == "number")
					Robot.numbers.put(Sidebar.programButtons.size()-1, Integer.parseInt(currentValue));
				else if(wherePutNum == "loop"){
					Robot.loops.put(Sidebar.programButtons.size()-1, new Loop(Integer.parseInt(currentValue), Sidebar.programButtons.size()-1));
					Robot.iterateCounts.put(Sidebar.programButtons.size()-1, Integer.parseInt(currentValue));
				}
				
				currentValue = "0";
			}
		};
		
		calcClear = new Button(x*2 + bSize, y, bSize, bSize){
			@Override
			public void onClick(){
				currentValue = "0";
			}
		};
		
		calcOk.defaultOpacity = 0.7f;
		calcClear.defaultOpacity = 0.7f;
		calcOk.color = new Color(129, 231, 115);
		calcClear.color = new Color(255, 52, 52);
		
		bg = new Rectangle(0, 0, bSize * 3 + Sidebar.padding * 4, bSize * 5 + Sidebar.padding * 6);
		screen = new Rectangle(Sidebar.padding, Sidebar.padding, bSize*3 + Sidebar.padding*2, bSize);
	}
	
	public static void update(int delta, Input input){

		for(int i = 0 ; i < buttons.length; i++){
			buttons[i].update(delta, input.getMouseX() - Sidebar.translateValue - calcTranslateX, input.getMouseY() - calcTranslateY, input);
		}
		calcOk.update(delta, input.getMouseX() - Sidebar.translateValue - calcTranslateX, input.getMouseY() - calcTranslateY, input);
		calcClear.update(delta, input.getMouseX() - Sidebar.translateValue - calcTranslateX, input.getMouseY() - calcTranslateY, input);
	}
	
	public static void render(Graphics g){
		calcTranslateX = -170;
		calcTranslateY = 105;
		g.translate(calcTranslateX, calcTranslateY);
		
		
		g.setColor(new Color(225, 225, 215, 220));
		g.fill(bg);
		
		g.setColor(Color.white);
		g.fill(screen);
		
		for(int i = 0 ; i < buttons.length; i++){
			buttons[i].render(g);
		}
		
		Res.futura36.drawString(Sidebar.padding + (Sidebar.bSize * 3 + Sidebar.padding*2)/2 - Res.futura36.getWidth(currentValue+"")/2, Sidebar.padding + 1, currentValue, Color.black);
		
		calcOk.render(g);
		Res.futura24.drawString(calcOk.pos.getX() + 5, calcOk.pos.getY() + 8, "OK");
		calcClear.render(g);
		Res.futura24.drawString(calcClear.pos.getX() + 13, calcClear.pos.getY() + 8, "C");
		
		g.setLineWidth(1);
		
		g.translate(-calcTranslateX, -calcTranslateY);
	}

}
