# Flowchart & Entity Relationship Diagram

## 1. Overall Application Flowchart

```mermaid
flowchart TD
    A[Start Application] --> B[Login Screen]
    B -->|Invalid credentials| B
    B -->|Valid credentials| C[Dashboard]

    C --> D{Choose Action}
    D --> E[Add Book]
    D --> F[Update Book]
    D --> G[Delete Book]
    D --> H[View All Books]
    D --> I[Search Book]
    D --> J[Issue Book]
    D --> K[Return Book]
    D --> L[View Waiting Queue]
    D --> M[View Issued Books]
    D --> N[Logout]

    E --> E1{Valid & Unique ID?}
    E1 -->|No| E2[Show Error] --> C
    E1 -->|Yes| E3[Insert into Linked List + Hash Table] --> E4[Save to File] --> C

    J --> J1{Book Exists?}
    J1 -->|No| J2[Show Error] --> C
    J1 -->|Yes| J3{Copies Available?}
    J3 -->|Yes| J4[Issue Book, Decrease Available] --> J6[Save to File] --> C
    J3 -->|No| J5[Add Student to Waiting Queue] --> J6

    K --> K1{Issued Record Found?}
    K1 -->|No| K2[Show Error] --> C
    K1 -->|Yes| K3[Remove Record, Increase Available]
    K3 --> K4{Anyone Waiting?}
    K4 -->|Yes| K5[Dequeue Next Student, Auto-Issue] --> K6[Save to File] --> C
    K4 -->|No| K6

    N --> B
```

## 2. Entity Relationship (Data Model) Diagram

> Note: This project uses **file-based serialized storage, not a
> relational database** — there are no SQL tables or foreign keys. The
> diagram below shows the logical relationships between the data entities
> for documentation purposes.

```mermaid
erDiagram
    BOOK ||--o{ ISSUE_RECORD : "is issued via"
    BOOK ||--o{ WAITING_STUDENT : "has waiting students"

    BOOK {
        int bookId PK
        string title
        string author
        string publisher
        int totalCopies
        int availableCopies
    }

    ISSUE_RECORD {
        int bookId FK
        string bookTitle
        string studentName
        string studentRollNo
        date issueDate
        date dueDate
    }

    WAITING_STUDENT {
        int bookId FK
        string studentName
        string studentRollNo
        int queuePosition
    }
```

- **BOOK (1) → ISSUE_RECORD (many):** one book can have many historical
  issue transactions over time (though only "currently issued" ones are
  kept in this simple version).
- **BOOK (1) → WAITING_STUDENT (many):** one book can have many students
  waiting for it, held in strict FIFO order by the Queue.
