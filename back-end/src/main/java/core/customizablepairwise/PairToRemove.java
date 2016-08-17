package core.customizablepairwise;

public class PairToRemove {
	private long indexA;
	private long indexB;
	private long a;
	private long b;

	public PairToRemove(long indexA, long indexB, long a, long b) {
		this.indexA=indexA;
		this.indexB=indexB;
		this.a=a;
		this.b=b;
	}

	public long getIndexA() {
		return indexA;
	}

	public long getIndexB() {
		return indexB;
	}

	public long getA() {
		return a;
	}

	public long getB() {
		return b;
	}

	public boolean equals(Object o) {
		return (o.toString().equals(this.toString()));
	}

	@Override
	public String toString() {
		return indexA + "_" + this.indexB + "_" + a + "_" + b;
	}
}
