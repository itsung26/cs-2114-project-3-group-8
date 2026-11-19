/**
 * A restaurant table identified by a number and seating capacity,
 * optionally occupied by a seated {@link Party}.
 */
public class Table {
    private final int tableNumber;
    private final int capacity;
    private Party seatedParty;

    /**
     * Creates a table with the given number, capacity, and seated party.
     *
     * @param tableNumber the unique identifier for this table
     * @param capacity the maximum number of people this table can seat
     * @param seatedParty the party currently seated at this table, or {@code null}
     *                    if the table is vacant
     */
    public Table(int tableNumber, int capacity, Party seatedParty) {
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.seatedParty = seatedParty;
    }

    /**
     * Creates a vacant table with the given number and capacity.
     *
     * @param tableNumber the unique identifier for this table
     * @param capacity the maximum number of people this table can seat
     */
    public Table(int tableNumber, int capacity) {
        this(tableNumber, capacity, null);
    }

    /**
     * Returns the unique identifier for this table.
     *
     * @return the table number
     */
    public int getTableNumber() {
        return tableNumber;
    }

    /**
     * Returns the maximum number of people this table can seat.
     *
     * @return the seating capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Returns whether a party is currently seated at this table.
     *
     * @return {@code true} if a party is seated; {@code false} if the table is vacant
     */
    public boolean isOccupied() {
        return seatedParty != null;
    }

    /**
     * Returns the party currently seated at this table.
     *
     * @return the seated party, or {@code null} if the table is vacant
     */
    public Party getSeatedParty() {
        return seatedParty;
    }

    /**
     * Sets the party seated at this table.
     *
     * @param seatedParty the party to seat, or {@code null} to mark the table vacant
     */
    public void setSeatedParty(Party seatedParty) {
        this.seatedParty = seatedParty;
    }

    /**
     * Returns whether this table is equal to the given object.
     * Two tables are equal when they have the same table number, capacity,
     * and seated party (including both being vacant).
     *
     * @param obj the object to compare with this table
     * @return {@code true} if {@code obj} is a {@code Table} with the same
     *         table number, capacity, and seated party; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Table other = (Table) obj;
        if (tableNumber != other.tableNumber)
            return false;
        if (capacity != other.capacity)
            return false;
        if (seatedParty == null) {
            if (other.seatedParty != null)
                return false;
        } else if (!seatedParty.equals(other.seatedParty))
            return false;
        return true;
    }

    /**
     * Returns a string representation of this table, including its number,
     * capacity, and seated party.
     *
     * @return a string in the form
     *         {@code Table [tableNumber=..., capacity=..., seatedParty=...]}
     */
    @Override
    public String toString() {
        return "Table [tableNumber=" + tableNumber + ", capacity=" + capacity + ", seatedParty=" + seatedParty + "]";
    }
}
