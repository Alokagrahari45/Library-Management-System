package library.gui;

import library.service.LibraryService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * AddBookPanel.java
 * -----------------
 * Form used by the Admin to add a new Book record.
 * Performs basic validation, then delegates the real work to
 * LibraryService.addBook(), which inserts into the Linked List AND
 * indexes the book in the Hash Table.
 */
public class AddBookPanel extends JPanel {

    private final LibraryService libraryService;
    private final Dashboard dashboard;

    private JTextField idField, titleField, authorField, publisherField, copiesField;

    public AddBookPanel(LibraryService libraryService, Dashboard dashboard) {
        this.libraryService = libraryService;
        this.dashboard = dashboard;

        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);
        setBorder(new EmptyBorder(30, 40, 30, 40));

        add(UITheme.heading("Add New Book"), BorderLayout.NORTH);

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

        addFormRow(card, gbc, 0, "Book ID (unique number):", idField);
        addFormRow(card, gbc, 1, "Title:", titleField);
        addFormRow(card, gbc, 2, "Author:", authorField);
        addFormRow(card, gbc, 3, "Publisher:", publisherField);
        addFormRow(card, gbc, 4, "Total Copies:", copiesField);

        JButton addBtn = UITheme.primaryButton("Add Book");
        JButton clearBtn = UITheme.secondaryButton("Clear");
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnRow.setOpaque(false);
        btnRow.add(addBtn);
        btnRow.add(clearBtn);

        gbc.gridx = 1; gbc.gridy = 5;
        card.add(btnRow, gbc);

        addBtn.addActionListener(e -> handleAdd());
        clearBtn.addActionListener(e -> clearForm());

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

    private void handleAdd() {
        // ----- Basic input validation before touching the service layer -----
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

        String error = libraryService.addBook(
                bookId,
                titleField.getText(),
                authorField.getText(),
                publisherField.getText(),
                totalCopies
        );

        if (error != null) {
            UITheme.showError(this, error);
        } else {
            UITheme.showSuccess(this, "Book added successfully!");
            clearForm();
            dashboard.showCard("View"); // jump to View All Books to see the result
        }
    }

    private void clearForm() {
        idField.setText("");
        titleField.setText("");
        authorField.setText("");
        publisherField.setText("");
        copiesField.setText("");
    }
}
