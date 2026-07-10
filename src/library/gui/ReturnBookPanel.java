package library.gui;

import library.service.LibraryService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * ReturnBookPanel.java
 * --------------------
 * Lets the Admin return a previously-issued book. If a student was
 * waiting in the Queue for that book, LibraryService automatically
 * dequeues and issues it to them (FIFO), and this panel informs the
 * Admin who received it next.
 */
public class ReturnBookPanel extends JPanel {

    private final LibraryService libraryService;
    private final Dashboard dashboard;

    private JTextField bookIdField, rollNoField;

    public ReturnBookPanel(LibraryService libraryService, Dashboard dashboard) {
        this.libraryService = libraryService;
        this.dashboard = dashboard;

        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);
        setBorder(new EmptyBorder(30, 40, 30, 40));

        add(UITheme.heading("Return Book"), BorderLayout.NORTH);

        JPanel card = UITheme.card();
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        bookIdField = UITheme.textField();
        rollNoField = UITheme.textField();

        addFormRow(card, gbc, 0, "Book ID:", bookIdField);
        addFormRow(card, gbc, 1, "Student Roll No.:", rollNoField);

        JButton returnBtn = UITheme.primaryButton("Return Book");
        gbc.gridx = 1; gbc.gridy = 2;
        card.add(returnBtn, gbc);

        returnBtn.addActionListener(e -> handleReturn());

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

    private void handleReturn() {
        int bookId;
        try {
            bookId = Integer.parseInt(bookIdField.getText().trim());
        } catch (NumberFormatException ex) {
            UITheme.showError(this, "Book ID must be a whole number.");
            return;
        }
        if (rollNoField.getText().trim().isEmpty()) {
            UITheme.showError(this, "Please enter the student's roll number.");
            return;
        }

        String result = libraryService.returnBook(bookId, rollNoField.getText());

        if (result == null) {
            UITheme.showSuccess(this, "Book returned successfully!");
            clearForm();
            dashboard.showCard("View");
        } else if (result.startsWith("AUTO_ISSUED:")) {
            String[] parts = result.split(":");
            JOptionPane.showMessageDialog(this,
                    "Book returned successfully!\nIt was automatically issued to the next student "
                            + "in the waiting queue:\n" + parts[1] + " (Roll No: " + parts[2] + ")",
                    "Waiting Queue Processed", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            dashboard.showCard("Issued");
        } else {
            UITheme.showError(this, result);
        }
    }

    private void clearForm() {
        bookIdField.setText("");
        rollNoField.setText("");
    }
}
