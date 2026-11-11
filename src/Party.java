/**
 * A dining party waiting to be seated, identified by a name and
 * a party size.
 */
public class Party {
    private final String name;
    private final int size;

    /**
     * Creates a party with the given name and size.
     *
     * @param name the name used to identify this party; must not be {@code null}
     * @param size the number of people in the party
     * @throws IllegalArgumentException if {@code name} is {@code null}
     */
    public Party(String name, int size) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null.");
        }
        this.name = name;
        this.size = size;
    }

    /**
     * Returns the name used to identify this party.
     *
     * @return the party name; never {@code null}
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the number of people in this party.
     *
     * @return the party size
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns a string representation of this party, including its name and size.
     *
     * @return a string in the form {@code Party [name=..., size=...]}
     */
    @Override
    public String toString() {
        return "Party [name=" + name + ", size=" + size + "]";
    }

    /**
     * Returns whether this party is equal to the given object.
     * Two parties are equal when they have the same name and size.
     *
     * @param obj the object to compare with this party
     * @return {@code true} if {@code obj} is a {@code Party} with the same name
     *         and size; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Party other = (Party) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (size != other.size)
            return false;
        return true;
    }

    

}
