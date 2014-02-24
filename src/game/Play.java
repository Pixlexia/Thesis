package game;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.CollisionListener;
import net.phys2d.raw.World;
import net.phys2d.raw.shapes.Box;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import worldlevels.World1;
import entities.Button;
import entities.Function;
import entities.GameText;
import entities.HelpText;
import entities.PButton;
import entities.Player;
import entities.Robot;

public class Play extends BasicGameState implements CollisionListener{
	
	public static Player player;
	public static Robot robot;
	
	public static World physWorld;
	public static int level, world;
	
	public static boolean showGrid;
	public static Image restart;
	public static Button bRestart, toggleFx1, toggleFx2;
	
	static CollisionListener cl;
	
	public static ArrayList<GameText> gTexts;
	
	public static boolean worldWin;
	public static boolean tutorialDone;
	public static boolean levelWin;
	public static boolean gamePaused;
	
	// pause elements
	public static boolean quit, gotoworld;
	public static Rectangle pauseBg;
	public static Rectangle pauseSelectRect;
	public static Button resume, gotoworldselect, quitgame;
	
	// DDA factors
	public static int ddaTime, ddaCommands, ddaRetries, ddaErrors, ddaReread;
	public static int timer; // not actual dda, just timer
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		new Res();
		new User();
		new World1();
		
		level = 1;
		world = 0;
		
		initLevel();
		cl = this;
		physWorld.addListener(cl);
		
		showGrid = true;
		restart = new Image("res/restart.png");
		restart.setAlpha(0.5f);
		bRestart = new Button(10, 10, restart.getWidth(), restart.getHeight()){
			@Override
			public void onClick(){
				inflate();
				try {
					restartLevel();
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void isHovered(){
				
			}
		};
		bRestart.borderRadius = 8;
		bRestart.color = new Color(230, 230, 230);
		bRestart.hoverColor = new Color(220, 220, 220);
		bRestart.defaultOpacity = 0.8f;
		
		// toggle fx1
		toggleFx1 = new Button(Game.PWIDTH - 10 - restart.getWidth(), 10, restart.getWidth(), restart.getHeight()){
			@Override
			public void onClick(){
				inflate();
				Sidebar.activeFunction = Sidebar.F1;
				Sidebar.fui1.toggleDisplay();
			}
			
			@Override
			public void isHovered(){
				
			}
		};
		toggleFx1.borderRadius = 8;
		toggleFx1.color = new Color(180, 180, 180);
		toggleFx1.defaultOpacity = 0.2f;
		toggleFx1.hoverOpacity = 0.3f;
		
		// toggle fx2
		toggleFx2 = new Button(Game.PWIDTH - 10 - restart.getWidth(), restart.getHeight() + 20, restart.getWidth(), restart.getHeight()){
			@Override
			public void onClick(){
				inflate();
				Sidebar.activeFunction = Sidebar.F2;
				Sidebar.fui2.toggleDisplay();
			}
			
			@Override
			public void isHovered(){
				
			}
		};
		toggleFx2.borderRadius = 8;
		toggleFx2.color = new Color(180, 180, 180);
		toggleFx2.defaultOpacity = 0.2f;
		toggleFx2.hoverOpacity = 0.3f;
		
		gc.setMouseCursor(Res.handImage, 5, 0);
		
		worldWin = false;
		
		// pause
		gamePaused = false;
		
		int w = 300, h = 260;
		pauseBg = new Rectangle(Game.GWIDTH/2 - w/2, Game.GHEIGHT/2 - h/2, w, h);
		
		resume = new Button(Game.GWIDTH/2 - Res.futura24.getWidth("RESUME")/2 - 10, pauseBg.getY() + 80, Res.futura24.getWidth("RESUME") + 20, Res.futura24.getHeight("RESUME") + 10){
			@Override
			public void onClick(){
				gamePaused = !gamePaused;
			}
			
			@Override
			public void isHovered(){
				inflate();
			}
		};
		
		gotoworldselect = new Button(Game.GWIDTH/2 - Res.futura24.getWidth("GO TO WORLD SELECT")/2 - 10, pauseBg.getY() + 140, Res.futura24.getWidth("GO TO WORLD SELECT") + 20, Res.futura24.getHeight("GO TO WORLD SELECT") + 10){
			@Override
			public void onClick(){
				gotoworld = true;
			}
			
			@Override
			public void isHovered(){
				inflate();
			}
		};
		
		quitgame = new Button(Game.GWIDTH/2 - Res.futura24.getWidth("QUIT GAME")/2 - 10, pauseBg.getY() + 200, Res.futura24.getWidth("QUIT GAME") + 20, Res.futura24.getHeight("QUIT GAME") + 10){
			@Override
			public void onClick(){
				quit = true;
			}
			
			@Override
			public void isHovered(){
				inflate();
			}
		};
		
		resume.color = new Color(240, 240, 240);
		resume.hoverColor = new Color(220, 220, 220);
		resume.defaultOpacity = 0;
		resume.hoverOpacity = 1;
		
		gotoworldselect.color = resume.color;
		gotoworldselect.hoverColor = resume.hoverColor;
		gotoworldselect.defaultOpacity = resume.defaultOpacity;
		gotoworldselect.hoverOpacity = resume.hoverOpacity;
		
		quitgame.color = resume.color;
		quitgame.hoverColor = resume.hoverColor;
		quitgame.defaultOpacity = resume.defaultOpacity;
		quitgame.hoverOpacity = resume.hoverOpacity;
	}
	
	public static void restartLevel() throws SlickException{
		ddaRetries++;
		
		Robot.doneRun = false;
		Robot.isRunning = false;
		
		Robot.inventory[0] = 0;
		Robot.inventory[1] = 0;
		Robot.inventory[2] = 0;
		
		robot.body.setPosition(Level.rStart.getX() - Level.map.getTileWidth()/2, Level.rStart.getY());
		player.body.setPosition(Level.pStart.getX(), Level.pStart.getY());
		
		for(int i = 0 ; i < Sidebar.programButtons.size(); i++){
			PButton pb = Sidebar.programButtons.get(i);
			pb.colorRed = false;
			pb.colorGreen = false;
			
			if(pb.type == RAction.startLoop){
				Robot.loops.get(i).reset();
			}
		}
		
		Robot.functions = new Stack<Function>();
	}
	
	public static void initLevel() throws SlickException{
		if(Robot.loops != null){
			System.out.println(Robot.loops.size());
		}
		Robot.doneRun = false;
		levelWin = false;
		physWorld = new World(new Vector2f(0.0f, 1000), 100);
		new Level();
		
		robot = new Robot(Level.rStart.getX(), Level.rStart.getY());
		player = new Player(Level.pStart.getX(), Level.pStart.getY());

		physWorld.add(player.body);
		physWorld.add(robot.body);
		gTexts = new ArrayList<GameText>();
		
		new Sidebar();
		
		// reset DDAs
		ddaCommands = 0;
		ddaErrors = 0;
		ddaReread = 0;
		ddaRetries = 0;
		ddaTime = 0;
		timer = 0;
	}
	
	public static void initWorld(){
		gamePaused = false;
		gotoworld = false;
		
		level = 1;
		
		if(world == 0){
			User.doneTutorial[world] = false;
		}
		
		try {
			initLevel();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setBackground(Color.white);
		
		// translate -----------
		g.translate(Player.offsetX - Player.camX, Player.offsetY);

		// map
		Level.map.render(0, 0, 0); // layer 0 only (layer 1 is invi layer)
		
		robot.render(g);
		
		// Draw grid
		g.setClip(0, 0, Game.PWIDTH, Game.GHEIGHT);
		if(showGrid){
			int boxSize = 40;
			g.setColor(new Color(0, 0, 0, 0.05f));
			for(int i = 0 ; i < Level.map.getHeight() * Level.map.getTileWidth(); i++){
				g.drawLine(0, i*boxSize, Level.map.getWidth() * Game.TS, i*boxSize);
			}
			for(int i = 0 ; i < Level.map.getWidth() * Level.map.getTileWidth(); i++){
				g.drawLine(i*boxSize, 0, i*boxSize, Level.map.getHeight() * Level.map.getTileWidth());
			}
			g.setColor(Color.black);
		}
		g.clearClip();
		
		// end translate -------------
		g.translate(-Player.offsetX, -Player.offsetY);

		player.render(g);
		Sidebar.render(g);
		
		for(GameText gt : gTexts){
			gt.render(g);
		}
		
		// level, world
		g.setColor(new Color(0, 0, 0, 255));
		g.setAntiAlias(false);
		Res.centerText(Res.futura10, "WORLD " + Play.world, (int) Game.PWIDTH/2, (int) 5, new Color(170, 170, 170));
		Res.centerText(Res.futura16, "LEVEL " + Play.level, (int) Game.PWIDTH/2, (int) 15, new Color(50, 50, 50));
		
		// buttons
		bRestart.render(g);
		restart.draw(bRestart.pos.getX(), bRestart.pos.getY());
		
		// toggle functions buttons
		if(Sidebar.fui1 != null && Sidebar.fui1.maxCommands > 0){
			toggleFx1.render(g);
			Res.futura16.drawString((int) (toggleFx1.pos.getX() + toggleFx1.defaultW/2 - Res.futura16.getWidth("F(x)1")/2), (int) (toggleFx1.pos.getY() + 9), "F(x)1", new Color(160, 160, 140));
		}
		if(Sidebar.fui1 != null && Sidebar.fui2.maxCommands > 0){
			Res.futura16.drawString((int) (toggleFx2.pos.getX() + toggleFx2.defaultW/2 - Res.futura16.getWidth("F(x)2")/2), (int) (toggleFx2.pos.getY() + 9), "F(x)2", new Color(160, 160, 140));			
			toggleFx2.render(g);
		}

		HelpText.render(g);
		
		
		// pause menu
		if(gamePaused){
			g.setColor(new Color(255, 255, 255, 0.7f));
			g.fillRect(0,0,Game.GWIDTH, Game.GHEIGHT);
			
			g.scale(1, 1);
			g.setLineWidth(2);
			g.setColor(Color.white);
			g.fill(pauseBg);
			g.setColor(new Color(70, 70, 70));
			g.drawRect(pauseBg.getX(), pauseBg.getY(), pauseBg.getWidth(), pauseBg.getHeight());
			g.setLineWidth(1);
			
			resume.render(g);
			gotoworldselect.render(g);
			quitgame.render(g);
			
			Color text = new Color(50, 50, 50);
			Res.futura36.drawString(Game.GWIDTH/2 - Res.futura36.getWidth("PAUSED")/2, pauseBg.getY() + 10, "PAUSED", text);
			Res.futura24.drawString(Game.GWIDTH/2 - Res.futura24.getWidth("RESUME")/2, resume.pos.getY() + 5, "RESUME", text);
			Res.futura24.drawString(Game.GWIDTH/2 - Res.futura24.getWidth("GO TO WORLD SELECT")/2, gotoworldselect.pos.getY() + 5, "GO TO WORLD SELECT", text);
			Res.futura24.drawString(Game.GWIDTH/2 - Res.futura24.getWidth("QUIT GAME")/2, quitgame.pos.getY() + 5, "QUIT GAME", text);
		}
		
		g.drawString("TIMER: " + timer/1000, 10, 50);
		g.drawString("COMMANDS: " + ddaCommands, 10, 70);
		g.drawString("RETRIES: " + ddaRetries, 10, 90);
		g.drawString("ERRORS: " + ddaErrors, 10, 110);
		g.drawString("REREAD: " + ddaReread, 10, 130);
		g.drawString("TIME SOLVED: " + ddaTime/1000, 10, 150);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		
		ddaCommands = Sidebar.programButtons.size();
		
		// restart level
		if(input.isKeyPressed(Input.KEY_F5)){
			initLevel();
		}
		
		// pause game
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			gamePaused = !gamePaused;
		}
		
		// go to world
		if(input.isKeyPressed(Input.KEY_0)){
			Play.world = 0;
			Play.level = 1;
			initLevel();
		}
		if(input.isKeyPressed(Input.KEY_1)){
			Play.world = 1;
			Play.level = 1;
			initLevel();
		}
		if(input.isKeyPressed(Input.KEY_2)){
			Play.world = 2;
			Play.level = 1;
			initLevel();
		}
		if(input.isKeyPressed(Input.KEY_3)){
			Play.world = 3;
			Play.level = 1;
			initLevel();
		}
		if(input.isKeyPressed(Input.KEY_4)){
			Play.world = 4;
			Play.level = 1;
			initLevel();
		}
		
		// skip level+1
		if(input.isKeyPressed(Input.KEY_ENTER)){
			// inside if(!helptext) from gotoNextLevel()
			if(tutorialDone){
				User.doneTutorial[world] = true;
				if(world == 0){
					worldWin = true;
				}				
			}
			
			if(!worldWin){
				level++;
				initLevel();
			}
		}
		
		// gotoworld menu
		if(gotoworld){
			sbg.enterState(1);
		}
		
		// GAME
		
		if(!gamePaused){
			// physics engine
			physWorld.step();
			
			// help texts
			if(HelpText.isAlive){
				HelpText.update(delta, gc.getInput());
			}
			// actual game logic
			else{
				timer += delta;
				player.update(input, delta);
				robot.update(input, delta);
				
				// Update all game texts
				for (Iterator<GameText> iterator = gTexts.iterator(); iterator.hasNext(); ) {
					GameText gt = iterator.next();
					if (!gt.isAlive) {
						iterator.remove();
					} else
						gt.update(delta);
				}
				
				Sidebar.update(delta, input);
			}
			
			// restart + function toggle buttons
			bRestart.update(delta, gc.getInput().getMouseX(), gc.getInput().getMouseY(), gc.getInput());
			if(Sidebar.fui1 != null && Sidebar.fui1.maxCommands > 0)
				toggleFx1.update(delta, gc.getInput().getMouseX(), gc.getInput().getMouseY(), gc.getInput());
			
			if(Sidebar.fui1 != null && Sidebar.fui2.maxCommands > 0)
				toggleFx2.update(delta, gc.getInput().getMouseX(), gc.getInput().getMouseY(), gc.getInput());
			
			Res.updateCursor(gc);
			
			// check if win
			if(worldWin){
				WorldMenu.unlockNewWorld();
				worldWin = false;
				WorldMenu.clickRight();
				sbg.enterState(1);
			}
			else if(levelWin){
				gotoNextLevel();
			}
			// GAME LOOP END
		}
		// paused
		else{
			resume.update(delta, input.getMouseX(), input.getMouseY(), input);
			gotoworldselect.update(delta, input.getMouseX(), input.getMouseY(), input);
			quitgame.update(delta, input.getMouseX(), input.getMouseY(), input);
		}
		
		
	}

	public static void nextWorld(){
		worldWin = true;
		tutorialDone = false;
	}
	
	public static void gotoNextLevel() throws SlickException{
		if(!HelpText.isAlive){
			if(tutorialDone){
				User.doneTutorial[world] = true;
				if(world == 0){
					worldWin = true;
				}				
			}
			
			if(!worldWin){
				level++;
				initLevel();
			}
		}
	}
	
	@Override
	public int getID() {
		return 2;
	}

	@Override
	public void collisionOccured(CollisionEvent e) {
//		System.out.println(e);
	}
	
	public static void drawBody(Graphics g, Body b) {
		Vector2f[] pts = ((Box) b.getShape()).getPoints(b.getPosition(), b.getRotation());
		Vector2f v1 = pts[0];
		Vector2f v2 = pts[1];
		Vector2f v3 = pts[2];
		Vector2f v4 = pts[3];
		
		g.drawLine(v1.x, v1.y, v2.x, v2.y);
		g.drawLine(v2.x, v2.y, v3.x, v3.y);
		g.drawLine(v3.x, v3.y, v4.x, v4.y);
		g.drawLine(v4.x, v4.y, v1.x, v1.y);
	}
	
	

}
