import java.util.Arrays;

/**
 * Examines 4 points at a time and checks whether they all lie on the same line
 * segment, printing out any such line segments to standard output and drawing
 * them using standard drawing. To check whether the 4 points p, q, r, and s are
 * collinear, checks whether the slopes between p and q, between p and r, and
 * between p and s are all equal.
 * 
 * @author esloho
 *
 */
public class Brute {

    /**
     * Initializes the animation mode and rescales the coordinate system.
     */
    private static void initAnimationMode() {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01); // make the points a bit larger
    }

    private static void closeAnimationMode() {
        // display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius();
    }

    /**
     * Reads the file and initializes an array of points with the coordinates
     * indicated in the file.
     * 
     * @param filename
     * @return
     */
    private static Point[] initPoints(final String filename) {
        final In file = new In(filename);
        final int numPoints = file.readInt(); // number of points in the file
        Point[] points = new Point[numPoints];

        for (int i = 0; i < numPoints; i++) {
            final int x = file.readInt();
            final int y = file.readInt();
            points[i] = new Point(x, y);
        }

        return points;
    }

    /**
     * Checks whether all the points in the array lie in the same line.
     * 
     * @param points
     * @return
     */
    private static boolean areCollinear(final Point[] points) {
        boolean inline = true;

        final double firstSlope = points[0].slopeTo(points[1]);
        double slope;
        boolean found = false;
        int i = 1;

        while (i < points.length && !found) {
            slope = points[0].slopeTo(points[i]);

            if (slope != firstSlope) {
                inline = false;
                found = true;
            }
            i++;
        }

        return inline;
    }

    /**
     * Draws each point in the array
     * 
     * @param points
     */
    private static void drawPoints(final Point[] points) {
        for (Point p : points) {
            p.draw();
        }
    }

    /**
     * Print to standard output a line segment as an ordered sequence of its
     * constituent points, separated by " -> ".
     * 
     * @param points
     */
    private static String printLinePoints(final Point[] points) {
        String line = "";
        boolean first = true;

        for (Point p : points) {
            if (first) {
                line += p.toString();
                first = false;
            } else {
                line += " -> " + p.toString();
            }
        }

        System.out.println(line);
        return line;
    }

    /**
     * Examines 4 points at a time and checks whether they all lie on the same
     * line segment, printing out such line segments to standard output and
     * drawing them using standard drawing.
     * 
     * @param points
     */
    private static void drawLines(final Point[] points) {
        // Check all the combinations of 4 points (N choose 4)
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        final Point[] tuple = { points[i], points[j],
                                points[k], points[l] };
                        // If all 4 points are collinear, order the points and
                        // draw the segment
                        if (areCollinear(tuple)) {
                            Arrays.sort(tuple);
                            tuple[0].drawTo(tuple[3]);
                            printLinePoints(tuple);
                        }
                    }
                }
            }
        }
    }

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {

        Point[] points = initPoints(args[0]);

        initAnimationMode();
        drawPoints(points);
        drawLines(points);
        closeAnimationMode();
    }
}
