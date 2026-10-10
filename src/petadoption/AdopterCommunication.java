package petadoption;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdopterCommunication extends JFrame {

    private JTable messageTable;
    private DefaultTableModel model;

    private JTextField receiverField;
    private JTextArea messageArea;

    private final Color BG = new Color(244, 247, 251);
    private final Color CARD = Color.WHITE;
    private final Color SIDEBAR = new Color(24, 30, 38);
    private final Color BLUE = new Color(37, 99, 235);
    private final Color GREEN = new Color(22, 163, 74);
    private final Color TEXT = new Color(25, 35, 50);
    private final Color MUTED = new Color(100, 116, 139);

    public AdopterCommunication() {

        Theme.apply();

        setTitle("Communication - Online Pet Adoption Platform");
        setSize(1050, 700);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);

        // ================= SIDEBAR =================

        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(SIDEBAR);
        sidebar.setPreferredSize(new Dimension(210, 0));
        sidebar.setBorder(new EmptyBorder(30, 18, 25, 18));

        JLabel logo = new JLabel(
                "<html><div style='text-align:center;'>"
                        + "<font color='#FFFFFF' size='5'><b>PET ADOPTION</b></font><br>"
                        + "<font color='#94A3B8' size='2'>ADOPTER PORTAL</font>"
                        + "</div></html>",
                SwingConstants.CENTER
        );

        sidebar.add(logo, BorderLayout.NORTH);

        JPanel sideCenter = new JPanel();
        sideCenter.setOpaque(false);
        sideCenter.setLayout(new BoxLayout(sideCenter, BoxLayout.Y_AXIS));

        JLabel section = new JLabel("ADOPTION");
        section.setForeground(new Color(148, 163, 184));
        section.setFont(new Font("Arial", Font.BOLD, 11));
        section.setAlignmentX(Component.CENTER_ALIGNMENT);

        sideCenter.add(Box.createVerticalStrut(55));
        sideCenter.add(section);
        sideCenter.add(Box.createVerticalStrut(15));

        JButton browseBtn = sideButton("Browse Pets");
        JButton applicationsBtn = sideButton("My Applications");
        JButton favoritesBtn = sideButton("My Favorites");
        JButton communicationBtn = sideButton("Communication");

        communicationBtn.setBackground(new Color(45, 55, 70));

        sideCenter.add(browseBtn);
        sideCenter.add(Box.createVerticalStrut(7));
        sideCenter.add(applicationsBtn);
        sideCenter.add(Box.createVerticalStrut(7));
        sideCenter.add(favoritesBtn);
        sideCenter.add(Box.createVerticalStrut(7));
        sideCenter.add(communicationBtn);

        sidebar.add(sideCenter, BorderLayout.CENTER);

        JPanel userPanel = new JPanel();
        userPanel.setOpaque(false);
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));

        JLabel userName = new JLabel(
                Session.name == null ? "Adopter" : Session.name
        );
        userName.setForeground(Color.WHITE);
        userName.setFont(new Font("Arial", Font.BOLD, 14));
        userName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel role = new JLabel("Pet Adopter");
        role.setForeground(new Color(148, 163, 184));
        role.setFont(new Font("Arial", Font.PLAIN, 11));
        role.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton logout = new JButton("Logout");
        logout.setAlignmentX(Component.CENTER_ALIGNMENT);
        logout.setMaximumSize(new Dimension(150, 38));
        logout.setFocusPainted(false);
        logout.setForeground(Color.WHITE);
        logout.setBackground(new Color(55, 65, 81));
        logout.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        logout.setCursor(new Cursor(Cursor.HAND_CURSOR));

        logout.addActionListener(e -> {
            Session.userId = 0;
            Session.name = null;
            Session.role = null;

            dispose();
            new Login();
        });

        userPanel.add(userName);
        userPanel.add(Box.createVerticalStrut(4));
        userPanel.add(role);
        userPanel.add(Box.createVerticalStrut(15));
        userPanel.add(logout);

        sidebar.add(userPanel, BorderLayout.SOUTH);

        // ================= MAIN CONTENT =================

        JPanel content = new JPanel(new BorderLayout(0, 18));
        content.setBackground(BG);
        content.setBorder(new EmptyBorder(28, 30, 28, 30));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Shelter Communication");
        title.setForeground(TEXT);
        title.setFont(new Font("Arial", Font.BOLD, 28));

        JLabel subtitle = new JLabel(
                "Talk directly with shelters about your adoption."
        );
        subtitle.setForeground(MUTED);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(6));
        titlePanel.add(subtitle);

        JButton refresh = new JButton("Refresh");
        refresh.setFocusPainted(false);
        refresh.setFont(new Font("Arial", Font.BOLD, 13));
        refresh.setForeground(BLUE);
        refresh.setBackground(Color.WHITE);
        refresh.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225)),
                BorderFactory.createEmptyBorder(10, 18, 10, 18)
        ));
        refresh.setCursor(new Cursor(Cursor.HAND_CURSOR));

        refresh.addActionListener(e -> loadMessages());

        header.add(titlePanel, BorderLayout.WEST);
        header.add(refresh, BorderLayout.EAST);

        content.add(header, BorderLayout.NORTH);

        // ================= MESSAGE TABLE CARD =================

        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(CARD);
        tableCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(18, 18, 18, 18)
        ));

        JLabel tableTitle = new JLabel("Conversation History");
        tableTitle.setForeground(TEXT);
        tableTitle.setFont(new Font("Arial", Font.BOLD, 17));
        tableTitle.setBorder(new EmptyBorder(0, 0, 14, 0));

        tableCard.add(tableTitle, BorderLayout.NORTH);

        model = new DefaultTableModel(
                new String[]{
                        "ID",
                        "From",
                        "To",
                        "Message",
                        "Sent At"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        messageTable = new JTable(model);

        messageTable.setRowHeight(38);
        messageTable.setFont(new Font("Arial", Font.PLAIN, 13));
        messageTable.setForeground(TEXT);
        messageTable.setBackground(Color.WHITE);
        messageTable.setGridColor(new Color(226, 232, 240));
        messageTable.setSelectionBackground(new Color(219, 234, 254));
        messageTable.setSelectionForeground(TEXT);
        messageTable.setShowVerticalLines(false);

        messageTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        messageTable.getTableHeader().setBackground(
                new Color(30, 41, 59)
        );

        messageTable.getTableHeader().setForeground(Color.WHITE);
        messageTable.getTableHeader().setPreferredSize(
                new Dimension(0, 42)
        );

        DefaultTableCellRenderer center =
                new DefaultTableCellRenderer();

        center.setHorizontalAlignment(SwingConstants.CENTER);

        messageTable.getColumnModel()
                .getColumn(0)
                .setCellRenderer(center);

        messageTable.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(55);

        messageTable.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(150);

        messageTable.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(150);

        messageTable.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(380);

        messageTable.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(170);

        JScrollPane tableScroll = new JScrollPane(messageTable);
        tableScroll.setBorder(
                BorderFactory.createLineBorder(
                        new Color(226, 232, 240)
                )
        );

        tableCard.add(tableScroll, BorderLayout.CENTER);

        content.add(tableCard, BorderLayout.CENTER);

        // ================= SEND MESSAGE CARD =================

        JPanel sendCard = new JPanel(new BorderLayout(18, 10));
        sendCard.setBackground(CARD);
        sendCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(18, 18, 18, 18)
        ));

        JPanel sendTop = new JPanel(new BorderLayout(12, 0));
        sendTop.setOpaque(false);

        JLabel receiverLabel = new JLabel("Shelter User ID");
        receiverLabel.setForeground(TEXT);
        receiverLabel.setFont(new Font("Arial", Font.BOLD, 13));

        receiverField = new JTextField();
        receiverField.setPreferredSize(new Dimension(180, 38));
        receiverField.setFont(new Font("Arial", Font.PLAIN, 14));

        sendTop.add(receiverLabel, BorderLayout.WEST);
        sendTop.add(receiverField, BorderLayout.CENTER);

        sendCard.add(sendTop, BorderLayout.NORTH);

        messageArea = new JTextArea(3, 30);
        messageArea.setFont(new Font("Arial", Font.PLAIN, 14));
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(203, 213, 225)
                        ),
                        new EmptyBorder(10, 10, 10, 10)
                )
        );

        JScrollPane messageScroll = new JScrollPane(messageArea);
        messageScroll.setBorder(null);

        sendCard.add(messageScroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(
                FlowLayout.RIGHT, 10, 0
        ));
        bottom.setOpaque(false);

        JButton sendButton = new JButton("Send Message");
        sendButton.setFocusPainted(false);
        sendButton.setForeground(Color.WHITE);
        sendButton.setBackground(BLUE);
        sendButton.setFont(new Font("Arial", Font.BOLD, 13));
        sendButton.setBorder(BorderFactory.createEmptyBorder(
                10, 22, 10, 22
        ));
        sendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton clearButton = new JButton("Clear");
        clearButton.setFocusPainted(false);
        clearButton.setFont(new Font("Arial", Font.BOLD, 13));
        clearButton.setBackground(new Color(241, 245, 249));
        clearButton.setForeground(TEXT);
        clearButton.setBorder(BorderFactory.createEmptyBorder(
                10, 20, 10, 20
        ));

        sendButton.addActionListener(e -> sendMessage());

        clearButton.addActionListener(e -> {
            receiverField.setText("");
            messageArea.setText("");
        });

        bottom.add(clearButton);
        bottom.add(sendButton);

        sendCard.add(bottom, BorderLayout.SOUTH);

        content.add(sendCard, BorderLayout.SOUTH);

        root.add(sidebar, BorderLayout.WEST);
        root.add(content, BorderLayout.CENTER);

        add(root);

        // Navigation
        browseBtn.addActionListener(e -> {
            dispose();
            new AdopterBrowsePets();
        });

        applicationsBtn.addActionListener(e -> {
            dispose();
            new AdopterMyApplications();
        });

        favoritesBtn.addActionListener(e -> {
            dispose();
            new AdopterFavorites();
        });

        communicationBtn.addActionListener(e -> loadMessages());

        loadMessages();

        setVisible(true);
    }

    private JButton sideButton(String text) {

        JButton button = new JButton(text);

        button.setMaximumSize(
                new Dimension(175, 38)
        );

        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(
                new Color(36, 44, 56)
        );

        button.setFont(
                new Font("Arial", Font.BOLD, 12)
        );

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        8, 12, 8, 12
                )
        );

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        return button;
    }

    // ================= LOAD MESSAGES =================

    private void loadMessages() {

        if (model == null) return;

        model.setRowCount(0);

        try (Connection con =
                     DatabaseConnection.getConnection();
             PreparedStatement pst =
                     con.prepareStatement(
                             "SELECT m.message_id, " +
                                     "s.name AS sender_name, " +
                                     "r.name AS receiver_name, " +
                                     "m.message, " +
                                     "m.sent_at " +
                                     "FROM messages m " +
                                     "JOIN users s ON m.sender_id=s.user_id " +
                                     "JOIN users r ON m.receiver_id=r.user_id " +
                                     "WHERE m.sender_id=? " +
                                     "OR m.receiver_id=? " +
                                     "ORDER BY m.sent_at ASC"
                     )) {

            pst.setInt(1, Session.userId);
            pst.setInt(2, Session.userId);

            try (ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {

                    model.addRow(new Object[]{
                            rs.getInt("message_id"),
                            rs.getString("sender_name"),
                            rs.getString("receiver_name"),
                            rs.getString("message"),
                            rs.getTimestamp("sent_at")
                    });
                }
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load messages.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ================= SEND MESSAGE =================

    private void sendMessage() {

        String receiverText =
                receiverField.getText().trim();

        String message =
                messageArea.getText().trim();

        if (receiverText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the Shelter User ID.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            receiverField.requestFocus();
            return;
        }

        if (message.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please type a message.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            messageArea.requestFocus();
            return;
        }

        try {

            int receiverId =
                    Integer.parseInt(receiverText);

            try (Connection con =
                         DatabaseConnection.getConnection();
                 PreparedStatement checkPst =
                         con.prepareStatement(
                                 "SELECT user_id FROM users "
                                         + "WHERE user_id=? "
                                         + "AND role='Shelter'"
                         )) {

                checkPst.setInt(1, receiverId);

                try (ResultSet rs =
                             checkPst.executeQuery()) {

                    if (!rs.next()) {

                        JOptionPane.showMessageDialog(
                                this,
                                "No Shelter found with User ID "
                                        + receiverId + ".",
                                "Shelter Not Found",
                                JOptionPane.WARNING_MESSAGE
                        );

                        return;
                    }
                }

                try (PreparedStatement pst =
                             con.prepareStatement(
                                     "INSERT INTO messages "
                                             + "(sender_id, receiver_id, message) "
                                             + "VALUES (?, ?, ?)"
                             )) {

                    pst.setInt(1, Session.userId);
                    pst.setInt(2, receiverId);
                    pst.setString(3, message);

                    pst.executeUpdate();
                }
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Message sent successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            receiverField.setText("");
            messageArea.setText("");

            loadMessages();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Shelter User ID must be a number.",
                    "Invalid User ID",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to send message.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}