import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

/**
 * <P>
 * The HeavyBag class implements a Set-like collection that allows duplicates (a
 * lot of them).
 * </P>
 * <P>
 * The HeavyBag class provides Bag semantics: it represents a collection with
 * duplicates. The "Heavy" part of the class name comes from the fact that the
 * class needs to efficiently handle the case where the bag contains 100,000,000
 * copies of a particular item (e.g., don't store 100,000,000 references to the
 * item).
 * </P>
 * <P>
 * In a Bag, removing an item removes a single instance of the item. For
 * example, a Bag b could contain additional instances of the String "a" even
 * after calling b.remove("a").
 * </P>
 * <P>
 * The iterator for a heavy bag must iterate over all instances, including
 * duplicates. In other words, if a bag contains 5 instances of the String "a",
 * an iterator will generate the String "a" 5 times.
 * </P>
 * <P>
 * In addition to the methods defined in the Collection interface, the HeavyBag
 * class supports several additional methods: uniqueElements, getCount, and
 * choose.
 * </P>
 * <P>
 * The class extends AbstractCollection in order to get implementations of
 * addAll, removeAll, retainAll and containsAll. (We will not be over-riding
 * those). All other methods defined in the Collection interface will be
 * implemented here.
 * </P>
 */
public class HeavyBag<T> extends AbstractCollection<T> implements Serializable {

	/* Leave this here! (We need it for our testing) */
	private static final long serialVersionUID = 1L;

	/* Create whatever instance variables you think are good choices */

	Map<T, Integer> bag;
	int numOcurrances = 0;

	private Object size;

	/**
	 * Initialize a new, empty HeavyBag
	 */
	public HeavyBag() {
		bag = new HashMap<T, Integer>();

	}

	/**
	 * Adds an instance of o to the Bag
	 * 
	 * @return always returns true, since added an element to a bag always
	 *         changes it
	 * 
	 */
	@Override
	public boolean add(T o) {
		// kesize takes the value of the bag at the given object
		Integer keySize = bag.get(o);
		// if the bag does not contain the object we are supposed to insert then
		// it adds the obejct and sets the sixe of the bag to 1
		if (!(bag.containsKey(o))) {
			bag.put(o, 1); // adds the key to the bag and sets its value to 1.
		} else {
			bag.put(o, ++keySize); // Increments the value attached to the key
		}
		// incremenes the number of occurances of a particular key in the bag
		numOcurrances++;
		return true;
	}

	/**
	 * Adds multiple instances of o to the Bag. If count is less than 0 or count
	 * is greater than 1 billion, throws an IllegalArgumentException.
	 * 
	 * @param o     the element to add
	 * @param count the number of instances of o to add
	 * @return true, since addMany always modifies the HeavyBag.
	 */
	public boolean addMany(T o, int count) {
		// throws illegalArgumentException if the value of count is less than 0
		// or greater than a billion
		if (count < 0 || count > 1000000000) {
			throw new IllegalArgumentException();
		} else {
			// set the value of count to a new variable
			Integer keySize = count;
			// take the value of th bag at the object of the parameter
			Integer key = bag.get(o);
			// if the bag doesnt contain the particular object in the paraemter
			// then it adds it and sets the value to count
			if (!bag.containsKey(o)) {
				bag.put(o, keySize);
				// esle if adds the parameter then adds all the other values
				// that are the same as the parameter so if the parameteer had
				// one 'a' and you added three a's then the value will return 4
			} else {
				bag.put(o, key + count);

			}
			// sets the number of values in the bag to size plus the occurances
			numOcurrances = numOcurrances + keySize;

		}
		return true;

	}

	/**
	 * Generate a String representation of the HeavyBag. This will be useful for
	 * your own debugging purposes, but will not be tested other than to ensure
	 * that it does return a String and that two different HeavyBags return two
	 * different Strings.
	 */
	@Override
	public String toString() {
		return bag.keySet() + " ";
	}

	/**
	 * Tests if two HeavyBags are equal. Two HeavyBags are considered equal if
	 * they contain the same number of copies of the same elements. Comparing a
	 * HeavyBag to an instance of any other class should return false;
	 */
	@Override
	public boolean equals(Object o) {

		Integer variable = 0;
		// compares the parameter to another value in another heavybag if they
		// are equal it returns true
		if (o == this) {
			return true;
		}
		// if o isn't an instance of the heavybag then the method returns false
		if (!(o instanceof HeavyBag)) {
			return false;
		}
		// creates a bag that contains to o parameter values sets the parameter
		// o to a new heavyBag
		HeavyBag<?> newBag = (HeavyBag<?>) o;
		// loops through the values in the bag
		for (T values : bag.keySet()) {
			// if the at the specific value is equals to the same value in
			// another bag the method returns true
			if (bag.get(values).equals(newBag.bag.get(values))) {
				variable = 1;
				// else it will return false;
			} else {
				variable = 0;
				break;
			}
		}
		return variable == 1 && bag.size() == newBag.bag.size();

	}

	/**
	 * Return a hashCode that fulfills the requirements for hashCode (such as
	 * any two equal HeavyBags must have the same hashCode) as well as desired
	 * properties (two unequal HeavyBags will generally, but not always, have
	 * unequal hashCodes).
	 */
	@Override
	public int hashCode() {
		return bag.hashCode();
	}

	/**
	 * <P>
	 * Returns an iterator over the elements in a heavy bag. Note that if a
	 * Heavy bag contains 3 a's, then the iterator must iterate over 3 a's
	 * individually.
	 * </P>
	 */
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			private Iterator<T> entrySet = bag.keySet().iterator();
			private int count = 0;
			private int max = 0;
			private T current = null;
			private int expectedCount = numOcurrances;
			Integer keySize = bag.size();

			@Override
			public boolean hasNext() {
				if (count < max) {
					return true;
				} else {
					return entrySet.hasNext();
				}
			}

			@Override
			public T next() {
				if (numOcurrances != expectedCount) {
					throw new ConcurrentModificationException();
				}
				if (count < max) {
					count++;
				} else {
					T entrySets = entrySet.next();
					current = entrySets;
					count = 1;
				}

				return current;

			}

			@Override
			public void remove() {
				// if the current value is null then throw illegalstateexpection
				if (current == null)
					throw new IllegalStateException();
				if (numOcurrances != expectedCount)
					throw new ConcurrentModificationException();
				if (max > 1) {
					// if the max is greater than 1 then remove the current
					// value in the bag then decrement
					HeavyBag.this.remove(current);
					count--;
				} else {
					entrySet.remove();
					keySize--;
				}
				max--;
				expectedCount = numOcurrances;
			}
		};

	}

	/**
	 * return a Set of the elements in the Bag (since the returned value is a
	 * set, it can contain no duplicates. It will contain one value for each
	 * UNIQUE value in the Bag).
	 * 
	 * @return A set of elements in the Bag
	 */
	public Set<T> uniqueElements() {
		// returns the keyset of values in the bag
		return bag.keySet();
	}

	/**
	 * Return the number of instances of a particular object in the bag. Return
	 * 0 if it doesn't exist at all.
	 * 
	 * @param o object of interest
	 * @return number of times that object occurs in the Bag
	 */
	public int getCount(Object o) {
		// if the bag doesnt contain the parameter then it sets the count of the
		// parameter to 0
		if (!bag.containsKey(o)) {
			return 0;
		}
		// else it just returns the value at the specific key in thw bag
		return bag.get(o);

	}

	/**
	 * Given a random number generator, randomly choose an element from the Bag
	 * according to the distribution of objects in the Bag (e.g., if a Bag
	 * contains 7 a's and 3 b's, then 70% of the time choose should return an a,
	 * and 30% of the time it should return a b.
	 * 
	 * This operation can take time proportional to the number of unique objects
	 * in the Bag, but no more.
	 * 
	 * This operation should not affect the Bag.
	 * 
	 * @param r Random number generator
	 * @return randomly chosen element
	 */
	public T choose(Random r) {
		// the value that should be chosen will be set to null
		T chosenValue = null;
		int count = 0;
		// size of the next int
		int index = r.nextInt(size());
		// loops through the values inside the bag
		for (T element : this.bag.keySet()) {
			// adds the values at the specific element
			count += bag.get(element);
			// if the vaalues inside the bag are greater than the index
			if (count > index) {
				// set the value to be chosen to the element
				chosenValue = element;
				break;
			}
		}
		return chosenValue;
	}

	/**
	 * Returns true if the Bag contains one or more instances of o
	 */
	@Override
	public boolean contains(Object o) {
		if (bag.containsKey(o)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Decrements the number of instances of o in the Bag.
	 * 
	 * @return return true if and only if at least one instance of o exists in
	 *         the Bag and was removed.
	 */
	@Override
	public boolean remove(Object o) {
		// gets the value of the parameter
		Integer keySize = bag.get(o);
		// if the baf contains the parameter object and the parameter value is
		// greater than 1
		if (bag.containsKey(o) && bag.get(o) > 0) {
			if (keySize >= 2) {
				bag.put((T) o, --keySize); // decrements the key value if at
											// least 2
				numOcurrances--;
			} else {
				bag.remove(o); // if there is only one, the key is removed
								// entirely.
				numOcurrances--;
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Total number of instances of any object in the Bag (counting duplicates)
	 */
	@Override
	// returns the number of values inside the bag including duplicates
	public int size() {
		return numOcurrances;

	}
}