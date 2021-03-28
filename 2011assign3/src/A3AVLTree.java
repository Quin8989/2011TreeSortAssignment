import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class A3AVLTree<E> implements Tree<E> { // consider extending A3BSTree
	private Node root;
	private int size = 0;
	
	private class Node {
		private E item;
		private Node left, right, parent;
		


		public Node(E item) {
			this.item = item;
		}
	}

	public A3AVLTree() {
		// TODO Auto-generated method stub
	}

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
		int cmp = ((Comparable<? super E>) item).compareTo(x.item);
		if (cmp < 0) {
			x.left = add(x.left, item);
			x.left.parent = x;
		} else if (cmp > 0) {
			x.right = add(x.right, item);
			x.right.parent = x;
		} else
			x.item = item;
//		x.size = 1 + size(x.left) + size(x.right);
		
		return balance(x);
	}
	
	/* ROTATIONS AND STUFF*/
	 private Node balance(Node x) {
	        if (balanceFactor(x) < -1) {
	            if (balanceFactor(x.right) > 0) {
	                x.right = rotateRight(x.right);
	            }
	            x = rotateLeft(x);
	        }
	        else if (balanceFactor(x) > 1) {
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
	
	
	

	@Override
	public boolean addAll(Collection<? extends E> c) {
		for (E e : c) {
			add(e);
		}
		return true;
	}
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
//		x.size = size(x.left) + size(x.right) + 1;
		return balance(x);
	}

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
		return size;
		}
	


	/* for testing DELETE BEFORE SUBMITTING */
	public static void main(String[] args) {
		Tree<Integer> tree = new A3AVLTree<>();

		ArrayList<Integer> dan = new ArrayList<Integer>();
		dan.add(13);
		dan.add(206);
		dan.add(2);
		dan.add(10);
		dan.add(1000);
		
		





		tree.addAll(dan);
		
		//tree.remove(2);

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
		while (iterator.hasNext()) {
			Integer item = iterator.next();
			System.out.println("Output: " + item);
	}
		System.out.println("size: " + tree.size());
		System.out.println("height: " + tree.height());
//		System.out.println(tree.item);

		
		
		
}}