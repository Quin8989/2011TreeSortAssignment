import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class A3BSTree<E extends Comparable<? super E>> implements Tree<E> {

	private Node root; // root of BST

	private class Node {
		private E item;
		private Node left, right, parent; // left and right subtrees
		private int size; // number of nodes in subtree

		public Node(E item, int size) {
			this.item = item;
			this.size = size;
		}
	}

	public A3BSTree() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean add(E item) {

		root = add(root, item);
		return true;
	}

	private Node add(Node x, E item) {
		if (x == null)
			return new Node(item, 1);
		int cmp = item.compareTo(x.item);
		if (cmp < 0)
			x.left = add(x.left, item);
		else if (cmp > 0)
			x.right = add(x.right, item);
		else
			x.item = item;
			x.parent = root;
		x.size = 1 + size(x.left) + size(x.right);
		return x;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<E> iterator() {
		return new TreeIterator();
		
	}
	
	private class TreeIterator implements Iterator<E>{

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public E next() {
			// TODO Auto-generated method stub
			return null;
		}
	  
	 }
	
	
	

	@Override
	public int height() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size(root);
	}

	private int size(Node x) {
		if (x == null)
			return 0;
		else
			return x.size;
	}

	/*for testing DELETE BEFORE SUBMITTING*/
	public static void main(String[] args) {
		Tree <Integer> tree = new A3BSTree<>();

		for (Integer i = 0; i < 10; i++) { 
		System.out.println(i);
		tree.add(i);
		}
		
		System.out.println();
	}

}
