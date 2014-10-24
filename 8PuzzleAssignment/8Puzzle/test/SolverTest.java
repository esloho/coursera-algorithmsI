import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */

/**
 * @author esloho
 *
 */
public class SolverTest {

    private Solver solver;
    private int[][] blocks2D;
    

    /**
     * Test method for {@link Solver#Solver(Board)}.
     */
    @Test
    public final void testSolverSolvable() {
        blocks2D = new int[][]{ { 0, 1, 3 }, { 4, 2, 5 }, { 7, 8, 6} };
        solver = new Solver(new Board(blocks2D));
        Assert.assertTrue(solver.isSolvable());
    }
    
    /**
     * Test method for {@link Solver#Solver(Board)}.
     */
    @Test
    public final void testSolverSolvable1Move() {
        blocks2D = new int[][]{ {1, 2, 3 }, { 4, 5, 6 }, { 7, 0, 8} };
        solver = new Solver(new Board(blocks2D));
        Assert.assertTrue(solver.isSolvable());
    }
    
    /**
     * Test method for {@link Solver#Solver(Board)}.
     */
    @Test
    public final void testSolverGoal() {
        blocks2D = new int[][]{ { 1, 2, 3 }, { 4, 5, 6}, { 7, 8, 0} };
        solver = new Solver(new Board(blocks2D));
        Assert.assertTrue(solver.isSolvable());
    }
    
    /**
     * Test method for {@link Solver#Solver(Board)}.
     */
    @Test
    public final void testSolverUnsolvable() {
        blocks2D = new int[][]{ { 1, 2, 3 }, { 4, 5, 6}, { 8, 7, 0} };
        solver = new Solver(new Board(blocks2D));
        Assert.assertFalse(solver.isSolvable());
    }


    /**
     * Test method for {@link Solver#moves()}.
     */
    @Test
    public final void testMoves() {
        blocks2D = new int[][]{ { 0, 1, 3 }, { 4, 2, 5 }, { 7, 8, 6} };
        solver = new Solver(new Board(blocks2D));
        Assert.assertEquals(4, solver.moves());
    }

    /**
     * Test method for {@link Solver#solution()}.
     */
    @Test
    public final void testSolution() {
        blocks2D = new int[][]{ { 0, 1, 3 }, { 4, 2, 5 }, { 7, 8, 6} };
        solver = new Solver(new Board(blocks2D));

        int n = 0;
        System.out.println("Moves towards solution");
        for (Board b : solver.solution()) {
            System.out.println(b.toString());
            n++;
        }
        
        // Expected boards: initial + number of moves
        Assert.assertEquals(5, n);
    }
    
    /**
     * Test method for {@link Solver#solution()}.
     */
    @Test
    public final void testSolutionEmpty() {
        blocks2D = new int[][]{ { 1, 2, 3 }, { 4, 5, 6}, { 8, 7, 0} };
        solver = new Solver(new Board(blocks2D));
        
        Assert.assertNull(solver.solution());
    }

}
