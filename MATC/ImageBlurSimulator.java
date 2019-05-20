/**
 * My assigned problem for test 1 was to create a blur method that one could use on part of a picture
 * 	file to blur out a portion of the picture, such as a person’s face or a license plate number.
 * In my main method I created the sample picture array that was provided in the printout for my
 * 	problem, I display this picture to make certain it is accurate, then I send this picture to the
 * 	blur method
 * @author Steven Wiener
 * @Version 1
 */

public class ImageBlurSimulator {
	public static void main(String[] args) {
		// Create and initialize test picture
		double[][] picture = {
			{1.2, 1.3, 4.5, 6.0, 2.7},
			{1.7, 3.3, 4.4, 10.5, 17.0},
			{1.1, 4.5, 2.1, 25.3, 9.2},
			{1.0, 9.5, 8.3, 2.9, 2.1}
		};
		
		// Display picture
		for (int i = 0; i < picture[0].length - 1; i++)	{
			for (int j = 0; j < picture[1].length; j++)	{
				System.out.printf("%4.1f | ", picture[i][j]);
			}
			System.out.println();
		}
		
		// Send to blur method
		double[][] result = blur(picture);
		
		// Display test result
		for (int i = 0; i < result.length; i++)	{
			System.out.print("\n---------------------------------------\n");
			for (int j = 0; j < result[0].length; j++)	{
				System.out.printf("%5.2f | ", result[i][j]);
			}
		}
		
	}
	
	/**
	 * Returns a blurred result from an input picture
	 * @param picture Original picture array
	 * @param result Generated result returned to main method
	 * @param denominator Denominator for calculation used in determining result array
	 * @param pictureCopy Exact copy of picture array with 0s lining the outside
	 */
	public static double[][] blur(double[][]picture) {
		// Create denominator and result array
		int denominator = 16;
		double[][] result = new double[picture.length][picture[0].length];

		// Create pictureCopy array with 0s on the borders
		double[][] pictureCopy = new double[picture.length + 2][picture[0].length + 2];
		for (int i = 0; i < pictureCopy.length; i++)	{
			for (int j = 0; j < pictureCopy[i].length; j++)	{
				pictureCopy[i][j] = 0;
			}
		}
		for (int i = 1; i < pictureCopy.length - 1; i++)	{
			for (int j = 1; j < pictureCopy[i].length - 1; j++)	{
				pictureCopy[i][j] = picture[i - 1][j - 1];
			}
		}
		
		// Create result
		for (int i = 1; i < result.length + 1; i++)	{
			for (int j = 1; j < result[0].length + 1; j++)	{
				// Create denominator for corners
				if ((i == 1 || i == result.length) && (j == 1 || j == result.length + 1))	{
					denominator = 9;
				}
				// Create denominator for sides
				else if (i == 1 || i == result.length || j == 1 || j == result.length + 1)	{
					denominator = 12;
				}
				
				// Default denominator is 16
				result[i - 1][j - 1] = ((pictureCopy[i][j] * 4) + (pictureCopy[i][j + 1] * 2) + pictureCopy[i + 1][j + 1] + (pictureCopy[i + 1][j] * 2) + pictureCopy[i + 1][j - 1] + (pictureCopy[i][j - 1] * 2) + pictureCopy[i - 1][j - 1] + (pictureCopy[i - 1][j] * 2) + pictureCopy[i - 1][j + 1]) / denominator;
				denominator = 16;
			}
		}
		
		return result;		
	}
}
