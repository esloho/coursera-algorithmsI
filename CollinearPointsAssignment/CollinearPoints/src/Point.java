import java.util.Comparator;

/**
 * An immutable data type Point that represents a point in the plane
 * 
 * @author esloho
 *
 */
public class Point implements Comparable<Point> {

    private class SlopeOrder implements Comparator<Point> {

        public int compare(Point q, Point r) {
            int comparison = 0;
            final double slopePQ = slopeTo(q);
            final double slopePR = slopeTo(r);

            if (slopePQ < slopePR) {
                comparison = -1;
            } else if (slopePQ > slopePR) {
                comparison = 1;
            }
            return comparison;
        }
    }

    // compares points by slope to this point
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();
    private final int coordinateX;
    private final int coordinateY;

    /**
     * Construct the point (x, y)
     */
    public Point(int x, int y) {
        this.coordinateX = x;
        this.coordinateY = y;
    }

    /**
     * Plot this point to standard drawing
     */
    public void draw() {
        StdDraw.point(coordinateX, coordinateY);
    }

    /**
     * Draw the line segment from this point to that point
     * 
     * @param that
     */
    public void drawTo(Point that) {
        StdDraw.line(this.coordinateX, this.coordinateY, that.coordinateX,
                that.coordinateY);
    }

    /**
     * Return string representation of this point
     */
    public String toString() {
        return "(" + coordinateX + ", " + coordinateY + ")";
    }

    /**
     * Is this point lexicographically smaller than that point? Returns -1 if
     * this point is smaller than that, 1 if this point is greater than that,
     * and 0 if both points are equal
     */
    public int compareTo(Point that) {
        if (this.coordinateY < that.coordinateY)
            return -1;
        if (this.coordinateY > that.coordinateY)
            return 1;
        if (this.coordinateX < that.coordinateX)
            return -1;
        if (this.coordinateX > that.coordinateX)
            return 1;

        return 0;
    }

    /**
     * The slope between this point (x0, y0) and that point (x1, y1) given by
     * the formula (y1 − y0) / (x1 − x0)
     */
    public double slopeTo(Point that) {

        double slope;
        double distanceX = that.coordinateX - this.coordinateX;
        double distanceY = that.coordinateY - this.coordinateY;

        if (distanceY == 0) {
            if (distanceX == 0) {
                // degenerate line segment (between a point and itself
                slope = Double.NEGATIVE_INFINITY;
            } else {
                // horizontal line segment
                slope = new Double(0.0);
            }
        } else if (distanceX == 0) {
            // vertical line segment
            slope = Double.POSITIVE_INFINITY;
        } else {
            slope = distanceY / distanceX;
        }

        return slope;
    }

    // Some unit test
    public static void main(String[] args) {

    }
}