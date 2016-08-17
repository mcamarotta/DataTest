package core.algorithm;

import java.util.Random;
import java.util.Vector;

import core.Algorithm;
import core.Combination;
import core.SetCustom;


public class EachChoiceAlgorithm extends Algorithm {

	@Override
	public void buildCombinations() {
//		verbose("Marking unvisited test values<br>");
		Vector<Vector<Boolean>> visitados=new Vector<Vector<Boolean>>();
		for (SetCustom s : this.sets) {
			Vector<Boolean> vb=new Vector<Boolean>();
			for (int i=0; i<s.size(); i++)
				vb.add(false);
			visitados.add(vb);
			//randomize(s);
		}
		
		procesaPreSelectedPositions(visitados);
		
		
//		verbose("Generating test data:<br><ol>");
		Random rnd=new Random();
		while (thereAreUnvisitedElements(visitados)) {
			Combination c=new Combination(this.sets.size());
//			verbose("<li>Generating new combination</li>");
			for (int i=0; i<this.sets.size(); i++) {
				SetCustom s=this.sets.get(i);
				int selected=-1;
				Vector<Boolean> vb=visitados.get(i);
				for (int j=0; j<vb.size() && selected==-1; j++) {
					if (!vb.get(j)) {
						selected=j;
						vb.set(j, true);
					}
				}
				if (selected==-1)
					selected=rnd.nextInt(vb.size());
				c.setValuePosition(i, selected);
//				verbose("<li>Selected " + s.getElementAt(selected).toString() + "</li>");
			}
//			verbose("</ol></li>");
			c.updateIndex(this.divisors);
			this.selectedPositions.add(c.getIndex());
		}
//		verbose("</ol>");
	}

	private void procesaPreSelectedPositions(Vector<Vector<Boolean>> visitados) {
		for (long position : this.preSelectedPositions) {
			Combination c=this.getCombination(position);
			for (int i=0; i<c.size(); i++) {
				Vector<Boolean> vb=visitados.get(i);
				vb.set((int) c.getPosition(i), true);
//				System.out.println("Se ha marcado " + c.getPosition(i) + " del conjunto " + i + " como visitado de la combi. " + c.getIndex());
			}			
			c.updateIndex(this.divisors);
			this.selectedPositions.add(c.getIndex());
		}
	}

	private boolean thereAreUnvisitedElements(Vector<Vector<Boolean>> visitados) {
		for (Vector<Boolean> vb : visitados)
			for (boolean b : vb)
				if (!b)
					return true;
		return false;
	}

	@Override
	public String getName() {
		return "each choice";
	}


	
	@Override
	public boolean requiresRegister() {
		return false;
	}
}