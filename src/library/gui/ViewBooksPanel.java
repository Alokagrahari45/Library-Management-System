package library.gui;

import library.model.Book;
import library.service.LibraryService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * ViewBooksPanel.java
 * -------------------
 * Displays every book currently stored in the Linked List (bookList) in
 * a JTable. refreshTable() walks the linked list from head to tail and
 * rebuilds the table rows -- this is the classic "traversal" operation
 * of a linked list, done every time this screen is opened.
 */
public class ViewBooksPanel extends JPanel {

    private final LibraryService libraryService;
    private final DefaultTableModel tableModel;
    private final JTable table;

    private static final String[] COLUMNS = {"Book ID", "Title", "Author", "Publisher", "Total", "Available"};

    public ViewBooksPanel(LibraryService libraryService) {
        this.libraryService = libraryService;

        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);
        setBorder(new EmptyBorder(30, 40, 30, 40));

        JPanel headerRow = new JPanel(new BorderLayout());
        headerRow.setOpaque(false);
        headerRow.add(UITheme.heading("All Books"), BorderLayout.WEST);

        JButton refreshBtn = UITheme.secondaryButton("Refresh");
        refreshBtn.addActionListener(e -> refreshTable());
        headerRow.add(refreshBtn, BorderLayout.EAST);

        add(headerRow, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setFont(UITheme.FONT_LABEL);
        table.setRowHeight(28);
        table.getTableHeader().setFont(UITheme.FONT_HEADING);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(UITheme.BORDER));
        scrollPane.setBorder(new EmptyBorder(15, 0, 0, 0));

        add(scrollPane, BorderLayout.CENTER);

        refreshTable();
    }

    /** Re-reads all books from the Linked List and repopulates the table. */
    public void refreshTable() {
        tableModel.setRowCount(0); // clear existing rows
        List<Book> books = libraryService.getAllBooks(); // traverses the linked list
        for (Book b : books) {
            tableModel.addRow(new Object[]{
                    b.getBookId(), b.getTitle(), b.getAuthor(), b.getPublisher(),
                    b.getTotalCopies(), b.getAvailableCopies()
            });
        }
    }
}
