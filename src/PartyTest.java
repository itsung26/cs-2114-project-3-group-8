import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Party}.
 */
public class PartyTest {
    /**
     * Verifies that {@link Party#getName()} returns the name supplied to the
     * constructor.
     */
    @Test
    void testGetName() {
        Party party = new Party("Smith", 4);
        assertEquals("Smith", party.getName());
    }

    /**
     * Verifies that {@link Party#getSize()} returns the size supplied to the
     * constructor.
     */
    @Test
    void testGetSize() {
        Party party = new Party("Smith", 4);
        assertEquals(4, party.getSize());
    }

    /**
     * Verifies that {@link Party#toString()} includes the party's name and size.
     */
    @Test
    void testToString() {
        Party party = new Party("Smith", 4);
        assertEquals("Party [name=Smith, size=4]", party.toString());
    }

    /**
     * Verifies that constructing a {@link Party} with a {@code null} name throws
     * {@link IllegalArgumentException}.
     */
    @Test
    void testNullNameThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Party(null, 2));
    }

    /**
     * Verifies that parties with the same name and size are equal, including
     * equality with the same instance.
     */
    @Test
    void testDoesEquals() {
        Party party = new Party("Smith", 4);
        Party same = new Party("Smith", 4);

        assertTrue(party.equals(party));
        assertTrue(party.equals(same));
    }

    /**
     * Verifies that parties with a different name or size are not equal, and that
     * a party is not equal to {@code null} or a non-{@link Party} object.
     */
    @Test
    void testDoesNotEquals() {
        Party party = new Party("Smith", 4);
        Party differentName = new Party("Jones", 4);
        Party differentSize = new Party("Smith", 2);

        assertFalse(party.equals(differentName));
        assertFalse(party.equals(differentSize));
        assertFalse(party.equals(null));
        assertFalse(party.equals("Smith"));
    }
}
