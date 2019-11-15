import java.util.ArrayList;
import java.util.Comparator;

public class Task {

	private ArrayList<Activity> activities;
	private int taskNumber;
	private int timeTaken;
	private int waitingTime;
	private String currentState;
	
	int holding = 0;
	int initialClaim;
	
	int activityIndex;
	
	
	
	
	public Task(int taskNumber) {
		
		activities = new ArrayList<Activity>();
		this.taskNumber = taskNumber;
		timeTaken = 0;
		waitingTime = 0;
		currentState = "ready";
		activityIndex = 0;
		
	}
	
	
	public void addActivity(Activity a) {
		
		activities.add(a);
		
	}
	
	public ArrayList<Activity> getActivities(){
		
		return activities;
		
	}
	
	public void increaseTimeTaken() {
		
		timeTaken++;
		
	}
	
	public void increaseWaitingTime() {
		
		waitingTime++;
		
	}
	
	public void decreaseWaitingTime() {
		
		waitingTime--;
		
	}
	
	public void setTimeTaken(int t) {
		
		timeTaken = t;
		
	}
	
	public int getTaskNumber() {
		
		return taskNumber;
		
	}
	
	public int getWaitingTime() {
		
		return waitingTime;
		
	}
	
	public int getTimeTaken() {
		
		return timeTaken;
		
	}
	
	public void updateState(String s) {
		
		currentState = s;
		
	}
	
	public String getCurrentState() {
		
		return currentState;
		
	}
	
	public int compareTo(Task c) {
		
		if(this.getTaskNumber() > c.getTaskNumber())
			return 1;
		
		if(this.getTaskNumber() < c.getTaskNumber())
			return -1;
		
		return 0;
	}
	
	public static Comparator<Task> taskNum = new Comparator<Task>() {

		public int compare(Task p1, Task p2) {

			if(p1.getTaskNumber() > p2.getTaskNumber())
				return 1;
			
			if(p1.getTaskNumber() < p2.getTaskNumber())
				return -1;
			
			return 0;

		}

    };
	
}
