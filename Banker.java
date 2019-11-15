import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Banker {
	
	static ArrayList<Task> tasks;
	static ArrayList<Resource> resources;

	public static void main(String[] args) throws FileNotFoundException {
		
		//Scanner scan = new Scanner(System.in);
		//String fileName = scan.next();
		String fileName = "src/Lab3/input-06";
		//String fileName = "input-02";
		
		
		File file = new File(fileName);
		Scanner input = new Scanner(file);
		//File f = new File(args[0]);
		
		//System.out.println(input.next());
		
		//try {
			
			
		//} catch (FileNotFoundException e) {
			
			//System.out.println("Please enter in the correct filename");
			//e.printStackTrace();
		//}
		
		
		
		tasks = new ArrayList<Task>();
		resources = new ArrayList<Resource>();
		
		ArrayList<Activity> allActivities = new ArrayList<Activity>();
		
		int numOfTasks = Integer.parseInt(input.next());
		//System.out.println("Num of tasks "+numOfTasks);
		int numOfResourceTypes = Integer.parseInt(input.next());
		//System.out.println("Num of resource types  "+numOfResourceTypes);
		
		for(int i = 0; i < numOfResourceTypes; i++) {
			
			resources.add(new Resource(i+1,Integer.parseInt(input.next())));
			
		}
		
		while(input.hasNext()) {
			
			allActivities.add(new Activity(input.next(),Integer.parseInt(input.next()),Integer.parseInt(input.next()),Integer.parseInt(input.next())));
			
		}
		
		
		for(int i = 0; i < numOfTasks; i++) {
			
			Task a = new Task(i+1);
			tasks.add(a);
			
			for(int j = 0; j < allActivities.size();j++) {
				
				if(allActivities.get(j).getTaskNumber() == i+1)
					a.addActivity(allActivities.get(j));
				
			}
			
		}
		
		/*
		for(int i = 0; i < tasks.size(); i++) {
			System.out.println(tasks.get(i).getTaskNumber());
			for(int j = 0; j < tasks.get(i).getActivities().size();j++) {
				System.out.println(tasks.get(i).getActivities().get(j).getType());
			}
		}
		System.out.println("resources");
		for(int i = 0; i < resources.size(); i++) {
			System.out.println(resources.get(i).getType() + " " +resources.get(i).getUnitsPresent());
		}
		*/
		
		fifo();
		
		//fifo2();
		System.out.println("FIFO");
		output();
		
		System.out.println();
		
		
		//bankers();
		//System.out.println("BANKER'S ALGORITHM");
		//output();
	
		
	}
	
	public static void fifo() {
		
		
		int time = 0; 
		int processesTerminated = 0;
		boolean isFirst = true;
		
		int isFirstCycle = 0;
		
		ArrayList<Task> blockedQueue = new ArrayList<Task>();
		
		
		while(processesTerminated < tasks.size()) {
			
			boolean satisfied = false;
			
			
			//imma have to have another for loop to see if at least one of the blockedQueue can be satisfies,
			//if not, then we abort one. and if it was satisfied then just make it wait. so just a forloop to set satisfied correctly.
			
			for(int k = 0; k < tasks.size(); k++) {
				
				
				if(tasks.get(k).getActivities().get(tasks.get(k).activityIndex).getType().equals("request")) {
				
				int rType = tasks.get(k).getActivities().get(tasks.get(k).activityIndex).getResourceType();
				int rAmount = tasks.get(k).getActivities().get(tasks.get(k).activityIndex).getNumberRequested();
				
				System.out.println("rtype "+rType+" k:"+k);
				
				//for(int i = 0; i < resources.size(); i++)
					//System.out.println("resource "+resources.get(i).getType());
				if(rType==0)
					rType = tasks.get(k).getActivities().get(tasks.get(k).activityIndex-1).getResourceType();
				
				if(resources.get(rType-1).getUnitsPresent() - rAmount >= 0) {
					
					satisfied = true;
					System.out.println(satisfied);
					
					
				}
				}
				else if(tasks.get(k).getActivities().get(tasks.get(k).activityIndex).getType().equals("release")) {
					
					satisfied = true;
					System.out.println(satisfied+"2");
					
				}
				
				//else if(resources.get(rType-1).getUnitsPresent()+resources.get(rType-1).getTempAdd() - rAmount < 0) {
					
					
				
				//}
				
				else {
					//blockedQueue.get(k).increaseWaitingTime();
					//blockedQueue.add(0, blockedQueue.get(i));
					
					//System.out.println("inq Task " +blockedQueue.get(k).getTaskNumber()+ "'s grant could not be accepted of an amount of "+rAmount+ " time "+time);
				}
				
				
				
				
			}
			
			ArrayList<Task> aborted = new ArrayList<Task>();
			
			boolean test = false;
			
			for(int k = 0; k < blockedQueue.size(); k++) {
				
				if(blockedQueue.size() == tasks.size())
					test = true;
					
				
				int rType = blockedQueue.get(k).getActivities().get(blockedQueue.get(k).activityIndex).getResourceType();
				int rAmount = blockedQueue.get(k).getActivities().get(blockedQueue.get(k).activityIndex).getNumberRequested();
				
				
				//if(tasks.get(k).getActivities().get(tasks.get(k).activityIndex).getType().equals("request")) {
				
					
				System.out.println("task num in queue "+blockedQueue.get(k).getTaskNumber());
				System.out.println(rType);
				System.out.println("resources "+resources.get(rType-1).getUnitsPresent()+" + tempadd "+resources.get(rType-1).getTempAdd());
				System.out.println(rAmount);
				
				
				if(resources.get(rType-1).getUnitsPresent() - rAmount >= 0) {
					resources.get(rType-1).removeUnits(rAmount);
					blockedQueue.get(k).activityIndex++;
					blockedQueue.get(k).updateState("unblocked");
					//satisfied = true;
					//blockedQueue.remove(k);
					System.out.println("inq Task " +blockedQueue.get(k).getTaskNumber()+ " has requested "+rAmount+ " time "+time);
					blockedQueue.get(k).holding = blockedQueue.get(k).holding + rAmount;
					blockedQueue.remove(k);
					k--;
					
				}
				
				else if(resources.get(rType-1).getUnitsPresent()+resources.get(rType-1).getTempAdd() - rAmount < 0 && satisfied == false) {
					
					aborted.add(blockedQueue.get(k));
					
					/*
					blockedQueue.get(k).updateState("aborted");
					
					
					System.out.println("Task "+blockedQueue.get(k).getTaskNumber()+" has been aborted "+time);
					rType = blockedQueue.get(k).getActivities().get(blockedQueue.get(k).activityIndex - 1).getResourceType();
					rAmount = blockedQueue.get(k).getActivities().get(blockedQueue.get(k).activityIndex - 1).getNumberRequested();
					
					//System.out.println(rType);
					//System.out.println(rAmount);
					
					//isFirstCycle = time;
					
					if(test == true || isFirstCycle == time) {
					
						isFirstCycle = time;
						
						resources.get(rType-1).addUnits(rAmount);
						System.out.println("ji");
						test = false;
					}
					else
						resources.get(rType-1).tempAdd(resources.get(rType-1).getTempAdd() + rAmount);
					
					//resources.get(rType-1).addUnits(resources.get(rType-1).getTempAdd() + rAmount);
					blockedQueue.remove(k);
					k--;
					processesTerminated++;
					
					//for(int k = i+1; k < tasks.size(); k++)
						//tasks.get(k).increaseWaitingTime();
					
					*/
					
					
					//i++;
					//break;
				
				}
				
				
					
				else {
					
					if((blockedQueue.get(k).getActivities().get(blockedQueue.get(k).activityIndex+1).getType().equals("terminate"))) {
						System.out.println("terminated here");
						/*
						//if(!(blockedQueue.get(k).getCurrentState().equals("terminated"))) {
							processesTerminated++;
							blockedQueue.get(k).setTimeTaken(time);
							System.out.println("terminated here ");
							blockedQueue.get(k).updateState("terminated");
						//}
						*/
						
					}
						//blockedQueue.get(k).decreaseWaitingTime();
						
					//blockedQueue.add(0, blockedQueue.get(i));
					
					else
						blockedQueue.get(k).increaseWaitingTime();
					System.out.println("inq Task " +blockedQueue.get(k).getTaskNumber()+ "'s grant could not be accepted of an amount of "+rAmount+ " time "+time);
				}
				
				
				
				//for(int i = 0; i < resources.size(); i++)
					//resources.get(i).fullyAdd();

				//}
				
				
			}
			
			
			
			
			for(int i = 0; i < tasks.size(); i++) {
				
				
				if(!(tasks.get(i).getCurrentState().equals("aborted")) && !(tasks.get(i).getCurrentState().equals("blocked")) && !(tasks.get(i).getCurrentState().equals("unblocked"))){
					
					
					if(tasks.get(i).getActivities().get(tasks.get(i).activityIndex).getType().equals("initiate")) {
						
						System.out.println("Task " +tasks.get(i).getTaskNumber()+ " is initiated"+ " time "+time);
						tasks.get(i).activityIndex++;
						
					}
					
					else if(tasks.get(i).getActivities().get(tasks.get(i).activityIndex).getType().equals("request")) {
						int rType = tasks.get(i).getActivities().get(tasks.get(i).activityIndex).getResourceType();
						int rAmount = tasks.get(i).getActivities().get(tasks.get(i).activityIndex).getNumberRequested();
						
						System.out.println(rType);
						System.out.println(rAmount);
						
						
						if(resources.get(rType-1).getUnitsPresent() - rAmount >= 0) {
							resources.get(rType-1).removeUnits(rAmount);
							tasks.get(i).activityIndex++;
							System.out.println("Task " +tasks.get(i).getTaskNumber()+ " has requested "+rAmount+ " time "+time);
							tasks.get(i).holding = tasks.get(i).holding + rAmount;
						}
						
						
							
						else {
							tasks.get(i).increaseWaitingTime();
							tasks.get(i).updateState("blocked");
							blockedQueue.add(tasks.get(i));
							//isFirst = false;
							System.out.println("added " +tasks.get(i).getTaskNumber()+" to queue");
							System.out.println("Task " +tasks.get(i).getTaskNumber()+ "'s grant could not be accepted of an amount of "+rAmount+ " time "+time);
							//if(isFirst != true)
								//isFirst = false;
						
						}
					}
					
					
					else if(tasks.get(i).getActivities().get(tasks.get(i).activityIndex).getType().equals("release")) {
						
						int rType = tasks.get(i).getActivities().get(tasks.get(i).activityIndex).getResourceType();
						int rAmount = tasks.get(i).getActivities().get(tasks.get(i).activityIndex).getNumberReleased();
						
						//resources.get(rType-1).addUnits(rAmount);
						resources.get(rType-1).tempAdd(rAmount);
						tasks.get(i).activityIndex++;
						System.out.println("Task " +tasks.get(i).getTaskNumber()+ " has released "+rAmount+ " time "+time);
						tasks.get(i).holding = tasks.get(i).holding - rAmount;
						
						/*
						if(tasks.get(i).getActivities().get(tasks.get(i).activityIndex).getType().equals("terminate")) {
							
							if(!(tasks.get(i).getCurrentState().equals("terminated"))) {
								processesTerminated++;
								
								tasks.get(i).setTimeTaken(time);
								//System.out.println(processesTerminated);
								tasks.get(i).updateState("terminated");
							}
							
							
						}
						*/
						
					}
					
					
				//}
				
					else if(tasks.get(i).getActivities().get(tasks.get(i).activityIndex).getType().equals("terminate")) {
						
						if(!(tasks.get(i).getCurrentState().equals("terminated"))) {
							processesTerminated++;
							tasks.get(i).setTimeTaken(time);
							//System.out.println(processesTerminated);
							tasks.get(i).updateState("terminated");
							tasks.get(i).holding = 0;
						}
						
						
					}
				}	
				
				
				
					
			}
			
			boolean test2 = false;
			
			Collections.sort(aborted, Task.taskNum);
			boolean didAbort = false;
			
			for(int i = 0; i < aborted.size(); i++)
				System.out.println("aborted list "+aborted.get(i).getTaskNumber());
			
			for(int k = 0; k < aborted.size(); k++) {
					
				System.out.println("aborted "+aborted.get(k).getTaskNumber());
				
				int rType = aborted.get(k).getActivities().get(aborted.get(k).activityIndex).getResourceType();
				int rAmount = aborted.get(k).getActivities().get(aborted.get(k).activityIndex).getNumberRequested();
				
				
				
				if(resources.get(rType-1).getUnitsPresent()+resources.get(rType-1).getTempAdd() - rAmount < 0 && satisfied == false) {
					aborted.get(k).updateState("aborted");
					
					
					System.out.println("Task "+aborted.get(k).getTaskNumber()+" has been aborted "+time);
					rType = aborted.get(k).getActivities().get(aborted.get(k).activityIndex - 1).getResourceType();
					//rAmount = aborted.get(k).getActivities().get(aborted.get(k).activityIndex - 1).getNumberRequested();
					
					didAbort = true;
					//System.out.println(rType);
					//System.out.println(rAmount);
					
					//isFirstCycle = time;
					
					//if(test2 == true || isFirstCycle == time) {
					
						//isFirstCycle = time;
						
						resources.get(rType-1).addUnits(aborted.get(k).holding);
						//time--;
						///System.out.println("ji");
						//test2 = false;
					//}
					//else
						//resources.get(rType-1).tempAdd(resources.get(rType-1).getTempAdd() + rAmount);
					
					//resources.get(rType-1).addUnits(resources.get(rType-1).getTempAdd() + rAmount);
					
					
					for(int i = 0; i < blockedQueue.size();i++)
						if(blockedQueue.get(i).getTaskNumber() == aborted.get(k).getTaskNumber()) {
							blockedQueue.remove(i);
							i--;
						
						}
					aborted.remove(k);
					
					k--;
					processesTerminated++;
					
					//for(int k = i+1; k < tasks.size(); k++)
						//tasks.get(k).increaseWaitingTime();
					
					
					
					
					//i++;
					//break;
				
				}
				
				
					
				else {
						
					aborted.get(k).increaseWaitingTime();
					System.out.println("inq Task " +aborted.get(k).getTaskNumber()+ "'s grant could not be accepted of an amount of "+rAmount+ " time "+time);
				
				
				}
				
				//for(int i = 0; i < resources.size(); i++)
					//resources.get(i).fullyAdd();

				//}
				
				
			}
			
			
			
			//if(didAbort == true)
			//	time--;
			
			//didAbort = false;
			
			
			
			
			
			
			
			for(int i = 0; i < tasks.size(); i++)
				if(tasks.get(i).getCurrentState().equals("unblocked"))
					tasks.get(i).updateState("ready");
			
			
			System.out.println();
			time++;
			
			for(int i = 0; i < resources.size(); i++)
				resources.get(i).fullyAdd();
			
			
			
			
			
			
		}


		
		
		
		
		
		
		
		
		
		
		
	}
	
	public static void bankers() {

		int time = 0; 
		int processesTerminated = 0;
		boolean isFirst = true;
		
		int isFirstCycle = 0;
		
		ArrayList<Task> blockedQueue = new ArrayList<Task>();
		
		ArrayList<Task> pending = new ArrayList<Task>();
		
		
		
		while(processesTerminated < tasks.size()) {
			
			
			for(int i = 0; i < resources.size(); i++)
				resources.get(i).fullyAdd();
			
			
			
			ArrayList<Task> runCycle = new ArrayList<Task>();
			
			
			runCycle.addAll(pending);
			
			for (Task task : tasks) {
				if (!runCycle.contains(task)) {
					runCycle.add(task);
				}
			}
			
			
			for(int i = 0; i < runCycle.size(); i++) {
				
				if(runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getType().equals("compute")) {
					
					runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).decreaseNumberOfCycles();
					
					int c = runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getNumberOfCycles();
					
					if(c == 0) {
						
						runCycle.get(i).activityIndex++;
						
					}
					else
						continue;
					
					
					
				}
				
				if(runCycle.get(i).getCurrentState().equals("aborted") || runCycle.get(i).getCurrentState().equals("terminated")) {
					
					continue;
					
					
				}
				
				else if(runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getType().equals("initiate")) {
					
					int claimed = runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getInitialClaim();
					int need = runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getInitialClaim();
					
					
					int rType = runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getResourceType();
					
					runCycle.get(i).initialClaim = claimed;
					
					
					if(resources.get(rType-1).getUnitsPresent() < claimed) {
						
						runCycle.get(i).updateState("aborted");
						System.out.println("Task "+runCycle.get(i).getTaskNumber()+" has been aborted "+time);
						processesTerminated++;
						runCycle.remove(i);
						
						
					}
					
					else {
						runCycle.get(i).activityIndex++;
						System.out.println("Task " +tasks.get(i).getTaskNumber()+ " is initiated"+ " time "+time);
					}
					
					
				}
				
				else if(runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getType().equals("terminate")) {
				
					if(!(runCycle.get(i).getCurrentState().equals("terminated"))) {
						processesTerminated++;
						runCycle.get(i).setTimeTaken(time);
						//System.out.println(processesTerminated);
						runCycle.get(i).updateState("terminated");
					}
				
				
				
				}
				
				else if(runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getType().equals("request")) {
				
					int rType = runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getResourceType();
					int rAmount = runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getNumberRequested();
					
					System.out.println(rType);
					System.out.println(rAmount);
					
					
					
					
					
					
					//if(runCycle.get(i).holding + resources.get(rType-1).getUnitsPresent() > runCycle.get(i).initialClaim) {
					if(rAmount > runCycle.get(i).initialClaim) {	
						runCycle.get(i).updateState("aborted");
						System.out.println("Task "+runCycle.get(i).getTaskNumber()+" has been aborted "+time);
						processesTerminated++;
						runCycle.remove(i);
						
						
						
					}
					
					else if(resources.get(rType-1).getUnitsPresent() < rAmount) {
						
						if(!(runCycle.get(i).getCurrentState().equals("waiting"))) {
						
							pending.add(runCycle.get(i));
							
						}
						
						runCycle.get(i).increaseWaitingTime();
						System.out.println("Task " +runCycle.get(i).getTaskNumber()+ "'s grant could not be accepted of an amount of "+rAmount+ " time "+time);
						
			
					}
					
					else {
						
						resources.get(rType-1).removeUnits(rAmount);
						runCycle.get(i).activityIndex++;
						System.out.println("Task " +runCycle.get(i).getTaskNumber()+ " has requested "+rAmount+ " time "+time);
						
						
					}
					
					
					
				}
				
				else if(runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getType().equals("release")) {
				
					
					int rType = runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getResourceType();
					int rAmount = runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getNumberReleased();
					
					//resources.get(rType-1).addUnits(rAmount);
					resources.get(rType-1).tempAdd(rAmount);
					runCycle.get(i).activityIndex++;
					System.out.println("Task " +runCycle.get(i).getTaskNumber()+ " has released "+rAmount+ " time "+time);
					
					
					
				
				
				}
				
			}
			
			
			time++;
			
		}
		
		
	
	
	}
	
	
	
	public static void fifo2() {

		int time = 0; 
		int processesTerminated = 0;
		boolean isFirst = true;
		int numberWaiting = 0;
		
		boolean inDeadlock = false;
		
		int isFirstCycle = 0;
		
		ArrayList<Task> blockedQueue = new ArrayList<Task>();
		
		ArrayList<Task> pending = new ArrayList<Task>();
		
		
		
		while(processesTerminated < tasks.size()) {
			
			
			for(int i = 0; i < resources.size(); i++)
				resources.get(i).fullyAdd();
			
			
			
			ArrayList<Task> runCycle = new ArrayList<Task>();
			
			
			runCycle.addAll(pending);
			pending.clear();
			
			//for (Task task : pending) {
				//System.out.print(task.getTaskNumber() + " pending ");
			
			//}
			
			for (Task task : tasks) {
				if (!runCycle.contains(task)) {
					runCycle.add(task);
				}
			}
			
			for (Task task : runCycle) {
					System.out.print(task.getTaskNumber() + " ");
				
			}
			
			
			for(int i = 0; i < runCycle.size(); i++) {
				
				if(runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getType().equals("compute")) {
					
					runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).decreaseNumberOfCycles();
					
					int c = runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getNumberOfCycles();
					
					if(c == 0) {
						
						runCycle.get(i).activityIndex++;
						
					}
					else
						continue;
					
					
					
				}
				
				if(runCycle.get(i).getCurrentState().equals("aborted") || runCycle.get(i).getCurrentState().equals("terminated")) {
					
					continue;
					
					
				}
				
				else if(runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getType().equals("initiate")) {
					
					
						runCycle.get(i).activityIndex++;
						System.out.println("Task " +tasks.get(i).getTaskNumber()+ " is initiated"+ " time "+time);
					
					
					
				}
				
				else if(runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getType().equals("terminate")) {
				
					if(!(runCycle.get(i).getCurrentState().equals("terminated"))) {
						processesTerminated++;
						runCycle.get(i).setTimeTaken(time);
						//System.out.println(processesTerminated);
						runCycle.get(i).updateState("terminated");
					}
				
				
				
				}
				
				else if(runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getType().equals("request")) {
				
					int rType = runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getResourceType();
					int rAmount = runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getNumberRequested();
					
					System.out.println(rType);
					System.out.println(rAmount);
					
				
					if(resources.get(rType-1).getUnitsPresent() >= rAmount) {
						
						if((runCycle.get(i).getCurrentState().equals("waiting"))) {
							
							runCycle.get(i).updateState("ready");
							numberWaiting--;
							
						}
						
						inDeadlock = false;
						
						
						resources.get(rType-1).removeUnits(rAmount);
						runCycle.get(i).activityIndex++;
						System.out.println("Task " +runCycle.get(i).getTaskNumber()+ " has requested "+rAmount+ " time "+time);
						
						
					}
					
					else {
						
						if(!(runCycle.get(i).getCurrentState().equals("waiting"))) {
						
							pending.add(runCycle.get(i));
							numberWaiting++;
							
						}
						
						runCycle.get(i).updateState("waiting");
						
						if (!inDeadlock) {
							
							//numberWaiting++;
							runCycle.get(i).increaseWaitingTime();
							System.out.println("Task " +runCycle.get(i).getTaskNumber()+ "'s grant could not be accepted of an amount of "+rAmount+ " time "+time);
						}
			
					}
					
					
					
				}
				
				else if(runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getType().equals("release")) {
				
					
					int rType = runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getResourceType();
					int rAmount = runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getNumberReleased();
					
					//resources.get(rType-1).addUnits(rAmount);
					resources.get(rType-1).tempAdd(rAmount);
					runCycle.get(i).activityIndex++;
					System.out.println("Task " +runCycle.get(i).getTaskNumber()+ " has released "+rAmount+ " time "+time);
					
					
					
				
				
				}
				
			}
			
			if (numberWaiting == tasks.size() - processesTerminated) {
				inDeadlock = true;
				for (Task task : tasks) {
					System.out.println("tasks: "+task.getCurrentState()+" num "+task.getTaskNumber());
					if (!(task.getCurrentState().equals("aborted") || task.getCurrentState().equals("terminated"))) {
						
						numberWaiting--;
						
						task.updateState("aborted");
						System.out.println("Task "+task.getTaskNumber()+" has been aborted "+time);
						processesTerminated++;
						
						int rType = task.getActivities().get(task.activityIndex).getResourceType();
						int rAmount = task.getActivities().get(task.activityIndex).getNumberRequested();
						
								
						resources.get(rType-1).tempAdd(resources.get(rType-1).getTempAdd() + rAmount);
						
						
						
						//runCycle.remove(task);
						
						
						
						//task.abort();
						//processesTerminated++;
						break;
					}
				}
			}
			
			
			time++;
			
		}
		
		
	
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void output(){
		
		int totalTimeTaken = 0;
		int totalWaitingTime = 0;
		
		for(int i = 0; i < tasks.size(); i++) {
			
			if(tasks.get(i).getCurrentState().equals("aborted"))
				System.out.println("Task "+tasks.get(i).getTaskNumber()+" aborted");
			
			else {
				double percentage = ((double)tasks.get(i).getWaitingTime() / (double)tasks.get(i).getTimeTaken())*100;
				
				totalTimeTaken = totalTimeTaken +tasks.get(i).getTimeTaken();
				totalWaitingTime = totalWaitingTime + tasks.get(i).getWaitingTime();
				System.out.println("Task "+tasks.get(i).getTaskNumber()+"\t"+ tasks.get(i).getTimeTaken()+"\t"+ tasks.get(i).getWaitingTime()+"\t"+Math.round(percentage)+"%");
				
			}
		}
		double totalPercentage = ((double)totalWaitingTime/(double)totalTimeTaken)*100;
		
		System.out.println("Total\t"+totalTimeTaken+"\t"+totalWaitingTime+"\t"+Math.round(totalPercentage)+"%");
		
		
	}

}
