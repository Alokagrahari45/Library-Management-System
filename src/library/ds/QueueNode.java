package library.ds;

import java.io.Serializable;

/**
 * QueueNode.java
 * --------------
 * A single node used inside WaitingQueue. Generic type T lets the same
 * node class be reused for any kind of queue (here we store a "waiting
 * student" record as the value, see WaitingStudent.java).
 */
public class QueueNode<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    T data;
    QueueNode<T> next;

    public QueueNode(T data) {
        this.data = data;
        this.next = null;
    }
}
