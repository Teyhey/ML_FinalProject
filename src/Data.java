
public class Data {

	int choice;
	Person A;
	Person B;
	
	public Data(int c, Person a, Person b) {
		super();
		this.choice = c;
		this.A = a;
		this.B = b;
	}

	public Person getA() {
		return A;
	}

	public void setA(Person a) {
		A = a;
	}

	public Person getB() {
		return B;
	}

	public void setB(Person b) {
		B = b;
	}

	public int getChoice() {
		return choice;
	}

	public void setChoice(int choice) {
		this.choice = choice;
	}
	

	
	
}
