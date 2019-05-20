package P3;

////////////////////////////////////////////////////////////////////////////////
//Main Class File:  RealTimeScheduler.java
//File:             ProcessGenerator.java
//Semester:         CS367 Spring 2014
//
//Author:           Steven Wiener
//Email:            SWiener@wisc.edu
//Pair Partner:     Andrew Minneci
//Email:            minneci@wisc.edu
///////////////////////////////////////////////////////////////////////////////

import java.util.*;

public class ProcessGenerator {
	ArrayList<Process> list;
	Comparing compare;

	/**
	 *  Constructor to create the new Process Generator
	 *  @return A new ProcessGenerator object
	 */
	public ProcessGenerator(){
		compare = new Comparing();
		list = new ArrayList<Process>();		
	}

	/**
	 *  isEmpty returns if the current queue is empty
	 *  @param p processing time
	 *  @param c max time
	 */
	void addProcess(int p, int c){
		list.add(new Process(p, c));
	}

	/**
	 *  getTasks takes in an integer that is the max "running time" of a task,
	 *  and then computes the tasks created.
	 *  @param t max running time
	 *  @return ArrayList of Tasks to be completed
	 */
	ArrayList<Task> getTasks(int t){
		ArrayList<Task> taskList = new ArrayList<Task>();
		for (int i = 0; i < list.size(); i++) {
			Process p = list.get(i);
			if (t%p.getPeriod() == 0) {
				Task curr = new Task(p,t);
				taskList.add(curr);
			}
		}
		return taskList;
	}
}
