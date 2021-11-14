/**
 * This program generates a series of numbers by producing rows and columns using two for
 * 	loops before determining whether or not to display a number versus spaces inside the loops.
 * 	Expected Output:
                               1
                           1   2   1
                       1   2   4   2   1
                   1   2   4   8   4   2   1
               1   2   4   8  16   8   4   2   1
           1   2   4   8  16  32  16   8   4   2   1
       1   2   4   8  16  32  64  32  16   8   4   2   1
   1   2   4   8  16  32  64 128  64  32  16   8   4   2   1
 * @author Steven Wiener
 * @Version 1
 */
public class ForLoopExample {
	public static void main(String[] args) {
		// Loop generates rows 
		for (int y = 1; y <= 8; y++)	{
			// Loop generates columns 1-8
			for (int x = 1; x <= 8; x++)	{
				// If column + row is 9 or more, displays 2 ^ (column + row - 9)
				if (x + y >= 9)	{
					System.out.printf("%4d", (int)Math.pow(2, (x + y - 9)));
				}
				// Else, displays four spaces
				else	{
					System.out.print("    ");
				}
			}
			// Loop generates columns 9-15
			for (int x = 8; x >=1; x--)	{
				// If column + row is 10 or more, displays 2 ^ (column + row - 10)
				if (x + y >= 10)	{
					System.out.printf("%4d", (int)Math.pow(2, (x + y - 10)));
				}
			}
			// Generates new line
			System.out.println();	
		}
	}
}
