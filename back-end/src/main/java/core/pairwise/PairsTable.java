package core.pairwise;

import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import core.Combination;
import core.Element;
import core.SetCustom;



public class PairsTable {
	protected Vector<Pair> pairs;
	protected int indexA;
	protected int indexB;

	public PairsTable() {
		this.pairs=new Vector<Pair>();
	}

	public PairsTable(SetCustom a, SetCustom b) {
		this();
		for (int i=0; i<a.size(); i++) {
			for (int j=0; j<b.size(); j++) {
				Pair p=new Pair(i, j);
				this.pairs.add(p);
			}
		}
	}

	public Vector<Pair> getPairs() {
		return pairs;
	}

	public void setIndexA(int a) {
		this.indexA=a;
	}

	public void setIndexB(int b) {
		this.indexB=b;
	}

	public Pair getPairWithWeight(int weight) {
		for (Pair p : this.pairs) {
			if (p.weight==weight)
				return p;
		}
		return null;
	}

	public int getIndexA() {
		return indexA;
	}

	public int getIndexB() {
		return indexB;
	}

	public int weightOfThePairs(Combination combination) {
		for (int i=0; i<pairs.size(); i++) {
			Pair auxi=pairs.get(i);
			if (auxi.getA()==combination.getPosition(this.indexA) &&
					auxi.getB()==combination.getPosition(this.indexB))
				return auxi.weight();
		}
		return 0;
	}

	public int getNumberOfPairsVisited(Combination combination) {
		for (int i=0; i<pairs.size(); i++) {
			Pair auxi=pairs.get(i);
			if (auxi.getA()==combination.getPosition(this.indexA) &&
					auxi.getB()==combination.getPosition(this.indexB))
				return 1;
		}
		return 0;	
	}
	

	public Pair getPairVisited(Combination combination) {
		Vector<Pair> result=new Vector<Pair>();
		
		for (int i=0; i<pairs.size(); i++) {
			Pair auxi=pairs.get(i);
			if (auxi.getA()==combination.getPosition(this.indexA) &&
					auxi.getB()==combination.getPosition(this.indexB))
				return auxi;
		}
		return null;	
	}

	public void increaseWeightOfPair(long a, long b) {
		for (int i=0; i<this.pairs.size(); i++) {
			Pair p=this.pairs.get(i);
			if (p.getA()==a && p.getB()==b) {
				p.increaseWeight();
				return;
			}
		}
	}

	public void show() {
		String titulo=this.pairs.size() + " pairs in (" + indexA + ", " + indexB + ")";
		System.out.println(titulo);
		for (int i=0; i<titulo.length(); i++)
			System.out.print("-");
		System.out.println();
		int cont=0;
		for (Pair p : this.pairs) {
			System.out.println(cont++ + ". (" + p.a + ", " + p.b + "), " + p.weight);
		}
		System.out.println();
	}

	public void removePair(long a, long b) {
		for (Pair p : this.pairs) {
			if (p.getA()==a && p.getB()==b) {
				this.pairs.remove(p);
				return;
			}
		}
	}

	public String toHTMLString() {
		String r="<table border='1'><tr><th colspan='2'>" + this.pairs.size() + " pairs in (" + indexA + ", " +
			indexB + ")</th></tr>";
		r+="<tr><th>Elements</th><th># of visits</th></tr>";
		for (Pair p : this.pairs) {
			r=r+"<tr><td>(" + p.a + ", " + p.b + ")</td><td>" + p.weight + "</td></tr>";
		}
		r+="</table>";
		return r;
	}

	public Pair getPair(long a, long b) {
		for (Pair p : this.pairs)
			if (p.a==a && p.b==b)
				return p;
		return null;
	}
	
	public String toRemovableHTMLString(int indexA, int indexB, SetCustom a, SetCustom b) {
		String r="<table border='1'><tr><th colspan='3'>" + this.pairs.size() + " pairs in (" + indexA + ", " +
			indexB + ")</th></tr>";
		r+="<tr><th colspan='2'>Elements</th><th># of visits</th></tr>";
		String name;
		for (Pair p : this.pairs) {
			Element ea=a.getElementAt(p.a);
			Element eb=b.getElementAt(p.b);
			name=indexA + "_" + indexB + "_" + p.a + "_" + p.b;
			r=r+"<tr><td><input type='checkbox' onClick=\"markToRemove('" + name + "')\"></td>";
			r+="<td>(" + ea + ", " + eb + ")</td><td>" + p.weight + "</td></tr>";
		}
		r+="</table>";
		return r;
	}

	public String toRemovableHTMLString(int indexA, int indexB, SetCustom a, SetCustom b, String pairsToRemove) {
		String r="<table border='1'><tr><th colspan='3'>" + this.pairs.size() + " pairs in (" + 
			"<label id='table" + indexA + "_" + indexB + "'>" +
			(a.getName()!=null ? a.getName() : indexA) + ", " +
			(b.getName()!=null ? b.getName() : indexB) + 
			"</label>" + ")</th></tr>";
		r+="<tr><th colspan='2'>Elements</th><th># of visits</th></tr>";
		String name;
		for (Pair p : this.pairs) {
			Element ea=a.getElementAt(p.a);
			Element eb=b.getElementAt(p.b);
			name=indexA + "_" + indexB + "_" + p.a + "_" + p.b;
			r=r+"<tr><td>(" + ea + ", " + eb + ")</td>"; 

			if (pairsToRemove!=null && pairsToRemove.indexOf(name)!=-1) {
				r+="<td><input type='checkbox' checked onClick=\"markToRemove('" + name + "')\"></td>";
			} else
				r+="<td><input type='checkbox' onClick=\"markToRemove('" + name + "')\"></td>";
			r+="</tr>";
		}
		r+="</table>";
		return r;
	}

	public String toDetailedHTMLString(SetCustom a, SetCustom b) {
		String r="<table border='1'><tr><th colspan='2'>" + this.pairs.size() + " pairs in (" + indexA + ", " +
			indexB + ")</th></tr>";
		r+="<tr><th>Elements</th><th># of visits</th></tr>";
		for (Pair p : this.pairs) {
			r=r+"<tr><td>(" + a.getElementAt(p.a) + ", " + b.getElementAt(p.b) + ")</td><td>" + p.weight + "</td></tr>";
		}
		r+="</table>";
		return r;
	}

	public void setSelectionFactor(int a, int b, double desiredFactor) {
		for (Pair p : this.pairs) {
			if (p.getA()==a && p.getB()==b) {
				p.setSelectionFactor(desiredFactor);
				return;
			}
		}
	}

	public String toHTMLPairsTablesWithSelectionFactor(int indexA, int indexB, SetCustom a, SetCustom b, String pairsToRemove, Vector<Vector<String>> selectionFactors) {
		Hashtable<String, String> hPairsToRemove=new Hashtable<String, String>();
		if (pairsToRemove!=null && pairsToRemove.length()>0) {
			java.util.StringTokenizer st=new StringTokenizer(pairsToRemove, "/");
			String token;
			while (st.hasMoreTokens()) {
				token=st.nextToken();
				hPairsToRemove.put(token, token);
			}
		}
		
		String r="<table border='1'><tr><th colspan='3'>" + this.pairs.size() + " pairs in (" + 
			"<label id='table" + indexA + "_" + indexB + "'>" +
			(a.getName()!=null ? a.getName() : indexA) + ", " +
			(b.getName()!=null ? b.getName() : indexB) + 
			"</label>" + ")</th></tr>";
		r+="<tr><th>Elements</th><th>Remove</th><th>Sel. factor</th></tr>";
		String name;
		for (Pair p : this.pairs) {
			Element ea=a.getElementAt(p.a);
			Element eb=b.getElementAt(p.b);
			name=indexA + "_" + indexB + "_" + p.a + "_" + p.b;
			r=r+"<tr><td>(" + ea + ", " + eb + ")</td>";
			if (selectionFactors!=null) {
				double weight=getWeight(indexA, indexB, p.a, p.b, selectionFactors);
				if (weight!=0)
					p.setSelectionFactor(weight);
			}
			if (hPairsToRemove.get(name)!=null) {
				r+="<td><input type='checkbox' checked onClick=\"markToRemove('" + name + "')\"></td>";
			} else
				r+="<td><input type='checkbox' onClick=\"markToRemove('" + name + "')\"></td>";
			r+="<td><input type='text' value='" + p.getSelectionFactor() + "' name='sf_" + name + "' size='2'></td></tr>";
		}
		r+="</table>";
		return r;
	}
	
	public String toHTMLPairsTablesWithSelectionFactor(int indexA, int indexB, SetCustom a, SetCustom b) {
		String r="<table border='1'><tr><th colspan='3'>" + this.pairs.size() + " pairs in (" + indexA + ", " +
			indexB + ")</th></tr>";
		r+="<tr><th>Elements</th><th>Remove</th><th>Sel. factor</th></tr>";
		String name;
		for (Pair p : this.pairs) {
			Element ea=a.getElementAt(p.a);
			Element eb=b.getElementAt(p.b);
			name=indexA + "_" + indexB + "_" + p.a + "_" + p.b;
			r=r+"<tr><td>(" + ea + ", " + eb + ")</td>" +
				"<td><input type='checkbox' onClick=\"markToRemove('" + name + "')\"></td>" +
				"<td><input type='text' value='" + p.getSelectionFactor() + "' name='sf_" + name + "' size='2'></td></tr>";
		}
		r+="</table>";
		return r;
	}

	private double getWeight(int indexA, int indexB, int a, int b, Vector<Vector<String>> selectionFactors) {
		for (Vector<String> sf : selectionFactors) {
			if (sf.get(0).equals(""+indexA) && sf.get(1).equals(""+indexB) && sf.get(2).equals(""+a) && sf.get(3).equals(""+b))
				return Double.parseDouble(sf.get(4));
		}
		return 0;
	}

	private double getWeight(String selectionFactors, int pos) {
		String trozo=selectionFactors.substring(pos);
		String r="";
		int i=0;
		while (trozo.charAt(i)!='/')
			i++;
		trozo=selectionFactors.substring(pos, i-1);
		StringTokenizer st=new StringTokenizer(trozo, "_");
		st.nextToken(); st.nextToken(); st.nextToken(); st.nextToken(); st.nextToken();
		double result=Double.parseDouble(st.nextToken());
		return result;
	}

}
