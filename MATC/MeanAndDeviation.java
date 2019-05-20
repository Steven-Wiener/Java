/**
 * This exercise computes the mean and standard deviation of an array by first prompting
 * 	the user for 10 numbers, entering those numbers into an array using a for loop, then displaying the
 * 	mean and standard deviation by calculating with methods for the respective formulas.
 * The mean method calculates mean by using a for loop to add all the numbers of an array, then
 * 	dividing those numbers by 10.
 * The deviation calculates deviation by using a for loop which adds each number of an array,
 * 	subtracting the mean, and squaring it, then divides this number by 9 and square rooting it.
 * @author Steven Wiener
 * @Version 1
 */

import java.util.Scanner;

public class MeanAndDeviation {
	public static void main(String[] args) {
		// Create Scanner object
		Scanner in = new Scanner(System.in);
		
		// Prompt user for ten numbers
		System.out.print("Enter ten numbers: ");
		
		// Create array, enter numbers into array using for loop
		double[] numbers = new double[10];
		for (int i = 0; i < 10; i++)	{
			numbers[i] = in.nextDouble();
		}
		
		// Display mean and standard deviation
		System.out.println("The mean is " + mean(numbers));
		System.out.println("The standard deviation is " + deviation(numbers));
		
		// Close Scanner object
		in.close();
	}
	
	/**
	 * Compute the deviation of double values
	 * @param x array used in calculation
	 * @param devation value to be returned
	 */	
	public static double deviation(double[] x)	{
		double deviation = 0;
		
		// Use for loop to calculate numerator of equation
		for (int i = 0; i < 10; i++)	{
			deviation += Math.pow(x[i] - mean(x), 2);
		}
		
		// Divide numerator by 9 and square root it to find value
		return Math.pow(deviation / 9, .5);
	}
	
	/**
	 * Compute the mean of an array of double values
	 * @param x array used in calculation
	 * @param mean value to be returned
	 */	
	public static double mean(double[] x)	{
		double mean = 0;
		
		// Use for loop to calculate numerator of equation
		for (int i = 0; i < 10; i++)	{
			mean += x[i];
		}
		
		// Divide numerator by 10 to find value
		return mean / 10;
	}

}
