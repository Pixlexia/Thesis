package game;

public class User {
	public static String name;
	public static boolean w0[], w1[], w2[], w3[], w4[];
	public static boolean doneTutorial[];
	
	public static int rating; // 0 = easy, 1 = normal, 2 = hard
	
	public User(){
		rating = 0;
		
		// 24 levels per world, 8 for each difficulty level
		w0 = new boolean[24];
		w1 = new boolean[24];
		w2 = new boolean[24];
		w3 = new boolean[24];
		w4 = new boolean[24];
		
		doneTutorial = new boolean[5];
		
		doneTutorial[1] = false;
		doneTutorial[2] = false;
		doneTutorial[3] = false;
		doneTutorial[4] = false;
	}
	
	public void loadData(){
		
	}
	
	public void saveData(){
		
	}
}
