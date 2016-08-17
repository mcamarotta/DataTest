package handler;

import datatypes.DataVariableAndValues;

import java.util.ArrayList;
import java.util.Vector;

import core.Algorithm;
import core.Combination;
import core.SetCustom;
import core.algorithm.*;
import ctweb.ParametersLoader;
import datatypes.EnumAlgorithmNames;
import datatypes.DataCombination;

/**
 * @author Michel Camarotta This class works like a interface between logic of
 *          algorithms and other layer which wants to use some algorithm
 * 
 *         Here there are different methods to invoke the different algorithms
 *         which have different results of pairwise sets.
 */
public final class handlerCombinationCTWebNewLogic {

//	private static ArrayList<DataVariableAndValues> variableAndValues;

	/**
	 * This method receive a set of variables with different values and combine
	 * them with algorithm SimplePairWise. Return back the combination of
	 * variables received by parameter
	 * Use for this the algorithm All Combination
	 * The Explanation of Algorithm: This algorithm generates all combinations 
	 * for the elements (values) contained in the parameters (sets). 
	 * Actually, this is got by calculating the cartesian product of all the sets involved..
	 * 
	 * @param variableAndValues
	 * @return
	 */
	public static ArrayList<DataCombination> getPairWiseCombinationWithAllCombination(ArrayList<DataVariableAndValues> variableAndValues) {
		
		EnumAlgorithmNames algorithmName=EnumAlgorithmNames.allCombinations;
		
		return getCombination(algorithmName,variableAndValues);

	}
	
	
	/**
	 * This method receive a set of variables with different values and combine
	 * them with algorithm SimplePairWise. Return back the combination of
	 * variables received by parameter
	 * Use for this the algorithm Each Choice
	 * The Explanation of Algorithm: The basic idea behind the Each Choice combination strategy is to include each value of each parameter in at 
	 * least one test case. This is achieved by generating test cases by successively selecting unused values for each parameter.
	 * 
	 * @param variableAndValues
	 * @return
	 */
	public static ArrayList<DataCombination> getPairWiseCombinationWithEachChoice(ArrayList<DataVariableAndValues> variableAndValues) {
		
		EnumAlgorithmNames algorithmName=EnumAlgorithmNames.eachChoice;
		
		return getCombination(algorithmName,variableAndValues);

	}
	
	/**
	 * This method receive a set of variables with different values and combine
	 * them with algorithm SimplePairWise. Return back the combination of
	 * variables received by parameter
	 * Use for this the algorithm Simple Pairwise
	 * The Explanation of Algorithm: this algorithm produces test cases with pairwise coverage. 
	 * Pairwise algorithms generate test cases with the goal of visiting all the pairs of the parameter values. 
	 * @param variableAndValues
	 * @return
	 */
	public static ArrayList<DataCombination> getPairWiseCombinationWithSimplePairWise(ArrayList<DataVariableAndValues> variableAndValues) {
		
		EnumAlgorithmNames algorithmName=EnumAlgorithmNames.simplePairwise;
		
		return getCombination(algorithmName,variableAndValues);

	}
	
	
	/**
	 * This method receive a set of variables with different values and combine
	 * them with algorithm SimplePairWise. Return back the combination of
	 * variables received by parameter
	 * Use for this the algorithm PROW - (Pairwise with Restrictions, Order and Weight) 
	 * The Explanation of Algorithm: 
	 * With the PROW algorithm you must: Execute the algorithm (Execute button) to see the pairs table. 
	 * Secondly, select those pairs to be removed. 
	 * Thirdly, assign a selection factor to those pairs you are more interested (just in case it is necessary to repeat pairs).
	 * Finally, press again the Execute button to obtain the test cases.
	 * 
	 * @param variableAndValues
	 * @return
	 */
	public static ArrayList<DataCombination> getPairWiseCombinationWithProwFirstStep(ArrayList<DataVariableAndValues> variableAndValues) {
		
		EnumAlgorithmNames algorithmName=EnumAlgorithmNames.prow;
		
		return getCombination(algorithmName,variableAndValues);

	}

	
	
	/**
	 * This intern method calculate the combinations of values given the variables and values and what algorithm you want to use.
	 * 
	 * @param algorithmName
	 * @param variableAndValues
	 * @return
	 */
	private static ArrayList<DataCombination> getCombination(EnumAlgorithmNames algorithmName,ArrayList<DataVariableAndValues> variableAndValues) {
		
		int howManyColums = variableAndValues.size();

		// The algorithm come from the front-end, and define how to calculate
		// the combinations
		Algorithm a = getAlgorithm(algorithmName);

		// To return the results
		ArrayList<DataCombination> combinations = new ArrayList<DataCombination>();

		// To transform the new set of variable to the structure that legacy
		// code can understand
		Vector<SetCustom> sSets = ParametersLoader.loadSets(variableAndValues);

		// Preparing data as the legacy code did it
		for (SetCustom set : sSets) {
			a.add(set);
		}

		// This comment for who knows the old code: In this implementation we
		// don't need to iterate, because always we will do one time.
		a.clear();
		a.buildCombinations();

		// This line brings the combination done by the algorithm
		Vector<Combination> resOldWayCombinationsToTranform = a.getSelectedCombinations();

		// To transform the results brought by the legacy code to the new
		// structure to return to the up layer which consume this data.
		combinations = transformaOldWayCombinationToNewOne(howManyColums, resOldWayCombinationsToTranform);

		return combinations;

}



	private static ArrayList<DataCombination> transformaOldWayCombinationToNewOne(int howManyColums, Vector<Combination> resOldWayCombinationsToTranform) {
		
		ArrayList<DataCombination> resNewWayCombination = new ArrayList<DataCombination>(); 
		
		
		//I need the combination by combination transforming the elements that it has to the new datatype 
		//Which will be interpreted by the Rest Service to send back the information.
		for (Combination oldWayCombination : resOldWayCombinationsToTranform) {
			DataCombination combinationNewWay = new DataCombination(); 
			for (int i = 0; i < howManyColums; i++) {
				//The old combination has a set of values. This values are stored on Strings.
				//The only thing that I need to do is iterate and copy the values to the new way, more simple. 
				combinationNewWay.addValueToCombination(oldWayCombination.getElements()[i].getValue().toString());
			}
			resNewWayCombination.add(combinationNewWay);
			
//			if (a instanceof PROWAlgorithm) {
////				PROWAlgorithm aa=(PROWAlgorithm) a;
////				out.print("; sel. factor=" + aa.getSelectionFactor(cont-1));
////			}
		}
		return resNewWayCombination;

	}

	private static Algorithm getAlgorithm(EnumAlgorithmNames algorithmName) {
		if (algorithmName.equals(EnumAlgorithmNames.allCombinations))
			return new AllCombinationsAlgorithm();
		if (algorithmName.equals(EnumAlgorithmNames.eachChoice))
			return new EachChoiceAlgorithm();
		if (algorithmName.equals(EnumAlgorithmNames.simplePairwise))
			return new SimpleAllPairsAlgorithm();
		if (algorithmName.equals(EnumAlgorithmNames.prow))
			return new PROWAlgorithm();
		return new SimpleAllPairsAlgorithm();
	}

}
