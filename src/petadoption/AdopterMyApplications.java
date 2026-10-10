package petadoption;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdopterMyApplications extends JFrame {

    JTable applicationTable;
    DefaultTableModel model;

    public AdopterMyApplications() {

        Theme.apply();

        setTitle("My Adoption Applications");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15
                )
        );

        JLabel title =
                new JLabel("📋 MY APPLICATIONS");

        title.setFont(
                new Font("Arial", Font.BOLD, 26)
        );

        mainPanel.add(
                title,
                BorderLayout.NORTH
        );

        model = new DefaultTableModel(
                new String[]{
                        "Request ID",
                        "Pet Name",
                        "Request Date",
                        "Status",
                        "Reason"
                },
                0
        );

        applicationTable =
                new JTable(model);

        JScrollPane scrollPane =
                new JScrollPane(applicationTable);

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        JButton refreshButton =
                new JButton("Refresh");

        refreshButton.addActionListener(
                e -> loadApplications()
        );

        mainPanel.add(
                refreshButton,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        loadApplications();

        setVisible(true);
    }

    private void loadApplications() {

        model.setRowCount(0);

        try {

            Connection con =
                    DatabaseConnection.getConnection();

            String sql =
                    "SELECT ar.request_id, " +
                            "p.pet_name, " +
                            "ar.request_date, " +
                            "ar.status, " +
                            "ar.reason " +
                            "FROM adoption_requests ar " +
                            "JOIN pets p " +
                            "ON ar.pet_id = p.pet_id " +
                            "WHERE ar.adopter_id=?";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            // CURRENTLY LOGGED-IN ADOPTER
            pst.setInt(
                    1,
                    Session.userId
            );

            ResultSet rs =
                    pst.executeQuery();

            while (rs.next()) {

                model.addRow(
                        new Object[]{
                                rs.getInt("request_id"),
                                rs.getString("pet_name"),
                                rs.getDate("request_date"),
                                rs.getString("status"),
                                rs.getString("reason")
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

    public static void main(String[] args) {

        new AdopterMyApplications();

    }
}