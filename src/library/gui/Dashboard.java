package library.gui;

import library.service.LibraryService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Dashboard.java
 * --------------
 * The main application window shown after a successful login.
 * Uses a CardLayout to switch between all the feature panels (Add Book,
 * View Books, Issue Book, etc.) without opening lots of separate windows,
 * which keeps the app feeling like one cohesive desktop application.
 *
 * A single shared LibraryService instance is created here and passed down
 * to every panel, so all panels operate on the SAME data structures.
 */
public class Dashboard extends JFrame {

    private final LibraryService libraryService;
    private final CardLayout cardLayout;
    private final JPanel cardsContainer;

    // Panels are kept as fields so we can refresh them (e.g. reload the
    // table) whenever the user navigates to them.
    private ViewBooksPanel viewBooksPanel;
    private IssuedBooksPanel issuedBooksPanel;
    private WaitingQueuePanel waitingQueuePanel;

    public Dashboard() {
        this.libraryService = new LibraryService();

        setTitle("Library Management System - Dashboard");
        setSize(1100, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(900, 600));

        // ----- Center container that swaps between feature panels -----
        cardLayout = new CardLayout();
        cardsContainer = new JPanel(cardLayout);
        cardsContainer.setBackground(UITheme.BACKGROUND);

        buildPanels();

        // ----- Sidebar navigation -----
        JPanel sidebar = buildSidebar();

        JPanel root = new JPanel(new BorderLayout());
        root.add(sidebar, BorderLayout.WEST);
        root.add(cardsContainer, BorderLayout.CENTER);
        setContentPane(root);

        showCard("Home");
    }

    private void buildPanels() {
        cardsContainer.add(buildHomePanel(), "Home");
        cardsContainer.add(new AddBookPanel(libraryService, this), "Add");
        cardsContainer.add(new UpdateBookPanel(libraryService, this), "Update");
        cardsContainer.add(new DeleteBookPanel(libraryService, this), "Delete");

        viewBooksPanel = new ViewBooksPanel(libraryService);
        cardsContainer.add(viewBooksPanel, "View");

        cardsContainer.add(new SearchBookPanel(libraryService), "Search");
        cardsContainer.add(new IssueBookPanel(libraryService, this), "Issue");
        cardsContainer.add(new ReturnBookPanel(libraryService, this), "Return");

        waitingQueuePanel = new WaitingQueuePanel(libraryService);
        cardsContainer.add(waitingQueuePanel, "Waiting");

        issuedBooksPanel = new IssuedBooksPanel(libraryService);
        cardsContainer.add(issuedBooksPanel, "Issued");
    }

    private JPanel buildHomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UITheme.BACKGROUND);
        panel.setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel welcome = new JLabel("Welcome, Admin!");
        welcome.setFont(UITheme.FONT_TITLE);
        welcome.setForeground(UITheme.TEXT_DARK);

        JLabel sub = new JLabel("<html>Use the menu on the left to manage books, issue/return "
                + "books, and monitor the waiting list.<br>All data is automatically saved to files "
                + "in the /data folder.</html>");
        sub.setFont(UITheme.FONT_LABEL);
        sub.setForeground(Color.GRAY);
        sub.setBorder(new EmptyBorder(10, 0, 0, 0));

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.add(welcome);
        top.add(sub);

        panel.add(top, BorderLayout.NORTH);
        return panel;
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(UITheme.SIDEBAR);
        sidebar.setPreferredSize(new Dimension(230, 0));
        sidebar.setBorder(new EmptyBorder(20, 0, 20, 0));

        JLabel logo = new JLabel("  📚 LMS");
        logo.setFont(new Font("SansSerif", Font.BOLD, 20));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);
        logo.setBorder(new EmptyBorder(0, 14, 24, 0));
        sidebar.add(logo);

        sidebar.add(sidebarButton("Home", "Home"));
        sidebar.add(sidebarButton("Add Book", "Add"));
        sidebar.add(sidebarButton("Update Book", "Update"));
        sidebar.add(sidebarButton("Delete Book", "Delete"));
        sidebar.add(sidebarButton("View All Books", "View"));
        sidebar.add(sidebarButton("Search Book", "Search"));
        sidebar.add(sidebarButton("Issue Book", "Issue"));
        sidebar.add(sidebarButton("Return Book", "Return"));
        sidebar.add(sidebarButton("Waiting Queue", "Waiting"));
        sidebar.add(sidebarButton("Issued Books", "Issued"));

        sidebar.add(Box.createVerticalGlue());

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        logoutBtn.setMaximumSize(new Dimension(230, 40));
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBackground(UITheme.DANGER);
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setBorder(new EmptyBorder(10, 14, 10, 14));
        logoutBtn.addActionListener(e -> {
            this.dispose();
            SwingUtilities.invokeLater(() -> new LoginScreen().setVisible(true));
        });
        sidebar.add(logoutBtn);

        return sidebar;
    }

    private JButton sidebarButton(String label, String cardName) {
        JButton btn = new JButton("  " + label);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(230, 44));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFont(UITheme.FONT_LABEL);
        btn.setForeground(Color.WHITE);
        btn.setBackground(UITheme.SIDEBAR);
        btn.setBorder(new EmptyBorder(10, 14, 10, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> showCard(cardName));
        return btn;
    }

    /** Switches the visible panel, refreshing data-driven panels as needed. */
    public void showCard(String name) {
        // Refresh tables so they always show the latest data structure contents
        if (viewBooksPanel != null) viewBooksPanel.refreshTable();
        if (issuedBooksPanel != null) issuedBooksPanel.refreshTable();
        if (waitingQueuePanel != null) waitingQueuePanel.refresh();
        cardLayout.show(cardsContainer, name);
    }
}
