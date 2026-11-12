import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ViewTest {
    View testView;
    ByteArrayOutputStream redirectedOutputStream;

    @BeforeEach
    void setup() {
        testView = new View();
        redirectedOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(redirectedOutputStream));
    }

    @Test
    void testDisplayWaitlist() {
    }

    @Test
    void testDisplay() {
        testView.display("Hello World.");
        // Compare the expected bytes with the printed bytes.
        assertEquals("\033[H\033[2JHello World." + System.lineSeparator(), redirectedOutputStream.toString());
    }
}
