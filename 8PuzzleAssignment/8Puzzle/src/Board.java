public class Board {

    private final int[][] blocks;

    /**
     * Construct a board from an N-by-N array of blocks (where blocks[i][j] =
     * block in row i, column j)
     * 
     * @param blocks
     */
    public Board(int[][] b) {
        blocks = b;
    }

    /**
     * Board dimension N
     * 
     * @return
     */
    public int dimension() {
        return blocks.length;
    }

    /**
     * Number of blocks out of place
     * 
     * @return
     */
    public int hamming() {
        return 0; // change
    }

    /**
     * Sum of Manhattan distances between blocks and goal
     * 
     * @return
     */
    public int manhattan() {
        return 0; // change
    }

    /**
     * Is this board the goal board?
     * 
     * @return
     */
    public boolean isGoal() {
        return false; // change
    }

    /**
     * A board that is obtained by exchanging two adjacent blocks in the same
     * row
     * 
     * @return
     */
    public Board twin() {
        return null; // change
    }

    /**
     * Does this board equal y?
     */
    public boolean equals(Object y) {
        return false; // change
    }

    /**
     * All neighboring boards
     * 
     * @return
     */
    public Iterable<Board> neighbors() {
        return null; // change
    }

    /**
     * String representation of this board (in the output format specified in
     * the assignment)
     */
    public String toString() {
        return ""; // change
    }

    // unit tests (not graded)
    public static void main(String[] args) {

    }

}
