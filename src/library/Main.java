package library;

import library.gui.LoginScreen;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Could not set Look-and-Feel: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> new LoginScreen().setVisible(true));
    }
}
