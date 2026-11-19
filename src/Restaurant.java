import java.rmi.server.Operation;
import java.util.Arrays;

import javax.naming.OperationNotSupportedException;

/**
 * The restaurant model: a named collection of tables and a waitlist of
 * {@link Party} parties. Defaults to 12 tables of capacity 4 each.
 * Intended to become fully serializable for save/load and swappable at
 * runtime in the future.
 */
public class Restaurant {
    private final int DEFAULT_TABLE_COUNT = 12;
    private final int DEFAULT_CAPACITY_PER_TABLE = 4;

    private final String restaurantName;
    private final WaitlistQueue<Party> waitlistQueue;
    private final Table[] tables;

    /**
     * Creates a restaurant with the given name, an empty waitlist, and
     * the default set of vacant tables numbered starting at 1.
     *
     * @param name the restaurant name, or {@code null} to use an empty string
     */
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

    /**
     * Returns the tables in this restaurant.
     *
     * @return the live table array; callers that mutate it affect this restaurant
     */
    public Table[] getTables() {
        return tables;
    }

    /**
     * Adds a party to the end of the waitlist.
     *
     * @param party the party to enqueue; must not be {@code null}
     * @throws IllegalArgumentException if {@code party} is {@code null}
     */
    public void addPartyToWaitlist(Party party) {
        if (party == null) {
            throw new IllegalArgumentException();
        }
        waitlistQueue.enqueue(party);
    }

    /**
     * Seats the next party from the front of the waitlist at the best-fit
     * vacant table. Prefers an exact capacity match; otherwise uses the
     * smallest vacant table whose capacity still exceeds the party size.
     * The waitlist is unchanged if seating fails.
     *
     * @throws OperationNotSupportedException if the waitlist is empty, or if no
     *                                        vacant table can fit the next party
     */
    public void seatNextParty() throws OperationNotSupportedException {
        if (waitlistQueue.size() < 1) {
            throw new OperationNotSupportedException("Cannot seat next party: waitlist is empty.");
        }

        // Only modifies the queue when neccessary.
        Party peeked = waitlistQueue.getFront();
        boolean seated = false;

        // First iteration: check for perfectly matching table size to party size.
        for (Table table : tables) {
            if (!table.isOccupied() && table.getCapacity() == peeked.getSize()) {
                table.setSeatedParty(waitlistQueue.dequeue());
                seated = true;
                break;
            }
        }

        // Second iteration: check for table with smallest capacity that can still fit
        // the party.
        if (!seated) {
            Table[] sortedCopy = sortByTableCapacity(tables);
            for (Table table : sortedCopy) {
                if (!table.isOccupied() && table.getCapacity() > peeked.getSize()) {
                    table.setSeatedParty(waitlistQueue.dequeue());
                    seated = true;
                    break;
                }
            }
        }

        if (!seated) {
            throw new OperationNotSupportedException(
                    "Cannot seat next party: no available table can fit the party.");
        }
    }

    /**
     * Returns a snapshot of the parties currently on the waitlist, in
     * front-to-rear order. The returned array is a copy; mutating it does
     * not affect the waitlist.
     *
     * @return a new array of parties on the waitlist; empty if none are waiting
     */
    public Party[] getWaitlistSnapshot() {
        Object[] waitlist = waitlistQueue.toArray();
        Party[] snapshot = new Party[waitlist.length];
        for (int i = 0; i < waitlist.length; i++) {
            snapshot[i] = (Party) waitlist[i];
        }
        return snapshot;
    }

    /**
     * Returns an estimate of wait time in minutes for a party of the given size.
     * If any vacant table can fit the party, the wait is {@code 0}. Otherwise the
     * wait is the shortest estimated remaining dining time among occupied tables
     * that can fit the party, using {@link #getDiningTimeEstimate(int)} for the
     * seated party's size. If no table can fit the party at all, returns the
     * dining-time estimate for {@code partySize} itself.
     *
     * @param partySize the number of people in the party
     * @return the estimated wait time in minutes
     */
    public int getWaitTimeEstimate(int partySize) {
        for (Table table : tables) {
            if (!table.isOccupied() && table.getCapacity() >= partySize) {
                return 0;
            }
        }

        int minWait = Integer.MAX_VALUE;
        boolean foundFittingTable = false;
        for (Table table : tables) {
            if (table.isOccupied() && table.getCapacity() >= partySize) {
                int diningTime = getDiningTimeEstimate(table.getSeatedParty().getSize());
                if (diningTime < minWait) {
                    minWait = diningTime;
                }
                foundFittingTable = true;
            }
        }

        if (!foundFittingTable) {
            return getDiningTimeEstimate(partySize);
        }
        return minWait;
    }

    /**
     * Returns the estimated dining time in minutes for a party of the given size.
     * Sizes below 1 use the lowest estimate; sizes above 8 use the highest
     * estimate.
     *
     * @param partySize the number of people in the party
     * @return the estimated dining time in minutes
     */
    private static int getDiningTimeEstimate(int partySize) {
        switch (partySize) {
            case 1:
                return 35;
            case 2:
                return 45;
            case 3:
                return 55;
            case 4:
                return 60;
            case 5:
                return 75;
            case 6:
                return 90;
            case 7:
                return 93;
            case 8:
                return 95;
            default:
                if (partySize < 1) {
                    return 35;
                }
                return 95;
        }
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
