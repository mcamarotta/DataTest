/**
 * This class is the root of the algorithm's hierarchy. An Algorithm represents a combination strategy.
 * The algorithm contains a vector of @see Set elements, a list of @see PairsTable and a collection of integers, which represents the selected positions.
 * When the algorithm is executed from a web application, a JspWriter out object can be assigned to the algorithm. If it exists, each algorithm
 * will show the different steps of its execution.
 *  @author Macario Polo Usaola
 */

package core;

import java.util.Vector;


import core.pairwise.Pair;
import core.pairwise.PairsTable;

public abstract class Algorithm {
	protected Vector<SetCustom> sets;
	protected long[] divisors;
	protected long maxNumberOfCombinations;
	protected OrderedVector selectedPositions;
	protected OrderedVector preSelectedPositions;

	protected PairsTable[] pairsTables;

	/**
	 * Builds an instance of the algorithm.
	 */
	public Algorithm() {
		this.sets=new Vector<SetCustom>();
		this.selectedPositions=new OrderedVector();
		this.preSelectedPositions=new OrderedVector();

	}

	/**
	 * Removes the collection of selected positions
	 */
	public void clear() {
		this.selectedPositions.clear();
	}

	
	public void addPreselectedCombination(long index) {
		this.preSelectedPositions.add(index);
	}


	/**
	 * Adds a set (i.e., a parameter) to the algorithm. Each set has a set of @see Element
	 * @param set The set to be added
	 * @see SetCustom
	 */
	public void add(SetCustom set) {
		this.sets.add(set);
		this.calculateDivisors();
	}

	/**
	 * @return The maximum number of possible combinations. This is the product of the cardinals of all the sets in the algorithm.
	 */
	public long getMaxNumberOfCombinations() {
		if (this.maxNumberOfCombinations!=0)
			return this.maxNumberOfCombinations;
		this.maxNumberOfCombinations=1;
		for (SetCustom s : this.sets)
			this.maxNumberOfCombinations=this.maxNumberOfCombinations*s.size();
		return this.maxNumberOfCombinations;
	}

	/**
	 *
	 * @param position
	 * @return the @see Combination object corresponding to the position passed as parameter
	 */
	public Combination getCombination(long position) {
		Combination result=getSizedCombination(this.sets.size());
		for (int i=0; i<result.size(); i++) {
			long divisor=this.divisors[i];
			SetCustom s=this.sets.get(i);
			long elementPosition=(position/divisor)%s.size();
			result.setValuePosition(i, elementPosition);
			Element element=s.getElementAt((int) elementPosition);
			result.setValue(i, element);
		}
		return result;
	}

	/**
	 *
	 * @param size The number of elements the combination (test case) will have
	 * @return A combination adequately sized (with so many elements as sets there are)
	 */
	protected Combination getSizedCombination(int size) {
		return new Combination(size);
	}

	protected void calculateDivisors() {
//		verbose("<ul><li>Calculating divisors</li><ol>");
		this.divisors=new long[this.sets.size()];
		for(int i=0; i<this.sets.size(); i++) {
			long divisor=1;
			for (int j=i+1; j<this.sets.size(); j++) {
				SetCustom s=this.sets.get(j);
				divisor=divisor*s.size();
			}
//			verbose("<li>Divisor for set #" + i + "=" + divisor + "</li>");
			this.divisors[i]=divisor;
		}
//		verbose("</ol></ul>");
	}

	/**
	 *
	 * @return all the combinations (i.e., the cartesian product of all the elements in the sets)
	 */
	public Vector<Combination> getAllCombinations() {
		Vector<Combination> result=new Vector<Combination>();
		for (long i=0; i<this.maxNumberOfCombinations; i++) {
			result.add(getCombination(i));
		}
		return result;
	}

	/**
	 *
	 * @return A string with the positions of all the selected combinations
	 */
	public String toStringPositions() {
		String result="Positions= {\n";
		long numberOfCombinations=this.selectedPositions.size();
		for (long i=0; i<numberOfCombinations; i++) {
			Combination combination=this.getCombination(this.selectedPositions.get(i));
			result+=i + "-> " + combination.toStringPositions() + "\n";
		}
		result+="}\n";
		return result;
	}

	/**
	 * @return A string with the elements in all the selected combinations
	 */
	public String toString() {
		String result="Values = {\n";
		int cont=0;
		for (long sp : this.selectedPositions) {
			Combination combination=this.getCombination(sp);
			result+=cont + "-> " + combination.toString() + "\n";
			cont++;
		}
		result+="}\n";
		return result;
	}

	public String selectedOrders() {
		String result="selectedPositions = {";
		long numberOfCombinations=this.selectedPositions.size();
		for (long i=0; i<numberOfCombinations; i++) {
			result+=this.selectedPositions.get(i) + "\n";
		}
		result+="}\n";
		return result;
	}

	/**
	 * Function which must be implemented by any combination strategy
	 */
	public abstract void buildCombinations();

	public Vector<Combination> getSelectedCombinations() {
		Vector<Combination> result=new Vector<Combination>();
		for (Long i : this.selectedPositions) {
			result.add(this.getCombination(i));
		}
		return result;
	}

	/**
	 * Function which returns the algorithm's name
	 * @return the algorithm's name
	 */
	public abstract String getName();

	/**
	 *
	 * @return A vector with the sets
	 */
	public Vector<SetCustom> getSets() {
		return sets;
	}

	/**
	 * Returns the number of pairs visited in a set by the element passed as parameter
	 * @param setPosition the index of the set we are interested in counting its pairs
	 * @param elementPosition the position of the element whose participation in pairs must be counted
	 * @return the number of pairs visited
	 */
	protected int getPairsVisited(int setPosition, int elementPosition) {
		int result=0;
		for (PairsTable pt : this.pairsTables) {
			if (pt.getIndexA()==setPosition) {
				for (Pair p : pt.getPairs()) {
					if (p.weight()==0 && p.getA()==elementPosition)
						result++;
				}
			} else if (pt.getIndexB()==setPosition) {
				for (Pair p : pt.getPairs()) {
					if (p.weight()==0 && p.getB()==elementPosition)
						result++;
				}
			}
		}
		return result;
	}

	/**
	 * Returns the number of pairs visited by a given combination in the set passed as second parameter
	 * @param c the combination whose visited pairs must be counted
	 * @param setPosition the position of the set
	 * @return the number of pairs visited
	 */
	protected int getPairsVisited(Combination c, int setPosition) {
		int pairsVisited=0;
		for (int i=0; i<c.size(); i++) {
			if (c.getPosition(i)!=-1) {
				if (i!=setPosition) {
					Pair p=null;
					PairsTable pt;
					if (i>setPosition) {
						pt=this.findPairsTable(setPosition, i);
						p=pt.getPair(c.getPosition(setPosition), c.getPosition(i));
					} else if (i<setPosition) {
						pt=this.findPairsTable(i, setPosition);
						p=pt.getPair(c.getPosition(i), c.getPosition(setPosition));
					}
					if (p==null)
						return Integer.MIN_VALUE;
					if (p.weight()==0)
						pairsVisited++;
				}
			}
		}
		return pairsVisited;
	}

	/**
	 * Returns the pairsTable corresponding to the sets a and b
	 * @param a the index of the first set involved in the pairs table
	 * @param b the index of the second set involved in the pairs table
	 * @return the pair table
	 */
	protected PairsTable findPairsTable(long a, long b) {
/*		if (a>b) {
			int aux=a;
			a=b;
			b=aux;
		}*/
		for (PairsTable pt : this.pairsTables)
			if (pt.getIndexA()==a && pt.getIndexB()==b)
				return pt;
		return null;
	}

	/**
	 * Builds the pairs tables corresponding to the elements added to this algorithm
	 * @return the pairs tables
	 */
	protected PairsTable[] buildPairTables() {
		int numberOfTables=this.sets.size()*(this.sets.size()-1)/2;
		PairsTable[] result=new PairsTable[numberOfTables];
		PairsTable auxi;
		int cont=0;
		for (int i=0; i<this.sets.size(); i++) {
			for (int j=i+1; j<this.sets.size(); j++) {
				auxi=getPairsTable(i, j);
				result[cont++]=auxi;
			}
		}
		return result;
	}

	/**
	 * Builds the pair table corresponding to the sets i and j
	 * @param i the index of the first set
	 * @param j the index of the second set
	 * @return the pairs table
	 */
	protected PairsTable getPairsTable(int i, int j) {
		SetCustom a = this.sets.get(i);
		SetCustom b = this.sets.get(j);
		PairsTable result=new PairsTable(a, b);
		result.setIndexA(i);
		result.setIndexB(j);
		return result;
	}



	/**
	 *
	 * @return The number of visits made to pairs
	 */
	public int getNumberOfVisits() {
		int result=0;
		for (PairsTable pt : this.pairsTables) {
			Vector<Pair> pairs=pt.getPairs();
			for (Pair p : pairs) {
				result+=p.weight();
			}
		}
		return result;
	}

	/**
	 *
	 * @return The number of test cases in the suite
	 */
	public int getTestSize() {
		return this.selectedPositions.size();
	}
	


	public long[] getDivisors() {
		return divisors;
	} 
	
	public abstract boolean requiresRegister();
}
