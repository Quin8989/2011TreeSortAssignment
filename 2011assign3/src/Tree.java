import java.util.Collection;
import java.util.Iterator;


public interface Tree <E> {

	/**
	 * Adds the specified element to this tree (duplicates are not allowed)
	 * @param e element to add
	 * @return true if the element was added (the tree was modified) 
	 */
	public boolean add(E e);

	/**
	 * Adds all of the elements in the specified collection to this tree.
	 * (duplicates are not allowed)
	 * @param c Collection containing the elements
	 * @return true if the tree was changed as a result of the call
	 */
	public boolean addAll (Collection<? extends E> c);

	/**
	 * Removes the specified element from this tree, if it is present. 
	 * @param o object to remove
	 * @return true if this tree contained the element 
	 */
	public boolean remove(Object o);

	/**
	 * Returns true if this tree contains the specified element. 
	 * @param o
	 * @return
	 */
	public boolean contains(Object o);
	
	/** Returns an iterator over the elements of this tree in ascending order
	 * @return the iterator described above
	 */
	public Iterator<E> iterator();

	/** Returns the height of the tree. 
	 * For an empty tree should return -1. 
	 * For a tree of one node should return 0
	 * @return height of the tree
	 */
	public int height();
	
	/** Returns the number of elements in the tree. 
	 * @return number of elements
	 */
	public int size();

}
