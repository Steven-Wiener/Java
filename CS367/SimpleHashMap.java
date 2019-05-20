import java.util.LinkedList;
import java.util.List;

////////////////////////////////////////////////////////////////////////////////
//Main Class File:  LoadBalancerMain.java
//File:             SimpleHashMap.java
//Semester:         CS367 Spring 2014
//
//Author:           Steven Wiener
//Email:            SWiener@wisc.edu
//Pair Partner:     Andrew Minneci
//Email:            minneci@wisc.edu
////////////////////////////80 columns wide ///////////////////////////////////

/**
 * This class implements a generic map based on hash buckets using chained
 * buckets for collision resolution.
 *
 * <p>A map is a data structure that creates a key-value mapping. Keys are
 * unique in the map. That is, there cannot be more than one value associated
 * with a same key. However, two keys can map to a same value.</p>
 *
 * <p>The <tt>SimpleHashMap</tt> class takes two generic parameters, <tt>K</tt>
 * and <tt>V</tt>, standing for the types of keys and values respectively. Items
 * are stored in a hash bucket. Hash values are computed from the
 * <tt>hashCode()</tt> method of the <tt>K</tt> type objects.</p>
 *
 * <p>The chained buckets are implemented using Java's <tt>LinkedList</tt>
 * class.  When a hash bucket is created, its initial bucket size and maximum
 * load factor is set to <tt>11</tt> and <tt>0.75</tt>. The hash bucket can hold
 * arbitrarily many key-value pairs and resizes itself whenever it reaches its
 * maximum load factor.</p>
 *
 * <p><tt>null</tt> values are not allowed in <tt>SimpleHashMap</tt> and a
 * NullPointerException is thrown if used. You can assume that <tt>equals()</tt>
 * and <tt>hashCode()</tt> on <tt>K</tt> are defined, and that, for two
 * non-<tt>null</tt> keys <tt>k1</tt> and <tt>k2</tt>, if <tt>k1.equals(k2)</tt>
 * then <tt>k1.hashCode() == k2.hashCode()</tt>. Do not assume that if the hash
 * codes are the same that the keys are equal since collisions are possible.</p>
 */
public class SimpleHashMap<K, V> {
	/**
	 * A map entry (key-value pair).
	 */
	public static class Entry<K, V> {
		private K key;
		private V value;
		private Entry<K,V> next;

		/**
		 * Constructs the map entry with the specified key and value.
		 */
		public Entry(K k, V v) {
			this.key = k;
			this.value = v;
		}

		/**
		 * Returns the key corresponding to this entry.
		 * @return the key corresponding to this entry
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Returns the value corresponding to this entry.
		 * @return the value corresponding to this entry
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Replaces the value corresponding to this entry with the specified
		 * value.
		 * @param value new value to be stored in this entry
		 * @return old value corresponding to the entry
		 */
		public V setValue(V value) {
			V oldValue = this.value;
			this.value = value;
			return oldValue;
		}
	}

	Entry<K, V>[] bucket;
	int capacity;
	int size = 0;

	/**
	 * Constructs an empty hash map with initial capacity <tt>11</tt> and
	 * maximum load factor <tt>0.75</tt>.
	 **/
	public SimpleHashMap() {
		this.capacity = 11;
		this.bucket = (Entry<K,V>[]) new Entry[capacity];
	}

	/**
	 * Returns the value to which the specified key is mapped, or null if this
	 * map contains no mapping for the key.
	 * @param key the key whose associated value is to be returned
	 * @return the value to which the specified key is mapped, or <tt>null</tt>
	 * if this map contains no mapping for the key
	 * @throws NullPointerException if the specified key is <tt>null</tt>
	 */
	public V get(Object key) {
		if (key == null)
			throw new NullPointerException();

		for (int i = 0; i < size; i++)
			if (bucket[i].getKey().equals(key))
				return bucket[i].getValue();
		return null;
	}

	/**
	 * <p>Associates the specified value with the specified key in this map.
	 * Neither the key nor the value can be <tt>null</tt>. If the map
	 * previously contained a mapping for the key, the old value is replaced.</p>
	 *
	 * <p>If the load factor of the hash bucket after the insertion would exceed
	 * the maximum load factor <tt>0.75</tt>, then the resizing mechanism is
	 * triggered. The size of the bucket should grow at least by a constant
	 * factor in order to ensure the amortized constant complexity, but you
	 * are free to decide the exact value of the new bucket size (e.g. whether
	 * to use a prime or not). After that, all of the mappings are rehashed to
	 * the new bucket.</p>
	 * @param key key with which the specified value is to be associated
	 * @param value value to be associated with the specified key
	 * @return the previous value associated with <tt>key</tt>, or
	 * <tt>null</tt> if there was no mapping for <tt>key</tt>.
	 * @throws NullPointerException if the key or value is <tt>null</tt>
	 */
	public V put(K key, V value) {
		if (key == null || value == null)
			throw new NullPointerException();

		Entry<K,V> entry = locate(hashCode(key), key);

		if (entry != null)
			return entry.setValue(value);

		entry = new Entry<K,V>(key, value);

		if (bucket[hashCode(key)] != null)
			entry.next = bucket[hashCode(key)];

		bucket[size] = entry;
		size++;
		if ((double)size / capacity > .75)
			resize(capacity *= 2);
		return null;
	}

	/**
	 * Removes the mapping for the specified key from this map if present. This
	 * method does nothing if the key is not in the hash bucket.
	 * @param key key whose mapping is to be removed from the map
	 * @return the previous value associated with <tt>key</tt>, or <tt>null</tt>
	 * if there was no mapping for <tt>key</tt>.
	 * @throws NullPointerException if key is <tt>null</tt>
	 */
	public V remove(Object key) {
		if (key == null)
			throw new NullPointerException();
		Entry<K,V> entry = bucket[hashCode((K)key)];

		if (entry != null) {
			if (entry.getKey().equals(key)) {
				bucket[hashCode((K)key)] = entry.next;
				size--;
				return entry.getValue();
			} else {
				while(entry.next != null) {
					if (entry.next.getKey().equals(key)) {
						V value = entry.next.getValue();
						entry.next = entry.next.next;
						size--;
						return value;
					}
					entry = entry.next;
				}
			}
		}
		return null;
	}

	/**
	 * Returns the number of key-value mappings in this map.
	 * @return the number of key-value mappings in this map
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns a list of all the mappings contained in this map. This method
	 * will iterate over the hash bucket and collect all the entries into a new
	 * list. If the map is empty, return an empty list (not <tt>null</tt>).
	 * The order of entries in the list can be arbitrary.
	 * @return a list of mappings in this map
	 */
	public List<Entry<K, V>> entries() {
		List<Entry<K, V>> entries = new LinkedList<Entry<K,V>>();
		for (int i = 0; i < bucket.length; i++)
			if(bucket[i] != null)
				entries.add(bucket[i]);
		return entries;
	}

	/**
	 * Returns the hashcode of a key
	 * @return the hashcode of a key
	 */
	private int hashCode(K key) {
		return Math.abs(key.hashCode() % capacity);
	}

	/**
	 * Locates the entry for a specific key
	 * @return the entry for a specific key
	 */
	private Entry<K,V> locate(int index, K key) {
		Entry<K,V> entry = bucket[index];
		
		while(entry != null) {
			if(entry.getKey().equals(key))
				return entry;
			entry = entry.next;
		}
		return null;
	}

	/**
	 * Resizes the bucket
	 */
	public void resize(int capacity) {
		Entry<K, V>[] prevBucket = bucket;
		bucket = (Entry<K, V>[]) new Entry[capacity];

		for (int i = 0; i < prevBucket.length; i++)
			bucket[i] = prevBucket[i];
	}
}
