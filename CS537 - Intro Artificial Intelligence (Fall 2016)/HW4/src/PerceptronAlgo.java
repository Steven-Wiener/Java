import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

///////////////////////////////////////////////////////////////////////////////
//Title:            PerceptronAlgo
//Files:            PerceptronAlgo.java
//Semester:         CS537 Fall 2016
//
//Author:           Steven Wiener
//Email:            SWiener@wisc.edu
///////////////////////////////////////////////////////////////////////////////

public class PerceptronAlgo {
	public static ArrayList<Feature> features;
	public static int numFeatures;
	public static double alpha = 0.1;
	public static double threshold = 0.0;
	public static double maxTune = Double.MIN_VALUE;
	public static double testAcc = 0.0;
	public static int numExamples;
	public static int epoch;
	public static String posVal;
	public static String outputOne;

	public static void main(String[] args) {
        // Exit if no train/test data provided
        if (args.length != 3) {
        	System.err.println("Usage: java PerceptronAlgo <train.data> <tune.data> <test.data>");
        	System.exit(1);
        }

		// Read files
		ArrayList<Example> trainExamples = new ArrayList<>();
		ArrayList<Example> tuneExamples = new ArrayList<>();
		ArrayList<Example> testExamples = new ArrayList<>();
		try {
			trainExamples = readFile(new Scanner(new File(args[0])));
			tuneExamples = readFile(new Scanner(new File(args[1])));
			testExamples = readFile(new Scanner(new File(args[2])));
		} catch (Exception e) { e.printStackTrace(); }

		// Add feature inputs and bias
		ArrayList<Input> perceptronIn = new ArrayList<Input>();
		for (int i = 0; i < numFeatures; i++)
			perceptronIn.add(new Input(features.get(i), 0));
		perceptronIn.add(new Input(new Feature("Bias", -1), 0));

		createPerceptron(trainExamples, perceptronIn);
		for (int i = 1; i <= 1000; i++) {
			ArrayList<Example> trainPermutation = new ArrayList<Example>();
			ArrayList<Example> tempList = new ArrayList<Example>();

			// Permute list by assigning random integer to every element, then sort
			tempList.addAll(trainExamples);
			while (tempList.size() > 0) {
				int randInt = new Random().nextInt(tempList.size());
				trainPermutation.add(tempList.get(randInt));
				tempList.remove(randInt);
			}

			createPerceptron(trainPermutation, perceptronIn);
			if ((i % 50) == 0) {
				double tuneAccuracy = getAccuracy(tuneExamples, perceptronIn);
				double testAccuracy = getAccuracy(testExamples, perceptronIn);
				DecimalFormat df = new DecimalFormat("0.0");
				System.out.print("Epoch " + i + ": ");
				System.out.print("train = " + (df.format(getAccuracy(trainPermutation, perceptronIn) * 100)) + "% ");
				System.out.print("tune = " + (df.format(tuneAccuracy * 100)) + "% ");
				System.out.print("test = " + (df.format(testAccuracy * 100)) + "% \n");
				/*
				// CSV printout
				System.out.print(i + ",");
				System.out.print((getAccuracy(trainPermutation, perceptronIn) * 100) + ",");
				System.out.print((tuneAccuracy * 100) + ",");
				System.out.print((testAccuracy * 100) + "\n");				
				 */
				if (tuneAccuracy > maxTune) {
					maxTune = tuneAccuracy;
					testAcc = testAccuracy;
					epoch = i;
				}
			}
		}

		// Print statistics
		DecimalFormat pf = new DecimalFormat("0.0");
		DecimalFormat wf = new DecimalFormat(" 0.0;-0.0");
		System.out.println("\nThe tune set was highest (" + pf.format(maxTune * 100) + "% accuracy) at Epoch " + epoch + ". Test set = " + pf.format(testAcc * 100) + "% here.\n");
		for (Input input : perceptronIn)
			System.out.println("Wgt = " + wf.format(input.weight) + " " + input.feature.name);
		System.out.println("\nThreshold: " + wf.format(threshold));
	}

	private static double getAccuracy(ArrayList<Example> examples, ArrayList<Input> perceptronIn) {
		int numCorrect = 0;
		for (Example example : examples) {
			// Compute number correct
			double weightedSum = 0.0;
			for (int i = 0; i < numFeatures; i++)
				weightedSum += example.features.get(i).value * perceptronIn.get(i).weight;
			weightedSum += perceptronIn.get(perceptronIn.size() - 1).feature.value * perceptronIn.get(perceptronIn.size() - 1).weight;

			if (((weightedSum >= threshold) ? 1 : 0) == example.category)
				numCorrect++;
		}
		// Return accuracy
		return (double) numCorrect / examples.size();
	}

	private static void createPerceptron(ArrayList<Example> trainExamples, ArrayList<Input> perceptronIn) {
		for (int i = 0; i < numExamples; i++) {
			Example currentExample = trainExamples.get(i);
			int hyp;
			do {
				double weightedSum = 0.0;
				for (int j = 0; j < numFeatures; j++)
					// Add feature value * weight
					weightedSum += currentExample.features.get(j).value * perceptronIn.get(j).weight;
				// Add last feature
				weightedSum += perceptronIn.get(perceptronIn.size() - 1).feature.value * perceptronIn.get(perceptronIn.size() - 1).weight;

				hyp = (weightedSum >= threshold) ? 1 : 0;
				if (currentExample.category != hyp) {
					// Change feature weight and update threshold
					for (int j = 0; j < numFeatures; j++)
						perceptronIn.get(j).weight += (alpha * (currentExample.category - hyp) * currentExample.features.get(j).value);
					perceptronIn.get(perceptronIn.size() - 1).weight += (alpha * (currentExample.category - hyp) * -1);
					threshold = perceptronIn.get(perceptronIn.size() - 1).weight;
				}
			} while (currentExample.category != hyp);
		}
	}

	private static ArrayList<Example> readFile(Scanner fileScanner) {
		numFeatures = Integer.parseInt(nextValidLine(fileScanner));
		features = new ArrayList<Feature>();

		for (int i = 0; i < numFeatures; i++) {
			String line;
			while ((line = fileScanner.nextLine()) != null) // Skip empty or commented lines
				if (line.startsWith("//") || line.trim().isEmpty())
					continue; // Skip line
				else
					break;

			Scanner lineScanner = new Scanner(line);
			String name = lineScanner.next();
			features.add(new Feature(name));
			lineScanner.next(); // Skip dash
			posVal = lineScanner.next();
			lineScanner.next(); // Skip negVal, disregarded
			lineScanner.close();
		}

		outputOne = nextValidLine(fileScanner);
		nextValidLine(fileScanner); // Skip next line, outputTwo is disregarded
		numExamples = Integer.parseInt(nextValidLine(fileScanner));
		// Iterate through examples and add to list
		ArrayList<Example> examples = new ArrayList<>();
		for (int i = 0; i < numExamples; i++) {
			String line;
			while ((line = fileScanner.nextLine()) != null) // Skip empty or commented lines
				if (line.startsWith("//") || line.trim().isEmpty())
					continue; // Skip line
				else
					break;
			Scanner lineScanner = new Scanner(line);
			Example ex = new Example(new ArrayList<>(), lineScanner.next(), lineScanner.next().equals(outputOne) ? 0 : 1);

			for (int j = 0; j < numFeatures; j++)
				ex.features.add(new Feature("feature" + j, lineScanner.next().equals(posVal) ? 0 : 1));
			lineScanner.close();
			examples.add(ex);
		}
		return examples;
	}

	private static String nextValidLine(Scanner fileScanner) {
		String line;
		while ((line = fileScanner.nextLine()) != null) // Skip empty or commented lines
			if (line.startsWith("//") || line.trim().isEmpty())
				continue; // Skip line
			else
				break;

		Scanner lineScanner = new Scanner(line);
		String next = lineScanner.next();
		lineScanner.close();
		return next;
	}
}

class Input {
	public Feature feature;
	public double weight;

	public Input(Feature feature, double weight) {
		this.feature = feature;
		this.weight = weight;
	}

	public void setFeatureWeight(double weight) {
		this.weight = weight;
	}

	public double getFeatureWeight() {
		return this.weight;
	}
}

class Example {
	public List<Feature> features;
	public String name;
	public int category;

	public Example(List<Feature> features, String name, int category) {
		this.features = features;
		this.name = name;
		this.category = category;
	}

	public void setListOfFeatures(ArrayList<Feature> features) {
		this.features = features;
	}
}

class Feature {
	public String name;
	public int value;

	public Feature(String name) {
		this.name = name;
	}

	public Feature(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}
}