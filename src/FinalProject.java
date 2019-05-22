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

	ArrayList<Person> A = new ArrayList<Person>(); // All person A data goes
													// here
	ArrayList<Person> B = new ArrayList<Person>(); // All person B data goes
													// here
	ArrayList<Data> allRows = new ArrayList<Data>(); // Each index corresponds
														// to a row from the
														// .csv file

	// We perform a 70/30 split on 2750 instances of the data
	// this is 50% of 5500 instances
	ArrayList<Data> trainingSplit = new ArrayList<Data>();
	ArrayList<Data> validationSplit = new ArrayList<Data>();

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

	}

	public double[] applyMultimonial(Data d) {

		return null;
	}

	public boolean classification(Data d) {
		return (Boolean) null;
	}

	public String reportAccuracy() {
		return null;
	}

	// ********************************************************
	// LOGISTIC REGRESSION
	// ********************************************************

	
	
	
	
	// ********************************************************
	// PERCEPTRON
	// ********************************************************
	
	
	
	
	

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

	}

}
