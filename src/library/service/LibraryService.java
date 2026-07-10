package library.service;

import library.ds.BookHashTable;
import library.ds.BookLinkedList;
import library.ds.WaitingQueue;
import library.ds.WaitingQueueManager;
import library.model.Book;
import library.model.IssueRecord;
import library.model.WaitingStudent;

import java.util.ArrayList;
import java.util.List;

/**
 * LibraryService.java
 * --------------------
 * The "brain" of the application. This class ties all three mandatory
 * data structures together and exposes clean, reusable methods that the
 * GUI panels call. Keeping ALL business logic here (and none of it in the
 * GUI classes) is a basic but important OOP design principle: separation
 * of concerns between the UI layer and the logic/data layer.
 *
 * Data structures used:
 *   1. BookLinkedList  bookList     -> master storage of all books (insertion order)
 *   2. BookHashTable   bookIndex    -> O(1) average lookup of a Book by its ID
 *   3. WaitingQueueManager waitingQueueManager -> one FIFO Queue per book
 *
 * Other supporting storage:
 *   4. List<IssueRecord> issuedBooks -> simple ArrayList of currently issued books
 */
public class LibraryService {

    // ---- File paths where our data is persisted between runs ----
    private static final String DATA_FOLDER = "data";
    private static final String BOOKS_FILE = DATA_FOLDER + "/books.dat";
    private static final String ISSUED_FILE = DATA_FOLDER + "/issued.dat";
    private static final String WAITING_FILE = DATA_FOLDER + "/waiting.dat";

    private BookLinkedList bookList;
    private BookHashTable bookIndex;
    private List<IssueRecord> issuedBooks;
    private WaitingQueueManager waitingQueueManager;

    @SuppressWarnings("unchecked")
    public LibraryService() {
        FileHandler.ensureDataFolderExists(DATA_FOLDER);

        // ----- Load books (or start empty on first run) -----
        Object loadedBooks = FileHandler.load(BOOKS_FILE);
        bookList = (loadedBooks instanceof BookLinkedList) ? (BookLinkedList) loadedBooks : new BookLinkedList();

        // Rebuild the hash index from the linked list every time we start up.
        // (We only persist the linked list; the hash table is always rebuilt
        //  from it, which keeps the two structures perfectly in sync.)
        bookIndex = new BookHashTable();
        for (Book b : bookList) {
            bookIndex.put(b.getBookId(), b);
        }

        // ----- Load issued records -----
        Object loadedIssued = FileHandler.load(ISSUED_FILE);
        issuedBooks = (loadedIssued instanceof List) ? (List<IssueRecord>) loadedIssued : new ArrayList<>();

        // ----- Load waiting queues -----
        Object loadedWaiting = FileHandler.load(WAITING_FILE);
        waitingQueueManager = (loadedWaiting instanceof WaitingQueueManager)
                ? (WaitingQueueManager) loadedWaiting : new WaitingQueueManager();
    }

    /** Persist every in-memory data structure to disk. Call this after every change. */
    public void saveAll() {
        FileHandler.save(bookList, BOOKS_FILE);
        FileHandler.save(issuedBooks, ISSUED_FILE);
        FileHandler.save(waitingQueueManager, WAITING_FILE);
    }

    // ======================= ADD BOOK =======================
    /** Returns an error message, or null if the book was added successfully. */
    public String addBook(int bookId, String title, String author, String publisher, int totalCopies) {
        if (bookIndex.containsKey(bookId)) {
            return "A book with ID " + bookId + " already exists.";
        }
        if (title == null || title.trim().isEmpty()) return "Title cannot be empty.";
        if (author == null || author.trim().isEmpty()) return "Author cannot be empty.";
        if (totalCopies <= 0) return "Total copies must be a positive number.";

        Book book = new Book(bookId, title.trim(), author.trim(), publisher.trim(), totalCopies);
        bookList.insert(book);      // O(1) insert into linked list
        bookIndex.put(bookId, book); // O(1) insert into hash table index
        saveAll();
        return null; // success
    }

    // ======================= UPDATE BOOK =======================
    public String updateBook(int bookId, String title, String author, String publisher, int totalCopies) {
        Book book = bookIndex.get(bookId); // O(1) lookup
        if (book == null) return "No book found with ID " + bookId + ".";
        if (title == null || title.trim().isEmpty()) return "Title cannot be empty.";
        if (author == null || author.trim().isEmpty()) return "Author cannot be empty.";

        int issuedCount = book.getTotalCopies() - book.getAvailableCopies();
        if (totalCopies < issuedCount) {
            return "Total copies cannot be less than the " + issuedCount + " cop(y/ies) currently issued.";
        }

        book.setTitle(title.trim());
        book.setAuthor(author.trim());
        book.setPublisher(publisher.trim());
        // Keep 'available' consistent when total copies changes
        book.setAvailableCopies(totalCopies - issuedCount);
        book.setTotalCopies(totalCopies);

        saveAll();
        return null; // success
    }

    // ======================= DELETE BOOK =======================
    public String deleteBook(int bookId) {
        Book book = bookIndex.get(bookId);
        if (book == null) return "No book found with ID " + bookId + ".";
        if (book.getAvailableCopies() < book.getTotalCopies()) {
            return "Cannot delete: some copies of this book are still issued.";
        }
        bookList.delete(bookId);
        bookIndex.remove(bookId);
        saveAll();
        return null; // success
    }

    // ======================= SEARCH =======================
    /** O(1) average lookup, used everywhere the app needs a book by its exact ID. */
    public Book searchById(int bookId) {
        return bookIndex.get(bookId);
    }

    public List<Book> searchByTitle(String title) {
        return bookList.searchByTitle(title);
    }

    public List<Book> searchByAuthor(String author) {
        return bookList.searchByAuthor(author);
    }

    public List<Book> getAllBooks() {
        return bookList.toList();
    }

    // ======================= ISSUE BOOK =======================
    /**
     * Attempts to issue a book to a student.
     * If no copies are available, the student is placed into that book's
     * waiting Queue instead, and a special message is returned.
     */
    public String issueBook(int bookId, String studentName, String studentRollNo) {
        Book book = bookIndex.get(bookId);
        if (book == null) return "No book found with ID " + bookId + ".";
        if (studentName == null || studentName.trim().isEmpty()) return "Student name cannot be empty.";
        if (studentRollNo == null || studentRollNo.trim().isEmpty()) return "Student roll number cannot be empty.";

        if (!book.isAvailable()) {
            // No copies left -> add the student to the FIFO waiting queue for this book
            waitingQueueManager.addToQueue(bookId, new WaitingStudent(studentName.trim(), studentRollNo.trim()));
            saveAll();
            return "WAITLISTED"; // special code the GUI checks for
        }

        book.decreaseAvailable();
        issuedBooks.add(new IssueRecord(bookId, book.getTitle(), studentName.trim(), studentRollNo.trim()));
        saveAll();
        return null; // success
    }

    // ======================= RETURN BOOK =======================
    /**
     * Returns a book previously issued to a student (matched by Book ID +
     * Roll No). If students are waiting in the Queue for this book, the
     * next one in line automatically receives it.
     */
    public String returnBook(int bookId, String studentRollNo) {
        IssueRecord recordToRemove = null;
        for (IssueRecord r : issuedBooks) {
            if (r.getBookId() == bookId && r.getStudentRollNo().equalsIgnoreCase(studentRollNo.trim())) {
                recordToRemove = r;
                break;
            }
        }
        if (recordToRemove == null) return "No matching issued record found for this Book ID and Roll No.";

        issuedBooks.remove(recordToRemove);
        Book book = bookIndex.get(bookId);
        if (book != null) {
            book.increaseAvailable();

            // If someone is waiting for this book, immediately hand it to them (FIFO)
            if (waitingQueueManager.hasWaitingStudents(bookId)) {
                WaitingStudent next = waitingQueueManager.getNextInLine(bookId);
                book.decreaseAvailable();
                issuedBooks.add(new IssueRecord(bookId, book.getTitle(), next.getStudentName(), next.getStudentRollNo()));
                saveAll();
                return "AUTO_ISSUED:" + next.getStudentName() + ":" + next.getStudentRollNo();
            }
        }
        saveAll();
        return null; // success, nobody was waiting
    }

    // ======================= WAITING QUEUE =======================
    public WaitingQueue getWaitingQueue(int bookId) {
        return waitingQueueManager.getQueue(bookId);
    }

    public WaitingQueueManager getWaitingQueueManager() {
        return waitingQueueManager;
    }

    // ======================= ISSUED BOOKS =======================
    public List<IssueRecord> getIssuedBooks() {
        return issuedBooks;
    }
}
