package library.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * UITheme.java
 * ------------
 * Central place for colors, fonts and small helper methods so every
 * screen in the application looks consistent, modern and simple.
 * (Pure Java Swing is used -- no external UI library -- so the project
 * compiles and runs with nothing but a standard JDK.)
 */
public class UITheme {

    public static final Color PRIMARY = new Color(0x2E5CFF);
    public static final Color PRIMARY_DARK = new Color(0x1E3FCC);
    public static final Color SIDEBAR = new Color(0x1F2437);
    public static final Color BACKGROUND = new Color(0xF4F6FB);
    public static final Color CARD_BG = Color.WHITE;
    public static final Color TEXT_DARK = new Color(0x1F2437);
    public static final Color TEXT_LIGHT = new Color(0xFFFFFF);
    public static final Color SUCCESS = new Color(0x2EA043);
    public static final Color DANGER = new Color(0xE5484D);
    public static final Color BORDER = new Color(0xE0E4EC);

    public static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 22);
    public static final Font FONT_HEADING = new Font("SansSerif", Font.BOLD, 16);
    public static final Font FONT_LABEL = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font FONT_BUTTON = new Font("SansSerif", Font.BOLD, 14);

    /** Creates a primary-colored, flat, rounded-look action button. */
    public static JButton primaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setBackground(PRIMARY);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 18, 10, 18));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    /** Creates a neutral / secondary button (e.g. Clear, Cancel). */
    public static JButton secondaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setBackground(Color.WHITE);
        btn.setForeground(TEXT_DARK);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(BORDER, 1, true));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    /** Creates a red "danger" button (e.g. Delete). */
    public static JButton dangerButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setBackground(DANGER);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 18, 10, 18));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static JTextField textField() {
        JTextField field = new JTextField();
        field.setFont(FONT_LABEL);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                new EmptyBorder(8, 10, 8, 10)));
        return field;
    }

    public static JLabel heading(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_HEADING);
        label.setForeground(TEXT_DARK);
        return label;
    }

    public static JLabel formLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_LABEL);
        label.setForeground(TEXT_DARK);
        return label;
    }

    public static JPanel card() {
        JPanel panel = new JPanel();
        panel.setBackground(CARD_BG);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                new EmptyBorder(20, 20, 20, 20)));
        return panel;
    }

    /** Shows a small green "success" popup dialog. */
    public static void showSuccess(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Shows a small red "error" popup dialog. */
    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
