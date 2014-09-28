public class Subset {

    /**
     * Takes a command-line integer k, reads in a sequence of N strings from
     * standard input using StdIn.readString(), and prints out exactly k of
     * them, uniformly at random. Each item from the sequence can be printed out
     * at most once.
     */
    public static void main(String[] args) {
        int k = new Integer(args[0]).intValue();
        final RandomizedQueue<String> queue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            queue.enqueue(s);
        }

        while (k > 0) {
            System.out.println(queue.dequeue());
            k--;
        }
    }
}
