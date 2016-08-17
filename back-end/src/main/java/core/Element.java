package core;

public final class Element {
	protected String value;

	public Element(String value) {
		this.value=value;
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(Object value) {
		this.value=value.toString();
	}

	public String toString() {
		return this.value;
	}

	public boolean equals(Object o) {
		if (!(o instanceof Element))
			return false;
		Element v=(Element) o;
		return this.value.equals(v.value);
	}
}
