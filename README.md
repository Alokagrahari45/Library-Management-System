# 📚 Library Management System

A Java-based Library Management System developed using Java Swing and Data Structures. This desktop application helps manage library operations such as book management, issuing and returning books, student validation, overdue fines, and waiting queue management.

## 🚀 Features

- **Admin Login:** Secure admin login and dashboard.
- **Book Management:** Add, update, delete, and view books.
- **Search Books:** Search for books easily.
- **Issue Books:** Issue books to registered students.
- **Return Books:** Manage book returns.
- **Student Validation:** Validate students before issuing books.
- **Automatic Due Dates:** Generate due dates for issued books.
- **Overdue Fine:** Calculate fines for overdue books.
- **Waiting Queue:** Maintain a queue when requested books are unavailable.
- **File Handling:** Store and manage library records.

## 🛠️ Technologies Used

- Java
- Java Swing
- Object-Oriented Programming (OOP)
- Data Structures
  - ArrayList
  - LinkedList
  - Queue (FIFO)
  - HashMap
- File Handling
- Visual Studio Code
- Git & GitHub

## 📂 Project Structure

```text
Original_LMS_System/
│
├── src/
│   └── library/
│       ├── Main.java
│       │
│       ├── gui/
│       │   ├── LoginScreen.java
│       │   ├── IssuedBooksPanel.java
│       │   ├── ReturnBookPanel.java
│       │   ├── SearchBookPanel.java
│       │   ├── ViewBooksPanel.java
│       │   ├── UpdateBookPanel.java
│       │   └── WaitingQueuePanel.java
│       │
│       ├── model/
│       │   ├── Book.java
│       │   ├── IssueRecord.java
│       │   └── WaitingStudent.java
│       │
│       └── service/
│           ├── FileHandler.java
│           └── LibraryService.java
│
└── README.md
```

## ⚙️ Requirements

- Java JDK installed
- Visual Studio Code (recommended)
- Java Extension Pack for VS Code

## ▶️ How to Run

1. Clone the repository:

```bash
git clone YOUR_GITHUB_REPOSITORY_URL
```

2. Open the project folder in VS Code.

3. Open the terminal in the project root directory.

4. Compile the Java files (Windows PowerShell):

```powershell
New-Item -ItemType Directory -Force out
javac -d out (Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
```

5. Run the application:

```powershell
java -cp out library.Main
```

## 🎯 Learning Outcomes

- Practical implementation of Object-Oriented Programming.
- Understanding and application of Data Structures.
- Development of desktop applications using Java Swing.
- File handling and record management.
- Understanding real-world library management operations.

## 👨‍💻 Developer

**Alok Kumar Agrahari**

B.Tech – Computer Science and Engineering

Lovely Professional University

---

⭐ If you find this project useful, feel free to explore the repository!