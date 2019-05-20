package P3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

///////////////////////////////////////////////////////////////////////////////
//Title:            RealTimeScheduler
//Files:            RealTimeScheduler.java
//Semester:         CS367 Spring 2014
//
//Author:           Steven Wiener
//Email:            SWiener@wisc.edu
//Pair Partner:     Andrew Minneci
//Email:            minneci@wisc.edu
///////////////////////////////////////////////////////////////////////////////
public class RealTimeScheduler {

	/**
	 *  The Main class, that reads in the file, making Circular and Priority 
	 *  Queues that will handle the tasks that are later in the file
	 * @param args One file name, containing the process data
	 */
	public static void main(String[] args){
		try {
			File file = new File(args[0]);
			Scanner scnr = new Scanner(file);
			ComputeResourceGenerator crg = new ComputeResourceGenerator(Integer.parseInt(scnr.nextLine()));
			CircularQueue cq = new CircularQueue(Integer.parseInt(scnr.nextLine()));
			Comparing Compare = new Comparing();
			PriorityQueue pq = new PriorityQueue(Compare, Integer.parseInt(scnr.nextLine()));
			ProcessGenerator pg = new ProcessGenerator();
			ArrayList<Integer> process = new ArrayList<Integer>();
			
			while (scnr.hasNextLine()) {
				String currLine = scnr.nextLine();
				String[] curr = currLine.split(" ");
				pg.addProcess(Integer.parseInt(curr[0]), Integer.parseInt(curr[1]));
				process.add(Integer.parseInt(curr[0]));
			}
			scnr.close();
			int timeInterval = 1;
			for (int i = 0; i < process.size(); i++)
				timeInterval = (timeInterval * process.get(i)) / LCM(timeInterval, process.get(i));
			for (int i = 0; i < timeInterval; i++) {
				try {
					while (!cq.isEmpty())
						cq.dequeue();
					
					ArrayList<ComputeResource> currRe = crg.getResources();
					for (int j = 0; j < currRe.size(); j++)
						cq.enqueue(currRe.get(j));
					
					ArrayList<Task> currTasks = pg.getTasks(i);
					for (int j = 0; j < currTasks.size(); j++)
						if (!pq.contains(currTasks.get(j)) && pq.capacity() > pq.size())
							pq.enqueue(currTasks.get(j));
					
					for (int j = 0; j < cq.size(); j++)
						if (pq.peek() != null)
							pq.peek().updateProgress(cq.dequeue().getValue());
					
					if (pq.peek() != null && pq.peek().isComplete())
						pq.dequeue();
					
					if (pq.peek() != null && pq.peek().missedDeadline(i)) {
						System.out.println("Deadline missed at timestep " + i);
						System.exit(-1);
					}
				} catch(FullQueueException e) {
					//System.out.println("Queue is full");
					//System.exit(1);
				} catch (EmptyQueueException e) {
					//System.out.println("Queue is empty");
					//System.exit(1);
				}
			}
			System.out.println("Scheduling complete after " + timeInterval + " timesteps");
		} catch (FileNotFoundException e) {
			System.out.println("Error: Cannot access input file");
		} 
	}

	/**
	 *  LCM finds the least common multiple to two numbers
	 * @param a, b: Two Integers to compare
	 * @return the Least Common Multiple, in an int.
	 */
	private static int LCM (int a, int b){
		while (b != 0) {
			int t = b;
			b = a % b;
			a = t;
		}
		return a;
	}
}