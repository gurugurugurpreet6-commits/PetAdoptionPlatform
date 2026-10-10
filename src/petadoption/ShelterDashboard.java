
package petadoption;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

public class ShelterDashboard extends JFrame {

    private final Color navy = new Color(18, 32, 70);
    private final Color blue = new Color(49, 112, 245);
    private final Color purple = new Color(135, 75, 225);
    private final Color green = new Color(15, 170, 125);
    private final Color orange = new Color(240, 137, 35);
    private final Color bg = new Color(244, 247, 252);
    private final Color ink = new Color(26, 48, 95);
    private final Color muted = new Color(103, 122, 158);

    private JLabel petsValue, applicationsValue;
    private JLabel approvedValue, adoptedValue;

    public ShelterDashboard() {
        Theme.apply();

        setTitle("Shelter Dashboard - Online Pet Adoption Platform");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1360, 850);
        setMinimumSize(new Dimension(1100, 720));
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(bg);
        root.add(createSidebar(), BorderLayout.WEST);
        root.add(createMainContent(), BorderLayout.CENTER);

        setContentPane(root);
        refreshStatistics();
        setVisible(true);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(255, 0));
        sidebar.setBackground(navy);

        JPanel menu = new JPanel();
        menu.setOpaque(false);
        menu.setBorder(new EmptyBorder(28, 17, 10, 17));
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));

        JLabel paw = new JLabel("🐾");
        paw.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 38));
        paw.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel brand = new JLabel("PET ADOPTION");
        brand.setForeground(Color.WHITE);
        brand.setFont(new Font("Segoe UI", Font.BOLD, 19));
        brand.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel portal = new JLabel("SHELTER PORTAL");
        portal.setForeground(new Color(172, 201, 255));
        portal.setFont(new Font("Segoe UI", Font.BOLD, 11));
        portal.setAlignmentX(Component.LEFT_ALIGNMENT);

        menu.add(paw);
        menu.add(Box.createVerticalStrut(7));
        menu.add(brand);
        menu.add(Box.createVerticalStrut(4));
        menu.add(portal);
        menu.add(Box.createVerticalStrut(42));

        JLabel section = new JLabel("   SHELTER MANAGEMENT");
        section.setForeground(new Color(145, 169, 213));
        section.setFont(new Font("Segoe UI", Font.BOLD, 10));
        section.setAlignmentX(Component.LEFT_ALIGNMENT);
        menu.add(section);
        menu.add(Box.createVerticalStrut(13));

        menu.add(navButton("▦", "Dashboard", true,
                this::refreshStatistics));
        menu.add(Box.createVerticalStrut(7));

        menu.add(navButton("🐾", "My Pet Listings", false,
                () -> new ShelterPetListing()));
        menu.add(Box.createVerticalStrut(7));

        menu.add(navButton("▤", "Adoption Applications", false,
                () -> new ShelterAdoptionApplications()));
        menu.add(Box.createVerticalStrut(7));

        menu.add(navButton("☏", "Adopter Communication", false,
                () -> new ShelterCommunication()));
        menu.add(Box.createVerticalStrut(7));

        menu.add(navButton("▥", "Adoption Statistics", false,
                () -> new ShelterAdoptionStatistics()));

        sidebar.add(menu, BorderLayout.NORTH);

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setBorder(new EmptyBorder(12, 17, 22, 17));
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));

        JLabel animals = new JLabel(
                "<html><div style='text-align:center;"
                        + "color:white;font-size:27px'>🐶 &nbsp; 🐱"
                        + "<br><span style='font-size:13px'>"
                        + "<i>Every pet deserves a home.</i></span></div></html>"
        );
        animals.setAlignmentX(Component.CENTER_ALIGNMENT);
        animals.setBorder(new EmptyBorder(0, 0, 23, 0));
        bottom.add(animals);

        JPanel profile = new JPanel(new BorderLayout(10, 0));
        profile.setBackground(new Color(30, 51, 98));
        profile.setBorder(new EmptyBorder(13, 12, 13, 8));

        JLabel avatar = new JLabel("🏠");
        avatar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 27));
        profile.add(avatar, BorderLayout.WEST);

        JPanel profileText = new JPanel();
        profileText.setOpaque(false);
        profileText.setLayout(new BoxLayout(profileText, BoxLayout.Y_AXIS));

        String name = Session.name == null || Session.name.isBlank()
                ? "Shelter Account" : Session.name;

        JLabel nameLabel = new JLabel(name);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));

        JLabel roleLabel = new JLabel("Verified Shelter");
        roleLabel.setForeground(new Color(174, 199, 239));
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        profileText.add(nameLabel);
        profileText.add(Box.createVerticalStrut(5));
        profileText.add(roleLabel);
        profile.add(profileText, BorderLayout.CENTER);

        bottom.add(profile);
        bottom.add(Box.createVerticalStrut(13));

        JButton logout = new JButton("↪   Logout");
        styleButton(logout, new Color(177, 62, 113));
        logout.setForeground(Color.WHITE);
        logout.setAlignmentX(Component.CENTER_ALIGNMENT);
        logout.setMaximumSize(new Dimension(Integer.MAX_VALUE, 43));
        logout.addActionListener(e -> logout());

        bottom.add(logout);
        sidebar.add(bottom, BorderLayout.SOUTH);

        return sidebar;
    }

    private JButton navButton(String icon, String text,
                              boolean selected, Runnable action) {
        JButton button = new JButton(icon + "    " + text);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(selected ? blue : navy);
        button.setBorder(new EmptyBorder(13, 13, 13, 8));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 47));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        if (!selected) {
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    button.setBackground(new Color(36, 57, 105));
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    button.setBackground(navy);
                }
            });
        }

        button.addActionListener(e -> action.run());
        return button;
    }

    private JPanel createMainContent() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(bg);

        JPanel topbar = new JPanel(new BorderLayout());
        topbar.setBackground(Color.WHITE);
        topbar.setBorder(new EmptyBorder(20, 28, 20, 28));

        JLabel title = new JLabel("Shelter Dashboard");
        title.setForeground(ink);
        title.setFont(new Font("Segoe UI", Font.BOLD, 23));

        JLabel date = new JLabel(
                "●  SHELTER ONLINE     |     "
                        + java.time.LocalDate.now().format(
                        java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy"))
        );
        date.setForeground(green);
        date.setFont(new Font("Segoe UI", Font.BOLD, 12));

        topbar.add(title, BorderLayout.WEST);
        topbar.add(date, BorderLayout.EAST);
        main.add(topbar, BorderLayout.NORTH);

        JPanel page = new JPanel();
        page.setBackground(bg);
        page.setBorder(new EmptyBorder(25, 28, 28, 28));
        page.setLayout(new BoxLayout(page, BoxLayout.Y_AXIS));

        page.add(createWelcomeBanner());
        page.add(Box.createVerticalStrut(24));

        JPanel stats = new JPanel(new GridLayout(1, 4, 17, 0));
        stats.setOpaque(false);

        stats.add(statCard("🐾", "MY PETS", green,
                new Color(232, 250, 243), "0"));
        stats.add(statCard("▤", "APPLICATIONS", blue,
                new Color(235, 244, 255), "0"));
        stats.add(statCard("✓", "APPROVED", purple,
                new Color(245, 238, 255), "0"));
        stats.add(statCard("♡", "ADOPTED", orange,
                new Color(255, 245, 231), "0"));

        stats.setMaximumSize(new Dimension(Integer.MAX_VALUE, 133));
        page.add(stats);
        page.add(Box.createVerticalStrut(29));

        JPanel heading = new JPanel(new BorderLayout());
        heading.setOpaque(false);
        heading.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JLabel quick = new JLabel("⚡  Quick Actions");
        quick.setFont(new Font("Segoe UI", Font.BOLD, 21));
        quick.setForeground(ink);

        JLabel subtitle = new JLabel("Manage your shelter in one place");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(muted);

        heading.add(quick, BorderLayout.WEST);
        heading.add(subtitle, BorderLayout.EAST);
        page.add(heading);
        page.add(Box.createVerticalStrut(16));

        JPanel actions = new JPanel(new GridLayout(2, 2, 17, 16));
        actions.setOpaque(false);

        actions.add(actionCard("🐾", "My Pet Listings",
                "Add, edit and manage pets available for adoption.",
                green, new Color(235, 250, 243),
                () -> new ShelterPetListing()));

        actions.add(actionCard("▤", "Adoption Applications",
                "Review applications and respond to adopters.",
                blue, new Color(236, 245, 255),
                () -> new ShelterAdoptionApplications()));

        actions.add(actionCard("☏", "Adopter Communication",
                "Connect with people interested in your pets.",
                purple, new Color(246, 240, 255),
                () -> new ShelterCommunication()));

        actions.add(actionCard("▥", "Adoption Statistics",
                "Track applications, approvals and adoptions.",
                orange, new Color(255, 246, 233),
                () -> new ShelterAdoptionStatistics()));

        actions.setMaximumSize(new Dimension(Integer.MAX_VALUE, 245));
        page.add(actions);
        page.add(Box.createVerticalStrut(22));

        JPanel tip = new JPanel(new BorderLayout(13, 0));
        tip.setBackground(new Color(229, 248, 238));
        tip.setBorder(new EmptyBorder(17, 20, 17, 20));

        JLabel bulb = new JLabel("💡");
        bulb.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 25));

        JLabel tipText = new JLabel(
                "<html><b style='color:#137b53'>Shelter Tip:</b> "
                        + "<span style='color:#426d62'>Keep pet details and photos "
                        + "updated so adopters can find the right companion.</span></html>"
        );
        tipText.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        tip.add(bulb, BorderLayout.WEST);
        tip.add(tipText, BorderLayout.CENTER);
        tip.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));
        page.add(tip);

        JScrollPane scroll = new JScrollPane(page);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(bg);
        scroll.getVerticalScrollBar().setUnitIncrement(18);

        main.add(scroll, BorderLayout.CENTER);
        return main;
    }

    private JPanel createWelcomeBanner() {
        JPanel banner = new JPanel(new BorderLayout(18, 0));
        banner.setBackground(new Color(225, 241, 255));
        banner.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(199, 223, 255)),
                new EmptyBorder(23, 24, 23, 24)
        ));
        banner.setMaximumSize(new Dimension(Integer.MAX_VALUE, 155));

        JPanel words = new JPanel();
        words.setOpaque(false);
        words.setLayout(new BoxLayout(words, BoxLayout.Y_AXIS));

        String name = Session.name == null || Session.name.isBlank()
                ? "Shelter Partner" : Session.name;

        JLabel welcome = new JLabel("Welcome back, " + name + "!");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcome.setForeground(ink);

        JLabel heading = new JLabel("Every Pet Deserves a Loving Home");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 18));
        heading.setForeground(new Color(45, 104, 182));

        JLabel description = new JLabel(
                "Manage your pets, applications and adoption journey."
        );
        description.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        description.setForeground(muted);

        words.add(welcome);
        words.add(Box.createVerticalStrut(9));
        words.add(heading);
        words.add(Box.createVerticalStrut(7));
        words.add(description);

        banner.add(words, BorderLayout.CENTER);

        JLabel animals = new JLabel(
                "<html><div style='text-align:center;font-size:40px'>"
                        + "🐶 &nbsp; 🐱<br>"
                        + "<span style='font-size:12px;color:#3163a8'>"
                        + "<i>Better Together ♥</i></span></div></html>"
        );
        animals.setHorizontalAlignment(SwingConstants.CENTER);
        animals.setPreferredSize(new Dimension(220, 95));
        banner.add(animals, BorderLayout.EAST);

        return banner;
    }

    private JPanel statCard(String icon, String label,
                            Color accent, Color tint, String initial) {
        JPanel card = new JPanel(new BorderLayout(10, 0));
        card.setBackground(tint);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 5, 0, 0, accent),
                new EmptyBorder(16, 13, 16, 10)
        ));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
        iconLabel.setForeground(accent);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setPreferredSize(new Dimension(45, 50));
        card.add(iconLabel, BorderLayout.WEST);

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));

        JLabel value = new JLabel(initial);
        value.setFont(new Font("Segoe UI", Font.BOLD, 28));
        value.setForeground(ink);

        JLabel title = new JLabel(label);
        title.setFont(new Font("Segoe UI", Font.BOLD, 10));
        title.setForeground(muted);

        text.add(value);
        text.add(Box.createVerticalStrut(5));
        text.add(title);
        card.add(text, BorderLayout.CENTER);

        if (label.equals("MY PETS")) petsValue = value;
        if (label.equals("APPLICATIONS")) applicationsValue = value;
        if (label.equals("APPROVED")) approvedValue = value;
        if (label.equals("ADOPTED")) adoptedValue = value;

        return card;
    }

    private JPanel actionCard(String icon, String title, String description,
                              Color accent, Color tint, Runnable openAction) {
        JPanel card = new JPanel(new BorderLayout(12, 8));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(224, 231, 243)),
                new EmptyBorder(17, 17, 17, 17)
        ));

        JLabel symbol = new JLabel(icon);
        symbol.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
        symbol.setForeground(accent);
        symbol.setHorizontalAlignment(SwingConstants.CENTER);
        symbol.setPreferredSize(new Dimension(48, 48));
        card.add(symbol, BorderLayout.WEST);

        JPanel details = new JPanel();
        details.setOpaque(false);
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));

        JLabel name = new JLabel(title);
        name.setFont(new Font("Segoe UI", Font.BOLD, 15));
        name.setForeground(ink);

        JLabel desc = new JLabel(
                "<html><div style='width:235px'>" + description + "</div></html>"
        );
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        desc.setForeground(muted);

        details.add(name);
        details.add(Box.createVerticalStrut(8));
        details.add(desc);
        card.add(details, BorderLayout.CENTER);

        JButton open = new JButton("Open  →");
        styleButton(open, accent);
        open.setForeground(Color.WHITE);
        open.addActionListener(e -> openAction.run());

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonRow.setOpaque(false);
        buttonRow.add(open);
        card.add(buttonRow, BorderLayout.SOUTH);

        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return card;
    }

    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBorder(new EmptyBorder(10, 17, 10, 17));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void refreshStatistics() {
        int myPets = 0;
        int applications = 0;
        int approved = 0;
        int adopted = 0;

        String petSql =
                "SELECT COUNT(*) FROM pets WHERE shelter_id = ?";
        String applicationSql =
                "SELECT COUNT(*) FROM adoption_requests ar "
                        + "JOIN pets p ON ar.pet_id = p.pet_id "
                        + "WHERE p.shelter_id = ?";
        String approvedSql =
                "SELECT COUNT(*) FROM adoption_requests ar "
                        + "JOIN pets p ON ar.pet_id = p.pet_id "
                        + "WHERE p.shelter_id = ? AND ar.status = 'Approved'";
        String adoptedSql =
                "SELECT COUNT(*) FROM pets WHERE shelter_id = ? "
                        + "AND status = 'Adopted'";

        try (Connection con = DatabaseConnection.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(petSql)) {
                ps.setInt(1, Session.userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) myPets = rs.getInt(1);
                }
            }

            try (PreparedStatement ps = con.prepareStatement(applicationSql)) {
                ps.setInt(1, Session.userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) applications = rs.getInt(1);
                }
            }

            try (PreparedStatement ps = con.prepareStatement(approvedSql)) {
                ps.setInt(1, Session.userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) approved = rs.getInt(1);
                }
            }

            try (PreparedStatement ps = con.prepareStatement(adoptedSql)) {
                ps.setInt(1, Session.userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) adopted = rs.getInt(1);
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load shelter statistics.\n" + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        if (petsValue != null) petsValue.setText(String.valueOf(myPets));
        if (applicationsValue != null)
            applicationsValue.setText(String.valueOf(applications));
        if (approvedValue != null)
            approvedValue.setText(String.valueOf(approved));
        if (adoptedValue != null)
            adoptedValue.setText(String.valueOf(adopted));
    }

    private void logout() {
        int answer = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (answer == JOptionPane.YES_OPTION) {
            Session.userId = 0;
            Session.name = null;
            Session.role = null;
            dispose();
            new Login();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ShelterDashboard::new);
    }
}
