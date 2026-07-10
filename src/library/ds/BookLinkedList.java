package library.ds;

import library.model.Book;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * BookLinkedList.java
 * -------------------
 * A custom SINGLY LINKED LIST implementation that acts as the primary
 * ("master") storage for all Book records in the library.
 *
 * WHY A LINKED LIST?
 *  - Books are added and removed frequently. A linked list gives O(1)
 *    insertion at the head/tail without shifting elements (unlike an array).
 *  - We don't need random (index-based) access to books, we mostly need to
 *    add, delete and walk through every book -> a perfect use case for a
 *    linked list.
 *  - It demonstrates the fundamental "self-referential node" data structure
 *    which is central to a Data Structures course.
 *
 * The list keeps a head and tail pointer so insertion at the end is O(1),
 * and a size counter so size() is O(1).
 */
public class BookLinkedList implements Serializable, Iterable<Book> {

    private static final long serialVersionUID = 1L;

    private BookNode head;
    private BookNode tail;
    private int size;

    public BookLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /** Insert a new book at the end of the list. O(1) because we keep a tail pointer. */
    public void insert(Book book) {
        BookNode newNode = new BookNode(book);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    /**
     * Delete the node holding the book with the given bookId.
     * O(n) because in the worst case we must walk the whole list.
     * Returns true if a book was found & removed.
     */
    public boolean delete(int bookId) {
        if (head == null) return false;

        // Case 1: book to delete is the head node
        if (head.data.getBookId() == bookId) {
            head = head.next;
            if (head == null) tail = null; // list became empty
            size--;
            return true;
        }

        // Case 2: search the rest of the list, keeping a "previous" pointer
        BookNode prev = head;
        BookNode curr = head.next;
        while (curr != null) {
            if (curr.data.getBookId() == bookId) {
                prev.next = curr.next;
                if (curr == tail) tail = prev; // deleted the last node
                size--;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false; // not found
    }

    /** Linear search by Book ID (kept for completeness; BookHashTable is used for fast search). */
    public Book searchById(int bookId) {
        BookNode curr = head;
        while (curr != null) {
            if (curr.data.getBookId() == bookId) return curr.data;
            curr = curr.next;
        }
        return null;
    }

    /** Linear search by title (case-insensitive, partial match). */
    public List<Book> searchByTitle(String title) {
        List<Book> results = new ArrayList<>();
        BookNode curr = head;
        String needle = title.toLowerCase().trim();
        while (curr != null) {
            if (curr.data.getTitle().toLowerCase().contains(needle)) {
                results.add(curr.data);
            }
            curr = curr.next;
        }
        return results;
    }

    /** Linear search by author (case-insensitive, partial match). */
    public List<Book> searchByAuthor(String author) {
        List<Book> results = new ArrayList<>();
        BookNode curr = head;
        String needle = author.toLowerCase().trim();
        while (curr != null) {
            if (curr.data.getAuthor().toLowerCase().contains(needle)) {
                results.add(curr.data);
            }
            curr = curr.next;
        }
        return results;
    }

    /** Returns every book as a List (used to populate the JTable in View All Books). */
    public List<Book> toList() {
        List<Book> results = new ArrayList<>();
        BookNode curr = head;
        while (curr != null) {
            results.add(curr.data);
            curr = curr.next;
        }
        return results;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /** Allows using this list directly in a for-each loop: for (Book b : bookLinkedList) */
    @Override
    public java.util.Iterator<Book> iterator() {
        return toList().iterator();
    }
}
