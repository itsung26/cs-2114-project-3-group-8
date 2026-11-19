import java.util.Arrays;

/**
 * Represents the model - a restaurant with a name, queue of parties,
 * and array of tables. Will eventually be fully serializable in the future to
 * allow for save/load capability. Will also be swappable at runtime in the
 * future.
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
            tables[i] = new Table(i + 1, DEFAULT_CAPACITY_PER_TABLE);
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

    /**
     * Finds the best fit table for the next party and seats them. Returns true if
     * seating was successful and false otherwise.
     * 
     * @return
     */
    public boolean seatNextParty() {
        // Only modifies the queue when neccessary.
        Party peeked = waitlistQueue.getFront();

        // First iteration: check for perfectly matching table size to party size.
        for (Table table : tables) {
            if (!table.isOccupied() && table.getCapacity() == peeked.getSize()) {
                table.setSeatedParty(waitlistQueue.dequeue());
                return true;
            }
        }

        // Second iteration: check for table with smallest capacity that can still fit
        // the party.
        Table[] sortedCopy = sortByTableCapacity(tables);
        for (Table table : sortedCopy) {
            if (!table.isOccupied() && table.getCapacity() > peeked.getSize()) {
                table.setSeatedParty(waitlistQueue.dequeue());
                return true;
            }
        }
        
        return false;
    }

    public Party[] getWaitlistSnapshot() {
        Object[] waitlist = waitlistQueue.toArray();
        Party[] snapshot = new Party[waitlist.length];
        for (int i = 0; i < waitlist.length; i++) {
            snapshot[i] = (Party) waitlist[i];
        }
        return snapshot;
    }

    /**
     * Selection sort that returns a new array of the given tables sorted by
     * capacity from least to greatest. The original array is left unchanged.
     *
     * @param tables the tables to sort; must not be {@code null}
     * @return a new array containing the same tables ordered by ascending capacity
     */
    private static Table[] sortByTableCapacity(Table[] tables) {
        Table[] sorted = Arrays.copyOf(tables, tables.length);
        for (int i = 0; i < sorted.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < sorted.length; j++) {
                if (sorted[j].getCapacity() < sorted[minIndex].getCapacity()) {
                    minIndex = j;
                }
            }
            Table temp = sorted[i];
            sorted[i] = sorted[minIndex];
            sorted[minIndex] = temp;
        }
        return sorted;
    }

}
