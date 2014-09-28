/**
 * Execution: java PercolationStats N T
 * 
 * Dependencies: Percolation.java StdRandom.java StdStats.java
 * 
 * This program estimates the percolation threshold by running T executions for
 * a NxN grid, and prints out the mean, standard deviation and the 95%
 * confidence interval for the percolation threshold.
 * 
 * 
 * Date: September 7, 2014
 * 
 * @author esloho
 */

public class PercolationStats {

    private double[] thresholds;

    /**
     * Initializes N-by-N grid with Percolation and creates the array for
     * storing the percolation thresholds for each execution.
     * 
     * @param N
     * @param T
     */
    public PercolationStats(final int N, final int T) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException(
                    "Arguments must be greater than 0");

        thresholds = new double[T];

        final double totalSites = N * N;

        for (int i = 0; i < T; i++) {
            final Percolation perc = new Percolation(N); // reset
            boolean percolates = false;
            int p, q;
            double opened = 0;

            while (!percolates) {

                do {
                    p = StdRandom.uniform(1, N + 1); // between [a, b)
                    q = StdRandom.uniform(1, N + 1);
                } while (perc.isOpen(p, q)); // repeat until site is
                                             // closed

                perc.open(p, q);
                opened++;
                percolates = perc.percolates();
            }

            thresholds[i] = opened / totalSites;
        }
    }

    /**
     * Sample mean of percolation threshold
     * 
     * @return
     */
    public double mean() {
        return StdStats.mean(thresholds);
    }

    /**
     * Sample standard deviation of percolation threshold
     * 
     * @return
     */
    public double stddev() {
        double stddev = Double.NaN;

        // don't compute it for single execution
        if (thresholds.length > 1) {
            stddev = StdStats.stddev(thresholds);
        }

        return stddev;
    }

    /**
     * Returns lower bound of the 95% confidence interval
     * 
     * @return
     */
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(thresholds.length));
    }

    /**
     * Returns upper bound of the 95% confidence interval
     * 
     * @return
     */
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(thresholds.length));
    }

    /**
     * Takes two command line arguments N and T Performs T independent
     * computational experiments on an N--by-N grid and prints out the mean,
     * standard deviation, and 95% confidence interval for the percolation
     * threshold.
     * 
     * @param args
     */
    public static void main(String[] args) {
        final int inputN = new Integer(args[0]).intValue();
        final int inputT = new Integer(args[1]).intValue();

        final PercolationStats percStats = new PercolationStats(inputN, inputT);

        System.out.println("mean                    = " + percStats.mean());
        System.out.println("stddev                  = " + percStats.stddev());
        System.out.println("95% confidence interval = "
                + percStats.confidenceLo() + ", " + percStats.confidenceHi());
    }
}
