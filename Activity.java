//package Lab3;

public class Activity {
	
	//this is my activity class

	private String type;
	private int taskNumber;
	
	//these are the inputs, which i store them here
	private int resourceType;
	private int numberOfCycles;
	
	private int initialClaim;
	private int numberRequested;
	private int numberReleased;
	
	public int unused = 0;
	
	
	
	public Activity(String type, int firstNum, int secondNum, int thirdNum){
		
		this.type = type;
		
		
		//here i set the corresponding values to the corresponding type
		if(type.equals("initiate")) {
			
			taskNumber = firstNum;
			resourceType = secondNum;
			initialClaim = thirdNum;
			
		}
		
		if(type.equals("request")) {
			
			taskNumber = firstNum;
			resourceType = secondNum;
			numberRequested = thirdNum;
			
		}
		
		if(type.equals("release")) {
			
			taskNumber = firstNum;
			resourceType = secondNum;
			numberReleased = thirdNum;
			
		}
		
		if(type.equals("compute")) {
			
			taskNumber = firstNum;
			numberOfCycles = secondNum;
			
			
		}
		
		if(type.equals("terminate")) {
			
			taskNumber = firstNum;
			
			
			
		}
	
	}
	
	//setters and getters
	
	public String getType() {
		
		return type;
		
	}
	
	public int getTaskNumber() {
		
		return taskNumber;
		
	}
	
	public int getResourceType() {
		
		return resourceType;
		
	}
	
	public int getNumberOfCycles() {
		
		return numberOfCycles;
		
	}
	
	public void decreaseNumberOfCycles() {
		
		numberOfCycles--;
		
	}
	
	
	public int getInitialClaim() {
		
		return initialClaim;
		
	}
	
	public int getNumberRequested() {
		
		return numberRequested;
		
	}
	
	public int getNumberReleased() {
		
		return numberReleased;
		
	}
	
	
}
