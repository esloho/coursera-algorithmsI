import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */

/**
 * @author esloho
 *
 */
public class PointSETTest {

    private final PointSET pointSET = new PointSET();
    private final static Point2D POINT1 = new Point2D(1.0, 1.0);
    private final static Point2D POINT2 = new Point2D(0.2, 0.2);

    /**
     * Test method for {@link PointSET#isEmpty()}.
     */
    @Test
    public final void testIsEmpty() {
        Assert.assertTrue(pointSET.isEmpty());
    }

    /**
     * Test method for {@link PointSET#size()}.
     */
    @Test
    public final void testSizeWhenEmpty() {
        Assert.assertEquals(0, pointSET.size());
    }

    /**
     * Test method for {@link PointSET#insert(Point2D)}.
     */
    @Test
    public final void testInsert() {
        pointSET.insert(POINT1);
        Assert.assertFalse(pointSET.isEmpty());
        Assert.assertEquals(1, pointSET.size());
    }

    /**
     * Test method for {@link PointSET#contains(Point2D)}.
     */
    @Test
    public final void testContains() {
        pointSET.insert(POINT1);
        Assert.assertTrue(pointSET.contains(POINT1));
    }

    /**
     * Test method for {@link PointSET#range(RectHV)}.
     */
    @Test
    public final void testRange() {
        pointSET.insert(POINT1);
        pointSET.insert(POINT2);

        final RectHV rect = new RectHV(0.1, 0.1, 1.0, 1.0);
        int count = 0;

        for (Point2D p : pointSET.range(rect)) {
            count++;
        }

        Assert.assertEquals(2, count);
    }

    /**
     * Test method for {@link PointSET#nearest(Point2D)}.
     */
    @Test
    public final void testNearest() {
        pointSET.insert(POINT1);
        pointSET.insert(POINT2);
        Assert.assertEquals(POINT2, pointSET.nearest(new Point2D(0.3, 0.3)));
    }

}
