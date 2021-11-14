/**
 * This exercise checks whether one input list is equal to another. First the user is
 * 	prompted for list 1 (which can be any length) and then list 2 (which also can be any length). These
 *  values are assigned to the list1 and list2 arrays using a for loop, and then the arrays are sorted
 *  using the java utility Arrays.sort. An “equals” method is called to determine the correct response,
 *  and the response is printed to the console.
 * The equals method first ensures the two lists are of equal length, then checks each value using a
 * 	for loop and an if statement. The default response is to return true – but if either of the if
 * 	statements are triggered, the method returns false.
 * @author Steven Wiener
 * @Version 1
 */

import java.util.Scanner;

public class CompareTwoLists {
	public static void main(String[] args) {
		// Create Scanner object
		Scanner in = new Scanner(System.in);
		
		// Prompt user for list 1
		System.out.print("Enter list1: ");
		
		// Use for loop to assign numbers to array
		int[] list1 = new int[in.nextInt()];
		for (int i = 0; i < list1.length; i++)	{
			list1[i] = in.nextInt();
		}
		
		// Prompt user for list 2
		System.out.print("Enter list2: ");
		
		// Use for loop to assign numbers to array
		int[] list2 = new int[in.nextInt()];
		for (int i = 0; i < list2.length; i++)	{
			list2[i] = in.nextInt();
		}
		
		// Sort arrays sequentially
		java.util.Arrays.sort(list1);
		java.util.Arrays.sort(list2);
				
		// If statement to determine correct response
		if (equals(list1, list2))	{
			System.out.println("Two lists are identical");
		}
		else	{
			System.out.println("Two lists are not identical");
		}			

		// Close Scanner object
		in.close();
	}
	
	/**
	 * Determine whether two arrays are identical
	 * @param list1 array to be compared
	 * @param list2 array to be compared
	 */	
	public static boolean equals(int[] list1, int[] list2)	{
		// If statement ensures lists are of equal length
		if (list1.length != list2.length)	{
			return false;
		}
		
		// For loop checks each value
		for (int i = 0; i < list1.length; i++)	{
			// If statement returns false if number is not equal
			if (list1[i] != list2[i])	{
				return false;
			}
		}
		
		return true;
	}	
}
