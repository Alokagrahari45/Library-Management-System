package library.gui;

import library.model.Book;
import library.service.LibraryService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * DeleteBookPanel.java
 * --------------------
 * Lets the Admin delete a book by ID. The book is first looked up (Hash
 * Table) and shown for confirmation before LibraryService removes it from
 * both the Linked List and the Hash Table.
 */
public class DeleteBookPanel extends JPanel {

    private final LibraryService libraryService;
    private final Dashboard dashboard;

    private JTextField idField;
    private JLabel previewLabel;

    public DeleteBookPanel(LibraryService libraryService, Dashboard dashboard) {
        this.libraryService = libraryService;
        this.dashboard = dashboard;

        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);
        setBorder(new EmptyBorder(30, 40, 30, 40));

        add(UITheme.heading("Delete Book"), BorderLayout.NORTH);

        JPanel card = UITheme.card();
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        idField = UITheme.textField();
        idField.setPreferredSize(new Dimension(200, 34));

        JButton findBtn = UITheme.secondaryButton("Find");
        JButton deleteBtn = UITheme.dangerButton("Delete Book");

        gbc.gridx = 0; gbc.gridy = 0;
        card.add(UITheme.formLabel("Book ID:"), gbc);
        gbc.gridx = 1;
        card.add(idField, gbc);
        gbc.gridx = 2;
        card.add(findBtn, gbc);

        previewLabel = new JLabel(" ");
        previewLabel.setFont(UITheme.FONT_LABEL);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 3;
        card.add(previewLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        card.add(deleteBtn, gbc);

        findBtn.addActionListener(e -> handleFind());
        deleteBtn.addActionListener(e -> handleDelete());

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(20, 0, 0, 0));
        wrapper.add(card, BorderLayout.NORTH);
        add(wrapper, BorderLayout.CENTER);
    }

    private void handleFind() {
        int bookId;
        try {
            bookId = Integer.parseInt(idField.getText().trim());
        } catch (NumberFormatException ex) {
            UITheme.showError(this, "Book ID must be a whole number.");
            return;
        }
        Book book = libraryService.searchById(bookId);
        if (book == null) {
            previewLabel.setText("No book found with ID " + bookId + ".");
        } else {
            previewLabel.setText("<html>Found: <b>" + book.getTitle() + "</b> by " + book.getAuthor()
                    + " (Available: " + book.getAvailableCopies() + "/" + book.getTotalCopies() + ")</html>");
        }
    }

    private void handleDelete() {
        int bookId;
        try {
            bookId = Integer.parseInt(idField.getText().trim());
        } catch (NumberFormatException ex) {
            UITheme.showError(this, "Book ID must be a whole number.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete book ID " + bookId + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        String error = libraryService.deleteBook(bookId);
        if (error != null) {
            UITheme.showError(this, error);
        } else {
            UITheme.showSuccess(this, "Book deleted successfully!");
            idField.setText("");
            previewLabel.setText(" ");
            dashboard.showCard("View");
        }
    }
}
