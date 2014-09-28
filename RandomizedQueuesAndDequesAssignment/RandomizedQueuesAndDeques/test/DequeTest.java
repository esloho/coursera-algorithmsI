import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */

/**
 * @author esloho
 *
 */
public class DequeTest {

    private static final int ITEM1 = 3;
    private static final int ITEM2 = 5;
    private static final int ITEM3 = 6;
    private Deque<Integer> deque;

    /**
     * Test method for {@link Deque#Deque()}.
     */
    @Test
    public final void testConstructorIntDeque() {
        deque = new Deque<Integer>();

        Assert.assertNotNull(deque);
    }

    /**
     * Test method for {@link Deque#isEmpty()}.
     */
    @Test
    public final void testIsEmpty() {
        deque = new Deque<Integer>();
        Assert.assertTrue(deque.isEmpty());
        deque.addFirst(ITEM1);
        Assert.assertFalse(deque.isEmpty());
        deque.removeFirst();
        Assert.assertTrue(deque.isEmpty());
        deque.addLast(ITEM2);
        Assert.assertFalse(deque.isEmpty());
    }

    /**
     * Test method for {@link Deque#size()}.
     */
    @Test
    public final void testSize() {
        deque = new Deque<Integer>();
        Assert.assertTrue(deque.size() == 0);
        deque.addFirst(ITEM1);
        Assert.assertTrue(deque.size() == 1);
        deque.addLast(ITEM2);
        Assert.assertTrue(deque.size() == 2);
    }

    /**
     * 
     */
    @Test(expected = NullPointerException.class)
    public final void testAddFirstNull() {
        deque = new Deque<Integer>();
        deque.addFirst(null);
    }

    /**
     * Test method for {@link Deque#addFirst(java.lang.Object)}.
     */
    @Test
    public final void testAddFirst() {
        deque = new Deque<Integer>();
        deque.addFirst(ITEM1);
        Assert.assertTrue(deque.removeFirst() == ITEM1);
        deque.addFirst(ITEM2);
        deque.addFirst(ITEM3);
        Assert.assertTrue(deque.removeFirst() == ITEM3);
        Assert.assertTrue(deque.removeFirst() == ITEM2);
    }

    /**
     * 
     */
    @Test(expected = NullPointerException.class)
    public final void testAddLastNull() {
        deque = new Deque<Integer>();
        deque.addLast(null);
    }

    /**
     * Test method for {@link Deque#addLast(java.lang.Object)}.
     */
    @Test
    public final void testAddLast() {
        deque = new Deque<Integer>();
        deque.addLast(ITEM1);
        Assert.assertTrue(deque.removeLast() == ITEM1);
        deque.addLast(ITEM2);
        deque.addLast(ITEM3);
        Assert.assertTrue(deque.removeLast() == ITEM3);
        Assert.assertTrue(deque.removeLast() == ITEM2);
    }

    /**
     * 
     */
    @Test(expected = java.util.NoSuchElementException.class)
    public final void testRemoveFirstFromEmpty() {
        deque = new Deque<Integer>();
        deque.removeFirst();
    }

    /**
     * Test method for {@link Deque#removeFirst()}.
     */
    @Test
    public final void testRemoveFirst() {
        deque = new Deque<Integer>();
        deque.addFirst(ITEM1);
        Assert.assertTrue(deque.removeFirst() == ITEM1);
        Assert.assertTrue(deque.size() == 0);
        deque.addLast(ITEM2);
        Assert.assertTrue(deque.removeFirst() == ITEM2);
    }

    /**
     * 
     */
    @Test(expected = java.util.NoSuchElementException.class)
    public final void testRemoveLastFromEmpty() {
        deque = new Deque<Integer>();
        deque.removeLast();
    }

    /**
     * Test method for {@link Deque#removeLast()}.
     */
    @Test
    public final void testRemoveLast() {
        deque = new Deque<Integer>();
        deque.addLast(ITEM1);
        Assert.assertTrue(deque.removeLast() == ITEM1);
        Assert.assertTrue(deque.size() == 0);
        deque.addFirst(ITEM2);
        Assert.assertTrue(deque.removeLast() == ITEM2);
    }

    /**
     * Test method for {@link Deque#iterator()}.
     */
    @Test
    public final void testEmptyIterator() {
        deque = new Deque<Integer>();
        Iterator<Integer> iterator = deque.iterator();
        Assert.assertFalse(iterator.hasNext());
    }

    /**
     * Test method for {@link Deque#iterator()}.
     */
    @Test
    public final void testIterator() {
        deque = new Deque<Integer>();
        deque.addFirst(ITEM1);
        deque.addFirst(ITEM2);
        deque.addFirst(ITEM3);
        Iterator<Integer> iterator = deque.iterator();
        Assert.assertTrue(iterator.hasNext());
        Assert.assertTrue(iterator.next() == ITEM3);
        Assert.assertTrue(iterator.hasNext());
        Assert.assertTrue(iterator.next() == ITEM2);
        Assert.assertTrue(iterator.hasNext());
        Assert.assertTrue(iterator.next() == ITEM1);
        Assert.assertFalse(iterator.hasNext());
    }

    /**
     * 
     */
    @Test(expected = java.util.NoSuchElementException.class)
    public final void testEndIterator() {
        deque = new Deque<Integer>();
        deque.addFirst(ITEM1);
        Iterator<Integer> iterator = deque.iterator();
        iterator.next();
        iterator.next();
    }

    /**
     * 
     */
    @Test(expected = UnsupportedOperationException.class)
    public final void testRemoveFromIterator() {
        deque = new Deque<Integer>();
        Iterator<Integer> iterator = deque.iterator();
        iterator.remove();
    }
}
