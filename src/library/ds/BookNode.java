package library.ds;

import library.model.Book;
import java.io.Serializable;


public class BookNode implements Serializable {

    private static final long serialVersionUID = 1L;

    Book data;      // the actual Book stored in this node
    BookNode next;  // pointer to the next node (null if this is the last node)

    public BookNode(Book data) {
        this.data = data;
        this.next = null;
    }
}
