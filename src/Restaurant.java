public class Restaurant {
    private final int DEFAULT_TABLE_COUNT = 12;
    private final int DEFAULT_CAPACITY_PER_TABLE = 4;

    private final Queue<Party> waitlistQueue;
    private final Table[] tables;

    public Restaurant() {
        waitlistQueue = new Queue<Party>();
        tables = new Table[DEFAULT_TABLE_COUNT];

        // Populate tables array.
        // Table numbers start at 1.
        for (int i = 0; i < tables.length; i++) {
            tables[i] = new Table(i + 1, DEFAULT_CAPACITY_PER_TABLE, false);
        }
    }

    public void addPartyToWaitlist(Party party) {
        // TODO: Enqueue a party.
    }

    public void seatNextParty() {
        // TODO: Seat the next party in the waitlist.
    }
}
