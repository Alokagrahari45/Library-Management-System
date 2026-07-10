# Viva Questions & Answers

## A. Data Structures

**1. Why did you use a Linked List for storing books instead of an array?**
Arrays have a fixed size and require shifting elements on insertion/deletion.
A linked list grows dynamically and lets us insert/delete without shifting,
which suits a library where books are added and removed often.

**2. What type of linked list did you implement?**
A singly linked list — each node points only to the next node, using
`head` and `tail` pointers for O(1) insertion at the end.

**3. Why keep a `tail` pointer?**
Without it, inserting at the end would require walking the whole list
(O(n)). With a tail pointer, insertion at the end is O(1).

**4. What is the time complexity of deleting a book from the linked list?**
O(n) in the worst case, because we may need to walk from the head to find
the node with the matching Book ID.

**5. Why use a Hash Table for searching by Book ID?**
Book ID is a unique key used constantly (Issue, Return, Update, Delete all
look it up). A hash table gives O(1) average lookup versus O(n) for a
linear scan through the linked list.

**6. How does your hash table handle collisions?**
Separate chaining — each bucket is itself a small linked list
(`HashEntry` nodes). If two Book IDs hash to the same index, they're
simply chained together in that bucket.

**7. What hash function did you use?**
`index = Math.abs(bookId) % numberOfBuckets` (64 buckets) — a simple
modulo-based hash suitable for integer keys.

**8. What is the worst-case time complexity of a hash table lookup?**
O(n), if every key collides into the same bucket. In practice, with a
reasonable table size and well-distributed IDs, average case is O(1).

**9. Why does the hash table store references instead of copies of Book objects?**
So the linked list remains the single source of truth. If we duplicated
Book objects, updating a book would require updating it in two places and
risk them going out of sync.

**10. Why is a Queue used for the waiting list?**
Fairness — the student who requested an unavailable book first should get
it first when a copy is returned. A Queue enforces First-In-First-Out
(FIFO) ordering, which is exactly this rule.

**11. How is your Queue implemented?**
As a singly linked list with separate `front` and `rear` pointers, giving
O(1) enqueue (add to rear) and O(1) dequeue (remove from front).

**12. Why not use an ArrayList as a queue?**
Removing the first element of an ArrayList (`remove(0)`) is O(n) because
every subsequent element must shift left. A linked-list-based queue avoids
this by using pointers instead of shifting.

**13. Is there one Queue for the whole library or one per book?**
One Queue per book, because each book has its own independent waiting
line — a student waiting for Book A shouldn't be affected by Book B's
queue. `WaitingQueueManager` maps each Book ID to its own `WaitingQueue`.

**14. What happens to the waiting queue when a book is returned?**
`returnBook()` checks if that book's queue has anyone waiting; if so, it
dequeues the front student and immediately issues the book to them.

**15. Why didn't you just use `java.util.LinkedList` or `java.util.HashMap` directly?**
The assignment specifically requires implementing these data structures,
to demonstrate understanding of how they work internally (nodes, pointers,
hashing, chaining) rather than only using a library's ready-made class.

**16. What is the difference between a Stack and a Queue?**
A Stack is LIFO (Last In First Out — like a pile of plates), a Queue is
FIFO (First In First Out — like a line at a counter). We use a Queue
because fairness requires FIFO order for waiting students.

**17. What is a self-referential structure?**
A class that contains a field of its own type, e.g. `BookNode` has a
`next` field of type `BookNode`. This is what allows nodes to be linked
together into a list.

**18. Why 64 buckets in the hash table? What if you had 10,000 books?**
64 is a small, simple starting size for demonstration. With many more
books you'd want more buckets (and possibly resizing/rehashing) to keep
the average chain length short and lookups close to O(1).

**19. What is load factor in a hash table?**
The ratio of stored entries to number of buckets. A high load factor means
longer chains and slower lookups; a common strategy is to resize (double
the buckets) once the load factor passes a threshold like 0.75.

**20. How would you convert this hash table to use open addressing instead of chaining?**
Instead of a linked list per bucket, you'd probe for the next empty slot
directly in the array (linear probing, quadratic probing, or double
hashing) when a collision occurs.

## B. OOP Concepts

**21. Where is encapsulation used in this project?**
`Book`'s fields are `private` with public getters/setters — the linked
list and hash table can't accidentally corrupt a Book's internal state
except through controlled methods like `decreaseAvailable()`.

**22. Where is separation of concerns applied?**
`LibraryService` contains ALL business logic and data structure
manipulation; GUI panels only collect input and display results — they
never touch `BookLinkedList` or `BookHashTable` directly.

**23. Is there any inheritance in this project?**
The GUI panels (`AddBookPanel`, `ViewBooksPanel`, etc.) all extend
`JPanel`, and `LoginScreen`/`Dashboard` extend `JFrame` — reusing Swing's
built-in behavior. `WaitingQueue`/`QueueNode` etc. don't need custom
inheritance since the project favors composition (e.g. `WaitingQueueManager`
"has-a" map of `WaitingQueue`s) which keeps the class relationships simple.

**24. What is composition, and where do you use it?**
Composition means a class contains another class as a field ("has-a"
relationship). E.g. `LibraryService` "has-a" `BookLinkedList`, a
`BookHashTable`, and a `WaitingQueueManager`.

**25. What is polymorphism, and does this project use it?**
Polymorphism means the same method call behaves differently depending on
the object's actual type. It's used lightly here via method overriding —
e.g. every GUI panel overrides Swing's paint/layout behavior implicitly by
extending `JPanel`, and `toString()` is overridden in `Book` and
`IssueRecord` for readable debug output.

**26. Why is `Book` marked `Serializable`?**
So Java's `ObjectOutputStream` can convert it into a byte stream and write
it to a file, and later reconstruct the exact same object when read back.

**27. What is `serialVersionUID` and why is it declared?**
A version identifier for a serializable class. If you don't declare it,
Java auto-generates one based on the class structure, which can silently
change if you edit the class later, breaking deserialization of old files.
Declaring it manually keeps it stable.

## C. GUI / Application Design

**28. Why did you use Java Swing instead of a web framework?**
The project requirement is a standalone desktop application (no
web-based application, no internet dependency) — Swing ships with the
standard JDK and needs no external framework or server.

**29. What is `CardLayout` and why is it used in the Dashboard?**
`CardLayout` stacks multiple panels in the same container and shows only
one at a time. It lets the Dashboard switch between Add/View/Search/etc.
screens without opening separate windows.

**30. How does input validation work in this project?**
Each form checks for empty fields and valid number formats
(`Integer.parseInt` inside a try/catch) before calling into
`LibraryService`, which performs further business-rule validation (e.g. no
duplicate Book IDs, can't reduce copies below the issued count).

**31. What happens if the user enters text instead of a number for Book ID?**
`Integer.parseInt()` throws a `NumberFormatException`, which is caught and
shown to the user as a friendly error message rather than crashing the app.

**32. Why is there one shared `LibraryService` instance instead of creating a new one per panel?**
So every panel operates on the exact same in-memory data structures. If
each panel created its own `LibraryService`, changes made in one screen
wouldn't be visible in another until a restart.

## D. File Handling / Persistence

**33. How is data saved between runs, since no database is used?**
Using Java Object Serialization (`ObjectOutputStream`/`ObjectInputStream`)
writing to `.dat` files inside a `data/` folder — the entire `BookLinkedList`,
the list of `IssueRecord`s, and the `WaitingQueueManager` are serialized
as-is.

**34. Why is the Hash Table not saved to a file directly?**
Because it stores the same `Book` objects as the linked list. Saving both
separately risks them going out of sync. Instead, only the linked list is
saved; the hash table is rebuilt from it (in O(n)) every time the app
starts.

**35. What happens the very first time the application runs (no data files yet)?**
`FileHandler.load()` returns `null` when the file doesn't exist, so
`LibraryService`'s constructor falls back to creating empty data
structures instead of crashing.

**36. What's the difference between `ObjectOutputStream` and simply writing text?**
`ObjectOutputStream` serializes the actual Java object graph (including
nested objects like `Book` inside `BookNode`) in a binary format that can
be read back into live objects directly, without manually parsing text.

**37. What would you need to change to store data as plain text (CSV) instead?**
Write a custom method that loops through the linked list and writes each
book's fields joined by a delimiter (e.g. comma) per line, and a matching
loader that reads each line, splits on the delimiter, and reconstructs
`Book` objects.

## E. General / Extension Questions

**38. What's the difference between `totalCopies` and `availableCopies`?**
`totalCopies` is how many physical copies the library owns; `availableCopies`
is how many are currently not issued. `availableCopies` decreases on Issue
and increases on Return, but never exceeds `totalCopies`.

**39. What happens if you try to delete a book that still has issued copies?**
`deleteBook()` checks `availableCopies < totalCopies` and returns an error
instead of deleting, to avoid losing track of books that are still out
with students.

**40. How would you add "fine calculation for late returns" to this project?**
Compare `IssueRecord.getDueDate()` to the actual return date in
`returnBook()`; if the return date is after the due date, calculate
`daysLate * finePerDay` and display/store it.

**41. How would you support multiple admins/users with different roles?**
Replace the hardcoded credentials with a `User` model (username, hashed
password, role), stored in its own file, and check the role before
allowing certain actions (e.g. only a "Librarian" role can delete books).

**42. Is this project thread-safe?**
Not explicitly — it's a single-user desktop app with all data structure
access happening on the Swing Event Dispatch Thread, so there's no
concurrent access to worry about in normal use.
