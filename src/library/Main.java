package library;

import library.gui.LoginScreen;

import javax.swing.*;

/**
 * Main.java
 * ---------
 * Application entry point. Simply launches the Login screen on the
 * Swing Event Dispatch Thread (the standard, thread-safe way to start
 * any Swing GUI application).
 */
public class Main {
    public static void main(String[] args) {
        // Force the cross-platform "Metal" Look-and-Feel instead of the OS
        // native one. Some native Look-and-Feels (notably macOS Aqua, and
        // certain Linux GTK themes) ignore JButton.setBackground() while
        // still applying setForeground(WHITE) -- this makes our custom
        // colored buttons (e.g. the blue "Add Book" button) render as
        // invisible white-on-white text. Metal respects custom colors
        // consistently on every platform, so the UI looks the same
        // everywhere it runs.
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Could not set Look-and-Feel: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> new LoginScreen().setVisible(true));
    }
}
