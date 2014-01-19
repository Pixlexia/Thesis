package game;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Sprites {
	public static Map<RAction, Image> rActionSprites;
	
	public Sprites() throws SlickException{
		rActionSprites = new HashMap<RAction, Image>();
		rActionSprites.put(RAction.moveRight, new Image("res/ractions.png").getSubImage(0, 0, 38, 38));
		rActionSprites.put(RAction.moveLeft, new Image("res/ractions.png").getSubImage(38, 0, 38, 38));
		rActionSprites.put(RAction.interact, new Image("res/ractions.png").getSubImage(38*2, 0, 38, 38));
		rActionSprites.put(RAction.redSlot, new Image("res/ractions.png").getSubImage(38*3, 0, 38, 38));
		rActionSprites.put(RAction.blueSlot, new Image("res/ractions.png").getSubImage(38*4, 0, 38, 38));
		rActionSprites.put(RAction.yellowSlot, new Image("res/ractions.png").getSubImage(38*5, 0, 38, 38));
		
		rActionSprites.put(RAction.add, new Image("res/ractions.png").getSubImage(0, 38, 38, 38));
		rActionSprites.put(RAction.subtract, new Image("res/ractions.png").getSubImage(38*1, 38, 38, 38));
		rActionSprites.put(RAction.multiply, new Image("res/ractions.png").getSubImage(38*2, 38, 38, 38));
		rActionSprites.put(RAction.divide, new Image("res/ractions.png").getSubImage(38*3, 38, 38, 38));
		rActionSprites.put(RAction.equals, new Image("res/ractions.png").getSubImage(38*4, 38, 38, 38));
		rActionSprites.put(RAction.modulo, new Image("res/ractions.png").getSubImage(38*5, 38, 38, 38));
				
		int i = 0;
		rActionSprites.put(RAction.startIf, new Image("res/ractions.png").getSubImage(38 * i++, 38*2, 38, 38));
		rActionSprites.put(RAction.startElse, new Image("res/ractions.png").getSubImage(38 * i++, 38*2, 38, 38));
		rActionSprites.put(RAction.isEqual, new Image("res/ractions.png").getSubImage(38 * i++, 38*2, 38, 38));
		rActionSprites.put(RAction.isNotEqual, new Image("res/ractions.png").getSubImage(38 * i++, 38*2, 38, 38));
		rActionSprites.put(RAction.isGreaterThan, new Image("res/ractions.png").getSubImage(38 * i++, 38*2, 38, 38));
		rActionSprites.put(RAction.isLessThan, new Image("res/ractions.png").getSubImage(38 * i++, 38*2, 38, 38));		

		i = 0;
		rActionSprites.put(RAction.startLoop, new Image("res/ractions.png").getSubImage(38 * i++, 38*3, 38, 38));
		rActionSprites.put(RAction.func1, new Image("res/ractions.png").getSubImage(38 * i++, 38*3, 38, 38));
		rActionSprites.put(RAction.func2, new Image("res/ractions.png").getSubImage(38 * i++, 38*3, 38, 38));
		rActionSprites.put(RAction.endIf, new Image("res/ractions.png").getSubImage(38 * i++, 38*3, 38, 38));
		rActionSprites.put(RAction.endElse, new Image("res/ractions.png").getSubImage(38 * i++, 38*3, 38, 38));
		rActionSprites.put(RAction.endLoop, new Image("res/ractions.png").getSubImage(38 * i++, 38*3, 38, 38));
	}
}
