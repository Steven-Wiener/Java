package P2;
///////////////////////////////////////////////////////////////////////////////
//Title:            DblListnode
//Files:            DblListnode.java
//Semester:         CS367 Spring 2014
//
//Author:           Steven Wiener
//Email:            SWiener@wisc.edu
//Pair Partner:     Andrew Minneci
//Email:            minneci@wisc.edu
///////////////////////////////////////////////////////////////////////////////

import java.util.List;
public class DblListnode<E> {
	private DblListnode<E> prev;
	private DblListnode<E> next;
	private List<E> data;

	public DblListnode() {
		this(null, null, null);
	}
	
	public DblListnode(List<E> d) {
		this(null, d, null);
	}
	
	public DblListnode(DblListnode<E> p, List<E> d, DblListnode<E> n) {
		prev = p;
		next = n;
		data = d;
	}
	
	public List<E> getData() {
		return data;
	}
	
	public DblListnode<E> getNext() {
		return next;
	}

	public DblListnode<E> getPrev() {
		return prev;
	}
	
	public void setData(List<E> obj) {
		data = obj;
	}
	
	public void setPrev(DblListnode<E> pr) {
		prev = pr;
	}
	
	public void setNext(DblListnode<E> nex) {
		next = nex;
	}
}
