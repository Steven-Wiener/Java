import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

///////////////////////////////////////////////////////////////////////////////
//Title:            BuildAndTestDecisionTree
//Files:            BuildAndTestDecisionTree.java
//Semester:         CS537 Fall 2016
//
//Author:           Steven Wiener
//Email:            SWiener@wisc.edu
///////////////////////////////////////////////////////////////////////////////

public class BuildAndTestDecisionTree {
    public static final boolean printFullTree = false;
    public static final boolean printCorrect = false;
    public static String defaultCategory;
    public static Node treeRoot = null;

    public static void main(String[] args) {
        // Exit if no train/test data provided
        if (args.length != 2) {
        	System.err.println("Usage: java BuildAndTestDecisionTree <train.data> <test.data>");
        	System.exit(1);
        }
		
    	Feature features = null;
        List<String> categories = null;
        List<Example> trainExamples = null;
        List<Example> testExamples = null;

        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            // Read number of features and add features to model
            String currentLine;
            while (((currentLine = br.readLine()) != null) && (features == null || features.getFeatureMapSize() < features.getTotFeatures())) {
                if (currentLine.startsWith("//") || currentLine.trim().isEmpty())
                	continue; // skip line
                if (features == null)
                	// First line sets number of Features
                	features = new Feature(Integer.parseInt(currentLine.trim()));
                else {
                	// Split feature line up and add name/values to feature set
                    String[] featLine = currentLine.split("- ");
                    features.add(featLine[0].trim(), featLine[1].trim().split("\\s+"));
                }
            }

            // Read categories and set default category
            categories = new ArrayList<>(2);
            while (((currentLine = br.readLine()) != null) && (categories.size() < 2))
                if (currentLine.startsWith("//") || currentLine.trim().isEmpty())
                	continue;
                else
                	categories.add(currentLine.trim());
            // Set the default category as the second category read from the file
            defaultCategory = categories.get(1);

            // Get number of examples
            int numExamples = 0;
            while ((currentLine = br.readLine()) != null)
                if (currentLine.startsWith("//") || currentLine.trim().isEmpty())
                	continue;
                else {
                	numExamples = Integer.parseInt(currentLine.trim());
                	break;
                }
            
            // Read training examples from file
            trainExamples = readExamples(br, features.getKeys(), numExamples);
        } catch (IOException e) { e.printStackTrace(); }
        
        // Create the tree and print off to console
        treeRoot = new ID3Algo(defaultCategory, features, categories).process(new Node(features.getKeys(), trainExamples));
        printTree(treeRoot, "", "", false, 0);

        try (BufferedReader br = new BufferedReader(new FileReader(args[1]))) {
        	String currentLine;
            int featureItr = -1;
            int totFeatures = 0;
            while (((currentLine = br.readLine()) != null) && (featureItr < totFeatures)) {
                if (currentLine.startsWith("//") || currentLine.trim().isEmpty())
                	continue;
                // Skip all features and two more lines for categories
                if (featureItr < 0) {
                	totFeatures = Integer.parseInt(currentLine.trim()) + 2;
                	featureItr = 0;
                } else
                	featureItr++;
            }
            
            // Get number of examples
            currentLine = null;
            int numExamples = 0;
            while ((currentLine = br.readLine()) != null) {
            	if (currentLine.startsWith("//") || currentLine.trim().isEmpty())
            		continue;
                else {
                	numExamples = Integer.parseInt(currentLine.trim());
                	break;
                }
            }
            // Read test examples from file
            testExamples = readExamples(br, features.getKeys(), numExamples);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n\nTesting set classification based on the decision tree:\n");
        predictExamples(testExamples);
    }
    
    public static void predictExamples(List<Example> testExamples) {
        int numIncorrect = 0;
        String predictedCategory;
        for (Example example : testExamples) {
        	Node node = treeRoot;
        	// Iterate through tree nodes until we find one with a category, then set this as the predicted category
            while (node.getCategory() == null)
            	node = node.getChild(example.valueOf(node.getBranchFeature()));
            predictedCategory = node.getCategory();
            
            if (!predictedCategory.equals(example.getCategory())) {
            	numIncorrect ++;
            	System.out.println(String.format("[INCORRECT] (%s) -> Predicted Category: %s", example.getName(), predictedCategory));
            } else if (printCorrect)
            	System.out.println(String.format("[ CORRECT ] (%s) -> Predicted Category: %s", example.getName(), predictedCategory));
        }
        int totExamples = testExamples.size();
        System.out.println(String.format("\n%d of %d predictions are incorrect.\nDecision Tree accuracy: %.2f%%",
                numIncorrect, totExamples, (100 * (double) (totExamples - numIncorrect) / totExamples)));
    }
    
    // Recursively print out the decision tree
    private static void printTree(Node node, String branchLabel, String prefix, boolean isTail, int depth) {
        Map<String, Node> children = node.getChildMap();
        ArrayList<String> features = new ArrayList<String>() {{ addAll(node.getChildMap().keySet()); }};
        /* print the current node */
        System.out.println(prefix + (isTail ? "|__ " : "|-> ") + node.getLabel(branchLabel));
        // Check if depth limit reached
        if (!printFullTree && depth > 5)
        	System.out.println(prefix + "Reached the depth limit (5)");
        else {
            /* recursively print (n-1) children */
            for (int i = 0; i < features.size() - 1; i++)
                printTree(children.get(features.get(i)), features.get(i).toString(), prefix + (isTail ? "    " : "|   "), false, depth + 1);
            /* recursively print the last child */
            if (children.size() > 0) {
                String lastBranch = features.get(features.size() - 1);
                printTree(children.get(lastBranch), lastBranch, prefix + (isTail ? "    " : "|   "), true, depth + 1);
            }
        }
    }

    private static class ID3Algo {
        private List<String> categories;
        private final Feature features;
        private final String defaultCategory;

        public ID3Algo(String defaultCategory, Feature features, List<String> categories) {
            this.defaultCategory = defaultCategory;
            this.features = features;
            this.categories = categories;
        }

        private Node process(Node node) {
        	List<Example> examples = node.getExamples();
        	List<String> feats = node.getFeatures();
        	if ((examples.size() == 0) || (examples.stream().allMatch(e -> e.getCategory().equals(defaultCategory))))
        		// If no examples in node, or all are of default category, set category of node to default
            	node.setCategory(defaultCategory);
        	else if (examples.stream().allMatch(e -> e.getCategory().equals(categories.get(0))))
        		// If all examples are of same (secondary) category, set category of node to secondary
            	node.setCategory(categories.get(0));
        	else if (feats.size() == 0) {
            	// If all features are identical for every example in this node,
            	// set the predicted category to the majority value (favoring the default category)
                if (examples.stream().filter(e -> e.getCategory().equals(categories.get(0))).count() > examples.stream().filter(e -> e.getCategory().equals(defaultCategory)).count())
                	node.setCategory(categories.get(0));
                else
                	node.setCategory(defaultCategory);
            } else {
                String branchFeature = getBranchFeature(node);
                
                node.setBranchFeature(branchFeature);
                List<String> remainingFeats = feats.stream().filter(f -> !f.equals(branchFeature)).collect(Collectors.toList());
                for (String featureVal : features.getValuesFor(branchFeature)) {
                	// Create list of all examples that contain the branching feature
                	List<Example> qualifyingExamples = node.getExamples().stream().filter(f -> f.valueOf(node.getBranchFeature()).equals(featureVal)).collect(Collectors.toList());
                    node.addChild(featureVal, process(new Node(node, remainingFeats, qualifyingExamples)));
                }
            }
            return node;
        }

        private String getBranchFeature(Node node) {
            String branchFeature = null;
            double minInfoRem = Double.MAX_VALUE;
            double infoRem;
            for (String feature : node.getFeatures()) {
                if ((infoRem = infoRemaining(node.getExamples(), feature)) == 0)
                	// If no info remaining, this is the branching feature
                	return feature;
                else if (infoRem < minInfoRem) {
                	// Set new minInfoRem and branchFeature for further iterations to possibly overwrite
                	minInfoRem = infoRem;
                	branchFeature = feature;
                }
            }
            return branchFeature;
        }

        // Implement infoRemaining function from text
        private double infoRemaining(List<Example> examples, String branchFeat) {
            double infoRemaining = 0;
            double fracSize, fracOne, fracTwo;
            for (String featVal : features.getValuesFor(branchFeat)) {
            	// Create list of examples where the branchFeat is equal to the feature value
                List<Example> filteredExamples = examples.stream().filter(f -> f.valueOf(branchFeat).equals(featVal)).collect(Collectors.toList());
                if ((fracSize = filteredExamples.size()) == 0)
                	continue;
                fracOne = filteredExamples.stream().filter(e -> e.getCategory().equals(categories.get(0))).count() / fracSize;
                fracTwo = filteredExamples.stream().filter(e -> e.getCategory().equals(categories.get(1))).count() / fracSize;
                infoRemaining += (fracSize/examples.size()) * infoNeeded(fracOne, fracTwo);
            }
            return infoRemaining;
        }
        
        // Implement infoNeeded function from text
        private double infoNeeded(double fracOne, double fracTwo) {
        	if (fracOne == 0 || fracTwo == 0)
        		return 0;
        	else
        		return (-fracOne * (Math.log(fracOne) / Math.log(2))) + (-fracTwo * (Math.log(fracTwo) / Math.log(2)));
        }
    }

    private static List<Example> readExamples(BufferedReader br, List<String> features, int numExamples) throws IOException {
        String currentLine;
        List<Example> examples = new ArrayList<>();
        while (examples.size() < numExamples && (currentLine = br.readLine()) != null) {
            if (currentLine.startsWith("//") || currentLine.trim().isEmpty())
            	continue;
            String[] exampleLine = currentLine.split("\\s+");
            
            // Create iterators to step through features and read values for these features into the examples list
            Iterator<String> featureItr = features.iterator();
            Iterator<String> valueItr = Arrays.asList(exampleLine).subList(2, exampleLine.length).iterator();
            Map<String, String> map = new HashMap<>();
            while (featureItr.hasNext() && valueItr.hasNext())
            	map.put(featureItr.next(), valueItr.next());
            // Add these values to the example list
            examples.add(new Example(exampleLine[0], exampleLine[1], map));
        }
        return examples;
    }

    private static class Feature {
        private static Map<String, List<String>> map; // Map of value (T/F) corresponding to each feature
        private final int totFeatures; // Number of features specified by file
        private Set<String> keys = new LinkedHashSet<>();

        public Feature(int totFeatures) {
            this.totFeatures = totFeatures;
            map = new HashMap<>(totFeatures);
        }

        public void add(String feature, String... values) {
            keys.add(feature);
            map.put(feature, new ArrayList<>(Arrays.asList(values)));
        }

        public int getFeatureMapSize() {
            return map.size();
        }

        public int getTotFeatures() {
            return totFeatures;
        }

        public List<String> getKeys() {
            return Arrays.asList(keys.toArray(new String[0]));
        }

        public List<String> getValuesFor(String branchFeat) {
            return map.get(branchFeat);
        }
    }
    

    private static class Example {
        private String name;
        private String category;
        private Map<String, String> features = new HashMap<>(); // Map of features and feature values

        public Example(String name, String category, Map<String, String> features) {
            this.name = name;
            this.category = category;
            this.features = features;
        }

        public String getName() {
            return name;
        }

        public String getCategory() {
            return category;
        }

        public String valueOf(String feature) {
            return features.get(feature);
        }
    }
    

    abstract class Tree {
        Node root;
        Feature features;
        List<Example> examples;
        List<String> categories;

        public Tree(List<Example> examples, Feature features, List<String> categories) {
            this.examples = examples;
            this.features = features;
            this.categories = categories;
        }
    }

    private static class Node {
        private Node parent;
        private Map<String, Node> childMap;
        private List<String> features;
        private String category;
        private List<Example> examples;
        private String branchFeature;

        public Node(List<String> features, List<Example> examples) {
            this.examples = examples;
            this.features = features;
            this.childMap = new HashMap<>();
        }

        public Node(Node parent, List<String> features, List<Example> examples) {
            this(features, examples);
            this.parent = parent;
        }

        public List<String> getFeatures() {
            return features;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public List<Example> getExamples() {
            return examples;
        }

        public String getBranchFeature() {
            return branchFeature;
        }

        public void setBranchFeature(String branchFeature) {
            this.branchFeature = branchFeature;
        }

        public Node getChild(String feature) {
            return childMap.get(feature);
        }

        public void addChild(String feature, Node node) {
        	childMap.put(feature, node);
        }

        public Map<String,Node> getChildMap() {
            return childMap;
        }

        public String getLabel(String featureValue) {
        	if (parent == null)
        		return "Root";
        	else
        		return String.format("%s = %s", parent.getBranchFeature(), featureValue)
        				// If we're at the end of a branch, print out the category and how many examples are in the category
                    + ((category != null)	? String.format(" : %s (%d)", getCategory(), getExamples().size())
                    		// If not at the end of the branch, print out how many examples remain under this node
                    						: String.format(" (%d)", getExamples().size()));
        }
    }
}