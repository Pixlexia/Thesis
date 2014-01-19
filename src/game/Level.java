package game;

import java.util.ArrayList;

import net.phys2d.raw.Body;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.Box;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class Level {
	public static TiledMap map;
	public static boolean[][] solid;
	public static ArrayList<Point> ladders;
	public static ArrayList<Point> exit;
	public static ArrayList<Computer> computers;
	public static ArrayList<Body> solidBodies;
	
	public static final int TILE_LADDER = 6;
	public static final int TILE_EXIT = 5;
	public static final int TILE_RSTART = 24;
	public static final int TILE_PSTART = 25;
	public static final int TILE_COMPUTER = 3;
	
	public static Point pStart, rStart;
	
	public static String instructions;
	
	/*
	 * NOTES:
	 * - Bug: Don't place ONE BLOCK ONLY on the right most tile (at least 2 if stick to the rightmost tile)
	 */
	
	public class Computer{
		public Point pos;
		public int value;
		
		public Computer(int x, int y){
			pos = new Point(x, y);
		}
		
		public boolean collideWith(Rectangle r){
			if(r.contains(new Point((pos.getX()+1) * Game.TS + Game.TS, (pos.getY()+1) * Game.TS)) ||
					r.contains(new Point((pos.getX()+1) * Game.TS + Game.TS, (pos.getY()+2) * Game.TS)) ||
					r.contains(new Point((pos.getX()+2) * Game.TS + Game.TS, (pos.getY()+1) * Game.TS)) ||
					r.contains(new Point((pos.getX()+2) * Game.TS + Game.TS, (pos.getY()+2) * Game.TS)))
				return true;
			
			return false;
		}
	}
	
	public Level(int level) throws SlickException{
		map = new TiledMap("res/levels/idea" + level + ".tmx");
//		map = new TiledMap("res/levels/idea3.tmx");
		Game.TS = map.getTileWidth();
		ladders = new ArrayList<Point>();
		computers = new ArrayList<Computer>();
		exit = new ArrayList<Point>();
		solid = new boolean[map.getWidth()][map.getHeight()];
		
		solidBodies = new ArrayList<Body>();
		
		for(int y = 0 ; y < map.getHeight(); y++ ){
			for(int x = 0, width = 0, start = 0; x < map.getWidth(); x++){
				// Check player and robot initial point
				int tileId = map.getTileId(x, y, 1);
				if(tileId == TILE_PSTART){
					pStart = new Point(x * Game.TS + Game.TS/2, y * Game.TS + Game.TS/2);
				}
				else if(tileId == TILE_RSTART){
					rStart = new Point(x * Game.TS + Game.TS, y * Game.TS + Game.TS);					
				}
				
				tileId = map.getTileId(x, y, 0);
				
				if(tileId == TILE_LADDER){
					ladders.add(new Point((x+1) * Game.TS + Game.TS/2, (y+1) * Game.TS));
				}
				else if(tileId == TILE_EXIT){
					exit.add(new Point((x+1) * Game.TS + Game.TS, (y+1) * Game.TS + Game.TS/2));
				}
				else if(tileId == TILE_COMPUTER){
					computers.add(new Computer(x, y));
//					computers.add(new Point((x+1) * Game.TS + Game.TS, (y+1) * Game.TS));
//					computers.add(new Point((x+1) * Game.TS + Game.TS, (y+2) * Game.TS));
//					computers.add(new Point((x+2) * Game.TS + Game.TS, (y+1) * Game.TS));
//					computers.add(new Point((x+2) * Game.TS + Game.TS, (y+2) * Game.TS));
				}

				if(tileId == 1 && ((x+1) < map.getWidth())){
					if(width == 0){
						start = x;
					}
					
					width += map.getTileWidth();
					solid[x][y] = true;
					
				}
				else{
					if(tileId == 1 && (x+1) >= map.getWidth()){
						width += map.getTileWidth();
						solid[x][y] = true;
						
						if(width == 0){
							start = map.getWidth();
						}
					}
					
					if(width > 0){
//						System.out.println("[" + start + " to " + width + ", " + y + "]");
						StaticBody body = new StaticBody("tile" + tileId, new Box(width, map.getTileHeight()));
						body.setPosition(start * map.getTileWidth() + (width/2), y * map.getTileHeight() + map.getTileHeight()/2);
						solidBodies.add(body);
						body.setFriction(0.5f);
						Play.physWorld.add(body);

						width = 0;
					}
				}
			}
		}
		System.out.println("Solidbodies: " + solidBodies.size());
		
		initComputerValues();
	}
	
	public void initComputerValues(){
		switch(Play.world){
		case 0:
			switch(Play.level){
			case 1:
				computers.get(0).value = 50;
				computers.get(1).value = 10;
				computers.get(2).value = 60;
				computers.get(3).value = 100;
				instructions = "Get the largest computer value\nand store it to yellow slot";
				break;
				
			case 2:
				instructions = "Store comp1 to blue slot";
				break;
				
			case 3:
				instructions = "You are now in level 3!";
			default:
				break;
			}
			break;
			
		case 1:
			break;
		}
		
		instructions = "Level: " + Play.level + "\nTask: " + instructions;
	}
}
