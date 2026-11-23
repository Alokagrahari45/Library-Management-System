package library.ds;

import library.model.Book;
import java.io.Serializable;


public class BookHashTable implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int DEFAULT_CAPACITY = 64; // number of buckets
    private HashEntry[] buckets;
    private int size;

    public BookHashTable() {
        buckets = new HashEntry[DEFAULT_CAPACITY];
        size = 0;
    }

    /** Simple hash function: mod the key by the number of buckets. */
    private int hash(int key) {
        // Math.abs guards against negative book IDs producing a negative index
        return Math.abs(key) % buckets.length;
    }

    /** Insert or update the (bookId -> Book) mapping. O(1) average. */
    public void put(int bookId, Book book) {
        int index = hash(bookId);
        HashEntry curr = buckets[index];

        // If key already exists in this bucket's chain, update the value
        while (curr != null) {
            if (curr.key == bookId) {
                curr.value = book;
                return;
            }
            curr = curr.next;
        }

        // Otherwise insert a new entry at the head of the chain
        HashEntry newEntry = new HashEntry(bookId, book);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;
        size++;
    }

    /** Fast lookup by Book ID. Returns null if not found. O(1) average. */
    public Book get(int bookId) {
        int index = hash(bookId);
        HashEntry curr = buckets[index];
        while (curr != null) {
            if (curr.key == bookId) return curr.value;
            curr = curr.next;
        }
        return null;
    }

    /** Remove the mapping for a given Book ID. Returns true if removed. */
    public boolean remove(int bookId) {
        int index = hash(bookId);
        HashEntry curr = buckets[index];
        HashEntry prev = null;

        while (curr != null) {
            if (curr.key == bookId) {
                if (prev == null) {
                    buckets[index] = curr.next; // removing head of chain
                } else {
                    prev.next = curr.next;
                }
                size--;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false;
    }

    public boolean containsKey(int bookId) {
        return get(bookId) != null;
    }

    public int size() {
        return size;
    }
}
