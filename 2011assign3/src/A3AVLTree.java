import java.util.Collection;
import java.util.Iterator;

public class A3AVLTree<E> implements Tree<E> {
	private Node root;
	private int size = 0;

	private class Node {
		private E item;
		private Node left, right, parent;

		private Node(E item) {
			this.item = item;
		}
	}

	public A3AVLTree() {
	}
	
	/**
	 * Adds the specified element to this tree (duplicates are not allowed)
	 * @param e element to add
	 * @return true if the element was added (the tree was modified) 
	 */
	@Override
	public boolean add(E item) {
		root = add(root, item);
		size++;
		return true;
	}

	private Node add(Node x, E item) {
		if (x == null) {
			return new Node(item);
		}
		@SuppressWarnings("unchecked")
		int cmp = ((Comparable<? super E>) item).compareTo(x.item);
		if (cmp < 0) {
			x.left = add(x.left, item);
			x.left.parent = x;
		} else if (cmp > 0) {
			x.right = add(x.right, item);
			x.right.parent = x;
		} else
			x.item = item;
		return balance(x);
	}

	private Node balance(Node x) {
		if (balanceFactor(x) < -1) {
			if (balanceFactor(x.right) > 0) {
				x.right = rotateRight(x.right);
			}
			x = rotateLeft(x);
		} else if (balanceFactor(x) > 1) {
			if (balanceFactor(x.left) < 0) {
				x.left = rotateLeft(x.left);
			}
			x = rotateRight(x);
		}
		return x;
	}

	private int balanceFactor(Node x) {
		return height(x.left) - height(x.right);
	}

	private Node rotateRight(Node a) {

		Node b = a.left;
		b.parent = a.parent;

		a.left = b.right;

		if (a.left != null)
			a.left.parent = a;

		b.right = a;
		a.parent = b;

		if (b.parent != null) {
			if (b.parent.right == a) {
				b.parent.right = b;
			} else {
				b.parent.left = b;
			}
		}

		return b;
	}

	private Node rotateLeft(Node a) {

		Node b = a.right;
		b.parent = a.parent;

		a.right = b.left;

		if (a.right != null)
			a.right.parent = a;

		b.left = a;
		a.parent = b;

		if (b.parent != null) {
			if (b.parent.right == a) {
				b.parent.right = b;
			} else {
				b.parent.left = b;
			}
		}
		return b;
	}
	
	/**
	 * Adds all of the elements in the specified collection to this tree.
	 * (duplicates are not allowed)
	 * @param c Collection containing the elements
	 * @return true if the tree was changed as a result of the call
	 */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		for (E e : c) {
			add(e);
		}
		return true;
	}
	
	/**
	 * Removes the specified element from this tree, if it is present. 
	 * @param o object to remove
	 * @return true if this tree contained the element 
	 */
	@Override
	public boolean remove(Object o) {
		if (o == null)
			throw new IllegalArgumentException();
		root = delete(root, o);
		size--;
		return true;
	}

	private Node delete(Node x, Object o) {
		if (x == null)
			return null;

		@SuppressWarnings("unchecked")
		int cmp = ((Comparable<? super E>) o).compareTo(x.item);
		if (cmp < 0)
			x.left = delete(x.left, o);
		else if (cmp > 0)
			x.right = delete(x.right, o);
		else {
			if (x.right == null)
				return x.left;
			if (x.left == null)
				return x.right;
			Node temp = x;
			while (temp.left != null) {
				temp = temp.left;
			}
			x.item = temp.item;

			temp = null;
			x.left = null;
		}
		return balance(x);
	}
	
	/* Returns if tree is empty
	 * @return true if empty
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns true if this tree contains the specified element. 
	 * @param o
	 * @return true if contains object
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean contains(Object o) {
		Node x = root;
		int cmp;
		while (true) {
			if (x == null)
				return false;
			cmp = ((Comparable<? super E>) o).compareTo(x.item);
			if (cmp < 0)
				x = x.left;
			else if (cmp > 0)
				x = x.right;
			else
				return true;
		}

	}
	
	/** Returns an iterator over the elements of this tree in ascending order
	 * @return the iterator described above
	 */
	@Override
	public Iterator<E> iterator() {
		return new TreeIterator();

	}

	private class TreeIterator implements Iterator<E> {
		Node next = root;

		private TreeIterator() {
			if (root == null) {
				return;
			}

			while (next.left != null)
				next = next.left;

		}
		
		
		@Override
		public boolean hasNext() {
			return next != null;
		}
		
		
		@Override
		public E next() {

			Node r = next;

			if (next.right != null) {
				next = next.right;
				while (next.left != null)
					next = next.left;
				return r.item;
			}

			while (true) {
				if (next.parent == null) {
					next = null;
					return r.item;
				}
				if (next.parent.left == next) {
					next = next.parent;
					return r.item;
				}
				next = next.parent;
			}

		}
	}

	/** Returns the height of the tree. 
	 * For an empty tree should return -1. 
	 * For a tree of one node should return 0
	 * @return height of the tree
	 */
	@Override
	public int height() {
		return height(root);
	}

	int height(Node node) {
		if (node == null)
			return -1;
		else {
			int lDepth = height(node.left);
			int rDepth = height(node.right);

			if (lDepth > rDepth)
				return (lDepth +1);
			else
				return (rDepth +1);
		}

	}
	
	/** Returns the number of elements in the tree. 
	 * @return number of elements
	 */
	@Override
	public int size() {
		return size;
	}
} 
/******************************************************************************
 * Methods add(), remove(), balance() balanceFactor() referenced from the
 * respective methods from:
 * https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/AVLTreeST.java by
 * Robert Sedgewick and Kevin Wayne
 ******************************************************************************/