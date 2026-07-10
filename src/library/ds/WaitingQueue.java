package library.ds;

import library.model.WaitingStudent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * WaitingQueue.java
 * -----------------
 * A custom QUEUE (FIFO - First In First Out) implementation, built using
 * a singly linked list with front and rear pointers.
 *
 * WHY A QUEUE?
 *  - When a student requests a book that has zero available copies, the
 *    only fair way to manage who gets it next is FIFO ordering: the first
 *    student to ask should be the first one served when a copy is returned.
 *  - enqueue() / dequeue() are both O(1) thanks to the front & rear pointers,
 *    which is exactly the performance a waiting-list needs.
 *
 * One WaitingQueue object is created PER BOOK (see WaitingQueueManager),
 * so every book that runs out of copies gets its own independent queue.
 */
public class WaitingQueue implements Serializable {

    private static final long serialVersionUID = 1L;

    private QueueNode<WaitingStudent> front; // where we remove from
    private QueueNode<WaitingStudent> rear;  // where we add to
    private int size;

    public WaitingQueue() {
        front = null;
        rear = null;
        size = 0;
    }

    /** Add a student to the back of the queue. O(1). */
    public void enqueue(WaitingStudent student) {
        QueueNode<WaitingStudent> newNode = new QueueNode<>(student);
        if (rear == null) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    /** Remove and return the student at the front of the queue. O(1). Null if empty. */
    public WaitingStudent dequeue() {
        if (front == null) return null;
        WaitingStudent data = front.data;
        front = front.next;
        if (front == null) rear = null; // queue became empty
        size--;
        return data;
    }

    /** Look at the front student without removing them. */
    public WaitingStudent peek() {
        return front == null ? null : front.data;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }

    /** Returns all waiting students, in queue order, for display in the GUI. */
    public List<WaitingStudent> toList() {
        List<WaitingStudent> results = new ArrayList<>();
        QueueNode<WaitingStudent> curr = front;
        while (curr != null) {
            results.add(curr.data);
            curr = curr.next;
        }
        return results;
    }
}
