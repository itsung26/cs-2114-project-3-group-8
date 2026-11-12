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
}
