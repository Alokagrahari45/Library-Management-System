package library.gui;

import library.model.IssueRecord;
import library.service.LibraryService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * IssuedBooksPanel.java
 * ---------------------
 * Displays every book currently issued to a student, including the due
 * date, by reading LibraryService's issuedBooks list.
 */
public class IssuedBooksPanel extends JPanel {

    private final LibraryService libraryService;
    private final DefaultTableModel tableModel;

    private static final String[] COLUMNS =
            {"Book ID", "Book Title", "Student Name", "Roll No.", "Issue Date", "Due Date"};

    public IssuedBooksPanel(LibraryService libraryService) {
        this.libraryService = libraryService;

        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);
        setBorder(new EmptyBorder(30, 40, 30, 40));

        JPanel headerRow = new JPanel(new BorderLayout());
        headerRow.setOpaque(false);
        headerRow.add(UITheme.heading("Issued Books"), BorderLayout.WEST);
        JButton refreshBtn = UITheme.secondaryButton("Refresh");
        refreshBtn.addActionListener(e -> refreshTable());
        headerRow.add(refreshBtn, BorderLayout.EAST);
        add(headerRow, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable table = new JTable(tableModel);
        table.setFont(UITheme.FONT_LABEL);
        table.setRowHeight(28);
        table.getTableHeader().setFont(UITheme.FONT_HEADING);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(15, 0, 0, 0));
        add(scrollPane, BorderLayout.CENTER);

        refreshTable();
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        List<IssueRecord> records = libraryService.getIssuedBooks();
        for (IssueRecord r : records) {
            tableModel.addRow(new Object[]{
                    r.getBookId(), r.getBookTitle(), r.getStudentName(), r.getStudentRollNo(),
                    r.getIssueDate(), r.getDueDate()
            });
        }
    }
}
