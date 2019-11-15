public class Resource {

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
	
	public int getUnitsPresent() {
		
		return unitsPresent;
		
	}
	
	public void tempAdd(int u) {
		
		tempAdd = u;
		
	}
	
	public int getTempAdd() {
		
		return tempAdd;
		
	}
	
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
