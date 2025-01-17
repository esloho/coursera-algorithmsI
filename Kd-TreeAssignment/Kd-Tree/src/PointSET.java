/**
 * A mutable data type PointSET.java that represents a set of points in the unit
 * square, implemented by using a red-black BST.
 * 
 * @author esloho
 *
 */

public class PointSET {

    private SET<Point2D> rbBST;

    /**
     * Construct an empty set of points
     */
    public PointSET() {
        rbBST = new SET<Point2D>();
    }

    /**
     * Return whether the set is empty.
     * 
     * @return
     */
    public boolean isEmpty() {
        return rbBST.isEmpty();
    }

    /**
     * Returns the number of points in the set.
     * 
     * @return
     */
    public int size() {
        return rbBST.size();
    }

    /**
     * Adds the point to the set (if it is not already in the set).
     * 
     * @param p
     */
    public void insert(Point2D p) {
        if (!rbBST.contains(p)) {
            rbBST.add(p);
        }
    }

    /**
     * Returns whether the set contains point p.
     * 
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        return rbBST.contains(p);
    }

    /**
     * Draws all points to standard draw.
     */
    public void draw() {
        for (Point2D p : rbBST) {
            p.draw();
        }
    }

    /**
     * Returns all points that are inside the rectangle.
     * 
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        final Stack<Point2D> pointsInRange = new Stack<Point2D>();

        for (Point2D p : rbBST) {
            if (rect.contains(p)) {
                pointsInRange.push(p);
            }
        }

        return pointsInRange;
    }

    /**
     * Returns a nearest neighbor in the set to point p; null if the set is
     * empty.
     * 
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        Point2D champion = null;

        for (Point2D point : rbBST) {
            if (champion != null) {
                if (p.distanceSquaredTo(point) < p.distanceSquaredTo(champion)) {
                    champion = point;
                }
            } else { // The first point to examine is at that time the nearest
                champion = point;
            }
        }

        return champion;
    }

    // Some testing
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        PointSET brute = new PointSET();

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            brute.insert(p);
        }

        // // draw in blue the nearest neighbor (using kd-tree algorithm)
        // StdDraw.setPenColor(StdDraw.BLUE);
        final Point2D query = new Point2D(0.607421875, 0.42695312500000004);
        final Point2D nearest = brute.nearest(query);
        // kdtree.nearest(query).draw();
        System.out.println("Nearest point to the query by brute force: "
                + nearest.toString());
    }

}
