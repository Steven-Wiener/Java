package P2;
import java.util.Iterator;

///////////////////////////////////////////////////////////////////////////////
//Title:            MessageLoop
//Files:            MessageLoop.java
//Semester:         CS367 Spring 2014
//
//Author:           Steven Wiener
//Email:            SWiener@wisc.edu
//Pair Partner:     Andrew Minneci
//Email:            minneci@wisc.edu
///////////////////////////////////////////////////////////////////////////////

public class MessageLoop implements LoopADT {
	private DblListnode<String> current;
	private int size;
	
	public MessageLoop() {
		current = null;
		size =0;
	}

	public void addBefore(Object item) {
		DblListnode<String> newNode = (DblListnode<String>) item;
		if (current == null) {
			current = newNode;
    		newNode.setNext(current);
    		newNode.setPrev(current);
    	} else {
    		newNode.setNext(current);
    		newNode.setPrev(current.getPrev());
    		current.getPrev().setNext(newNode);
    		current.setPrev(newNode);
    		current = newNode;
    	}
		size ++;
	}

    public void addAfter(Object item) {
    	DblListnode<String> newNode = (DblListnode<String>) item;
    	if (current == null) {
    		current = newNode;
    		newNode.setNext(current);
    		newNode.setPrev(current);
    	} else {	
    		newNode.setNext(current.getNext());
    		newNode.setPrev(current);
        	current.getNext().setPrev(newNode);
        	current.setNext(newNode);
        	current = newNode;
    	}
    	size ++;
	}

    @Override
	public DblListnode<String> getCurrent() {
		if (current == null)
			throw new EmptyLoopException();
		return current;
	}

	@Override
	public DblListnode<String> removeCurrent() {
		if (current == null) {
			throw new EmptyLoopException();
		} else {
			DblListnode<String> remove = current;
			current = current.getNext();
			current.setPrev(remove.getPrev());
			remove.getPrev().setNext(current);
			remove.getNext().setPrev(current);
			
			size--;
			return remove;
		}
	}

	@Override
	public void forward() {
		current = current.getNext();
	}

	@Override
	public void back() {
		current = current.getPrev();
	}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<DblListnode<String>> iterator() {
		MessageLoopIterator itr = new MessageLoopIterator(current);
		return itr;
	}
}
