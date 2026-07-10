package library.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * IssueRecord.java
 * ----------------
 * Represents one "Book Issued to a Student" transaction.
 * A List of these records is maintained by LibraryService and is
 * used by the "Display Issued Books" and "Return Book" modules.
 */
public class IssueRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private int bookId;
    private String bookTitle;
    private String studentName;
    private String studentRollNo;
    private LocalDate issueDate;
    private LocalDate dueDate;

    public IssueRecord(int bookId, String bookTitle, String studentName, String studentRollNo) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.studentName = studentName;
        this.studentRollNo = studentRollNo;
        this.issueDate = LocalDate.now();
        this.dueDate = issueDate.plusDays(14); // 14-day issue period
    }

    public int getBookId() { return bookId; }
    public String getBookTitle() { return bookTitle; }
    public String getStudentName() { return studentName; }
    public String getStudentRollNo() { return studentRollNo; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getDueDate() { return dueDate; }

    @Override
    public String toString() {
        return "IssueRecord{" +
                "BookID=" + bookId +
                ", Title='" + bookTitle + '\'' +
                ", Student='" + studentName + '\'' +
                ", Roll='" + studentRollNo + '\'' +
                ", Issued=" + issueDate +
                ", Due=" + dueDate +
                '}';
    }
}
