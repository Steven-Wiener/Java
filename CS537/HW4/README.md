# Perceptron Algorithm

This program contains one main file (PerceptronAlgo.java), and a set of train/tune/test data has been provided.

After iterating through the data sets and separating into features and examples, the program creates a perceptrion from the train examples. It then permutes through the list 1000 times, with a learning rate (alpha) of 0.1, first assigning a random integer to every element, then recreating the perceptron with this permutation. After every 50 iterations, the Epoch #, as well as train, tune, and test set accuracy are output. When the program is complete, the highest tune set (and cooresponding test set) accuracy is displayed, followed by the weights of all features.


## Getting Started

Simply compile PerceptronAlgo.java, then run with the following usage:

```
java PerceptronAlgo <train.data> <tune.data> <test.data>
```

For example:

```
java PerceptronAlgo red-wine-quality-train.data red-wine-quality-tune.data red-wine-quality-test.data
```

### Prerequisites

* [Java](https://openjdk.java.net/install/)

### Machine Learning Algo(s):

* [Perceptron Learning Algorithm](https://en.wikipedia.org/wiki/Perceptron#Learning_algorithm)