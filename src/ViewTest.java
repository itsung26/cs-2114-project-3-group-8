import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link View}.
 */
public class ViewTest {
    private View testView;
    private ByteArrayOutputStream redirectedOutputStream;
    private PrintStream originalOut;

    /**
     * Creates a new view and redirects {@code System.out} before each test.
     */
    @BeforeEach
    void setup() {
        testView = new View();
        originalOut = System.out;
        redirectedOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(redirectedOutputStream));
    }

    /**
     * Restores {@code System.out} after each test.
     */
    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    /**
     * Verifies that {@link View#display(String)} prints the given message
     * followed by a line separator.
     */
    @Test
    void testDisplay() {
        testView.display("Hello World.");
        assertEquals("Hello World." + System.lineSeparator(),
            redirectedOutputStream.toString());
    }

    /**
     * Verifies that {@link View#displayWaitlist(Party[])} reports an empty
     * waitlist when given {@code null} or a zero-length array.
     */
    @Test
    void testDisplayWaitlistEmpty() {
        testView.displayWaitlist(null);
        assertEquals("The waitlist is empty." + System.lineSeparator(),
            redirectedOutputStream.toString());

        redirectedOutputStream.reset();
        testView.displayWaitlist(new Party[0]);
        assertEquals("The waitlist is empty." + System.lineSeparator(),
            redirectedOutputStream.toString());
    }

    /**
     * Verifies that {@link View#displayWaitlist(Party[])} lists each party
     * in order with its position, name, and size.
     */
    @Test
    void testDisplayWaitlist() {
        Party[] waitlist = {
            new Party("Smith", 4),
            new Party("Jones", 1)
        };

        testView.displayWaitlist(waitlist);

        String expected = "Current Waitlist:"
            + System.lineSeparator()
            + "1. Smith (4 people)"
            + System.lineSeparator()
            + "2. Jones (1 person)"
            + System.lineSeparator();
        assertEquals(expected, redirectedOutputStream.toString());
    }
}
