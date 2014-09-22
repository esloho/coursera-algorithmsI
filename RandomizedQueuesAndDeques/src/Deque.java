import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A double-ended queue or deque is a generalization of a stack and a queue that
 * supports inserting and removing items from either the front or the back of
 * the data structure
 * 
 * @author esloho
 *
 * @param <Item>
 *            Type of the elements
 */
public class Deque<Item> implements Iterable<Item> {
    private int size; // size of the stack
    private Node first; // top of stack
    private Node last;

    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }

    /**
     * Construct an empty deque as a linked list
     */
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * Check whether the deque is empty
     * 
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Return the number of items on the deque
     */
    public int size() {
        return size;
    }

    /**
     * Insert the item at the front
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("Can't add a null item");
        }

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.previous = null;

        // If the deque was empty, the first is also the last
        if (oldFirst == null) {
            last = first;
        } else {
            oldFirst.previous = first;
        }

        size++;
    }

    /**
     * Insert the item at the end
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("Can't add a null item");
        }

        Node newItem = new Node();
        newItem.item = item;
        newItem.next = null;
        newItem.previous = last;

        // if the deque was empty, the last is also the first
        if (size == 0) {
            first = newItem;
        } else {
            last.next = newItem;
        }

        last = newItem;

        size++;
    }

    /**
     * Delete and return the item at the front
     */
    public Item removeFirst() {
        if (size == 0) {
            throw new java.util.NoSuchElementException(
                    "Can't remove from an empty deque");
        }

        Item firstItem = first.item;
        first = first.next;
        size--;

        if (first == null) {
            last = null;
        } else {
            first.previous = null;
        }

        return firstItem;
    }

    /**
     * Delete and return the item at the end
     */
    public Item removeLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException(
                    "Can't remove from an empty deque");
        }

        Item lastItem = last.item;
        last = last.previous;
        size--;

        if (last == null) {
            first = null;
        } else {
            last.next = null;
        }

        return lastItem;
    }

    /**
     * Return an iterator over items in order from front to end
     */
    public Iterator<Item> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();

            Item nextItem = current.item;
            current = current.next;

            return nextItem;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void printDeque() {
        for (Item it : this) {
            System.out.println(it);
        }
    }

    /**
     * Some testing
     */
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(3);
        deque.addFirst(2);
        deque.addFirst(1);
        deque.addFirst(0);
        deque.addLast(4);
        deque.printDeque();
        System.out.println("Start removing:");
        deque.removeFirst();
        deque.removeLast();
        deque.printDeque();
    }
}
