package searchTree;

import java.util.Collection;

/**
 * This class represents a non-empty search tree. An instance of this class
 * should contain:
 * <ul>
 * <li>A key
 * <li>A value (that the key maps to)
 * <li>A reference to a left Tree that contains key:value pairs such that the
 * keys in the left Tree are less than the key stored in this tree node.
 * <li>A reference to a right Tree that contains key:value pairs such that the
 * keys in the right Tree are greater than the key stored in this tree node.
 * </ul>
 * 
 */
public class NonEmptyTree<K extends Comparable<K>, V> implements Tree<K, V> {

	private Tree<K, V> left, right;
	private K key;
	private V value;
	private K maxKey = this.key;
	private K minKey = this.key;
	private int numElements = 0;

	public NonEmptyTree(K key, V value, Tree<K, V> left, Tree<K, V> right) {
		this.key = key;
		this.value = value;
		this.left = left;
		this.right = right;

	}

//This method searches for the key value inside the tree, if the key value is equals to the current key
//The method will return the current value of the key
	/*
	 * Find the value that this key is bound to in this tree.
	 * 
	 * @key to search for returns a value associated with the keys by this Tree,
	 * or null if the key does jot have an association in this tree
	 */
	public V search(K key) {

		if (key.compareTo(this.key) == 0) {
			return this.value;

		} else if (this.key.compareTo(key) < 0) {
			return right.search(key);
			// if the current key is greater than the parameter key then you
			// search for the parameter key on the left side of the tree
		} else {
			return left.search(key);
		}

	}

	/*
	 * Insert/update the Tree with a new key:value pair. If the key already
	 * exists in the tree, update the value associated with it. If the key
	 * doesn't exist, insert the new key value pair. This method returns a
	 * reference to an Tree that represents the updated value. In many, but not
	 * all cases, the method may just return a reference to this. This method is
	 * annotated @CheckReturnValue because you have to pay attention to the
	 * return value; if you simply invoke insert on a Tree and ignore the return
	 * value, your code is almost certainly wrong. this method inserts a key
	 * into the Non empty tree
	 * 
	 * @key, key to compare to the key inside the tree already
	 * 
	 * @value of the key to be inserted into the tree
	 */
	public NonEmptyTree<K, V> insert(K key, V value) {
		if (this.key.compareTo(key) == 0) {
			numElements++;// From Comparable Interface.
			this.value = value;
			return this;
			// else if the current key is greater than the key, this method will
			// return a recursive call to the left side and insert the key and
			// value
		} else if (this.key.compareTo(key) > 0) {
			numElements++;
			this.left = this.left.insert(key, value);
			// else return a recursive call to right side and insert
		} else {
			numElements++;
			this.right = this.right.insert(key, value);
		}
		return this;

	}

	/*
	 * Delete any binding the key has in this tree. If the key isn't bound, this
	 * is a no-op
	 * 
	 * This method returns a reference to an Tree that represents the updated
	 * value. In many, but not all cases, the method may just return a reference
	 * to this. This method is annotated @CheckReturnValue because you have to
	 * pay attention to the return value; if you simply invoke delete on a Tree
	 * and ignore the return value, your code is almost certainly wrong.
	 * 
	 * @param key -- Key
	 * 
	 * @return -- updated tree
	 */
	public Tree<K, V> delete(K key) {
		if (key.compareTo(this.key) == 0) {// if the parameter is the current
			numElements--; // key
			try {
				this.key = left.max();
				this.value = left.search(this.key);
				left = EmptyTree.getInstance();
				return this;

			} catch (TreeIsEmptyException e) {
				try {
					this.key = right.min();
					this.value = right.search(this.key);
					right = EmptyTree.getInstance();
					return this;

				} catch (TreeIsEmptyException f) {
					return EmptyTree.getInstance();

				}
			}
		} else if (key.compareTo(this.key) > 0) {

			right = right.delete(key);
		} else {

			left = left.delete(key);
		}
		return this;

	}

	/*
	 * Return the maximum key in the subtree
	 * 
	 * @returns a key
	 */
	public K max() {
		try {
			maxKey = right.max();
		} catch (TreeIsEmptyException a) {
			return key;
		}
		return maxKey;
	}

	/*
	 * Return the minimum key in the subtree returns the minimum key
	 */
	public K min() {
		try {
			minKey = left.min();
		} catch (TreeIsEmptyException a) {
			return key;
		}
		return minKey;
	}

	// Return number of keys that are bound in this tree.
	// returns a number of keys that are bound in this tree
	public int size() {
		return 1 + left.size() + right.size();
	}

	/*
	 * Add all keys bound in this tree to the collection c. The elements must be
	 * added in their sorted order.
	 * 
	 * @ c - - A list that acts as an accumulator for keys. Keys are inserted in
	 * the list in increasing order. You may not use any sorting method or
	 * Collections.sort to keep the list sorted.
	 */
	public void addKeysToCollection(Collection<K> c) {
		left.addKeysToCollection(c);
		c.add(this.key);
		right.addKeysToCollection(c);

	}

	/*
	 * Returns a Tree containing all entries between fromKey and toKey,
	 * inclusive
	 * 
	 * @Parameters: fromKey - - Lower bound value for keys in subtree toKey - -
	 * Upper bound value for keys in subtree Returns: Tree containing all
	 * entries between fromKey and toKey, inclusive
	 */
	public Tree<K, V> subTree(K fromKey, K toKey) {

		if (this.key.compareTo(fromKey) < 0) {
			return this.right.subTree(fromKey, toKey);

		} else if (toKey.compareTo(this.key) < 0) {
			return this.left.subTree(fromKey, toKey);

		} else {
			return new NonEmptyTree<K, V>(this.key, this.value,
					left.subTree(fromKey, toKey),
					right.subTree(fromKey, toKey));
		}
	}
}