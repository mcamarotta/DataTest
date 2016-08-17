package core;

import java.util.Vector;


import core.pairwise.Pair;
import core.pairwise.PairsTable;





public class Combination {
	protected long[] positions;
	protected Element[] elements;
	protected double fitness;
	protected long index;

	public Combination(int size) {
		this.positions=new long[size];
		this.elements=new Element[size];
		this.index=0;
	}
	

	public long getIndex() {
		return index;
	}

	public void setIndex(long index) {
		this.index = index;
	}

	public int size() {
		return this.positions.length;
	}

	public void setValue(int position, Element element) {
		this.elements[position]=element;
	}
	
	public void setValue(long position, Element element) {
		this.elements[(int) position]=element;
	}

	public void setValuePosition(long position, long valueIndex) {
		this.positions[(int) position]=valueIndex;
	}

	public void setValuePosition(int position, int valueIndex) {
		this.positions[position]=valueIndex;
	}
	
	public final Element[] getElements() {
		return this.elements;
	}

	public long getPosition(int position) {
		return this.positions[position];
	}

	public boolean equals(Object o) {
		if (!(o instanceof Combination))
			return false;
		Combination c=(Combination) o;
		if (this.size()!=c.size())
			return false;
		for (int i=0; i<this.elements.length; i++)
			if (!(this.elements[i].equals(c.elements[i])))
				return false;
		return true;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public int getWeightOfPairs(PairsTable[] pptt) {
		int result=0;
		for (int i=0; i<pptt.length; i++) {
			PairsTable pt=pptt[i];
			result+=pt.weightOfThePairs(this);
		}
		return result;
	}

	public int getNumberOfPairsVisited(PairsTable[] pptt) {
		int result=0;
		for (int i=0; i<pptt.length; i++) {
			PairsTable pt=pptt[i];
			result+=pt.getNumberOfPairsVisited(this);
		}
		return result;
	}
	
	public Vector<Pair> getPairsVisited(PairsTable[] pptt) {
		Vector<Pair> result=new Vector<Pair>();
		for (int i=0; i<pptt.length; i++) {
			PairsTable pt=pptt[i];
			result.add(pt.getPairVisited(this));
		}
		return result;
	}
	
	public double getSumOfSelectionFactors(PairsTable[] pptt) {
		double result=0;
		Vector<Pair> pairs=this.getPairsVisited(pptt);
		for (Pair p : pairs)
			result+=p.getSelectionFactor();
		return result;
	}

	public boolean contains(Pair p, int indexA, int indexB) {
		if (this.positions[indexA]==p.getA() && this.positions[indexB]==p.getB())
			return true;
		return false;
	}

	public void visitPairs(PairsTable[] pptt) {
		int cont=0;
		for (int i=0; i<this.elements.length; i++) {
			for (int j=i+1; j<this.elements.length; j++) {
				PairsTable pt=pptt[cont++];
				pt.increaseWeightOfPair(this.positions[i], this.positions[j]);
			}
		}
	}

	public String toString() {
		String result="{";
		for (Element e : this.elements)
			result+=e.toString() + ", ";
		if (result.endsWith(", "))
			result=result.substring(0, result.length()-2);
		result+="}";
		return result;
	}

	public String toStringPositions() {
		String result="{";
		for (long p : this.positions)
			result+=p + ", ";
		result=result.substring(0, result.length()-2);
		result+="}";
		return result;
	}

	public void updateIndex(long[] divisors) {
		this.index=0;
		for (int i=0; i<divisors.length; i++) {
			this.index=this.index+this.positions[i]*divisors[i];
		}
	}
	
	public Combination copy() {
		Combination result=new Combination(this.size());
		for (int i=0; i<result.size(); i++) {
			result.positions[i]=this.positions[i];
			result.elements[i]=this.elements[i];
			result.index=this.index;
		}
		return result;
	}
}
