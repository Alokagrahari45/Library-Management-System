package library.ds;

import library.model.WaitingStudent;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * WaitingQueueManager.java
 * ------------------------
 * Manages ONE WaitingQueue (see WaitingQueue.java) per Book ID.
 *
 * Internally this uses java.util.HashMap<Integer, WaitingQueue> simply as a
 * convenient way to route "which book's queue do I need?" -> the mandatory
 * custom Hash Table data structure requirement of this project is already
 * fulfilled by BookHashTable.java (used for Book ID search). Every queue
 * itself, however, is our own hand-written linked-list based WaitingQueue.
 */
public class WaitingQueueManager implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<Integer, WaitingQueue> queues = new HashMap<>();

    /** Add a student to the waiting queue of the given book (creates the queue if needed). */
    public void addToQueue(int bookId, WaitingStudent student) {
        queues.computeIfAbsent(bookId, k -> new WaitingQueue()).enqueue(student);
    }

    /** Remove and return the next student in line for a given book (null if none waiting). */
    public WaitingStudent getNextInLine(int bookId) {
        WaitingQueue q = queues.get(bookId);
        if (q == null || q.isEmpty()) return null;
        return q.dequeue();
    }

    public WaitingQueue getQueue(int bookId) {
        return queues.get(bookId);
    }

    public boolean hasWaitingStudents(int bookId) {
        WaitingQueue q = queues.get(bookId);
        return q != null && !q.isEmpty();
    }

    public Map<Integer, WaitingQueue> getAllQueues() {
        return queues;
    }
}
