/**
 * This program displays the first 100 pentagonal numbers. When the user clicks run, a for
 * 	loop creates 10 rows, and a nested for loop creates 10 columns. The
 * 	inner for loop calls a method named “getPentagonalNumber”, sending it the value of the row*10 +
 * 	column number. getPentagonalNumber uses this value to return the requested pentagonal number based
 * 	on the number value. The inner for loop displays the requested pentagonal number on a 10x1 line, and
 * 	the outer for loop creates a new line once the inner for loop has been executed. It then starts the
 * 	next cycle until the outer for loop has been completed.
 * Expected Output:
1     5     12    22    35    51    70    92    117   145   
176   210   247   287   330   376   425   477   532   590   
651   715   782   852   925   1001  1080  1162  1247  1335  
1426  1520  1617  1717  1820  1926  2035  2147  2262  2380  
2501  2625  2752  2882  3015  3151  3290  3432  3577  3725  
3876  4030  4187  4347  4510  4676  4845  5017  5192  5370  
5551  5735  5922  6112  6305  6501  6700  6902  7107  7315  
7526  7740  7957  8177  8400  8626  8855  9087  9322  9560  
9801  10045 10292 10542 10795 11051 11310 11572 11837 12105 
12376 12650 12927 13207 13490 13776 14065 14357 14652 14950
 * @author Steven Wiener
 * @Version 1
 */
public class PentagonalNumberGenerator {
	public static void main(String[] args) {
		// For loop creates rows
		for (int y = 0; y <= 9; y++)	{
			// For loop creates columns
			for (int x = 1; x <= 10; x++)	{
				// Calls getPentagonalNumber using n = (row * 10) + column, formats string integer into 6 wide
				System.out.printf("%-6d", (int)getPentagonalNumber((y * 10) + x));
			}
			// Initiates next line
			System.out.println();
		}
	}
	
	public static int getPentagonalNumber(int n)	{
		// Returns value for formula outlined in text
		return n * ((3 * n) - 1) / 2;
	}
}
