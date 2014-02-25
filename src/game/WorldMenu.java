package game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.BlobbyTransition;
import org.newdawn.slick.state.transition.EmptyTransition;

import entities.Button;

public class WorldMenu extends BasicGameState{
	
	public static int worldsUnlocked;
	
	public static ConceptWorld[] worlds;
	static int index;

	private int imageXpos;
	public static boolean goToNext;
	
	public int gridx = 0, gridy = 0;

	Button enter, right, left;
	Image rightArrow, leftArrow, lock = new Image("res/lock.png");
	
	public class ConceptWorld{
		String title;
		Image img;
		
		int index;
		
		private float xpos, targetx;
		
		public ConceptWorld(int i) throws SlickException{		
//			img = new Image("res/greenguybig.png");			
			new Res();
			
			index = i;
						
			switch(i){
			case 0:
				title = "Tutorial";	
				img = Res.worldImages.get("tutorial");
				
				xpos = Game.GWIDTH/2 - img.getWidth()/2;
				targetx = xpos;
				break;
			case 1:
				title = "Variables";
				img = Res.worldImages.get("variables");
				
				xpos = Game.GWIDTH;
				targetx = xpos;
				
				break;
			case 2:
				title = "Conditional Branching";
				img = Res.worldImages.get("conditions");
				xpos = Game.GWIDTH + Game.GWIDTH/2 + img.getWidth()/2;
				targetx = xpos;
				break;
			case 3:
				title = "Loops";
				img = Res.worldImages.get("loops");
				xpos = Game.GWIDTH + Game.GWIDTH + img.getWidth();
				targetx = xpos;
				break;
			case 4:
				title = "Functions";
				img = Res.worldImages.get("functions");
				xpos = Game.GWIDTH + Game.GWIDTH + Game.GWIDTH/2 + img.getWidth() + img.getWidth()/2;
				targetx = xpos;
				break;
			default: break;
			}
		}
		
		public void gotoLeft(){
			targetx -= Game.GWIDTH/2 + img.getWidth()/2;
		}
		
		public void gotoRight(){
			targetx += Game.GWIDTH/2 + img.getWidth()/2;
		}
	}
	
	public WorldMenu() throws SlickException{
		index = 0;
		worlds = new ConceptWorld[5];
		worldsUnlocked = 10;
		
		for(int i = 0 ; i < worlds.length; i++){
			worlds[i] = new ConceptWorld(i);
		}
		
		imageXpos = Game.GWIDTH/2 - worlds[0].img.getWidth()/2;
	}
	
	public static void unlockNewWorld(){
		worldsUnlocked++;
		goToNext = true; // used to animate to next world when unlocked
	}

	@Override
	public void init(GameContainer gc, final StateBasedGame sbg) throws SlickException {
		goToNext = false;
		enter = new Button(Game.GWIDTH/2 - 300/2, 490, 300, 50){
			@Override
			public void onClick(){
				if(index <= worldsUnlocked){					
					Play.world = index;
					Play.initWorld();
					sbg.enterState(2);
				}
			}
			
			@Override
			public void isHovered(){
				inflate();
			}
		};

		enter.color = new Color(220, 220, 220);
		enter.hoverColor = new Color(220, 220, 220);
		enter.inflateRate = 2;
		
		rightArrow = new Image("res/arrows.png");
		leftArrow = rightArrow.getFlippedCopy(true, false);
		
		int buttonPadding = 180;
		right = new Button(Game.GWIDTH - buttonPadding, 240, rightArrow.getWidth(), rightArrow.getHeight()){			
			@Override
			public void onClick(){
				inflate();
				clickRight();
			}

			@Override
			public void isHovered(){
			}
		};
		
		left = new Button(buttonPadding - right.getBounds().getWidth(), 240, rightArrow.getWidth(), rightArrow.getHeight()){
			@Override
			public void onClick(){
				inflate();
				clickLeft();
			}
			
			@Override
			public void isHovered(){
			}
		};
		
		right.inflateRate = 4;
		left.inflateRate = right.inflateRate;
		right.enlargeSize = 10;
		left.enlargeSize = right.enlargeSize;
		
		int boxColor = 220;
		right.color = new Color(boxColor, boxColor, boxColor);
		left.color = right.color;		
		
		boxColor = 210;
		right.hoverColor = new Color(boxColor, boxColor, boxColor);
		left.hoverColor = new Color(boxColor, boxColor, boxColor);
		
		left.borderRadius = 5;
		right.borderRadius = left.borderRadius;
		enter.borderRadius = left.borderRadius;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// grid
		int boxSize = 50;
		
		gridx--;
		gridy++;
		
		gridx %= boxSize;
		gridy %= boxSize;
		
		g.setLineWidth(1);
		
		g.setColor(new Color(235, 235, 235));
		for(int i = 0 ; i < Game.GHEIGHT; i++){
			g.drawLine(gridx + 0, gridy + i*boxSize, boxSize + gridx + Game.GWIDTH, gridy + i*boxSize);
		}
		for(int i = 0 ; i < Game.GWIDTH; i++){
			g.drawLine(gridx + i*boxSize, gridy + 0 - boxSize, gridx + i*boxSize, boxSize + gridy + Game.GWIDTH);
		}
		
		
		Res.centerText(Res.futura24, "WORLD " + index, Game.GWIDTH/2, 40, new Color(200, 200, 200));
		Res.centerText(Res.futura36, worlds[index].title.toUpperCase(), Game.GWIDTH/2, 60);

		enter.render(g);
		Res.centerText(Res.futura24, "ENTER", Game.GWIDTH/2, enter.pos.getY() + 14, new Color(50, 50, 50));
		
		// image
		
		Image img1;
		
		if(index <= worldsUnlocked){
			img1 = worlds[index].img;
		}
		else{
			img1 = lock;
		}
		
		for(ConceptWorld cw : worlds){
			g.drawImage(cw.img, cw.xpos, 240);
		}

//		worlds[index].worldImage.draw(xpos1, 240);
//		lock.draw(Game.GWIDTH/2 - lock.getWidth()/2, 240);
		
		if(index != worlds.length - 1){
			right.render(g);
			rightArrow.draw(right.pos.getX(), right.pos.getY());			
		}
		
		if(index != 0){
			left.render(g);
			leftArrow.draw(left.pos.getX(), left.pos.getY());			
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		
		if(input.isKeyPressed(Input.KEY_0)){
			Play.initLevel();
			sbg.enterState(2);
		}
		else if(input.isKeyPressed(Input.KEY_1)){
			
		}
		else if(input.isKeyPressed(Input.KEY_2)){
			
		}
		else if(input.isKeyPressed(Input.KEY_3)){
			
		}
		else if(input.isKeyPressed(Input.KEY_4)){
			
		}
		
		enter.update(delta, gc.getInput().getMouseX(), gc.getInput().getMouseY(), gc.getInput());
		
		if(index != worlds.length - 1)
			right.update(delta, gc.getInput().getMouseX(), gc.getInput().getMouseY(), gc.getInput());
		
		if(index != 0)
			left.update(delta, gc.getInput().getMouseX(), gc.getInput().getMouseY(), gc.getInput());
		
		for(ConceptWorld cw : worlds){
			if(Math.round(cw.xpos) != Math.round(cw.targetx)){
				cw.xpos += (cw.targetx - cw.xpos)/10;
//				System.out.println(cw.title + " " + cw.xpos);
			}
		}
		
	}
	
	public static void clickRight(){
		index = (index + 1) %worlds.length;
		
		for(int i = 0 ; i < worlds.length ; i++){
			worlds[i].gotoLeft();
		}
	}
	
	public static void clickLeft(){
		if(index == 0){
			index = worlds.length-1;
		}
		else
			index--;
		
		for(int i = 0 ; i < worlds.length ; i++){
			worlds[i].gotoRight();
		}
	}

	@Override
	public int getID() {
		
		return 1;
	}

}
