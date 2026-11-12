
/**
 * Represents the model - a restaurant with a name, queue of parties,
 * and array of tables. Will eventually be fully serializable in the future to
 * allow for save/load capability. Will also be swappable at runtime in the future.
 */
public class Restaurant {
    private final int DEFAULT_TABLE_COUNT = 12;
    private final int DEFAULT_CAPACITY_PER_TABLE = 4;

    private final String restaurantName;
    private final WaitlistQueue<Party> waitlistQueue;
    private final Table[] tables;

    public Restaurant(String name) {
        waitlistQueue = new WaitlistQueue<Party>();
        tables = new Table[DEFAULT_TABLE_COUNT];
        // Set name to "" if name is null. Otherwise use name.
        restaurantName = name == null ? "" : name;

        // Populate tables array.
        // Table numbers start at 1.
        for (int i = 0; i < tables.length; i++) {
            tables[i] = new Table(i + 1, DEFAULT_CAPACITY_PER_TABLE, false);
        }
    }

    public Table[] getTables() {
        return tables;
    }

    public void addPartyToWaitlist(Party party) {
        if (party == null) {
            throw new IllegalArgumentException();
        }
        waitlistQueue.enqueue(party);
    }

    public void seatNextParty() {
        // TODO: Seat the next party in the waitlist.
    }

    public Party[] getWaitlistSnapshot() {
        Object[] waitlist = waitlistQueue.toArray();
        Party[] snapshot = new Party[waitlist.length];
        for (int i = 0; i < waitlist.length; i++) {
            snapshot[i] = (Party) waitlist[i];
        }
        return snapshot;
    }

}
