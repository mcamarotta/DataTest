package core;

import java.util.Iterator;
import java.util.Vector;

public class OrderedVector implements java.lang.Iterable<Long> {
	protected Vector<Long> elementos;

	public OrderedVector() {
		this.elementos=new Vector<Long>();
	}

	public void add(long n) {
		if (this.contains(n, 0, this.elementos.size()-1))
			return;
		if (this.elementos.size()==0) {
			this.elementos.add(n);
			return;
		}
		if (this.elementos.lastElement()<n) {
			this.elementos.add(n);
			return;
		}
		int pos=this.findPos(n);
		this.elementos.insertElementAt(n, pos);
	}

	public boolean contains(long n) {
		return this.contains(n, 0, this.elementos.size()-1);
	}

	protected boolean contains(long n, int a, int b) {
		if (a>b)
			return false;
		int mitad=(a+b)/2;
		if (this.elementos.get(mitad)==n)
			return true;
		if (this.elementos.get(mitad)>n)
			return this.contains(n, a, mitad-1);
		else return this.contains(n, mitad+1, b);
	}

	@Override
	public synchronized String toString() {
		String r="";
		for (int i=0; i<this.elementos.size(); i++)
			r+=this.elementos.get(i)+"-";
		return r;
	}

	public void remove(int index) {
		this.elementos.remove(index);
	}

	public void clear() {
		this.elementos.clear();
	}

	public int findPos(long n) {
		int a=0, b=this.elementos.size()-1, mitad, pos=-1;
		if (this.elementos.size()==0) {
			pos=0;
		} else if (this.elementos.get(b)<n) {
			pos=b+1;
		}
		while (a<=b) {
			mitad=(a+b)/2;
			long elemento=this.elementos.get(mitad);
			if (elemento==n)
				pos=mitad;
			else if (elemento<n)
				a=mitad+1;
			else
				b=mitad-1;
		}
		pos=a;
		return pos;
	}

	public int size() {
		return this.elementos.size();
	}


	public Iterator<Long> iterator() {
		return this.elementos.iterator();
	}

	public long get(int index) {
		return this.elementos.get(index);
	}

	public long get(long index) {
		return this.elementos.get((int) index);
	}

	public void removeRepeated() {
		OrderedVector copia=new OrderedVector();
		for (int i=0; i<this.elementos.size(); i++) {
			copia.add(this.elementos.get(i));
		}
		this.elementos=copia.elementos;
	}
}
