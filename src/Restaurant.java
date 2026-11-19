import java.rmi.server.Operation;
import java.util.Arrays;

import javax.naming.OperationNotSupportedException;

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
     * seated party's size.
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
