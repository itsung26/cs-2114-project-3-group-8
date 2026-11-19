import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Table}.
 */
public class TableTest {
    /**
     * Verifies that tables with the same number, capacity, and seated party are
     * equal, including equality with the same instance and two vacant tables.
     */
    @Test
    void testEquals() {
        Party party = new Party("Smith", 4);
        Table table = new Table(1, 4, party);
        Table same = new Table(1, 4, new Party("Smith", 4));
        Table vacant = new Table(2, 4);
        Table alsoVacant = new Table(2, 4);

        assertTrue(table.equals(table));
        assertTrue(table.equals(same));
        assertTrue(vacant.equals(alsoVacant));
    }

    /**
     * Verifies that tables that differ in number, capacity, or seated party are
     * not equal, and that a table is not equal to {@code null} or a
     * non-{@link Table} object.
     */
    @Test
    void testDoesNotEquals() {
        Party party = new Party("Smith", 4);
        Table table = new Table(1, 4, party);
        Table differentNumber = new Table(2, 4, party);
        Table differentCapacity = new Table(1, 6, party);
        Table differentParty = new Table(1, 4, new Party("Jones", 4));
        Table vacant = new Table(1, 4);

        assertFalse(table.equals(differentNumber));
        assertFalse(table.equals(differentCapacity));
        assertFalse(table.equals(differentParty));
        assertFalse(table.equals(vacant));
        assertFalse(vacant.equals(table));
        assertFalse(table.equals(null));
        assertFalse(table.equals("Table 1"));
    }

    /**
     * Verifies that {@link Table#getCapacity()} returns the capacity supplied to
     * the constructor.
     */
    @Test
    void testGetCapacity() {
        Table table = new Table(1, 4);
        assertEquals(4, table.getCapacity());
    }

    /**
     * Verifies that {@link Table#getSeatedParty()} returns the seated party
     * supplied to the constructor, or {@code null} when the table is vacant.
     */
    @Test
    void testGetSeatedParty() {
        Party party = new Party("Smith", 4);
        Table occupied = new Table(1, 4, party);
        Table vacant = new Table(2, 4);

        assertEquals(party, occupied.getSeatedParty());
        assertNull(vacant.getSeatedParty());
    }

    /**
     * Verifies that {@link Table#getTableNumber()} returns the table number
     * supplied to the constructor.
     */
    @Test
    void testGetTableNumber() {
        Table table = new Table(1, 4);
        assertEquals(1, table.getTableNumber());
    }

    /**
     * Verifies that {@link Table#isOccupied()} returns {@code true} when a party
     * is seated and {@code false} when the table is vacant.
     */
    @Test
    void testIsOccupied() {
        Party party = new Party("Smith", 4);
        Table occupied = new Table(1, 4, party);
        Table vacant = new Table(2, 4);

        assertTrue(occupied.isOccupied());
        assertFalse(vacant.isOccupied());
    }

    /**
     * Verifies that {@link Table#setSeatedParty(Party)} seats a party and can
     * clear the table by setting the seated party to {@code null}.
     */
    @Test
    void testSetSeatedParty() {
        Party party = new Party("Smith", 4);
        Table table = new Table(1, 4);

        assertFalse(table.isOccupied());
        table.setSeatedParty(party);
        assertEquals(party, table.getSeatedParty());
        assertTrue(table.isOccupied());

        table.setSeatedParty(null);
        assertNull(table.getSeatedParty());
        assertFalse(table.isOccupied());
    }

    /**
     * Verifies that {@link Table#toString()} includes the table number, capacity,
     * and seated party.
     */
    @Test
    void testToString() {
        Party party = new Party("Smith", 4);
        Table occupied = new Table(1, 4, party);
        Table vacant = new Table(2, 4);

        assertEquals(
                "Table [tableNumber=1, capacity=4, seatedParty=Party [name=Smith, size=4]]",
                occupied.toString());
        assertEquals(
                "Table [tableNumber=2, capacity=4, seatedParty=null]",
                vacant.toString());
    }
}
