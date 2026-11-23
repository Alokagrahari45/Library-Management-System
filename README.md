# 📚 Library Management System

A desktop-based Library Management System developed using **Java and Java Swing**. This project simplifies library operations through book management, issue and return functionality, student validation, fine calculation, and waiting queue management.

## 🚀 Key Features

- 🔐 **Admin Login:** Login screen for accessing the library system.
- 📚 **Book Management:** Add, update, delete, and view books.
- 🔍 **Book Search:** Search books using available book information.
- 📖 **Issue Books:** Issue books to students.
- 🔄 **Return Books:** Manage book return operations.
- 👨‍🎓 **Student Validation:** Validate student details during book issuing.
- 📅 **Due Date Management:** Manage book return deadlines.
- 💰 **Fine Calculation:** Calculate overdue fines.
- ⏳ **Waiting Queue:** Manage students waiting for unavailable books.
- 💾 **File Handling:** Store and retrieve library records.

## 🛠️ Technologies Used

- Java
- Java Swing (GUI)
- Object-Oriented Programming (OOP)
- Data Structures
- File Handling
- Git & GitHub
- Visual Studio Code

## 🧠 Data Structures Implemented

This project demonstrates practical implementation of Data Structures:

| Data Structure | Purpose |
|---|---|
| Custom Linked List | Managing book records |
| Custom Hash Table | Efficient book searching |
| Queue (FIFO) | Managing waiting students |
| ArrayList | Collection management where used |

## 📂 Project Structure

```text
Library-Management-System/
│
├── data/
│   └── books.dat
│
├── docs/
│
├── src/
│   └── library/
│       ├── Main.java
│       │
│       ├── ds/
│       │   ├── BookHashTable.java
│       │   ├── BookLinkedList.java
│       │   ├── BookNode.java
│       │   └── HashEntry.java
│       │
│       ├── gui/
│       │   ├── LoginScreen.java
│       │   ├── IssuedBooksPanel.java
│       │   ├── ReturnBookPanel.java
│       │   ├── SearchBookPanel.java
│       │   ├── ViewBooksPanel.java
│       │   ├── UpdateBookPanel.java
│       │   ├── WaitingQueuePanel.java
│       │   └── UITheme.java
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
├── .gitignore
└── README.md
```

## ⚙️ Requirements

- Java JDK
- Visual Studio Code (recommended)
- Java Extension Pack for VS Code

## ▶️ How to Run the Project

### 1. Clone the Repository

```bash
git clone https://github.com/Alokagrahari45/Library-Management-System.git
```

### 2. Open the Project

Open the cloned project folder in Visual Studio Code.

### 3. Open Terminal

Open the terminal in the project root directory.

### 4. Compile the Java Files

For Windows PowerShell:

```powershell
New-Item -ItemType Directory -Force out

javac -d out (Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
```

### 5. Run the Application

```powershell
java -cp out library.Main
```

## 🎯 Learning Outcomes

- Understanding Object-Oriented Programming concepts.
- Implementing custom Data Structures.
- Understanding Linked Lists and Hash Tables.
- Developing desktop applications using Java Swing.
- Working with file handling and data persistence.
- Applying programming concepts to real-world problems.

## 👨‍💻 Developer

**Alok Kumar Agrahari**

B.Tech – Computer Science and Engineering

Lovely Professional University

**GitHub:** [Alokagrahari45](https://github.com/Alokagrahari45)

---

⭐ If you find this project useful, feel free to explore the repository!