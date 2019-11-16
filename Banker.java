//package Lab3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Banker {
	
	static ArrayList<Task> tasks;
	static ArrayList<Resource> resources;
	
	static ArrayList<Task> tasks2;
	static ArrayList<Resource> resources2;

	public static void main(String[] args) throws FileNotFoundException {
		
		
		
		try {
			
			File file = new File(args[0]);
			
			
			
			Scanner input = new Scanner(file);
			Scanner input2 = new Scanner(file);
			
			
			//here i take in the input from the file and organize them by objects
			tasks = new ArrayList<Task>();
			resources = new ArrayList<Resource>();
			
			ArrayList<Activity> allActivities = new ArrayList<Activity>();
			
			int numOfTasks = Integer.parseInt(input.next());
			int numOfResourceTypes = Integer.parseInt(input.next());
			
			//adding the all the different resource types
			for(int i = 0; i < numOfResourceTypes; i++) {
				
				resources.add(new Resource(i+1,Integer.parseInt(input.next())));
				
			}
			
			//adding in each activity
			while(input.hasNext()) {
				
				allActivities.add(new Activity(input.next(),Integer.parseInt(input.next()),Integer.parseInt(input.next()),Integer.parseInt(input.next())));
				
			}
			
			// then here i am adding the activities to the correspoinding tasks
			for(int i = 0; i < numOfTasks; i++) {
				
				Task a = new Task(i+1);
				tasks.add(a);
				
				for(int j = 0; j < allActivities.size();j++) {
					
					if(allActivities.get(j).getTaskNumber() == i+1)
						a.addActivity(allActivities.get(j));
					
				}
				
			}
			
			
			// this is the same as above but for the bankers algo
			//Scanner input2 = new Scanner(file);
			tasks2 = new ArrayList<Task>();
			resources2 = new ArrayList<Resource>();
			
			ArrayList<Activity> allActivities2 = new ArrayList<Activity>();
			
			int numOfTasks2 = Integer.parseInt(input2.next());
			int numOfResourceTypes2 = Integer.parseInt(input2.next());
			
			
			for(int i = 0; i < numOfResourceTypes2; i++) {
				
				resources2.add(new Resource(i+1,Integer.parseInt(input2.next())));
				
			}
			
			while(input2.hasNext()) {
				
				allActivities2.add(new Activity(input2.next(),Integer.parseInt(input2.next()),Integer.parseInt(input2.next()),Integer.parseInt(input2.next())));
				
			}
			
			
			for(int i = 0; i < numOfTasks2; i++) {
				
				Task a = new Task(i+1);
				tasks2.add(a);
				
				for(int j = 0; j < allActivities2.size();j++) {
					
					if(allActivities2.get(j).getTaskNumber() == i+1)
						a.addActivity(allActivities2.get(j));
					
				}
				
			}
			
			
			//method callers 
			fifo2();
			System.out.println("FIFO");
			output();
			
			System.out.println();
			
			
			bankers2();
			System.out.println("BANKER'S ALGORITHM");
			output2();
			
			
			
			
		} catch (FileNotFoundException e) {
			
			//System.out.println("Please enter in the correct filename");
			//e.printStackTrace();
		}
		
		
		
		
	
		
	}
	
	public static void fifo2() {

		int time = 0; 
		int processesTerminated = 0;

		int numberWaiting = 0;
		
		boolean inDeadlock = false;
	
		// a list of the tasks that are pending/blocked
		ArrayList<Task> pending = new ArrayList<Task>();
		
		// iterate until all tasks have been terminated or aborted
		while(processesTerminated < tasks.size()) {
			
			//make all the previous released resources available
			for(int i = 0; i < resources.size(); i++)
				resources.get(i).fullyAdd();
			
			
			// this is the list of the order in which tasks are run
			ArrayList<Task> runCycle = new ArrayList<Task>();
			
			// first add in all the pending, to make them to the front of the line
			runCycle.addAll(pending);
			
			
			HashSet<Integer> set = new HashSet<Integer>();
			
			for(int i = 0; i < runCycle.size(); i++)
				set.add(runCycle.get(i).getTaskNumber());
			
			//then here we add in the ones that are not pending, add them to the back
			for(int i = 0; i < tasks.size(); i++) {
				if(!(set.contains(tasks.get(i).getTaskNumber()))) {
					set.add(tasks.get(i).getTaskNumber());
					runCycle.add(tasks.get(i));
					
					
				}
				
			}
			
			// iterating through the run cycle
			for(int i = 0; i < runCycle.size(); i++) {
				
				
				//first checks if the activity equals computer
				if(runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getType().equals("compute")) {
					
					runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).decreaseNumberOfCycles();
					
					int c = runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getNumberOfCycles();
					
					//when finished computing, move on to the next activity in line
					if(c == 0) {
						
						runCycle.get(i).activityIndex++;
						continue;
					}
					else
						continue;
					
				}
				
				//skip the aborted ir terminated tasks
				if(runCycle.get(i).getCurrentState().equals("aborted") || runCycle.get(i).getCurrentState().equals("terminated")) {
					
					continue;
					
					
				}
				
				//moves on to the next activity 
				else if(runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getType().equals("initiate")) {
					
					
						runCycle.get(i).activityIndex++;
						
					
				}
				
				// if activity is equal to terminated
				else if(runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getType().equals("terminate")) {
				
					//if not already terminated, terminate
					if(!(runCycle.get(i).getCurrentState().equals("terminated"))) {
						processesTerminated++;
						runCycle.get(i).setTimeTaken(time);
						
						runCycle.get(i).updateState("terminated");
						
						
						int index = -1;
						
						//remove from pending list
						for(int k = 0; k < pending.size(); k++)
							if(pending.get(k).getTaskNumber() == runCycle.get(i).getTaskNumber())
								index = k;
						
						if(index != -1) {
							pending.remove(index);
						
						}
						
					}
				
				
				
				}
				
				// when activity is request
				else if(runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getType().equals("request")) {
				
					int rType = runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getResourceType();
					int rAmount = runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getNumberRequested();
					
					// if activity is requesting is less than available, grant the request
					if(resources.get(rType-1).getUnitsPresent() >= rAmount) {
						
						//if already waiting, take out from waiting, decrease number of waiting
						if((runCycle.get(i).getCurrentState().equals("waiting"))) {
							
							runCycle.get(i).updateState("ready");
							numberWaiting--;
							
						}
						
						//deadlock is false right now, since one was able to request
						inDeadlock = false;
						
						//take away units from the resource
						resources.get(rType-1).removeUnits(rAmount);
						runCycle.get(i).activityIndex++;
						//increase the holding count for this specific resource
						runCycle.get(i).holding += rAmount;
						
						int index = -1;
						
						//remove from pending, since task was granted
						for(int k = 0; k < pending.size(); k++)
							if(pending.get(k).getTaskNumber() == runCycle.get(i).getTaskNumber())
								index = k;
						
						if(index != -1)
							pending.remove(index);
						
						
						
					}
					
					// if cannot satisfy request
					else {
						
						//if not already waiting, make it wait
						if(!(runCycle.get(i).getCurrentState().equals("waiting"))) {
						
							pending.add(runCycle.get(i));
							numberWaiting++;
							
						}
						
						runCycle.get(i).updateState("waiting");
						
						// if not in a deadlock, increase time waiting
						if (!inDeadlock) {
							
							runCycle.get(i).increaseWaitingTime();
							
						}
			
					}
					
				}
				
				// if activity is to release, just release the resources
				else if(runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getType().equals("release")) {
				
					
					int rType = runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getResourceType();
					int rAmount = runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getNumberReleased();
					
					//temp release means to temporary release, then make available when fully release is called at the beginning of the cycle
					resources.get(rType-1).tempAdd(rAmount);
					runCycle.get(i).holding = runCycle.get(i).holding - rAmount;
					runCycle.get(i).activityIndex++;
					
				}
				
			}
			
			
			// checks for deadlocks
			if (numberWaiting == tasks.size() - processesTerminated) {
				inDeadlock = true;
				//System.out.println("Deadlock has occurred, lowest task has been aborted");
				
				for (Task task : tasks) {
					// iterates through all the tasks checking to see which ones are waiting, to then release the first lowest
					if(task.getCurrentState().equals("waiting")) {
						numberWaiting--;
						//abort the task
						task.updateState("aborted");
						System.out.println("Deadlock has occurred, lowest task has been aborted");
						processesTerminated++;
						
						int rType = task.getActivities().get(task.activityIndex).getResourceType();
						int rAmount = task.getActivities().get(task.activityIndex).getNumberRequested();
						
						
						resources.get(rType-1).tempAdd(task.holding);
						task.holding = 0;
						
						//break if resources released is more than 0, because some are aborted, but deadlock remains because they dont release any resources
						if(resources.get(rType-1).getTempAdd()  > 0)
							break;
						
					}
				}
			}
			
			
			int w = 0;
			//checks for number of waiting tasks in the run cycle
			for(int i = 0; i < runCycle.size();i++)
				if(runCycle.get(i).getCurrentState().equals("waiting"))
					w++;
			
			int rsize = 0;
			
			//checks the number of active tasks
			for(int i = 0; i < runCycle.size();i++)
				if(!(runCycle.get(i).getCurrentState().equals("aborted")) || (runCycle.get(i).getCurrentState().equals("terminated")))
					rsize++;
			//checks to see if the waiting is not equal to number of active, increase time. or if active tasks = 1, then increase time
			if(w   != rsize || rsize == 1)
				time++;
			
		
		}
		
		
	
	
	}
	
	// bankers ago
	// i used the majority of my code from fifo, changed some stuff around to make the bankers algo
	
	// I will comment on the parts that are different from fifo
	
	
	public static void bankers2() {

		int time = 0; 
		int processesTerminated = 0;

		int numberWaiting = 0;
		
		
		ArrayList<Task> pending = new ArrayList<Task>();
		
		
		
		while(processesTerminated < tasks2.size()) {
			
			
			ArrayList<Task> runCycle = new ArrayList<Task>();
			
			
			runCycle.addAll(pending);
			
			
			HashSet<Integer> set = new HashSet<Integer>();
			
			for(int i = 0; i < runCycle.size(); i++)
				set.add(runCycle.get(i).getTaskNumber());
			
			for(int i = 0; i < tasks2.size(); i++) {
				if(!(set.contains(tasks2.get(i).getTaskNumber()))) {
					set.add(tasks2.get(i).getTaskNumber());
					runCycle.add(tasks2.get(i));
					
					
				}
				
			}
			
			
			for(int i = 0; i < runCycle.size(); i++) {
				
				if(runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getType().equals("compute")) {
					
					runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).decreaseNumberOfCycles();
					
					int c = runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getNumberOfCycles();
					
					if(c == 0) {
						
						runCycle.get(i).activityIndex++;
						continue;
					}
					else
						continue;
					
				}
				
				if(runCycle.get(i).getCurrentState().equals("aborted") || runCycle.get(i).getCurrentState().equals("terminated")) {
					
					continue;
					
					
				}
				
				else if(runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getType().equals("initiate")) {
					
						int claimed = runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getInitialClaim();
						
						
						int rType = runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getResourceType();
						
						runCycle.get(i).holding2.put(rType, 0);
						runCycle.get(i).initialClaim2.put(rType, claimed);
						
						// checks if the initial claim is greater than the units present, if so. abort
						if(resources2.get(rType-1).getUnitsPresent() < claimed) {
							
							System.out.println("Task "+runCycle.get(i).getTaskNumber()+ " has claimed more resources than there is present. Aborted");
							runCycle.get(i).updateState("aborted");
					
							processesTerminated++;
							
							//temporarily release resources
							resources2.get(rType-1).tempAdd(runCycle.get(i).holding2.get(rType).intValue());
							
							//make all the resource holdings 0, since we have aborted the task
							for (HashMap.Entry mapElement : runCycle.get(i).holding2.entrySet()) {
								
								mapElement.setValue(0);
								
							}
							
							
						}
						
						else {
							runCycle.get(i).activityIndex++;
							
						}
						
						
						
					
				}
				
				else if(runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getType().equals("terminate")) {
				
					//handles the activities which say to terminate
					if(!(runCycle.get(i).getCurrentState().equals("terminated"))) {
						processesTerminated++;
						runCycle.get(i).setTimeTaken(time);
						
						runCycle.get(i).updateState("terminated");
						
						
						for (HashMap.Entry mapElement : runCycle.get(i).holding2.entrySet()) {
							
							mapElement.setValue(0);
							
						}
						
						
						int index = -1;
						
						for(int k = 0; k < pending.size(); k++)
							if(pending.get(k).getTaskNumber() == runCycle.get(i).getTaskNumber())
								index = k;
						
						if(index != -1) {
							pending.remove(index);
						
						}
						
					}
				
				
				
				}
				
				// handles the requests
				else if(runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getType().equals("request")) {
				
					int rType = runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getResourceType();
					int rAmount = runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getNumberRequested();
					
					
					// checks if the request amount + the current holdings is greater than initial meaning that the request exceeds the claim. and aborts
					if(rAmount + runCycle.get(i).holding2.get(rType).intValue() > runCycle.get(i).initialClaim2.get(rType).intValue()) {	
					
						runCycle.get(i).updateState("aborted");
						
						processesTerminated++;
						
						resources2.get(rType-1).tempAdd(runCycle.get(i).holding2.get(rType).intValue());
						
						for (HashMap.Entry mapElement : runCycle.get(i).holding2.entrySet()) {
							
							mapElement.setValue(0);
							
						}
						
						
					}
					
					
					// checks to see if it is safe to run, if not, make it wait
					else if(!(isSafe(runCycle.get(i),rType, rAmount, runCycle.get(i).activityIndex))) {
		
						if(!(runCycle.get(i).getCurrentState().equals("waiting"))) {
							
							pending.add(runCycle.get(i));
							numberWaiting++;
							
						}
						runCycle.get(i).updateState("waiting");
						runCycle.get(i).increaseWaitingTime();
						
			
					}
					
					// else we can grant the request. meaning the task is safe
					else {
						
						resources2.get(rType-1).removeUnits(rAmount);
						runCycle.get(i).activityIndex++;
						
						int b = runCycle.get(i).holding2.get(rType).intValue();
						
						int value = (b+rAmount);
						runCycle.get(i).holding2.put(rType, value);
						
						
						int index = -1;
						//remove from pending, since request was granted 
						for(int k = 0; k < pending.size(); k++)
							if(pending.get(k).getTaskNumber() == runCycle.get(i).getTaskNumber())
								index = k;
						
						if(index != -1)
							pending.remove(index);
						
						
					}
					
					
					
				}
				
				else if(runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getType().equals("release")) {
				
					//releases the resources
					int rType = runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getResourceType();
					int rAmount = runCycle.get(i).getActivities().get(runCycle.get(i).activityIndex).getNumberReleased();
					
					resources2.get(rType-1).tempAdd(rAmount);
					
					int value = runCycle.get(i).holding2.get(rType).intValue()-rAmount;
					
					runCycle.get(i).holding2.put(rType, value);
					
					
					runCycle.get(i).activityIndex++;
				
				
				}
				
			}
			
			for(int i = 0; i < resources2.size(); i++)
				resources2.get(i).fullyAdd();
			
			// time is increased
			time++;
			
		}
		
	
	
	
	}
	
	// this is my method to check if a task is safe to run
	public static boolean isSafe(Task task, int rType, int rAmount, int activityIndex) {
		
		
		int resourceAmountLeft = resources2.get(rType-1).getUnitsPresent();
		
		// this for loop checks to see if the amount of resource left, for each resource, is less than the initial claim - the current holdings(the need).
		// if it is less, then it is not safe to grant
		for(int i = 0; i < resources2.size(); i++) {
			
			rType = resources2.get(i).getType();
			resourceAmountLeft = resources2.get(i).getUnitsPresent();
			
			if(resourceAmountLeft < task.initialClaim2.get(rType).intValue() - task.holding2.get(rType).intValue())
				return false;
			
			
		}
		
		return true;
		
	}
	
	// method which prints the output of the fifo algo
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
	
	// method which prints the output of the bankers algo
	public static void output2(){
		
		int totalTimeTaken = 0;
		int totalWaitingTime = 0;
		
		for(int i = 0; i < tasks2.size(); i++) {
			
			if(tasks2.get(i).getCurrentState().equals("aborted"))
				System.out.println("Task "+tasks2.get(i).getTaskNumber()+" aborted");
			
			else {
				double percentage = ((double)tasks2.get(i).getWaitingTime() / (double)tasks2.get(i).getTimeTaken())*100;
				
				totalTimeTaken = totalTimeTaken +tasks2.get(i).getTimeTaken();
				totalWaitingTime = totalWaitingTime + tasks2.get(i).getWaitingTime();
				System.out.println("Task "+tasks2.get(i).getTaskNumber()+"\t"+ tasks2.get(i).getTimeTaken()+"\t"+ tasks2.get(i).getWaitingTime()+"\t"+Math.round(percentage)+"%");
				
			}
		}
		double totalPercentage = ((double)totalWaitingTime/(double)totalTimeTaken)*100;
		
		System.out.println("Total\t"+totalTimeTaken+"\t"+totalWaitingTime+"\t"+Math.round(totalPercentage)+"%");
		
		
	}

}

