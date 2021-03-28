import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class A3AVLTree<E> implements Tree<E> { // consider extending A3BSTree
	private int size = 0;
	private Node root;

	private class Node {
		private E item;
		private Node left, right, parent;
		private int height = 1;

		public Node(E item) {
			this.item = item;

		}
	}

	public A3AVLTree() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean add(E item) {
		System.out.println("request to add: " + item);
		root = add(root, item);
		size++;

		return true;
	}

	private Node add(Node node, E item) {
		/* 1. Perform the normal BST rotation */
		if (node == null) {
			System.out.println("added: " + item);
			System.out.println(" ");
			return (new Node(item));
		}

		int cmp = ((Comparable<? super E>) item).compareTo(node.item);

		if (cmp < 0) {
			System.out.println("node " + item + " is smaller than: " + node.item + " go left");
			node.left = add(node.left, item);
			node.left.parent = node;
		} else {
			System.out.println("node " + item + " is larger than: " + node.item + " go right");
			node.right = add(node.right, item);
			node.right.parent = node;
		}

		/* 2. Update height of this ancestor node */
		node.height = Math.max(height(node.left), height(node.right)) + 1;

		/*
		 * 3. Get the balance factor of this ancestor node to check whether this node
		 * became unbalanced
		 */
		System.out.println("checking balance at " + node.item);

		int balance = getBalance(node);

		if (node.left == null && node.right == null) {
			System.out.println("this is the bottom, no action");
			return node;
		}

		int cmp2;
		if (node.left == null) {
			System.out.println("nothing to the left, " + node.item + " is bigger");
			cmp2 = 69420;
		} else {
			cmp2 = ((Comparable<? super E>) item).compareTo(node.left.item);
		}

		int cmp3;
		if (node.right == null) {
			System.out.println("nothing to the right, " + node.item + " is bigger");
			cmp3 = 69420;
		} else {
			cmp3 = ((Comparable<? super E>) item).compareTo(node.right.item);
		}
//		System.out.println(node.right.item);
		// If this node becomes unbalanced, then there are 4 cases

		// Left Left Case
		if (balance > 1 && cmp2 < 0) {
			System.out.println(node.item + "'s subtree is left heavy & node to left is larger, rotate right");
			return rightRotate(node);
		}

		// Right Right Case
		if (balance < -1 && cmp3 > 0) {
			System.out.println(node.item + "'s subtree is right heavy & node to right is smaller, rotate left");
			return leftRotate(node);
		}

		// Left Right Case
		if (balance > 1 && cmp2 > 0) {
			System.out.println(node.item + "'s subtree is left heavy & node to left is smaller, rotate left-right");
			node.left = leftRotate(node.left);
			return rightRotate(node);
		}

		// Right Left Case
		if (balance < -1 && cmp3 < 0) {
			System.out.println(node.item + "'s subtree is lright heavy & node to right is larger, rotate right-left");
			node.right = rightRotate(node.right);
			return leftRotate(node);
		}

		/* return the (unchanged) node pointer */
		return node;
	}

	private Node rightRotate(Node y) {
		Node x = y.left;
		Node T2 = x.right;

		// Perform rotation
		x.right = y;
		y.left = T2;

		// Update heights
		y.height = Math.max(height(y.left), height(y.right)) + 1;
		x.height = Math.max(height(x.left), height(x.right)) + 1;

		// Return new root
		return x;
	}

	private Node leftRotate(Node x) {
		Node y = x.right;
		Node T2 = y.left;

		// Perform rotation
		y.left = x;
		x.right = T2;

		// Update heights
		x.height = Math.max(height(x.left), height(x.right)) + 1;
		y.height = Math.max(height(y.left), height(y.right)) + 1;

		// Return new root
		return y;
	}

	// Get Balance factor of node N
	private int getBalance(Node N) {
		if (N == null)
			return 0;
		return height(N.left) - height(N.right);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		for (E e : c) {
			add(e);
		}
		return true;
	}
	/*
	 * @Override public boolean remove(Object o) { if (o == null) throw new
	 * IllegalArgumentException(); root = delete(root, o); return true; }
	 * 
	 * private Node delete(Node x, Object o) { if (x == null) return null;
	 * 
	 * int cmp = ((Comparable<? super E>) o).compareTo(x.item); if (cmp < 0) x.left
	 * = delete(x.left, o); else if (cmp > 0) x.right = delete(x.right, o); else {
	 * if (x.right == null) return x.left; if (x.left == null) return x.right; Node
	 * temp = x; while (temp.left != null) { temp = temp.left; } x.item = temp.item;
	 * 
	 * temp = null; x.left = null; } x.size = size(x.left) + size(x.right) + 1;
	 * return x; }
	 */

	public boolean isEmpty() {
		return size() == 0;
	}

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
			if(!isEmpty() || next != null) {
				
				while(next.left != null) {
					System.out.println("hi");
					next = next.left;
				}
			}else return;
		}

		@Override
		public boolean hasNext() {
			return next != null;
		}

		@Override
		public E next() {
			if(next.parent != null) {
				next = next.parent;
				return next.item;
			}
			System.out.println("shit");
			return null;

		}
		private void next(Node root) {
			
		}

		
	}

	@Override
	public int height() {
		if (root == null)
			return 0;
		return root.height;
	}

	private int height(Node node) {
		if (node == null)
			return 0;
		return node.height;

	}

	@Override
	public int size() {
		return size;
	}

	/* for testing DELETE BEFORE SUBMITTING */
	public static void main(String[] args) {
		Tree<Integer> tree = new A3AVLTree<>();

		ArrayList<Integer> dan = new ArrayList<Integer>();
		dan.add(1);
		dan.add(2);
		dan.add(3);




		Set<Integer> set = new HashSet<Integer>();
		set.add(15);
		set.add(16);
		set.add(14);
		set.add(13);
//		set.add(12);
//		set.add(11);

		tree.addAll(dan);

//		tree.add(15);
//		tree.add(16);
//		tree.add(14);
//		tree.add(13);
//		tree.add(12);
//		tree.add(11);
		System.out.println(" ");
		System.out.println(" ");
		System.out.println(" ");

	Iterator<Integer> iterator = tree.iterator();
//		while (iterator.hasNext()) {
		for (int i = 0; i < 11; i++) {
			Integer item = iterator.next();
			System.out.println("Output: " + item);
	}
//		System.out.println("size: " + tree.size());
//		System.out.println("height: " + tree.height());
//		System.out.println(tree.item);

	}

}