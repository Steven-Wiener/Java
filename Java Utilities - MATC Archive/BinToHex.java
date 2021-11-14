/**
 * This program prompts a user for a binary number and converts it to hexadecimal before printing it
 * 	to the console.
 * @author Steven Wiener
 * @Version 1
 */

import java.util.Scanner;
public class BinToHex {
	public static void main(String[] args) {
		// Create Scanner object
		Scanner in = new Scanner(System.in);
		
		// Prompt user for binary number
		System.out.println("Enter a binary number:");
		System.out.println(binaryToHex(in.nextLine()).toUpperCase());

		// Close in
		in.close();
	}
	
	/**
	 * This method returns a hex value for an inputted binary value
	 * @param binaryValue Inputted value to be converted 
	 */
	public static String binaryToHex(String binaryValue)	{
		// Return Hex Value
		return Integer.toHexString(Integer.parseInt(binaryValue, 2));
	}

}
