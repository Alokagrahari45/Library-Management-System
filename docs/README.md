# Library Management System (Java Swing – Standalone Desktop App)

A standalone, offline **Library Management System** built with core Java and
Swing, created for a 2nd-year B.Tech CSE Data Structures course project.

No database, no internet connection, and no external libraries are required —
only a standard JDK.

---

## 1. Features

1. Admin Login screen
2. Dashboard (sidebar navigation)
3. Add Book
4. Update Book
5. Delete Book
6. View All Books
7. Search Book by ID / Title / Author
8. Issue Book (with automatic Waiting Queue if no copies are available)
9. Return Book (automatically issues to the next waiting student, if any)
10. Waiting Queue Management (view per-book FIFO queues)
11. Display Issued Books
12. Automatic file-based persistence (data survives an application restart)
13. Input validation on every form

## 2. Data Structures Used

| Data Structure | Class | Purpose |
|---|---|---|
| Singly Linked List | `library.ds.BookLinkedList` | Master storage of all books |
| Hash Table (separate chaining) | `library.ds.BookHashTable` | O(1) average search of a Book by ID |
| Queue (linked list based, FIFO) | `library.ds.WaitingQueue` | Waiting list of students for an out-of-stock book |

See `docs/DATA_STRUCTURES.md` for a full explanation and complexity analysis.

## 3. Folder Structure

```
LibraryManagementSystem/
├── src/
│   └── library/
│       ├── Main.java                    # application entry point
│       ├── model/                       # plain data classes (POJOs)
│       │   ├── Book.java
│       │   ├── IssueRecord.java
│       │   └── WaitingStudent.java
│       ├── ds/                          # hand-written data structures
│       │   ├── BookNode.java
│       │   ├── BookLinkedList.java
│       │   ├── HashEntry.java
│       │   ├── BookHashTable.java
│       │   ├── QueueNode.java
│       │   ├── WaitingQueue.java
│       │   └── WaitingQueueManager.java
│       ├── service/                     # business logic / file handling
│       │   ├── LibraryService.java
│       │   └── FileHandler.java
│       └── gui/                         # Swing screens
│           ├── UITheme.java
│           ├── LoginScreen.java
│           ├── Dashboard.java
│           ├── AddBookPanel.java
│           ├── UpdateBookPanel.java
│           ├── DeleteBookPanel.java
│           ├── ViewBooksPanel.java
│           ├── SearchBookPanel.java
│           ├── IssueBookPanel.java
│           ├── ReturnBookPanel.java
│           ├── WaitingQueuePanel.java
│           └── IssuedBooksPanel.java
├── data/                                 # auto-created; stores .dat files
├── docs/                                 # all project documentation
│   ├── README.md
│   ├── DATA_STRUCTURES.md
│   ├── CLASS_EXPLANATION.md
│   ├── ALGORITHMS.md
│   ├── VIVA_QUESTIONS.md
│   ├── sample_test_data.txt
│   └── future_improvements.md
└── bin/                                   # compiled .class files (generated)
```

## 4. Requirements

- JDK 8 or newer (JDK 17/21 recommended). No other software or internet
  connection is needed.

Check your version:
```
java -version
javac -version
```

## 5. How to Compile and Run

Open a terminal inside the `LibraryManagementSystem` folder.

**Step 1 — Compile:**
```bash
mkdir -p bin
javac -d bin $(find src -name "*.java")
```
(On Windows PowerShell, use: `javac -d bin (Get-ChildItem -Recurse -Filter *.java src | % FullName)`)

**Step 2 — Run:**
```bash
java -cp bin library.Main
```

A login window will appear.

## 6. Default Login Credentials

```
Username: admin
Password: admin123
```
(These are defined as constants in `LoginScreen.java` and can be changed there.)

## 7. Data Storage

All data is saved automatically (after every Add/Update/Delete/Issue/Return
action) into three files inside the `data/` folder, created automatically on
first run:

- `data/books.dat`    — serialized `BookLinkedList` (all books)
- `data/issued.dat`   — serialized list of `IssueRecord` (currently issued books)
- `data/waiting.dat`  — serialized `WaitingQueueManager` (all waiting queues)

These are binary files produced with Java's built-in object serialization
(`ObjectOutputStream` / `ObjectInputStream`), so no external database is used
and the app works completely offline. Delete the `data/` folder to reset the
application to a blank state.

## 8. Sample Test Data

See `docs/sample_test_data.txt` for a ready-made set of books and
issue/return scenarios you can use to demonstrate every feature quickly.

## 9. Troubleshooting

| Problem | Solution |
|---|---|
| `javac: command not found` | Install a full JDK, not just a JRE |
| GUI looks tiny/huge | Adjust your OS display scaling; window is resizable |
| "No book found" errors | Make sure you Add the book first, and double check the Book ID |
| Data not saving | Ensure the app has write permission in its folder (avoid running from a read-only location) |

## 10. Author / Submission Notes

This project was built to demonstrate a Linked List, a Hash Table, and a
Queue working together inside one real, practical desktop application, using
plain Java OOP and file-based persistence — with no database and no internet
dependency.
