
/**
 * This exercise reads from a text file or a URL to create a set of points that are connected by lines.
 * @author Steven Wiener
 * @Version 1
 */

import java.awt.Graphics;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class PolygonGenerator {
	public static void main(String[] args) {
		// Prompt user for choice
		System.out.print("Would you like to read from a file(1), or from a URL(2)? \nEnter Choice: ");
		
		// If user chooses file, execute readFromFile and return error if valid, else readFromURL
		if (new Scanner(System.in).nextInt() == 1)	{
			try {
				readFromFile();
			} 
			catch (Exception e) {
				System.out.println("\nNo file selected.");
			}
		}
		else	{
			readFromURL();
		}
	}
	
	/**
	 * @param file Selected file
	 * @param line String of first line
	 * @param count Number of points, stored in first line
	 * @param list ArrayList generated from text
	 * @param split Line after split with spaces
	 * @param points Remainder of line stored in y value of array
	 * @param array Text file converted to array
	 */
	public static void readFromFile() throws Exception	{
		// Create FileChooser and execute 
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(null);
		
		// Get the selected file
		java.io.File file = fileChooser.getSelectedFile();
		
		// Create a Scanner for the file
		Scanner input = new Scanner(file);
			
		// Read text from the file
		String line = input.nextLine();	// Store first line as String
		int count = Integer.parseInt(line);	// Set value of line as number of points
		ArrayList<int[]> list = new ArrayList<int[]>();	// Generate Arraylist
		// Add each value to ArrayList, calling for the next line after every loop
		for (int i = 0; i < count; i++)	{
			line = input.nextLine();	// Call for next line
			String[] split = line.split(" ");	// Split line into string with spaces between integers
			int[] points = new int[split.length - 1]; // Create array of points with length of integers on line
			for (int j = 1; j < split.length; j++)	{
				points[j - 1] = Integer.parseInt(split[j]);	// Add all integers to array
			}
			list.add(points);	// Add points to list
		}
		
		// Convert ArrayList to array and send to draw method
		int[][] array = list.toArray(new int[list.size()][list.size()]);
		draw(array);
		
		// Close the scanner
		input.close();
	}
	
	public static void readFromURL()	{
		// Prompt for URL and store in string
		System.out.println("\nEnter a URL: ");
		String URLString = new Scanner(System.in).next();
		
		try	{
			// Get data from URL and store in scanner
			URL url = new URL(URLString);
			Scanner input = new Scanner(url.openStream());
			
			// Read text from the file
			String line = input.nextLine();	// Store first line as String
			int count = Integer.parseInt(line);	// Set value of line as number of points
			ArrayList<int[]> list = new ArrayList<int[]>();	// Generate Arraylist
			// Add each value to ArrayList, calling for the next line after every loop
			for (int i = 0; i < count; i++)	{
				line = input.nextLine();	// Call for next line
				String[] split = line.split(" ");	// Split line into string with spaces between integers
				int[] points = new int[split.length - 1]; // Create array of points with length of integers on line
				for (int j = 1; j < split.length; j++)	{
					points[j - 1] = Integer.parseInt(split[j]);	// Add all integers to array
				}
				list.add(points);	// Add points to list
			}
			
			// Convert ArrayList to array and send to draw method
			int[][] array = list.toArray(new int[list.size()][list.size()]);
			draw(array);
			
			// Close scanner
			input.close();
		}
		// Return various errors if necessary
		catch (java.net.MalformedURLException ex)	{
			System.out.println("Invalid URL");
		}
		catch (java.io.IOException ex)	{
			System.out.println("No such file");
		}
	}
	
	public static void draw(int[][] array)	{
		// Create frame
		JFrame f = new JFrame("Exercise14_21");
		f.setSize(300,300);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		
		// Create panel and add to frame
		DrawPanel p = new DrawPanel(array);
		f.add(p);
	}
}

/**
 * @param array[][] Array converted from text passed to method
 * @param point Point to be connected
 */
class DrawPanel extends JComponent	{
	// Create array variable
	private final int[][] array;
	
	// Default Constructor
	public DrawPanel(int[][] array)	{
		this.array = array;
	}
	
	// Draw points and lines
	public void paintComponent(Graphics g)	{
		for (int i = 0; i < array.length; i++)	{
			g.fillOval(array[i][0] - 7, array[i][1] - 7, 15, 15);	// Draw points (stored as first dimension of array)
			g.drawString(Integer.toString(i), array[i][0] - 10, array[i][1] - 10);	// Label points
			for (int j = 2; j < array[i].length; j++)	{
				int point = array[i][j];	// Store requested point as integer
				g.drawLine(array[i][0], array[i][1], array[point][0], array[point][1]); // Connect point to requested point
			}
		}
	}
}