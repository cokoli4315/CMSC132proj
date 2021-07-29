import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * The MyHashSet API is similar to the Java Set interface. This collection is
 * backed by a hash table.
 */
public class MyHashSet<E> implements Iterable<E> {

	/**
	 * Unless otherwise specified, the table will start as an array (ArrayList)
	 * of this size.
	 */
	private final static int DEFAULT_INITIAL_CAPACITY = 4;

	/**
	 * When the ratio of size/capacity exceeds this value, the table will be
	 * expanded.
	 */
	private final static double MAX_LOAD_FACTOR = 0.75;

	/* STUDENTS: LEAVE THIS AS PUBLIC */
	public ArrayList<Node<E>> hashTable;

	private int size; // number of elements in the table

	// STUDENTS: DO NOT ADD ANY OTHER INSTANCE VARIABLES OR STATIC
	// VARIABLES TO THIS CLASS!

	/* STUDENTS: LEAVE THIS PUBLIC, and do not modify the Node class. */
	public static class Node<T> {
		private T data;
		public Node<T> next; // STUDENTS: Leave this public, too!

		private Node(T data) {
			this.data = data;
			next = null;
		}
	}

	/**
	 * Initializes an empty table with the specified capacity.
	 *
	 * @param initialCapacity initial capacity (length) of the underlying table
	 */
	// STUDENTS: Calling the ArrayList constructor that takes
	// an int argument doesn't do what we want here.
	// You need to make an empty ArrayList, and then add a bunch
	// of null values to it until the size reaches the
	// initialCapacity requested.
	public MyHashSet(int initialCapacity) {
		hashTable = new ArrayList<>();

		for (int index = 0; index < initialCapacity; index++) {
			hashTable.add(null);
		}
	}

	/**
	 * Initializes an empty table of length equal to DEFAULT_INITIAL_CAPACITY
	 */
	// STUDENTS: This constructor should just call the other
	// constructor
	public MyHashSet() {
		this(DEFAULT_INITIAL_CAPACITY);
	}

	/**
	 * Returns the number of elements stored in the table.
	 * 
	 * @return number of elements in the table
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns the length of the table (the number of buckets).
	 * 
	 * @return length of the table (capacity)
	 */
	public int getCapacity() {
		return hashTable.size();
	}

	// Rehash method, this method is called anytime the load factor of the
	// hashTableis greater than 0.75, this method will create a new hashTable
	// that is times two the previous hashtable then rehash the nodes into new
	// indexes in the hashtable
	public void reHash() {
		ArrayList<Node<E>> newTable = new ArrayList<>();
		for (int index = 0; index < (hashTable.size()) * 2; index++) {
			newTable.add(null);
		}

		for (int index = 0; index < hashTable.size(); index++) {
			if (hashTable.get(index) != null) {
				Node<E> current = hashTable.get(index);
				while (current != null) {
					int currentElmHash = Math
							.abs((current.data.hashCode()) % newTable.size());

					Node<E> currInNew = new Node<>(current.data);
					if (newTable.get(currentElmHash) == null) {
						newTable.set(currentElmHash, currInNew);
					} else {
						Node<E> spot = newTable.get(currentElmHash);
						while (spot.next != null) {
							spot = spot.next;
						}
						if (spot.next == null) {
							spot.next = currInNew;
						}
					}
					current = current.next;
				}
			}

		}
		// assigning old hastable to new hash table that is two times the
		// previous hashstable
		hashTable = newTable;
	}

	/**
	 * Looks for the specified element in the table. Goes through each node in
	 * the hash table and if a node data is equal to the element then the method
	 * returns true else it returns false
	 * 
	 * @param element to be found
	 * @return true if the element is in the table, false otherwise
	 */
	public boolean contains(Object element) {
		int indexes = Math.abs(element.hashCode() % hashTable.size());
		Node<E> current = hashTable.get(indexes);

		if (current == null) {
			return false;
		}
		// if the node at the soecific index isnt equal to null, this will then
		// check if the data element is present in the linked list of nodes
		while (current != null) {
			if (current.data.equals(element)) {
				return true;
			}
			current = current.next;

		}
		return false;

	}

	/**
	 * Adds the specified element to the collection, if it is not already
	 * present. If the element is already in the collection, then this method
	 * does nothing.
	 * 
	 * @param element the element to be added to the collection
	 */
	// STUDENTS: After adding the element to the table, consider
	// the load factor. If it is greater than MAX_LOAD_FACTOR then
	// you must double the size of the table. [Create a new table
	// that is twice as big as the current one and then re-hash
	// of all of the data from the old table into the new one.
	// Hint: It's OK to iterate over the original table.]
	public void add(E element) {
		int indexes = Math.abs(element.hashCode() % hashTable.size());

		if (contains(element)) {
			return;
		} else {
			Node<E> newElement = new Node<>(element); // this is a node with the
			// // element's data
			if (hashTable.get(indexes) == null) {
				hashTable.set(indexes, newElement);
				// if the hash position in the table is null, then the element
				// is just added to that index
			} else {
				Node<E> current = hashTable.get(indexes);
				while (current.next != null) {
					current = current.next;
				}
				// this loop finds the last node in the current bucket
				if (current.next == null) {
					current.next = newElement;
				}
				// when the last node is found, the node then points to the
				// newElement
			}
			size++;
		}
		// checks the load factor and if the load factor is greater than 0.75
		// this will then call on the rehash methdo
		if (((double) size() / (double) getCapacity()) >= MAX_LOAD_FACTOR) {
			reHash();
		}
	}

	/**
	 * Removes the specified element from the collection. If the element is not
	 * present then this method should do nothing.
	 *
	 * @param element the element to be removed
	 * @return true if an element was removed, false if no element removed
	 */
	public boolean remove(Object element) {
		int count = 0;
		// checks if the hashtable doesnt contain the element to be removed, if
		// the element isnt present the method returns false
		if (!contains(element)) {
			return false;
		} else {
			int indexes = Math.abs(element.hashCode() % hashTable.size());
			Node<E> current = hashTable.get(indexes); // node with the
			// current
			if (current.data.equals(element)) {
				hashTable.set(indexes, current.next);
				size -= 1;
				return true;
			} // hash index's
				// element
				// takes node of thr previous
			Node<E> previous = null;
			// sets the previous to the current
			previous = current;// previous node/element
			while (current != null) {
				if (current.data.equals(element)) {
					count++;
					// decrement size
					// sets the previous next node to the node after the current
					previous.next = current.next;
					size -= 1;
					return true;

				} else {

					previous = current;
					current = current.next;
				}

			}

		}

		return false;

	}

	/**
	 * Returns an Iterator that can be used to iterate over all of the elements
	 * in the collection.
	 * 
	 * The order of the elements is unspecified.
	 */
	// STUDENTS: You may NOT copy the data from the hash table
	// into a different structure. You must write an iterator
	// that iterates over your hash table directly, without
	// copying the data anywhere.
	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			int marker = 0;
			int position = 0;
			Node<E> currentNode = hashTable.get(position); // the currentNode
															// for the bucket

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return marker < size();
			}

			@Override
			public E next() {
				while (currentNode == null) {
					position++;
					currentNode = hashTable.get(position);
				}
				E data = currentNode.data;
				currentNode = currentNode.next;
				marker++;
				return data;

			}

		};
	}
}
