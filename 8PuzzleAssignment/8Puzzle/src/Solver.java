public class Solver {

    private final SearchNode gameTree;
    private final SearchNode twinTree;
    private MinPQ<SearchNode> priorityQueue;
    private final SearchNode solution;

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final SearchNode previousNode;
        private final int moves;

        // Constructs a SearchNode
        public SearchNode(Board b, SearchNode previous, int m) {
            this.board = b;
            this.previousNode = previous;
            this.moves = m;
        }

        // Overrides the natural order comparison
        public int compareTo(SearchNode that) {
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
    public Solver(Board initial) {
        gameTree = new SearchNode(initial, null, 0);
        priorityQueue = new MinPQ<SearchNode>();

        twinTree = new SearchNode(initial.twin(), null, 0);

        // If the initial board is the goal, work is done
        if (initial.isGoal()) {
            solution = gameTree;
        } else {
            // Insert initial search node and its twin
            priorityQueue.insert(gameTree);
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
        if (minSN.board == gameTree.board) {
            solved = minSN;
        } else {
            solved = null;
        }

        return solved;
    }

    /**
     * Insert all neighboring search nodes (those that can be reached in one
     * move from the dequeued search node).
     * 
     * @param current
     */
    private void insertNeighborsInPQ(SearchNode dequeued) {

        for (Board n : dequeued.board.neighbors()) {
            // Critical optimization: don't insert the neighbor that is equal to
            // the previous search node
            if (!dequeued.previousNode.board.equals(n)) {
                priorityQueue.insert(new SearchNode(n, dequeued,
                        dequeued.moves + 1));
            }
        }
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
        return solution.moves + solution.board.manhattan();
    }

    /**
     * Sequence of boards in a shortest solution; null if unsolvable
     * 
     * @return
     */
    public Iterable<Board> solution() {
        // create a stack and put the solution board, then the previous one,
        // etc.
        Stack<Board> solutionPath = new Stack<Board>();
        SearchNode current = solution;

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
