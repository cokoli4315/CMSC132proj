package searchTree;

import java.util.Collection;

/**
 * This class is used to represent the empty search tree: a search tree that
 * contains no entries.
 * 
 * This class is a singleton class: since all empty search trees are the same,
 * there is no need for multiple instances of this class. Instead, a single
 * instance of the class is created and made available through the static field
 * SINGLETON.
 * 
 * The constructor is private, preventing other code from mistakenly creating
 * additional instances of the class.
 * 
 */
public class EmptyTree<K extends Comparable<K>, V> implements Tree<K, V> {
	/**
	 * This static field references the one and only instance of this class. We
	 * won't declare generic types for this one, so the same singleton can be
	 * used for any kind of EmptyTree.
	 */
	private static EmptyTree SINGLETON = new EmptyTree();
	private Tree<K, V> tree;

	public static <K extends Comparable<K>, V> EmptyTree<K, V> getInstance() {
		return SINGLETON;
	}

	/**
	 * Constructor is private to enforce it being a singleton
	 * 
	 */
	private EmptyTree() {
		// Nothing to do
	}

	/*
	 * Find the value that this key is bound to in this tree. -- value
	 * associated with the key by this Tree, or null if the key does not have an
	 * association in this tree.
	 * 
	 * @key- key to search for
	 */
	public V search(K key) {
		return null;
	}

	/*
	 * Insert/update the Tree with a new key:value pair. If the key already
	 * exists in the tree, update the value associated with it. If the key
	 * doesn't exist, insert the new key value pair. This method returns a
	 * reference to an Tree that represents the updated value. In many, but not
	 * all cases, the method may just return a reference to this.
	 * 
	 * @key-key
	 * 
	 * @value-value that the key maps
	 */
	public NonEmptyTree<K, V> insert(K key, V value) {

		return new NonEmptyTree<K, V>(key, value, this, this);
	}

	/*
	 * Delete any binding the key has in this tree. If the key isn't bound, this
	 * is a no-op This method returns a reference to an Tree that represents the
	 * updated value. In many, but not all cases, the method may just return a
	 * reference to this. This method is annotated @CheckReturnValue because you
	 * have to pay attention to the return value;
	 * 
	 * @key-key to delete from the binary search tree
	 */
	public Tree<K, V> delete(K key) {
		return this;
	}

	/*
	 * Return the maximum key in the subtree
	 * 
	 * @throws Tree is empty exception if the tree is empty
	 */
	public K max() throws TreeIsEmptyException {
		throw new TreeIsEmptyException();
	}

	/*
	 * Return minimum key in the Empty tree
	 * 
	 * @Throws tree is empty exception if the tree is empty
	 */
	public K min() throws TreeIsEmptyException {
		throw new TreeIsEmptyException();
	}

	/*
	 * return number of keys that are bound in this tree
	 * 
	 * @returns the number of keys bound in tree
	 */
	public int size() {
		return 0;
	}

	// Add all keys bound in this tree to the collection c. The elements must be
	// added in their sorted order.
	public void addKeysToCollection(Collection<K> c) {
		return;
	}

	// Returns a Tree containing all entries between fromKey and toKey,
	// inclusive
	public Tree<K, V> subTree(K fromKey, K toKey) {
		return this;
	}
}