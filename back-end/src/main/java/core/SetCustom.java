/**
 * Instances of this class represent sets of elements. In combinatorial testing, a Set correspond to a parameter.
 * Each parameter has test values, which correspond to the elements in the Set.
 * @see Element
 * @author Macario Polo Usaola
 */

package core;

import java.util.Vector;

public class SetCustom {
	protected Vector<Element> elements;
	private String name;

	/**
	 * Builds an empty set
	 */
	public SetCustom() {
		super();
		this.elements=new Vector<Element>();
	}

	/**
	 * Builds a set with the elements passed
	 * @param elements The elements to be included in the set
	 */
	public SetCustom(Vector<Element> elements) {
		super();
		this.elements=elements;
	}

	public Vector<Element> getElements() {
		return this.elements;
	}

	public void setElements(Vector<Element> elements) {
		this.elements=elements;
	}

	/**
	 *
	 * @return The size of the set
	 */
	public int size() {
		return this.elements.size();
	}

	public void add(String element) {
		this.elements.add(new Element(element));
	}

	public void add(Element element) {
		this.elements.add(element);
	}

	/**
	 *
	 * @param position The position of the element
	 * @return The element placed at the position
	 */
	public Element getElementAt(int position) {
		return this.elements.get(position);
	}

	public void setElementAt(int index, Element element) {
		this.elements.set(index, element);
	}

	public void setName(String name) {
		this.name=name;
	}

	public String getName() {
		return this.name;
	}
	
	public Element findElement(String name) {
		for (Element e : this.elements)
			if (e.getValue().toString().equals(name))
				return e;
		return null;
	}
	
	public int findElementIndex(String name) {
		String element;
		for (int i=0; i<this.elements.size(); i++) {
			element=this.elements.get(i).getValue().toString();
			if (element.equals(name))
				return i;
		}
		return -1;
	}
	
	public void setSize(int size) {
		this.elements=new Vector<Element>(size);
	}
}
