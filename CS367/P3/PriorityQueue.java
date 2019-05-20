package P3;

import java.util.Comparator;

////////////////////////////////////////////////////////////////////////////////
//Main Class File:  RealTimeScheduler.java
//File:             PriorityQueue.java
//Semester:         CS367 Spring 2014
//
//Author:           Steven Wiener
//Email:            SWiener@wisc.edu
//Pair Partner:     Andrew Minneci
//Email:            minneci@wisc.edu
///////////////////////////////////////////////////////////////////////////////
public class PriorityQueue implements QueueADT<Task> {
	private Task[] queue;
	private int numItems;
	private int maxCapacity;
	Comparator<Task> compare;
	
	// Makes a Queue of tasks and sorts them by their priorities
	public PriorityQueue(Comparator<Task> comparator, int maxCapacity) {
		this.maxCapacity = maxCapacity;
		numItems=0;
		queue = new Task[maxCapacity];
		compare = comparator;
	}
	
	// Returns true if the current queue is empty
	public boolean isEmpty() {
		return (numItems == 0);
	}

	// Returns true if the current queue is Full
	public boolean isFull() {
		return (numItems == maxCapacity && numItems != 0);
	}

	// Returns the current task
	public Task peek() throws EmptyQueueException {
		if (isEmpty())
			throw new EmptyQueueException ();
		
		if (queue[1] == queue[0]) {
			Process process = new Process(queue[1].period,queue[1].compute_time);
			queue[1] = new Task(process, queue[1].getDeadline());
		}
			
		return queue[0];
	}
	
	// Removes the current task, and returns it
	public Task dequeue() throws EmptyQueueException {
		if (isEmpty()) throw new EmptyQueueException();
		
		Task item = queue[0];
		for (int i = 0; i < maxCapacity - 1; i++)
			queue[i] = queue[i+1];
		numItems--;
		for (int i = maxCapacity - 1; i > numItems; i--)
			queue[i] = null;
		if (numItems > 1)
			queue = this.heapify();
		
		return item;
	}

	// Adds a queue to the priority queue "stack"
	public void enqueue(Task item) throws FullQueueException {
		if (isFull())
			throw new FullQueueException();
		
		queue[numItems] = item;
		numItems ++;
		if (numItems > 1)
			queue = this.heapify();
	}

	// Returns the maxCapacity of the queue
	public int capacity() {
		return maxCapacity;
	}

	// Returns the current number of tasks in the queue
	public int size() {
		return numItems;
	}
	
	// Returns true if queue contains the item passed in
	public boolean contains(Task item) {
		for (int i = 0; i < numItems; i++)
			if (queue[i] == item)
				return true;
		return false;
	}
	
	// Overrides the default and prints the tasks in the queue
	public String toString() {
		String string = "";
		int count = 0;
		
		while (count < numItems) {
			if (queue[count] != null)
				string += queue[count].toString() + "\n";
			count++;
		}
		return string;
	}
	
	// Min-heapifies the list, reordering if needed, returning a list that is properly ordered
	private Task[] heapify() {
		Task left, right, temp;
		boolean stop = false;
		while (!stop) {
			stop = true;
			for (int i = 0; i < maxCapacity; i++) {
				if (queue[i] != null && (2 * i + 1 < maxCapacity || 2 * i + 2 < maxCapacity)) {
					left = queue[2 * i + 1];
					right = queue[2 * i + 2];
					if (left != null && compare.compare(queue[i], left) > 0) {
						temp = left;
						queue[2*i+1] = queue[i];
						queue[i] = temp;
						stop = false;
					} else if (right != null && compare.compare(queue[i], right) > 0) {
						temp = right;
						queue[2*i+2] = queue[i];
						queue[i] = temp;
						stop = false;
					}
				}
			}
		}
		return queue;
	}

}