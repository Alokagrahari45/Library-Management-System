package library.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginScreen extends JFrame {

    // Hardcoded admin credentials (documented here for the viva)
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginScreen() {
        setTitle("Library Management System - Login");
        setSize(420, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BACKGROUND);
        root.setBorder(new EmptyBorder(40, 40, 40, 40));

        // ----- Header -----
        JLabel titleLabel = new JLabel("Library Management System", SwingConstants.CENTER);
        titleLabel.setFont(UITheme.FONT_TITLE);
        titleLabel.setForeground(UITheme.PRIMARY);

        JLabel subLabel = new JLabel("Admin Login", SwingConstants.CENTER);
        subLabel.setFont(UITheme.FONT_LABEL);
        subLabel.setForeground(Color.GRAY);

        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 0, 6));
        headerPanel.setOpaque(false);
        headerPanel.add(titleLabel);
        headerPanel.add(subLabel);

        // ----- Form -----
        JPanel formPanel = new JPanel();
        formPanel.setOpaque(false);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(new EmptyBorder(30, 0, 0, 0));

        JLabel userLabel = UITheme.formLabel("Username");
        usernameField = UITheme.textField();
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));

        JLabel passLabel = UITheme.formLabel("Password");
        passwordField = new JPasswordField();
        passwordField.setFont(UITheme.FONT_LABEL);
        passwordField.setBorder(usernameField.getBorder());
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));

        JButton loginBtn = UITheme.primaryButton("Login");
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        loginBtn.addActionListener(this::handleLogin);

        // Allow pressing Enter inside the password field to log in
        passwordField.addActionListener(this::handleLogin);

        JLabel hintLabel = new JLabel("Default: admin / admin123", SwingConstants.CENTER);
        hintLabel.setFont(new Font("SansSerif", Font.ITALIC, 11));
        hintLabel.setForeground(Color.GRAY);
        hintLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        hintLabel.setBorder(new EmptyBorder(10, 0, 0, 0));

        formPanel.add(userLabel);
        formPanel.add(Box.createVerticalStrut(6));
        formPanel.add(usernameField);
        formPanel.add(Box.createVerticalStrut(18));
        formPanel.add(passLabel);
        formPanel.add(Box.createVerticalStrut(6));
        formPanel.add(passwordField);
        formPanel.add(Box.createVerticalStrut(24));
        formPanel.add(loginBtn);
        formPanel.add(hintLabel);

        root.add(headerPanel, BorderLayout.NORTH);
        root.add(formPanel, BorderLayout.CENTER);
        setContentPane(root);
    }

    /** Validates input and checks credentials; opens the Dashboard on success. */
    private void handleLogin(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            UITheme.showError(this, "Please enter both username and password.");
            return;
        }

        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            this.dispose(); // close login window
            SwingUtilities.invokeLater(() -> new Dashboard().setVisible(true));
        } else {
            UITheme.showError(this, "Invalid username or password.");
            passwordField.setText("");
        }
    }
}
