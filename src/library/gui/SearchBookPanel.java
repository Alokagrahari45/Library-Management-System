package library.gui;

import library.model.Book;
import library.service.LibraryService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SearchBookPanel.java
 * --------------------
 * Lets the Admin search books by ID (Hash Table O(1) lookup), or by
 * Title / Author (Linked List linear search, since those aren't unique
 * keys and require scanning/matching substrings).
 */
public class SearchBookPanel extends JPanel {

    private final LibraryService libraryService;

    private JTextField searchField;
    private JComboBox<String> searchTypeBox;
    private DefaultTableModel tableModel;

    private static final String[] COLUMNS = {"Book ID", "Title", "Author", "Publisher", "Total", "Available"};

    public SearchBookPanel(LibraryService libraryService) {
        this.libraryService = libraryService;

        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);
        setBorder(new EmptyBorder(30, 40, 30, 40));

        add(UITheme.heading("Search Book"), BorderLayout.NORTH);

        JPanel searchBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchBar.setOpaque(false);
        searchBar.setBorder(new EmptyBorder(15, 0, 0, 0));

        searchTypeBox = new JComboBox<>(new String[]{"By ID", "By Title", "By Author"});
        searchField = UITheme.textField();
        searchField.setPreferredSize(new Dimension(260, 34));
        JButton searchBtn = UITheme.primaryButton("Search");

        searchBar.add(new JLabel("Search:"));
        searchBar.add(searchTypeBox);
        searchBar.add(searchField);
        searchBar.add(searchBtn);

        tableModel = new DefaultTableModel(COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable table = new JTable(tableModel);
        table.setFont(UITheme.FONT_LABEL);
        table.setRowHeight(28);
        table.getTableHeader().setFont(UITheme.FONT_HEADING);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(searchBar, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        searchBtn.addActionListener(e -> handleSearch());
        searchField.addActionListener(e -> handleSearch()); // Enter key also searches
    }

    private void handleSearch() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            UITheme.showError(this, "Please enter a search term.");
            return;
        }

        String mode = (String) searchTypeBox.getSelectedItem();
        List<Book> results = new ArrayList<>();

        if ("By ID".equals(mode)) {
            try {
                int id = Integer.parseInt(query);
                Book b = libraryService.searchById(id); // O(1) average - Hash Table
                if (b != null) results.add(b);
            } catch (NumberFormatException ex) {
                UITheme.showError(this, "Book ID must be a whole number.");
                return;
            }
        } else if ("By Title".equals(mode)) {
            results = libraryService.searchByTitle(query); // O(n) - Linked List scan
        } else {
            results = libraryService.searchByAuthor(query); // O(n) - Linked List scan
        }

        tableModel.setRowCount(0);
        for (Book b : results) {
            tableModel.addRow(new Object[]{
                    b.getBookId(), b.getTitle(), b.getAuthor(), b.getPublisher(),
                    b.getTotalCopies(), b.getAvailableCopies()
            });
        }

        if (results.isEmpty()) {
            UITheme.showError(this, "No matching books found.");
        }
    }
}
