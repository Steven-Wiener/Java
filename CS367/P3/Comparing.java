package P3;

////////////////////////////////////////////////////////////////////////////////
//Main Class File:  RealTimeScheduler.java
//File:             Comparing.java
//Semester:         CS367 Spring 2014
//
//Author:           Steven Wiener
//Email:            SWiener@wisc.edu
//Pair Partner:     Andrew Minneci
//Email:            minneci@wisc.edu
///////////////////////////////////////////////////////////////////////////////

import java.util.Comparator;

public class Comparing implements Comparator<Task> {

	// Takes in two tasks, and compares them to see which one should come first
	public int compare(Task e1, Task e2)	{
		return (e1.getDeadline() - e2.getDeadline());
	}

	// Removes the object class override, and returns an error.
	public boolean equals(Object o)	{
		throw new UnsupportedOperationException();
	}
}
