package petadoption;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;

/** Shared visual theme for the whole desktop application. */
public final class Theme {
    public static final Color BG = new Color(242, 245, 249);
    public static final Color SIDEBAR = new Color(24, 30, 38);
    public static final Color CARD = Color.WHITE;
    public static final Color TEXT = new Color(35, 42, 52);
    public static final Color MUTED = new Color(120, 130, 145);
    public static final Color BLUE = new Color(52, 152, 219);
    public static final Color GREEN = new Color(46, 204, 113);
    public static final Color RED = new Color(231, 76, 60);
    public static final Color ORANGE = new Color(243, 156, 18);
    public static final Color PURPLE = new Color(155, 89, 182);
    public static final Color BORDER = new Color(225, 229, 235);
    public static final Color INPUT = new Color(250, 252, 255);

    private Theme() {}

    public static void apply() {
        try {
            // Nimbus gives dialogs, combo boxes and standard Swing controls a consistent look.
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}

        Font normal = new Font("Arial", Font.PLAIN, 13);
        Font bold = new Font("Arial", Font.BOLD, 13);

        // Global palette. Individual screens can still override these values when needed.
        UIManager.put("control", BG);
        UIManager.put("info", CARD);
        UIManager.put("nimbusBase", new Color(38, 48, 65));
        UIManager.put("nimbusBlueGrey", new Color(90, 105, 125));
        UIManager.put("nimbusLightBackground", CARD);
        UIManager.put("text", TEXT);
        UIManager.put("nimbusSelectionBackground", new Color(210, 231, 248));
        UIManager.put("nimbusSelection", BLUE);

        UIManager.put("Panel.background", BG);
        UIManager.put("Label.font", normal);
        UIManager.put("Label.foreground", TEXT);

        UIManager.put("Button.font", bold);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.background", BLUE);
        UIManager.put("Button.focus", new Color(0, 0, 0, 0));
        UIManager.put("Button.border", BorderFactory.createEmptyBorder(8, 14, 8, 14));

        UIManager.put("TextField.font", normal);
        UIManager.put("TextField.foreground", TEXT);
        UIManager.put("TextField.background", INPUT);
        UIManager.put("TextField.caretForeground", BLUE);
        UIManager.put("PasswordField.font", normal);
        UIManager.put("PasswordField.foreground", TEXT);
        UIManager.put("PasswordField.background", INPUT);
        UIManager.put("PasswordField.caretForeground", BLUE);
        UIManager.put("ComboBox.font", normal);
        UIManager.put("ComboBox.background", INPUT);
        UIManager.put("ComboBox.foreground", TEXT);

        UIManager.put("Table.font", normal);
        UIManager.put("Table.foreground", TEXT);
        UIManager.put("Table.background", CARD);
        UIManager.put("Table.selectionBackground", new Color(225, 239, 252));
        UIManager.put("Table.selectionForeground", TEXT);
        UIManager.put("TableHeader.font", bold);
        UIManager.put("TableHeader.background", new Color(38, 48, 65));
        UIManager.put("TableHeader.foreground", Color.WHITE);

        // Make JOptionPane dialogs consistent instead of default-looking white popups.
        UIManager.put("OptionPane.background", BG);
        UIManager.put("OptionPane.messageAreaBackground", BG);
        UIManager.put("OptionPane.buttonAreaBackground", BG);
        UIManager.put("OptionPane.messageForeground", TEXT);
        UIManager.put("OptionPane.messageFont", normal);
        UIManager.put("OptionPane.buttonFont", bold);
        UIManager.put("OptionPane.minimumSize", new Dimension(360, 150));
        UIManager.put("ScrollPane.border", BorderFactory.createEmptyBorder());
    }

    public static JButton button(String text, Color color, int width) {
        JButton b = new JButton(text);
        b.setPreferredSize(new Dimension(width, 42));
        b.setFont(new Font("Arial", Font.BOLD, 12));
        b.setForeground(Color.WHITE);
        b.setBackground(color);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);
        return b;
    }

    public static void styleTable(JTable t) {
        t.setRowHeight(44);
        t.setFont(new Font("Arial", Font.PLAIN, 13));
        t.setForeground(TEXT);
        t.setBackground(Color.WHITE);
        t.setGridColor(BORDER);
        t.setSelectionBackground(new Color(225, 239, 252));
        t.setSelectionForeground(TEXT);
        t.setShowVerticalLines(false);
        JTableHeader h = t.getTableHeader();
        h.setFont(new Font("Arial", Font.BOLD, 13));
        h.setBackground(new Color(38, 48, 65));
        h.setForeground(Color.WHITE);
        h.setPreferredSize(new Dimension(0, 42));
        h.setReorderingAllowed(false);
    }

    public static JPanel pageHeader(String title, String subtitle, String rightText) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(24, 28, 20, 28));
        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        JLabel t = new JLabel(title);
        t.setFont(new Font("Arial", Font.BOLD, 27));
        t.setForeground(TEXT);
        JLabel s = new JLabel(subtitle);
        s.setFont(new Font("Arial", Font.PLAIN, 14));
        s.setForeground(MUTED);
        left.add(t);
        left.add(Box.createVerticalStrut(5));
        left.add(s);
        header.add(left, BorderLayout.WEST);
        if (rightText != null) {
            JLabel r = new JLabel(rightText);
            r.setFont(new Font("Arial", Font.BOLD, 13));
            r.setForeground(BLUE);
            header.add(r, BorderLayout.EAST);
        }
        return header;
    }

    public static JPanel sidebar(String active, Runnable dashboard, Runnable users, Runnable pets, Runnable settings, Runnable analytics) {
        JPanel side = new JPanel();
        side.setPreferredSize(new Dimension(245, 0));
        side.setBackground(SIDEBAR);
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
        side.add(Box.createVerticalStrut(25));

        JLabel logo = new JLabel("PA");
        logo.setOpaque(true);
        logo.setBackground(BLUE);
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Arial", Font.BOLD, 28));
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setPreferredSize(new Dimension(65, 65));
        logo.setMaximumSize(new Dimension(65, 65));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        side.add(logo);
        side.add(Box.createVerticalStrut(12));

        JLabel brand = new JLabel("PET ADOPTION");
        brand.setForeground(Color.WHITE);
        brand.setFont(new Font("Arial", Font.BOLD, 19));
        brand.setAlignmentX(Component.CENTER_ALIGNMENT);
        side.add(brand);

        JLabel sub = new JLabel("ADMIN CONTROL");
        sub.setForeground(new Color(145, 155, 170));
        sub.setFont(new Font("Arial", Font.PLAIN, 10));
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);
        side.add(sub);
        side.add(Box.createVerticalStrut(35));

        addSide(side, "⌂  Dashboard", "Dashboard".equals(active), dashboard);
        addSide(side, "▣  User Management", "User Management".equals(active), users);
        addSide(side, "🐾  Pet Listings", "Pet Listings".equals(active), pets);
        addSide(side, "⚙  System Settings", "System Settings".equals(active), settings);
        addSide(side, "▥  Analytics", "Analytics".equals(active), analytics);

        side.add(Box.createVerticalGlue());

        JPanel profile = new JPanel(new BorderLayout(10, 0));
        profile.setMaximumSize(new Dimension(220, 65));
        profile.setBackground(new Color(31, 38, 48));
        profile.setBorder(new EmptyBorder(8, 12, 8, 12));

        JLabel avatar = new JLabel("A");
        avatar.setOpaque(true);
        avatar.setBackground(PURPLE);
        avatar.setForeground(Color.WHITE);
        avatar.setFont(new Font("Arial", Font.BOLD, 22));
        avatar.setHorizontalAlignment(SwingConstants.CENTER);
        avatar.setPreferredSize(new Dimension(42, 42));
        profile.add(avatar, BorderLayout.WEST);

        JPanel pt = new JPanel();
        pt.setOpaque(false);
        pt.setLayout(new BoxLayout(pt, BoxLayout.Y_AXIS));
        JLabel n = new JLabel(Session.name != null ? Session.name : "Administrator");
        n.setForeground(Color.WHITE);
        n.setFont(new Font("Arial", Font.BOLD, 13));
        JLabel r = new JLabel("Administrator");
        r.setForeground(new Color(145, 155, 170));
        r.setFont(new Font("Arial", Font.PLAIN, 11));
        pt.add(n);
        pt.add(r);
        profile.add(pt, BorderLayout.CENTER);
        side.add(profile);
        side.add(Box.createVerticalStrut(8));

        JButton logout = new JButton("↪  Logout");
        logout.setForeground(new Color(235, 100, 100));
        logout.setBackground(SIDEBAR);
        logout.setFont(new Font("Arial", Font.BOLD, 13));
        logout.setBorderPainted(false);
        logout.setFocusPainted(false);
        logout.setAlignmentX(Component.CENTER_ALIGNMENT);
        logout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logout.addActionListener(e -> {
            Session.userId = 0;
            Session.name = null;
            Session.role = null;
            Window w = SwingUtilities.getWindowAncestor(side);
            if (w != null) w.dispose();
            new Login();
        });
        side.add(logout);
        side.add(Box.createVerticalStrut(20));
        return side;
    }

    private static void addSide(JPanel side, String text, boolean active, Runnable action) {
        JButton b = new JButton(text);
        b.setMaximumSize(new Dimension(220, 45));
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setFont(new Font("Arial", Font.BOLD, 13));
        b.setForeground(active ? Color.WHITE : new Color(205, 215, 225));
        b.setBackground(active ? new Color(43, 53, 66) : SIDEBAR);
        b.setBorder(new EmptyBorder(0, 18, 0, 0));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addActionListener(e -> { if (action != null) action.run(); });
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (!active) {
                    b.setBackground(new Color(42, 51, 63));
                    b.setForeground(Color.WHITE);
                }
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (!active) {
                    b.setBackground(SIDEBAR);
                    b.setForeground(new Color(205, 215, 225));
                }
            }
        });
        side.add(b);
        side.add(Box.createVerticalStrut(3));
    }
}
