package library.ds;

import library.model.Book;
import java.io.Serializable;

/**
 * BookHashTable.java
 * ------------------
 * A custom HASH TABLE (Hash Map) implementation used to search a Book
 * by its Book ID in average O(1) time, instead of the O(n) linear scan
 * that the linked list would require.
 *
 * WHY A HASH TABLE?
 *  - Book ID is a unique integer key, ideal for hashing.
 *  - "Search Book by ID" is used very frequently (every Issue/Return/Update
 *    operation looks the book up first), so we want the fastest possible
 *    lookup -> average case O(1) for a hash table vs O(n) for a linked list.
 *
 * COLLISION HANDLING: Separate Chaining.
 *  Each bucket (array slot) holds the head of a small linked list
 *  (see HashEntry.java). If two Book IDs hash to the same bucket index,
 *  they simply become part of the same chain.
 *
 * Note: This table stores a *reference* to the same Book object that is
 * stored in BookLinkedList. So the linked list is the "source of truth"
 * for ordering / iteration, and this hash table is purely an index that
 * points into it for O(1) lookup.
 */
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
