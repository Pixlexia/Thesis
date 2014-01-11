package game;

import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.CollisionListener;

public class CollisionHandler implements CollisionListener{

	public CollisionHandler(){
		
	}
	
	@Override
	public void collisionOccured(CollisionEvent ce) {
		System.out.println("COLLIDE!!!!!!!!!!!!!!");
	}

}
