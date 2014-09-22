import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 */

/**
 * @author esloho
 *
 */
public class RandomizedQueueTest {

    private static final int ITEM1 = 3;
    private static final int ITEM2 = 5;
    private static final int ITEM3 = 6;
    private RandomizedQueue<Integer> randomQueue;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Test method for {@link RandomizedQueue#RandomizedQueue()}.
     */
    @Test
    public final void testRandomizedQueue() {
        randomQueue = new RandomizedQueue<Integer>();
        Assert.assertNotNull(randomQueue);
    }

    /**
     * Test method for {@link RandomizedQueue#isEmpty()}.
     */
    @Test
    public final void testIsEmpty() {
        randomQueue = new RandomizedQueue<Integer>();
        Assert.assertTrue(randomQueue.isEmpty());
        randomQueue.enqueue(ITEM1);
        Assert.assertFalse(randomQueue.isEmpty());
        randomQueue.enqueue(ITEM2);
        Assert.assertFalse(randomQueue.isEmpty());
    }

    /**
     * Test method for {@link RandomizedQueue#size()}.
     */
    @Test
    public final void testSize() {
        randomQueue = new RandomizedQueue<Integer>();
        Assert.assertTrue(randomQueue.size() == 0);
        randomQueue.enqueue(ITEM1);
        randomQueue.enqueue(ITEM2);
        Assert.assertTrue(randomQueue.size() == 2);
    }

    /**
     * Test method for {@link RandomizedQueue#enqueue(java.lang.Object)}.
     */
    @Test
    public final void testEnqueue() {
        randomQueue = new RandomizedQueue<Integer>();
        randomQueue.enqueue(ITEM1);
        randomQueue.enqueue(ITEM2);
        randomQueue.enqueue(ITEM3);
        randomQueue.enqueue(ITEM1);
        randomQueue.enqueue(ITEM2);
        randomQueue.enqueue(ITEM3);
        Assert.assertTrue(randomQueue.printQueue().equals("[ 3 5 6 3 5 6 ]"));
    }

    /**
     * 
     */
    @Test(expected = NullPointerException.class)
    public void testEnqueueNull() {
        randomQueue = new RandomizedQueue<Integer>();
        randomQueue.enqueue(null);
    }

    /**
     * Test method for {@link RandomizedQueue#dequeue()}.
     */
    @Test
    public final void testDequeue() {
        randomQueue = new RandomizedQueue<Integer>();
        randomQueue.enqueue(ITEM1);
        Assert.assertTrue(randomQueue.dequeue() == ITEM1);
        Assert.assertTrue(randomQueue.isEmpty());
    }

    /**
     * 
     */
    @Test(expected = NoSuchElementException.class)
    public final void testDequeueEmpty() {
        randomQueue = new RandomizedQueue<Integer>();
        randomQueue.dequeue();
    }

    /**
     * Test method for {@link RandomizedQueue#sample()}.
     */
    @Test
    public final void testSample() {
        randomQueue = new RandomizedQueue<Integer>();
        randomQueue.enqueue(ITEM1);
        Assert.assertTrue(randomQueue.dequeue() == ITEM1);
        Assert.assertTrue(randomQueue.isEmpty());
    }

    /**
     * 
     */
    @Test(expected = NoSuchElementException.class)
    public final void testSampleEmpty() {
        randomQueue = new RandomizedQueue<Integer>();
        randomQueue.sample();
    }

    @Test
    public final void testIterator() {
        randomQueue = new RandomizedQueue<Integer>();
        randomQueue.enqueue(ITEM1);
        randomQueue.enqueue(ITEM2);
        randomQueue.enqueue(ITEM3);
        Iterator<Integer> iterator = randomQueue.iterator();
        Assert.assertTrue(iterator.hasNext());
        iterator.next();
        Assert.assertTrue(iterator.hasNext());
        iterator.next();
        Assert.assertTrue(iterator.hasNext());
        iterator.next();
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void testIndependentIterators() {
        randomQueue = new RandomizedQueue<Integer>();
        randomQueue.enqueue(ITEM1);
        randomQueue.enqueue(ITEM2);
        randomQueue.enqueue(ITEM3);
        Iterator<Integer> iterator1 = randomQueue.iterator();
        Iterator<Integer> iterator2 = randomQueue.iterator();

        String printIterator1 = "[";
        String printIterator2 = "[";

        while (iterator1.hasNext()) {
            printIterator1 += " " + iterator1.next();
        }

        printIterator1 += "]";
        System.out.println("Iterator1: " + printIterator1);

        while (iterator2.hasNext()) {
            printIterator2 += " " + iterator2.next();
        }

        printIterator2 += "]";
        System.out.println("Iterator2: " + printIterator2);

        Assert.assertNotEquals(printIterator1, printIterator2);
    }

    /**
     * Test method for {@link RandomizedQueue#iterator()}.
     */
    @Test(expected = java.util.NoSuchElementException.class)
    public final void testEndIterator() {
        randomQueue = new RandomizedQueue<Integer>();
        randomQueue.enqueue(ITEM1);
        Iterator<Integer> iterator = randomQueue.iterator();
        iterator.next();
        iterator.next();
    }

    /**
     * 
     */
    @Test(expected = UnsupportedOperationException.class)
    public final void testRemoveFromIterator() {
        randomQueue = new RandomizedQueue<Integer>();
        Iterator<Integer> iterator = randomQueue.iterator();
        iterator.remove();
    }
}
