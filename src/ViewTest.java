import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ViewTest {
    Controller testApp;
    PrintStream originalOut = System.out;
    ByteArrayOutputStream redirectedOutputStream;

    @BeforeAll
    private void before() {
        originalOut = System.out;
    }

    @BeforeEach
    private void setup() {
        testApp = new Controller();
        System.setOut(new PrintStream(redirectedOutputStream));
    }

    @Test
    private void testDisplayWaitlist() {

    }

    @Test
    private void testPrint() {

    }

    @AfterAll
}
