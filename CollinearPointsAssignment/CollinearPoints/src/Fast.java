import java.util.Arrays;

/**
 * Given a point p, the following method determines whether p participates in a
 * set of 4 or more collinear points. The order of growth of the running time is
 * N2 log N in the worst case and it uses space proportional to N.
 * 
 * @author esloho
 *
 */
public class Fast {

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

    /**
     * Writes all at once at the display and reset pen radius.
     */
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
     * Copy the content of one array of Point to another.
     * 
     * @param points
     *            Source of the copy action.
     * @param copy
     *            Destiny of the copy action.
     */
    private static void copyContent(Point[] points, Point[] copy) {
        for (int i = 0; i < points.length; i++) {
            copy[i] = points[i];
        }
    }

    /**
     * Print to standard output a line segment as an ordered sequence of its
     * constituent points, separated by " -> " and draw the line between the
     * first and the last point.
     * 
     * @param points
     */
    private static void printAndDrawLine(Point first, Point[] points, int lo,
            int hi) {
        // check if first is actually the first natural point, ignore otherwise
        if (first.compareTo(points[lo]) < 0) {
            String line = first.toString();

            for (int i = lo; i < hi; i++) {
                line += " -> " + points[i].toString();
            }

            System.out.println(line);
            first.drawTo(points[hi - 1]);
        }
    }

    /**
     * For each point p, determines whether p participates in a set of 4 or more
     * collinear points, printing out the corresponding line segments to
     * standard output and drawing them using standard drawing.
     * 
     * @param points
     */
    private static void drawLines(Point[] points) {
        // Sort the points and keep a copy of the ordered array
        Arrays.sort(points);
        Point[] slopeOrdered = new Point[points.length];

        for (int i = 0; i < points.length; i++) {
            // reestablish natural order before sorting by slope
            copyContent(points, slopeOrdered);
            // order the whole array by the slope with point i
            Arrays.sort(slopeOrdered, points[i].SLOPE_ORDER);
            double previousSlope = points[i].slopeTo(slopeOrdered[0]);
            int j = 1;
            int count = 1; // count number of slopes with same value
            int firstIndex = 0; // first point found in a collinear set

            while (j < slopeOrdered.length) {
                double newSlope = points[i].slopeTo(slopeOrdered[j]);

                if (previousSlope == newSlope) {
                    // if it is the beginning of a series of equal slopes
                    if (count == 1) {
                        firstIndex = j - 1; // the one producing previousSlope
                    }
                    count++;
                } else {
                    // if the new point is not collinear, check if the last was
                    // the end
                    // of a segment
                    if (count >= 3) {
                        printAndDrawLine(points[i], slopeOrdered, firstIndex,
                                firstIndex + count);
                    }
                    // reset (end of segment or not enough collinear points)
                    count = 1;
                    previousSlope = newSlope;
                }
                j++;
            }
            // check if the last point was the end of a segment
            if (count >= 3) {
                printAndDrawLine(points[i], slopeOrdered, firstIndex,
                        firstIndex + count);
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
