package platformer;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Sprites {
	public static Image left;
	public static Image leftHover;
	public static Image right;
	public static Image rightHover;
	public static Image interact;
	public static Image interactHover;
	
	public Sprites() throws SlickException{
		left = new Image("res/left.png").getSubImage(0, 0, 38, 38);
		leftHover = new Image("res/left.png").getSubImage(38, 0, 38, 38);
		
		right = new Image("res/right.png").getSubImage(0, 0, 38, 38);
		rightHover = new Image("res/right.png").getSubImage(38, 0, 38, 38);
		
		interact = new Image("res/button.png").getSubImage(0, 0, 38, 38);
		interactHover = new Image("res/button.png").getSubImage(38, 0, 38, 38);
	}
}
