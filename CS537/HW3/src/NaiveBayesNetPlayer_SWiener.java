import java.util.List;

///////////////////////////////////////////////////////////////////////////////
//Title:            PlayNannon
//Files:            NaiveBayesNetPlayer_SWiener.java
//Semester:         CS537 Fall 2016
//
//Author:           Steven Wiener
//Email:            SWiener@wisc.edu
///////////////////////////////////////////////////////////////////////////////

public class NaiveBayesNetPlayer_SWiener extends NannonPlayer {
	// Defaults
	private static int boardSize = 6;
	private static int pieces = 3;

	int homeWin[] = new int[pieces + 1]; // P(homeX | win)
	int homeLose[] = new int[pieces + 1]; // P(homeX | !win)
	int safeWin[] = new int[pieces + 1]; // P(safeX | win)
	int safeLose[] = new int[pieces + 1]; // P(safeX | !win)
	int winBoard[][] = new int[boardSize][3]; 
	int loseBoard[][] = new int[boardSize][3];
	
	// m estimates
	int wins = 1;
	int losses = 1;
	
	int hitOpponentWin = 1;
	int hitOpponentLose = 1;
    int brokeMyPrimeWin = 1;
    int brokeMyPrimeLose = 1;
    int extendsPrimeOfMineWin = 1;
    int extendsPrimeOfMineLose = 1;
    int createsPrimeOfMineWin = 1;
    int createsPrimeOfMineLose = 1;
    
    int bestCreatesPrimeOfMine = 0;
    double bestRatio = 0;
	
	@Override
	public String getPlayerName() { return "NaiveBayesNetPlayer_SWiener"; }
	
	// Constructors.
	public NaiveBayesNetPlayer_SWiener() {
		if (boardSize == 0) boardSize = 6;
		if (pieces == 0) pieces = 3;
		initialize(boardSize, pieces);
	}
	public NaiveBayesNetPlayer_SWiener(NannonGameBoard gameBoard) {
		super(gameBoard);
		initialize(NannonGameBoard.getCellsOnBoard(), NannonGameBoard.getPiecesPerPlayer());
	}
	
	private void initialize(int cells, int pieces) {
		NaiveBayesNetPlayer_SWiener.boardSize = cells;
		NaiveBayesNetPlayer_SWiener.pieces = pieces;
		int homeWin[] = new int[pieces + 1]; // P(homeX | win)
		int homeLose[] = new int[pieces + 1]; // P(homeX | !win)
		int safeWin[] = new int[pieces + 1]; // P(safeeX | win)
		int safeLose[] = new int[pieces + 1]; // P(safeeX | !win)
		int winBoard[][] = new int[boardSize][3]; 
		int loseBoard[][] = new int[boardSize][3];
		
		for (int i = 0; i < pieces + 1; i++) {
			homeWin[i] = 1;
			homeLose[i] = 1;
			safeWin[i] = 1;
			safeLose[i] = 1;
		}
		
		for (int i = 0; i < boardSize; i++)
			for (int j = 0; j < 3; j++) {
				winBoard[i][j] = 1; 
				loseBoard[i][j] = 1; 
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
		
		if (legalMoves != null) for (List<Integer> move : legalMoves) { // <----- be sure to drop the "false &&" !
			
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
			double pHitOpponentWin = 1;
		    double pHitOpponentLose = 1;
		    double pBrokeMyPrimeWin = 1;
		    double pBrokeMyPrimeLose = 1;
		    double pExtendsPrimeOfMineWin = 1;
		    double pExtendsPrimeOfMineLose = 1;
		    double pCreatesPrimeOfMineWin = 1;
		    double pCreatesPrimeOfMineLose = 1;
			double pHomeWin = (double) homeWin[resultingBoard[1]] / (double) wins;
			double pHomeLose = (double) homeLose[resultingBoard[1]] / (double) losses;
			double pSafeWin = (double) safeWin[resultingBoard[3]] / (double) wins;
			double pSafeLose = (double) safeLose[resultingBoard[3]] / (double) losses;

			double pBoardWin = 1;
			double pBoardLose = 1;
			
			if (hitOpponent) {
				pHitOpponentWin = (double) hitOpponentWin / (double) wins;
				pHitOpponentLose = (double) hitOpponentLose / (double) losses;
			}
			if (brokeMyPrime) {
				pBrokeMyPrimeWin = (double) brokeMyPrimeWin / (double) wins;
				pBrokeMyPrimeLose = (double) brokeMyPrimeLose / (double) losses;
			}
			if (extendsPrimeOfMine) {
				pExtendsPrimeOfMineWin = (double) extendsPrimeOfMineWin / (double) wins;
				pExtendsPrimeOfMineLose = (double) extendsPrimeOfMineLose / (double) losses;
			}
			if (createsPrimeOfMine) {
				pCreatesPrimeOfMineWin = (double) createsPrimeOfMineWin / (double) wins;
				pCreatesPrimeOfMineLose = (double) createsPrimeOfMineLose / (double) losses;
			}
			
	    	for (int i = 0; i < boardSize; i++) {
	    		pBoardWin = pBoardWin * winBoard[i][resultingBoard[i + 7]];
	    		pBoardLose = pBoardLose * loseBoard[i][resultingBoard[i + 7]];
	    	}
			
	    	//with the naive assumption we can just multiply everything individually
	    	double winLikeliness = pHomeWin * pSafeWin * pHitOpponentWin * pBrokeMyPrimeWin * pExtendsPrimeOfMineWin * pCreatesPrimeOfMineWin * pBoardWin;
	    	double lossLikeliness = pHomeLose * pSafeLose * pHitOpponentLose * pBrokeMyPrimeLose * pExtendsPrimeOfMineLose * pCreatesPrimeOfMineLose * pBoardLose;
	    	
	    	double pWin = (winLikeliness * ((double) wins / ((double) wins + (double) losses))) / (lossLikeliness * ((double) wins / ((double) wins + (double) losses)));
			
			if (pWin > pBest) {
				pBest = pWin;
				bestMove = move;
			}
			
			if (pBest > bestRatio) bestRatio = pBest;
		}
		
		return (bestMove == null) ? Utils.chooseRandomElementFromThisList(legalMoves) : bestMove; // In you own code you should of course get rid of this line.
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
			
		    // Increment array nodes/variables based on win/lose
		    if (didIwinThisGame) {
		    	if (hitOpponent) hitOpponentWin++;
		    	if (brokeMyPrime) brokeMyPrimeWin++;
		    	if (extendsPrimeOfMine) extendsPrimeOfMineWin++;
		    	if (createsPrimeOfMine) createsPrimeOfMineWin++;
		    	
		    	homeWin[resultingBoard[1]]++; // homePieces
		    	safeWin[resultingBoard[3]]++; // safePieces
		    	wins++;
		    	
		    	for (int i = 0; i < boardSize; i++) winBoard[i][resultingBoard[i + 7]]++;
		    } else {
		    	if (hitOpponent) hitOpponentLose++;
		    	if (brokeMyPrime) brokeMyPrimeLose++;
		    	if (extendsPrimeOfMine) extendsPrimeOfMineLose++;
		    	if (createsPrimeOfMine) createsPrimeOfMineLose++;
		    	
		    	homeLose[resultingBoard[1]]++; // homePieces
		    	safeLose[resultingBoard[3]]++; // safePieces
		    	losses++;
		    	
		    	for (int i = 0; i < boardSize; i++) loseBoard[i][resultingBoard[i + 7]]++;
		    }
			//}
		}
	}
	
	@Override
	public void reportLearnedModel() {
		Utils.println("\n-------------------------------------------------");
		Utils.println("This is the learned model for " + getPlayerName());
		Utils.println("Best home pieces value: " + Integer.toString(bestCreatesPrimeOfMine));
		Utils.println("Best win/loss ratio: " + bestRatio);
		Utils.println("\n-------------------------------------------------");
	}
}
