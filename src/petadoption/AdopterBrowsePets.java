package petadoption;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.sql.*;
import java.time.LocalDate;

public class AdopterBrowsePets extends JFrame {

    private final Color BG = new Color(246, 248, 251);
    private final Color TEXT = new Color(35, 45, 55);
    private final Color MUTED = new Color(120, 132, 145);
    private final Color BLUE = new Color(52, 152, 219);
    private final Color PINK = new Color(231, 76, 130);
    private final Color GREEN = new Color(39, 174, 96);
    private final Color WHITE = Color.WHITE;

    private JPanel petsPanel;

    public AdopterBrowsePets() {

        Theme.apply();

        setTitle("Browse Pets - Pet Adoption Platform");
        setSize(1150, 720);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel root =
                new JPanel(new BorderLayout());

        root.setBackground(BG);

        // ================= HEADER =================

        JPanel header =
                new JPanel(new BorderLayout());

        header.setBackground(WHITE);

        header.setBorder(
                new EmptyBorder(
                        22, 30, 18, 30
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
                new JLabel("🐾 Find Your Perfect Companion");

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        27
                )
        );

        title.setForeground(TEXT);

        JLabel subtitle =
                new JLabel(
                        "Browse pets approved and ready for adoption."
                );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        subtitle.setForeground(MUTED);

        heading.add(title);

        heading.add(
                Box.createRigidArea(
                        new Dimension(0, 5)
                )
        );

        heading.add(subtitle);

        header.add(
                heading,
                BorderLayout.WEST
        );

        JButton refreshButton =
                new JButton("⟳ Refresh");

        refreshButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        refreshButton.setForeground(BLUE);

        refreshButton.setBackground(WHITE);

        refreshButton.setFocusPainted(false);

        refreshButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        refreshButton.addActionListener(
                e -> loadPets()
        );

        header.add(
                refreshButton,
                BorderLayout.EAST
        );

        root.add(
                header,
                BorderLayout.NORTH
        );

        // ================= PET PANEL =================

        petsPanel =
                new JPanel();

        petsPanel.setBackground(BG);

        petsPanel.setBorder(
                new EmptyBorder(
                        25, 30, 30, 30
                )
        );

        petsPanel.setLayout(
                new GridLayout(
                        0,
                        3,
                        20,
                        20
                )
        );

        JScrollPane scroll =
                new JScrollPane(petsPanel);

        scroll.setBorder(null);

        scroll.setBackground(BG);

        scroll.getVerticalScrollBar()
                .setUnitIncrement(16);

        root.add(
                scroll,
                BorderLayout.CENTER
        );

        add(root);

        loadPets();

        setVisible(true);
    }

    // =====================================================
    // LOAD PETS
    // =====================================================

    private void loadPets() {

        petsPanel.removeAll();

        try {

            Connection con =
                    DatabaseConnection.getConnection();

            String sql =
                    "SELECT pet_id, pet_name, type, breed, " +
                            "age, gender, description, image " +
                            "FROM pets " +
                            "WHERE status='Approved' " +
                            "ORDER BY pet_id DESC";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            ResultSet rs =
                    pst.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                int petId =
                        rs.getInt("pet_id");

                String name =
                        rs.getString("pet_name");

                String type =
                        rs.getString("type");

                String breed =
                        rs.getString("breed");

                int age =
                        rs.getInt("age");

                String gender =
                        rs.getString("gender");

                String description =
                        rs.getString("description");

                String image =
                        rs.getString("image");

                petsPanel.add(
                        createPetCard(
                                petId,
                                name,
                                type,
                                breed,
                                age,
                                gender,
                                description,
                                image
                        )
                );
            }

            if (!found) {

                JLabel empty =
                        new JLabel(
                                "<html><center>" +
                                        "🐾<br><br>" +
                                        "<b>No pets available right now.</b><br>" +
                                        "Please check again later." +
                                        "</center></html>"
                        );

                empty.setFont(
                        new Font(
                                "Arial",
                                Font.PLAIN,
                                16
                        )
                );

                empty.setForeground(MUTED);

                petsPanel.add(empty);
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

        petsPanel.revalidate();
        petsPanel.repaint();
    }

    // =====================================================
    // PET CARD
    // =====================================================

    private JPanel createPetCard(
            int petId,
            String name,
            String type,
            String breed,
            int age,
            String gender,
            String description,
            String imagePath
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBackground(WHITE);

        card.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                225,
                                230,
                                235
                        )
                )
        );

        // ================= IMAGE =================

        JLabel imageLabel =
                new JLabel();

        imageLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        imageLabel.setPreferredSize(
                new Dimension(
                        300,
                        190
                )
        );

        imageLabel.setOpaque(true);

        imageLabel.setBackground(
                new Color(
                        235,
                        240,
                        245
                )
        );

        if (
                imagePath != null &&
                        !imagePath.trim().isEmpty()
        ) {

            File imageFile =
                    new File(imagePath);

            if (imageFile.exists()) {

                ImageIcon icon =
                        new ImageIcon(
                                imageFile.getAbsolutePath()
                        );

                Image scaled =
                        icon.getImage()
                                .getScaledInstance(
                                        300,
                                        190,
                                        Image.SCALE_SMOOTH
                                );

                imageLabel.setIcon(
                        new ImageIcon(scaled)
                );

            } else {

                imageLabel.setText(
                        "🐾"
                );

                imageLabel.setFont(
                        new Font(
                                "Segoe UI Emoji",
                                Font.PLAIN,
                                55
                        )
                );
            }

        } else {

            imageLabel.setText("🐾");

            imageLabel.setFont(
                    new Font(
                            "Segoe UI Emoji",
                            Font.PLAIN,
                            55
                    )
            );
        }

        card.add(
                imageLabel,
                BorderLayout.NORTH
        );

        // ================= INFO =================

        JPanel info =
                new JPanel();

        info.setBackground(WHITE);

        info.setBorder(
                new EmptyBorder(
                        13, 15, 10, 15
                )
        );

        info.setLayout(
                new BoxLayout(
                        info,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel nameLabel =
                new JLabel(name);

        nameLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        nameLabel.setForeground(TEXT);

        JLabel breedLabel =
                new JLabel(
                        breed +
                                " • " +
                                type
                );

        breedLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        breedLabel.setForeground(MUTED);

        JLabel details =
                new JLabel(
                        "Age: " +
                                age +
                                " years   •   " +
                                gender
                );

        details.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        details.setForeground(
                new Color(
                        90,
                        105,
                        120
                )
        );

        info.add(nameLabel);

        info.add(
                Box.createRigidArea(
                        new Dimension(0, 4)
                )
        );

        info.add(breedLabel);

        info.add(
                Box.createRigidArea(
                        new Dimension(0, 8)
                )
        );

        info.add(details);

        if (
                description != null &&
                        !description.trim().isEmpty()
        ) {

            String shortDescription =
                    description.length() > 70
                            ? description.substring(
                            0,
                            70
                    ) + "..."
                            : description;

            JLabel desc =
                    new JLabel(
                            "<html>" +
                                    shortDescription +
                                    "</html>"
                    );

            desc.setFont(
                    new Font(
                            "Arial",
                            Font.PLAIN,
                            11
                    )
            );

            desc.setForeground(MUTED);

            info.add(
                    Box.createRigidArea(
                            new Dimension(0, 8)
                    )
            );

            info.add(desc);
        }

        card.add(
                info,
                BorderLayout.CENTER
        );

        // ================= BUTTONS =================

        JPanel buttons =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                7,
                                0
                        )
                );

        buttons.setBackground(WHITE);

        buttons.setBorder(
                new EmptyBorder(
                        5, 15, 15, 15
                )
        );

        JButton viewButton =
                new JButton("VIEW");

        JButton favoriteButton =
                new JButton("♥");

        JButton applyButton =
                new JButton("APPLY");

        styleButton(
                viewButton,
                BLUE
        );

        styleButton(
                favoriteButton,
                PINK
        );

        styleButton(
                applyButton,
                GREEN
        );

        buttons.add(viewButton);
        buttons.add(favoriteButton);
        buttons.add(applyButton);

        viewButton.addActionListener(
                e -> showPetDetails(
                        petId,
                        name,
                        type,
                        breed,
                        age,
                        gender,
                        description
                )
        );

        favoriteButton.addActionListener(
                e -> addFavorite(petId)
        );

        applyButton.addActionListener(
                e -> applyForPet(
                        petId,
                        name
                )
        );

        card.add(
                buttons,
                BorderLayout.SOUTH
        );

        return card;
    }

    // =====================================================
    // BUTTON STYLE
    // =====================================================

    private void styleButton(
            JButton button,
            Color color
    ) {

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        button.setForeground(color);

        button.setBackground(WHITE);

        button.setBorder(
                BorderFactory.createLineBorder(
                        color
                )
        );

        button.setFocusPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );
    }

    // =====================================================
    // VIEW DETAILS
    // =====================================================

    private void showPetDetails(
            int petId,
            String name,
            String type,
            String breed,
            int age,
            String gender,
            String description
    ) {

        JTextArea text =
                new JTextArea();

        text.setEditable(false);

        text.setLineWrap(true);

        text.setWrapStyleWord(true);

        text.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        text.setBackground(
                new Color(
                        248,
                        250,
                        252
                )
        );

        text.setBorder(
                new EmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );

        text.setText(
                "🐾  " + name +
                        "\n\n" +

                        "Type: " + type +
                        "\n" +

                        "Breed: " + breed +
                        "\n" +

                        "Age: " + age +
                        " years\n" +

                        "Gender: " + gender +
                        "\n\n" +

                        "About " + name + ":\n" +
                        (
                                description == null
                                        ? "No description available."
                                        : description
                        )
        );

        JOptionPane.showMessageDialog(
                this,
                new JScrollPane(text),
                "Pet Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =====================================================
    // FAVORITE
    // =====================================================

    private void addFavorite(
            int petId
    ) {

        try {

            Connection con =
                    DatabaseConnection.getConnection();

            String checkSql =
                    "SELECT favorite_id " +
                            "FROM favorites " +
                            "WHERE adopter_id=? " +
                            "AND pet_id=?";

            PreparedStatement check =
                    con.prepareStatement(
                            checkSql
                    );

            check.setInt(
                    1,
                    Session.userId
            );

            check.setInt(
                    2,
                    petId
            );

            ResultSet rs =
                    check.executeQuery();

            if (rs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "❤️ This pet is already in your favorites!"
                );

                rs.close();
                check.close();
                con.close();

                return;
            }

            rs.close();
            check.close();

            String sql =
                    "INSERT INTO favorites " +
                            "(adopter_id, pet_id) " +
                            "VALUES (?, ?)";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setInt(
                    1,
                    Session.userId
            );

            pst.setInt(
                    2,
                    petId
            );

            pst.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "❤️ Added to Favorites!"
            );

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
    // APPLY
    // =====================================================

    private void applyForPet(
            int petId,
            String petName
    ) {

        String reason =
                JOptionPane.showInputDialog(
                        this,
                        "Why would you like to adopt " +
                                petName +
                                "?"
                );

        if (
                reason == null ||
                        reason.trim().isEmpty()
        ) {

            return;
        }

        try {

            Connection con =
                    DatabaseConnection.getConnection();

            // Check duplicate application

            String checkSql =
                    "SELECT request_id " +
                            "FROM adoption_requests " +
                            "WHERE pet_id=? " +
                            "AND adopter_id=? " +
                            "AND status NOT IN ('Rejected')";

            PreparedStatement check =
                    con.prepareStatement(
                            checkSql
                    );

            check.setInt(
                    1,
                    petId
            );

            check.setInt(
                    2,
                    Session.userId
            );

            ResultSet rs =
                    check.executeQuery();

            if (rs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "You already have an active application for this pet."
                );

                rs.close();
                check.close();
                con.close();

                return;
            }

            rs.close();
            check.close();

            String sql =
                    "INSERT INTO adoption_requests " +
                            "(pet_id, adopter_id, request_date, status, reason) " +
                            "VALUES (?, ?, ?, 'Pending', ?)";

            PreparedStatement pst =
                    con.prepareStatement(
                            sql
                    );

            pst.setInt(
                    1,
                    petId
            );

            pst.setInt(
                    2,
                    Session.userId
            );

            pst.setDate(
                    3,
                    Date.valueOf(
                            LocalDate.now()
                    )
            );

            pst.setString(
                    4,
                    reason
            );

            pst.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "📋 Application Submitted Successfully!\n\n" +
                            "Your application is now Pending."
            );

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
    // MAIN
    // =====================================================

    public static void main(String[] args) {

        new AdopterBrowsePets();

    }
}