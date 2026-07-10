package library.ds;

import library.model.Book;
import java.io.Serializable;

/**
 * BookNode.java
 * -------------
 * A single node of our custom Singly Linked List.
 * Each node holds one Book object and a reference ("next") to the
 * following node in the list.
 */
public class BookNode implements Serializable {

    private static final long serialVersionUID = 1L;

    Book data;      // the actual Book stored in this node
    BookNode next;  // pointer to the next node (null if this is the last node)

    public BookNode(Book data) {
        this.data = data;
        this.next = null;
    }
}
