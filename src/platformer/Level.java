package platformer;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Level {
	static TiledMap map;
	static boolean[][] solid;
	
	public Level() throws SlickException{
		map = new TiledMap("res/1.tmx");
		solid = new boolean[map.getWidth()][map.getHeight()];
		
		for(int y = 0 ; y < map.getHeight(); y++ ){
			for(int x = 0 ; x < map.getWidth(); x++){
				int tileId = map.getTileId(x, y, 0);
				
				if(tileId == 1)
					solid[x][y] = true;
			}
		}
		
		System.out.println(map.getWidth() + " " + map.getHeight());
	}
}
