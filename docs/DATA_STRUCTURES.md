# Data Structures Explanation

This project mandates three data structures. Each was chosen deliberately
based on how it is *used* in the application, not just to tick a box.

---

## 1. Singly Linked List — `BookLinkedList` (master book storage)

**Why a Linked List?**
- Books are inserted and deleted often, and a linked list needs no shifting
  of elements (unlike an array/ArrayList) — insertion/deletion at a known
  node is O(1).
- We don't need random/index-based access to books (we never ask "give me
  the 5th book"); we mostly need to walk through every book, or add/remove
  one — exactly what a linked list is built for.
- It's the simplest possible demonstration of the fundamental
  "self-referential node" concept taught in a Data Structures course.

**Structure:** Each `BookNode` holds a `Book` object and a `next` pointer.
`BookLinkedList` keeps `head` and `tail` pointers so that:
- Insert at the end → **O(1)**
- Delete by Book ID → **O(n)** worst case (must walk to find the node)
- Search by ID/Title/Author → **O(n)** (linear scan)
- Traversal (View All Books) → **O(n)**

**Where used:** `Add Book`, `Delete Book`, `View All Books`, and as the
fallback linear search for Title/Author (since those aren't unique keys).

---

## 2. Hash Table (Hash Map) — `BookHashTable` (fast search by Book ID)

**Why a Hash Table?**
- `Book ID` is a unique integer key. Almost every operation (Issue, Return,
  Update, Delete, Search-by-ID) needs to find a book by its ID, and it needs
  to be **fast**.
- A hash table gives **O(1) average-case** lookup, insert, and delete —
  dramatically faster than scanning the linked list (O(n)) every time.

**Structure:** An array of 64 buckets. Each bucket is a small linked list of
`HashEntry` objects (key = Book ID, value = reference to the `Book` object).
This collision-handling technique is called **separate chaining**: if two
Book IDs hash to the same bucket, they simply form a small chain there.

**Hash function used:** `index = Math.abs(bookId) % numberOfBuckets`
— a simple, easy-to-explain modulo hash, appropriate for integer keys in a
college project.

**Complexity:**
- `put()` / `get()` / `remove()` → **O(1) average**, O(n) worst case (if all
  keys collide into one bucket, which is unlikely with a reasonable table
  size and a spread of Book IDs)

**Important design note:** The hash table stores a *reference* to the same
`Book` object that lives inside the linked list — it does not duplicate
data. This means the linked list is the "source of truth" for the full
collection, and the hash table is purely a fast index into it. Every time
the app starts, the hash table is rebuilt in O(n) time from the linked list
(see `LibraryService` constructor), so only the linked list needs to be
saved to disk.

**Where used:** `Search Book by ID`, `Issue Book`, `Return Book`,
`Update Book`, `Delete Book` — anywhere the app needs a book by its exact ID.

---

## 3. Queue (FIFO) — `WaitingQueue` (per-book waiting list)

**Why a Queue?**
- When a student requests a book that has zero available copies, fairness
  requires **First-In-First-Out (FIFO)** ordering: whoever asked first
  should receive the book first when a copy becomes available.
- A Queue is exactly the abstract data type that models this rule.

**Structure:** A singly linked list with separate `front` and `rear`
pointers (see `QueueNode`).
- `enqueue()` (add to `rear`) → **O(1)**
- `dequeue()` (remove from `front`) → **O(1)**

Using front/rear pointers (instead of, say, an ArrayList with
`remove(0)`, which is O(n)) is what makes this a proper Queue
implementation rather than just a re-purposed list.

**One Queue per Book:** `WaitingQueueManager` maps each Book ID to its own
independent `WaitingQueue`, since each book's waiting list is a separate
FIFO line. (The map itself uses `java.util.HashMap` purely as a routing
convenience — the mandatory custom Hash Table requirement is fulfilled by
`BookHashTable`, described above.)

**Where used:** `Issue Book` (enqueue when no copies are available),
`Return Book` (dequeue and auto-issue to the next student in line),
`Waiting Queue Management` screen (traverse and display the current order).

---

## 4. Supporting (non-mandatory) structures

- `java.util.ArrayList<IssueRecord>` — a simple growable list of currently
  issued books. An ArrayList was intentionally chosen here (rather than a
  custom structure) because this project's DS requirements are specifically
  about the Linked List / Hash Table / Queue above; the issued-books list
  is a straightforward record log, not something that needs a specialised
  structure.

## 5. Summary Table

| Operation | Data Structure | Time Complexity |
|---|---|---|
| Add a book | Linked List (insert at tail) + Hash Table (put) | O(1) |
| Delete a book | Linked List (delete) + Hash Table (remove) | O(n) |
| Search by ID | Hash Table (get) | O(1) average |
| Search by Title/Author | Linked List (linear scan) | O(n) |
| View all books | Linked List (traversal) | O(n) |
| Add student to waiting list | Queue (enqueue) | O(1) |
| Serve next waiting student | Queue (dequeue) | O(1) |
