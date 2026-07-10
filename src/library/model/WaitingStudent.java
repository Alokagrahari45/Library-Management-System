package library.model;

import java.io.Serializable;

/**
 * WaitingStudent.java
 * -------------------
 * Represents one student waiting in line for a book that is currently
 * unavailable (all copies issued). Stored as the element type of our
 * custom Queue (see library.ds.WaitingQueue).
 */
public class WaitingStudent implements Serializable {

    private static final long serialVersionUID = 1L;

    private String studentName;
    private String studentRollNo;

    public WaitingStudent(String studentName, String studentRollNo) {
        this.studentName = studentName;
        this.studentRollNo = studentRollNo;
    }

    public String getStudentName() { return studentName; }
    public String getStudentRollNo() { return studentRollNo; }

    @Override
    public String toString() {
        return studentName + " (Roll No: " + studentRollNo + ")";
    }
}
