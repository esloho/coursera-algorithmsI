public class Board {

    private final int[] blocks;
    private final int dimension;
    private final int size;

    /**
     * Construct a board from an N-by-N array of blocks (where blocks[i][j] =
     * block in row i, column j)
     * 
     * @param blocks2D
     */
    public Board(int[][] b) {
        dimension = b.length;
        size = dimension * dimension;

        blocks = new int[size];

        for (int row = 0; row < dimension; row++) {
            for (int column = 0; column < dimension; column++) {
                blocks[row * dimension + column] = b[row][column];
            }
        }
    }

    private Board(int[] b, int dim) {
        blocks = b;
        size = b.length;
        dimension = dim;
    }

    /**
     * Board dimension N
     * 
     * @return
     */
    public int dimension() {
        return dimension;
    }

    /**
     * Number of blocks out of place
     * 
     * @return
     */
    public int hamming() {
        int hamming = 0;

        for (int i = 0; i < size; i++) {
            // Count blocks with wrong value excluding the 0
            if ((blocks[i] != i + 1) && (blocks[i] != 0)) {
                hamming++;
            }
        }

        return hamming;
    }

    /**
     * Sum of Manhattan distances between blocks and goal
     * 
     * @return
     */
    public int manhattan() {
        int manhattan = 0;

        for (int i = 0; i < size; i++) {
            // Explore blocks with wrong value excluding the 0
            // Block 0 should contain value 1, etc.
            if ((blocks[i] != i + 1) && (blocks[i] != 0)) {
                int row = i / dimension;
                int column = i % dimension;

                int value = blocks[i];
                // The value 1 should be at block 0, etc.
                int expectedRow = (value - 1) / dimension;
                int expectedColumn = (value - 1) % dimension;

                manhattan += Math.abs(expectedRow - row)
                        + Math.abs(expectedColumn - column);
            }
        }

        return manhattan;
    }

    /**
     * Is this board the goal board?
     * 
     * @return
     */
    public boolean isGoal() {
        // If any block has a different value than the expected: is not the goal
        for (int i = 0; i < size; i++) {
            // The last block has to be different (=0)
            if ((blocks[i] != i + 1) && (i != size - 1)) {
                return false;
            }
        }

        return true;
    }

    /**
     * A board that is obtained by exchanging two adjacent blocks in the same
     * row
     * 
     * @return
     */
    public Board twin() {
        return getNeighbor(0, 1);
    }

    /**
     * Return a board that is obtained by exchanging two adjacent blocks in the
     * given positions
     * 
     * @param pos1
     * @param pos2
     * @return
     */
    private Board getNeighbor(int pos1, int pos2) {
        int[] newBlocks = new int[size];

        // First copy all the blocks as in the original board
        for (int i = 0; i < size; i++) {
            newBlocks[i] = blocks[i];
        }

        // Exchange the 2 first blocks in the twin
        int aux = newBlocks[pos1];
        newBlocks[pos1] = newBlocks[pos2];
        newBlocks[pos2] = aux;

        return new Board(newBlocks, dimension);
    }

    /**
     * Does this board equal y?
     */
    public boolean equals(Object y) {
        if (y == this)
            return true;
        if (y == null)
            return false;
        if (y.getClass() != this.getClass())
            return false;

        Board that = (Board) y;

        // If at any block they 2 boards have different value: not equals
        for (int i = 0; i < size; i++) {
            if (blocks[i] != that.blocks[i]) {
                return false;
            }
        }

        return true;
    }

    /**
     * All neighboring boards
     * 
     * @return
     */
    public Iterable<Board> neighbors() {
        // Locate block with value 0
        int pos0 = 0;

        for (int i = 0; i < size; i++) {
            if (blocks[i] == 0) {
                pos0 = i;
                break;
            }
        }

        Stack<Board> neighbors = new Stack<Board>();

        final int row = pos0 / dimension;
        final int column = pos0 % dimension;

        // Add top neighbor
        if (row > 0) {
            neighbors.push(getNeighbor(pos0, (row - 1) * dimension + column));
        }

        // Add bottom neighbor
        if (row < dimension - 1) {
            neighbors.push(getNeighbor(pos0, (row + 1) * dimension + column));
        }

        // Add left neighbor
        if (column > 0) {
            neighbors.push(getNeighbor(pos0, row * dimension + (column - 1)));
        }

        // Add right neighbor
        if (column < dimension - 1) {
            neighbors.push(getNeighbor(pos0, row * dimension + (column + 1)));
        }

        return neighbors;
    }

    /**
     * String representation of this board (given by the assignment)
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int row = 0; row < dimension; row++) {
            for (int column = 0; column < dimension; column++) {
                s.append(String
                        .format("%2d ", blocks[row * dimension + column]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        // requested by the course API
    }

}
