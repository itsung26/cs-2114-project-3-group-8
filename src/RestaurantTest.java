import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.naming.OperationNotSupportedException;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Restaurant}.
 */
public class RestaurantTest {
    /**
     * Verifies that {@link Restaurant#addPartyToWaitlist(Party)} enqueues a party
     * at the end of the waitlist, and that a {@code null} party throws
     * {@link IllegalArgumentException}.
     */
    @Test
    void testAddPartyToWaitlist() {
        Restaurant restaurant = new Restaurant("Test");
        Party first = new Party("Smith", 4);
        Party second = new Party("Jones", 2);

        restaurant.addPartyToWaitlist(first);
        restaurant.addPartyToWaitlist(second);

        Party[] snapshot = restaurant.getWaitlistSnapshot();
        assertEquals(2, snapshot.length);
        assertEquals(first, snapshot[0]);
        assertEquals(second, snapshot[1]);

        assertThrows(IllegalArgumentException.class, () -> restaurant.addPartyToWaitlist(null));
    }

    /**
     * Verifies that {@link Restaurant#getTables()} returns the default set of
     * twelve vacant tables numbered starting at 1, each with capacity 4.
     */
    @Test
    void testGetTables() {
        Restaurant restaurant = new Restaurant("Test");
        Table[] tables = restaurant.getTables();

        assertEquals(12, tables.length);
        for (int i = 0; i < tables.length; i++) {
            assertEquals(i + 1, tables[i].getTableNumber());
            assertEquals(4, tables[i].getCapacity());
            assertFalse(tables[i].isOccupied());
        }
    }

    /**
     * Verifies that {@link Restaurant#getWaitTimeEstimate(int)} returns
     * {@code 0} when a vacant table can fit the party, the seated party's
     * dining-time estimate when only occupied tables can fit, and the
     * dining-time estimate for {@code partySize} when no table can fit.
     */
    @Test
    void testGetWaitTimeEstimate() {
        Restaurant restaurant = new Restaurant("Test");

        assertEquals(0, restaurant.getWaitTimeEstimate(4));

        for (Table table : restaurant.getTables()) {
            table.setSeatedParty(new Party("Seated", 4));
        }
        assertEquals(60, restaurant.getWaitTimeEstimate(4));

        assertEquals(75, restaurant.getWaitTimeEstimate(5));
    }

    /**
     * Verifies that {@link Restaurant#getWaitlistSnapshot()} returns parties in
     * front-to-rear order, and that mutating the returned array does not change
     * the waitlist.
     */
    @Test
    void testGetWaitlistSnapshot() {
        Restaurant restaurant = new Restaurant("Test");
        assertEquals(0, restaurant.getWaitlistSnapshot().length);

        Party first = new Party("Smith", 4);
        Party second = new Party("Jones", 2);
        restaurant.addPartyToWaitlist(first);
        restaurant.addPartyToWaitlist(second);

        Party[] snapshot = restaurant.getWaitlistSnapshot();
        assertEquals(2, snapshot.length);
        assertEquals(first, snapshot[0]);
        assertEquals(second, snapshot[1]);

        snapshot[0] = new Party("Changed", 1);
        Party[] unchanged = restaurant.getWaitlistSnapshot();
        assertEquals(first, unchanged[0]);
        assertEquals(second, unchanged[1]);
    }

    /**
     * Verifies that {@link Restaurant#seatNextParty()} seats the front waitlist
     * party at a fitting vacant table and removes them from the waitlist, and
     * that seating fails with {@link OperationNotSupportedException} when the
     * waitlist is empty or no table can fit the party.
     */
    @Test
    void testSeatNextParty() throws OperationNotSupportedException {
        Restaurant restaurant = new Restaurant("Test");
        Party party = new Party("Smith", 4);
        restaurant.addPartyToWaitlist(party);

        restaurant.seatNextParty();

        assertEquals(0, restaurant.getWaitlistSnapshot().length);
        assertTrue(restaurant.getTables()[0].isOccupied());
        assertEquals(party, restaurant.getTables()[0].getSeatedParty());

        assertThrows(OperationNotSupportedException.class, () -> restaurant.seatNextParty());

        restaurant.addPartyToWaitlist(new Party("Huge", 10));
        assertThrows(OperationNotSupportedException.class, () -> restaurant.seatNextParty());
        assertEquals(1, restaurant.getWaitlistSnapshot().length);
    }
}
