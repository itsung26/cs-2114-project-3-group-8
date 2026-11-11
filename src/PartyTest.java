import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PartyTest {
    @Test
    void testGetName() {
        Party party = new Party("Smith", 4);
        assertEquals("Smith", party.getName());
    }

    @Test
    void testGetSize() {
        Party party = new Party("Smith", 4);
        assertEquals(4, party.getSize());
    }

    @Test
    void testToString() {
        Party party = new Party("Smith", 4);
        assertEquals("Party [name=Smith, size=4]", party.toString());
    }

    @Test
    void testNullNameThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Party(null, 2));
    }

    @Test
    void testDoesEquals() {
        Party party = new Party("Smith", 4);
        Party same = new Party("Smith", 4);

        assertTrue(party.equals(party));
        assertTrue(party.equals(same));
    }

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
