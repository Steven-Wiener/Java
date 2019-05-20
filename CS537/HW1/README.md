# Simple Train/Test Machine Learning Using ID3

For this program, there is one main java file (BuildAndTestDecisionTree.java), and several sets of train and test data that can be fed into the included ID3 algorithm. The program will first train the algorithm with the provided ***-train.data, create a prediction tree, and print the prediction tree (a depth-limited tree unless printFullTree is set to true in the source file). The program will then read the provided ***-test.data, predict which category the test example should fall into, and print the example and predicted category for all incorrect predictions (as well as correct predictions if printCorrect is set to true in the source file). Finally, it will print the total number and percent of predictions that were incorrect, with the accuracy of the decision tree.

## Getting Started

Simply compile BuildAndTestDecisionTree.java, then run with the following usage:

```
java BuildAndTestDecisionTree <train.data> <test.data>
```

For example:

```
java BuildAndTestDecisionTree red-wine-quality-train.data red-wine-quality-test.data
```

### Prerequisites

* [Java](https://openjdk.java.net/install/)

### Machine Learning Algo(s):

* [ID3](https://en.wikipedia.org/wiki/ID3_algorithm)

