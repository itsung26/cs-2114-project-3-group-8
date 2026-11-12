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
     */
    public void enqueue(T item) {
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
     * @throws IllegalStateException if the queue is empty
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty.");
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
    public int getSize() {
        return size;
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
