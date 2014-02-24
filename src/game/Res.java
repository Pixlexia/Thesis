package game;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;


public class Res {
	public static Map<RAction, Image> rActionSprites;
	public static Map<String, Image> worldImages;
	public static Map<Operator, String> operators;
	public static Sound hover, click;
	public static Image handImage;
	public static AngelCodeFont futura24, futura36, futura16, futura60, futura72, futura10;
	
	public Res() throws SlickException{
		// Sounds
		hover = new Sound("res/sounds/hover.wav");
		click = new Sound("res/sounds/click.wav");
		
		// Fonts
		String font = "futura24";
		futura24 = new AngelCodeFont("res/fonts/" + font +".fnt", "res/fonts/" + font + "_0.png");
		font = "futura16";
		futura16 = new AngelCodeFont("res/fonts/" + font +".fnt", "res/fonts/" + font + "_0.png");
		font = "futura36";
		futura36 = new AngelCodeFont("res/fonts/" + font +".fnt", "res/fonts/" + font + "_0.png");
		font = "futura60";
		futura60 = new AngelCodeFont("res/fonts/" + font +".fnt", "res/fonts/" + font + "_0.png");
		font = "futura72";
		futura72 = new AngelCodeFont("res/fonts/" + font +".fnt", "res/fonts/" + font + "_0.png");
		font = "futura10";
		futura10 = new AngelCodeFont("res/fonts/" + font +".fnt", "res/fonts/" + font + "_0.png");
		
		// Sprites
		handImage = new Image("res/hand.png");
		rActionSprites = new HashMap<RAction, Image>();
		rActionSprites.put(RAction.moveRight, new Image("res/ractions.png").getSubImage(0, 0, 38, 38));
		rActionSprites.put(RAction.moveLeft, new Image("res/ractions.png").getSubImage(38, 0, 38, 38));
		rActionSprites.put(RAction.interact, new Image("res/ractions.png").getSubImage(38*2, 0, 38, 38));
		rActionSprites.put(RAction.redSlot, new Image("res/ractions.png").getSubImage(38*3, 0, 38, 38));
		rActionSprites.put(RAction.yellowSlot, new Image("res/ractions.png").getSubImage(38*4, 0, 38, 38));
		rActionSprites.put(RAction.blueSlot, new Image("res/ractions.png").getSubImage(38*5, 0, 38, 38));
		
		rActionSprites.put(RAction.equals, new Image("res/ractions.png").getSubImage(0, 38, 38, 38));
		rActionSprites.put(RAction.number, new Image("res/ractions.png").getSubImage(38*1, 38, 38, 38));
		rActionSprites.put(RAction.add, new Image("res/ractions.png").getSubImage(38*2, 38, 38, 38));
		rActionSprites.put(RAction.subtract, new Image("res/ractions.png").getSubImage(38*3, 38, 38, 38));
		rActionSprites.put(RAction.multiply, new Image("res/ractions.png").getSubImage(38*4, 38, 38, 38));
		rActionSprites.put(RAction.divide, new Image("res/ractions.png").getSubImage(38*5, 38, 38, 38));
				
		int i = 0;
		rActionSprites.put(RAction.startIf, new Image("res/ractions.png").getSubImage(38 * i++, 38*2, 38, 38));
		rActionSprites.put(RAction.startElse, new Image("res/ractions.png").getSubImage(38 * i++, 38*2, 38, 38));
		rActionSprites.put(RAction.isEqual, new Image("res/ractions.png").getSubImage(38 * i++, 38*2, 38, 38));
		rActionSprites.put(RAction.isNotEqual, new Image("res/ractions.png").getSubImage(38 * i++, 38*2, 38, 38));
		rActionSprites.put(RAction.isGreaterThan, new Image("res/ractions.png").getSubImage(38 * i++, 38*2, 38, 38));
		rActionSprites.put(RAction.isLessThan, new Image("res/ractions.png").getSubImage(38 * i++, 38*2, 38, 38));		

		i = 0;
		rActionSprites.put(RAction.endIf, new Image("res/ractions.png").getSubImage(38 * i++, 38*3, 38, 38));
		rActionSprites.put(RAction.endElse, new Image("res/ractions.png").getSubImage(38 * i++, 38*3, 38, 38));
		rActionSprites.put(RAction.startLoop, new Image("res/ractions.png").getSubImage(38 * i++, 38*3, 38, 38));
		rActionSprites.put(RAction.endLoop, new Image("res/ractions.png").getSubImage(38 * i++, 38*3, 38, 38));
		rActionSprites.put(RAction.func1, new Image("res/ractions.png").getSubImage(38 * i++, 38*3, 38, 38));
		rActionSprites.put(RAction.func2, new Image("res/ractions.png").getSubImage(38 * i++, 38*3, 38, 38));
		
		// world sprites
		worldImages = new HashMap<String, Image>();
		worldImages.put("tutorial", new Image("res/greenguybig.png"));
		worldImages.put("variables", new Image("res/redguybig.png"));
		worldImages.put("conditions", new Image("res/greenguybig.png"));
		worldImages.put("loops", new Image("res/redguybig.png"));
		worldImages.put("functions", new Image("res/greenguybig.png"));
		
		// arithmetic operators
		operators = new HashMap<Operator, String>();
		operators.put(Operator.ADD, "SUM");
		operators.put(Operator.SUB, "DIFFERENCE");
		operators.put(Operator.MUL, "PRODUCT");
		operators.put(Operator.DIV, "QUOTIENT");
	}
	
	// x = center X
	public static void centerText(AngelCodeFont f, String s, float x, float y){
		f.drawString(x - f.getWidth(s)/2, y, s, new Color(50, 50, 50));
	}

	// with color
	public static void centerText(AngelCodeFont f, String s, float x, float y, Color c){
		f.drawString(x - f.getWidth(s)/2, y, s, c);
	}
	
	public static void updateCursor(GameContainer gc) throws SlickException{
//		if(Button.handCursor){
//			gc.setMouseCursor(handImage, 5, 0);
//		}
//		else{
//			gc.setDefaultMouseCursor();
//		}
	}
}
