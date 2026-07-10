package library.ds;

import library.model.Book;
import java.io.Serializable;

/**
 * HashEntry.java
 * --------------
 * One (key, value) pair stored inside a bucket of BookHashTable.
 * Buckets are implemented as singly linked lists of HashEntry nodes,
 * this is the classic "separate chaining" collision-handling technique.
 */
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
