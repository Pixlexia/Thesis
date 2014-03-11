package game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String name;
	public ArrayList<Integer> w1, w2, w3, w4;
	public int finishedLevels[];
	public int finishedTutorialLevels[];
	public boolean doneTutorial[];
	
	public int rating; // 0 = easy, 1 = normal, 2 = hard
	
	public User(){
		rating = 0;
		name = "Alfonz2";
		
		w1 = new ArrayList<Integer>();
		w2 = new ArrayList<Integer>();
		w3 = new ArrayList<Integer>();
		w4 = new ArrayList<Integer>();
		
		doneTutorial = new boolean[5];
		finishedTutorialLevels = new int[5];
		finishedLevels = new int[5];
		
		doneTutorial[1] = false;
		doneTutorial[2] = false;
		doneTutorial[3] = false;
		doneTutorial[4] = false;
		
		loadData();
	}
	
	public void addScore(int i){
		switch(i){
		case 2: i = 5; break;
		case 1: i = 3; break;
		case 0: i = 1; break;
		}
		switch(Play.world){
		case 1: w1.add(i); break;
		case 2: w2.add(i); break;
		case 3: w3.add(i); break;
		case 4: w4.add(i); break;
		}
	}
	
	public ArrayList<Integer> getWorldScores(int i){
		switch(i){
		case 1: return w1;
		case 2: return w2;
		case 3: return w3;
		case 4: return w4;
		}		
		
		return null;
	}
	
	public int getWorldScore(ArrayList<Integer> scores){
		int worldScore = 0;
		
		for(int s : scores){
			worldScore += s;
		}
		
		return worldScore;
	}
	
	public void loadData(){
		String fileName = "data.dat";
		FileInputStream fin = null;
		ObjectInputStream ois = null;
		System.out.println("Reading data");
		try {
			fin = new FileInputStream(fileName);
			ois = new ObjectInputStream(fin);
			User user = (User) ois.readObject();
			Play.user = user;
			
			// display user data
			System.out.println("Name: " + user.name);
			
			// tutorial levels finished for each world
			for(int i = 1 ; i < 5; i++){
				System.out.println("Tutorials finished World " + i + ": " + user.finishedTutorialLevels[i]);				
			}
			
			// levels finisehd for each world
			for(int i = 1 ; i < 5; i++){
				System.out.println("Levels finished World " + i + ": " + user.finishedLevels[i]);				
			}
			
			// score for each world
			for(int i = 1 ; i < 5 ; i++){
				System.out.println("Score for World " + i + ": " + user.getWorldScores(i));
			}
			
			// game scores
			System.out.println("Total game score: " + (getWorldScore(user.w1) + getWorldScore(user.w2) + getWorldScore(user.w3) + getWorldScore(user.w4)));
			System.out.println("Average game score: " + (getWorldScore(user.w1) + getWorldScore(user.w2) + getWorldScore(user.w3) + getWorldScore(user.w4))/4);
						
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void saveData(){
		String filename = "data.dat";
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filename);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		try {
//			System.out.println("Writing to file!");
//			PrintWriter out = new PrintWriter("data.txt");
//			out.println("Name: " + User.name);
//			
//			out.append("Tutorial levels finished:");
//			// Loop all worlds count tutorial levels finished
//			for(int i = 0; i < 4; i++){
//				out.append("World " + i+1 + " " + finishedTutorialLevels[1+1]);
//			}
//			
//			String text = "Level: " + Play.level;
//			out.append(text);
//			out.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
	}
}
