package game;

import java.util.ArrayList;

import net.phys2d.raw.Body;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.Box;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.tiled.TiledMap;

public class Level {
	public static TiledMap map;
	public static boolean[][] solid;
	public static ArrayList<Point> ladders;
	public static ArrayList<Body> solidBodies;
	
	public static int TILE_LADDER = 6;
	
	/*
	 * NOTES:
	 * - Bug: Don't place ONE BLOCK ONLY on the right most tile (at least 2 if stick to the rightmost tile)
	 */
	
	public Level() throws SlickException{
		map = new TiledMap("res/1.tmx");
		ladders = new ArrayList<Point>();
		solid = new boolean[map.getWidth()][map.getHeight()];
		
		solidBodies = new ArrayList<Body>();
		
		for(int y = 0 ; y < map.getHeight(); y++ ){
			System.out.print("Y: " + y + " " );
			for(int x = 0, width = 0, start = 0; x < map.getWidth(); x++){
				int tileId = map.getTileId(x, y, 0);

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
						System.out.println("---"+y+"---");
						
						if(width == 0){
							start = map.getWidth();
						}
					}
					
					if(width > 0){
//						System.out.println("[" + start + " to " + width + ", " + y + "]");
						StaticBody body = new StaticBody("" + tileId, new Box(width, map.getTileHeight()));
						body.setPosition(start * map.getTileWidth() + (width/2), y * map.getTileHeight() + map.getTileHeight()/2);
						solidBodies.add(body);
						body.setFriction(0.01f);
						Play.world.add(body);

						width = 0;
					}
				}
				
				System.out.print(tileId + " " );
			}
			System.out.println();
		}
		System.out.println("Solidbodies: " + solidBodies.size());
	}
//			for(int x = 0; x < map.getWidth(); x++){
//				int localWidth = 0;
//				int localX = 0;
//				boolean adding = false;
//
//				int tileId = map.getTileId(x, y, 0);
//				if(tileId == 1){
//
//					if(!adding){
//						localX = x;
//						localWidth += map.getTileWidth();
//						adding = true;
//					}
//
//					while((x+1) < map.getWidth() && tileId == 1){
//						localWidth += map.getTileWidth();
//						x++;
//						solid[x][y] = true;
//					}
//					
//					System.out.println((localX * map.getTileWidth() + (localWidth/2))+ " to " + localWidth + " " + y);
//					StaticBody body = new StaticBody("" + tileId, new Box(localWidth, map.getTileHeight()));
//					body.setPosition(localX * map.getTileWidth() + (localWidth/2), y * map.getTileHeight() + map.getTileHeight()/2);
//					body.setFriction(0.01f);
//					solidBodies.add(body);
//					Play.world.add(body);
//				}
//				
//			}
//				//====
//				
//				if(tileId == 1){
//					x++;
//					solid[x][y] = true;
//					StaticBody body = new StaticBody("" + tileId, new Box(map.getTileWidth(), map.getTileHeight()));
//					body.setPosition((float) (x * map.getTileWidth() + map.getTileWidth()/2), y * map.getTileHeight() + map.getTileHeight()/2);
//					solidBodies.add(body);
//					body.setFriction(0.01f);	
//					Play.world.add(body);
//				}
//				else if(tileId == TILE_LADDER){
//					ladders.add(new Point((x+1) * Game.TS, y * Game.TS));
//				}
//				
//				System.out.print(tileId);
//			}
//			System.out.println();
//		}
//		
}
