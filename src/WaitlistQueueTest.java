import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link WaitlistQueue}.
 */
public class WaitlistQueueTest {
    private WaitlistQueue<String> queue;

    /**
     * Creates a new empty queue before each test.
     */
    @BeforeEach
    void setUp() {
        queue = new WaitlistQueue<String>();
    }

    /**
     * Verifies that a new queue is empty and has a size of zero.
     */
    @Test
    void testNewQueueIsEmpty() {
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.getSize());
    }

    /**
     * Verifies that {@code enqueue} adds items and increases the size
     * each time.
     */
    @Test
    void testEnqueue() {
        queue.enqueue("A");
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.getSize());

        queue.enqueue("B");
        queue.enqueue("C");
        assertEquals(3, queue.getSize());
    }

    /**
     * Verifies that {@link WaitlistQueue#dequeue()} removes items in the
     * same order they were added.
     */
    @Test
    void testDequeueOrder() {
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        assertEquals("A", queue.dequeue());
        assertEquals("B", queue.dequeue());
        assertEquals("C", queue.dequeue());
    }

    /**
     * Verifies that {@link WaitlistQueue#dequeue()} decreases the size and
     * leaves the queue empty once the last item is removed.
     */
    @Test
    void testDequeueUpdatesSize() {
        queue.enqueue("A");
        queue.enqueue("B");

        queue.dequeue();
        assertEquals(1, queue.getSize());
        assertFalse(queue.isEmpty());

        queue.dequeue();
        assertEquals(0, queue.getSize());
        assertTrue(queue.isEmpty());
    }

    /**
     * Verifies that {@link WaitlistQueue#dequeue()} on an empty queue throws
     * {@link IllegalStateException}.
     */
    @Test
    void testDequeueEmptyThrows() {
        assertThrows(IllegalStateException.class, () -> queue.dequeue());
    }

    /**
     * Verifies that the queue still works when items are added again after
     * it has been emptied.
     */
    @Test
    void testEnqueueAfterEmptying() {
        queue.enqueue("A");
        queue.dequeue();

        queue.enqueue("B");
        queue.enqueue("C");
        assertEquals(2, queue.getSize());
        assertEquals("B", queue.dequeue());
        assertEquals("C", queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    /**
     * Verifies that {@link WaitlistQueue#getFront()} returns the first item
     * added without removing it.
     */
    @Test
    void testGetFront() {
        queue.enqueue("A");
        queue.enqueue("B");

        assertEquals("A", queue.getFront());
        assertEquals("A", queue.getFront());
        assertEquals(2, queue.getSize());
    }

    /**
     * Verifies that {@link WaitlistQueue#getFront()} returns the next item
     * in line after the front item is removed.
     */
    @Test
    void testGetFrontAfterDequeue() {
        queue.enqueue("A");
        queue.enqueue("B");
        queue.dequeue();

        assertEquals("B", queue.getFront());
    }

    /**
     * Verifies that {@link WaitlistQueue#getFront()} on an empty queue throws
     * {@link IllegalStateException}.
     */
    @Test
    void testGetFrontEmptyThrows() {
        assertThrows(IllegalStateException.class, () -> queue.getFront());
    }

    /**
     * Verifies that {@link WaitlistQueue#clear()} removes every item.
     */
    @Test
    void testClear() {
        queue.enqueue("A");
        queue.enqueue("B");
        queue.clear();

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.getSize());
        assertThrows(IllegalStateException.class, () -> queue.dequeue());
    }

    /**
     * Verifies that the queue still works when items are added again after
     * it has been cleared.
     */
    @Test
    void testEnqueueAfterClear() {
        queue.enqueue("A");
        queue.clear();

        queue.enqueue("B");
        assertEquals(1, queue.getSize());
        assertEquals("B", queue.getFront());
        assertEquals("B", queue.dequeue());
    }

    /**
     * Verifies that {@link WaitlistQueue#toString()} returns empty brackets
     * for an empty queue.
     */
    @Test
    void testToStringEmpty() {
        assertEquals("[]", queue.toString());
    }

    /**
     * Verifies that {@link WaitlistQueue#toString()} lists the items from
     * front to rear, separated by commas.
     */
    @Test
    void testToString() {
        queue.enqueue("A");
        assertEquals("[A]", queue.toString());

        queue.enqueue("B");
        queue.enqueue("C");
        assertEquals("[A, B, C]", queue.toString());
    }

    /**
     * Verifies that {@link WaitlistQueue#toArray()} returns a zero-length
     * array when the queue is empty.
     */
    @Test
    void testToArrayEmpty() {
        Object[] result = queue.toArray();
        assertEquals(0, result.length);
    }

    /**
     * Verifies that {@link WaitlistQueue#toArray()} returns the items from
     * front to rear without changing the queue.
     */
    @Test
    void testToArray() {
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        Object[] result = queue.toArray();
        assertEquals(3, result.length);
        assertEquals("A", result[0]);
        assertEquals("B", result[1]);
        assertEquals("C", result[2]);

        assertEquals(3, queue.getSize());
        assertEquals("A", queue.getFront());
    }

    /**
     * Verifies that the queue works with {@link Party} objects, the type
     * the restaurant waitlist holds.
     */
    @Test
    void testQueueOfParties() {
        WaitlistQueue<Party> waitlist = new WaitlistQueue<Party>();
        Party smith = new Party("Smith", 4);
        Party jones = new Party("Jones", 2);

        waitlist.enqueue(smith);
        waitlist.enqueue(jones);

        assertEquals(smith, waitlist.dequeue());
        assertEquals(jones, waitlist.getFront());
        assertEquals(1, waitlist.getSize());
    }
}
