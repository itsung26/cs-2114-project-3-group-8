public class Restaurant {
    private final int DEFAULT_TABLE_COUNT = 12;
    private final int DEFAULT_CAPACITY_PER_TABLE = 4;

    private final WaitlistQueue<Party> waitlistQueue;
    private final Table[] tables;

    public Restaurant() {
        waitlistQueue = new WaitlistQueue<Party>();
        tables = new Table[DEFAULT_TABLE_COUNT];

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
