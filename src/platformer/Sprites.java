package platformer;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Sprites {
	static Image left, leftHover, right, rightHover, interact, interactHover;
	public Sprites() throws SlickException{
		left = new Image("res/left.png").getSubImage(0, 0, 38, 38);
		leftHover = new Image("res/left.png").getSubImage(38, 0, 38, 38);
		
		right = new Image("res/right.png").getSubImage(0, 0, 38, 38);
		rightHover = new Image("res/right.png").getSubImage(38, 0, 38, 38);
		
		interact = new Image("res/button.png").getSubImage(0, 0, 38, 38);
		interactHover = new Image("res/button.png").getSubImage(38, 0, 38, 38);
	}
}
