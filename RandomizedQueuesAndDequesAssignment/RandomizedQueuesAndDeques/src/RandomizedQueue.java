import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A randomized queue is similar to a stack or queue, except that the item
 * removed is chosen uniformly at random from items in the data structure.
 * 
 * @author esloho
 *
 * @param <Item>
 *            Type of the elements
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue; // array of items
    private int size; // number of elements on stack

    /**
     * Construct an empty randomized queue as a resizing-array
     */
    public RandomizedQueue() {
        queue = (Item[]) new Object[2];
        size = 0;
    }

    /**
     * Checks whether the queue is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Return the number of items on the queue
     */
    public int size() {
        return size;
    }

    /**
     * 
     * @param capacity
     */
    private void resize(int capacity) {
        assert capacity >= size;
        Item[] temp = (Item[]) new Object[capacity];

        for (int i = 0; i < size; i++) {
            temp[i] = queue[i];
        }

        queue = temp;
    }

    /**
     * Add the item
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("Can't add a null item");
        }

        if (size == queue.length) {
            resize(2 * queue.length);
        }

        // add to size and increment after
        queue[size++] = item;
    }

    /**
     * Delete and return a random item
     */
    public Item dequeue() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Stack underflow");
        }

        int index = StdRandom.uniform(size);
        Item item = queue[index];
        queue[index] = queue[size - 1]; // rearrange to avoid middle nulls
        queue[size - 1] = null;
        size--;

        // Resize if the queue is only 1/4 full leaving some space
        if (size > 0 && size <= queue.length / 4) {
            resize(queue.length / 2);
        }

        return item;
    }

    /**
     * Return (but do not delete) a random item
     */
    public Item sample() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Stack underflow");
        }

        int index = StdRandom.uniform(size);

        return queue[index];
    }

    /**
     * Return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int current;
        private int sizeRandomQueue;
        private Item[] randomQueue;

        public ArrayIterator() {
            sizeRandomQueue = size;
            randomQueue = (Item[]) new Object[sizeRandomQueue];

            for (int i = 0; i < size; i++) {
                randomQueue[i] = queue[i];
            }

            StdRandom.shuffle(randomQueue);
            current = 0;
        }

        public boolean hasNext() {
            return current < sizeRandomQueue;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();

            Item item = randomQueue[current];
            current++;

            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public String printQueue() {

        String print = "[ ";
        for (int i = 0; i < size; i++) {
            print += queue[i] + " ";
        }

        print += "]";
        System.out.println(print);

        return print;
    }

    private String printFullQueue() {

        String print = "[ ";
        for (int i = 0; i < queue.length; i++) {
            print += queue[i] + " ";
        }

        print += "]";
        System.out.println(print);

        return print;
    }

    /**
     * Some testing
     */
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomQueue = new RandomizedQueue<Integer>();
        randomQueue.enqueue(0);
        randomQueue.enqueue(1);
        randomQueue.enqueue(2);
        System.out.println("Expected: 0 1 2 null");
        randomQueue.printFullQueue();

        randomQueue.dequeue();
        System.out.println("Expected: x x null null");
        randomQueue.printFullQueue();

        randomQueue.dequeue();
        System.out.println("Expected: x null");
        randomQueue.printFullQueue();
    }
}