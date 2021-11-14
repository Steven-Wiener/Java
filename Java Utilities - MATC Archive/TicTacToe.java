/**
 * This exercise is a common tic tac toe game. Most of the code is not actually in the
 * 	main method. The main method creates a grid character array and a player character variable. It
 * 	initializes the grid using an inner and outer for loop and assigns a space to each character of
 * 	the array. Then it calls the nextTurn method, and the bulk of the code is executed within this.
 * The nextTurn method displays the grid (which is initialized as empty but changes depending on the
 * 	values held in the grid. It prompts the user for a move, and records the move to the grid array as
 * 	long as it’s a valid move. If it is a valid move, the player changes from ‘X’ to ‘O’ (or from ‘O’
 * 	to ‘X’). If not, the console prints “That spot is already taken” and calls the nextTurn method
 * 	again. After the move, it checks whether or not there is a win by using a checkWin method.
 * The checkWin method first checks the diagonals for the same char value (so long as it’s not ‘ ‘)
 * 	– if they are the same it returns the value of the grid (which would be the player –
 * 	either X or O). It then checks the horizontal and vertical (using an inner and outer for loop) for
 * 	these same conditions. Inside these loops it also checks for a draw by counting the number of
 * 	taken spaces. If none of these conditions are satisfied, it returns a space character, and the
 * 	next turn is called based on this information.
 * @author Steven Wiener
 * @Version 1
 */

import java.util.Scanner;

public class TicTacToe {
	public static void main(String[] args) {
		// Create grid array and player variables
		char[][] grid = new char[3][3];
		char player = 'X';
		
		// Initialize grid
		for (int i = 0; i < 3; i++)	{
			for (int j = 0; j < 3; j++)	{
				grid[i][j] = ' ';
			}
		}
		
		// Display grid and prompt player X for row and column
		nextTurn(grid, player);		
	}
	
	/**
	 * Executes the next turn and then checks for win
	 * @param grid Array storing board data
	 * @param player Char storing current player
	 */
	public static void nextTurn(char grid[][], char player)	{
		// Create Scanner object
		Scanner in = new Scanner(System.in);
		
		// Display grid
		System.out.println("\n-------------"
			   	   + "\n| " + grid[0][0] + " | " + grid[0][1] + " | " + grid[0][2] + " |"
			   	   + "\n-------------"
			   	   + "\n| " + grid[1][0] + " | " + grid[1][1] + " | " + grid[1][2] + " |"
			   	   + "\n-------------"
			   	   + "\n| " + grid[2][0] + " | " + grid[2][1] + " | " + grid[2][2] + " |"
			   	   + "\n-------------");
		
		// Prompt for move
		System.out.print("Enter a row (0, 1, or 2) and column (0, 1, or 2) for player " + player + ": ");
		
		int x = in.nextInt();
		int y = in.nextInt();
		
		// Record move in array
		if (grid[x][y] == ' ')	{
			grid[x][y] = player;
			
			if (player == 'X')	{
				player = 'O';
			}
			else	{
				player = 'X';
			}
		}
		// Check for valid move
		else if (grid[x][y] != ' ')	{
			System.out.print("That spot is already taken");
			nextTurn(grid, player);
		}
		
		// Display winner if game over. If not start next turn
		if (checkWin(grid) == 'D')	{
			System.out.print("Draw. Try again?");
		}
		else if (checkWin(grid) == ' ')	{
			nextTurn(grid, player);
		}
		else	{
			System.out.print(checkWin(grid) + " player won.");
		}
		
		// Close Scanner object
		in.close();
	}
	
	/**
	 * Checks for win or draw
	 * @param grid Array storing board data
	 * @param count Integer storing count for draw scenario
	 */
	public static char checkWin(char grid[][])	{
		int count = 0;
		
		// Check diagonals
		if (((grid[0][0] == grid[1][1]) && (grid[1][1] == grid[2][2]) && (grid[0][0] != ' ')) || 
			((grid[0][2] == grid[1][1]) && (grid[1][1] == grid[2][0]) && (grid[0][2] != ' ')))	
				{
			return grid[0][0];
		}
		
		// Check horizontal and vertical
		for (int i = 0; i < 3; i++)	{
			for (int j = 0; j < 3; j++)	{
				if (grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2] && grid[i][0] != ' ')	{
					return grid[i][0];
				}
				else if (grid[0][j] == grid[1][j] && grid[1][j] == grid[2][j] && grid[0][j] != ' ')	{
					return grid[0][j];
				}
				// Check for draw
				else if (grid[i][j] != ' ')	{
					count += 1;
					if (count == 9)	{
						return 'D';
					}
				}
			}
		}
		
		// Else return a blank
		return ' ';
	}

}
