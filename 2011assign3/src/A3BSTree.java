import java.util.Collection;
import java.util.Iterator;

public class A3BSTree<E extends Comparable<? super E>> implements Tree<E> {

	private Node root;

	private class Node {
		private E item;
		private Node left, right, parent;
		private int size = 0;

		private Node(E item, int size) {
			this.item = item;
			this.size = size;
		}
	}

	public A3BSTree() {
	}

	@Override
	public boolean add(E item) {
		root = add(root, item);
		return true;
	}

	private Node add(Node x, E item) {
		if (x == null) {
			return new Node(item, 1);
		}
		int cmp = item.compareTo(x.item);
		if (cmp < 0) {
			x.left = add(x.left, item);
			x.left.parent = x;
		} else if (cmp > 0) {
			x.right = add(x.right, item);
			x.right.parent = x;
		} else
			x.item = item;
		x.size = 1 + size(x.left) + size(x.right);
		return x;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean modified = false;
		for (E e : c)
			if (add(e))
				modified = true;
		return modified;
	}

	@Override
	public boolean remove(Object o) {
		if (o == null)
			throw new IllegalArgumentException();
		root = delete(root, o);
		return true;
	}

	private Node delete(Node x, Object o) {
		if (x == null)
			return null;

		@SuppressWarnings("unchecked")
		int cmp = ((Comparable<? super E>) o).compareTo(x.item);
		if (cmp < 0) {
			x.left = delete(x.left, o);
		} else if (cmp > 0)
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
		x.size = size(x.left) + size(x.right) + 1;
		return x;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

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

	@Override
	public Iterator<E> iterator() {
		return new TreeIterator();

	}

	private class TreeIterator implements Iterator<E> {
		Node next = root;

		public TreeIterator() {
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

	@Override
	public int height() {
		return height(root);
	}

	int height(Node node) {
		if (node == null)
			return 0;
		else {
			int lDepth = height(node.left);
			int rDepth = height(node.right);

			if (lDepth > rDepth)
				return (lDepth + 1);
			else
				return (rDepth + 1);
		}

	}

	@Override
	public int size() {
		return size(root);
	}

	private int size(Node x) {
		if (x == null)
			return 0;
		else
			return x.size;
	}

}

/******************************************************************************
 * Methods add(), remove(), balance() balanceFactor() referenced from the
 * respective methods from:
 * https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/AVLTreeST.java by
 * Robert Sedgewick and Kevin Wayne
 ******************************************************************************/
