package core.algorithm;


import core.Algorithm;


public class AllCombinationsAlgorithm extends Algorithm {
	


	@Override
	public void buildCombinations() {
		long numberOfCombinations=super.getMaxNumberOfCombinations();
//		verbose("Maximum number of combinations: " + numberOfCombinations + "<br>");
		for (int i=0; i<numberOfCombinations; i++) {
			this.selectedPositions.add(i);
		}
	}

	@Override
	public String getName() {
		return "All combinations";
	}

	
	@Override
	public boolean requiresRegister() {
		return true;
	}
}
