package listClass;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BasicLinkedList<T> implements Iterable<T> {

	private static class Node<T> {
		private T data;
		private Node<T> next;

		private Node(T data) {
			this.data = data;
			next = null;
		}
	}

	// Head and tail references for the nodes
	private Node<T> head;
	private Node<T> tail;
	private int size;

	// setting the head reference to null
	public BasicLinkedList() {
		head = null;
	}

	/**
	 * @param data
	 */
	// returns the size of the node after adding or removing from the linked
	// list
	public int getSize() {
		return size;
	}
	/*
	 * This method returns the linked list after a node has been added to the
	 * linked list
	 * 
	 * @data this is the data to be added to the linked list
	 */

	public BasicLinkedList<T> addToEnd(T data) {
		// Node that carries a reference to the data
		Node<T> node = new Node<>(data);
		// if the linked list is empty then the head and the tail are pointing
		// towards the same node
		if (head == null) {
			head = node;
			tail = node;
		} else {
			// else the next node
			tail.next = node;
			tail = node;

		}
		// then the size increases by 1 each time a node is added
		size += 1;
		return this;
	}

	public BasicLinkedList<T> addToFront(T data) {
		// A node reference for the data to be added to the linked list
		Node<T> node = new Node<>(data);
		// if the head reference is null then the head and the tail are pointed
		// towards the same node
		if (head == null) {
			head = node;
			tail = node;
			// The next node is the head
		} else {
			node.next = head;
			head = node;
		}
		size += 1;
		return this;

	}

	// returns the head of the linked list, if the head is not null and the size
	// is greater than 0
	public T getFirst() {
		if (head != null || size > 0) {
			return head.data;
			// else return null
		} else {
			return null;
		}
	}

	// returns the tail of the linked list if the linked list isn't null and the
	// size is above 0
	public T getLast() {
		if (head != null && tail != null || size > 0) {
			return tail.data;
		} else {
			return null;
		}

	}

	// Returning the first element in the linked list if the linked list isn't
	// null
	public T retrieveFirstElement() {
		// Reference to the head of the linked list
		Node<T> prev = head;
		if (head == null) {
			return null;
		} else {
			// if the head is equal to the tail
			if (head == tail) {
				head = null;
				tail = null;
			} else {
				head = head.next;
			}

		}
		// After the the first element is returned the size of the linked list
		// is then reduced by one
		size -= 1;
		// returns a reference to the head node
		return prev.data;
	}

	// Removes and returns the tail element. If the list is empty, returns null.
	public T retrieveLastElement() {
		Node<T> prevTail = tail;
		if (tail == null)
			return null;
		else {
			if (head == tail) {
				head = null;
				tail = null;
			} else {
				Node<T> previousToTail = head;
				while (previousToTail.next != tail)
					previousToTail = previousToTail.next;
				tail = previousToTail;
				tail.next = null;
			}
		}
		// returns a reference to the tail of the linked list then reduces the
		// size of the linked list by one
		size -= 1;
		return prevTail.data;
	}

	// Removes all instances of the target element from the list. The return
	// value is just a reference to the current object.
	public BasicLinkedList<T> removeAllInstances(T targetData) {
		int count = 0;
		while (head != null && head.data.equals(targetData)) {
			head = head.next;
			size -= 1;
		}

		if (head == null) {
			return null;
		}
		// A reference to the head
		Node<T> current = head;
		while (current.next != null) {
			// if the linked list has an element that is equals to the target
			// element then the count will increase by one and the size
			// decreases by 1
			if (current.next.data.equals(targetData)) {
				count++;
				current.next = current.next.next;
				size -= 1;
			} else {
				current = current.next;
			}

		}
		size -= count;
		// return the current object
		return this;
	}

	@Override
	// Returns an instance of an anonymous inner class that defines an Iterator
	// over this list (from head to tail).
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			// makes a node reference to the head of the linked list
			Node<T> pointer = head;

			@Override
			// This will only return if the head doesn't equal to null meaning
			// that the linked list is empty
			public boolean hasNext() {
				return (pointer != null);
			}

			@Override
			// Returns the head of the linked list
			public T next() {
				Node<T> trac = pointer;
				pointer = pointer.next;
				return trac.data;
			}

		};

	}
}
