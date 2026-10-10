package petadoption;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class UserManagement extends JFrame {

    // ================= COLORS =================

    private final Color BG = new Color(242, 245, 249);
    private final Color SIDEBAR = new Color(24, 30, 38);
    private final Color CARD = Color.WHITE;
    private final Color TEXT = new Color(35, 42, 52);
    private final Color MUTED = new Color(120, 130, 145);

    private final Color BLUE = new Color(52, 152, 219);
    private final Color GREEN = new Color(46, 204, 113);
    private final Color RED = new Color(231, 76, 60);
    private final Color ORANGE = new Color(243, 156, 18);
    private final Color PURPLE = new Color(155, 89, 182);

    // ================= TABLE =================

    private JTable table;
    private DefaultTableModel model;

    // ================= STAT LABELS =================

    private JLabel totalUsersLabel;
    private JLabel sheltersLabel;
    private JLabel adoptersLabel;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public UserManagement() {

        Theme.apply();

        setTitle("User Management - Pet Adoption Platform");
        setSize(1150, 720);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        // Logo

        JLabel logo = new JLabel("PA");

        logo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        logo.setForeground(Color.WHITE);
        logo.setHorizontalAlignment(SwingConstants.CENTER);

        logo.setOpaque(true);
        logo.setBackground(BLUE);

        logo.setPreferredSize(
                new Dimension(65, 65)
        );

        logo.setMaximumSize(
                new Dimension(65, 65)
        );

        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(
                Box.createVerticalStrut(25)
        );

        sidebar.add(logo);

        sidebar.add(
                Box.createVerticalStrut(12)
        );

        JLabel brand = new JLabel("PET ADOPTION");

        brand.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        19
                )
        );

        brand.setForeground(Color.WHITE);
        brand.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(brand);

        JLabel subtitle = new JLabel("ADMIN CONTROL");

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        10
                )
        );

        subtitle.setForeground(
                new Color(
                        145,
                        155,
                        170
                )
        );

        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(subtitle);

        sidebar.add(
                Box.createVerticalStrut(35)
        );

        // =====================================================
        // SIDEBAR BUTTONS
        // =====================================================

        JButton dashboardButton =
                createSideButton("⌂  Dashboard");

        JButton usersButton =
                createSideButton("▣  User Management");

        JButton petsButton =
                createSideButton("🐾  Pet Listings");

        JButton settingsButton =
                createSideButton("⚙  System Settings");

        JButton analyticsButton =
                createSideButton("▥  Analytics");

        // Highlight User Management

        usersButton.setBackground(
                new Color(
                        43,
                        53,
                        66
                )
        );

        usersButton.setForeground(Color.WHITE);

        sidebar.add(dashboardButton);
        sidebar.add(usersButton);
        sidebar.add(petsButton);
        sidebar.add(settingsButton);
        sidebar.add(analyticsButton);

        dashboardButton.addActionListener(
                e -> {
                    dispose();
                    new AdminDashboard();
                }
        );

        petsButton.addActionListener(
                e -> new PetListingManagement()
        );

        settingsButton.addActionListener(
                e -> new SystemSettings()
        );

        analyticsButton.addActionListener(
                e -> new Analytics()
        );

        sidebar.add(
                Box.createVerticalGlue()
        );

        // =====================================================
        // ADMIN PROFILE
        // =====================================================

        JPanel profile =
                new JPanel(
                        new BorderLayout(
                                10,
                                0
                        )
                );

        profile.setMaximumSize(
                new Dimension(
                        220,
                        65
                )
        );

        profile.setBackground(
                new Color(
                        31,
                        38,
                        48
                )
        );

        profile.setBorder(
                BorderFactory.createEmptyBorder(
                        8,
                        12,
                        8,
                        12
                )
        );

        JLabel avatar =
                new JLabel("A");

        avatar.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
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
                        42,
                        42
                )
        );

        profile.add(
                avatar,
                BorderLayout.WEST
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

        JLabel adminName =
                new JLabel(
                        Session.name != null
                                ? Session.name
                                : "Administrator"
                );

        adminName.setForeground(Color.WHITE);

        adminName.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        JLabel adminRole =
                new JLabel("Administrator");

        adminRole.setForeground(
                new Color(
                        145,
                        155,
                        170
                )
        );

        adminRole.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        profileText.add(adminName);
        profileText.add(adminRole);

        profile.add(
                profileText,
                BorderLayout.CENTER
        );

        sidebar.add(profile);

        sidebar.add(
                Box.createVerticalStrut(8)
        );

        JButton logout =
                new JButton("↪  Logout");

        logout.setForeground(
                new Color(
                        235,
                        100,
                        100
                )
        );

        logout.setBackground(SIDEBAR);

        logout.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
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
                Box.createVerticalStrut(20)
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

        header.setBackground(Color.WHITE);

        header.setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        32,
                        20,
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

        JLabel title =
                new JLabel("User Management");

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        title.setForeground(TEXT);

        JLabel description =
                new JLabel(
                        "Manage administrators, shelters and adopters"
                );

        description.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        description.setForeground(MUTED);

        heading.add(title);

        heading.add(
                Box.createVerticalStrut(5)
        );

        heading.add(description);

        header.add(
                heading,
                BorderLayout.WEST
        );

        JLabel usersIcon =
                new JLabel("USERS");

        usersIcon.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        usersIcon.setForeground(BLUE);

        header.add(
                usersIcon,
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
                        20,
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
        // STAT CARDS
        // =====================================================

        JPanel stats =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                15,
                                0
                        )
                );

        stats.setBackground(BG);

        totalUsersLabel =
                new JLabel("0");

        sheltersLabel =
                new JLabel("0");

        adoptersLabel =
                new JLabel("0");

        stats.add(
                createStatCard(
                        "TOTAL USERS",
                        totalUsersLabel,
                        BLUE
                )
        );

        stats.add(
                createStatCard(
                        "SHELTERS",
                        sheltersLabel,
                        GREEN
                )
        );

        stats.add(
                createStatCard(
                        "ADOPTERS",
                        adoptersLabel,
                        ORANGE
                )
        );

        stats.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        90
                )
        );

        body.add(stats);

        body.add(
                Box.createVerticalStrut(20)
        );

        // =====================================================
        // TABLE CARD
        // =====================================================

        JPanel tableCard =
                new JPanel(
                        new BorderLayout()
                );

        tableCard.setBackground(CARD);

        tableCard.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                225,
                                229,
                                235
                        )
                )
        );

        // Table Header

        JPanel tableHeader =
                new JPanel(
                        new BorderLayout()
                );

        tableHeader.setBackground(CARD);

        tableHeader.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        18,
                        12,
                        18
                )
        );

        JLabel registered =
                new JLabel(
                        "Registered Users"
                );

        registered.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        19
                )
        );

        registered.setForeground(TEXT);

        JLabel active =
                new JLabel(
                        "Active platform accounts"
                );

        active.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        active.setForeground(MUTED);

        tableHeader.add(
                registered,
                BorderLayout.WEST
        );

        tableHeader.add(
                active,
                BorderLayout.EAST
        );

        tableCard.add(
                tableHeader,
                BorderLayout.NORTH
        );

        // =====================================================
        // TABLE
        // =====================================================

        model =
                new DefaultTableModel(
                        new String[]{
                                "ID",
                                "Name",
                                "Email",
                                "Role",
                                "Status"
                        },
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        table =
                new JTable(model);

        table.setRowHeight(45);

        table.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        table.setForeground(TEXT);

        table.setBackground(Color.WHITE);

        table.setGridColor(
                new Color(
                        225,
                        229,
                        235
                )
        );

        table.setSelectionBackground(
                new Color(
                        225,
                        239,
                        252
                )
        );

        table.setSelectionForeground(TEXT);

        table.setShowVerticalLines(false);

        table.getTableHeader().setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        table.getTableHeader().setBackground(
                new Color(
                        38,
                        48,
                        65
                )
        );

        table.getTableHeader().setForeground(
                Color.WHITE
        );

        table.getTableHeader().setPreferredSize(
                new Dimension(
                        0,
                        42
                )
        );

        JScrollPane scroll =
                new JScrollPane(table);

        scroll.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        10,
                        10,
                        10
                )
        );

        tableCard.add(
                scroll,
                BorderLayout.CENTER
        );

        // =====================================================
        // BUTTON BAR
        // =====================================================

        JPanel buttons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                12,
                                12
                        )
                );

        buttons.setBackground(Color.WHITE);

        JButton addButton =
                createActionButton(
                        "＋ Add User",
                        GREEN
                );

        JButton editButton =
                createActionButton(
                        "✎ Edit User",
                        BLUE
                );

        JButton deleteButton =
                createActionButton(
                        "▣ Delete User",
                        RED
                );

        JButton refreshButton =
                createActionButton(
                        "↻ Refresh",
                        ORANGE
                );

        buttons.add(addButton);
        buttons.add(editButton);
        buttons.add(deleteButton);
        buttons.add(refreshButton);

        tableCard.add(
                buttons,
                BorderLayout.SOUTH
        );

        tableCard.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        430
                )
        );

        body.add(tableCard);

        content.add(
                body,
                BorderLayout.CENTER
        );

        root.add(
                sidebar,
                BorderLayout.WEST
        );

        root.add(
                content,
                BorderLayout.CENTER
        );

        add(root);

        // =====================================================
        // BUTTON ACTIONS
        // =====================================================

        addButton.addActionListener(
                e -> showUserDialog(
                        false,
                        -1
                )
        );

        editButton.addActionListener(
                e -> editSelectedUser()
        );

        deleteButton.addActionListener(
                e -> deleteSelectedUser()
        );

        refreshButton.addActionListener(
                e -> loadUsers()
        );

        // =====================================================
        // LOAD
        // =====================================================

        loadUsers();

        setVisible(true);
    }

    // =========================================================
    // SIDE BUTTON
    // =========================================================

    private JButton createSideButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setMaximumSize(
                new Dimension(
                        220,
                        45
                )
        );

        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        button.setForeground(
                new Color(
                        205,
                        215,
                        225
                )
        );

        button.setBackground(SIDEBAR);

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        18,
                        0,
                        0
                )
        );

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                new Color(
                                        42,
                                        51,
                                        63
                                )
                        );

                        button.setForeground(
                                Color.WHITE
                        );
                    }

                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {

                        if (
                                !button.getText()
                                        .contains(
                                                "User Management"
                                        )
                        ) {

                            button.setBackground(
                                    SIDEBAR
                            );

                            button.setForeground(
                                    new Color(
                                            205,
                                            215,
                                            225
                                    )
                            );
                        }
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
            JLabel valueLabel,
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
                                4,
                                0,
                                0,
                                accent
                        ),
                        BorderFactory.createEmptyBorder(
                                12,
                                18,
                                12,
                                18
                        )
                )
        );

        valueLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        valueLabel.setForeground(accent);

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

        JPanel text =
                new JPanel();

        text.setOpaque(false);

        text.setLayout(
                new BoxLayout(
                        text,
                        BoxLayout.Y_AXIS
                )
        );

        text.add(valueLabel);

        text.add(titleLabel);

        card.add(
                text,
                BorderLayout.CENTER
        );

        return card;
    }

    // =========================================================
    // ACTION BUTTON
    // =========================================================

    private JButton createActionButton(
            String text,
            Color color
    ) {

        JButton button =
                new JButton(text);

        button.setPreferredSize(
                new Dimension(
                        145,
                        42
                )
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        button.setForeground(Color.WHITE);

        button.setBackground(color);

        button.setBorderPainted(false);

        button.setFocusPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    // =========================================================
    // LOAD USERS + STATISTICS
    // =========================================================

    private void loadUsers() {

        model.setRowCount(0);

        try {

            Connection con =
                    DatabaseConnection.getConnection();

            // -------------------------------
            // USERS TABLE
            // -------------------------------

            String sql =
                    "SELECT user_id, name, email, role, status " +
                            "FROM users ORDER BY user_id";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            ResultSet rs =
                    pst.executeQuery();

            while (rs.next()) {

                model.addRow(
                        new Object[]{

                                rs.getInt("user_id"),

                                rs.getString("name"),

                                rs.getString("email"),

                                rs.getString("role"),

                                rs.getString("status")
                        }
                );
            }

            rs.close();
            pst.close();

            // -------------------------------
            // TOTAL USERS
            // -------------------------------

            Statement st =
                    con.createStatement();

            rs =
                    st.executeQuery(
                            "SELECT COUNT(*) FROM users"
                    );

            if (rs.next()) {

                totalUsersLabel.setText(
                        String.valueOf(
                                rs.getInt(1)
                        )
                );
            }

            rs.close();

            // -------------------------------
            // SHELTERS
            // -------------------------------

            rs =
                    st.executeQuery(
                            "SELECT COUNT(*) FROM users " +
                                    "WHERE role='Shelter'"
                    );

            if (rs.next()) {

                sheltersLabel.setText(
                        String.valueOf(
                                rs.getInt(1)
                        )
                );
            }

            rs.close();

            // -------------------------------
            // ADOPTERS
            // -------------------------------

            rs =
                    st.executeQuery(
                            "SELECT COUNT(*) FROM users " +
                                    "WHERE role='Adopter'"
                    );

            if (rs.next()) {

                adoptersLabel.setText(
                        String.valueOf(
                                rs.getInt(1)
                        )
                );
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load users.\n\n" +
                            e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // ADD / EDIT USER DIALOG
    // =========================================================

    private void showUserDialog(
            boolean editMode,
            int userId
    ) {

        JDialog dialog =
                new JDialog(
                        this,
                        editMode
                                ? "Edit User"
                                : "Add New User",
                        true
                );

        dialog.setSize(
                500,
                530
        );

        dialog.setLocationRelativeTo(this);

        dialog.setResizable(false);

        JPanel main =
                new JPanel(
                        new BorderLayout()
                );

        main.setBackground(BG);

        // =====================================================
        // DIALOG HEADER
        // =====================================================

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(SIDEBAR);

        header.setBorder(
                BorderFactory.createEmptyBorder(
                        18,
                        22,
                        18,
                        22
                )
        );

        JLabel icon =
                new JLabel(
                        editMode
                                ? "✎"
                                : "＋"
                );

        icon.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        30
                )
        );

        icon.setForeground(Color.WHITE);

        header.add(
                icon,
                BorderLayout.WEST
        );

        JPanel headerText =
                new JPanel();

        headerText.setOpaque(false);

        headerText.setLayout(
                new BoxLayout(
                        headerText,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel dialogTitle =
                new JLabel(
                        editMode
                                ? "Edit User"
                                : "Create New User"
                );

        dialogTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        dialogTitle.setForeground(Color.WHITE);

        JLabel dialogSubtitle =
                new JLabel(
                        editMode
                                ? "Update account information"
                                : "Add a new platform account"
                );

        dialogSubtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        dialogSubtitle.setForeground(
                new Color(
                        170,
                        180,
                        195
                )
        );

        headerText.add(dialogTitle);

        headerText.add(
                Box.createVerticalStrut(3)
        );

        headerText.add(dialogSubtitle);

        header.add(
                headerText,
                BorderLayout.CENTER
        );

        main.add(
                header,
                BorderLayout.NORTH
        );

        // =====================================================
        // FORM
        // =====================================================

        JPanel form =
                new JPanel();

        form.setBackground(Color.WHITE);

        form.setBorder(
                BorderFactory.createEmptyBorder(
                        22,
                        28,
                        15,
                        28
                )
        );

        form.setLayout(
                new GridBagLayout()
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.insets =
                new Insets(
                        7,
                        0,
                        7,
                        0
                );

        gbc.weightx = 1;

        JTextField nameField =
                new JTextField();

        JTextField emailField =
                new JTextField();

        JPasswordField passwordField =
                new JPasswordField();

        JComboBox<String> roleBox =
                new JComboBox<>(
                        new String[]{
                                "Admin",
                                "Shelter",
                                "Adopter"
                        }
                );

        styleField(nameField);
        styleField(emailField);
        styleField(passwordField);
        styleCombo(roleBox);

        addFormField(
                form,
                gbc,
                0,
                "Full Name",
                nameField
        );

        addFormField(
                form,
                gbc,
                1,
                "Email Address",
                emailField
        );

        addFormField(
                form,
                gbc,
                2,
                editMode
                        ? "Password (leave blank to keep current)"
                        : "Password",
                passwordField
        );

        addFormField(
                form,
                gbc,
                3,
                "Account Role",
                roleBox
        );

        // =====================================================
        // LOAD EDIT DATA
        // =====================================================

        if (editMode) {

            try {

                Connection con =
                        DatabaseConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(
                                "SELECT name, email, role " +
                                        "FROM users WHERE user_id=?"
                        );

                pst.setInt(
                        1,
                        userId
                );

                ResultSet rs =
                        pst.executeQuery();

                if (rs.next()) {

                    nameField.setText(
                            rs.getString("name")
                    );

                    emailField.setText(
                            rs.getString("email")
                    );

                    roleBox.setSelectedItem(
                            rs.getString("role")
                    );
                }

                rs.close();
                pst.close();
                con.close();

            } catch (Exception e) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Unable to load user.\n\n" +
                                e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                dialog.dispose();

                return;
            }
        }

        main.add(
                form,
                BorderLayout.CENTER
        );

        // =====================================================
        // BUTTON PANEL
        // =====================================================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                12
                        )
                );

        buttonPanel.setBackground(Color.WHITE);

        JButton cancel =
                createDialogButton(
                        "Cancel",
                        new Color(
                                120,
                                130,
                                145
                        )
                );

        JButton save =
                createDialogButton(
                        editMode
                                ? "Save Changes"
                                : "Create User",
                        editMode
                                ? BLUE
                                : GREEN
                );

        buttonPanel.add(cancel);
        buttonPanel.add(save);

        main.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        cancel.addActionListener(
                e -> dialog.dispose()
        );

        save.addActionListener(
                e -> {

                    String name =
                            nameField
                                    .getText()
                                    .trim();

                    String email =
                            emailField
                                    .getText()
                                    .trim();

                    String password =
                            new String(
                                    passwordField
                                            .getPassword()
                            );

                    String role =
                            roleBox
                                    .getSelectedItem()
                                    .toString();

                    if (
                            name.isEmpty()
                                    ||
                                    email.isEmpty()
                    ) {

                        JOptionPane.showMessageDialog(
                                dialog,
                                "Please fill all required fields.",
                                "Missing Information",
                                JOptionPane.WARNING_MESSAGE
                        );

                        return;
                    }

                    if (
                            !editMode
                                    &&
                                    password.isEmpty()
                    ) {

                        JOptionPane.showMessageDialog(
                                dialog,
                                "Password is required.",
                                "Missing Information",
                                JOptionPane.WARNING_MESSAGE
                        );

                        return;
                    }

                    saveUser(
                            dialog,
                            editMode,
                            userId,
                            name,
                            email,
                            password,
                            role
                    );
                }
        );

        main.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                220,
                                225,
                                232
                        )
                )
        );

        dialog.add(main);

        dialog.setVisible(true);
    }

    // =========================================================
    // FORM FIELD
    // =========================================================

    private void addFormField(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String labelText,
            JComponent field
    ) {

        gbc.gridx = 0;
        gbc.gridy = row;

        gbc.weighty = 0;

        JLabel label =
                new JLabel(labelText);

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        label.setForeground(TEXT);

        panel.add(
                label,
                gbc
        );

        gbc.gridy = row + 1;

        panel.add(
                field,
                gbc
        );

        gbc.gridy = row + 1;
    }

    // =========================================================
    // STYLE TEXT FIELD
    // =========================================================

    private void styleField(
            JTextField field
    ) {

        field.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        field.setPreferredSize(
                new Dimension(
                        400,
                        40
                )
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        215,
                                        220,
                                        228
                                )
                        ),
                        BorderFactory.createEmptyBorder(
                                5,
                                10,
                                5,
                                10
                        )
                )
        );
    }

    // =========================================================
    // STYLE COMBO
    // =========================================================

    private void styleCombo(
            JComboBox<String> box
    ) {

        box.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        box.setPreferredSize(
                new Dimension(
                        400,
                        40
                )
        );

        box.setBackground(Color.WHITE);
    }

    // =========================================================
    // DIALOG BUTTON
    // =========================================================

    private JButton createDialogButton(
            String text,
            Color color
    ) {

        JButton button =
                new JButton(text);

        button.setPreferredSize(
                new Dimension(
                        125,
                        40
                )
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        button.setForeground(Color.WHITE);

        button.setBackground(color);

        button.setBorderPainted(false);

        button.setFocusPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    // =========================================================
    // SAVE USER
    // =========================================================

    private void saveUser(
            JDialog dialog,
            boolean editMode,
            int userId,
            String name,
            String email,
            String password,
            String role
    ) {

        try {

            Connection con =
                    DatabaseConnection.getConnection();

            PreparedStatement pst;

            if (editMode) {

                if (password.isEmpty()) {

                    pst =
                            con.prepareStatement(
                                    "UPDATE users " +
                                            "SET name=?, email=?, role=? " +
                                            "WHERE user_id=?"
                            );

                    pst.setString(
                            1,
                            name
                    );

                    pst.setString(
                            2,
                            email
                    );

                    pst.setString(
                            3,
                            role
                    );

                    pst.setInt(
                            4,
                            userId
                    );

                } else {

                    pst =
                            con.prepareStatement(
                                    "UPDATE users " +
                                            "SET name=?, email=?, password=?, role=? " +
                                            "WHERE user_id=?"
                            );

                    pst.setString(
                            1,
                            name
                    );

                    pst.setString(
                            2,
                            email
                    );

                    pst.setString(
                            3,
                            password
                    );

                    pst.setString(
                            4,
                            role
                    );

                    pst.setInt(
                            5,
                            userId
                    );
                }

            } else {

                pst =
                        con.prepareStatement(
                                "INSERT INTO users " +
                                        "(name,email,password,role,status) " +
                                        "VALUES (?,?,?,?,?)"
                        );

                pst.setString(
                        1,
                        name
                );

                pst.setString(
                        2,
                        email
                );

                pst.setString(
                        3,
                        password
                );

                pst.setString(
                        4,
                        role
                );

                pst.setString(
                        5,
                        "Active"
                );
            }

            pst.executeUpdate();

            pst.close();
            con.close();

            dialog.dispose();

            loadUsers();

            JOptionPane.showMessageDialog(
                    this,
                    editMode
                            ? "User updated successfully."
                            : "User created successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SQLIntegrityConstraintViolationException e) {

            JOptionPane.showMessageDialog(
                    dialog,
                    "This email is already registered.",
                    "Duplicate Email",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    dialog,
                    "Operation failed.\n\n" +
                            e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // EDIT USER
    // =========================================================

    private void editSelectedUser() {

        int row =
                table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a user first.",
                    "Select User",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int userId =
                Integer.parseInt(
                        table.getValueAt(
                                row,
                                0
                        ).toString()
                );

        showUserDialog(
                true,
                userId
        );
    }

    // =========================================================
    // DELETE USER
    // =========================================================

    private void deleteSelectedUser() {

        int row =
                table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a user first.",
                    "Select User",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int userId =
                Integer.parseInt(
                        table.getValueAt(
                                row,
                                0
                        ).toString()
                );

        String name =
                table.getValueAt(
                        row,
                        1
                ).toString();

        // Don't allow current admin to delete himself

        if (
                userId ==
                        Session.userId
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "You cannot delete your own account.",
                    "Action Not Allowed",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete user \"" +
                                name +
                                "\"?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (
                choice !=
                        JOptionPane.YES_OPTION
        ) {

            return;
        }

        try {

            Connection con =
                    DatabaseConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(
                            "DELETE FROM users " +
                                    "WHERE user_id=?"
                    );

            pst.setInt(
                    1,
                    userId
            );

            pst.executeUpdate();

            pst.close();
            con.close();

            loadUsers();

            JOptionPane.showMessageDialog(
                    this,
                    "User deleted successfully.",
                    "Deleted",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "User could not be deleted.\n\n" +
                            "This user may have related pet or adoption records.",
                    "Delete Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
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

    public static void main(
            String[] args
    ) {

        new UserManagement();
    }
}