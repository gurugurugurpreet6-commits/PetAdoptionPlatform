package petadoption;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.*;

public class AdopterDashboard extends JFrame {
    private final Color navy = new Color(15, 31, 73);
    private final Color blue = new Color(42, 111, 255);
    private final Color purple = new Color(124, 66, 225);
    private final Color pink = new Color(235, 65, 123);
    private final Color green = new Color(0, 164, 124);
    private final Color bg = new Color(246, 249, 255);
    private final Color ink = new Color(20, 43, 105);
    private final Color muted = new Color(83, 108, 158);

    private JLabel availableValue, applicationsValue, favoritesValue, approvedValue;

    public AdopterDashboard() {
        Theme.apply();
        setTitle("Adopter Dashboard - Online Pet Adoption Platform");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1360, 850);
        setMinimumSize(new Dimension(1100, 720));
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(bg);
        root.add(sidebar(), BorderLayout.WEST);
        root.add(mainPanel(), BorderLayout.CENTER);
        setContentPane(root);

        refreshStatistics();
        setVisible(true);
    }

    private JPanel sidebar() {
        JPanel side = new JPanel(new BorderLayout());
        side.setPreferredSize(new Dimension(255, 0));
        side.setBackground(navy);

        JPanel menu = new JPanel();
        menu.setOpaque(false);
        menu.setBorder(new EmptyBorder(25, 17, 10, 17));
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));

        JPanel brand = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        brand.setOpaque(false);
        JLabel paw = new JLabel("🐾");
        paw.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 42));
        brand.add(paw);

        JPanel words = new JPanel();
        words.setOpaque(false);
        words.setLayout(new BoxLayout(words, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("PET ADOPTION");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        JLabel sub = new JLabel("ADOPTER PORTAL");
        sub.setFont(new Font("Segoe UI", Font.BOLD, 11));
        sub.setForeground(new Color(173, 202, 255));
        words.add(title);
        words.add(Box.createVerticalStrut(3));
        words.add(sub);
        brand.add(words);
        menu.add(brand);
        menu.add(Box.createVerticalStrut(32));

        JButton dashboard = nav("⌂", "Dashboard", true);
        dashboard.addActionListener(e -> refreshStatistics());
        menu.add(dashboard);
        menu.add(nav("🐾", "Browse Pets", false, () -> new AdopterBrowsePets()));
        menu.add(nav("▤", "My Applications", false, () -> new AdopterMyApplications()));
        menu.add(nav("♡", "My Favorites", false, () -> new AdopterFavorites()));
        menu.add(nav("☏", "Communication", false, () -> new AdopterCommunication()));

        side.add(menu, BorderLayout.NORTH);

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setBorder(new EmptyBorder(8, 17, 18, 17));
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));

        JLabel petsImage = imageLabel("pet_images/dashboard_sidebar_pets.png", 220, 175);
        petsImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottom.add(petsImage);
        bottom.add(Box.createVerticalStrut(8));

        JLabel slogan = new JLabel("<html><div style='text-align:center;color:white;font-size:17px'><i>Adopt, don't shop.</i> ♡</div></html>");
        slogan.setAlignmentX(Component.CENTER_ALIGNMENT);
        slogan.setBorder(new EmptyBorder(0, 0, 16, 0));
        bottom.add(slogan);

        JPanel profile = new JPanel(new BorderLayout(10, 0));
        profile.setBackground(new Color(27, 49, 101));
        profile.setBorder(new EmptyBorder(13, 12, 13, 8));
        JLabel avatar = new JLabel("👤");
        avatar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 27));
        profile.add(avatar, BorderLayout.WEST);

        JPanel profileWords = new JPanel();
        profileWords.setOpaque(false);
        profileWords.setLayout(new BoxLayout(profileWords, BoxLayout.Y_AXIS));
        String name = Session.name == null || Session.name.isBlank() ? "Adopter" : Session.name;
        JLabel person = new JLabel(name);
        person.setFont(new Font("Segoe UI", Font.BOLD, 13));
        person.setForeground(Color.WHITE);
        JLabel role = new JLabel("Pet Adopter");
        role.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        role.setForeground(new Color(180, 202, 244));
        profileWords.add(person);
        profileWords.add(Box.createVerticalStrut(4));
        profileWords.add(role);
        profile.add(profileWords, BorderLayout.CENTER);
        bottom.add(profile);
        bottom.add(Box.createVerticalStrut(12));

        JButton logout = new JButton("↪   Logout");
        logout.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        logout.setBackground(new Color(117, 48, 91));
        logout.setForeground(new Color(255, 190, 210));
        logout.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logout.setFocusPainted(false);
        logout.setBorderPainted(false);
        logout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logout.addActionListener(e -> logout());
        bottom.add(logout);
        side.add(bottom, BorderLayout.SOUTH);
        return side;
    }

    private JLabel imageLabel(String path, int width, int height) {
        JLabel label = new JLabel();
        label.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon icon = loadScaledImage(path, width, height);
        if (icon != null) {
            label.setIcon(icon);
        } else {
            label.setText("<html><div style='text-align:center;color:#b8ccf5'>🐶 &nbsp; 🐱<br>Pet Adoption</div></html>");
            label.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        }
        label.setPreferredSize(new Dimension(width, height));
        return label;
    }

    private ImageIcon loadScaledImage(String path, int width, int height) {
        File file = new File(path);
        if (!file.exists()) {
            file = new File("PetAdoptionPlatform/" + path);
        }
        if (!file.exists()) return null;
        Image image = new ImageIcon(file.getAbsolutePath()).getImage()
                .getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    private JButton nav(String icon, String label, boolean selected) {
        return nav(icon, label, selected, null);
    }

    private JButton nav(String icon, String label, boolean selected, Runnable action) {
        JButton b = new JButton(icon + "    " + label);
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setForeground(Color.WHITE);
        b.setBackground(selected ? blue : navy);
        b.setBorder(new EmptyBorder(13, 18, 13, 10));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (!selected) b.setBackground(new Color(27, 49, 101));
            }
            public void mouseExited(MouseEvent e) {
                if (!selected) b.setBackground(navy);
            }
        });
        if (action != null) b.addActionListener(e -> action.run());
        return b;
    }

    private JPanel mainPanel() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(bg);

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(new EmptyBorder(19, 28, 19, 28));
        JLabel pageTitle = new JLabel("Adopter Dashboard");
        pageTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        pageTitle.setForeground(ink);
        top.add(pageTitle, BorderLayout.WEST);

        JLabel date = new JLabel("♧  Notifications       ▣  " +
                java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy")));
        date.setFont(new Font("Segoe UI", Font.BOLD, 13));
        date.setForeground(muted);
        top.add(date, BorderLayout.EAST);
        main.add(top, BorderLayout.NORTH);

        JPanel page = new JPanel();
        page.setBackground(bg);
        page.setBorder(new EmptyBorder(22, 26, 24, 26));
        page.setLayout(new BoxLayout(page, BoxLayout.Y_AXIS));

        page.add(hero());
        page.add(Box.createVerticalStrut(22));

        JPanel stats = new JPanel(new GridLayout(1, 4, 18, 0));
        stats.setOpaque(false);
        stats.add(stat("🐾", "AVAILABLE PETS", blue, new Color(236, 245, 255), "0", "New pets"));
        stats.add(stat("▤", "MY APPLICATIONS", purple, new Color(246, 240, 255), "0", "Track requests"));
        stats.add(stat("♡", "MY FAVORITES", pink, new Color(255, 239, 246), "0", "Saved for later"));
        stats.add(stat("✓", "APPROVED", green, new Color(232, 250, 246), "0", "Adoption progress"));
        stats.setMaximumSize(new Dimension(Integer.MAX_VALUE, 135));
        page.add(stats);
        page.add(Box.createVerticalStrut(27));

        JPanel heading = new JPanel(new BorderLayout());
        heading.setOpaque(false);
        JLabel quick = new JLabel("🚀  Quick Actions");
        quick.setFont(new Font("Segoe UI", Font.BOLD, 21));
        quick.setForeground(ink);
        JLabel slogan = new JLabel("Small steps. Big changes. ♡");
        slogan.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        slogan.setForeground(new Color(54, 119, 220));
        heading.add(quick, BorderLayout.WEST);
        heading.add(slogan, BorderLayout.EAST);
        heading.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        page.add(heading);
        page.add(Box.createVerticalStrut(15));

        JPanel actions = new JPanel(new GridLayout(1, 4, 18, 0));
        actions.setOpaque(false);
        actions.add(action("🐾", "Browse Pets", "Explore approved pets looking for a forever home.",
                blue, new Color(237, 246, 255), () -> new AdopterBrowsePets()));
        actions.add(action("▤", "My Applications", "Track all your adoption applications and status.",
                purple, new Color(246, 241, 255), () -> new AdopterMyApplications()));
        actions.add(action("♡", "My Favorites", "View the pets you have saved for later.",
                pink, new Color(255, 240, 247), () -> new AdopterFavorites()));
        actions.add(action("☏", "Communication", "Talk directly with shelters about adoption.",
                green, new Color(233, 250, 246), () -> new AdopterCommunication()));
        actions.setMaximumSize(new Dimension(Integer.MAX_VALUE, 225));
        page.add(actions);
        page.add(Box.createVerticalStrut(18));

        JPanel tip = new JPanel(new BorderLayout(14, 0));
        tip.setBackground(new Color(255, 246, 222));
        tip.setBorder(new EmptyBorder(17, 20, 17, 20));
        JLabel bulb = new JLabel("☼");
        bulb.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 30));
        bulb.setForeground(new Color(230, 153, 0));
        tip.add(bulb, BorderLayout.WEST);
        JLabel message = new JLabel("<html><b style='color:#9b5b00'>Adoption Tip:</b> " +
                "<span style='color:#596b88'>Take time to learn about a pet's personality and needs before applying.</span></html>");
        message.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tip.add(message, BorderLayout.CENTER);
        JLabel tails = new JLabel("🐾  Happy Tails ♡");
        tails.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        tails.setForeground(new Color(171, 105, 0));
        tip.add(tails, BorderLayout.EAST);
        tip.setMaximumSize(new Dimension(Integer.MAX_VALUE, 66));
        page.add(tip);

        JScrollPane scroll = new JScrollPane(page);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(bg);
        scroll.getVerticalScrollBar().setUnitIncrement(18);
        main.add(scroll, BorderLayout.CENTER);
        return main;
    }

    private JPanel hero() {
        JPanel wrapper = new JPanel(new BorderLayout(16, 0));
        wrapper.setOpaque(false);

        JPanel banner = new JPanel(new BorderLayout(15, 0));
        banner.setBackground(new Color(226, 244, 255));
        banner.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(205, 229, 255)),
                new EmptyBorder(18, 22, 18, 22)));

        JPanel words = new JPanel();
        words.setOpaque(false);
        words.setLayout(new BoxLayout(words, BoxLayout.Y_AXIS));
        JLabel paw = new JLabel("🐾");
        paw.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        JLabel title = new JLabel("<html>Find Your<br><span style='color:#6d46e8'>Perfect Companion</span></html>");
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(ink);
        JLabel sub = new JLabel("<html>Discover, connect and give a loving home to<br>" +
                "pets in need. Every adoption changes a life.</html>");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        sub.setForeground(muted);
        words.add(paw);
        words.add(Box.createVerticalStrut(3));
        words.add(title);
        words.add(Box.createVerticalStrut(9));
        words.add(sub);
        banner.add(words, BorderLayout.CENTER);

        JLabel animals = imageLabel("pet_images/dashboard_hero_pets.png", 360, 180);
        animals.setPreferredSize(new Dimension(360, 180));
        banner.add(animals, BorderLayout.EAST);
        wrapper.add(banner, BorderLayout.CENTER);

        JPanel kindness = new JPanel();
        kindness.setBackground(new Color(239, 247, 255));
        kindness.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(205, 229, 255)),
                new EmptyBorder(15, 17, 15, 17)));
        kindness.setLayout(new BoxLayout(kindness, BoxLayout.Y_AXIS));
        JLabel paw2 = new JLabel("🐾");
        paw2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 27));
        JLabel words2 = new JLabel("<html><i>A little kindness<br>can make a<br>big difference.</i><br><br>♡</html>");
        words2.setFont(new Font("Segoe UI", Font.ITALIC, 17));
        words2.setForeground(muted);
        kindness.add(paw2);
        kindness.add(Box.createVerticalStrut(6));
        kindness.add(words2);
        kindness.setPreferredSize(new Dimension(185, 200));
        wrapper.add(kindness, BorderLayout.EAST);
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));
        return wrapper;
    }

    private JPanel stat(String icon, String title, Color accent, Color tint, String initial, String footer) {
        JPanel card = new JPanel(new BorderLayout(9, 8));
        card.setBackground(tint);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 70)),
                new EmptyBorder(16, 14, 14, 10)));
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        iconLabel.setForeground(accent);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setPreferredSize(new Dimension(48, 52));
        card.add(iconLabel, BorderLayout.WEST);

        JPanel labels = new JPanel();
        labels.setOpaque(false);
        labels.setLayout(new BoxLayout(labels, BoxLayout.Y_AXIS));
        JLabel number = new JLabel(initial);
        number.setFont(new Font("Segoe UI", Font.BOLD, 29));
        number.setForeground(ink);
        JLabel name = new JLabel(title);
        name.setFont(new Font("Segoe UI", Font.BOLD, 10));
        name.setForeground(muted);
        JLabel foot = new JLabel("↑ " + footer);
        foot.setFont(new Font("Segoe UI", Font.BOLD, 11));
        foot.setForeground(accent);
        labels.add(number);
        labels.add(name);
        labels.add(Box.createVerticalStrut(8));
        labels.add(foot);
        card.add(labels, BorderLayout.CENTER);
        card.setPreferredSize(new Dimension(210, 132));

        if (title.equals("AVAILABLE PETS")) availableValue = number;
        if (title.equals("MY APPLICATIONS")) applicationsValue = number;
        if (title.equals("MY FAVORITES")) favoritesValue = number;
        if (title.equals("APPROVED")) approvedValue = number;
        return card;
    }

    private JPanel action(String icon, String title, String description, Color accent, Color tint, Runnable openAction) {
        JPanel card = new JPanel(new BorderLayout(8, 10));
        card.setBackground(tint);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 70)),
                new EmptyBorder(15, 16, 15, 15)));

        JLabel badge = new JLabel(icon);
        badge.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 31));
        badge.setForeground(accent);
        badge.setHorizontalAlignment(SwingConstants.CENTER);
        badge.setPreferredSize(new Dimension(46, 43));
        card.add(badge, BorderLayout.NORTH);

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        JLabel heading = new JLabel(title);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 16));
        heading.setForeground(ink);
        JLabel desc = new JLabel("<html>" + description + "</html>");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        desc.setForeground(muted);
        text.add(heading);
        text.add(Box.createVerticalStrut(7));
        text.add(desc);
        card.add(text, BorderLayout.CENTER);

        JButton open = new JButton("Open   →");
        open.setFont(new Font("Segoe UI", Font.BOLD, 13));
        open.setForeground(Color.WHITE);
        open.setBackground(accent);
        open.setBorder(new EmptyBorder(9, 16, 9, 16));
        open.setFocusPainted(false);
        open.setBorderPainted(false);
        open.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        open.addActionListener(e -> openAction.run());
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        row.setOpaque(false);
        row.add(open);
        card.add(row, BorderLayout.SOUTH);

        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(accent, 2), new EmptyBorder(14, 15, 14, 14)));
            }
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 70)),
                        new EmptyBorder(15, 16, 15, 15)));
            }
        });
        return card;
    }

    private void refreshStatistics() {
        int[] v = {0, 0, 0, 0};
        String[] sql = {
                "SELECT COUNT(*) FROM pets WHERE status='Approved'",
                "SELECT COUNT(*) FROM adoption_requests WHERE adopter_id=?",
                "SELECT COUNT(*) FROM favorites WHERE adopter_id=?",
                "SELECT COUNT(*) FROM adoption_requests WHERE adopter_id=? AND status='Approved'"
        };

        try (Connection con = DatabaseConnection.getConnection()) {
            for (int i = 0; i < sql.length; i++) {
                try (PreparedStatement ps = con.prepareStatement(sql[i])) {
                    if (i > 0) ps.setInt(1, Session.userId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) v[i] = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Adopter statistics error: " + ex.getMessage());
        }

        if (availableValue != null) availableValue.setText(String.valueOf(v[0]));
        if (applicationsValue != null) applicationsValue.setText(String.valueOf(v[1]));
        if (favoritesValue != null) favoritesValue.setText(String.valueOf(v[2]));
        if (approvedValue != null) approvedValue.setText(String.valueOf(v[3]));
    }

    private void logout() {
        int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?",
                "Confirm Logout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (answer == JOptionPane.YES_OPTION) {
            Session.userId = 0;
            Session.name = null;
            Session.role = null;
            dispose();
            new Login();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdopterDashboard::new);
    }
}
