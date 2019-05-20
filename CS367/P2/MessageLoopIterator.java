package P2;
import java.util.Iterator;

///////////////////////////////////////////////////////////////////////////////
//Title:            MessageLoopIterator
//Files:            MessageLoopIterator.java
//Semester:         CS367 Spring 2014
//
//Author:           Steven Wiener
//Email:            SWiener@wisc.edu
//Pair Partner:     Andrew Minneci
//Email:            minneci@wisc.edu
///////////////////////////////////////////////////////////////////////////////

public class MessageLoopIterator implements Iterator<DblListnode<String>> {
	private DblListnode<String> origNode;
	private DblListnode<String> node;
	private int i;

	public MessageLoopIterator(DblListnode<String> l) {
		node = l;
		origNode = node;
		i = 0;
	}

	@Override
	public boolean hasNext() {
		if (i == 0) {
			i++;
			return true;
		} else if (node != origNode) {
			return true;
		}
		return false;
	}

	@Override
	public DblListnode<String> next() {
		DblListnode<String> past = node;
		node = node.getNext();
		return past;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}