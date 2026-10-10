package petadoption;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;

public class ShelterPetListing extends JFrame {

    JTable petTable;
    DefaultTableModel model;

    // ================= COLORS =================

    Color sidebarColor = new Color(25, 32, 45);
    Color backgroundColor = new Color(245, 247, 250);
    Color green = new Color(46, 204, 113);
    Color blue = new Color(52, 152, 219);
    Color orange = new Color(243, 156, 18);
    Color red = new Color(231, 76, 60);
    Color white = Color.WHITE;
    Color textColor = new Color(40, 45, 55);

    // ================= CONSTRUCTOR =================

    public ShelterPetListing() {

        Theme.apply();

        setTitle("Shelter Pet Listings");
        setSize(1100, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // ================= ROOT =================

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(backgroundColor);

        // ================= SIDEBAR =================

        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(225, 0));
        sidebar.setBackground(sidebarColor);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("🐾  PET SHELTER");

        logo.setForeground(Color.WHITE);
        logo.setFont(
                new Font("Arial", Font.BOLD, 20)
        );

        logo.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 20, 35, 10
                )
        );

        sidebar.add(logo);

        JLabel section = new JLabel(
                "  PET MANAGEMENT"
        );

        section.setForeground(
                new Color(150, 160, 175)
        );

        section.setFont(
                new Font("Arial", Font.BOLD, 12)
        );

        section.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        sidebar.add(section);

        sidebar.add(
                Box.createVerticalStrut(15)
        );

        JButton sideAdd =
                createSideButton("＋  Add New Pet");

        JButton sideEdit =
                createSideButton("✎  Edit Pet");

        JButton sideRefresh =
                createSideButton("↻  Refresh");

        sidebar.add(sideAdd);
        sidebar.add(sideEdit);
        sidebar.add(sideRefresh);

        sidebar.add(
                Box.createVerticalGlue()
        );

        JButton backButton =
                createSideButton(
                        "←  Back to Dashboard"
                );

        sidebar.add(backButton);

        sidebar.add(
                Box.createVerticalStrut(20)
        );

        // ================= HEADER =================

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(Color.WHITE);

        header.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 25, 20, 25
                )
        );

        JPanel headingPanel =
                new JPanel();

        headingPanel.setOpaque(false);

        headingPanel.setLayout(
                new BoxLayout(
                        headingPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                new JLabel(
                        "My Pet Listings"
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        title.setForeground(textColor);

        JLabel subtitle =
                new JLabel(
                        "Manage and update your pets available for adoption"
                );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        subtitle.setForeground(
                new Color(120, 125, 135)
        );

        headingPanel.add(title);

        headingPanel.add(
                Box.createVerticalStrut(5)
        );

        headingPanel.add(subtitle);

        header.add(
                headingPanel,
                BorderLayout.WEST
        );

        JLabel paw =
                new JLabel("🐾");

        paw.setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.PLAIN,
                        42
                )
        );

        header.add(
                paw,
                BorderLayout.EAST
        );

        // ================= TABLE =================

        model =
                new DefaultTableModel(
                        new String[]{
                                "ID",
                                "Pet Name",
                                "Type",
                                "Breed",
                                "Age",
                                "Gender",
                                "Status",
                                "Image"
                        },
                        0
                );

        petTable =
                new JTable(model);

        petTable.setRowHeight(40);

        petTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        petTable.setForeground(textColor);

        petTable.setBackground(Color.WHITE);

        petTable.setGridColor(
                new Color(
                        230,
                        233,
                        238
                )
        );

        petTable.setSelectionBackground(
                new Color(
                        220,
                        240,
                        255
                )
        );

        petTable.setSelectionForeground(
                textColor
        );

        petTable.setShowVerticalLines(false);

        petTable.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                13
                        )
                );

        petTable.getTableHeader()
                .setBackground(
                        new Color(
                                38,
                                48,
                                65
                        )
                );

        petTable.getTableHeader()
                .setForeground(Color.WHITE);

        petTable.getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                42
                        )
                );

        JScrollPane scrollPane =
                new JScrollPane(
                        petTable
                );

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        20,
                        10,
                        20
                )
        );

        // ================= TABLE PANEL =================

        JPanel tablePanel =
                new JPanel(
                        new BorderLayout()
                );

        tablePanel.setBackground(
                backgroundColor
        );

        tablePanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // ================= BOTTOM =================

        JPanel bottom =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                12,
                                12
                        )
                );

        bottom.setBackground(
                Color.WHITE
        );

        bottom.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        20,
                        10,
                        20
                )
        );

        JButton addButton =
                createButton(
                        "＋ Add New Pet",
                        green
                );

        JButton editButton =
                createButton(
                        "✎ Edit Pet",
                        blue
                );

        JButton refreshButton =
                createButton(
                        "↻ Refresh",
                        orange
                );

        bottom.add(addButton);
        bottom.add(editButton);
        bottom.add(refreshButton);

        // ================= CONTENT =================

        JPanel content =
                new JPanel(
                        new BorderLayout()
                );

        content.setBackground(
                backgroundColor
        );

        content.add(
                header,
                BorderLayout.NORTH
        );

        content.add(
                tablePanel,
                BorderLayout.CENTER
        );

        content.add(
                bottom,
                BorderLayout.SOUTH
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

        // ================= ACTIONS =================

        addButton.addActionListener(
                e -> addPet()
        );

        editButton.addActionListener(
                e -> editPet()
        );

        refreshButton.addActionListener(
                e -> loadPets()
        );

        sideAdd.addActionListener(
                e -> addPet()
        );

        sideEdit.addActionListener(
                e -> editPet()
        );

        sideRefresh.addActionListener(
                e -> loadPets()
        );

        backButton.addActionListener(
                e -> {

                    dispose();

                    new ShelterDashboard();
                }
        );

        loadPets();

        setVisible(true);
    }

    // =========================================================
    // SIDEBAR BUTTON
    // =========================================================

    private JButton createSideButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                sidebarColor
        );

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        button.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        button.setMaximumSize(
                new Dimension(
                        225,
                        48
                )
        );

        button.setPreferredSize(
                new Dimension(
                        225,
                        48
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    // =========================================================
    // COLORED BUTTON
    // =========================================================

    private JButton createButton(
            String text,
            Color color
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                color
        );

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(
                        145,
                        40
                )
        );

        return button;
    }

    // =========================================================
    // LOAD PETS
    // =========================================================

    private void loadPets() {

        model.setRowCount(0);

        try {

            Connection con =
                    DatabaseConnection
                            .getConnection();

            String sql =
                    "SELECT pet_id, pet_name, type, breed, " +
                            "age, gender, status, image " +
                            "FROM pets WHERE shelter_id=?";

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

                                rs.getInt(
                                        "pet_id"
                                ),

                                rs.getString(
                                        "pet_name"
                                ),

                                rs.getString(
                                        "type"
                                ),

                                rs.getString(
                                        "breed"
                                ),

                                rs.getInt(
                                        "age"
                                ),

                                rs.getString(
                                        "gender"
                                ),

                                rs.getString(
                                        "status"
                                ),

                                rs.getString(
                                        "image"
                                )
                        }
                );
            }

            rs.close();

            pst.close();

            con.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error:\n"
                            + e.getMessage()
            );
        }
    }

    // =========================================================
    // ADD PET
    // =========================================================

    private void addPet() {

        JTextField nameField =
                new JTextField();

        JTextField typeField =
                new JTextField();

        JTextField breedField =
                new JTextField();

        JTextField ageField =
                new JTextField();

        JComboBox<String> genderBox =
                new JComboBox<>(
                        new String[]{
                                "Male",
                                "Female"
                        }
                );

        JTextField descriptionField =
                new JTextField();

        JTextField imageField =
                new JTextField();

        imageField.setEditable(false);

        JButton browseButton =
                new JButton(
                        "Browse Image"
                );

        browseButton.addActionListener(
                e -> {

                    JFileChooser chooser =
                            new JFileChooser();

                    int result =
                            chooser.showOpenDialog(
                                    this
                            );

                    if (result ==
                            JFileChooser
                                    .APPROVE_OPTION) {

                        File selectedFile =
                                chooser.getSelectedFile();

                        imageField.setText(
                                selectedFile
                                        .getAbsolutePath()
                        );
                    }
                }
        );

        JPanel imagePanel =
                new JPanel(
                        new BorderLayout(
                                5,
                                5
                        )
                );

        imagePanel.add(
                imageField,
                BorderLayout.CENTER
        );

        imagePanel.add(
                browseButton,
                BorderLayout.EAST
        );

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                7,
                                2,
                                10,
                                10
                        )
                );

        panel.add(
                new JLabel(
                        "Pet Name:"
                )
        );

        panel.add(nameField);

        panel.add(
                new JLabel(
                        "Type:"
                )
        );

        panel.add(typeField);

        panel.add(
                new JLabel(
                        "Breed:"
                )
        );

        panel.add(breedField);

        panel.add(
                new JLabel(
                        "Age:"
                )
        );

        panel.add(ageField);

        panel.add(
                new JLabel(
                        "Gender:"
                )
        );

        panel.add(genderBox);

        panel.add(
                new JLabel(
                        "Description:"
                )
        );

        panel.add(descriptionField);

        panel.add(
                new JLabel(
                        "Pet Image:"
                )
        );

        panel.add(imagePanel);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Add New Pet",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (result !=
                JOptionPane.OK_OPTION) {

            return;
        }

        String petName =
                nameField.getText()
                        .trim();

        String type =
                typeField.getText()
                        .trim();

        String breed =
                breedField.getText()
                        .trim();

        String ageText =
                ageField.getText()
                        .trim();

        String gender =
                genderBox
                        .getSelectedItem()
                        .toString();

        String description =
                descriptionField
                        .getText()
                        .trim();

        String selectedImage =
                imageField.getText()
                        .trim();

        if (
                petName.isEmpty()
                        || type.isEmpty()
                        || breed.isEmpty()
                        || ageText.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all required fields!"
            );

            return;
        }

        try {

            int age =
                    Integer.parseInt(
                            ageText
                    );

            File imageFolder =
                    new File(
                            "pet_images"
                    );

            if (!imageFolder.exists()) {

                imageFolder.mkdirs();
            }

            String imagePath =
                    null;

            if (!selectedImage.isEmpty()) {

                File sourceFile =
                        new File(
                                selectedImage
                        );

                if (!sourceFile.exists()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Selected image not found!"
                    );

                    return;
                }

                String fileName =
                        System.currentTimeMillis()
                                + "_"
                                + sourceFile
                                .getName();

                File destinationFile =
                        new File(
                                imageFolder,
                                fileName
                        );

                Files.copy(
                        sourceFile.toPath(),
                        destinationFile.toPath(),
                        StandardCopyOption
                                .REPLACE_EXISTING
                );

                imagePath =
                        "pet_images/"
                                + fileName;
            }

            Connection con =
                    DatabaseConnection
                            .getConnection();

            String sql =
                    "INSERT INTO pets " +
                            "(pet_name, type, breed, age, gender, " +
                            "description, image, status, shelter_id) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, 'Pending', ?)";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(
                    1,
                    petName
            );

            pst.setString(
                    2,
                    type
            );

            pst.setString(
                    3,
                    breed
            );

            pst.setInt(
                    4,
                    age
            );

            pst.setString(
                    5,
                    gender
            );

            pst.setString(
                    6,
                    description
            );

            pst.setString(
                    7,
                    imagePath
            );

            pst.setInt(
                    8,
                    Session.userId
            );

            pst.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Pet Added Successfully! 🐾\n\n"
                            + "Status: Pending for Admin Approval"
            );

            pst.close();

            con.close();

            loadPets();

        } catch (
                NumberFormatException e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Age must be a number!"
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error:\n"
                            + e.getMessage()
            );
        }
    }

    // =========================================================
    // EDIT PET
    // =========================================================

    private void editPet() {

        int selectedRow =
                petTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a pet to edit!"
            );

            return;
        }

        int petId =
                (int) model.getValueAt(
                        selectedRow,
                        0
                );

        String currentName =
                model.getValueAt(
                        selectedRow,
                        1
                ).toString();

        String currentType =
                model.getValueAt(
                        selectedRow,
                        2
                ).toString();

        String currentBreed =
                model.getValueAt(
                        selectedRow,
                        3
                ).toString();

        String currentAge =
                model.getValueAt(
                        selectedRow,
                        4
                ).toString();

        String currentGender =
                model.getValueAt(
                        selectedRow,
                        5
                ).toString();

        JTextField nameField =
                new JTextField(
                        currentName
                );

        JTextField typeField =
                new JTextField(
                        currentType
                );

        JTextField breedField =
                new JTextField(
                        currentBreed
                );

        JTextField ageField =
                new JTextField(
                        currentAge
                );

        JComboBox<String> genderBox =
                new JComboBox<>(
                        new String[]{
                                "Male",
                                "Female"
                        }
                );

        genderBox.setSelectedItem(
                currentGender
        );

        JTextField descriptionField =
                new JTextField();

        // ================= GET DESCRIPTION =================

        try {

            Connection con =
                    DatabaseConnection
                            .getConnection();

            String sql =
                    "SELECT description FROM pets " +
                            "WHERE pet_id=? AND shelter_id=?";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setInt(
                    1,
                    petId
            );

            pst.setInt(
                    2,
                    Session.userId
            );

            ResultSet rs =
                    pst.executeQuery();

            if (rs.next()) {

                String description =
                        rs.getString(
                                "description"
                        );

                if (description != null) {

                    descriptionField.setText(
                            description
                    );
                }
            }

            rs.close();

            pst.close();

            con.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not load pet details!"
            );

            return;
        }

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                6,
                                2,
                                10,
                                10
                        )
                );

        panel.add(
                new JLabel(
                        "Pet Name:"
                )
        );

        panel.add(nameField);

        panel.add(
                new JLabel(
                        "Type:"
                )
        );

        panel.add(typeField);

        panel.add(
                new JLabel(
                        "Breed:"
                )
        );

        panel.add(breedField);

        panel.add(
                new JLabel(
                        "Age:"
                )
        );

        panel.add(ageField);

        panel.add(
                new JLabel(
                        "Gender:"
                )
        );

        panel.add(genderBox);

        panel.add(
                new JLabel(
                        "Description:"
                )
        );

        panel.add(descriptionField);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Edit Pet Details",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (result !=
                JOptionPane.OK_OPTION) {

            return;
        }

        String petName =
                nameField.getText()
                        .trim();

        String type =
                typeField.getText()
                        .trim();

        String breed =
                breedField.getText()
                        .trim();

        String ageText =
                ageField.getText()
                        .trim();

        String gender =
                genderBox
                        .getSelectedItem()
                        .toString();

        String description =
                descriptionField
                        .getText()
                        .trim();

        if (
                petName.isEmpty()
                        || type.isEmpty()
                        || breed.isEmpty()
                        || ageText.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all required fields!"
            );

            return;
        }

        try {

            int age =
                    Integer.parseInt(
                            ageText
                    );

            Connection con =
                    DatabaseConnection
                            .getConnection();

            String sql =
                    "UPDATE pets SET " +
                            "pet_name=?, " +
                            "type=?, " +
                            "breed=?, " +
                            "age=?, " +
                            "gender=?, " +
                            "description=? " +
                            "WHERE pet_id=? " +
                            "AND shelter_id=?";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(
                    1,
                    petName
            );

            pst.setString(
                    2,
                    type
            );

            pst.setString(
                    3,
                    breed
            );

            pst.setInt(
                    4,
                    age
            );

            pst.setString(
                    5,
                    gender
            );

            pst.setString(
                    6,
                    description
            );

            pst.setInt(
                    7,
                    petId
            );

            pst.setInt(
                    8,
                    Session.userId
            );

            int rows =
                    pst.executeUpdate();

            if (rows > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Pet Details Updated Successfully! ✅"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Pet could not be updated!"
                );
            }

            pst.close();

            con.close();

            loadPets();

        } catch (
                NumberFormatException e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Age must be a number!"
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error:\n"
                            + e.getMessage()
            );
        }
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(
            String[] args
    ) {

        new ShelterPetListing();
    }
}