/**
 * Creates two octagons with radius 5, displays @Override functionality
 * @author Steven Wiener
 * @Version 1
 */
public class OctagonGenerator {
	public static void main(String[] args) {
		// Create new octagon with radius 5 and print
		Octagon octagon1 = new Octagon(5);
		octagon1.printOctagon();
		
		// Create new octagon and compare to first octagon
		Octagon octagon2 = (Octagon)octagon1.clone();
		System.out.println(octagon1.compareTo(octagon2));
	}

}

abstract class GeometricObject {
	private String color = "white";
	private boolean filled;
	private java.util.Date dateCreated;
	
	/** Construct a default geometric object */
	protected GeometricObject() {
		dateCreated = new java.util.Date();
	}

	/** Construct a geometric object with color and filled value */
	protected GeometricObject(String color, boolean filled) {
		dateCreated = new java.util.Date();
		this.color = color;
		this.filled = filled;
	}

	/** Return color */
	public String getColor() {
		return color;
	}
	
	/** Set a new color */
	public void setColor(String color) {
		this.color = color;
	}
	
	/** Return filled. Since filled is boolean,
	 * the get method is named isFilled */
	public boolean isFilled() {
		return filled;
	}
	
	/** Set a new filled */
	public void setFilled(boolean filled) {
		this.filled = filled;
	}
	
	/** Get dateCreated */
	public java.util.Date getDateCreated() {
		return dateCreated;
	}
	
	@Override
	public String toString() {
		return "created on "+ dateCreated + "\ncolor: "+ color +
			" and filled: "+ filled;
	}
	
	/** Abstract method getArea */
	public abstract double getArea();
	
	/** Abstract method getPerimeter */
	public abstract double getPerimeter();
}

class Octagon extends GeometricObject
		implements Cloneable, Comparable<Octagon>	{
	private double side;
	
	public Octagon() {
	}
	
	public Octagon(double side) {
		this.side = side;
	}

	public Octagon(double side,	String color, boolean filled) {
		this.side = side;
		setColor(color);
		setFilled(filled);
	}
	
	/** Return side length */
	public double getSide() {
		return side;
	}

	/** Set a new side length */
	public void setSide(double side) {
		this.side = side;
	}

	/** Return area */
	public double getArea() {
		return (2 + 4 / Math.sqrt(2)) * side * side;
	}

	/** Return perimeter */
	public double getPerimeter() {
		return 8 * side;
	}

	/** Print the octagon info */
	public void printOctagon() {
		System.out.println("The octagon is created "+ getDateCreated() +
			", the area is " + getArea() + ", and the perimeter is "+ getPerimeter());
	}
	
	// Overrides clone method
	@Override
	public Object clone()	{
		try	{
			Octagon octagonClone = (Octagon)super.clone();
			return octagonClone;
		}
		catch (CloneNotSupportedException ex)	{
			return null;
		}
	}
	
	// Overrides compareTo, returning 1 if area is larger, -1 if it is smaller, and 0 if equal
	@Override
	public int compareTo(Octagon obj)	{
		if (getArea() > obj.getArea())
			return 1;
		else if (getArea() < obj.getArea())
			return -1;
		else
			return 0;
	}
}