/**
 * A restaurant table identified by a number and seating capacity,
 * with a flag for whether it is currently occupied.
 */
public class Table {
    private final int tableNumber;
    private final int capacity;
    private boolean isOccupied;

    /**
     * Creates a table with the given number, capacity, and occupancy state.
     *
     * @param tableNumber the number used to identify this table
     * @param capacity the maximum number of guests this table can seat
     * @param isOccupied {@code true} if the table is currently occupied;
     *                   {@code false} otherwise
     */
    public Table(int tableNumber, int capacity, boolean isOccupied) {
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.isOccupied = isOccupied;
    }

    /**
     * Returns the number used to identify this table.
     *
     * @return the table number
     */
    public int getTableNumber() {
        return tableNumber;
    }

    /**
     * Returns the maximum number of guests this table can seat.
     *
     * @return the table capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Returns whether this table is currently occupied.
     *
     * @return {@code true} if the table is occupied; {@code false} otherwise
     */
    public boolean isOccupied() {
        return isOccupied;
    }

    /**
     * Sets whether this table is currently occupied.
     *
     * @param isOccupied {@code true} if the table is occupied;
     *                   {@code false} otherwise
     */
    public void setOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    /**
     * Returns whether this table is equal to the given object.
     * Two tables are equal when they have the same number, capacity,
     * and occupancy state.
     *
     * @param obj the object to compare with this table
     * @return {@code true} if {@code obj} is a {@code Table} with the same
     *         number, capacity, and occupancy; {@code false} otherwise
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
        if (isOccupied != other.isOccupied)
            return false;
        return true;
    }

    /**
     * Returns a string representation of this table, including its number,
     * capacity, and occupancy state.
     *
     * @return a string in the form
     *         {@code Table [tableNumber=..., capacity=..., isOccupied=...]}
     */
    @Override
    public String toString() {
        return "Table [tableNumber=" + tableNumber + ", capacity=" + capacity + ", isOccupied=" + isOccupied + "]";
    }
}
