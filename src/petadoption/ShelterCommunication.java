package petadoption;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ShelterCommunication extends JFrame {

    JTable messageTable;
    DefaultTableModel model;

    JTextField receiverField;
    JTextArea messageArea;

    public ShelterCommunication() {

        Theme.apply();

        setTitle("Shelter - Adopter Communication");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15
                )
        );

        // ================= TITLE =================

        JLabel title =
                new JLabel("💬 ADOPTER COMMUNICATION");

        title.setFont(
                new Font("Arial", Font.BOLD, 26)
        );

        mainPanel.add(
                title,
                BorderLayout.NORTH
        );


        // ================= MESSAGE TABLE =================

        model = new DefaultTableModel(
                new String[]{
                        "Message ID",
                        "From",
                        "To",
                        "Message",
                        "Sent At"
                },
                0
        );

        messageTable =
                new JTable(model);

        messageTable.setRowHeight(30);

        JScrollPane tableScroll =
                new JScrollPane(messageTable);

        mainPanel.add(
                tableScroll,
                BorderLayout.CENTER
        );


        // ================= MESSAGE PANEL =================

        JPanel messagePanel =
                new JPanel(
                        new BorderLayout(10, 10)
                );


        // Adopter ID

        JPanel topPanel =
                new JPanel(
                        new GridLayout(1, 2, 10, 10)
                );

        receiverField =
                new JTextField();

        topPanel.add(
                new JLabel("Adopter User ID:")
        );

        topPanel.add(
                receiverField
        );

        messagePanel.add(
                topPanel,
                BorderLayout.NORTH
        );


        // Message Area

        messageArea =
                new JTextArea(5, 30);

        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);

        JScrollPane messageScroll =
                new JScrollPane(messageArea);

        messagePanel.add(
                messageScroll,
                BorderLayout.CENTER
        );


        // Buttons

        JPanel buttonPanel =
                new JPanel();

        JButton sendButton =
                new JButton("Send Message");

        JButton refreshButton =
                new JButton("Refresh");

        buttonPanel.add(sendButton);
        buttonPanel.add(refreshButton);

        messagePanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );


        mainPanel.add(
                messagePanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);


        // ================= BUTTON ACTIONS =================

        sendButton.addActionListener(
                e -> sendMessage()
        );

        refreshButton.addActionListener(
                e -> loadMessages()
        );


        loadMessages();

        setVisible(true);
    }


    // =====================================================
    // LOAD ALL MESSAGES
    // =====================================================

    private void loadMessages() {

        model.setRowCount(0);

        try {

            Connection con =
                    DatabaseConnection.getConnection();

            String sql =
                    "SELECT m.message_id, " +
                            "s.name AS sender_name, " +
                            "r.name AS receiver_name, " +
                            "m.message, " +
                            "m.sent_at " +
                            "FROM messages m " +
                            "JOIN users s " +
                            "ON m.sender_id = s.user_id " +
                            "JOIN users r " +
                            "ON m.receiver_id = r.user_id " +
                            "WHERE m.sender_id=? " +
                            "OR m.receiver_id=? " +
                            "ORDER BY m.sent_at ASC";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            // Logged-in shelter

            pst.setInt(
                    1,
                    Session.userId
            );

            pst.setInt(
                    2,
                    Session.userId
            );

            ResultSet rs =
                    pst.executeQuery();

            while (rs.next()) {

                model.addRow(
                        new Object[]{
                                rs.getInt("message_id"),
                                rs.getString("sender_name"),
                                rs.getString("receiver_name"),
                                rs.getString("message"),
                                rs.getTimestamp("sent_at")
                        }
                );
            }

            rs.close();
            pst.close();
            con.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error:\n" +
                            e.getMessage()
            );
        }
    }


    // =====================================================
    // SEND MESSAGE
    // =====================================================

    private void sendMessage() {

        String receiverText =
                receiverField.getText().trim();

        String message =
                messageArea.getText().trim();


        if (
                receiverText.isEmpty()
                        ||
                        message.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Adopter ID and message!"
            );

            return;
        }


        try {

            int receiverId =
                    Integer.parseInt(receiverText);


            Connection con =
                    DatabaseConnection.getConnection();


            // ================= CHECK ADOPTER =================

            String checkSql =
                    "SELECT user_id " +
                            "FROM users " +
                            "WHERE user_id=? " +
                            "AND role='Adopter' " +
                            "AND status='Active'";

            PreparedStatement checkPst =
                    con.prepareStatement(checkSql);

            checkPst.setInt(
                    1,
                    receiverId
            );

            ResultSet rs =
                    checkPst.executeQuery();


            if (!rs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Adopter not found!"
                );

                rs.close();
                checkPst.close();
                con.close();

                return;
            }


            rs.close();
            checkPst.close();


            // ================= INSERT MESSAGE =================

            String sql =
                    "INSERT INTO messages " +
                            "(sender_id, receiver_id, message) " +
                            "VALUES (?, ?, ?)";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            // Logged-in shelter

            pst.setInt(
                    1,
                    Session.userId
            );

            // Adopter

            pst.setInt(
                    2,
                    receiverId
            );

            pst.setString(
                    3,
                    message
            );

            pst.executeUpdate();


            JOptionPane.showMessageDialog(
                    this,
                    "Message Sent Successfully! 💬"
            );


            receiverField.setText("");
            messageArea.setText("");


            pst.close();
            con.close();


            loadMessages();


        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Adopter ID must be a number!"
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error:\n" +
                            e.getMessage()
            );
        }
    }


    // =====================================================
    // MAIN
    // =====================================================

    public static void main(String[] args) {

        new ShelterCommunication();

    }
}