import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */

/**
 * @author esloho
 *
 */
public class KdTreeTest {

    private final KdTree kd = new KdTree();
    private final static Point2D POINT1 = new Point2D(1.0, 1.0);
    private final static Point2D POINT2 = new Point2D(0.2, 0.2);

    /**
     * Test method for {@link KdTree#isEmpty()}.
     */
    @Test
    public final void testIsEmpty() {
        Assert.assertTrue(kd.isEmpty());
    }

    /**
     * Test method for {@link KdTree#size()}.
     */
    @Test
    public final void testSizeWhenEmpty() {
        Assert.assertEquals(0, kd.size());
    }

    /**
     * Test method for {@link KdTree#insert(Point2D)}.
     */
    @Test
    public final void testInsert() {
        kd.insert(POINT1);
        Assert.assertFalse(kd.isEmpty());
        Assert.assertEquals(1, kd.size());
        kd.insert(POINT2);
        Assert.assertEquals(2, kd.size());

        // Check that duplicates are not stored
        kd.insert(POINT1);
        Assert.assertEquals(2, kd.size());
    }

    /**
     * Test method for {@link KdTree#contains(Point2D)}.
     */
    @Test
    public final void testContains() {
        kd.insert(POINT1);
        Assert.assertTrue(kd.contains(POINT1));
    }

    /**
     * Test method for {@link KdTree#range(RectHV)}.
     */
    @Test
    public final void testRange() {
        kd.insert(POINT1);
        kd.insert(POINT2);

        final RectHV rect = new RectHV(0.1, 0.1, 1.0, 1.0);
        int count = 0;

        for (Point2D p : kd.range(rect)) {
            count++;
        }

        Assert.assertEquals(2, count);
    }

    /**
     * Test method for {@link KdTree#nearest(Point2D)}.
     */
    @Test
    public final void testNearest() {
        kd.insert(POINT1);
        kd.insert(POINT2);
        Assert.assertEquals(POINT2, kd.nearest(new Point2D(0.3, 0.3)));
    }

}
