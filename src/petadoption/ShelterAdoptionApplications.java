package petadoption;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ShelterAdoptionApplications extends JFrame {

    JTable applicationTable;
    DefaultTableModel model;

    public ShelterAdoptionApplications() {

        Theme.apply();

        setTitle("Shelter - Adoption Applications");
        setSize(950, 550);
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
                new JLabel("📋 ADOPTION APPLICATIONS");

        title.setFont(
                new Font("Arial", Font.BOLD, 26)
        );

        mainPanel.add(title, BorderLayout.NORTH);


        // ================= TABLE =================

        model = new DefaultTableModel(
                new String[]{
                        "Request ID",
                        "Pet Name",
                        "Adopter",
                        "Email",
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


        // ================= BUTTONS =================

        JPanel buttonPanel =
                new JPanel();

        JButton approveButton =
                new JButton("Approve");

        JButton rejectButton =
                new JButton("Reject");

        JButton refreshButton =
                new JButton("Refresh");

        buttonPanel.add(approveButton);
        buttonPanel.add(rejectButton);
        buttonPanel.add(refreshButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);


        // ================= ACTIONS =================

        approveButton.addActionListener(
                e -> updateStatus("Approved")
        );

        rejectButton.addActionListener(
                e -> updateStatus("Rejected")
        );

        refreshButton.addActionListener(
                e -> loadApplications()
        );


        loadApplications();

        setVisible(true);
    }


    // =====================================================
    // LOAD APPLICATIONS
    // =====================================================

    private void loadApplications() {

        model.setRowCount(0);

        try {

            Connection con =
                    DatabaseConnection.getConnection();

            String sql =
                    "SELECT ar.request_id, " +
                            "p.pet_name, " +
                            "u.name AS adopter_name, " +
                            "u.email, " +
                            "ar.request_date, " +
                            "ar.status, " +
                            "ar.reason " +
                            "FROM adoption_requests ar " +
                            "JOIN pets p " +
                            "ON ar.pet_id = p.pet_id " +
                            "JOIN users u " +
                            "ON ar.adopter_id = u.user_id " +
                            "WHERE p.shelter_id=?";

            PreparedStatement pst =
                    con.prepareStatement(sql);

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
                                rs.getString("adopter_name"),
                                rs.getString("email"),
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
                    "Database Error: " +
                            e.getMessage()
            );
        }
    }


    // =====================================================
    // UPDATE APPLICATION STATUS
    // =====================================================

    private void updateStatus(String newStatus) {

        int selectedRow =
                applicationTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an application first!"
            );

            return;
        }


        int requestId =
                (int) model.getValueAt(
                        selectedRow,
                        0
                );


        try {

            Connection con =
                    DatabaseConnection.getConnection();

            // Get the pet ID for this application
            String petSql =
                    "SELECT pet_id " +
                            "FROM adoption_requests " +
                            "WHERE request_id=?";

            PreparedStatement petPst =
                    con.prepareStatement(petSql);

            petPst.setInt(
                    1,
                    requestId
            );

            ResultSet petRs =
                    petPst.executeQuery();


            if (!petRs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Application not found!"
                );

                petRs.close();
                petPst.close();
                con.close();

                return;
            }


            int petId =
                    petRs.getInt("pet_id");


            petRs.close();
            petPst.close();


            // Update application status
            String applicationSql =
                    "UPDATE adoption_requests " +
                            "SET status=? " +
                            "WHERE request_id=?";

            PreparedStatement applicationPst =
                    con.prepareStatement(
                            applicationSql
                    );

            applicationPst.setString(
                    1,
                    newStatus
            );

            applicationPst.setInt(
                    2,
                    requestId
            );

            applicationPst.executeUpdate();

            applicationPst.close();


            // If application is approved,
            // mark the pet as adopted
            if (newStatus.equals("Approved")) {

                String petUpdateSql =
                        "UPDATE pets " +
                                "SET status='Adopted' " +
                                "WHERE pet_id=?";

                PreparedStatement petUpdatePst =
                        con.prepareStatement(
                                petUpdateSql
                        );

                petUpdatePst.setInt(
                        1,
                        petId
                );

                petUpdatePst.executeUpdate();

                petUpdatePst.close();
            }


            JOptionPane.showMessageDialog(
                    this,
                    "Application " +
                            newStatus +
                            " Successfully!"
            );


            con.close();

            loadApplications();


        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " +
                            e.getMessage()
            );
        }
    }


    // =====================================================
    // MAIN
    // =====================================================

    public static void main(String[] args) {

        new ShelterAdoptionApplications();

    }
}