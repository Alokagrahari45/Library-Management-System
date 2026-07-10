package library.gui;

import library.ds.WaitingQueue;
import library.model.Book;
import library.model.WaitingStudent;
import library.service.LibraryService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * WaitingQueuePanel.java
 * ----------------------
 * Shows, for every book that currently has students waiting, the full
 * FIFO order of that book's Queue -- letting the Admin see exactly who
 * is next in line for each book.
 */
public class WaitingQueuePanel extends JPanel {

    private final LibraryService libraryService;
    private final DefaultTableModel tableModel;

    private static final String[] COLUMNS = {"Position", "Book ID", "Book Title", "Student Name", "Roll No."};

    public WaitingQueuePanel(LibraryService libraryService) {
        this.libraryService = libraryService;

        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);
        setBorder(new EmptyBorder(30, 40, 30, 40));

        JPanel headerRow = new JPanel(new BorderLayout());
        headerRow.setOpaque(false);
        headerRow.add(UITheme.heading("Waiting Queue (per book, FIFO order)"), BorderLayout.WEST);
        JButton refreshBtn = UITheme.secondaryButton("Refresh");
        refreshBtn.addActionListener(e -> refresh());
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

        refresh();
    }

    /** Walks every book's Queue (front to rear) and lists all waiting students. */
    public void refresh() {
        tableModel.setRowCount(0);
        Map<Integer, WaitingQueue> allQueues = libraryService.getWaitingQueueManager().getAllQueues();

        for (Map.Entry<Integer, WaitingQueue> entry : allQueues.entrySet()) {
            int bookId = entry.getKey();
            WaitingQueue queue = entry.getValue();
            if (queue == null || queue.isEmpty()) continue;

            Book book = libraryService.searchById(bookId);
            String title = (book != null) ? book.getTitle() : "(deleted book)";

            List<WaitingStudent> students = queue.toList(); // front -> rear order
            int position = 1;
            for (WaitingStudent s : students) {
                tableModel.addRow(new Object[]{
                        position++, bookId, title, s.getStudentName(), s.getStudentRollNo()
                });
            }
        }
    }
}
