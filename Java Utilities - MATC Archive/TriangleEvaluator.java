/**
 * This program prompts the user for three points – (x0, y0), (x1, y1), and (x2, y2). It
 * 	then sends these values to three methods – first checking if point 3 is left of the line
 * 	(when (x1 - x0) * (y2 - y0) - (x2 - x0) * (y1 - y0)) > 0 ), then whether it is on the same line
 * 	(if the same value is equal to 0 AND if the last point is left or right of the line from point 1 to
 * 	2), and then checks whether the value is ONLY equal to 0. If it doesn’t meet any of these
 * 	conditions, the third point must be on the right side of the line. Based on the values returned by
 * 	these three methods, the console prints the result, and then the code closes the scanner object.
 * @author Steven Wiener
 * @Version 1
 */

import java.util.Scanner;

public class TriangleEvaluator {
	public static void main(String[] args) {
		// Generate Scanner object
		Scanner in = new Scanner(System.in);
		
		// Prompt user for three points
		System.out.print("Enter three points for p0, p1, and p2: ");
		double x0 = in.nextDouble();
		double y0 = in.nextDouble();
		double x1 = in.nextDouble();
		double y1 = in.nextDouble();
		double x2 = in.nextDouble();
		double y2 = in.nextDouble();
		
		// Checks if p2 is on the left of the line, displays message if true
		if (leftOfTheLine(x0, y0, x1, y1, x2, y2))	{
			System.out.println("(" + x2 + ", " + y2 + ") is on the left side of the line from (" + x0 + ", " + y0 + ") to (" + x1 + ", " + y1 + ")");
		}
		// Checks if p2 is on the same line, displays message if true
		else if (onTheSameLine(x0, y0, x1, y1, x2, y2))	{
			System.out.println("(" + x2 + ", " + y2 + ") is on the same line from (" + x0 + ", " + y0 + ") to (" + x1 + ", " + y1 + ")");
		}
		// Checks if p2 is on the line segment, displays message if true
		else if (onTheLineSegment(x0, y0, x1, y1, x2, y2))	{
			System.out.println("(" + x2 + ", " + y2 + ") is on the line segment from (" + x0 + ", " + y0 + ") to (" + x1 + ", " + y1 + ")");
		}
		// Else p2 must be on the right of the line, displays message
		else	{
			System.out.println("(" + x2 + ", " + y2 + ") is on the right side of the line from (" + x0 + ", " + y0 + ") to (" + x1 + ", " + y1 + ")");
		}
		
		// Close Scanner object
		in.close();
	}
	
	/** Return true if point (x2, y2) is on the left side of the 
	* directed line from (x0, y0) to (x1, y1) 
	* @param x0 x value for p0
	* @param y0 y value for p0
	* @param x1 x value for p1
	* @param y1 y value for p1
	* @param x2 x value for p2
	* @param y2 y value for p2 
	*/ 
	public static boolean leftOfTheLine(double x0, double y0, double x1, double y1, double x2, double y2)	{
		if (((x1 - x0) * (y2 - y0) - (x2 - x0) * (y1 - y0)) > 0)	{
			return true;
		}
		return false;
	}
	
	/** Return true if point (x2, y2) is on the
	* same line from (x0, y0) to (x1, y1) 
	* @param x0 x value for p0
	* @param y0 y value for p0
	* @param x1 x value for p1
	* @param y1 y value for p1
	* @param x2 x value for p2
	* @param y2 y value for p2 
	*/ 
	public static boolean onTheSameLine(double x0, double y0, double x1, double y1, double x2, double y2)	{
		if ((((x1 - x0) * (y2 - y0) - (x2 - x0) * (y1 - y0)) == 0) && (((x2 < x0) && (y2 < y0)) || ((x2 > x1) && (y2 > y1))))	{
			return true;
		}
		return false;
	}
	
	/** Return true if point (x2, y2) is on the 
	* line segment from (x0, y0) to (x1, y1) 
	* @param x0 x value for p0
	* @param y0 y value for p0
	* @param x1 x value for p1
	* @param y1 y value for p1
	* @param x2 x value for p2
	* @param y2 y value for p2 
	*/ 
	public static boolean onTheLineSegment(double x0, double y0, double x1, double y1, double x2, double y2)	{
		if (((x1 - x0) * (y2 - y0) - (x2 - x0) * (y1 - y0)) == 0)	{
			return true;
		}
		return false;
	}
}
