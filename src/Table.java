public class Table {
    private final int tableNumber;
    private final int capacity;
    private Party seatedParty;

    public Table(int tableNumber, int capacity, Party seatedParty) {
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.seatedParty = seatedParty;
    }

    public Table(int tableNumber, int capacity) {
        this(tableNumber, capacity, null);
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isOccupied() {
        return seatedParty != null;
    }

    public Party getSeatedParty() {
        return seatedParty;
    }

    public void setSeatedParty(Party seatedParty) {
        this.seatedParty = seatedParty;
    }

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

    @Override
    public String toString() {
        return "Table [tableNumber=" + tableNumber + ", capacity=" + capacity + ", seatedParty=" + seatedParty + "]";
    }
}
