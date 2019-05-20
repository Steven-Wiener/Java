/**
 * This program creates a pie chart of a grading chart for a specific class
 * @author Steven Wiener
 * @Version 1
 */

import java.awt.Color;
import javax.swing.*;
import java.awt.Graphics;

public class PieChart extends JFrame	{
	public PieChart()	{
		// Adds ChartPanel to frame
		add(new ChartPanel());
	}
	
	public static void main(String[] args) {
		// Create frame, set title, size, location, default close operation, and visibility
		PieChart f = new PieChart();
		f.setTitle("PieChart");
		f.setSize(300, 200);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(PieChart.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

}

// Creates new panel for pie chart
class ChartPanel extends JPanel	{
	protected void paintComponent(Graphics g)	{
		// Creates Graphics g 
		super.paintComponent(g);
		
		// Sets center of circle location and radius according to form size
		int xCenter = getWidth() / 2;
		int yCenter = getHeight() / 2;
		int r = (int)(Math.min(getWidth(), getHeight()) * 0.4);
		
		// Sets x and y origins
		int x = xCenter - r;
		int y = yCenter - r;
		
		// Sets color to predetermined colors, displays pie chart
		g.setColor(Color.RED);
		g.fillArc(x, y, 2 * r, 2 * r, 0, (int)(360 * 0.2));
		g.setColor(Color.BLUE);
		g.fillArc(x, y, 2 * r, 2 * r, (int)(360 * 0.2), (int)(360 * 0.1));
		g.setColor(Color.GREEN);
		g.fillArc(x, y, 2 * r, 2 * r, (int)(360 * 0.3), (int)(360 * 0.3));
		g.setColor(Color.ORANGE);
		g.fillArc(x, y, 2 * r, 2 * r, (int)(360 * 0.6), (int)(360 * 0.4));
		
		// Sets color to black, displays labels
		g.setColor(Color.BLACK);
		g.drawString("Projects - 20%", 2 * getWidth() / 3, 3 * getHeight() / 8);
		g.drawString("Quizzes - 10%", getWidth() / 3, getHeight() / 10);
		g.drawString("Midterms - 30%", getWidth() / 50, 2 * getHeight() / 5);
		g.drawString("Final - 40%", getWidth() / 2, 4 * getHeight() / 5);
	}
}
