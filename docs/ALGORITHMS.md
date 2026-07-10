# Algorithms for Each Module

Pseudocode-style description of the core algorithm behind every feature.

---

### 1. Login
```
INPUT: username, password
IF username or password is empty:
    SHOW error "fields cannot be empty"
ELSE IF username == ADMIN_USERNAME AND password == ADMIN_PASSWORD:
    OPEN Dashboard
ELSE:
    SHOW error "invalid credentials"
```

### 2. Add Book
```
INPUT: bookId, title, author, publisher, totalCopies
IF bookHashTable.containsKey(bookId):
    RETURN error "duplicate ID"
IF title/author empty OR totalCopies <= 0:
    RETURN validation error
CREATE Book object (availableCopies = totalCopies)
bookLinkedList.insert(book)      // O(1) — insert at tail
bookHashTable.put(bookId, book)  // O(1) average
SAVE all data to disk
```

### 3. Update Book
```
INPUT: bookId, new title/author/publisher/totalCopies
book = bookHashTable.get(bookId)       // O(1) average
IF book == null: RETURN "not found"
issuedCount = book.totalCopies - book.availableCopies
IF newTotalCopies < issuedCount:
    RETURN error "can't reduce below issued count"
UPDATE book fields
book.availableCopies = newTotalCopies - issuedCount
SAVE all data to disk
```

### 4. Delete Book
```
INPUT: bookId
book = bookHashTable.get(bookId)
IF book == null: RETURN "not found"
IF book.availableCopies < book.totalCopies:
    RETURN error "copies still issued, cannot delete"
bookLinkedList.delete(bookId)     // O(n) — walk list, unlink node
bookHashTable.remove(bookId)      // O(1) average
SAVE all data to disk
```

### 5. View All Books (Linked List Traversal)
```
result = empty list
node = linkedList.head
WHILE node != null:
    result.add(node.data)
    node = node.next
DISPLAY result in table
```

### 6. Search Book
```
IF mode == "By ID":
    RETURN bookHashTable.get(id)              // O(1) average
ELSE IF mode == "By Title":
    RETURN bookLinkedList.searchByTitle(text)  // O(n) linear scan
ELSE IF mode == "By Author":
    RETURN bookLinkedList.searchByAuthor(text) // O(n) linear scan
```

### 7. Issue Book
```
INPUT: bookId, studentName, studentRollNo
book = bookHashTable.get(bookId)
IF book == null: RETURN "not found"
IF book.availableCopies == 0:
    waitingQueueManager.enqueue(bookId, new WaitingStudent(...))  // O(1)
    RETURN "WAITLISTED"
book.decreaseAvailable()
issuedBooks.add(new IssueRecord(...))
SAVE all data to disk
```

### 8. Return Book
```
INPUT: bookId, studentRollNo
FIND matching IssueRecord in issuedBooks list (bookId + rollNo)
IF not found: RETURN "no matching record"
REMOVE that record
book.increaseAvailable()
IF waitingQueueManager.hasWaitingStudents(bookId):
    nextStudent = waitingQueueManager.dequeue(bookId)   // O(1)
    book.decreaseAvailable()
    issuedBooks.add(new IssueRecord(bookId, ..., nextStudent))
    RETURN "AUTO_ISSUED to nextStudent"
SAVE all data to disk
```

### 9. Waiting Queue Display
```
FOR EACH (bookId, queue) in waitingQueueManager.getAllQueues():
    IF queue not empty:
        FOR EACH student IN queue.toList():   // front -> rear order
            DISPLAY (position, bookId, bookTitle, student)
```

### 10. Display Issued Books
```
FOR EACH record IN issuedBooks:
    DISPLAY (bookId, title, studentName, rollNo, issueDate, dueDate)
```

### 11. File Handling (Save / Load)
```
SAVE(object, path):
    open ObjectOutputStream on path
    write object
    close stream

LOAD(path):
    IF file does not exist: RETURN null
    open ObjectInputStream on path
    RETURN read object
```
