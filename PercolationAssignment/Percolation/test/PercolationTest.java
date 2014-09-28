import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author esloho
 *
 */
public class PercolationTest {

    private static final int SIZE = 5;
    private Percolation percolation;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Test method for {@link Percolation#Percolation(int)}.
     */
    @Test
    public void testConstructorNotNull() {
        percolation = new Percolation(SIZE);

        Assert.assertNotNull(percolation);
    }

    @Test
    public void testConstructorClosed() {
        percolation = new Percolation(SIZE);

        for (int i = 1; i < SIZE; i++) {
            for (int j = 1; j < SIZE; j++) {
                Assert.assertFalse(percolation.isOpen(i, j));
            }
        }
    }

    /**
     * Test method for {@link Percolation#open(int, int)}.
     */
    @Test
    public void testOpen() {
        Assert.fail("Not yet implemented");
    }

    /**
     * Test method for {@link Percolation#isOpen(int, int)}.
     */
    @Test
    public void testIsOpen() {
        percolation = new Percolation(SIZE);

        Assert.assertFalse(percolation.isOpen(SIZE, SIZE));

        percolation.open(SIZE, SIZE);

        Assert.assertTrue(percolation.isOpen(SIZE, SIZE));

        percolation.open(SIZE, SIZE);

        Assert.assertTrue(percolation.isOpen(SIZE, SIZE));
    }

    /**
     * Test method for {@link Percolation#isFull(int, int)}.
     */
    @Test
    public void testIsFull() {
        Assert.fail("Not yet implemented");
    }

    /**
     * Test method for {@link Percolation#percolates()}.
     */
    @Test
    public void testPercolates() {
        Assert.fail("Not yet implemented");
    }

}
