public class Solver {

    private final SearchNode gameTree;
    private final MinPQ<SearchNode> priorityQueue;
    private final SearchNode solution;

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final SearchNode previousNode;
        private final int moves;

        // Constructs a SearchNode
        public SearchNode(final Board b, final SearchNode previous, final int m) {
            this.board = b;
            this.previousNode = previous;
            this.moves = m;
        }

        // Overrides the natural order comparison
        public int compareTo(final SearchNode that) {
            if (this.board.manhattan() + this.moves < that.board.manhattan()
                    + that.moves) {
                return -1;
            }

            if (this.board.manhattan() + this.moves > that.board.manhattan()
                    + that.moves) {
                return 1;
            }

            if (this.board.manhattan() < that.board.manhattan()) {
                return -1;
            }

            if (this.board.manhattan() > that.board.manhattan()) {
                return 1;
            }

            if (this.board.hamming() < that.board.hamming()) {
                return -1;
            }

            if (this.board.hamming() > that.board.hamming()) {
                return 1;
            }

            return 0;
        }
    }

    /**
     * Find a solution to the initial board (using the A* algorithm)
     * 
     * @param initial
     */
    public Solver(final Board initial) {
        gameTree = new SearchNode(initial, null, 0);
        priorityQueue = new MinPQ<SearchNode>();

        // If the initial board is the goal, work is done
        if (initial.isGoal()) {
            solution = gameTree;
        } else {
            // Insert initial search node and its twin
            priorityQueue.insert(gameTree);
            final SearchNode twinTree = new SearchNode(initial.twin(), null, 0);
            priorityQueue.insert(twinTree);
            solution = solveBoard();
        }
    }

    private SearchNode solveBoard() {
        final SearchNode solved;

        // Delete from PQ the search node with the minimum priority
        SearchNode minSN = priorityQueue.delMin();

        // Repeat until search node dequeued corresponds to a goal board.
        while (!minSN.board.isGoal()) {
            // Insert all neighboring search nodes
            insertNeighborsInPQ(minSN);
            minSN = priorityQueue.delMin();
        }

        // The puzzle is solvable only if the goal board was generated through
        // the original initial board and not its twin.
        if (isGameTree(minSN)) {
            solved = minSN;
        } else {
            solved = null;
        }

        return solved;
    }
    
    
    private boolean isGameTree(final SearchNode node) {
        // Copy to a new variable to avoid modification of the parameter 
        // (so course's checkstyle stop complaining)
        SearchNode current = node;
        
        while (current.previousNode != null) {
            current = current.previousNode;
        }
        
        return current.board == gameTree.board;
    }

    /**
     * Insert all neighboring search nodes (those that can be reached in one
     * move from the dequeued search node).
     * 
     * @param current
     */
    private void insertNeighborsInPQ(final SearchNode dequeued) {

        for (Board n : dequeued.board.neighbors()) {
            // Critical optimization: don't insert the neighbor that is equal to
            // the previous search node
            if (!isRepeated(dequeued, n)) {
                priorityQueue.insert(new SearchNode(n, dequeued,
                        dequeued.moves + 1));
            }
        }
    }

    private boolean isRepeated(final SearchNode dequeued, final Board board) {
        if (dequeued.previousNode == null) {
            return false;
        }

        return dequeued.previousNode.board.equals(board);
    }

    /**
     * Is the initial board solvable?
     * 
     * @return
     */
    public boolean isSolvable() {
        return solution != null;
    }

    /**
     * Minimum number of moves to solve initial board; -1 if unsolvable
     * 
     * @return
     */
    public int moves() {
        if (solution == null) {
            return -1;
        }
        
        return solution.moves + solution.board.manhattan();
    }

    /**
     * Sequence of boards in a shortest solution; null if unsolvable
     * 
     * @return
     */
    public Iterable<Board> solution() {
        
        if (solution == null) {
            return null;
        }
        
        // Create an Iterable (stack)
        final Stack<Board> solutionPath = new Stack<Board>();
        SearchNode current = solution;

        // Push boards in reverse order (from solution to the initial)
        while (current != null) {
            solutionPath.push(current.board);
            current = current.previousNode;
        }

        return solutionPath;
    }

    /**
     * Solve a slider puzzle (given in the assignment)
     * 
     * @param args
     */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
