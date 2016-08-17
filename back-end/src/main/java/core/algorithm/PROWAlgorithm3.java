package core.algorithm;

import java.util.StringTokenizer;
import java.util.Vector;

import core.Algorithm;
import core.Combination;
import core.Element;
import core.SetCustom;
import core.customizablepairwise.PairToRemove;
import core.pairwise.Pair;
import core.pairwise.PairsTable;

public class PROWAlgorithm3 extends Algorithm {
	private Vector<Vector<Integer>> visitedPairs=null;
	private Vector<Long> prowCombinations=new Vector<Long>();
	private Vector<Double> selectionFactors=new Vector<Double>();
	//CAMBIO
	private int initIndex = -1;
	private int initElement = -1;
	private int TotalVisitas = 0;
	private int maxPairsVisited = 0;

	public PROWAlgorithm3() {
		super();
	}

	@Override
	public PairsTable[] buildPairTables() {
		this.pairsTables=super.buildPairTables();
		return this.pairsTables;
	}

	public void buildVisitedPairs() {
		this.visitedPairs=new Vector<Vector<Integer>>();
		for (SetCustom s : this.sets) {
			Vector<Integer> pv=new Vector<Integer>();
			for (Element e : s.getElements()) {
				pv.add(0);
			}
			this.visitedPairs.add(pv);
		}
	}

	private int updateVisitedPairs(Combination c) {
		int result=0;
		for (int i=0; i<c.size(); i++) {
			for (int j=i+1; j<c.size(); j++) {
				PairsTable pt=this.findPairsTable(i, j);
				Pair p=pt.getPair(c.getPosition(i), c.getPosition(j));
				if (p.weight()==0) {
					Vector<Integer> vpA=this.visitedPairs.get(i);
					Vector<Integer> vpB=this.visitedPairs.get(j);
					int visitsA=vpA.get(p.getA());
					int visitsB=vpB.get(p.getB());
					vpA.setElementAt(visitsA-1, p.getA());
					vpB.setElementAt(visitsB-1, p.getB());
					result+=visitsA+visitsB;
				}
			}
		}
		c.visitPairs(this.pairsTables);
		return result;
	}

	public void removePair(long indexA, long indexB, long a, long b) {
		if (this.pairsTables==null)
			this.buildPairTables();
		PairsTable pt=this.findPairsTable(indexA, indexB);
		pt.removePair(a, b);
	}

	private void findBestSetAndIndex(int[] si) {
		int max=Integer.MIN_VALUE;
		for (int i=0; i<this.visitedPairs.size(); i++) {
			Vector<Integer> vp=this.visitedPairs.get(i);
			for (int j=0; j<vp.size(); j++) {
				if (vp.get(j)>max) {
					si[0]=i;
					si[1]=j;
					max=vp.get(j);
				}
			}
		}
	}

	private Combination complete(Combination c) { 
		//Completa un caso de prueba
		Vector <Combination> combs = new Vector <Combination>();
		Vector <Combination> combsAux = new Vector <Combination>();
		Vector <Combination> combsAux2 = new Vector <Combination>();
		Vector <Combination> combsTotal = new Vector <Combination>();
		combs.add(c);
		combsAux.add(c);
		TotalVisitas = 0;
		int TotalVisitasLocal = 0;
		Boolean seguir = true;
		for (int i=0; i<this.sets.size(); i++) {
			combsTotal.clear();
			int size = combs.size();
			for (int j=0; j<size && seguir; j++){
				if (combs.get(j).getPosition(i)==-1) {
					combsAux2.add(combs.get(j));
					combsAux=selectElementVisitingMoreCompatiblePairs(i, combsAux2);
					if (TotalVisitas > TotalVisitasLocal){
						combsTotal.clear();
						TotalVisitasLocal = TotalVisitas;
					}
					for (int iter=0; iter<combsAux.size(); iter++){
						Combination aux = combsAux.get(iter).copy();
						combsTotal.add(aux);
					}
					combsAux.clear();
					combsAux2.clear();
					//if (selectedElementPosition!=-1) {
					//	c.setValuePosition(i, selectedElementPosition);
					//}
				}
			}
			if (i==(this.sets.size()-1) && (TotalVisitas==maxPairsVisited)){
				seguir = false;
			}
			if (combsTotal.size()>0){
				combs = copyVectorCombs(combsTotal);
			}
			
		}
		c = combs.get(0);
		for (int i=0; i<c.size(); i++)
			if (c.getPosition(i)==-1)
				return null;
		return c;
	}

	private int min(int a, int b) {
		return a<b ? a : b;
	}

	private int max(int a, int b) {
		return a>b ? a : b;
	}

	private Vector<Integer> getPosicionesRellenas(Combination c) {
		Vector<Integer> result=new Vector<Integer>();
		for (int i=0; i<c.size(); i++)
			if (c.getPosition(i)!=-1)
				result.add(i);
		return result;
	}

	private boolean validCombination (int setIndex, int i, Combination c){
		Combination auxi=c.copy(); //auxi tiene una copia de la combinacion
		auxi.setValuePosition(setIndex, i); //asigno en la posicion setIndex al parametro i
		boolean cont=true;
		boolean reached =false;

		for (int j=0; j<auxi.size() && cont; j++) { 
			if (auxi.getPosition(j)==-1) { // Recorro las posiciones no rellenas
				SetCustom set=this.sets.get(j); //set guarda los valores del parametro j

				for (int n=0; n<set.size() && !reached; n++) { //por cada valor del parametro j
					boolean cont2 = true;

					for (int m=0;(m<auxi.size() && cont2); m++){ //busco un par con cada una de los parametros ya asignados
						if (auxi.getPosition(m)!=-1) { 
							PairsTable pares=this.findPairsTable(min(m, j),max(m,j));
							long a1=m>j ? n : auxi.getPosition(m);
							long b1=m<j ? n : auxi.getPosition(m);
							if (pares.getPair(a1,b1)==null)
								cont2=false;

						}
					}
					if (cont2)
						reached = true;
				}
				if (!reached)
					cont=false;
				reached=false;  
			}
		}
		return cont;
	}

	private Vector <Combination>  selectElementVisitingMoreCompatiblePairs(int setIndex, Vector <Combination> combs) {
		//Busca el elemento del parametro setIndex que visita mas pares no visitados aun en el caso de prueba c
//		int result=-1;
		int max=Integer.MIN_VALUE;
		int MAXIMO_VISITAS=Integer.MIN_VALUE;
		int MAXIMO_PESO=Integer.MIN_VALUE;
		Pair p=null;
		Vector<Integer> vp=this.visitedPairs.get(setIndex); 
		//El vector vp devuelve un array con la cantidad de veces que aparece cada valor del paramtero setIndex 
		//en los pares aun no visitados
		
		Vector <Combination> combsAux = new Vector <Combination>(); //Nueva combinacion a devolver
		
		for (int ci=0; ci<combs.size(); ci++){
			Combination c = combs.get(ci);
			Combination caux = c.copy();
			for (int i=0; i<vp.size(); i++) { //i toma los valores de setIndex
				Vector<Integer> posicionesRellenas=getPosicionesRellenas(c); //posicionesRellenas es un array con los indices de los parametros ya asignados
				int visitas=0;
				int peso=0;
	
				for (int k=0; k<posicionesRellenas.size(); k++) { //por cada parametro ya asignado
					int posDelOtroCjto=posicionesRellenas.get(k); //tomo el indice del parametro ya asignado
					PairsTable pt=this.findPairsTable(min(setIndex, posDelOtroCjto), max(setIndex, posDelOtroCjto)); 
					//busco todos los pares entre el parametro ya asignado (posDelOtroCjto) y el que quiero asignar que es setIndex
					long a=setIndex<posDelOtroCjto ? i : c.getPosition(posDelOtroCjto);
					long b=setIndex>posDelOtroCjto ? i : c.getPosition(posDelOtroCjto);
					p=pt.getPair(a, b); //tomo el par entre el valor asignado para el parametro relleno e i 
	
					if (p!=null){
						if (!this.validCombination(setIndex, i, c))
							p=null;
					}
	
					if (p!=null && p.weight()==0) {// Si el par no ha sido aún visitado
						visitas++;
						peso+=p.getSelectionFactor(); 
					} else if (p!=null) {
						peso+=p.getSelectionFactor();
					}
				}
				if (p!=null && (vp.get(i)>max)) {
					caux = c.copy();
					if (valueIsCompatible(caux, setIndex, i)) {
//						result=i;
						max=vp.get(i);
						MAXIMO_VISITAS=visitas;
						MAXIMO_PESO=peso; 
						combsAux.clear();
						caux.setValuePosition(setIndex,i);
						combsAux.add(caux);
						caux = c.copy();
						TotalVisitas = visitas; //Var Global
						
					}
				} else if (p!=null && (vp.get(i)==max)) {
					// Tengo que coger el que visita más pares no
					// visitados en las tablas que no tengan -1 en esta combinación (*, setIndex)
					
					if (visitas>MAXIMO_VISITAS && valueIsCompatible(c, setIndex, i)) {
						MAXIMO_VISITAS=visitas;
						MAXIMO_PESO=peso;
//						result=i;
						combsAux = new Vector<Combination>();
						caux.setValuePosition(setIndex,i);
						combsAux.add(caux);
						caux = c.copy();
						TotalVisitas = visitas; //Var Global
						
						
					} else if (visitas==MAXIMO_VISITAS && valueIsCompatible(c, setIndex, i)) {
						if (peso>=MAXIMO_PESO) {//ACA ANTES ERA SOLO MAYOR
							MAXIMO_VISITAS=visitas;
							MAXIMO_PESO = peso;
//							result=i; //FALTABA
							caux.setValuePosition(setIndex,i);
							combsAux.add(caux);
							caux=c.copy();
							
						}
					}
				}
			}

		}
		return combsAux;
	}

	private boolean valueIsCompatible(Combination c, int setIndex, int elementPosition) {
		for (int i=0; i<c.size(); i++) {
			if (c.getPosition(i)!=-1) {
				int setIndexA, setIndexB;
				if (i<setIndex) {
					setIndexA=i;
					setIndexB=setIndex;
					PairsTable pt=this.findPairsTable(setIndexA, setIndexB);
					if (pt.getPair(c.getPosition(i), elementPosition)==null)
						return false;
				} else {
					setIndexA=setIndex;
					setIndexB=i;
					PairsTable pt=this.findPairsTable(setIndexA, setIndexB);
					if (pt.getPair(elementPosition, c.getPosition(i))==null)
						return false;
				}
			}
		}
		return true;
	}
	

	
	public void initPairTable (){
		for (int i=0; i<this.pairsTables.length; i++) {
			PairsTable pt=this.pairsTables[i];
			int setA=pt.getIndexA();
			int setB=pt.getIndexB();
			Vector<Integer> vpA=this.visitedPairs.get(setA);
			Vector<Integer> vpB=this.visitedPairs.get(setB);
			for (int j=0; j<pt.getPairs().size(); j++) {
				Pair p=pt.getPairs().get(j);
				int elementA=p.getA();
				int elementB=p.getB();
				int visitsA=vpA.get(elementA);
				int visitsB=vpB.get(elementB);
				vpA.setElementAt(visitsA+1, elementA);
				vpB.setElementAt(visitsB+1, elementB);
			}
		}
	}

	@Override
	public void buildCombinations() {
		this.initPairTable();
		maxPairsVisited = this.sets.size()*(this.sets.size()-1)/2; //Variable global
		int visits=0;
//		Pair p;
		Combination c=null;
		this.initializeIndex();
		//Para la primer combinaci—n
		c=this.combinationZero();
		if (c!=null) {
			c.updateIndex(divisors);
			visits=this.updateVisitedPairs(c);
			if (visits>0) {
				this.selectedPositions.add(c.getIndex());
				System.out.println(c.toStringPositions());
				System.out.println("\t"+this.visitedPairs.toString());
			}
		} 
		buildNextCombinations();
		saveCombinations();
		//orderSelectedCombinations();
		
	}
	
	public void buildNextCombinations(){
		int visits=0;
		Combination c = null;
//		Pair p;
		do {
			visits = 0;
			c=this.initializeNewCombination();
			c=complete(c);
			visits = this.addCombination(c); //Nueva funcion
			if (c==null | visits == 0){
				c=null;
				for (PairsTable pt2 : this.pairsTables){
					for (Pair  par : pt2.getPairs()) {
						if (par!=null){
							if (par.weight()==0) {
								c=this.resetCombination();
								c.setValuePosition(pt2.getIndexA(), par.getA());
								c.setValuePosition(pt2.getIndexB(), par.getB());
								c=complete(c);
								if (c!=null) {
									c.updateIndex(divisors);
									this.updateVisitedPairs(c);
									this.selectedPositions.add(c.getIndex());
									break;
								}
							}
						}
					}
				}
			}
		} while ((getPairWithWeight0(pairsTables))!=null);// && c!=null && visits>0);
	}
	
	public int addCombination (Combination c){
		int visits = 0;
		if (c!=null) {
			c.updateIndex(divisors);
			visits=this.updateVisitedPairs(c);
			if (visits>0) {
				this.selectedPositions.add(c.getIndex());
				System.out.println(c.toStringPositions());
				System.out.println("\t"+this.visitedPairs.toString());
			}
		} 
	   return visits;
	}

//	private void orderSelectedCombinations() {
//		for (long sp : this.selectedPositions) {
//			Combination c=this.getCombination(sp);
//			if (prowCombinations.size()==0) {
//				prowCombinations.add(sp);
//				this.selectionFactors.add(c.getSumOfSelectionFactors(this.pairsTables));
//			} else {
//				double peso=c.getSumOfSelectionFactors(this.pairsTables);
//				int i=0;
//				while (i<this.selectionFactors.size() && this.selectionFactors.get(i)>peso) {
//					i++;
//				}
//				if (i==this.selectionFactors.size()) {
//					prowCombinations.add(sp);
//					this.selectionFactors.add(peso);
//				} else {
//					prowCombinations.insertElementAt(sp, i);
//					this.selectionFactors.insertElementAt(peso, i);
//				}
//			}
//		}
//	}
	
	private void saveCombinations() {
		for (long sp : this.selectedPositions) {
			Combination c=this.getCombination(sp);
			prowCombinations.add(sp);
			this.selectionFactors.add(c.getSumOfSelectionFactors(this.pairsTables));
			
		}
	}
	
	public double getSelectionFactor(int index) {
		return this.selectionFactors.get(index);
	}
	
	public Vector<Long> getprowCombinations(){
		return (this.prowCombinations);
	}
	

	@Override
	public String toString() {
		String result="Values = {\n";
		int cont=0;
		for (long sp : this.prowCombinations) {
			Combination combination=this.getCombination(sp);
			result+=cont + "-> " + combination.toString() + "\t" +
				this.selectionFactors.get(cont) + "\n";
			cont++;
		}
		result+="}\n";
		return result;
	}

	@Override
	public java.util.Vector<Combination> getSelectedCombinations() {
		Vector<Combination> result=new Vector<Combination>();
		for (Long i : this.prowCombinations) {
			result.add(this.getCombination(i));
		}
		return result;
	}
	
	private void initializeIndex() { //CAMBIADOOOOOO
		int MAX=Integer.MIN_VALUE;
		int selectedSetIndex=0;
		//Busco el conjunto con mas valores
		for (int i=0; i<this.sets.size(); i++) {
			int values =this.sets.get(i).size();
			if (values>MAX){
				selectedSetIndex=i;
				MAX=values;
			}
		}
		this.initIndex = selectedSetIndex;
		this.initElement = 0;
	}

	private Combination initializeNewCombination() { //CAMBIADOOOOOO
		Combination selected=new Combination(this.sets.size());
		for (int i=0; i<this.sets.size(); i++)
			selected.setValuePosition(i, -1);
		if (this.sets.size()%3 == 0){
			int MAX=Integer.MIN_VALUE;
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
		}else{
			//Recorro el conjunto con mas valores y asigno uno a uno todos sus valores
//			SetCustom set=this.sets.get(this.initIndex);
			int pairsVisited=getPairsVisited(this.initIndex, this.initElement);
			if (pairsVisited==0) {
				this.initElement = this.initElement+1;
				if (this.initElement==this.sets.get(this.initIndex).size()){
					int bestSetAndIndex[]={-1, -1};
					findBestSetAndIndex(bestSetAndIndex);
					this.initIndex = bestSetAndIndex[0];
					this.initElement = bestSetAndIndex[1];
				}
			
			}
			selected.setValuePosition(this.initIndex, this.initElement);
		}//else
		
		return selected;
	}
	

	
	private Combination combinationZero() { //NUEVO
		Combination selected=new Combination(this.sets.size());
		for (int i=0; i<this.sets.size(); i++)
			selected.setValuePosition(i, 0);
		return selected;
	}
	
	private Combination resetCombination() {
		Combination selected=new Combination(this.sets.size());
		for (int i=0; i<this.sets.size(); i++)
			selected.setValuePosition(i, -1);
		
		return selected;
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
		return "PROW";
	}
	
	public String showDetailedRemovableHTMLPairsTables(String pairsToRemove) {

		String r="<table><tr>";

		int i=0;

		for (i=0; i<this.pairsTables.length; i++) {

			r+="<td>";

			PairsTable pt=this.pairsTables[i];

			int indexA=pt.getIndexA();

			int indexB=pt.getIndexB();

			r+=pt.toRemovableHTMLString(indexA, indexB, this.sets.get(indexA), this.sets.get(indexB), pairsToRemove);

			r+="</td>";

			if ((i+1)%3==0) {

				r+="</tr>";

			}

		}

		r+="</td></tr></table>";

		return r;

	}



	public String showDetailedRemovableHTMLPairsTablesWithSelectionFactor(String sPairsToRemove, Vector<Vector<String>> selectionFactors) {

			String r="<table><tr>";

		int i=0;

		for (i=0; i<this.pairsTables.length; i++) {

			r+="<td>";

			PairsTable pt=this.pairsTables[i];

			int indexA=pt.getIndexA();

			int indexB=pt.getIndexB();

			r+=pt.toHTMLPairsTablesWithSelectionFactor(indexA, indexB, this.sets.get(indexA), this.sets.get(indexB), sPairsToRemove, selectionFactors);

			r+="</td>";

			if ((i+1)%3==0) {

				r+="</tr>";

			}

		}

		r+="</td></tr></table>";

		return r;

	}

	public String showDetailedRemovableHTMLPairsTables() {
		String r="<table><tr>";
		int i=0;
		for (i=0; i<this.pairsTables.length; i++) {
			r+="<td>";
			PairsTable pt=this.pairsTables[i];
			int indexA=pt.getIndexA();
			int indexB=pt.getIndexB();
			r+=pt.toRemovableHTMLString(indexA, indexB, this.sets.get(indexA), this.sets.get(indexB));
			r+="</td>";
			if ((i+1)%3==0) {
				r+="</tr>";
			}
		}
		r+="</td></tr></table>";
		return r;
	}

	public String showDetailedRemovableHTMLPairsTablesWithSelectionFactor() {
		String r="<table><tr>";
		int i=0;
		for (i=0; i<this.pairsTables.length; i++) {
			r+="<td>";
			PairsTable pt=this.pairsTables[i];
			int indexA=pt.getIndexA();
			int indexB=pt.getIndexB();
			r+=pt.toHTMLPairsTablesWithSelectionFactor(indexA, indexB, this.sets.get(indexA), this.sets.get(indexB));
			r+="</td>";
			if ((i+1)%3==0) {
				r+="</tr>";
			}
		}
		r+="</td></tr></table>";
		return r;
	}

	public void removePairs(String sPairsToRemove) {
		StringTokenizer st=new StringTokenizer(sPairsToRemove, "/");
		Vector<PairToRemove> pairsToRemove=new Vector<PairToRemove>();
		while (st.hasMoreTokens()) {
			String par=st.nextToken();
			StringTokenizer stPar=new StringTokenizer(par, "_");
			while (stPar.hasMoreTokens()) {
				long indexA=Integer.parseInt(stPar.nextToken());
				long indexB=Integer.parseInt(stPar.nextToken());
				long a=Integer.parseInt(stPar.nextToken());
				long b=Integer.parseInt(stPar.nextToken());
				pairsToRemove.add(new PairToRemove(indexA, indexB, a, b));
			}
		}
		for (PairToRemove p : pairsToRemove) {
			this.removePair(p.getIndexA(), p.getIndexB(), p.getA(), p.getB());
		}
	}

	public void setSelectionFactors(Vector<Vector<String>> pairsWithSelectionFactor) {
		for (Vector<String> pair : pairsWithSelectionFactor) {
			int indexA=Integer.parseInt(pair.get(0));
			int indexB=Integer.parseInt(pair.get(1));
			int a=Integer.parseInt(pair.get(2));
			int b=Integer.parseInt(pair.get(3));
			double factor=Double.parseDouble(pair.get(4));
			this.setSelectionFactor(indexA, indexB, a, b, factor);
		}
	}

	public void setSelectionFactor(int indexA, int indexB, int a, int b, double desiredFactor) {
		if (this.pairsTables==null)
			this.buildPairTables();
		PairsTable pt=this.findPairsTable(indexA, indexB);
		pt.setSelectionFactor(a, b, desiredFactor);
	}
	
	public Vector <Combination> copyVectorCombs (Vector <Combination> comb) {
		Vector <Combination> combaux = new Vector<Combination>();

		for (int j=0; j<comb.size(); j++){
			Combination aux = comb.get(j).copy();
			combaux.add(aux);
		}
		return combaux;
	}
	
	@Override
	public boolean requiresRegister() {
		return true;
	}

}
