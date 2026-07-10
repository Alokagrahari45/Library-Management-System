package library.model;

import java.io.Serializable;

/**
 * Book.java
 * ---------
 * Represents a single Book record in the library.
 * This is a plain OOP model class (POJO) that is stored inside our
 * custom Linked List and indexed inside our custom Hash Table.
 *
 * implements Serializable -> so that Book objects can be written directly
 * to a file using ObjectOutputStream (file handling requirement).
 */
public class Book implements Serializable {

    // A fixed serialVersionUID is good practice for Serializable classes
    private static final long serialVersionUID = 1L;

    private int bookId;            // Unique ID -> used as the Hash Table key
    private String title;
    private String author;
    private String publisher;
    private int totalCopies;       // Total copies owned by the library
    private int availableCopies;   // Copies currently available to issue

    public Book(int bookId, String title, String author, String publisher, int totalCopies) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies; // initially all copies are available
    }

    // ---------- Getters ----------
    public int getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getPublisher() { return publisher; }
    public int getTotalCopies() { return totalCopies; }
    public int getAvailableCopies() { return availableCopies; }

    // ---------- Setters (used by Update Book module) ----------
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }

    /** Reduces available copies by 1 when a book is issued. Returns false if none left. */
    public boolean decreaseAvailable() {
        if (availableCopies <= 0) return false;
        availableCopies--;
        return true;
    }

    /** Increases available copies by 1 when a book is returned. */
    public void increaseAvailable() {
        if (availableCopies < totalCopies) {
            availableCopies++;
        }
    }

    public boolean isAvailable() {
        return availableCopies > 0;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ID=" + bookId +
                ", Title='" + title + '\'' +
                ", Author='" + author + '\'' +
                ", Publisher='" + publisher + '\'' +
                ", Total=" + totalCopies +
                ", Available=" + availableCopies +
                '}';
    }
}
