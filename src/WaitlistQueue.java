import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A first-in, first-out queue built from singly linked nodes.
 * Items are added at the rear and removed from the front.
 *
 * @param <T> the type of item the queue holds
 */
public class WaitlistQueue<T> {
    private Node<T> front;
    private Node<T> rear;
    private int size;

    /**
     * Creates an empty queue.
     */
    public WaitlistQueue() {
        front = null;
        rear = null;
        size = 0;
    }

    /**
     * Adds an item to the rear of the queue.
     *
     * @param item the item to add
     * @throws IllegalArgumentException if the item is null
     */
    public void enqueue(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }
        Node<T> newNode = new Node<T>(item);
        if (isEmpty()) {
            front = newNode;
        }
        else {
            rear.next = newNode;
        }
        rear = newNode;
        size++;
    }

    /**
     * Removes and returns the item at the front of the queue.
     *
     * @return the item that was at the front
     * @throws NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty.");
        }
        T item = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return item;
    }

    /**
     * Returns the item at the front of the queue without removing it.
     *
     * @return the item at the front
     * @throws NoSuchElementException if the queue is empty
     */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty.");
        }
        return front.data;
    }

    /**
     * Returns the item at the front of the queue without removing it.
     * Same as {@link #peek()}.
     *
     * @return the item at the front
     * @throws NoSuchElementException if the queue is empty
     */
    public T getFront() {
        return peek();
    }

    /**
     * Removes every item from the queue.
     */
    public void clear() {
        front = null;
        rear = null;
        size = 0;
    }

    /**
     * Returns whether the queue has no items in it.
     *
     * @return true if the queue is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of items in the queue.
     *
     * @return the number of items
     */
    public int size() {
        return size;
    }

    /**
     * Returns the number of items in the queue. Same as {@link #size()}.
     *
     * @return the number of items
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns a string listing the items from front to rear.
     *
     * @return a string in the form {@code [first, second, third]}
     */
    @Override
    public String toString() {
        String result = "[";
        Node<T> current = front;
        while (current != null) {
            result = result + current.data;
            if (current.next != null) {
                result = result + ", ";
            }
            current = current.next;
        }
        return result + "]";
    }

    /**
     * Returns a new array containing the items from front to rear.
     *
     * @return an array of the items currently in the queue
     */
    public Object[] toArray() {
        Object[] result = new Object[size];
        Node<T> current = front;
        int index = 0;
        while (current != null) {
            result[index] = current.data;
            index++;
            current = current.next;
        }
        return result;
    }

    /**
     * Returns a new list containing the items from front to rear.
     * Changing the list does not change the queue.
     *
     * @return a list of the items currently in the queue
     */
    public List<T> toList() {
        List<T> result = new ArrayList<T>();
        Node<T> current = front;
        while (current != null) {
            result.add(current.data);
            current = current.next;
        }
        return result;
    }

    /**
     * A single node in the linked chain. Holds one item and a
     * reference to the node behind it in line.
     *
     * @param <T> the type of item the node holds
     */
    private static class Node<T> {
        private T data;
        private Node<T> next;

        /**
         * Creates a node holding the given item with no next node.
         *
         * @param data the item to store
         */
        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
}
