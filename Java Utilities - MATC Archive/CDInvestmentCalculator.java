/**
 * This program displays a CD value for each month after prompting for an initial deposit
 * 	amount, annual percentage yield, and maturity period in months, then looping until the amount of
 * 	months has been reached.
 * @author Steven Wiener
 * @Version 1
 */

import java.util.Scanner;

public class CDInvestmentCalculator {
	public static void main(String[] args) {
		// Generate Scanner object
		Scanner in = new Scanner(System.in);
		
		// Prompt user for initial deposit amount, annual percentage yield, and maturity period in months
		System.out.print("Enter the initial deposit amount: ");
		double depositAmount = Math.abs(in.nextDouble());
		System.out.print("Enter annual percentage yield: ");
		double percentYield = Math.abs(in.nextDouble());
		System.out.print("Enter maturity period (number of months): ");
		int maturityPeriod = Math.abs(in.nextInt());
		
		// Display table header
		System.out.println("\nMonth CD Value");
		
		// Loop displays CD value every month
		for (int i = 1; i <= maturityPeriod; i++)	{
			depositAmount += depositAmount * percentYield / 1200;
			System.out.printf("%-6d", i);
			System.out.printf("%6.2f", depositAmount);
			System.out.println();
		}
		
		// Close scanner object
		in.close();
	}

}
