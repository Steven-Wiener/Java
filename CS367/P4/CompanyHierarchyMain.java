package P4;

import java.util.*;
import java.io.*;

///////////////////////////////////////////////////////////////////////////////
//Title:            CompanyHierarchyMain
//Files:            CompanyHierarchyMain.java
//Semester:         CS367 Spring 2014
//
//Author:           Steven Wiener
//Email:            SWiener@wisc.edu
//CS Login:         wiener
//Pair Partner:     Andrew Minneci
//Email:            minneci@wisc.edu
///////////////////////////////////////////////////////////////////////////////
/**
 * CompanyHierarchyMain class takes in a text document of a list of company
 * employees, starting with the CEO, and makes a Tree representation of the 
 * company. It also allows the user to give it commands to view statistics, and
 * make additions or subtractions from the Tree
 * @author Steven Wiener and Andrew Minneci
 */
public class CompanyHierarchyMain {

	/**
	 * The main method takes in a single .txt file, and makes a new company tree
	 * from it. Main then allows the user to change or view the tree, per
	 * command, until the user decides to quit.
	 * @param args The file name, in a .txt format
	 */
	public static void main(String[] args) {
		// *** Step 1: Check whether exactly one command-line argument is given ***
		Scanner in = new Scanner(System.in);
		if (args.length != 1) {
			System.err.println("Usage: java CompanyHierarchyMain FileName");
			System.exit(1);
		}

		// *** Step 2: Check whether the input file exists and is readable ***
		File inFile = new File(args[0]);
		if (!inFile.exists() || !inFile.canRead())	{
			System.err.println("Error: Cannot access input file");
			System.exit(1);
		}
		try {
			in = new Scanner(inFile);
		} catch (FileNotFoundException e) {
			System.err.println("Error: Cannot access input file");
			System.exit(1);
		}

		/* Step 3: Load the data from the input file and use it to 
		 *  construct an company tree. Note: employees are to be added to the 
		 * 	company tree in the order in which they appear in the text file. 
		 */
		List<String> fileInput = new ArrayList<String>();
		while (in.hasNextLine())
			fileInput.add(in.nextLine());
		in.close();

		CompanyHierarchy ch = new CompanyHierarchy();

		Scanner input = new Scanner(fileInput.get(0));
		input.useDelimiter(",");
		Employee newEmployee = new Employee(input.next().trim(), 
				Integer.parseInt(input.next().trim()), input.next().trim(), 
				input.next().trim());
		try {
			ch.addEmployee(newEmployee, 0, null);
		} catch (CompanyHierarchyException e) {
			System.out.println(e.getMessage());
		}

		for (int i = 1; i < fileInput.size(); i++) {
			input = new Scanner(fileInput.get(i));
			input.useDelimiter(",");
			try {
				Employee newEmploy = new Employee(input.next().trim(), 
						Integer.parseInt(input.next().trim()), 
						input.next().trim(), input.next().trim());
				String supName = input.next().trim();
				int supId = Integer.parseInt(input.next().trim());
				ch.addEmployee(newEmploy, supId, supName);
			} catch (CompanyHierarchyException e) {
				System.out.println(e.getMessage());
			}
		}
		input.close();

		/* Step 4: Prompt the user to enter command options and 
		 *  process them until the user types x for exit. 
		 */
		boolean stop = false;
		while (!stop) {
			Scanner stdin = new Scanner(System.in);
			System.out.println("\nEnter Command: ");
			String userInput = stdin.nextLine();
			String remainder = null;
			
			if (userInput.length() > 0) {
				char option = userInput.charAt(0);
				if (userInput.length() > 1)
					remainder = userInput.substring(1).trim();

				switch (option) {
				case 'a': {
					input = new Scanner(remainder);
					input.useDelimiter(",");
					int newid = Integer.parseInt(input.next().trim());
					String newname = input.next().trim();
					newEmployee = new Employee(newname, newid, input.next().trim(), input.next().trim());
					try {
						if (ch.addEmployee(newEmployee, Integer.parseInt(input.next().trim()), input.next().trim()))
							System.out.println("Employee added");
					} catch (CompanyHierarchyException e) {
						System.out.println(e.getMessage());
					}
					break;
				} case 'c': {
					input = new Scanner(remainder);
					input.useDelimiter(",");
					try {
						List<Employee> coworkers = ch.getCoWorkers(
								Integer.parseInt(input.next().trim()), 
								input.next().trim());
						Iterator<Employee> itr = coworkers.iterator();
						while (itr.hasNext())
							System.out.println(itr.next());
					} catch (CompanyHierarchyException e) {
						System.out.println(e.getMessage());
					}
					break;
				} case 'd': {
					System.out.println("# of employees in company tree: " + ch.getNumEmployees());
					System.out.println("max levels in company tree: " + ch.getMaxLevels());
					System.out.println("CEO: " + ch.getCEO());
					break;
				} case 'e': {
					List<Employee> employees =  ch.getEmployeeWithTitle(remainder);
					if (employees.isEmpty()) {
						System.out.println("Employee not found!");
					} else {
						Iterator<Employee> itr = employees.iterator();
						while (itr.hasNext())
							System.out.println(itr.next().getName());
					}
					break;
				} case 'j': {
					input = new Scanner(remainder);
					input.useDelimiter(",");
					try {
						List<Employee> employees = 
								ch.getEmployeeInJoiningDateRange(
										input.next().trim(), 
										input.next().trim());
						Iterator<Employee> itr = employees.iterator();
						while (itr.hasNext())
							System.out.println(itr.next().getName());
					} catch (CompanyHierarchyException e) {
						System.out.println(e.getMessage());
					}
					break;
				} case 'r': {
					input = new Scanner(remainder);
					input.useDelimiter(",");
					try {
						ch.removeEmployee(Integer.parseInt(input.next().trim()), input.next().trim());
						System.out.println("Employee removed");
					} catch (CompanyHierarchyException e) {
						System.out.println(e.getMessage());
					}
					break;
				} case 's': {
					input = new Scanner(remainder);
					input.useDelimiter(",");
					try {
						List<Employee> supervisors = ch.getSupervisorChain(
								Integer.parseInt(input.next().trim()), 
								input.next().trim());
						Iterator<Employee> itr = supervisors.iterator();
						while (itr.hasNext())
							System.out.println(itr.next().getName());
					} catch (CompanyHierarchyException e) {
						System.out.println(e.getMessage());
					}
					break;
				} case 'u': {
					input = new Scanner(remainder);
					input.useDelimiter(",");
					int id = Integer.parseInt(input.next().trim());
					String name = input.next().trim();
					int newId = Integer.parseInt(input.next().trim());
					String newName = input.next().trim();
					newEmployee = new Employee(newName, newId, input.next().trim(), input.next().trim());

					try {
						ch.replaceEmployee(id, name, newEmployee);
						System.out.println("Employee replaced");
					} catch (CompanyHierarchyException e) {
						System.out.println(e.getMessage());
					}					
					break;
				} case 'x': {
					stop = true;
					System.out.println("exit");
					break;
				} default:
					System.out.println("invalid command");
					break;
				}

			}
		}
	}
}
