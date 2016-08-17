package core.pairwise;


public class Pair {
	protected int a;
	protected int b;
	protected int weight;
	private double selectionFactor;

	public Pair(int a, int b) {
		this.a=a;
		this.b=b;
		this.weight=0;
		this.selectionFactor=0;
	}

	public String toString() {
		return "(" + a + ", " + b + ") -> " + this.weight;
	}

	public int weight() {
		return this.weight;
	}

	public int getA() {
		return a;
	}

	public int getB() {
		return b;
	}

	public void increaseWeight() {
		this.weight=weight+1;
	}

	public Pair copy() {
		return new Pair(this.a, this.b);
	}

	public double getSelectionFactor() {
		return this.selectionFactor;
	}

	public void setSelectionFactor(double selectionFactor) {
		this.selectionFactor = selectionFactor;
	}

}
