# Decision Tree Learning with Bagging

This program is an extension of the BuildAndTestDecisionTree function in HW1. Rather than having several train and test data sets to choose from, a set of 101 bootstrap samples are provided for the algorithm to train on as well as 298 tune-set and 298 test-set examples. After training, the predictions of the 101 trees are collected for each of the tune- and test-set examples, and the accuracy is output in .csv format, with the following combination rule applied:

For *L* in all odd-numbered values from 1 to 101, for both the tune set and the test set, if at least *L* of the 101 learned trees predict category 1, then output category 1, otherwise output category 2

## Getting Started

First, extract sets.zip to your working directory

Then, simply compile DecisionTreeBaggingExtension.java, then run with the following usage:

```
java DecisionTreeBaggingExtension
```

### Prerequisites

* [Java](https://openjdk.java.net/install/)

### Machine Learning Algo(s):

* [ID3](https://en.wikipedia.org/wiki/ID3_algorithm)

* [Bootstrap Aggregating (Bagging)](https://en.wikipedia.org/wiki/Bootstrap_aggregating)
