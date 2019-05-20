/**
 * The program imports the scanner class, creates a scanner object, and prompts the user to
 * 	enter a value for investment amount, interest rate, and number of years. It then calculates the
 * 	monthly interest rate as well as the future investment value, and then prints the calculation to the 
 * 	console.
 * @author Steven Wiener
 * @Version 1
 */

import java.util.Scanner;

public class InvestmentCalculator {

	public static void main(String[] args) {
		// Create a scanner object
		Scanner in = new Scanner(System.in);
		
		// Prompt the user to enter a value for investment amount, interest rate, and years
		System.out.print("Enter investment amount: ");
		double investmentAmount = in.nextDouble();
		
		System.out.print("Enter annual interest rate in percentage: ");
		double annualInterestRate = in.nextDouble();
		
		System.out.print("Enter number of years: ");
		int numberOfYears = in.nextInt();
		
		// Obtain monthly interest rate
		double monthlyInterestRate = annualInterestRate / 1200;
		
		// Calculate future investment value
		double futureInvestmentValue = investmentAmount * Math.pow(1 + monthlyInterestRate, numberOfYears * 12);
		
		// Print calculation to console
		System.out.print("Accumulated value is $" + (int)(futureInvestmentValue * 100) / 100.0);
		
		// Close scanner
		in.close();
		
	}

}