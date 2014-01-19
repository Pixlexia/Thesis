package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class WorldMenu extends BasicGameState{
	
	ConceptWorld[] worlds;
	
	public class ConceptWorld{
		String title;
		boolean unlocked;
		
		public ConceptWorld(int i){
			switch(i){
			case 0: title = "Tutorial"; break;
			case 1: title = "Variables"; break;
			case 2: title = "Conditional Branching"; break;
			case 3: title = "Loops"; break;
			case 4: title = "Functions"; break;
			default: break;
			}
			
			if(i == 0){
				unlocked = true;
			}
			else{				
				unlocked = false;
			}
		}
	}
	
	public WorldMenu(){
		worlds = new ConceptWorld[5];
		
		for(int i = 0 ; i < worlds.length; i++){
			worlds[i] = new ConceptWorld(i);
		}
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		for(int i = 0 ; i < worlds.length; i++){
			String t = "";
			if(!worlds[i].unlocked){
				t = " - LOCKED";
			}
			g.drawString(i + " " + worlds[i].title + t, 100, 100 + 20 * i);
		}
		
		g.drawString("Press world # to enter.", 100, 250);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		
		if(input.isKeyPressed(Input.KEY_0)){
			if(worlds[0].unlocked){
				sbg.enterState(2);
			}
		}
		else if(input.isKeyPressed(Input.KEY_1)){
			
		}
		else if(input.isKeyPressed(Input.KEY_2)){
			
		}
		else if(input.isKeyPressed(Input.KEY_3)){
			
		}
		else if(input.isKeyPressed(Input.KEY_4)){
			
		}
	}

	@Override
	public int getID() {
		
		return 1;
	}

}
