package P3;

import java.util.ArrayList;

////////////////////////////////////////////////////////////////////////////////
//Main Class File:  RealTimeScheduler.java
//File:             CircularQueue.java
//Semester:         CS367 Spring 2014
//
//Author:           Steven Wiener
//Email:            SWiener@wisc.edu
//Pair Partner:     Andrew Minneci
//Email:            minneci@wisc.edu
///////////////////////////////////////////////////////////////////////////////

public class CircularQueue implements QueueADT<ComputeResource> {
	private ArrayList<ComputeResource> queue;
	private int head;
	private int tail;
	private int numItems;
	private int maxCapacity;
	
	/**
	 *  The constructor creates a new circular queue with the given capacity
	 * @params maxCapacity Integer Max Capacity
	 * @return A CircularQueue Object
	 */
	public CircularQueue(int maxCapacity)	{
		head = 0;
		tail = 0;
		numItems = 0;
		this.maxCapacity = maxCapacity;
		queue = new ArrayList<ComputeResource>(maxCapacity);
	}

	// Returns true if the current queue is empty
	public boolean isEmpty() {
		return (numItems == 0);
	}

	// Returns true if the current queue is full
	public boolean isFull() {
		return (numItems == maxCapacity);
	}

	// Returns the current Resource to be evaluated.
	public ComputeResource peek() throws EmptyQueueException {
		if (queue.isEmpty())
			throw new EmptyQueueException ();
		
		return queue.get(head);
	}

	// Returns the current Resource to be removed from the queue
	public ComputeResource dequeue() throws EmptyQueueException {
		if (isEmpty())
			throw new EmptyQueueException();
		
		ComputeResource item = queue.get(head);
		queue.remove(head);
		head = (head + 1) % maxCapacity;
		numItems--;
		
		return item;
	}

	// Adds a ComputeResource item to the queue, if possible
	public void enqueue(ComputeResource item) throws FullQueueException {
		if (isFull())
			throw new FullQueueException();
		
		queue.add(item);
		tail = (tail + 1) % maxCapacity;
		numItems++;
	}

	// Returns the maxCapacity of the queue
	public int capacity() {
		return maxCapacity;
	}

	// Returns the current size of the queue
	public int size() {
		return numItems;
	}
	
	// Overrides the default and prints the tasks in the queue
	public String toString()	{
		String string = "";
		int count = 0;
		
		while (count < numItems) {
			if (queue.get(count) != null)
				string += queue.get(count).toString() + "\n";
			count++;
		}
		
		return string;
	}
	
}
