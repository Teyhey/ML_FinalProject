import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class FinalProject {

	// ********************************************************
	// VARIABLES
	// ********************************************************

	// ------------------ NAIVE BAYES VARIABLES ------------------
	ArrayList<Person> A = new ArrayList<Person>(); // All person A data goes
													// here
	ArrayList<Person> B = new ArrayList<Person>(); // All person B data goes
													// here
	ArrayList<Data> allRows = new ArrayList<Data>(); // Each index corresponds
														// to a row from the
														// .csv file

	// Tells who is most influential row by row
	ArrayList<Boolean> results = new ArrayList<Boolean>();

	int totalChoice1 = 0;
	int totalChoice0 = 0;

	// ------------------ LOGISTIC REGRESSION VARIABLES ------------------
	public static double learningRate;
	public ArrayList<Double> weights;
	// We perform a 70/30 split on 2750 instances of the data
	// this is 50% of 5500 instances
	ArrayList<Data> trainingSplit = new ArrayList<Data>();
	ArrayList<Data> validationSplit = new ArrayList<Data>();

	// Do we need a dictionary???

	// Since the provided test.csv doesn't have a class attribute
	// we have to resort to splitting up the training data.
	// We will put 50% of the Data in here, the other 50% goes
	// toward the 70/30 split above.
	ArrayList<Data> testSplit = new ArrayList<Data>();

	// ********************************************************
	// FUNCTIONS
	// ********************************************************

	public void readFile(String s) throws FileNotFoundException {
		File f = new File(s);
		Scanner scan = new Scanner(f);

		// Tmp holders
		int choice;
		String name;
		long followerCount;
		long followingCount;
		long listedCount;
		double mentionsRecieved;
		double retweetsRecieved;
		double mentionsSent;
		double retweetsSent;
		double postsCount;
		double networkFeature1;
		double networkFeature2;
		double networkFeature3;

		// We don't want the column names
		System.out.println(scan.next());

		while (scan.hasNext()) {

			String all = scan.next();
			String[] allSplit = all.split(",");

			choice = Integer.parseInt(allSplit[0]);

			if (choice == 1) {// A more Influential
				totalChoice1++;
			}

			if (choice == 0) {// B more Influential
				totalChoice0++;
			}

			name = "A"; // A gets read before B
			followerCount = Long.parseLong(allSplit[1]);
			followingCount = Long.parseLong(allSplit[2]);
			listedCount = Long.parseLong(allSplit[3]);
			mentionsRecieved = Double.parseDouble(allSplit[4]);
			retweetsRecieved = Double.parseDouble(allSplit[5]);
			mentionsSent = Double.parseDouble(allSplit[6]);
			retweetsSent = Double.parseDouble(allSplit[7]);
			postsCount = Double.parseDouble(allSplit[8]);
			networkFeature1 = Double.parseDouble(allSplit[9]);
			networkFeature2 = Double.parseDouble(allSplit[10]);
			networkFeature3 = Double.parseDouble(allSplit[11]);

			Person a = new Person(name, followerCount, followingCount, listedCount, mentionsRecieved, retweetsRecieved,
					mentionsSent, retweetsSent, postsCount, networkFeature1, networkFeature2, networkFeature3);
			A.add(a);

			name = "B"; // A gets read before B
			followerCount = Long.parseLong(allSplit[12]);
			followingCount = Long.parseLong(allSplit[13]);
			listedCount = Long.parseLong(allSplit[14]);
			mentionsRecieved = Double.parseDouble(allSplit[15]);
			retweetsRecieved = Double.parseDouble(allSplit[16]);
			mentionsSent = Double.parseDouble(allSplit[17]);
			retweetsSent = Double.parseDouble(allSplit[18]);
			postsCount = Double.parseDouble(allSplit[19]);
			networkFeature1 = Double.parseDouble(allSplit[20]);
			networkFeature2 = Double.parseDouble(allSplit[21]);
			networkFeature3 = Double.parseDouble(allSplit[22]);

			Person b = new Person(name, followerCount, followingCount, listedCount, mentionsRecieved, retweetsRecieved,
					mentionsSent, retweetsSent, postsCount, networkFeature1, networkFeature2, networkFeature3);
			B.add(b);

			Data d = new Data(choice, a, b);
			allRows.add(d);

		} // while

	} // readFile

	// This method populates the test set by splitting the data by 50%.
	public void populateSets() {

		// populates the testSet
		for (int u = (allRows.size() / 2); u < allRows.size(); u++) {
			testSplit.add(allRows.get(u));
		} // for

		int cap = 2750;
		int split70 = (int) (2750 * .70); // = 1,925
		int split30 = (int) (2750 * .30); // = 825
		ArrayList<Data> mixer = new ArrayList<Data>();

		// We only want the other half not used by the testSplit
		for (int s = 0; s < cap; s++) {
			mixer.add(allRows.get(s));
		} // for

		// populates the trainingSet
		for (int p = 0; p < split70; p++) {
			Collections.shuffle(mixer);
			trainingSplit.add(mixer.get(p));
		} // for

		// populates the validationSet
		for (int i = trainingSplit.size(); i < mixer.size(); i++) {
			validationSplit.add(mixer.get(i));
		} // for

	} // populateSets

	// ********************************************************
	// NAIVE BAYES
	// ********************************************************

	public void trainMultimonial() {

		// The total counts that determine influentialness
		double aSum_Counts = 0;
		double bSum_Counts = 0;

		// Computing conditonal probabilty
		for (Data d : allRows) {
			aSum_Counts += (double) d.A.mentionsRecieved + d.A.followerCount + d.A.retweetsRecieved;
			bSum_Counts += (double) d.B.mentionsRecieved + d.B.followerCount + d.B.retweetsRecieved;
		}

		for (Data d : allRows) {
			d.A.inflentialProb = (double) (d.A.mentionsRecieved + d.A.followerCount + d.A.retweetsRecieved + 1)
					/ aSum_Counts + 1;
			d.B.inflentialProb = (double) (d.B.mentionsRecieved + d.B.followerCount + d.B.retweetsRecieved + 1)
					/ bSum_Counts + 1;
			System.out.println("Person A prob: " + d.A.inflentialProb);
			System.out.println("Person B prob: " + d.B.inflentialProb);
		}

	}

	public double[] applyMultimonial(Data d) {

		double[] classScores = new double[2];

		int N = allRows.size();
		double priorA = (double) totalChoice1 / N;// choice 1 A more influential
		double priorB = (double) totalChoice0 / N;// choice 0 B more influential

		System.out.println("Prior A: " + priorA);
		System.out.println("Prior B: " + priorB);

		double personAScore = (double) Math.log(priorA);
		double personBScore = (double) Math.log(priorB);

		System.out.println("PersonA Score [Before]: " + personAScore);
		System.out.println("PersonB Score [Before]: " + personBScore);

		// The totals needed
		double aSum_Counts = (double) d.A.mentionsRecieved + d.A.followerCount + d.A.retweetsRecieved;
		double bSum_Counts = (double) d.B.mentionsRecieved + d.B.followerCount + d.B.retweetsRecieved;

		// We check if
		if (Math.log(d.A.inflentialProb) > 0) {
			d.A.inflentialProb = (double) 1 / 1 + A.size();
			personAScore += (double) Math.log(d.A.inflentialProb * aSum_Counts);

		}

		if (Math.log(d.B.inflentialProb) > 0) {
			d.B.inflentialProb = (double) 1 / 1 + B.size();
			personBScore += (double) Math.log(d.B.inflentialProb * bSum_Counts);

		}

		classScores[0] = personAScore;
		classScores[1] = personBScore;

		System.out.println("PersonA Score [After]: " + personAScore);
		System.out.println("PersonB Score [After]: " + personBScore);

		return classScores;
	}

	public boolean classification(Data d) {

		double[] sz = applyMultimonial(d);
		boolean influential = false;
		double maxScore = 0;

		// If A has a higher score than they are more influential
		if (sz[0] > sz[1]) {
			influential = true; // The personA is marked as influential.
			results.add(influential);
			return influential;
		} // if

		// If B score is > A score than B is more influential
		if (sz[0] < sz[1]) {
			influential = false; // The personA is marked as less influential
			results.add(influential);
			return influential;
		} // if

		return (Boolean) null;

	}

	public String reportAccuracy() {

		String outcome = "";
		double percentageOfAInfluential = 0;
		double percentageOfBInfluential = 0;

		int correctAInfluential = 0;
		int correctBInfluential = 0;
		int wrongAInfluential = 0;
		int wrongBInfluential = 0;

		for (int s = 0; s < allRows.size(); s++) {// choice 0 = B more influential, Choice 1 = A more Influential

			// Person A is tagged as influential but we classified it as wrong
			if (allRows.get(s).choice == 1 && results.get(s) == false) {
				wrongAInfluential++;
			}

			// Person B is tagged as influential and we classified it as such
			if (allRows.get(s).choice == 0 && results.get(s) == false) {
				correctBInfluential++;
			}

			// Person A is tagged as influential and we classified it as such
			if (allRows.get(s).choice == 1 && results.get(s) == true) {
				correctAInfluential++;
			}

			// Person B is tagged as influential but we classified it as wrong
			if (allRows.get(s).choice == 0 && results.get(s) == true) {
				wrongBInfluential++;
			}

		} // for

		percentageOfAInfluential = (double) correctAInfluential / A.size();
		percentageOfBInfluential = (double) correctBInfluential / B.size();

		System.out.println("Correct AInfluential: " + correctAInfluential);
		System.out.println("Correct BInfluential: " + correctBInfluential);
		System.out.println("Wrong AInfluential: " + wrongAInfluential);
		System.out.println("Wrong BInfluential: " + wrongBInfluential);
		System.out.println("Percentage AInfluential: " + percentageOfAInfluential * 100);
		System.out.println("Percentage BInfluential: " + percentageOfBInfluential * 100);

		outcome = "The percentage of Person A being labeled as Influential: " + percentageOfAInfluential * 100 + "\n"
				+ "The percentage of Person B being labeled as Influential: " + percentageOfBInfluential * 100;
		return outcome;

	}

	// ********************************************************
	// LOGISTIC REGRESSION
	// ********************************************************

	// This method populates the necessary weights
	public void populateWeights() {
		for (int i = 0; i < dictionary.size() + 1; i++) {
			weights.add(0.1);
		}
	} // populateWeights

	public void clearWeights() {
		int index = 0;
		for (Double weight : weights) {
			weights.set(index, 0.1);
			index++;
		}
	}

	// Sigmoid function: S(x) = 1/(1+e^-x)
	public static double sigmoid(double x) {
		double result = 1.0 / (1.0 + Math.exp(-x));
		return result;
	} // sigmoid

	// Probability = w0 + sum (from 1 to n) wi * Xi
	public static double calculateProbability(ArrayList<Double> weights, ArrayList<Integer> instance) {
		double result = weights.get(0);
		for (int i = 1; i < weights.size(); i++) {
			result += weights.get(i) * instance.get(i - 1);
		}
		return result;
	} // calculateProbability

	public double gradientAscent(double weight, double x, double observed, ArrayList<Integer> instance,
			ArrayList<Double> weights) {
		double newWeight = weight + (learningRate * x * (observed - sigmoid(calculateProbability(weights, instance))));
		return newWeight;
	}

	// L2 = learningRate * Lambda * wi
	public static double L2(double lambda, double weight) {
		double regularization = learningRate * lambda * weight;
		return regularization;
	} // L2

	public void trainLogisticRegression() {

	}

	public int bestAccuracyLogisticRegression(ArrayList<Double> accuracyArray) {
		int tempIndex = 0;
		int bestIndex = 0;
		double tempAccuracy = 0;
		for (double bestAccuracy : accuracyArray) {
			if (bestAccuracy > tempAccuracy) {
				tempAccuracy = bestAccuracy;
				bestIndex = tempIndex;
			}
			tempIndex++;
		}
		return bestIndex;
	}

	// Classification rule:
	public int classify(ArrayList<Double> weights, ArrayList<Integer> instance) {
		int spam = 0;
		// Pr[yi=1|xi] = 1/(1 + exp(w0 + Sum(wj * xi,j)))
		double probability1 = sigmoid(calculateProbability(weights, instance));
		// If Pr[yi=1|xi] >= .50 then its spam based on the classification rule
		if (probability1 >= .50) {
			spam = 1;
		}
		return spam;
	} // classify

	// Checks the accuracy of the test data set
	// The function iterates through all emails in the test set, classifies each
	// based on the weights from our trained model
	// then does simple matching (compare predicted classification with actual/known
	// labels) to calculate how accurate the model was at predicting
	public double checkAccuracyLogisticRegression(ArrayList<ArrayList<Integer>> dataSet) {
		int match = 0;
		for (int i = 0; i < dataSet.size(); i++) {
			if (dataSet.get(i).get(weights.size() - 1) == classify(weights, dataSet.get(i))) {
				match++;
			}
		}
		double accuracy = ((double) match / (double) dataSet.size());
		return accuracy;

	} // checkAccuracy

	
	
	// ********************************************************
	// PERCEPTRON
	// ********************************************************

	
	public int calculatePerceptron(ArrayList<Double> weights, ArrayList<Integer> instance) {
		double result = weights.get(0);
		for (int i = 1; i < weights.size(); i++) {
			result += weights.get(i) * instance.get(i - 1);
		}
		if (result > 0.0) {
			return 1;
		}
		else {
			return -1;
		}
	}
	
	public double perceptronRule(double weight, double x, double observed, ArrayList<Integer> instance,
			ArrayList<Double> weights) {
		double newWeight = weight + learningRate * x * (observed - (double) calculatePerceptron(weights, instance));
		return newWeight;
	}
	
	// L2 = learningRate * Lambda * wi
	public static double L2P(double lambda, double weight) {
		double regularization = learningRate * lambda * weight;
		return regularization;
	} // L2P
	
	
	public boolean hasConverged(ArrayList<Double> accuracyArray) {
		if(accuracyArray.size() >= 3) {
			double p1 = accuracyArray.get(accuracyArray.size()-1);
			double p2 = accuracyArray.get(accuracyArray.size()-2);
			double p3 = accuracyArray.get(accuracyArray.size()-3);
			
			double within = 0.0001; // Can change this
			
			double mse = (double)(1/3)*((p1-p2)*(p1-p2)+(p1-p3)*(p1-p3)+(p2-p3)*(p2-p3));
			
			if(mse <= within) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	} // hasConverged
	
	public void trainPerceptron() {
		
	}
	
	public int bestAccuracyPerceptron(ArrayList<Double> accuracyArray) {
		int tempIndex = 0;
		int bestIndex = 0;
		double tempAccuracy = 0;
		for(double bestAccuracy: accuracyArray) {
			if(bestAccuracy > tempAccuracy) {
				tempAccuracy = bestAccuracy;
				bestIndex = tempIndex;
			}
			tempIndex++;
		}
		return bestIndex;
	}
	
	public double checkAccuracyPerceptron(ArrayList<ArrayList<Integer>> dataSet) {
		int match = 0;
		for (int i = 0; i < dataSet.size(); i++) {
			if (dataSet.get(i).get(weights.size() - 1) == calculatePerceptron(weights, dataSet.get(i))) {
				match++;
			}
		}
		double accuracy = ((double) match / (double) dataSet.size());
		return accuracy;

	}
	
	
	
	// ********************************************************
	// MAIN
	// ********************************************************

	// Main
	public static void main(String[] args) throws FileNotFoundException {
		String path = "Data/train.csv";
		FinalProject algorithm1 = new FinalProject();
		algorithm1.readFile(path);
		System.out.println("Size of Data list: " + algorithm1.allRows.size());
		System.out.println("Size of A list: " + algorithm1.A.size());
		System.out.println("Size of B list: " + algorithm1.B.size());
		algorithm1.populateSets();
		System.out.println("Size of test list: " + algorithm1.testSplit.size());
		System.out.println("Size of training list: " + algorithm1.trainingSplit.size());
		System.out.println("Size of validation list: " + algorithm1.validationSplit.size());
		algorithm1.trainMultimonial();

		for (Data d : algorithm1.allRows) {
			algorithm1.applyMultimonial(d);
		}

		for (Data d : algorithm1.allRows) {
			algorithm1.classification(d);
		}

		System.out.println(algorithm1.reportAccuracy());
	}

}
