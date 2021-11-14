import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;

///////////////////////////////////////////////////////////////////////////////
//Title:            LoadBalancerMain
//Files:            LoadBalancerMain.java
//Semester:         CS367 Spring 2014
//
//Author:           Steven Wiener
//Email:            SWiener@wisc.edu
//Pair Partner:     Andrew Minneci
//Email:            minneci@wisc.edu
///////////////////////////////////////////////////////////////////////////////

public class LoadBalancerMain {
	public static <K, V> void main(String[] args) throws IOException {
		Scanner scanner = null;
		if (args.length != 3 && args.length != 4) {
			System.err.println("Wrong number of command line arguments");
			System.exit(1);
		}

		int maxServers = Integer.parseInt(args[0]);
		int cacheSize = Integer.parseInt(args[1]);

		File inFile = new File(args[2]);
		if (!inFile.exists() || !inFile.canRead()) {
			System.err.println("Error: Cannot access input file");
			System.exit(1);
		}
		try {
			scanner = new Scanner(inFile);
		} catch (FileNotFoundException e) {
			System.err.println("Error: Cannot access input file");
			System.exit(1);
		}

		boolean isVerbose = (args.length == 4) && (args[3].equals("-v"));

		String line;
		SimpleHashMap<String, Integer> map = new SimpleHashMap<String, Integer>();

		int nextIP = 0; 
		int evictions = 0;
		int IPPings[] = new int[maxServers];
		int IPKeys[] = new int[maxServers];

		while (scanner.hasNextLine()) {
			line = scanner.nextLine();

			if (map.get(line) != null) {
				IPPings[map.get(line)]++;
				if (isVerbose)
					System.out.println("192.168.0." + map.get(line));
			}

			else {
				if (IPKeys[nextIP] >= cacheSize) {
					if (isVerbose)
						System.out.println("Page " + map.entries().get(evictions).getKey() + " has been evicted.");
					map.put(line, nextIP);
					IPPings[nextIP]++;
					evictions++;
				} else {
					map.put(line, nextIP);
					IPPings[nextIP]++;
					IPKeys[nextIP]++;
				}
				if (isVerbose)
					System.out.println("192.168.0." + nextIP);
				nextIP++;
				if (nextIP >= maxServers)
					nextIP = 0;
			}


		}
		scanner.close();

		for (int i = 0; i < maxServers; i++) 
			System.out.println("192.168.0." + i + " : " + IPPings[i]);
		System.out.println("Evictions : " + evictions);
	}
}