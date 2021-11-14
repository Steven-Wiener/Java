/**
 * This program performs several types of mathematical calculations, including int power,
 * 	factorial, square root, cube root, exponential, sine, cosine, and int to binary conversion
 * @author Steven Wiener
 * @Version 1
 */

import java.util.Scanner;

public class Calculator {
	public static void main(String[] args) {
		// Create Scanner object
		Scanner in = new Scanner(System.in); 

		// Explain program
		System.out.print("This program performs several types of mathematical calculations\n");
		
		// Start loop to repeat menu until user exits
		boolean exit = false;
		while (exit == false)	{
		
		// Explain program, prompt for choice
		System.out.print("================================================================\n"
				+ "Your menu of choices is:\n"
				+ "1) Compute integer powers of a number.\n"
				+ "2) Compute a factorial.\n"
				+ "3) Compute a square root.\n"
				+ "4) Compute a cube root.\n"
				+ "5) Compute an exponential.\n"
				+ "6) Compute the sine of an angle.\n"
				+ "7) Compute the cosine of an angle.\n"
				+ "8) Convert a positive integer to binary.\n"
				+ "9) Exit\n"
				+ "Please enter your menu choice:\n");
		int choice = in.nextInt();

		// Use switch statement to organize menu
			switch (choice)	{
			case 1:	System.out.println("You chose to compute integer powers of a number.\n"
					+ "Enter any number for the base:");
					// User inputs base
					double base = in.nextDouble();
					System.out.println("Enter a non-negative integer for the exponent:");
					// User inputs exponent
					int exp = in.nextInt();
					// While loop ensures exponent is non-negative
					while (exp < 0)	{
						System.out.println("Error: exponent was negative!\n"
								+ "Enter a non-negative integer for the exponent:");
						exp = in.nextInt();						
					}
					// Generate needed variables
					double posResult = base;
					double negResult = base;
					int i = 1;
					// For loop calculates positive result
					for (i = 1; i < exp; i++)	{
						posResult = posResult * base;
					}
					// For loop calculates negative result
					for (i = -1; i < exp; i++)	{
						negResult = negResult / base;
					}
					// Display results
					System.out.println(base + "^" + exp + " = " + posResult + "\n"
							+ base + "^(-" + exp + ") = "+ negResult);
				break;
			case 2: System.out.println("You chose to compute n!, the factorial of a non-negative integer.\n"
					+ "Enter a non-negative integer for n:");
					// User inputs n
					int n = in.nextInt();
					// While loop ensures n is non-negative
					while (n < 0)	{
						System.out.println("Error: n was negative!\n"
								+ "Enter a non-negative integer for n:");
						n = in.nextInt();						
					}
					// If statements determine which type of variable should be used for the result
					if (n >= 172)	{
						System.out.println(n + "! is too large for this program to calculate!");
					}
					else if (n > 20)	{
						double result = 1;
						// For loop calculates result
						for (i = 1; i < (n + 1); i++)	{
							result = result * i;
						}
						// Display result
						System.out.println(n + "! = "+ result);
					}
					else	{
						long result = 1;
						// For loop calculates result
						for (i = 1; i < (n + 1); i++)	{
							result = result * i;
						}
						// Display result
						System.out.println(n + "! = "+ result);
					}
				break;
			case 3: System.out.println("You chose to compute a square root.\n"
					+ "Please enter any non-negative number for the radicand:");
					// User inputs radicand
					double squareRadicand = in.nextDouble();
					// While loop ensures r is non-negative
					while (squareRadicand < 0)	{
						System.out.println("Error: negative radicand!\n"
						+ "Please enter any non-negative number for radicand:");
						squareRadicand = in.nextDouble();						
					}
					// User inputs tolerance
					System.out.println("Please enter a (small) non-negative number for the tolerance:");
					double squareTolerance = Math.abs(in.nextDouble());
					// Compute square root using loop
					double squareApproximation = squareRadicand / 2;
					double squareGuess;
					int squareCount = 0;
					do {
						squareGuess = squareApproximation;
						squareApproximation = (squareGuess + (squareRadicand / squareGuess)) / 2;
						squareCount += 1;
					} while (((squareApproximation + squareGuess) / 2) - squareApproximation > squareTolerance);
					// Display results
					System.out.println("     sqrt(" + squareRadicand + ") = " + squareApproximation + "(after " + squareCount + " iterations)\n"
							+ 		   "Math.sqrt(" + squareRadicand + ") = " + Math.sqrt(squareRadicand));
				break;
			case 4: System.out.println("You chose to compute a cube root.\n"
					+ "Please enter any number for the radicand:");
					// User inputs radicand
					double cubeRadicand = in.nextDouble();
					// User inputs tolerance
					System.out.println("Please enter a (small) non-negative number for the tolerance:");
					double cubeTolerance = Math.abs(in.nextDouble());
					// Compute cube root using loop
					double cubeApproximation = cubeRadicand / 3;
					double cubeGuess;
					int cubeCount = 0;
					do {
						cubeGuess = cubeApproximation;
						cubeApproximation = ((2 * cubeGuess) + (cubeRadicand / (Math.pow(cubeGuess, 2)))) / 3;
						cubeCount += 1;
					} while (Math.abs(((cubeApproximation + cubeGuess) / 2) - cubeApproximation) > Math.abs(cubeTolerance));
					// Display results
					System.out.println("     cbrt(" + cubeRadicand + ") = " + cubeApproximation + "(after " + cubeCount + " iterations)\n"
							+ 		   "Math.cbrt(" + cubeRadicand + ") = " + Math.cbrt(cubeRadicand));
				break;
			case 5: System.out.println("You chose to compute an exponential, e^x:\n"
					+ "Please enter any number for the exponent:");
					// User inputs exponent
					double exponent = in.nextDouble();
					// User inputs tolerance
					System.out.println("Please enter a (small) non-negative number for the tolerance:");
					double exponentTolerance = Math.abs(in.nextDouble());
					
					int exponentCount = 1;
					double exponentResult = 1;
					double item = 1;
					while (Math.abs(exponentResult) > exponentTolerance)	{
						// secondCount += exponentCount;
						// exponentResult = exponentResult + ((1 / secondCount) * Math.pow(exponent, exponentCount));
						item = exponentResult / exponent; 
						exponentResult += item;
						exponentCount += 1;
						System.out.println(exponentCount + "\n" + "\n" + exponentResult);
					}
					// Display results
					System.out.println("      e^(" + exponent + ") = " + exponentResult + "(after " + exponentCount + " iterations)\n"
							+ 		   "Math.exp(" + exponent + ") = " + Math.exp(exponent));
				break;
			case 6: System.out.println("You chose to compute the sine of an angle.\n"
					+ "Please enter any number for the angle (the angle will interpreted as being measured in radians):");
					// User inputs angle
					double sineAngle = in.nextDouble();
					// User inputs tolerance
					System.out.println("Please enter a (small) non-negative number for the tolerance:");
					double sineTolerance = Math.abs(in.nextDouble());
					// Compute cube root using loop
					double sineResult = 0;
					int sineCount = 1;
					do {
						sineResult += sineResult * (-Math.pow(sineAngle, 2)) / 12; 
						sineCount += 1;
					} while (Math.abs(sineResult) > Math.abs(sineTolerance));
					// Display results
					System.out.println("     sin(" + sineAngle + ") = " + sineResult + "(after " + sineCount + " iterations)\n"
							+ 		   "Math.sin(" + sineAngle + ") = " + Math.sin(sineAngle));
				break;
			case 7: //Compute the cosine of an angle;
				break;
			case 8: //Convert a positive integer to binary;
				break;
			case 9: exit = true;
				break;
		
			default: System.out.println("Invalid menu choice.");
			}
		}
		
		
		// Close Scanner
		in.close();
	}

}
