//package Lab3;

public class Resource {
	
	//this is my resource class, I made each resource its own object

	private int type;
	private int unitsPresent;
	private int tempAdd;
	
	
	public Resource(int type, int units) {
		
		this.type = type;
		unitsPresent = units;
		
		
	}
	
	public int getType(){
		
		return type;
		
	}
	
	//get the units available
	public int getUnitsPresent() {
		
		return unitsPresent;
		
	}
	
	//when one releases resources, i first temp remove and then at the end of the cycle, i fully add them to make them available
	public void tempAdd(int u) {
		
		tempAdd = u;
		
	}
	
	public int getTempAdd() {
		
		return tempAdd;
		
	}
	
	//here i fully make the resources available after each cycle
	public void fullyAdd() {
		
		unitsPresent = unitsPresent + tempAdd;
		tempAdd = 0;
		
		
	}
	
	public void addUnits(int u){
		
		unitsPresent = unitsPresent + u;
		
	}
	
	public void removeUnits(int u){
		
		unitsPresent = unitsPresent - u;
		
	}
	
}
