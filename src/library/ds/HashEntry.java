package library.ds;

import library.model.Book;
import java.io.Serializable;

public class HashEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    int key;          // the Book ID
    Book value;       // reference to the actual Book object
    HashEntry next;   // next entry in the same bucket (collision chain)

    public HashEntry(int key, Book value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }
}
