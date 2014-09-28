/**
 * Execution: java Percolation N
 * 
 * Dependencies: WeightedQuickUnionUF.java
 * 
 * This models a percolation system on a N-by-N grid based on the Weighted Quick
 * Union Find algorithm. It allows to open sites, to check whether a site is
 * open, to check whether a site is full, and to determine if the system
 * percolates or not.
 * 
 * 
 * Date: September 7, 2014
 * 
 * @author esloho
 */

public class Percolation {

    private final int gridSize; // number of sites of each grid's side
    private final int extendedM; // sites in the extended 1D representation
    private final int basicM; // sites in the basic 1D representation
    private boolean[][] grid; // false = closed site, true = open site
    private final WeightedQuickUnionUF extendedWeightedUF;
    private final WeightedQuickUnionUF basicWeightedUF;
    private final int upperToken; // extra token to connect all sites in row 1
    private final int bottomToken; // extra token to connect all sites in row N

    /**
     * Create N-by-N grid, with all sites blocked. Creates a
     * WeightedQuickUnionUF object with an array size of N*N plus two extra
     * tokens: one to perform as "parent" for all sites in the first row and
     * another "parent" for all sites in the last row.
     */
    public Percolation(final int N) {
        if (N <= 0)
            throw new IllegalArgumentException(
                    "Argument must be greater than 0");

        gridSize = N;
        basicM = N * N + 1;
        extendedM = N * N + 2;
        upperToken = 0;
        bottomToken = extendedM - 1;
        extendedWeightedUF = new WeightedQuickUnionUF(extendedM);
        basicWeightedUF = new WeightedQuickUnionUF(basicM);
        grid = new boolean[N][N];

        // initialize all sites as blocked
        for (int row = 0; row < N; row++) {
            for (int column = 0; column < N; column++) {
                grid[row][column] = false;
            }
        }

        // connect all sites at the first row to the upper token
        for (int column = 0; column < N; column++) {
            basicWeightedUF.union(0, column + 1);
        }

        // connect all sites at the first row to the upper token
        for (int column = 0; column < N; column++) {
            extendedWeightedUF.union(0, column + 1);
        }

        // connect all sites at the last row to the bottom token
        for (int i = 0; i < N; i++) {
            extendedWeightedUF.union(bottomToken - 1 - i, bottomToken);
        }
    }

    /**
     * Check if indexes are out of bounds.
     * 
     * @param row
     *            row index from 1 to N
     * @param column
     *            column index from 1 to N
     */
    private void checkBounds(final int row, final int column) {
        if (row <= 0 || row > gridSize)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (column <= 0 || column > gridSize)
            throw new IndexOutOfBoundsException("column index j out of bounds");
    }

    /**
     * Converts (row,column) index into 1D array index. The new index is by
     * counting i-1 rows of size N + an incomplete row of j columns.
     */
    private int xyTo1D(final int row, final int column) {
        final int index = ((row - 1) * gridSize) + column;
        return index;
    }

    /**
     * Open site (row i, column j) if it is not already, and connect it with its
     * opened neighbors.
     */
    public void open(final int row, final int column) {
        checkBounds(row, column);
        if (!grid[row - 1][column - 1]) {
            grid[row - 1][column - 1] = true; // open the site

            final int p = xyTo1D(row, column);
            int q;

            // connect the new open site to any open neighbor
            // check up neighbor, except if we are at the first row
            if (row > 1 && grid[row - 2][column - 1]) {
                q = xyTo1D(row - 1, column);
                extendedWeightedUF.union(p, q);
                basicWeightedUF.union(p, q);
            }
            // check left neighbor, except if we are at first column
            if (column > 1 && grid[row - 1][column - 2]) {
                q = xyTo1D(row, column - 1);
                extendedWeightedUF.union(p, q);
                basicWeightedUF.union(p, q);
            }
            // check down neighbor, except if we are at last row
            if (row < gridSize && grid[row][column - 1]) {
                q = xyTo1D(row + 1, column);
                extendedWeightedUF.union(p, q);
                basicWeightedUF.union(p, q);
            }
            // check right neighbor, except if we are at last column
            if (column < gridSize && grid[row - 1][column]) {
                q = xyTo1D(row, column + 1);
                extendedWeightedUF.union(p, q);
                basicWeightedUF.union(p, q);
            }
        }
    }

    /**
     * Checks whether the given site is open.
     */
    public boolean isOpen(final int row, final int column) {
        checkBounds(row, column);

        return grid[row - 1][column - 1]; // open sites are true in the grid
    }

    /**
     * Checks whether the given site is opened and connected to the top row.
     */
    public boolean isFull(final int row, final int column) {
        checkBounds(row, column);
        boolean full = false;

        if (grid[row - 1][column - 1]) {
            final int q = xyTo1D(row, column);
            // is full if is connected to the top token
            // full = extendedWeightedUF.connected(upperToken, q);
            full = basicWeightedUF.connected(0, q);
        }

        return full;
    }

    /**
     * The system percolates if the upper token and the bottom token are
     * connected.
     */
    public boolean percolates() {
        if (gridSize == 1) {
            return grid[0][0];
        } else {
            return extendedWeightedUF.connected(upperToken, bottomToken);
        }
    }

}
