package core.oracle;

import java.util.Vector;

public class ParameterValues {
	private int setIndex;
	private String setName;
	private Vector<Long> valuesPositions;
	
	public ParameterValues() {
		this.setIndex=-1;
		this.valuesPositions=new Vector<Long>();
	}
	
	public int getSetIndex() {
		return this.setIndex;
	}

	public Vector<Long> getValuesPositions() {
		return this.valuesPositions;
	}

	public void setSetIndex(int setIndex) {
		this.setIndex = setIndex;
	}
	
	public void addValuePosition(long valuePosition) {
		this.valuesPositions.add(valuePosition);
	}

	public void setSetName(String setName) {
		this.setName=setName;
	}

	public String getSetName() {
		return setName;
	}
}