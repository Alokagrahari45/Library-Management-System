package library.gui;

import library.model.Book;
import library.service.LibraryService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * UpdateBookPanel.java
 * --------------------
 * Lets the Admin load an existing book by ID (using the fast Hash Table
 * lookup), edit its fields, then save the changes back.
 */
public class UpdateBookPanel extends JPanel {

    private final LibraryService libraryService;
    private final Dashboard dashboard;

    private JTextField idField, titleField, authorField, publisherField, copiesField;

    public UpdateBookPanel(LibraryService libraryService, Dashboard dashboard) {
        this.libraryService = libraryService;
        this.dashboard = dashboard;

        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);
        setBorder(new EmptyBorder(30, 40, 30, 40));

        add(UITheme.heading("Update Book"), BorderLayout.NORTH);

        JPanel card = UITheme.card();
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        idField = UITheme.textField();
        titleField = UITheme.textField();
        authorField = UITheme.textField();
        publisherField = UITheme.textField();
        copiesField = UITheme.textField();
        // Editable fields start disabled until a valid book is loaded
        setEditFieldsEnabled(false);

        JButton loadBtn = UITheme.secondaryButton("Load Book");

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        card.add(UITheme.formLabel("Book ID:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        idField.setPreferredSize(new Dimension(200, 34));
        card.add(idField, gbc);
        gbc.gridx = 2; gbc.weightx = 0;
        card.add(loadBtn, gbc);

        addFormRow(card, gbc, 1, "Title:", titleField);
        addFormRow(card, gbc, 2, "Author:", authorField);
        addFormRow(card, gbc, 3, "Publisher:", publisherField);
        addFormRow(card, gbc, 4, "Total Copies:", copiesField);

        JButton saveBtn = UITheme.primaryButton("Save Changes");
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnRow.setOpaque(false);
        btnRow.add(saveBtn);

        gbc.gridx = 1; gbc.gridy = 5;
        card.add(btnRow, gbc);

        loadBtn.addActionListener(e -> handleLoad());
        saveBtn.addActionListener(e -> handleSave());

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(20, 0, 0, 0));
        wrapper.add(card, BorderLayout.NORTH);
        add(wrapper, BorderLayout.CENTER);
    }

    private void addFormRow(JPanel card, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        card.add(UITheme.formLabel(label), gbc);
        gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 1;
        field.setPreferredSize(new Dimension(280, 34));
        card.add(field, gbc);
    }

    private void setEditFieldsEnabled(boolean enabled) {
        titleField.setEnabled(enabled);
        authorField.setEnabled(enabled);
        publisherField.setEnabled(enabled);
        copiesField.setEnabled(enabled);
    }

    private void handleLoad() {
        int bookId;
        try {
            bookId = Integer.parseInt(idField.getText().trim());
        } catch (NumberFormatException ex) {
            UITheme.showError(this, "Book ID must be a whole number.");
            return;
        }

        // Fast O(1) average lookup via the custom Hash Table
        Book book = libraryService.searchById(bookId);
        if (book == null) {
            UITheme.showError(this, "No book found with ID " + bookId + ".");
            setEditFieldsEnabled(false);
            return;
        }

        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        publisherField.setText(book.getPublisher());
        copiesField.setText(String.valueOf(book.getTotalCopies()));
        setEditFieldsEnabled(true);
    }

    private void handleSave() {
        int bookId;
        int totalCopies;
        try {
            bookId = Integer.parseInt(idField.getText().trim());
        } catch (NumberFormatException ex) {
            UITheme.showError(this, "Book ID must be a whole number.");
            return;
        }
        try {
            totalCopies = Integer.parseInt(copiesField.getText().trim());
        } catch (NumberFormatException ex) {
            UITheme.showError(this, "Total Copies must be a whole number.");
            return;
        }

        String error = libraryService.updateBook(
                bookId, titleField.getText(), authorField.getText(), publisherField.getText(), totalCopies);

        if (error != null) {
            UITheme.showError(this, error);
        } else {
            UITheme.showSuccess(this, "Book updated successfully!");
            dashboard.showCard("View");
        }
    }
}
