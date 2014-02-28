package entities;


public class Loop{
	int iterations, currentCount, startIndex;
	boolean isLooping;
	
	public Loop(int numOfIterations, int start){
		iterations = numOfIterations;
		startIndex = start;
		currentCount = numOfIterations;
		
		isLooping = true;
		
		System.out.println("new loop at " + startIndex + " " + iterations + " times");
		
	}
	
	public void reset(){
		currentCount = iterations;
		isLooping = true;
		Robot.iterateCounts.put(startIndex, iterations);
	}
	
	void makeIteration(){
		
		if(currentCount <= 0){
			isLooping = false;
		}
		else{
			currentCount--;
			Robot.iterateCounts.put(startIndex, currentCount);
			if(isLooping){
				System.out.println("asd");
				Robot.commandCount = startIndex;
			}			
		}
		
	}
}