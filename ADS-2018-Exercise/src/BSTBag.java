import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Lyle Christine
 * Student number 0912407c
 */

public class BSTBag<E extends Comparable<E>> implements Bag<E> {

    private Node<E> root;
    int size;

    //Constructs an empty, size zero Binary Search Tree
    public BSTBag() {
        root = null;
        size = 0;
    }

    //Inner node class
    private static class Node<E extends Comparable<E>> {
        protected CountedElement<E> element;
        protected Node<E> left, right;
        protected int count;

        protected Node(E elem) {
            element = new CountedElement<E>(elem);
            left = null;
            right = null;
            count = 1;
        }

        public boolean contains(E elem) {
            CountedElement<E> other = new CountedElement<E>(elem);
            int comp = other.compareTo(element);
            if (comp == 0 && count > 0)
                return true;
            if (comp < 0 && left != null && left.contains(elem))
                return true;
            if (comp > 0 && right != null && right.contains(elem))
                return true;
            return false;
        }
    }

    //add(E element) adds the element to the appropriate branch of the BST and increments its count
    @Override
    public void add(E element) {
        int direction = 0;
        Node<E> parent = null;
        Node<E> curr = root;

        size++;

        while (true) {
            if (curr == null) {
                Node<E> ins = new Node<E>(element);
                ins.element.setCount(ins.count);
                if (root == null) {
                    root = ins;
                } else if (direction < 0) {
                    parent.left = ins;
                } else {
                    parent.right = ins;
                }
                return;
            }

            CountedElement<E> elem = new CountedElement<E>(element);
            direction = elem.compareTo(curr.element);
            if (direction == 0) {
                curr.count++;
                curr.element.setCount(curr.count);
                return;
            }
            parent = curr;
            if (direction < 0) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
    }

    //isEmpty() checks if the BST is empty by confirming if root is null or not
    @Override
    public boolean isEmpty() {
        if (root == null) {
            return true;
        } else return false;
    }

    //size() returns number of elements
    @Override
    public int size() {
        return size;
    }

    //contains(E element) confirms if element exists in the BST
    @Override
    public boolean contains(E element) {
        if (root == null) {
            return false;
        } else {
            return root.contains(element);
        }
    }

    //equals(Bag<E> that) compares two bags by iterating, and comparing, the contents of each bag
    @Override
    public boolean equals(Bag<E> that) {

        Iterator<E> thisIt = this.iterator();
        Iterator<E> thatIt = that.iterator();

        while (thisIt.hasNext() && thatIt.hasNext()) {

            if (!thisIt.next().equals(thatIt.next())) {
                return false;
            }
        }
        return true;
    }

    //clear() empties the BST by setting root to null
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    //remove(E element) searches for E element and decrements its count variable
    @Override
    public void remove(E element) {

        Node<E> curr = root;
        int direction;
        size--;

        while (true) {
            if (curr == null)
                return;

            CountedElement<E> elem = new CountedElement<E>(element);
            direction = elem.compareTo(curr.element);

            if (direction == 0) {
                curr.count--;
                curr.element.setCount(curr.count);
                return;
            }

            if (direction < 0)
                curr = curr.left;
            else
                // direction >0
                curr = curr.right;
        }
    }

    @Override
    public Iterator iterator() {
        return new InOrderIterator();
    }

    private class InOrderIterator implements Iterator<E> {
        private Stack<Node<E>> track;

        //contains references to nodes still to be visited
        private InOrderIterator() {
            track = new LinkedStack<Node<E>>();
            for (Node<E> curr = (Node<E>) root; curr != null; curr = curr.left) {
                int count = curr.element.getCount();
                for (int i = 0; i < count; i++) {
                    if (count > 0)
                        track.push(curr);
                }
            }
        }

        public boolean hasNext() {
            return (!track.empty());
        }

        public E next() {
            if (track.empty())
                throw new NoSuchElementException();
            Node<E> place = track.pop();
            for (Node<E> curr = place.right; curr != null; curr = curr.left) {
                int count = curr.element.getCount();
                for (int i = 0; i < count; i++) {
                    if (count > 0)
                        track.push(curr);
                }

            } return place.element.getElement();
        }
    }
}
