import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;

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
     * {@link NoSuchElementException}.
     */
    @Test
    void testDequeueEmptyThrows() {
        assertThrows(NoSuchElementException.class, () -> queue.dequeue());
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
     * {@link NoSuchElementException}.
     */
    @Test
    void testGetFrontEmptyThrows() {
        assertThrows(NoSuchElementException.class, () -> queue.getFront());
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
        assertThrows(NoSuchElementException.class, () -> queue.dequeue());
    }

    /**
     * Verifies that {@link WaitlistQueue#clear()} on an empty queue does
     * not throw and leaves the queue empty.
     */
    @Test
    void testClearEmptyQueue() {
        queue.clear();

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertEquals("[]", queue.toString());
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
     * Verifies that enqueueing {@code null} throws
     * {@link IllegalArgumentException} and does not change the queue.
     */
    @Test
    void testEnqueueNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> queue.enqueue(null));
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());

        queue.enqueue("A");
        assertThrows(IllegalArgumentException.class, () -> queue.enqueue(null));
        assertEquals(1, queue.size());
        assertEquals("A", queue.peek());
    }

    /**
     * Verifies that {@link WaitlistQueue#dequeue()} throws
     * {@link NoSuchElementException} once the last item has been removed.
     */
    @Test
    void testDequeueAfterLastItemThrows() {
        queue.enqueue("A");
        assertEquals("A", queue.dequeue());

        assertThrows(NoSuchElementException.class, () -> queue.dequeue());
    }

    /**
     * Verifies that {@link WaitlistQueue#peek()} returns the front item
     * without removing it.
     */
    @Test
    void testPeek() {
        queue.enqueue("A");
        queue.enqueue("B");

        assertEquals("A", queue.peek());
        assertEquals("A", queue.peek());
        assertEquals(2, queue.size());
        assertEquals("A", queue.dequeue());
        assertEquals("B", queue.peek());
    }

    /**
     * Verifies that {@link WaitlistQueue#peek()} on an empty queue throws
     * {@link NoSuchElementException}, including after the last item is
     * removed.
     */
    @Test
    void testPeekEmptyThrows() {
        assertThrows(NoSuchElementException.class, () -> queue.peek());

        queue.enqueue("A");
        queue.dequeue();
        assertThrows(NoSuchElementException.class, () -> queue.peek());
    }

    /**
     * Verifies that {@link WaitlistQueue#isEmpty()} is true before any
     * items are added, false while items are in the queue, and true again
     * after they are all removed.
     */
    @Test
    void testIsEmptyBeforeAndAfter() {
        assertTrue(queue.isEmpty());

        queue.enqueue("A");
        queue.enqueue("B");
        assertFalse(queue.isEmpty());

        queue.dequeue();
        assertFalse(queue.isEmpty());

        queue.dequeue();
        assertTrue(queue.isEmpty());
    }

    /**
     * Verifies that {@link WaitlistQueue#size()} is correct after each
     * enqueue and dequeue.
     */
    @Test
    void testSize() {
        assertEquals(0, queue.size());

        queue.enqueue("A");
        assertEquals(1, queue.size());
        queue.enqueue("B");
        assertEquals(2, queue.size());
        queue.enqueue("C");
        assertEquals(3, queue.size());

        queue.dequeue();
        assertEquals(2, queue.size());
        queue.dequeue();
        assertEquals(1, queue.size());
        queue.dequeue();
        assertEquals(0, queue.size());
    }

    /**
     * Verifies that a failed dequeue on an empty queue does not change
     * the size.
     */
    @Test
    void testSizeAfterFailedDequeue() {
        assertThrows(NoSuchElementException.class, () -> queue.dequeue());
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
    }

    /**
     * Verifies that {@link WaitlistQueue#toList()} returns an empty list
     * for an empty queue.
     */
    @Test
    void testToListEmpty() {
        List<String> result = queue.toList();
        assertEquals(0, result.size());
    }

    /**
     * Verifies that {@link WaitlistQueue#toList()} returns the items from
     * front to rear without changing the queue.
     */
    @Test
    void testToListOrder() {
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        List<String> result = queue.toList();
        assertEquals(3, result.size());
        assertEquals("A", result.get(0));
        assertEquals("B", result.get(1));
        assertEquals("C", result.get(2));

        assertEquals(3, queue.size());
        assertEquals("A", queue.peek());
    }

    /**
     * Verifies that the list from {@link WaitlistQueue#toList()} is
     * independent: changing the list does not change the queue, and
     * changing the queue does not change the list.
     */
    @Test
    void testToListIsIndependent() {
        queue.enqueue("A");
        queue.enqueue("B");

        List<String> result = queue.toList();
        result.clear();
        result.add("Z");

        assertEquals(2, queue.size());
        assertEquals("A", queue.dequeue());
        assertEquals("B", queue.dequeue());

        List<String> second = queue.toList();
        queue.enqueue("C");
        assertEquals(0, second.size());
        assertNotSame(result, second);
    }

    /**
     * Verifies that mixing enqueues and dequeues still removes items in
     * the order they were added.
     */
    @Test
    void testInterleavedEnqueueDequeue() {
        queue.enqueue("A");
        queue.enqueue("B");
        assertEquals("A", queue.dequeue());

        queue.enqueue("C");
        assertEquals("B", queue.dequeue());

        queue.enqueue("D");
        queue.enqueue("E");
        assertEquals("C", queue.dequeue());
        assertEquals("D", queue.peek());
        assertEquals(2, queue.size());

        assertEquals("D", queue.dequeue());
        assertEquals("E", queue.dequeue());
        assertTrue(queue.isEmpty());

        queue.enqueue("F");
        assertEquals("F", queue.peek());
        assertEquals("F", queue.dequeue());
        assertTrue(queue.isEmpty());
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
