/**
 * This program simulates a three-number lottery game. It prompts the user for a lower
 * 	and upper bound, terminates if either bound is invalid, switches the bounds if the upper < lower,
 * 	sets the upper bound to be 2 + the lower bound if the gap isn’t large enough, and terminates if
 * 	the user picks a number outside the range. It ensures the lottery picks are not duplicates, then
 * 	picks three numbers for the lottery solutions, ensuring that none of these are duplicates.
 * 	Finally, it displays all prompted and generated numbers, and tells the user if they have won
 * 	anything.
 * @author Steven Wiener
 * @Version 1
 */

import java.util.Scanner;
import java.util.Random;

public class Lottery {

	public static void main(String[] args) {
		// Create objects for scanner and random
		Scanner in = new Scanner(System.in);
		Random r = new Random();
		
		// Explain the program
		System.out.print("This program simulates a three-number lottery game.");
		
		// Prompt for lower/upper bound
		
		System.out.print("\nPlease enter a positive lower bound for the lottery numbers:\n");
		int lowerBound = in.nextInt();
		
		System.out.print("Please enter a positive upper bound for the lottery numbers:\n");
		int upperBound = in.nextInt();
		
		// If either bound is <= 0 terminate
		
		if (lowerBound <= 0)	{
			System.out.print("Invalid lower bound.  Lower bound must be positive.  Program is now terminating.");
			System.exit(0);
		}
		else if (upperBound <= 0)	{
			System.out.print("\nInvalid upper bound.  Upper bound must be positive.  Program is now terminating.");
			System.exit(0);
		}
		
		// Switch numbers if upper < lower
		
		if (lowerBound > upperBound)	{
			int temp = lowerBound;
			lowerBound = upperBound;
			upperBound = temp;
		}
		
		// Set upper bound to be 2 + lower bound if range < 2
		
		if ((upperBound - lowerBound) < 2 )
			upperBound = lowerBound + 2;
		
		// Prompt for lottery picks
		
		System.out.print("Please enter your first lottery pick, between " + lowerBound + " and " + upperBound + ":\n");
			int firstPick = in.nextInt();
		System.out.print("Please enter your second lottery pick, between " + lowerBound + " and " + upperBound + ":\n");
			int secondPick = in.nextInt();
		System.out.print("Please enter your third lottery pick, between " + lowerBound + " and " + upperBound + ":\n");
			int thirdPick = in.nextInt();
		
		// If lottery picks are outside range terminate
		
		if (firstPick > upperBound || firstPick < lowerBound)	{
			System.out.print("You idiot! " + firstPick + " is not even in the range of values you set!");
			System.out.print("\nProgram is now terminating");
			System.exit(0);
		}
			
		// If lottery picks are duplicates terminate
		
		if (firstPick == secondPick || firstPick == thirdPick || secondPick == thirdPick)	{
			System.out.print("Invalid picks.  No repetition allowed.  Program is now terminating.");
			System.exit(0);
		}
		
		// Pick 3 random numbers for lottery
		
		int firstLottery = r.nextInt(upperBound-lowerBound+1)+lowerBound;
		int secondLottery = r.nextInt(upperBound-lowerBound+1)+lowerBound;
		int thirdLottery = r.nextInt(upperBound-lowerBound+1)+lowerBound;
		
		// Loop generating new lottery numbers until lottery numbers do not duplicate
		
		boolean duplicate = true;
		while (duplicate) {
			if (firstLottery == secondLottery || firstLottery == thirdLottery || secondLottery == thirdLottery)	{
				firstLottery = r.nextInt(upperBound-lowerBound+1)+lowerBound;
				secondLottery = r.nextInt(upperBound-lowerBound+1)+lowerBound;
				thirdLottery = r.nextInt(upperBound-lowerBound+1)+lowerBound;
			}
			else	{
				duplicate = false;
			}
		}

		// Display range, user picks, and lottery results
		
		System.out.print("The range of your lottery picks are from " + lowerBound + " to " + upperBound);
		System.out.print("\nHere are the lotto numbers:   " + firstLottery + "    " + secondLottery + "    " + thirdLottery);
		System.out.print("\nHere are your picks       :   " + firstPick + "    " + secondPick + "    " + thirdPick);
		
		// If user picks same three numbers in same order, s/he wins $10,000
		
		if (firstPick == firstLottery && secondPick == secondLottery && thirdPick == thirdLottery){
			System.out.print("\nExact match! You win $10,000!");
		}
		// If user picks same three numbers but in different order, s/he wins $3,000
		else if ((firstPick == firstLottery || firstPick == secondLottery || firstPick == thirdLottery) && (secondPick == firstLottery || secondPick == secondLottery || secondPick == thirdLottery) && (thirdPick == firstLottery || thirdPick == secondLottery || thirdPick == thirdLottery))	{
			System.out.print("\nDifferent order, but you still win $3,000!");
		}		
		// If user picks two matching numbers (order does not matter), s/he wins $2,000
		else if (((firstPick == firstLottery) && (secondPick == secondLottery)) || ((firstPick == firstLottery) && (thirdPick == thirdLottery)) || ((secondPick == secondLottery) && (thirdPick == thirdLottery)))	{
			System.out.print("\nTwo matching numbers, you win $2,000!");
		}
		// If user picks one matching number (order does not matter), s/he wins $1,000
		else if ((firstPick == firstLottery) || (secondPick == secondLottery) || (thirdPick == thirdLottery))	{
			System.out.print("\nOne matching number, you win $1,000");
		}
		// User has lost
		else
			System.out.print("\nBetter luck next time!");
		
		// Close scanner
		in.close();

	}

}
