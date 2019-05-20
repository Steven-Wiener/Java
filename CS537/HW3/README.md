# Simulate Nannon

This program contains some admittedly messy code (most already provided by the instructor) in which to simulate the game of [***Nannon***](http://www.nannon.com/rules.html) between two chosen "players".

After supplying a Player X and Player O, the program will play 100,000 burn-in games to ensure things are functioning correctly, followed by 1,000,000 simulated games between the two players. The console will then print the results including win %, and the mean number of turns per game, no-possible-moves, and forced moves for each player.

My best player on average turned out to be FullJointProbTablePlayer_SWiener, which is intuitive since it retains the most information.

## Getting Started

Simply compile PlayNannon.java, then run with the following usage:

```
java PlayNannon <PlayerX> <PlayerO>
```

Example:

```
java PlayNannon FullJointProbTablePlayer_SWiener greedy
```

**Players** (and *files*) to choose from:

1) **random** (*RandomNannonPlayer.java*) : For every turn, takes element from list of legal moves

2) **manual** (*ManualPlayer.java*) : User plays turn by inputting move

3) **HandCodedPlayer_SWiener** or **greedy** (*HandCodedPlayer_SWiener.java*) : Simulates greedy hand-coded player algorithm

4) **FullJointProbTablePlayer_SWiener** (*FullJointProbTablePlayer_SWiener.java*) : Uses joint probabilities with variables that represent the game board from a certain perspective

   * cell1-6: Piece in cell for all c cells, with 3 pieces {0, 1, 2}

   * homePieces: Number of pieces currently in home area {0 ... 3}

   * safePieces: Number of pieces currently in safe area {0 ... 3}

   * die: Die value of roll {0 ... 7}
   
   * pBest: Best ratio (set at 0 initially)
   
   * wins/losses: M estimates for wins/losses (set at 1 initially)

   These are stored in two arrays, one each for win and lose. At every move of the FJPT player, we increment the win/lose arrays respectfully, so it increases the probability that we will repeat a move if it resulted in a win previously.

5) **NaiveBayesNetPlayer_SWiener** (*NaiveBayesNetPlayer_SWiener.java*) : Uses the following independent variables (two each for win/lose)

   * pHitOpponent: hotOpponent on wins/losses divided by number of wins/losses

   * pBrokeMyPrime: brokeMyPrime on wins/losses divided by number of wins/losses

   * pExtendsPrimeOfMine: Whether the move extends a prime of my player

   * pCreatesPrimeOfMine: Whether the move creates a prime of my player

   * pHome: Number of pieces at home divided by number of wins
   
   * pSafe: Number of pieces at save divided by number of wins
   
   * pBoard: Boards that win/lose divided by number of wins/losses

   On wins, the win editions of these are incremented, likewise for loss editions. When choosing a move, the probability of a given move is calculated by multiplying all probabilities together, then selecting the best move out of this or a new random move

6) **BayesNetPlayer_SWiener** (*BayesNetPlayer_SWiener.java*) : Builds upon previous NaiveBayesNetPlayer by adding additional move effect between brokeMyPrime and hotOpponent, representing that if a mvoe broke a prime, a winning move hitting an opponent may increase the probability of winning. The chooseMove method is altered as well, by adding an extra variable called pBrokeHit(win/lose) that is added to the probability calculation

### Prerequisites

* [Java](https://openjdk.java.net/install/)

## Files:

### Unique:

* BayesNetPlayer_SWiener.java

* FullJointProbTablePlayer_SWiener.java

* HandCodedPlayer_SWiener.java (Modified)

* NativeBayesNetPlayer_SWiener.java

### Provided:

* GreedyHandCodedPlayer.java

* GUI_Player.java

* HandCodedPlayer.java (Template)

* ManageMoveEffects.java

* ManualPlayer.java

* Nannon.java

* NannonGameBoard.java

* NannonGUI.java

* NannonPlayer.java

* PlayingField.java

* PlayNannon.java

* RandomNannonPlayer.java

* Utils.java