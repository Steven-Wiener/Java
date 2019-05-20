/**
 * Description outlined in Word Document
 * @author Steven Wiener
 * @Version 1
 */

import java.util.Scanner;

public class FinalCircleGenerator {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		System.out.print("How many circles would you like to create? ");
		Circle_Wiener[] circles = new Circle_Wiener[in.nextInt()];
		Circle_Wiener.numberOfObjects = circles.length;
		
		for (int i = 0; i < circles.length; i++)	{
			circles[i] = new Circle_Wiener();
		}

		for (int i = 0; i < circles.length; i++)	{
			circles[i].TS();
		}

		in.close();
	}
}

class Circle_Wiener	{
	private double radius = 1;
	private boolean filled = true;
	public static int numberOfObjects;
	
	Circle_Wiener()	{
		
	}
	
	Circle_Wiener(double radius, boolean filled)	{
		this.radius = radius;
		this.filled = filled;
	}
	
	private double getRadius()	{
		return radius;
	}
	
	private void setRadius(double radius)	{
		this.radius = radius;
	}
	
	private boolean isFilled()	{
		return filled;
	}
	
	private void setFilled(boolean filled)	{
		this.filled = filled;
	}
	
	private int getNumberOfObjects()	{
		return numberOfObjects;
	}
	
	private double getArea()	{
		return  radius * radius * Math.PI;
	}
	
	private double getCircumference()	{
		return 2 * radius * Math.PI;
	}
	
	public void TS()	{
		for (int i = 0; i < numberOfObjects; i++)	{
			System.out.println("Object #: " + i);
			System.out.println("Radius: " + getRadius() + " Area: " + getArea() + " Circumference: " + getCircumference() + " Object is Filled: " + isFilled());
		}
	}
}