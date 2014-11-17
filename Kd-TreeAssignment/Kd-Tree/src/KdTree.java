/**
 * A a mutable data type KdTree.java that uses a 2d-tree to implement the same
 * API that PointSET. A 2d-tree is a generalization of a BST to two-dimensional
 * keys. The idea is to build a BST with points in the nodes, using the x- and
 * y-coordinates of the points as keys in strictly alternating sequence.
 * 
 * @author esloho
 *
 */
public class KdTree {

    private Node root = null;
    private int size = 0;

    private static class Node {
        private Point2D p; // the point
        private RectHV rect; // axis-aligned rectangle corresponding to this
                             // node
        private Node lb; // the left/bottom subtree
        private Node rt; // the right/top subtree

        public Node(Point2D point, double xmin, double ymin, double xmax,
                double ymax) {
            p = point;
            rect = new RectHV(xmin, ymin, xmax, ymax);
        }
    }

    /**
     * Construct an empty KdTree
     */
    public KdTree() {
    }

    /**
     * Return whether the set is empty.
     * 
     * @return
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Returns the number of points in the set.
     * 
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * Adds the point to the set (if it is not already in the set).
     * 
     * @param p
     */
    public void insert(Point2D p) {
        if (root == null) {
            root = new Node(p, 0.0, 0.0, 1.0, 1.0);
            size++;
        } else {
            // Recursive search from root, which is vertical node.
            put(root, true, p);
        }
    }

    private void put(Node n, boolean vertical, Point2D p) {
        // If the point already exists do not insert it
        if (n.p.compareTo(p) != 0) {
            if (vertical) { // vertical: divide the space by x coordinate
                            // (left/right)
                if (p.x() <= n.p.x()) { // go to the left subtree
                    if (n.lb == null) {
                        // create a new node which rect is the left part of its
                        // parent rectangle.
                        n.lb = new Node(p, n.rect.xmin(), n.rect.ymin(),
                                n.p.x(), n.rect.ymax());
                        size++;
                    } else {
                        // toggle the orientation for next level
                        put(n.lb, !vertical, p);
                    }
                } else { // go to the right subtree
                    if (n.rt == null) {
                        // create a new node which rect is the right part of its
                        // parent rectangle.
                        n.rt = new Node(p, n.p.x(), n.rect.ymin(),
                                n.rect.xmax(), n.rect.ymax());
                        size++;
                    } else {
                        put(n.rt, !vertical, p);
                    }
                }
            } else { // horizontal: divide the space by y coordinate
                     // (top/bottom)
                if (p.y() <= n.p.y()) { // go to the left subtree
                    if (n.lb == null) {
                        // create a new node which rect is the bottom part of
                        // its
                        // parent rectangle.
                        n.lb = new Node(p, n.rect.xmin(), n.rect.ymin(),
                                n.rect.xmax(), n.p.y());
                        size++;
                    } else {
                        put(n.lb, !vertical, p);
                    }
                } else { // go to the right subtree
                    if (n.rt == null) {
                        // create a new node which rect is the top part of its
                        // parent rectangle.
                        n.rt = new Node(p, n.rect.xmin(), n.p.y(),
                                n.rect.xmax(), n.rect.ymax());
                        size++;
                    } else {
                        put(n.rt, !vertical, p);
                    }
                }
            }
        }
    }

    /**
     * Returns whether the set contains point p.
     * 
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        // Search from the root node, which is a vertical node
        return contains(root, true, p);
    }

    /**
     * Recursive method that search for a given point in the tree
     * 
     * @param n
     * @param vertical
     * @param p
     * @return
     */
    private boolean contains(Node n, boolean vertical, Point2D p) {
        if (n == null) {
            return false;
        }

        boolean found = false;

        if (n.p.equals(p)) {
            found = true;
        } else if (vertical) { // vertical: compare nodes by x coordinate
            if (p.x() <= n.p.x()) { // go to the left subtree
                found = contains(n.lb, !vertical, p); // toggle the orientation
            } else { // go to the right subtree
                found = contains(n.rt, !vertical, p);
            }
        } else { // horizontal: compare nodes by y coordinate
            if (p.y() <= n.p.y()) { // go to the left subtree
                found = contains(n.lb, !vertical, p); // toggle the orientation
            } else { // go to the right subtree
                found = contains(n.rt, !vertical, p);
            }
        }

        return found;
    }

    /**
     * Draws all points to standard draw.
     */
    public void draw() {
        // Start drawing from root, which is a vertical node
        draw(root, true);
    }

    private void draw(Node n, boolean vertical) {
        if (n != null) {
            // Draw the point
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            n.p.draw();

            // Draw dividing line with the corresponding color
            if (vertical) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius();
                final Point2D bottomEnd = new Point2D(n.p.x(), n.rect.ymin());
                final Point2D topEnd = new Point2D(n.p.x(), n.rect.ymax());
                bottomEnd.drawTo(topEnd);
            } else { // horizontal line
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius();
                final Point2D leftEnd = new Point2D(n.rect.xmin(), n.p.y());
                final Point2D rightEnd = new Point2D(n.rect.xmax(), n.p.y());
                leftEnd.drawTo(rightEnd);
            }

            // Draw the left and right subtrees
            draw(n.lb, !vertical);
            draw(n.rt, !vertical);
        }
    }

    /**
     * Returns all points that are inside the rectangle.
     * 
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        return range(root, new Stack<Point2D>(), rect);
    }

    /**
     * Recursive method that search all points inside a given rectangle
     * 
     * @param n
     * @param pointsInRange
     * @param rect
     * @return
     */
    private Iterable<Point2D> range(Node n, Stack<Point2D> pointsInRange,
            RectHV rect) {

        if (n != null) {
            if (rect.contains(n.p)) {
                pointsInRange.push(n.p);
            }

            // check if rectangle intersects with line instead (obtained like in
            // draw)

            // Look left only if left node rectangle intersects with the given
            // one
            if (n.lb != null && rect.intersects(n.lb.rect)) {
                range(n.lb, pointsInRange, rect);
            }

            // Look right only if right node rectangle intersects with the given
            // one
            if (n.rt != null && rect.intersects(n.rt.rect)) {
                range(n.rt, pointsInRange, rect);
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
        return nearest(root, true, p, null);
    }

    private Point2D nearest(Node n, boolean vertical, Point2D p,
            Point2D oldChampion) {

        Point2D champion = oldChampion;

        if (n != null) {
            if (champion != null) {
                if (p.distanceSquaredTo(n.p) < p.distanceSquaredTo(champion)) {
                    champion = n.p;
                }
            } else { // first iteration (root becomes champion)
                champion = n.p;
            }

            if (vertical) { // vertical division by x coordinate
                // Cutting point for the segment between query point and the
                // division line
                final Point2D refPoint = new Point2D(n.p.x(), p.y());
                if (p.x() < n.p.x()) {
                    // The point is on the left side of the division
                    champion = nearest(n.lb, !vertical, p, champion);

                    // Check condition for search on the other side
                    // You need to check the back (other side) of a node if
                    // (after checking the front side) the distance (squared)
                    // from the point of interest to the best point so far is
                    // greater than the distance from the point of interest to
                    // the line passing through the point in the node.
                    if (p.distanceSquaredTo(champion) > p
                            .distanceSquaredTo(refPoint)) {
                        champion = nearest(n.rt, !vertical, p, champion);
                    }
                } else { // The point is on right side of the division
                    champion = nearest(n.rt, !vertical, p, champion);

                    if (p.distanceSquaredTo(champion) > p
                            .distanceSquaredTo(refPoint)) {
                        champion = nearest(n.lb, !vertical, p, champion);
                    }
                }
            } else { // horizontal division by y coordinate
                final Point2D refPoint = new Point2D(p.x(), n.p.y());

                if (p.y() < n.p.y()) {
                    // The point is on the bottom side of the division
                    champion = nearest(n.lb, !vertical, p, champion);

                    if (p.distanceSquaredTo(champion) > p
                            .distanceSquaredTo(refPoint)) {
                        champion = nearest(n.rt, !vertical, p, champion);
                    }
                } else { // The point is on the top side of the division
                    champion = nearest(n.rt, !vertical, p, champion);

                    if (p.distanceSquaredTo(champion) > p
                            .distanceSquaredTo(refPoint)) {
                        champion = nearest(n.lb, !vertical, p, champion);
                    }
                }
            }
        }

        return champion;
    }

    // Some testing
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        // StdDraw.show(0);

        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }

        kdtree.draw();

        // // draw in blue the nearest neighbor (using kd-tree algorithm)
        // StdDraw.setPenColor(StdDraw.BLUE);
        final Point2D query = new Point2D(0.607421875, 0.42695312500000004);
        final Point2D nearest = kdtree.nearest(query);
        // kdtree.nearest(query).draw();
        System.out.println("Nearest point to the query by Kd-Tree: "
                + nearest.toString());

        StdDraw.show(0);
    }
}
