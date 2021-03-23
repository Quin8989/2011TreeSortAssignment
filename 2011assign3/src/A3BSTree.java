import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class A3BSTree<E extends Comparable<? super E>> implements Tree<E> {

	private Node root; // root of BST

	private class Node {
		private E item;
		private Node left, right, parent; // left and right subtrees
		private int size = 0; // number of nodes in subtree

		public Node(E item, int size) {
			this.item = item;
			this.size = size;
		}
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
			x.left.parent = x;}
		else if (cmp > 0) {
			x.right = add(x.right, item);
			x.right.parent = x;}
		else
			x.item = item;
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
		if (o == null) throw new IllegalArgumentException("calls delete() with a null key");
        root = delete(root, o);
		return true;
    }

    private Node delete(Node x, Object o) {
        if (x == null) return null;

        int cmp = ((Comparable<? super E>) o).compareTo(x.item);
        if      (cmp < 0) x.left  = delete(x.left,  o);
        else if (cmp > 0) x.right = delete(x.right, o);
        else { 
            if (x.right == null) return x.left;
            if (x.left  == null) return x.right;
            Node temp = x;
            while(temp.left != null) {
            	temp = temp.left;
            }
            x.item=temp.item;
          
            temp = null;
            x.left = null;
        } 
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    } 
	    /**
	     * Returns true if this symbol table is empty.
	     * @return {@code true} if this symbol table is empty; {@code false} otherwise
	     */
	    public boolean isEmpty() {
	        return size() == 0;
	    }
	

	@Override
	public boolean contains(Object o) {
		Node x= root;
		int cmp;
		while(true) {
			if(x == null) return false;
			cmp = ((Comparable<? super E>) o).compareTo(x.item);
			if (cmp < 0) x = x.left;
			else if (cmp > 0) x = x.right;
			else return true;
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

			// If you can walk right, walk right, then fully left.
			// otherwise, walk up until you come from left.
			if (next.right != null) {
				next = next.right;
				while (next.left != null)
					next = next.left;
				return r.item;
			}

			while (true) {
				if (next.parent == null) {
					next = null;
//					System.out.println("hi");
					return r.item;
				}
				if (next.parent.left == next) {
//					System.out.println("hui");
					next = next.parent;
					return r.item;
				}
				next = next.parent;
			}

		}
	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
		return 0;
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

	/* for testing DELETE BEFORE SUBMITTING */
	public static void main(String[] args) {
		Tree<Integer> tree = new A3BSTree<>();

		tree.add(3);
		tree.add(2);
		tree.add(10);
		tree.add(13);
		tree.add(206);
		tree.add(12);
		tree.add(11);
		tree.add(1000);
		tree.add(1001);
		tree.add(205);
		
		tree.remove(206);
		

		Iterator<Integer> iterator = tree.iterator();
		while (iterator.hasNext()) {
			Integer item = iterator.next();
			System.out.println(item);
		}
		
		System.out.println(tree.size());
		System.out.println(" ");
		System.out.println(tree.contains(13));
		

	}

}
