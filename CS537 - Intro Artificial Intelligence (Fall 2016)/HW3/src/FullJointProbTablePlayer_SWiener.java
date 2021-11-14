import java.util.List;

///////////////////////////////////////////////////////////////////////////////
//Title:            PlayNannon
//Files:            FullJointProbTablePlayer_SWiener.java
//Semester:         CS537 Fall 2016
//
//Author:           Steven Wiener
//Email:            SWiener@wisc.edu
///////////////////////////////////////////////////////////////////////////////

public class FullJointProbTablePlayer_SWiener extends NannonPlayer {
	int[][][][][][][][][] fullState_win = new int[3][3][3][3][3][3][4][4][7];
    int[][][][][][][][][] fullState_lose = new int[3][3][3][3][3][3][4][4][7];
    
    int bestCell1, bestCell2, bestCell3, bestCell4, bestCell5, bestCell6 = 0;
    int bestHomePieces, bestSafePieces, bestDie = 0;
    
    double bestRatio = 0;
    
    // m estimates
	int wins = 1;
	int losses = 1;
    
	@Override
	public String getPlayerName() { return "FullJointProbTablePlayer_SWiener"; }
	
	// Constructors.
	public FullJointProbTablePlayer_SWiener() {
		initialize();
	}
	public FullJointProbTablePlayer_SWiener(NannonGameBoard gameBoard) {
		super(gameBoard);
		initialize();
	}
	
	private void initialize() {
		for (int i1 = 0; i1 < 3; i1++)
			for (int i2 = 0; i2 < 3; i2++)
				for (int i3 = 0; i3 < 3; i3++)
					for (int i4 = 0; i4 < 3; i4++)
						for (int i5 = 0; i5 < 3; i5++)
							for (int i6 = 0; i6 < 3; i6++)
								for (int i7 = 0; i7 < 4; i7++)
									for (int i8 = 0; i8 < 4; i8++)
										for (int i9 = 0; i9 < 7; i9++) {
											fullState_win[i1][i2][i3][i4][i5][i6][i7][i8][i9] = 1;
											fullState_lose[i1][i2][i3][i4][i5][i6][i7][i8][i9] = 1;
										}
	}

	//@SuppressWarnings("unused") // This prevents a warning from the "if (false)" below.
	@Override
	public List<Integer> chooseMove(int[] boardConfiguration, List<List<Integer>> legalMoves) {
		
		// Below is some code you might want to use in your solution.
		//      (a) converts to zero-based counting for the cell locations
		//      (b) converts NannonGameBoard.movingFromHOME and NannonGameBoard.movingToSAFE to NannonGameBoard.cellsOnBoard,
		//          (so you could then make arrays with dimension NannonGameBoard.cellsOnBoard+1)
		//      (c) gets the current and next board configurations.
		
		double pBest = 0;
		List<Integer> bestMove = null;
		
		if (legalMoves != null) for (List<Integer> move : legalMoves) {
			
			int fromCountingFromOne    = move.get(0);  // Convert below to an internal count-from-zero system.
			int   toCountingFromOne    = move.get(1);			
			int                 effect = move.get(2);  // See ManageMoveEffects.java for the possible values that can appear here.	
			
			// Note we use 0 for both 'from' and 'to' because one can never move FROM SAFETY or TO HOME, so we save a memory cell.
			int from = (fromCountingFromOne == NannonGameBoard.movingFromHOME ? 0 : fromCountingFromOne);
			int to   = (toCountingFromOne   == NannonGameBoard.movingToSAFETY ? 0 : toCountingFromOne);
			
			// The 'effect' of move is encoded in these four booleans:
		    boolean        hitOpponent = ManageMoveEffects.isaHit(      effect);  // Did this move 'land' on an opponent (sending it back to HOME)?
		    boolean       brokeMyPrime = ManageMoveEffects.breaksPrime( effect);  // A 'prime' is when two pieces from the same player are adjacent on the board;
		                                                                          // an opponent can NOT land on pieces that are 'prime' - so breaking up a prime of 
		                                                                          // might be a bad idea.
		    boolean extendsPrimeOfMine = ManageMoveEffects.extendsPrime(effect);  // Did this move lengthen (i.e., extend) an existing prime?
		    boolean createsPrimeOfMine = ManageMoveEffects.createsPrime(effect);  // Did this move CREATE a NEW prime? (A move cannot both extend and create a prime.)
		    
		    // Note that you can compute other effects than the four above (but you need to do it from the info in boardConfiguration, resultingBoard, and move).
		    
			// See comments in updateStatistics() regarding how to use these.
			int[] resultingBoard = gameBoard.getNextBoardConfiguration(boardConfiguration, move);  // You might choose NOT to use this - see updateStatistics().
			
			/* Here is what is in a board configuration vector.  There are also accessor functions in NannonGameBoard.java (starts at or around line 60).
			 
			   	boardConfiguration[0] = whoseTurn;        // Ignore, since it is OUR TURN when we play, by definition. (But needed to compute getNextBoardConfiguration.)
        		boardConfiguration[1] = homePieces_playerX; 
        		boardConfiguration[2] = homePieces_playerO;
        		boardConfiguration[3] = safePieces_playerX;
        		boardConfiguration[4] = safePieces_playerO;
        		boardConfiguration[5] = die_playerX;      // I added these early on, but never used them.
        		boardConfiguration[6] = die_playerO;      // Probably can be ignored since get the number of legal moves, which is more meaningful.
       
        		cells 7 to (6 + NannonGameBoard.cellsOnBoard) record what is on the board at each 'cell' (ie, board location).
        					- one of NannonGameBoard.playerX, NannonGameBoard.playerO, or NannonGameBoard.empty.
        		
			 */
			
			int cell1 = resultingBoard[7];
		    int cell2 = resultingBoard[8];
		    int cell3 = resultingBoard[9];
		    int cell4 = resultingBoard[10];
		    int cell5 = resultingBoard[11];
		    int cell6 = resultingBoard[12];
	    	int homePieces = resultingBoard[1];
	    	int safePieces = resultingBoard[3];
	    	int die = resultingBoard[5];
		    
	    	// Compute the probability from our joint probability table by incrementing win/lose times given the board state, and averaging
			double numWin = (double) fullState_win[cell1][cell2][cell3][cell4][cell5][cell6][homePieces][safePieces][die];
			double numLoss = (double) fullState_lose[cell1][cell2][cell3][cell4][cell5][cell6][homePieces][safePieces][die];
			double probWin = numWin / (numWin + numLoss);
			
			// Find likelihood this move ends in win, choose move giving best likelihood
			if (probWin >= pBest) {
				pBest = probWin;
				bestMove = move;
			}
			
			if (pBest >= bestRatio) {
				bestCell1 = cell1;
				bestCell2 = cell2;
				bestCell3 = cell3;
				bestCell4 = cell4;
				bestCell5 = cell5;
				bestCell6 = cell6;
				bestRatio = pBest;
				bestHomePieces = homePieces;
				bestSafePieces = safePieces;
				bestDie = die;
			}
		}
		
		return (bestMove == null) ? Utils.chooseRandomElementFromThisList(legalMoves) : bestMove;
	}

	//@SuppressWarnings("unused") // This prevents a warning from the "if (false)" below.
	@Override
	public void updateStatistics(boolean             didIwinThisGame, 
		                         List<int[]>         allBoardConfigurationsThisGameForPlayer,
			                     List<Integer>       allCountsOfPossibleMovesForPlayer,
			                     List<List<Integer>> allMovesThisGameForPlayer) {
		
		// Do nothing with these in the random player (but hints are here for use in your players).	
		
		// However, here are the beginnings of what you might want to do in your solution (see comments in 'chooseMove' as well).
		//if (false) { // <------------ Be sure to remove this 'false' *********************************************************************
		int numberOfMyMovesThisGame = allBoardConfigurationsThisGameForPlayer.size();	
		
		for (int myMove = 0; myMove < numberOfMyMovesThisGame; myMove++) {
			int[]         currentBoard        = allBoardConfigurationsThisGameForPlayer.get(myMove);
			int           numberPossibleMoves = allCountsOfPossibleMovesForPlayer.get(myMove);
			List<Integer> moveChosen          = allMovesThisGameForPlayer.get(myMove);
			int[]         resultingBoard      = (numberPossibleMoves < 1 ? currentBoard // No move possible, so board is unchanged.
					                                                     : gameBoard.getNextBoardConfiguration(currentBoard, moveChosen));
			
			// You should compute the statistics needed for a Bayes Net for any of these problem formulations:
			//
			//     prob(win | currentBoard and chosenMove and chosenMove's Effects)  <--- this is what I (Jude) did, but mainly because at that point I had not yet written getNextBoardConfiguration()
			//     prob(win | resultingBoard and chosenMove's Effects)               <--- condition on the board produced and also on the important changes from the prev board
			//
			//     prob(win | currentBoard and chosenMove)                           <--- if we ignore 'chosenMove's Effects' we would be more in the spirit of a State Board Evaluator (SBE)
			//     prob(win | resultingBoard)                                        <--- but it seems helpful to know something about the impact of the chosen move (ie, in the first two options)
			//
			//     prob(win | currentBoard)                                          <--- if you estimate this, be sure when CHOOSING moves you apply to the NEXT boards (since when choosing moves, one needs to score each legal move).
			
			if (numberPossibleMoves < 1) { continue; } // If NO moves possible, nothing to learn from (it is up to you if you want to learn for cases where there is a FORCED move, ie only one possible move).

			// Convert to our internal count-from-zero system.
			// A move is a list of three integers.  Their meanings should be clear from the variable names below.
			int fromCountingFromOne = moveChosen.get(0);  // Convert below to an internal count-from-zero system.
			int   toCountingFromOne = moveChosen.get(1);
			int              effect = moveChosen.get(2);  // See ManageMoveEffects.java for the possible values that can appear here. Also see the four booleans below.

			// Note we use 0 for both 'from' and 'to' because one can never move FROM SAFETY or TO HOME, so we save a memory cell.
			int from = (fromCountingFromOne == NannonGameBoard.movingFromHOME ? 0 : fromCountingFromOne);
			int to   = (toCountingFromOne   == NannonGameBoard.movingToSAFETY ? 0 : toCountingFromOne);
			
			// The 'effect' of move is encoded in these four booleans:
		    boolean        hitOpponent = ManageMoveEffects.isaHit(      effect); // Explained in chooseMove() above.
		    boolean       brokeMyPrime = ManageMoveEffects.breaksPrime( effect);
		    boolean extendsPrimeOfMine = ManageMoveEffects.extendsPrime(effect);
		    boolean createsPrimeOfMine = ManageMoveEffects.createsPrime(effect);
		    
		    int cell1 = resultingBoard[7];
		    int cell2 = resultingBoard[8];
		    int cell3 = resultingBoard[9];
		    int cell4 = resultingBoard[10];
		    int cell5 = resultingBoard[11];
		    int cell6 = resultingBoard[12];
		    int homePieces = resultingBoard[1];
		    int safePieces = resultingBoard[3];
		    int die = resultingBoard[5];
		    
		    // Increment fullState arrays
		    if (didIwinThisGame) {
		    	fullState_win[cell1][cell2][cell3][cell4][cell5][cell6][homePieces][safePieces][die]++;
		    	wins++;
		    } else {
		    	fullState_lose[cell1][cell2][cell3][cell4][cell5][cell6][homePieces][safePieces][die]++;
		    	losses++;
		    }
		}
		//}
	}
	
	@Override
	public void reportLearnedModel() {
		Utils.println("\n-------------------------------------------------");
		Utils.println("Learned model for " + getPlayerName());
		Utils.println("Best cell 1: " + Integer.toString(bestCell1));
		Utils.println("Best cell 2: " + Integer.toString(bestCell2));
		Utils.println("Best cell 3: " + Integer.toString(bestCell3));
		Utils.println("Best cell 4: " + Integer.toString(bestCell4));
		Utils.println("Best cell 5: " + Integer.toString(bestCell5));
		Utils.println("Best cell 6: " + Integer.toString(bestCell6));
		Utils.println("Best home pieces: " + Integer.toString(bestHomePieces));
		Utils.println("Best safe pieces: " + Integer.toString(bestSafePieces));
		Utils.println("Best die: " + Integer.toString(bestDie));
		Utils.println("Best win/loss ratio: " + bestRatio);
		Utils.println("\n-------------------------------------------------");
	}
}
