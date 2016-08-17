package ctweb;

import java.util.ArrayList;

import java.util.Vector;

import core.Element;
import core.SetCustom;
import datatypes.DataVariableAndValues;

public final class ParametersLoader {	
	
	/**
	 * 
	 * This method load the sets of Variable and its values from a Set of DataVariableAndValues
	 * @param request
	 * @return
	 */
	
	public static Vector<SetCustom> loadSets(ArrayList<DataVariableAndValues> setOfVariableAndValues) {

		Vector<SetCustom> sets=new Vector<SetCustom>(setOfVariableAndValues.size()); 
		
		//Create Sets
	    for (DataVariableAndValues dataVariableAndValues : setOfVariableAndValues) {
	    	SetCustom set=new SetCustom();
	    	set.setName(dataVariableAndValues.getVariableName());
	    	ArrayList<String> values =dataVariableAndValues.getValues();
	    	
	    	//This is legacy code. this is need to inicialize the set
	    	for (int i=0; i<values.size();i++)
				set.add(new Element(""));
	    	
	    	//This part add all values of one variable to a SetCustom. row is the row from the FrontEnd. Is needed, i supposed, from 
	    	//the algorithm. But i don't know because is legacy code :)
	      	for (int row = 0; row < values.size(); row++) {
				String value = values.get(row);
				set.setElementAt(row, new Element(value));	
	    	}
	    	sets.add(set);	    
	    }
		return sets;
	}
	
}
