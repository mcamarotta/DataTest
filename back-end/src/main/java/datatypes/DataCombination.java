package datatypes;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Michel
 *	This class represent a combination calculate from some algorithm
 */
public class DataCombination {
	
	ArrayList<String> combination;

	
	public DataCombination(ArrayList<String> combination) {
		this.combination = combination;
	}
	
	public DataCombination() {
		
	}

	public ArrayList<String> getCombination() {
		return combination;
	}

	public void setCombination(ArrayList<String> combination) {
		this.combination = combination;
	}
	
	public void addValueToCombination(String value){
		if(this.combination ==null)
			this.combination=new ArrayList<String>();
		this.combination.add(value);
	}

	@Override
	public boolean equals(Object obj) {
		DataCombination otherObj;
		if(obj instanceof DataCombination)
			otherObj= (DataCombination)obj;
		else
			return false;
		
		if(this.combination ==null || otherObj.combination ==null 
			|| this.combination.size()!=otherObj.combination.size())
			return false;
		
		return Arrays.equals(this.combination.toArray(),otherObj.combination.toArray());
	}

	@Override
	public String toString() {
		
		String res = "DataCombination [ ";
		for (String string : combination) {
			res=res+string+", ";
		}
		res=res+"]";
		return res;
	}
	
	
	
	

}
