/**
 * Creates two circles with radius 5, displays @Override functionality
 * @author Steven Wiener
 * @Version 1
 */
public class CircleGenerator {
	public static void main(String[] args) {
		// Create new circle with radius 5 and print
		Circle circle1 = new Circle(5);
		circle1.printCircle();
		
		// Create new circle and compare to first circle
		Circle circle2 = new Circle(5);
		System.out.println(circle1.compareTo(circle2));
		System.out.println(circle1.equals(circle2));
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

class Circle extends GeometricObject
		implements Comparable<Circle>	{
	private double radius;
	
	public Circle() {
	}
	
	public Circle(double radius) {
		this.radius = radius;
	}

	public Circle(double radius, String color, boolean filled) {
		this.radius = radius;
		setColor(color);
		setFilled(filled);
	}
	
	/** Return radius */
	public double getRadius() {
		return radius;
	}

	/** Set a new radius */
	public void setRadius(double radius) {
		this.radius = radius;
	}

	/** Return area */
	public double getArea() {
		return radius * radius * Math.PI;
	}

	/** Return diameter */
	public double getDiameter() {
		return 2 * radius;
	}

	/** Return perimeter */
	public double getPerimeter() {
		return 2 * radius * Math.PI;
	}

	/** Print the circle info */
	public void printCircle() {
		System.out.println("The circle is created "+ getDateCreated() +
			" and the radius is "+ radius);
	}
	
	// Overrides compareTo, returning 1 if area is larger, -1 if it is smaller, and 0 if equal
	@Override
	public int compareTo(Circle obj)	{
		if (getArea() > obj.getArea())
			return 1;
		else if (getArea() < obj.getArea())
			return -1;
		else
			return 0;
	}
	
	// Overrides equals, returning true if radii are equal
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Circle)	{
			return radius == ((Circle)obj).radius;
		}
		else
			return false;
	}
}