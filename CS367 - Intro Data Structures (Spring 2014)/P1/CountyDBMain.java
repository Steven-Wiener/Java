package P1;
///////////////////////////////////////////////////////////////////////////////
// Title:            CountyDBMain
// Files:            CountyDBMain.java
// Semester:         CS367 Spring 2014
//
// Author:           Steven Wiener
// Email:            SWiener@wisc.edu
// Pair Partner:     Andrew Minneci
// Email:            minneci@wisc.edu
///////////////////////////////////////////////////////////////////////////////

import java.util.*;
import java.io.*;

/**
 * CountyDBMain class uses a CountyDatabase to represent and process 
 * information about counties and storms
 * @author Steven Wiener, Andrew Minneci
 */
public class CountyDBMain {
	/**
	 * The Main method is broken down into four steps:
	 * 1.) Check whether exactly one command-line argument is given;
	 *           if not, display "Usage: java CountyDBMain FileName" and quit.
	 * 2.) Check whether the input file exists and is readable; 
	 *           if not, display "Error: Cannot access input file" and quit.
	 * 3.) Load the data from the input file and use it to 
	 *           construct a county database.	 * 
	 * 4.) Prompt the user to enter command options and process
	 *           them until the user types x for exit.
	 * @param (String args) (file name of current database)
	 */
	public static void main(String[] args) {
		/** Step 1: Check whether exactly one command-line argument is given */
		if (args.length != 1) {
			System.out.println("Usage: java CountyDBMain FileName");
			System.exit(-1);
		}

		/** Step 2: Check whether the input file exists and is readable ***/
		try {
			Scanner scnr = new Scanner (new File(args[0]));
		} catch (FileNotFoundException e) {
			System.out.println("Error: Cannot access input file");
			System.exit(-1);
		}

		/** Step 3: Load the data from the input file and use it to 
		 *  construct a county database. Note: counties and storms are to be 
		 *  added to the database in the order in which they appear in the text
		 *  file. 
		 */
		ArrayList<String> fileInput = new ArrayList<String>();
		CountyDatabase cdb = new CountyDatabase();

		try {
			Scanner scnr = new Scanner (new File(args[0]));
			while (scnr.hasNextLine())
				fileInput.add(scnr.nextLine());
			scnr.close();


			for (int i = 0; i<fileInput.size(); i++) {
				Scanner input = new Scanner(fileInput.get(i));
				input.useDelimiter(",");
				String county = input.next().trim();
				cdb.addCounty(county);


				String name = input.next();
				String date = input.next();

				Integer damage = 0;
				if (input.hasNextInt())
					damage = input.nextInt();

				Storm storm = new Storm(name, date, damage);

				cdb.addStorm(storm, county);


				if (input.hasNextLine())
					input.nextLine();
			}
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

		/** Step 4: Prompt the user to enter command options and 
		 *  process them until the user types x for exit. 
		 */
		boolean stop = false;
		while (!stop) {
			Scanner stdin = new Scanner(System.in);
			System.out.println("Enter Options");
			String input = stdin.nextLine();
			String remainder = null;
			if (input.length() > 0) {
				char option = input.charAt(0);
				if (input.length() > 1)
					remainder = input.substring(1).trim();

				switch (option) {
					case 'a': {
						List<Storm> storms = cdb.getStormsWithDamageAmount(Integer.parseInt(remainder));
	
						if (storms == null) {
							System.out.println("no storms found");
						} else {
							Iterator<Storm> stormItr = storms.iterator();
							while (stormItr.hasNext()) {
								Storm currStorm = stormItr.next();
								System.out.println(currStorm.getName() + ", " + currStorm.getDate());
							}
						}
						System.out.println("");
						break;
					} case 'c': {
						List<Storm> storms = cdb.getStormsFromCounty(remainder);
	
						if (storms == null) {
							System.out.println("county not found");
						} else {
							Iterator<Storm> stormItr = storms.iterator();
							while (stormItr.hasNext())	{
								Storm currStorm = stormItr.next();
								System.out.println(currStorm.getName() + ", " 
										+ currStorm.getDate() + ", " 
										+ currStorm.getDamageAmount());
							}
						}
						System.out.println("");
						break;
					} case 'd': {
						List<Storm> storms = cdb.getStormsWithDate(remainder);
	
						if (storms == null) {
							System.out.println("storm not found");
						} else {
							Iterator<Storm> stormItr = storms.iterator();
							while (stormItr.hasNext()) {
								Storm currStorm = stormItr.next();
								System.out.println(currStorm.getName() + ", "
										+ currStorm.getDamageAmount());
							}
						}
						System.out.println("");
						break;
					} case 'i':{
						int numStorms = 0;
						int mostStorms = 0;
						int leastStorms = 999;
						Iterator<County> countyItr = cdb.iterator();
						List<County> counties = new ArrayList<County>();
						
						while (countyItr.hasNext())
							counties.add(countyItr.next());
						Iterator<County> newItr = counties.iterator();
						while (newItr.hasNext()) {
							List<Storm> storms = cdb.getStormsFromCounty(newItr.next().getName());
							int countyStorms = storms.size();
							numStorms += countyStorms;
							if (countyStorms > mostStorms)
								mostStorms = countyStorms;
							else if (countyStorms < leastStorms)
								leastStorms = countyStorms;
						}
						
						System.out.println("Storms: " + numStorms + " Counties: " + cdb.size());
						System.out.println("# of storms/county: most " + mostStorms + ", least " + leastStorms + ", average " + numStorms / cdb.size());
						System.out.println("% of storms that have a damage amount of 0: " + (cdb.getPercentageOfStormsNoDamage()));
						cdb.printThreeMostExpensiveStorms();
						System.out.println("");
						break;
					} case 'r': {
						if (cdb.removeCounty(remainder))
							System.out.println("county removed");
						else
							System.out.println("county not found");
						System.out.println("");
						break;
					} case 's': {
						String[] split = remainder.split(";", 2);
	
						List<Storm> county1Storms = cdb.getStormsFromCounty(split[0]);
						List<Storm> county2Storms = cdb.getStormsFromCounty(split[1]);
	
						if (county1Storms == null || county2Storms == null) {
							System.out.println("counties are not valid");
						} else {
							Iterator<Storm> stormItr1 = county1Storms.iterator();
							Iterator<Storm> stormItr2 = county2Storms.iterator();
							int county1DollarAmount = 0;
							int county2DollarAmount = 0;
	
							while (stormItr1.hasNext())
								county1DollarAmount += stormItr1.next().getDamageAmount();
							while (stormItr2.hasNext())
								county2DollarAmount += stormItr2.next().getDamageAmount();
							
							if (county1DollarAmount == county2DollarAmount)
								System.out.println("same damage amount");
							else
								System.out.println("different damage amounts");
						}
						System.out.println("");
						break;
					} case 'w': {
						List<Storm> storms = cdb.getStormsWithName(remainder);
	
						if (storms == null) {
							System.out.println("no storms found");
						} else {
							int count = 0;
							int totalAmount = 0;
							Iterator<Storm> stormItr = storms.iterator();
	
							while (stormItr.hasNext())	{
								count += 1;
								totalAmount += stormItr.next().getDamageAmount();
							}
							float average = totalAmount / count;
							System.out.println("average damage amount: " + average);
						}
						System.out.println("");
						break;
					} case 'x': { // Exits program
						stop = true;
						System.out.println("exit");
						break;
					} default:
						break;
				}
			}
		}
	}
}
