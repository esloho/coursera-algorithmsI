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
public class BoardTest {

    private static final int N = 4;
    private Board board;
    private int[][] blocks2D;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        blocks2D = new int[N][N];
    }

    /**
     * Test method for {@link Board#dimension()}.
     */
    @Test
    public final void testDimension() {
        board = new Board(blocks2D);
        Assert.assertEquals(N, board.dimension());
    }

    /**
     * Test method for {@link Board#hamming()}.
     */
    @Test
    public final void testHammingGoal() {
        // Build a goal board
        for (int row = 0; row < N; row++) {
            for (int column = 0; column < N; column++) {
                blocks2D[row][column] = row * N + column + 1;
            }
        }

        blocks2D[N - 1][N - 1] = 0;

        board = new Board(blocks2D);

        Assert.assertEquals(0, board.hamming()); // TODO
    }

    /**
     * Test method for {@link Board#hamming()}.
     */
    @Test
    public final void testHammingAllWrong() {
        // Build a non goal board
        for (int row = 0; row < N; row++) {
            for (int column = 0; column < N; column++) {
                blocks2D[row][column] = row * N + column;
            }
        }

        board = new Board(blocks2D);

        Assert.assertEquals((N * N - 1), board.hamming()); // TODO
    }

    /**
     * Test method for {@link Board#manhattan()}.
     */
    @Test
    public final void testHamming10() {
        int[][] exampleAssignment = new int[][] { { 8, 1, 3 }, { 4, 0, 2 },
                { 7, 6, 5 } };
        Board boardAssignment = new Board(exampleAssignment);
        Assert.assertEquals(5, boardAssignment.hamming());
    }

    /**
     * Test method for {@link Board#manhattan()}.
     */
    @Test
    public final void testManhattanGoal() {
        // Build a goal board
        for (int row = 0; row < N; row++) {
            for (int column = 0; column < N; column++) {
                blocks2D[row][column] = row * N + column + 1;
            }
        }

        blocks2D[N - 1][N - 1] = 0;

        board = new Board(blocks2D);
        Assert.assertEquals(0, board.manhattan());
    }

    /**
     * Test method for {@link Board#manhattan()}.
     */
    @Test
    public final void testManhattan10() {
        int[][] exampleAssignment = new int[][] { { 8, 1, 3 }, { 4, 0, 2 },
                { 7, 6, 5 } };
        Board boardAssignment = new Board(exampleAssignment);
        Assert.assertEquals(10, boardAssignment.manhattan());
    }

    /**
     * Test method for {@link Board#isGoal()}.
     */
    @Test
    public final void testIsGoalTrue() {
        // Build a goal board
        for (int row = 0; row < N; row++) {
            for (int column = 0; column < N; column++) {
                blocks2D[row][column] = row * N + column + 1;
            }
        }

        blocks2D[N - 1][N - 1] = 0;

        board = new Board(blocks2D);
        Assert.assertTrue(board.isGoal());
    }

    /**
     * Test method for {@link Board#isGoal()}.
     */
    @Test
    public final void testIsGoalFalse() {
        // Build a non goal board
        for (int row = 0; row < N; row++) {
            for (int column = 0; column < N; column++) {
                blocks2D[row][column] = row * N + column;
            }
        }

        board = new Board(blocks2D);
        Assert.assertFalse(board.isGoal());
    }

    /**
     * Test method for {@link Board#twin()}.
     */
    @Test
    public final void testTwin() {
        // Build a non goal board
        for (int row = 0; row < N; row++) {
            for (int column = 0; column < N; column++) {
                blocks2D[row][column] = row * N + column;
            }
        }

        board = new Board(blocks2D);
        Board twin = board.twin();

        // Do expected changes
        blocks2D[0][0] = 1;
        blocks2D[0][1] = 0;

        Assert.assertEquals(toString(blocks2D, N), twin.toString()); // TODO
    }

    /**
     * Test method for {@link Board#equals(java.lang.Object)}.
     */
    @Test
    public final void testEqualsTrue() {
        // Build a non goal board
        for (int row = 0; row < N; row++) {
            for (int column = 0; column < N; column++) {
                blocks2D[row][column] = row * N + column;
            }
        }

        board = new Board(blocks2D);

        int[][] equalBlocks = new int[N][N];

        // Build a non goal board
        for (int row = 0; row < N; row++) {
            for (int column = 0; column < N; column++) {
                equalBlocks[row][column] = row * N + column;
            }
        }

        board = new Board(blocks2D);
        Board equalBoard = new Board(equalBlocks);

        Assert.assertTrue(board.equals(equalBoard)); // TODO
    }

    /**
     * Test method for {@link Board#equals(java.lang.Object)}.
     */
    @Test
    public final void testEqualsFalse() {
        // Build a non goal board
        for (int row = 0; row < N; row++) {
            for (int column = 0; column < N; column++) {
                blocks2D[row][column] = row * N + column;
            }
        }

        board = new Board(blocks2D);

        int[][] equalBlocks = new int[N][N];

        // Build a goal board
        for (int row = 0; row < N; row++) {
            for (int column = 0; column < N; column++) {
                equalBlocks[row][column] = row * N + column + 1;
            }
        }

        board = new Board(blocks2D);
        Board equalBoard = new Board(equalBlocks);

        Assert.assertFalse(board.equals(equalBoard)); // TODO
    }

    /**
     * Test method for {@link Board#neighbors()}.
     */
    @Test
    public final void testNeighbors() {
        Assert.fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link Board#neighbors()}.
     */
    @Test
    public final void testNeighborsNumber() {
        int[][] exampleAssignment = new int[][] { { 8, 1, 3 }, { 4, 0, 2 },
                { 7, 6, 5 } };
        Board boardAssignment = new Board(exampleAssignment);

        int numberNeighbors = 0;

        for (Board b : boardAssignment.neighbors()) {
            numberNeighbors++;
        }
        Assert.assertEquals(4, numberNeighbors);

        // Build a non goal board
        for (int row = 0; row < N; row++) {
            for (int column = 0; column < N; column++) {
                blocks2D[row][column] = row * N + column;
            }
        }

        board = new Board(blocks2D);

        numberNeighbors = 0;

        for (Board b : board.neighbors()) {
            numberNeighbors++;
        }
        Assert.assertEquals(2, numberNeighbors);
    }

    public String toString(int[][] tiles, int size) {
        StringBuilder s = new StringBuilder();
        s.append(size + "\n");
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                s.append(String.format("%2d ", tiles[row][column]));
            }
            s.append("\n");
        }
        return s.toString();
    }

}
