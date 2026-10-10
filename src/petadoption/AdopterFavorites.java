package petadoption;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdopterFavorites extends JFrame {

    JTable favoriteTable;
    DefaultTableModel model;

    public AdopterFavorites() {

        Theme.apply();

        setTitle("My Favorite Pets");
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
                new JLabel("❤️ MY FAVORITE PETS");

        title.setFont(
                new Font("Arial", Font.BOLD, 26)
        );

        mainPanel.add(
                title,
                BorderLayout.NORTH
        );

        model = new DefaultTableModel(
                new String[]{
                        "Favorite ID",
                        "Pet ID",
                        "Pet Name",
                        "Type",
                        "Breed",
                        "Age",
                        "Gender"
                },
                0
        );

        favoriteTable =
                new JTable(model);

        JScrollPane scrollPane =
                new JScrollPane(favoriteTable);

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        JButton removeButton =
                new JButton("Remove Favorite");

        JButton refreshButton =
                new JButton("Refresh");

        JPanel buttonPanel =
                new JPanel();

        buttonPanel.add(removeButton);
        buttonPanel.add(refreshButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        removeButton.addActionListener(
                e -> removeFavorite()
        );

        refreshButton.addActionListener(
                e -> loadFavorites()
        );

        loadFavorites();

        setVisible(true);
    }

    private void loadFavorites() {

        model.setRowCount(0);

        try {

            Connection con =
                    DatabaseConnection.getConnection();

            String sql =
                    "SELECT f.favorite_id, " +
                            "p.pet_id, " +
                            "p.pet_name, " +
                            "p.type, " +
                            "p.breed, " +
                            "p.age, " +
                            "p.gender " +
                            "FROM favorites f " +
                            "JOIN pets p " +
                            "ON f.pet_id = p.pet_id " +
                            "WHERE f.adopter_id=?";

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
                                rs.getInt("favorite_id"),
                                rs.getInt("pet_id"),
                                rs.getString("pet_name"),
                                rs.getString("type"),
                                rs.getString("breed"),
                                rs.getInt("age"),
                                rs.getString("gender")
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

    private void removeFavorite() {

        int selectedRow =
                favoriteTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a favorite first!"
            );

            return;
        }

        int favoriteId =
                (int) model.getValueAt(
                        selectedRow,
                        0
                );

        try {

            Connection con =
                    DatabaseConnection.getConnection();

            String sql =
                    "DELETE FROM favorites " +
                            "WHERE favorite_id=? " +
                            "AND adopter_id=?";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setInt(
                    1,
                    favoriteId
            );

            pst.setInt(
                    2,
                    Session.userId
            );

            pst.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Favorite Removed Successfully!"
            );

            pst.close();
            con.close();

            loadFavorites();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error:\n" +
                            e.getMessage()
            );
        }
    }

    public static void main(String[] args) {

        new AdopterFavorites();

    }
}