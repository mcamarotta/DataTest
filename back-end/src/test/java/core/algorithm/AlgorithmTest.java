package core.algorithm;

import core.Algorithm;
import core.Element;
import core.SetCustom;
import junit.framework.TestCase;


public abstract class AlgorithmTest extends TestCase {
	protected SetCustom[] sets;
	protected int numberOfSets=4;
	protected int numberOfElements=5;

	public void setUp() {
		this.sets=new SetCustom[numberOfSets];
		for (int i=0; i<sets.length; i++) {
			sets[i]=new SetCustom();
		}

		int cont=0;
		for (int i=0; i<sets.length; i++) {
			cont=0;
			for (int j=0; j<numberOfElements; j++) {
				sets[i].add(new Element(""+cont++));
			}
		}
	}

	public void test1() {
		long timeIni=System.currentTimeMillis();
		Algorithm algorithm=createAlgorithm();
		for (int i=0; i<sets.length; i++) {
			algorithm.add(sets[i]);
		}
		prepare(algorithm);
		algorithm.buildCombinations();
		//System.out.println(algorithm.toStringPositions());
		//System.out.println(algorithm.toString());
		long timeFin=System.currentTimeMillis();
		double time=((double) (timeFin-timeIni))/1000;
		System.out.println("Tiempo: " + time + " segundos");
		postTest(algorithm);
	}

	protected void postTest(Algorithm algorithm) {

	}

	protected abstract Algorithm createAlgorithm();

	protected void prepare(Algorithm a) {}

}
