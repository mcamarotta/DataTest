package datatypes;

import java.util.ArrayList;

/**
 * @author Michel Camarotta
 * This class represents the set of variable and its different values. A Set of this is the first entry to calculate the combination
 * pair of variables. 
 */
public class DataVariableAndValues {
	
	String variableName;
	ArrayList<String> values;
	
	
	public DataVariableAndValues(){
		
	}
	
	
	public DataVariableAndValues(String variableName, ArrayList<String> values) {
		
		this.variableName = variableName;
		this.values = values;
	}



	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public ArrayList<String> getValues() {
		return values;
	}

	public void setValues(ArrayList<String> values) {
		this.values = values;
	}

	
}
