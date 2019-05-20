/**
 * User enters two complex numbers (A + Bi), (C + Di) and the console will output these 
 * 	numbers with various calculations performed, including add, sub, mul, div, and abs
 * @author Steven Wiener
 * @Version 1
 */

import java.util.Scanner;

public class ComplexNumberCalculator {
	public static void main(String[] args) {
		// Create Scanner object
		Scanner in = new Scanner(System.in);
		
		// Prompt for first complex number and store as complex1
		System.out.print("Enter the first complex number: ");
		Complex complex1 = new Complex(in.nextDouble(), in.nextDouble());
		
		// Prompt for second complex number and store as complex2
		System.out.print("Enter the second complex number: ");
		Complex complex2 = new Complex(in.nextDouble(), in.nextDouble());
		
		// Print results of add, subtract, multiply, divide, and abs methods to console
		System.out.println("(" + complex1.getRealPart() + " + " + complex1.getImaginaryPart() + "i) + (" + complex2.getRealPart() + " + " + complex2.getImaginaryPart() + "i) = " + complex1.add(complex2));
		System.out.println("(" + complex1.getRealPart() + " + " + complex1.getImaginaryPart() + "i) - (" + complex2.getRealPart() + " + " + complex2.getImaginaryPart() + "i) = " + complex1.subtract(complex2));
		System.out.println("(" + complex1.getRealPart() + " + " + complex1.getImaginaryPart() + "i) * (" + complex2.getRealPart() + " + " + complex2.getImaginaryPart() + "i) = " + complex1.multiply(complex2));
		System.out.println("(" + complex1.getRealPart() + " + " + complex1.getImaginaryPart() + "i) / (" + complex2.getRealPart() + " + " + complex2.getImaginaryPart() + "i) = " + complex1.divide(complex2));
		System.out.println("|(" + complex1.getRealPart() + " + " + complex1.getImaginaryPart() + "i)| = " + complex1.abs());
		
		// Close scanner object
		in.close();
	}

}

class Complex	{
	private double a;
	private double b;
	
	Complex()	{
		this.a = 0;
		this.b = 0;
	}
	
	Complex(double a)	{
		this.a = a;
		this.b = 0;
	}
	
	Complex(double a, double b)	{
		this.a = a;
		this.b = b;
	}
	
	public double getRealPart()	{
		return a;
	}
	
	public double getImaginaryPart()	{
		return b;
	}
	
	// Returns added complex numbers in string form
	public String add(Complex complex)	{
		double c = (getRealPart() + complex.getRealPart());
		double d = (getImaginaryPart() + complex.getImaginaryPart());
		return c + " + " + d + "i";
	}
	
	// Returns subtracted complex numbers in string form
	public String subtract(Complex complex)	{
		double c = (getRealPart() - complex.getRealPart());
		double d = (getImaginaryPart() - complex.getImaginaryPart());
		return c + " + " + d + "i";
	}
	
	// Returns multiplied complex numbers in string form
	public String multiply(Complex complex)	{
		double c = ((getRealPart() * complex.getRealPart()) - (getImaginaryPart() * complex.getImaginaryPart()));
		double d = ((getImaginaryPart() * complex.getRealPart()) + (getRealPart() * complex.getImaginaryPart()));
		return c + " + " + d + "i";
	}
	
	// Returns divided complex numbers in string form
	public String divide(Complex complex)	{
		double c = (((getRealPart() * complex.getRealPart()) + (getImaginaryPart() * complex.getImaginaryPart())) / ((complex.getRealPart() * complex.getRealPart()) + (complex.getImaginaryPart() * complex.getImaginaryPart())));
		double d = (((getImaginaryPart() * complex.getRealPart()) - (getRealPart() * complex.getImaginaryPart())) / ((complex.getRealPart() * complex.getRealPart()) + (complex.getImaginaryPart() * complex.getImaginaryPart())));
		return c + " + " + d + "i";
	}
	
	// Returns absolute value of complex number in string form
	public double abs()	{
		return Math.sqrt((getRealPart() * getRealPart()) + (getImaginaryPart() * getImaginaryPart()));
	}
	
	@Override
	public String toString() {
		if (b == 0)	{
			return "" + a;
		}
		return "("+ a + " + " + b + "i)";
	}
}