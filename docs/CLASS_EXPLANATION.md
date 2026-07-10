# Step-by-Step Explanation of Every Class

## Package: `library.model` (plain data classes)

### `Book.java`
Represents one book record: ID, title, author, publisher, total copies, and
available copies. Implements `Serializable` so it can be written to a file.
Has helper methods `decreaseAvailable()` / `increaseAvailable()` /
`isAvailable()` used during Issue/Return so the "business rule" of not
issuing more copies than exist lives in one place.

### `IssueRecord.java`
Represents one "this book is currently issued to this student" transaction:
book ID/title, student name/roll number, issue date, and a due date
(auto-computed as issue date + 14 days).

### `WaitingStudent.java`
Represents one student waiting in line for an unavailable book. This is the
element type stored inside our custom `WaitingQueue`.

---

## Package: `library.ds` (the three mandatory data structures)

### `BookNode.java`
One node of the linked list: a `Book` plus a `next` pointer.

### `BookLinkedList.java`
The custom singly linked list holding every book in the library
(insertion order). Provides `insert`, `delete`, `searchById`,
`searchByTitle`, `searchByAuthor`, `toList()`, and implements `Iterable`
so it can be used directly in a for-each loop.

### `HashEntry.java`
One (key, value) pair inside a hash table bucket: `key` = Book ID,
`value` = reference to a `Book`, plus a `next` pointer for chaining.

### `BookHashTable.java`
The custom hash table (64 buckets, separate chaining) giving O(1) average
lookup of a `Book` by its ID. Provides `put`, `get`, `remove`,
`containsKey`.

### `QueueNode.java`
A generic node (`<T>`) used to build any linked-list-based queue.

### `WaitingQueue.java`
The custom FIFO queue (front/rear pointers) holding the students waiting
for ONE particular book. Provides `enqueue`, `dequeue`, `peek`, `toList()`.

### `WaitingQueueManager.java`
Maps each Book ID to its own `WaitingQueue` (using `java.util.HashMap`
purely as a routing convenience — see `DATA_STRUCTURES.md`).

---

## Package: `library.service` (business logic + persistence)

### `FileHandler.java`
Generic helper with two static methods, `save(Object, path)` and
`load(path)`, built on `ObjectOutputStream` / `ObjectInputStream`. This is
the entire "file handling" layer of the app — every data structure is
just serialized as-is.

### `LibraryService.java`
The central class that ties the Linked List, Hash Table, and
Waiting-Queue-Manager together, and is the ONLY class that contains
business rules (validation, issuing logic, return logic, etc). GUI panels
never touch the data structures directly — they always call a method on
`LibraryService`. This is a basic but important OOP principle: **separation
of concerns** between the UI layer and the logic/data layer.

Key methods: `addBook`, `updateBook`, `deleteBook`, `searchById`,
`searchByTitle`, `searchByAuthor`, `getAllBooks`, `issueBook`,
`returnBook`, `getWaitingQueue`, `getIssuedBooks`, `saveAll`.

---

## Package: `library.gui` (Swing screens)

### `UITheme.java`
A single place holding shared colors, fonts, and small factory methods
(`primaryButton()`, `textField()`, `card()`, etc.) so every screen looks
consistent without repeating styling code — a simple form of code reuse.

### `LoginScreen.java`
The first window shown. Validates that both fields are filled, checks the
username/password against constants, and opens the `Dashboard` on success.

### `Dashboard.java`
The main window after login. Uses a `CardLayout` to switch between all
feature panels without opening separate windows. Creates ONE shared
`LibraryService` instance and passes it to every panel, so all panels
operate on the same underlying data structures. Has a sidebar with a
button per feature plus Logout.

### `AddBookPanel.java` / `UpdateBookPanel.java` / `DeleteBookPanel.java`
Simple forms that validate user input, then call the matching
`LibraryService` method and show a success/error dialog.

### `ViewBooksPanel.java`
Displays every book (read from the linked list via `getAllBooks()`) in a
`JTable`. `refreshTable()` is called every time this screen is opened.

### `SearchBookPanel.java`
Lets the user pick "By ID / By Title / By Author" from a dropdown, then
calls the matching `LibraryService` search method and fills the table with
results.

### `IssueBookPanel.java`
Collects Book ID + student details, calls `issueBook()`. Handles three
outcomes: success, "no copies — added to waiting queue", or a validation
error.

### `ReturnBookPanel.java`
Collects Book ID + roll number, calls `returnBook()`. Handles three
outcomes: success, "auto-issued to next waiting student" (shows who), or
an error (e.g. no matching issued record).

### `WaitingQueuePanel.java`
Iterates every book's `WaitingQueue` and lists all waiting students in
front-to-rear (FIFO) order, so the admin can see exactly who is next.

### `IssuedBooksPanel.java`
Lists every currently issued book with issue date and due date.

### `Main.java` (package `library`)
The entry point — launches `LoginScreen` on Swing's Event Dispatch Thread.
