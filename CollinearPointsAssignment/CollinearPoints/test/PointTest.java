import org.junit.Assert;
import org.junit.Test;

/**
 * @author esloho
 *
 */
public class PointTest {

    /**
     * Test method for {@link Point#toString()}.
     */
    @Test
    public final void testToString() {
        Point p = new Point(3, 4);
        Assert.assertTrue(p.toString().equals("(3, 4)"));
        Point q = new Point(-3, 0);
        Assert.assertTrue(q.toString().equals("(-3, 0)"));
    }

    /**
     * Test method for {@link Point#compareTo(Point)}.
     */
    @Test
    public final void testCompareToLargerY() {
        Point p = new Point(3, 4);
        Point q = new Point(3, 3);
        Assert.assertTrue(p.compareTo(q) > 0);
    }

    /**
     * Test method for {@link Point#compareTo(Point)}.
     */
    @Test
    public final void testCompareToEqualYLargerX() {
        Point p = new Point(4, 3);
        Point q = new Point(3, 3);
        Assert.assertTrue(p.compareTo(q) > 0);
    }

    /**
     * Test method for {@link Point#compareTo(Point)}.
     */
    @Test
    public final void testCompareToEquals() {
        Point p = new Point(3, 3);
        Point q = new Point(3, 3);
        Assert.assertTrue(p.compareTo(q) == 0);
    }

    /**
     * Test method for {@link Point#slopeTo(Point)}.
     */
    @Test
    public final void testSlopeToHorizontalLine() {
        Point p = new Point(3, 4);
        Point q = new Point(2, 4);
        Assert.assertTrue(p.slopeTo(q) == new Double(0.0));
    }

    /**
     * Test method for {@link Point#slopeTo(Point)}.
     */
    @Test
    public final void testSlopeToVerticalLine() {
        Point p = new Point(3, 4);
        Point q = new Point(3, 5);
        Assert.assertTrue(p.slopeTo(q) == Double.POSITIVE_INFINITY);
    }

    /**
     * Test method for {@link Point#slopeTo(Point)}.
     */
    @Test
    public final void testSlopeToDegenerateLine() {
        Point p = new Point(3, 4);
        Point q = new Point(3, 4);
        Assert.assertTrue(p.slopeTo(q) == Double.NEGATIVE_INFINITY);
    }

    /**
     * Test method for {@link Point#slopeTo(Point)}.
     */
    @Test
    public final void testSlopeTo() {
        Point p = new Point(2, 2);
        Point q = new Point(3, 4);
        Assert.assertTrue(p.slopeTo(q) == 2.0);
    }
}
