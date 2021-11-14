package P2;
///////////////////////////////////////////////////////////////////////////////
//Title:            DisplayEditor
//Files:            DisplayEditor.java
//Semester:         CS367 Spring 2014
//
//Author:           Steven Wiener
//Email:            SWiener@wisc.edu
//Pair Partner:     Andrew Minneci
//Email:            minneci@wisc.edu
///////////////////////////////////////////////////////////////////////////////

import java.util.*;
import java.io.*;

/**
 * DisplayEditor class creates an empty loop of characters and prompts the user to enter commands to process until the user types q for quit
 * @author Steven Wiener, Andrew Minneci
 */
public class DisplayEditor {
// Takes up to two command arguments, and then utilizes user input to display messages
	public static void main(String[] args) throws EmptyLoopException {
		/** Check whether the input file exists and is readable ***/
		Scanner in = new Scanner(System.in);
		DotMatrix dm = new DotMatrix();

		if (args.length > 2) {
			System.err.println("invalid command-line arguments");
			System.exit(1);
		} else if (args.length == 2) {
			File inFile = new File(args[0]);
			if (!inFile.exists() || !inFile.canRead()) {
				System.err.println("Problem with input file!");
				System.exit(1);
			}
			try {
				in.close();
				in = new Scanner(inFile);
			} catch (FileNotFoundException e) {
				System.err.println("Problem with input file!");
				System.exit(1);
			}
			dm.loadAlphabets(args[1]);
		} else if (args.length == 1) {
			File inFile = new File(args[0]);
			if (!inFile.exists() || !inFile.canRead()) {
				System.err.println("Problem with input file!");
				System.exit(1);
			}
			dm.loadAlphabets(args[0]);
		} else {
			System.out.println("Enter the dot-matrix alphabets file:");
			dm.loadAlphabets(in.nextLine());
		}

		MessageLoop MainLoop = new MessageLoop();

		boolean stop = false;
		while (!stop) {
			System.out.println("enter command (? for help)>");
			String input = null;
			if (in.hasNextLine())
				input = in.nextLine();
			else
				System.exit(1);
			String remainder = null;
			if (input.length() > 0) {
				char option = input.charAt(0);
				if (input.length() > 1)
					remainder = input.substring(1);
				switch (option) {
					case '?': {
						if (remainder != null) {
							System.out.println("invalid command");
							break;
						}
						System.out.println("s (save)    l (load)       d (display)" + "\n" 
								+ "n (next)    p (previous)   j (jump)" + "\n"
								+ "x (delete)  a (add after)  i (insert before)" + "\n"
								+ "c (context) r (replace)    q (quit)");
						break;
					} case 's': {
						if (MainLoop.size() == 0) {
							System.out.println("invalid command");
						} else {
							try	{
								if (remainder == null ) {
									System.out.println("invalid command");
									break;
								}					
								remainder = remainder.trim();
	
								File output = new File(remainder);
								if (output.exists()) {
									System.out.println("warning: file already exists, will be overwritten");
									break;
								}
								PrintWriter pw = new PrintWriter(output);
								
								Iterator<DblListnode<String>> itr = MainLoop.iterator();
								while(itr.hasNext()) {
									DblListnode<String> curr = itr.next();
									List<String> list = curr.getData();
									for (int i = 0; i <list.size(); i++)
										pw.println(list.get(i));
									pw.println("##########");
								}
								pw.close();
								break;
							} catch (FileNotFoundException e) {
								System.out.println("unable to save");
							}
						}
						break;
					} case 'l': {
						try {
							if (remainder == null ) {
								System.out.println("invalid command");
								break;
							}
							remainder = remainder.substring(1, remainder.length());
							
							File file = new File(remainder);
							Scanner scnr = new Scanner (file);
							while (scnr.hasNextLine())	{
								DblListnode<String> curr = new DblListnode<String>();
								ArrayList<String> data = new ArrayList<String>();
								for (int i = 0; i < 7; i++)
									data.add(scnr.nextLine());
								curr.setData(data);
								MainLoop.addAfter(curr);
								scnr.nextLine();
							}
							MainLoop.forward();
							scnr.close();
						} catch (FileNotFoundException e) {
							System.out.println(e.getMessage());
						}
						break;
					} case 'd': {
						if (MainLoop.size() == 0) {
							System.out.println("no messages");
							break;
						} else if (remainder != null) {
							System.out.println("invalid command");
							break;
						}
						
						Iterator<DblListnode<String>> itr = MainLoop.iterator();
						System.out.println("");
						while (itr.hasNext()) {
							DblListnode<String> node = itr.next();
							for (int i = 0; i < 7; i++)
								System.out.println(node.getData().get(i));
							System.out.println("");
						}
						break;
					} case 'n': {
						if (remainder != null) {
							System.out.println("invalid command");
							break;
						} else if (MainLoop.size()==0) {
							System.out.println("no messages");
							break;
						}
						MainLoop.forward();
						DblListnode<String> curr = MainLoop.getCurrent();
						printOutput(MainLoop, curr);
						break;
					} case 'p': {
						if (MainLoop.size()==0) {
							System.out.println("no messages");
							break;
						} else if (remainder != null) {
							System.out.println("invalid command");
							break;
						}
	
						printOutput(MainLoop, MainLoop.getCurrent());
						break;
					} case 'j': {
						if (remainder == null) {
							System.out.println("invalid command");
							break;
						} else if (MainLoop.size() == 0) {
							System.out.println("no messages");
							break;
						}
						remainder = remainder.trim();
	
						int N = Integer.parseInt(remainder);
						if (N > 0)
							for (int i = 0; i < N; i++)
								MainLoop.forward();
						else if (N < 0)
							for (int i = 0; i > N; i--)
								MainLoop.back();
	
						printOutput(MainLoop, MainLoop.getCurrent());
						break;
					} case 'x': {
						if (MainLoop.size() == 0) {
							System.out.println("no messages");
							break;
						} else if (remainder != null) {
							System.out.println("invalid command");
							break;
						} else if (MainLoop.size() == 1) {
							MainLoop.removeCurrent();
							System.out.println("no messages");
							break;
						}
						printOutput(MainLoop, MainLoop.removeCurrent());
						break;
					} case 'a': {
						try {
							if (remainder.length() < 2) {
								System.out.println("invalid command");
								break;
							}
							remainder = remainder.substring(1, remainder.length());
							String[] letters = remainder.split("(?!^)");
	
							for (int i = 0; i < remainder.length(); i++) {
								if (!dm.isValidCharacter(letters[i]))
									throw new UnrecognizedCharacterException();
								DblListnode<String> newbie = new DblListnode<String>(null, dm.getDotMatrix(letters[i]), null);
								MainLoop.addAfter(newbie);
							}
							printOutput(MainLoop, MainLoop.getCurrent());
						} catch (UnrecognizedCharacterException e) {
							System.out.println("An unrecognized character has been entered");
						}
						break;
					} case 'i': {
						try {
							if (remainder == null) {
								System.out.println("invalid command");
								break;
							}
							remainder = remainder.substring(1, remainder.length());
							String[] letters = remainder.split("(?!^)");
	
							for (int i = 0; i < remainder.length(); i++) {
								if (!dm.isValidCharacter(letters[i]))
									throw new UnrecognizedCharacterException();
								DblListnode<String> newbie = new DblListnode<String>(null, dm.getDotMatrix(letters[i]), null);
								MainLoop.addBefore(newbie);
							}
							printOutput(MainLoop, MainLoop.getCurrent());
						} catch (UnrecognizedCharacterException e) {
							System.out.println("An unrecognized character has been entered");
						}
						break;
					} case 'c': {
						if (remainder != null) {
							System.out.println("invalid command");
							break;
						} else if(MainLoop.size() == 0) {
							System.out.println("no messages");
							break;
						}
						printOutput(MainLoop, MainLoop.getCurrent());
						break;
					} case 'r': {
						try {
							if (remainder == null || remainder.length() > 2) {
								System.out.println("invalid command");
								break;
							} else if (MainLoop.size() == 0) {
								System.out.println("no messages");
								break;
							}
	
							remainder = remainder.substring(1, remainder.length());
							
							if (!dm.isValidCharacter(remainder)) 
								throw new UnrecognizedCharacterException();
							DblListnode<String> newbie = new DblListnode<String>(null, dm.getDotMatrix(remainder), null);
							MainLoop.removeCurrent();
							MainLoop.addBefore(newbie);
							
							printOutput(MainLoop, MainLoop.getCurrent());
	
						} catch(UnrecognizedCharacterException e) {
							System.out.println();
						}
						break;
					} case 'q': { // Exits Program
						if (remainder != null){
							System.out.println("invalid command");
							break;
						}
						in.close();
						System.out.println("quit");
						System.exit(-1);
						break;
					} default: {
						System.out.println("invalid command");
						break;
					}
				}
			}
		}
	}
	
	// print of the current display
	private static void printOutput(MessageLoop MainLoop, DblListnode<String> curr)	{
		if (MainLoop.size() == 1) {
			System.out.println("\n**********");
			for (int i =0; i < curr.getData().size(); i++)
				System.out.println(curr.getData().get(i));
			System.out.println("**********");
		} else if (MainLoop.size() == 2) {
			System.out.println("**********");
			for (int i = 0; i < 7; i++)
				System.out.println(curr.getData().get(i));
			System.out.println("**********");
			for (int i = 0; i < 7; i++)
				System.out.println(curr.getPrev().getData().get(i));
		} else {
			for (int i = 0; i < 7; i++)
				System.out.println(curr.getPrev().getData().get(i));
			System.out.println("**********");
			for (int i = 0; i < 7; i++)
				System.out.println(curr.getData().get(i));
			System.out.println("**********");
			for (int i = 0; i < 7; i++)
				System.out.println(curr.getNext().getData().get(i));
		}
	}
}

