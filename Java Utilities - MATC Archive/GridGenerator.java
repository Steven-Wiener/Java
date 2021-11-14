/**
 * This program creates a grid with different colored lines.
 * The main method creates a frame, sets the title, size, location on the screen, default close
 * 	operation, and visibility.
 * @author Steven Wiener
 * @Version 1
 */

import java.awt.Color;
import javax.swing.*;
import java.awt.Graphics;

public class GridGenerator extends JFrame {
	public GridGenerator()	{
		// Adds panel to frame
		add(new GridPanel());
	}
	
	public static void main(String[] args) {
		// Creates a new frame, sets the title, size, location, default close operation, and visibility
		GridGenerator f = new GridGenerator();
		f.setTitle("GridGenerator");
		f.setSize(200, 100);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(GridGenerator.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}

// Creates new panel with grid lines
class GridPanel extends JPanel	{
	protected void paintComponent(Graphics g)	{
		// Creates Graphics g 
		super.paintComponent(g);
		
		// Sets color to red, draws vertical lines
		g.setColor(Color.RED);
		g.drawLine(getWidth() / 3, 0, getWidth() / 3, getHeight());
		g.drawLine(2 * getWidth() / 3, 0, 2 * getWidth() / 3, getHeight());
		
		// Sets color to blue, draws horizontal lines
		g.setColor(Color.BLUE);
		g.drawLine(0, getHeight() / 3, getWidth(), getHeight() / 3);
		g.drawLine(0, 2 * getHeight() / 3, getWidth(), 2 * getHeight() / 3);
	}
}
