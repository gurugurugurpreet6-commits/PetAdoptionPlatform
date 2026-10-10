package petadoption;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ShelterAdoptionStatistics extends JFrame {

    JLabel totalPetsLabel;
    JLabel totalApplicationsLabel;
    JLabel approvedLabel;
    JLabel rejectedLabel;
    JLabel pendingLabel;
    JLabel adoptedLabel;

    public ShelterAdoptionStatistics() {

        Theme.apply();

        setTitle("Shelter - Adoption Statistics");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel =
                new JPanel(new BorderLayout(15, 15));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        JLabel title =
                new JLabel("📊 ADOPTION STATISTICS");

        title.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        title.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        mainPanel.add(
                title,
                BorderLayout.NORTH
        );

        JPanel cardsPanel =
                new JPanel(
                        new GridLayout(3, 2, 20, 20)
                );

        totalPetsLabel =
                createCard(cardsPanel, "🐾 Total Pets");

        totalApplicationsLabel =
                createCard(cardsPanel, "📋 Applications");

        approvedLabel =
                createCard(cardsPanel, "✅ Approved");

        rejectedLabel =
                createCard(cardsPanel, "❌ Rejected");

        pendingLabel =
                createCard(cardsPanel, "⏳ Pending");

        adoptedLabel =
                createCard(cardsPanel, "🏠 Adopted");

        mainPanel.add(
                cardsPanel,
                BorderLayout.CENTER
        );

        JButton refreshButton =
                new JButton("Refresh Statistics");

        refreshButton.addActionListener(
                e -> loadStatistics()
        );

        mainPanel.add(
                refreshButton,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        loadStatistics();

        setVisible(true);
    }

    private JLabel createCard(
            JPanel panel,
            String title
    ) {

        JPanel card =
                new JPanel(new BorderLayout());

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                Color.GRAY
                        ),
                        BorderFactory.createEmptyBorder(
                                15, 15, 15, 15
                        )
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        titleLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        JLabel valueLabel =
                new JLabel("0");

        valueLabel.setFont(
                new Font("Arial", Font.BOLD, 30)
        );

        valueLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        card.add(
                titleLabel,
                BorderLayout.NORTH
        );

        card.add(
                valueLabel,
                BorderLayout.CENTER
        );

        panel.add(card);

        return valueLabel;
    }

    private void loadStatistics() {

        try {

            Connection con =
                    DatabaseConnection.getConnection();

            String petSql =
                    "SELECT COUNT(*) FROM pets " +
                            "WHERE shelter_id=?";

            PreparedStatement petPst =
                    con.prepareStatement(petSql);

            petPst.setInt(
                    1,
                    Session.userId
            );

            ResultSet petRs =
                    petPst.executeQuery();

            if (petRs.next()) {

                totalPetsLabel.setText(
                        String.valueOf(
                                petRs.getInt(1)
                        )
                );
            }

            String applicationSql =
                    "SELECT COUNT(*) " +
                            "FROM adoption_requests ar " +
                            "JOIN pets p " +
                            "ON ar.pet_id=p.pet_id " +
                            "WHERE p.shelter_id=?";

            PreparedStatement applicationPst =
                    con.prepareStatement(
                            applicationSql
                    );

            applicationPst.setInt(
                    1,
                    Session.userId
            );

            ResultSet applicationRs =
                    applicationPst.executeQuery();

            if (applicationRs.next()) {

                totalApplicationsLabel.setText(
                        String.valueOf(
                                applicationRs.getInt(1)
                        )
                );
            }

            approvedLabel.setText(
                    String.valueOf(
                            getApplicationCount(
                                    con,
                                    "Approved"
                            )
                    )
            );

            rejectedLabel.setText(
                    String.valueOf(
                            getApplicationCount(
                                    con,
                                    "Rejected"
                            )
                    )
            );

            pendingLabel.setText(
                    String.valueOf(
                            getApplicationCount(
                                    con,
                                    "Pending"
                            )
                    )
            );

            String adoptedSql =
                    "SELECT COUNT(*) FROM pets " +
                            "WHERE shelter_id=? " +
                            "AND status='Adopted'";

            PreparedStatement adoptedPst =
                    con.prepareStatement(
                            adoptedSql
                    );

            adoptedPst.setInt(
                    1,
                    Session.userId
            );

            ResultSet adoptedRs =
                    adoptedPst.executeQuery();

            if (adoptedRs.next()) {

                adoptedLabel.setText(
                        String.valueOf(
                                adoptedRs.getInt(1)
                        )
                );
            }

            con.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " +
                            e.getMessage()
            );
        }
    }

    private int getApplicationCount(
            Connection con,
            String status
    ) throws SQLException {

        String sql =
                "SELECT COUNT(*) " +
                        "FROM adoption_requests ar " +
                        "JOIN pets p " +
                        "ON ar.pet_id=p.pet_id " +
                        "WHERE p.shelter_id=? " +
                        "AND ar.status=?";

        PreparedStatement pst =
                con.prepareStatement(sql);

        pst.setInt(
                1,
                Session.userId
        );

        pst.setString(
                2,
                status
        );

        ResultSet rs =
                pst.executeQuery();

        if (rs.next()) {
            return rs.getInt(1);
        }

        return 0;
    }

    public static void main(String[] args) {

        new ShelterAdoptionStatistics();

    }
}