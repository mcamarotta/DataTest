package core.algorithm;

import core.Algorithm;
import core.Combination;
import core.SetCustom;
import core.pairwise.Pair;
import core.pairwise.PairsTable;


//TODO quitar los verbose

public class SimpleAllPairsAlgorithm extends Algorithm {

	@Override
	public void buildCombinations() {
//		verbose("Building pair tables:");
		this.pairsTables=buildPairTables();
		
		
		this.procesaPreSelectedPositions();

//		verbose("<ol>");
		Combination c=this.getCombination(0);
		c.updateIndex(this.divisors);
//		verbose("<li><b>Adding combination #0 : " + c.toString() + "</b>");
		c.visitPairs(this.pairsTables);
//		verbose("<br>Visiting pairs:");
		
//		verbose("</li>");
		this.selectedPositions.add(0);
		Combination aux=null;
		long selectedPos=0;
//		int iteracion=0;
		while (getPairWithWeight0(pairsTables)!=null) {
			//System.out.println(iteracion++);
			long MAX;
//			verbose("<li>Building new combination");
			Combination selected = initializeNewCombination();
//			verbose("<ol><li>Initial element positions: " + selected.toStringPositions()+"</li>");
			for (int i=0; i<this.sets.size(); i++) {
				//System.out.println("\t" + i);
				if (selected.getPosition(i)==-1) {
					SetCustom set=this.sets.get(i);
					MAX=Long.MIN_VALUE;
					for (int j=0; j<set.size(); j++) {
						//System.out.println("\t\t" + j);
						aux=copy(selected);
						aux.setValuePosition(i, j);
						int pairsVisited=getPairsVisited(aux, i);
						if (pairsVisited>MAX) {
							MAX=pairsVisited;
							selectedPos=j;
						}
					}
					aux.setValuePosition(i, selectedPos);
					selected=copy(aux);
//					verbose("<li>Modified position #" + selectedPos + ": " + selected.toStringPositions()+"</li>");
				}
			}
//			verbose("</ol>");
			selected.updateIndex(this.divisors);
//			verbose("<li>" + selected.toStringPositions() + " corresponds to combination #" + selected.getIndex() +
//					" (" + this.getCombination(selected.getIndex()).toString() + ").");
//			verbose(" <b>" + this.getCombination(selected.getIndex()).toString() + " added.</b><br>");
//			verbose("Visiting pairs:");
			selected.visitPairs(this.pairsTables);
//			System.out.println(selected.toStringPositions());
			
//			verbose("</li>");
			this.selectedPositions.add(selected.getIndex());
		}
//		verbose("</ol>");
	}

	private void procesaPreSelectedPositions() {
		for (long position : this.preSelectedPositions) {
			Combination c=this.getCombination(position);
			c.updateIndex(this.divisors);
			c.visitPairs(this.pairsTables);			
			this.selectedPositions.add(c.getIndex());
		}
	}
	
	private Combination initializeNewCombination() {
		Combination selected=new Combination(this.sets.size());
		for (int i=0; i<this.sets.size(); i++)
			selected.setValuePosition(i, -1);
		long MAX=Long.MIN_VALUE;
		int selectedSetIndex=0;
		int selectedElementIndex=0;
		for (int i=0; i<this.sets.size(); i++) {
			SetCustom set=this.sets.get(i);
			for (int j=0; j<set.size(); j++) {
				int pairsVisited=getPairsVisited(i, j);
				if (pairsVisited>MAX) {
					selectedSetIndex=i;
					selectedElementIndex=j;
					MAX=pairsVisited;
				}
			}
		}
		selected.setValuePosition(selectedSetIndex, selectedElementIndex);
		return selected;
	}

	private Combination copy(Combination selected) {
		Combination result=new Combination(selected.size());
		for (int i=0; i<selected.size(); i++) {
			result.setValuePosition(i, selected.getPosition(i));
		}
		return result;
	}

	private Pair getPairWithWeight0(PairsTable[] pptt) {
		for (int i=0; i<pptt.length; i++) {
			Pair p=pptt[i].getPairWithWeight(0);
			if (p!=null) {
				return p;
			}
		}
		return null;
	}


	@Override
	public String getName() {
		return "aetg";
	}
	
	@Override
	public boolean requiresRegister() {
		return true;
	}



}