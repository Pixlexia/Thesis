package entities;

import java.util.ArrayList;

import game.RAction;
import game.RActionList;
import game.Sidebar;
import game.Sprites;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;


/*
 *  Command buttons (top of sidebar commands panel)
 */

public class CButton extends Button{
	RAction type;
	boolean enabled;
	
	public static boolean inIfCond, inIfBlock;

	public CButton(RAction t, float w, float h) {
		super(w, h);
		type = t;
		
		enabled = true;
		
		image = Sprites.rActionSprites.get(t).copy();
		image.setAlpha(0.6f);
		
		hoverImage = Sprites.rActionSprites.get(t).copy();
		
		inIfCond = false;
		inIfBlock = false;
	}
	
	public void render(Graphics g){
		g.setColor(Color.white);
		g.fill(getBounds());
		if(isHovered && enabled){
			g.drawImage(hoverImage, pos.getX(), pos.getY());
		}
		else{
			g.drawImage(image, pos.getX(), pos.getY());
		}
		if(!enabled){
			g.setColor(new Color(0, 0, 0, 0.5f));
			g.fill(getBounds());
		}
	}
	
	public void onClick(){
		if(enabled)
			Sidebar.addCommand(type);
		
		buildProgram();
	}
	
	public static void buildProgram(){
//		ArrayList<PButton> pb = Sidebar.programButtons;
//		boolean identifier = false, operator = false, slot = false, equal = false;
//		boolean isOpenCond = false, isCloseCond = false, booleanOperator = false;
//
//		int n = 0;
//		RAction last = null;
//		// last index
//		if(!pb.isEmpty()){
//			n = pb.size() - 1;
//			last = pb.get(n).type;			
//		}
//		
//		// check for last program command
//		
//		// Identifier
//		if(RActionList.isIdentifier(last)){
//			if(inIfCond){ // if [ident]
//				if(!RActionList.isBooleanOperator(pb.get(n-1).type))
//					booleanOperator = true;
//				else{ // if ident boolop [ident]
//					inIfCond = false;
//
//					// if body starts here
//					inIfBlock = true;
//					identifier = true;
//					slot = true;
//				}
//			}
//			else if(!inIfCond && RActionList.isSlot(last) && (n == 0 || pb.get(n-1).type != RAction.equals && !RActionList.isArithmeticOperator(pb.get(n-1).type))){
//				// Left Value (Slot), enable equals only
//				equal = true;
//			}
//			else{
//				isOpenCond = true;
//				if(n > 0 && pb.get(n-1).type == RAction.equals){ // first operand
//					// first operand, or 1 value only
//					System.out.println("3");
//					operator = true;
//					identifier = true;
//					slot = true;
//				}
//				else if(n > 0 && RActionList.isArithmeticOperator(pb.get(n-1).type)){
//					// 2nd operand
//					
//					// default
//					identifier = true;
//					slot = true;
//				}				
//			}
//		}
//		// Equals
//		else if(last == RAction.equals){
//			// enable identifiers only
//			identifier = true;
//		}
//		// Operator
//		else if(RActionList.isArithmeticOperator(last)){
//			identifier = true;
//		}
//		// If/Else Open Condition
//		else if(RActionList.isStartCond(last)){
//			identifier = true;
//			inIfCond = true;
//		}
//		else if(RActionList.isEndCond(last)){
//			inIfCond = false;
//			inIfBlock = false;
//			
//			//default
//			identifier = true;
//			slot = true;
//			isOpenCond = true;
//		}
//		// Boolean operator
//		else if(RActionList.isBooleanOperator(last)){
//			identifier = true;
//		}
//		else{
//			// default
//			identifier = true;
//			slot = true;
//			isOpenCond = true;
//		}
//		
//		if(inIfBlock){
//			isCloseCond = true;
//		}
//		
//		for(int i = 0 ; i < Sidebar.cButtons.size(); i++){
//			RAction ra = Sidebar.cButtons.get(i).type;
//			if(ra == RAction.equals)
//				Sidebar.cButtons.get(i).enabled = equal;
//			else if(RActionList.isArithmeticOperator(ra))
//				Sidebar.cButtons.get(i).enabled = operator;
//			else if(RActionList.isIdentifier(ra))
//				Sidebar.cButtons.get(i).enabled = identifier;
//			else if(RActionList.isSlot(ra))
//				Sidebar.cButtons.get(i).enabled = slot;
//			else if(RActionList.isBooleanOperator(ra))
//				Sidebar.cButtons.get(i).enabled = booleanOperator;
//			else if(RActionList.isStartCond(ra))
//				Sidebar.cButtons.get(i).enabled = isOpenCond;
//			else if(RActionList.isEndCond(ra))
//				Sidebar.cButtons.get(i).enabled = isCloseCond;
//			else
//				Sidebar.cButtons.get(i).enabled = true;
//		}
		
		
//		if(!pb.isEmpty() && RActionList.isSlot(pb.get(pb.size()-1).type)){
//		}
//		
//		for(int i = 0 ; i < Sidebar.cButtons.size(); i++){
//			if(Sidebar.cButtons.get(i).type != RAction.equals)
//				Sidebar.cButtons.get(i).enabled = b;
//		}
	}

}
