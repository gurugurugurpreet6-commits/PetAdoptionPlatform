package petadoption;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class AdminDashboard extends JFrame {

    // ================= COLORS =================

    private final Color BG = new Color(244, 247, 251);
    private final Color SIDEBAR = new Color(22, 28, 38);
    private final Color SIDEBAR_HOVER = new Color(38, 48, 63);

    private final Color TEXT = new Color(30, 38, 50);
    private final Color MUTED = new Color(110, 122, 138);

    private final Color BLUE = new Color(52, 152, 219);
    private final Color GREEN = new Color(39, 174, 96);
    private final Color ORANGE = new Color(243, 156, 18);
    private final Color PURPLE = new Color(142, 68, 173);

    public AdminDashboard() {

        Theme.apply();

        setTitle("Admin Control Center - Pet Adoption Platform");
        setSize(1150, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);

        // =====================================================
        // SIDEBAR
        // =====================================================

        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(245, 720));
        sidebar.setBackground(SIDEBAR);

        sidebar.setLayout(
                new BoxLayout(
                        sidebar,
                        BoxLayout.Y_AXIS
                )
        );

        // Logo symbol
        JLabel logo = new JLabel("PA");

        logo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        25
                )
        );

        logo.setForeground(Color.WHITE);

        logo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        logo.setOpaque(true);

        logo.setBackground(BLUE);

        logo.setPreferredSize(
                new Dimension(58, 58)
        );

        logo.setMaximumSize(
                new Dimension(58, 58)
        );

        logo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        sidebar.add(
                Box.createRigidArea(
                        new Dimension(0, 25)
                )
        );

        sidebar.add(logo);

        // Brand
        JLabel brand =
                new JLabel("PET ADOPTION");

        brand.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        19
                )
        );

        brand.setForeground(Color.WHITE);
        brand.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        sidebar.add(
                Box.createRigidArea(
                        new Dimension(0, 12)
                )
        );

        sidebar.add(brand);

        JLabel panelText =
                new JLabel("ADMIN CONTROL CENTER");

        panelText.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        10
                )
        );

        panelText.setForeground(
                new Color(145, 155, 170)
        );

        panelText.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        sidebar.add(panelText);

        sidebar.add(
                Box.createRigidArea(
                        new Dimension(0, 38)
                )
        );

        // Menu heading
        JLabel menuTitle =
                new JLabel("  MANAGEMENT");

        menuTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        menuTitle.setForeground(
                new Color(
                        105,
                        117,
                        132
                )
        );

        menuTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        sidebar.add(menuTitle);

        sidebar.add(
                Box.createRigidArea(
                        new Dimension(0, 12)
                )
        );

        // Sidebar buttons
        JButton usersButton =
                createSideButton(
                        "USERS",
                        BLUE
                );

        JButton petsButton =
                createSideButton(
                        "PETS",
                        GREEN
                );

        JButton analyticsButton =
                createSideButton(
                        "DATA",
                        ORANGE
                );

        JButton settingsButton =
                createSideButton(
                        "SETTINGS",
                        PURPLE
                );

        sidebar.add(usersButton);
        sidebar.add(petsButton);
        sidebar.add(analyticsButton);
        sidebar.add(settingsButton);

        // Navigation
        usersButton.addActionListener(
                e -> new UserManagement()
        );

        petsButton.addActionListener(
                e -> new PetListingManagement()
        );

        analyticsButton.addActionListener(
                e -> new Analytics()
        );

        settingsButton.addActionListener(
                e -> new SystemSettings()
        );

        sidebar.add(
                Box.createVerticalGlue()
        );

        // =====================================================
        // PROFILE
        // =====================================================

        JPanel profile =
                new JPanel(
                        new BorderLayout(12, 0)
                );

        profile.setMaximumSize(
                new Dimension(
                        220,
                        72
                )
        );

        profile.setBackground(
                new Color(
                        30,
                        38,
                        49
                )
        );

        profile.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        12,
                        10,
                        12
                )
        );

        JLabel avatar =
                new JLabel("A");

        avatar.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        avatar.setForeground(Color.WHITE);
        avatar.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        avatar.setOpaque(true);
        avatar.setBackground(PURPLE);

        avatar.setPreferredSize(
                new Dimension(
                        40,
                        40
                )
        );

        JPanel profileText =
                new JPanel();

        profileText.setOpaque(false);

        profileText.setLayout(
                new BoxLayout(
                        profileText,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel name =
                new JLabel(
                        Session.name != null
                                ? Session.name
                                : "Administrator"
                );

        name.setForeground(Color.WHITE);

        name.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        JLabel role =
                new JLabel("Administrator");

        role.setForeground(
                new Color(
                        145,
                        155,
                        170
                )
        );

        role.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        profileText.add(name);

        profileText.add(
                Box.createRigidArea(
                        new Dimension(0, 3)
                )
        );

        profileText.add(role);

        profile.add(
                avatar,
                BorderLayout.WEST
        );

        profile.add(
                profileText,
                BorderLayout.CENTER
        );

        sidebar.add(profile);

        sidebar.add(
                Box.createRigidArea(
                        new Dimension(0, 12)
                )
        );

        // Logout
        JButton logout =
                new JButton("LOGOUT");

        logout.setForeground(
                new Color(
                        255,
                        105,
                        105
                )
        );

        logout.setBackground(SIDEBAR);

        logout.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        logout.setBorderPainted(false);
        logout.setFocusPainted(false);

        logout.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        logout.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        logout.addActionListener(
                e -> logout()
        );

        sidebar.add(logout);

        sidebar.add(
                Box.createRigidArea(
                        new Dimension(0, 20)
                )
        );

        // =====================================================
        // MAIN CONTENT
        // =====================================================

        JPanel content =
                new JPanel(
                        new BorderLayout()
                );

        content.setBackground(BG);

        // =====================================================
        // HEADER
        // =====================================================

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(BG);

        header.setBorder(
                BorderFactory.createEmptyBorder(
                        28,
                        32,
                        18,
                        32
                )
        );

        JPanel heading =
                new JPanel();

        heading.setOpaque(false);

        heading.setLayout(
                new BoxLayout(
                        heading,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel dashboardTitle =
                new JLabel(
                        "Good to see you, " +
                                (
                                        Session.name != null
                                                ? Session.name
                                                : "Admin"
                                )
                );

        dashboardTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        dashboardTitle.setForeground(TEXT);

        JLabel subtitle =
                new JLabel(
                        "Manage your pet adoption platform from one place."
                );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        subtitle.setForeground(MUTED);

        heading.add(dashboardTitle);

        heading.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                6
                        )
                )
        );

        heading.add(subtitle);

        header.add(
                heading,
                BorderLayout.WEST
        );

        // System status
        JPanel onlinePanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                0,
                                8
                        )
                );

        onlinePanel.setOpaque(false);

        JLabel onlineDot =
                new JLabel("●");

        onlineDot.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                )
        );

        onlineDot.setForeground(GREEN);

        JLabel onlineText =
                new JLabel("SYSTEM ONLINE");

        onlineText.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        onlineText.setForeground(GREEN);

        onlinePanel.add(onlineDot);
        onlinePanel.add(onlineText);

        header.add(
                onlinePanel,
                BorderLayout.EAST
        );

        content.add(
                header,
                BorderLayout.NORTH
        );

        // =====================================================
        // BODY
        // =====================================================

        JPanel body =
                new JPanel();

        body.setBackground(BG);

        body.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        32,
                        25,
                        32
                )
        );

        body.setLayout(
                new BoxLayout(
                        body,
                        BoxLayout.Y_AXIS
                )
        );

        // =====================================================
        // STATISTICS
        // =====================================================

        JPanel stats =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                16,
                                0
                        )
                );

        stats.setBackground(BG);

        int[] counts =
                getStatistics();

        stats.add(
                createStatCard(
                        "TOTAL USERS",
                        String.valueOf(counts[0]),
                        "USERS",
                        BLUE
                )
        );

        stats.add(
                createStatCard(
                        "TOTAL PETS",
                        String.valueOf(counts[1]),
                        "PETS",
                        GREEN
                )
        );

        stats.add(
                createStatCard(
                        "APPLICATIONS",
                        String.valueOf(counts[2]),
                        "APPS",
                        PURPLE
                )
        );

        stats.add(
                createStatCard(
                        "ADOPTED PETS",
                        String.valueOf(counts[3]),
                        "DONE",
                        ORANGE
                )
        );

        stats.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        120
                )
        );

        body.add(stats);

        body.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                24
                        )
                )
        );

        // =====================================================
        // SECTION TITLE
        // =====================================================

        JLabel quickTitle =
                new JLabel(
                        "Quick Management"
                );

        quickTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        21
                )
        );

        quickTitle.setForeground(TEXT);

        quickTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        body.add(quickTitle);

        JLabel quickSubtitle =
                new JLabel(
                        "Access the main administration modules"
                );

        quickSubtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        quickSubtitle.setForeground(MUTED);

        quickSubtitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        body.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                3
                        )
                )
        );

        body.add(quickSubtitle);

        body.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                13
                        )
                )
        );

        // =====================================================
        // FEATURE CARDS
        // =====================================================

        JPanel cards =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                16,
                                16
                        )
                );

        cards.setBackground(BG);

        cards.add(
                createFeatureCard(
                        "USERS",
                        "User Management",
                        "Add, remove and manage platform users.",
                        BLUE,
                        usersButton
                )
        );

        cards.add(
                createFeatureCard(
                        "PETS",
                        "Pet Listings",
                        "Review and approve shelter pet listings.",
                        GREEN,
                        petsButton
                )
        );

        cards.add(
                createFeatureCard(
                        "DATA",
                        "Platform Analytics",
                        "View adoption and platform statistics.",
                        ORANGE,
                        analyticsButton
                )
        );

        cards.add(
                createFeatureCard(
                        "SET",
                        "System Settings",
                        "Control platform configuration and settings.",
                        PURPLE,
                        settingsButton
                )
        );

        cards.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        230
                )
        );

        body.add(cards);

        body.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                20
                        )
                )
        );

        // =====================================================
        // INFO BAR
        // =====================================================

        JPanel info =
                new JPanel(
                        new BorderLayout()
                );

        info.setBackground(
                new Color(
                        232,
                        242,
                        255
                )
        );

        info.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                0,
                                4,
                                0,
                                0,
                                BLUE
                        ),
                        BorderFactory.createEmptyBorder(
                                14,
                                16,
                                14,
                                16
                        )
                )
        );

        JLabel infoTitle =
                new JLabel(
                        "ADMIN TIP"
                );

        infoTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        infoTitle.setForeground(BLUE);

        JLabel infoText =
                new JLabel(
                        "Regularly review pending pet listings and adoption applications."
                );

        infoText.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        infoText.setForeground(
                new Color(
                        65,
                        80,
                        100
                )
        );

        JPanel infoContent =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                10,
                                0
                        )
                );

        infoContent.setOpaque(false);

        infoContent.add(infoTitle);
        infoContent.add(infoText);

        info.add(
                infoContent,
                BorderLayout.CENTER
        );

        info.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        55
                )
        );

        body.add(info);

        // =====================================================
        // SCROLL
        // =====================================================

        JScrollPane scroll =
                new JScrollPane(body);

        scroll.setBorder(null);
        scroll.setBackground(BG);

        scroll.getVerticalScrollBar()
                .setUnitIncrement(15);

        content.add(
                scroll,
                BorderLayout.CENTER
        );

        // =====================================================
        // ROOT
        // =====================================================

        root.add(
                sidebar,
                BorderLayout.WEST
        );

        root.add(
                content,
                BorderLayout.CENTER
        );

        add(root);

        setVisible(true);
    }

    // =========================================================
    // SIDEBAR BUTTON
    // =========================================================

    private JButton createSideButton(
            String text,
            Color accent
    ) {

        JButton button =
                new JButton();

        button.setLayout(
                new BorderLayout()
        );

        button.setMaximumSize(
                new Dimension(
                        220,
                        48
                )
        );

        button.setPreferredSize(
                new Dimension(
                        220,
                        48
                )
        );

        button.setBackground(SIDEBAR);

        button.setBorderPainted(false);
        button.setFocusPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        JLabel colorBar =
                new JLabel();

        colorBar.setOpaque(true);
        colorBar.setBackground(accent);

        colorBar.setPreferredSize(
                new Dimension(
                        4,
                        48
                )
        );

        JLabel label =
                new JLabel(
                        "   " + text
                );

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        label.setForeground(
                new Color(
                        205,
                        215,
                        225
                )
        );

        button.add(
                colorBar,
                BorderLayout.WEST
        );

        button.add(
                label,
                BorderLayout.CENTER
        );

        button.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                SIDEBAR_HOVER
                        );

                        label.setForeground(
                                Color.WHITE
                        );
                    }

                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                SIDEBAR
                        );

                        label.setForeground(
                                new Color(
                                        205,
                                        215,
                                        225
                                )
                        );
                    }
                }
        );

        return button;
    }

    // =========================================================
    // STAT CARD
    // =========================================================

    private JPanel createStatCard(
            String title,
            String value,
            String tag,
            Color accent
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                0,
                                0,
                                4,
                                0,
                                accent
                        ),
                        BorderFactory.createEmptyBorder(
                                14,
                                16,
                                14,
                                16
                        )
                )
        );

        JPanel top =
                new JPanel(
                        new BorderLayout()
                );

        top.setOpaque(false);

        JLabel tagLabel =
                new JLabel(tag);

        tagLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        10
                )
        );

        tagLabel.setForeground(accent);

        top.add(
                tagLabel,
                BorderLayout.WEST
        );

        JLabel dot =
                new JLabel("●");

        dot.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        dot.setForeground(accent);

        top.add(
                dot,
                BorderLayout.EAST
        );

        card.add(
                top,
                BorderLayout.NORTH
        );

        JLabel valueLabel =
                new JLabel(value);

        valueLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        29
                )
        );

        valueLabel.setForeground(TEXT);

        card.add(
                valueLabel,
                BorderLayout.CENTER
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        10
                )
        );

        titleLabel.setForeground(MUTED);

        card.add(
                titleLabel,
                BorderLayout.SOUTH
        );

        return card;
    }

    // =========================================================
    // FEATURE CARD
    // =========================================================

    private JPanel createFeatureCard(
            String tag,
            String title,
            String description,
            Color accent,
            JButton actionButton
    ) {

        // Light colour background
        Color lightColor =
                new Color(
                        Math.min(255, accent.getRed() + 215),
                        Math.min(255, accent.getGreen() + 215),
                        Math.min(255, accent.getBlue() + 215)
                );

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                14,
                                5
                        )
                );

        card.setBackground(lightColor);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        225,
                                        230,
                                        238
                                )
                        ),
                        BorderFactory.createEmptyBorder(
                                15,
                                16,
                                15,
                                15
                        )
                )
        );

        // Colour indicator
        JPanel indicator =
                new JPanel();

        indicator.setBackground(accent);

        indicator.setPreferredSize(
                new Dimension(
                        7,
                        90
                )
        );

        card.add(
                indicator,
                BorderLayout.WEST
        );

        // Text
        JPanel text =
                new JPanel();

        text.setOpaque(false);

        text.setLayout(
                new BoxLayout(
                        text,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel tagLabel =
                new JLabel(tag);

        tagLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        10
                )
        );

        tagLabel.setForeground(accent);

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        17
                )
        );

        titleLabel.setForeground(TEXT);

        JLabel descriptionLabel =
                new JLabel(
                        "<html>" +
                                description +
                                "</html>"
                );

        descriptionLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        descriptionLabel.setForeground(
                new Color(
                        95,
                        105,
                        120
                )
        );

        text.add(tagLabel);

        text.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                4
                        )
                )
        );

        text.add(titleLabel);

        text.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                5
                        )
                )
        );

        text.add(descriptionLabel);

        card.add(
                text,
                BorderLayout.CENTER
        );

        // =====================================================
        // FILLED OPEN BUTTON
        // =====================================================

        JButton openButton =
                new JButton("OPEN");

        openButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        openButton.setForeground(Color.WHITE);

        // THIS IS THE IMPORTANT CHANGE
        openButton.setBackground(accent);

        openButton.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        16,
                        10,
                        16
                )
        );

        openButton.setFocusPainted(false);
        openButton.setBorderPainted(false);

        openButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        openButton.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        openButton.setBackground(
                                accent.darker()
                        );
                    }

                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {

                        openButton.setBackground(
                                accent
                        );
                    }
                }
        );

        openButton.addActionListener(
                e -> actionButton.doClick()
        );

        card.add(
                openButton,
                BorderLayout.EAST
        );

        return card;
    }

    // =========================================================
    // DATABASE STATISTICS
    // =========================================================

    private int[] getStatistics() {

        int[] result =
                new int[]{
                        0,
                        0,
                        0,
                        0
                };

        try {

            Connection con =
                    DatabaseConnection.getConnection();

            Statement st =
                    con.createStatement();

            ResultSet rs;

            // Total users
            rs =
                    st.executeQuery(
                            "SELECT COUNT(*) FROM users"
                    );

            if (rs.next()) {
                result[0] = rs.getInt(1);
            }

            rs.close();

            // Total pets
            rs =
                    st.executeQuery(
                            "SELECT COUNT(*) FROM pets"
                    );

            if (rs.next()) {
                result[1] = rs.getInt(1);
            }

            rs.close();

            // Applications
            rs =
                    st.executeQuery(
                            "SELECT COUNT(*) FROM adoption_requests"
                    );

            if (rs.next()) {
                result[2] = rs.getInt(1);
            }

            rs.close();

            // Adopted pets
            rs =
                    st.executeQuery(
                            "SELECT COUNT(*) FROM pets " +
                                    "WHERE status='Adopted'"
                    );

            if (rs.next()) {
                result[3] = rs.getInt(1);
            }

            rs.close();

            st.close();
            con.close();

        } catch (Exception e) {

            System.out.println(
                    "Statistics Error: " +
                            e.getMessage()
            );
        }

        return result;
    }

    // =========================================================
    // LOGOUT
    // =========================================================

    private void logout() {

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION
                );

        if (
                choice ==
                        JOptionPane.YES_OPTION
        ) {

            Session.userId = 0;
            Session.name = null;
            Session.role = null;

            dispose();

            new Login();
        }
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                AdminDashboard::new
        );
    }
}