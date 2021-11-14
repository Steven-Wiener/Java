/**
 * This program shows the user a GUI with six buttons, separated into two panels.
 * @author Steven Wiener
 * @Version 1
 */

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

public class GUIExample {
	public static void main(String[] args) {
		// Creates a new frame
		JFrame f = new JFrame("Exercise12_1");
		// Creates a new FlowLayout layout
		LayoutManager layout = new FlowLayout();
		// Sets layout to Flowlayout
		f.setLayout(layout);
		
		// Creates panels 1 and 2
		JPanel p = new JPanel();
		JPanel p2 = new JPanel();
		
		// Creates Buttons 1-3 on panel 1
		p.add(new JButton("Button 1"));
		p.add(new JButton("Button 2"));
		p.add(new JButton("Button 3"));
		// Creates Buttons 4-6 on panel 2
		p2.add(new JButton("Button 4"));
		p2.add(new JButton("Button 5"));
		p2.add(new JButton("Button 6"));
		
		// Adds panels 1 and 2 to frame
		f.add(p);
		f.add(p2);
		
		// Sets frame size
		f.setSize(575, 75);
		// Orients frame to center of screen
		f.setLocationRelativeTo(null);
		// Makes frame visible
		f.setVisible(true);
	}
}
