/**
 * This program prompts the user for a year, month, and day of month, then uses an
 * 	algorithm to calculate the day of the week from the prompted numbers.
 * @author Steven Wiener
 * @Version 1
 */

import java.util.Scanner;

public class DayOfWeekCalculator {
	public static void main(String[] args) {
		// Create Scanner object
		Scanner in = new Scanner(System.in);
		
		// Prompt for year, month, and day of month
		System.out.print("Enter year: (e.g., 2012): ");
		int y = in.nextInt();
		System.out.print("Enter month: 1-12: ");
		int m = in.nextInt();
		System.out.print("Enter the day of the month: 1-31: ");
		int q = in.nextInt();
		
		// Convert month if needed
		if (m == 1)	{
			m = 13;
			y -= 1;
		}
		else if (m == 2)	{
			m = 14;
			y -= 1;
		}
				
		// Calculate century and year of century
		int j = y / 100;
		int k = y % 100;
		
		// Calculate day of week
		int h = (q + ((26 * (m + 1)) / 10) + k + (k / 4) + (j / 4) + (5 * j)) % 7;
		
		// Calculate & display day of week
		switch (h)	{
		case 0: System.out.print("\nDay of the week is Saturday"); break;
		case 1: System.out.print("\nDay of the week is Sunday"); break;
		case 2: System.out.print("\nDay of the week is Monday"); break;
		case 3: System.out.print("\nDay of the week is Tuesday"); break;
		case 4: System.out.print("\nDay of the week is Wednesday"); break;
		case 5: System.out.print("\nDay of the week is Thursday"); break;
		case 6: System.out.print("\nDay of the week is Friday"); break;
		}

		// Close Scanner
		in.close();
	}

}
