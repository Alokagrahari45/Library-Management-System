package library.gui;

import library.service.LibraryService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * IssueBookPanel.java
 * -------------------
 * Lets the Admin issue a book to a student. If the book has zero
 * available copies, LibraryService automatically places the student in
 * that book's waiting Queue instead of issuing it, and this panel shows
 * the appropriate message.
 */
public class IssueBookPanel extends JPanel {

    private final LibraryService libraryService;
    private final Dashboard dashboard;

    private JTextField bookIdField, studentNameField, rollNoField;

    public IssueBookPanel(LibraryService libraryService, Dashboard dashboard) {
        this.libraryService = libraryService;
        this.dashboard = dashboard;

        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);
        setBorder(new EmptyBorder(30, 40, 30, 40));

        add(UITheme.heading("Issue Book"), BorderLayout.NORTH);

        JPanel card = UITheme.card();
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        bookIdField = UITheme.textField();
        studentNameField = UITheme.textField();
        rollNoField = UITheme.textField();

        addFormRow(card, gbc, 0, "Book ID:", bookIdField);
        addFormRow(card, gbc, 1, "Student Name:", studentNameField);
        addFormRow(card, gbc, 2, "Student Roll No.:", rollNoField);

        JButton issueBtn = UITheme.primaryButton("Issue Book");
        gbc.gridx = 1; gbc.gridy = 3;
        card.add(issueBtn, gbc);

        issueBtn.addActionListener(e -> handleIssue());

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

    private void handleIssue() {
        int bookId;
        try {
            bookId = Integer.parseInt(bookIdField.getText().trim());
        } catch (NumberFormatException ex) {
            UITheme.showError(this, "Book ID must be a whole number.");
            return;
        }

        String result = libraryService.issueBook(bookId, studentNameField.getText(), rollNoField.getText());

        if (result == null) {
            UITheme.showSuccess(this, "Book issued successfully!");
            clearForm();
            dashboard.showCard("Issued");
        } else if ("WAITLISTED".equals(result)) {
            JOptionPane.showMessageDialog(this,
                    "No copies are currently available.\nThe student has been added to the waiting queue.",
                    "Added to Waiting Queue", JOptionPane.WARNING_MESSAGE);
            clearForm();
            dashboard.showCard("Waiting");
        } else {
            UITheme.showError(this, result);
        }
    }

    private void clearForm() {
        bookIdField.setText("");
        studentNameField.setText("");
        rollNoField.setText("");
    }
}
