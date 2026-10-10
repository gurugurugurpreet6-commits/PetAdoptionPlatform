package petadoption;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame {

    private final Color navy = new Color(10, 29, 57);
    private final Color blue = new Color(47, 125, 246);
    private final Color background = new Color(244, 248, 255);
    private final Color ink = new Color(20, 40, 76);
    private final Color muted = new Color(103, 122, 153);

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton signInButton;
    private JLabel statusLabel;
    private JLabel petImageLabel;
    private JPanel visualPanel;

    public Login() {
        Theme.apply();

        setTitle("Online Pet Adoption Platform");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1250, 780);
        setMinimumSize(new Dimension(1000, 680));
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new GridLayout(1, 2));
        root.setBackground(background);

        root.add(createVisualPanel());
        root.add(createLoginPanel());

        setContentPane(root);
        setVisible(true);
    }

    // LEFT PANEL: DOG AND CAT IMAGE
    private JPanel createVisualPanel() {
        visualPanel = new JPanel(new BorderLayout(0, 0));
        visualPanel.setBackground(navy);
        visualPanel.setBorder(new EmptyBorder(24, 28, 20, 28));

        JPanel brand = new JPanel();
        brand.setOpaque(false);
        brand.setLayout(new BoxLayout(brand, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("🐾  PET ADOPTION");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 25));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subLogo = new JLabel("      O N L I N E   P L A T F O R M");
        subLogo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subLogo.setForeground(new Color(174, 198, 232));
        subLogo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel headline = new JLabel(
                "<html><div style='width:390px;'>"
                        + "<span style='color:#FFFFFF;'>Give Them Love.</span><br>"
                        + "<span style='color:#65D2FF;'>Give Them Home.</span>"
                        + "</div></html>"
        );
        headline.setFont(new Font("Segoe UI", Font.BOLD, 31));
        headline.setForeground(Color.WHITE);
        headline.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel description = new JLabel(
                "<html><div style='width:390px; line-height:1.4;'>"
                        + "Discover loving companions, connect with shelters "
                        + "and make a difference, one adoption at a time."
                        + "</div></html>"
        );
        description.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        description.setForeground(new Color(219, 231, 248));
        description.setAlignmentX(Component.LEFT_ALIGNMENT);

        brand.add(logo);
        brand.add(Box.createVerticalStrut(3));
        brand.add(subLogo);
        brand.add(Box.createVerticalStrut(26));
        brand.add(headline);
        brand.add(Box.createVerticalStrut(12));
        brand.add(description);
        brand.add(Box.createVerticalStrut(12));

        petImageLabel = new JLabel();
        petImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        petImageLabel.setVerticalAlignment(SwingConstants.CENTER);
        petImageLabel.setOpaque(false);
        petImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel imageHolder = new JPanel(new BorderLayout());
        imageHolder.setOpaque(false);
        imageHolder.setBorder(new EmptyBorder(4, 0, 4, 0));
        imageHolder.add(petImageLabel, BorderLayout.CENTER);

        JLabel footer = new JLabel(
                "<html><div style='text-align:center;'>"
                        + "<span style='color:#65D2FF;'>⌂</span> Real Homes &nbsp;&nbsp;&nbsp;"
                        + "<span style='color:#65D2FF;'>♡</span> Adopt, Don't Shop &nbsp;&nbsp;&nbsp;"
                        + "<span style='color:#65D2FF;'>🐾</span> Better Lives"
                        + "</div></html>"
        );
        footer.setFont(new Font("Segoe UI", Font.BOLD, 12));
        footer.setForeground(Color.WHITE);
        footer.setHorizontalAlignment(SwingConstants.CENTER);
        footer.setBorder(new EmptyBorder(12, 0, 8, 0));

        visualPanel.add(brand, BorderLayout.NORTH);
        visualPanel.add(imageHolder, BorderLayout.CENTER);
        visualPanel.add(footer, BorderLayout.SOUTH);

        // Keep the image fitted whenever the window changes size.
        visualPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                updatePetImage();
            }
        });

        SwingUtilities.invokeLater(this::updatePetImage);

        return visualPanel;
    }

    private void updatePetImage() {
        if (petImageLabel == null) {
            return;
        }

        int availableWidth = visualPanel.getWidth() - 56;
        int availableHeight = visualPanel.getHeight() - 255;

        if (availableWidth < 100 || availableHeight < 100) {
            return;
        }

        Image original = loadPetImage();
        if (original == null) {
            petImageLabel.setIcon(null);
            petImageLabel.setText(
                    "<html><div style='text-align:center;color:white;'>"
                            + "<font size='7'>🐶 &nbsp; 🐱</font><br><br>"
                            + "Your new best friend is waiting!"
                            + "</div></html>"
            );
            petImageLabel.setForeground(Color.WHITE);
            return;
        }

        int originalWidth = original.getWidth(null);
        int originalHeight = original.getHeight(null);

        if (originalWidth <= 0 || originalHeight <= 0) {
            return;
        }

        double scale = Math.min(
                (double) availableWidth / originalWidth,
                (double) availableHeight / originalHeight
        );

        int width = Math.max(1, (int) (originalWidth * scale));
        int height = Math.max(1, (int) (originalHeight * scale));

        Image scaled = original.getScaledInstance(
                width, height, Image.SCALE_SMOOTH
        );

        petImageLabel.setText("");
        petImageLabel.setIcon(new ImageIcon(scaled));
    }

    private Image loadPetImage() {
        String[] paths = {
                "pet_images/login_pets.jpg",
                "pet_images/login_pets.png",
                "pet_images/login_pets.jpeg",
                "pet_images/shitzu.jpg"
        };

        for (String path : paths) {
            File file = new File(path);

            if (file.exists() && file.isFile()) {
                ImageIcon icon = new ImageIcon(path);

                if (icon.getIconWidth() > 0) {
                    return icon.getImage();
                }
            }
        }

        return null;
    }

    // RIGHT PANEL: LOGIN FORM
    private JPanel createLoginPanel() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(background);
        outer.setBorder(new EmptyBorder(30, 40, 25, 40));

        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setMaximumSize(new Dimension(460, 600));

        JLabel badge = new JLabel("━  ACCOUNT ACCESS");
        badge.setFont(new Font("Segoe UI", Font.BOLD, 13));
        badge.setForeground(blue);
        badge.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel heading = new JLabel("Welcome back!");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 36));
        heading.setForeground(ink);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel(
                "<html>Sign in to continue your pet adoption journey.</html>"
        );
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitle.setForeground(muted);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        form.add(badge);
        form.add(Box.createVerticalStrut(18));
        form.add(heading);
        form.add(Box.createVerticalStrut(10));
        form.add(subtitle);
        form.add(Box.createVerticalStrut(36));

        addFieldLabel(form, "EMAIL ADDRESS");

        emailField = new JTextField();
        styleTextField(emailField);
        emailField.setToolTipText("Enter your email address");
        form.add(emailField);

        form.add(Box.createVerticalStrut(23));

        addFieldLabel(form, "PASSWORD");

        passwordField = new JPasswordField();
        styleTextField(passwordField);
        passwordField.setToolTipText("Enter your password");
        form.add(passwordField);

        form.add(Box.createVerticalStrut(28));

        signInButton = new JButton("Sign In     →");
        signInButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        signInButton.setForeground(Color.WHITE);
        signInButton.setBackground(blue);
        signInButton.setFocusPainted(false);
        signInButton.setBorderPainted(false);
        signInButton.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );
        signInButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        signInButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 53));
        signInButton.setPreferredSize(new Dimension(420, 53));

        signInButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                signInButton.setBackground(new Color(27, 100, 222));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                signInButton.setBackground(blue);
            }
        });

        signInButton.addActionListener(e -> login());
        form.add(signInButton);

        form.add(Box.createVerticalStrut(12));

        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(new Color(195, 52, 72));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(statusLabel);

        form.add(Box.createVerticalStrut(20));

        JLabel secure = new JLabel("🔒  Secure login  •  Role-based access");
        secure.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        secure.setForeground(muted);
        secure.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(secure);

        form.add(Box.createVerticalStrut(26));

        JPanel rolePanel = new JPanel(new GridLayout(1, 3, 10, 0));
        rolePanel.setOpaque(false);
        rolePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        rolePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        rolePanel.add(createRoleBadge("ADMIN", new Color(20, 174, 157)));
        rolePanel.add(createRoleBadge("SHELTER", new Color(133, 84, 237)));
        rolePanel.add(createRoleBadge("ADOPTER", new Color(238, 76, 132)));

        form.add(rolePanel);
        form.add(Box.createVerticalStrut(30));

        JLabel copyright = new JLabel(
                "© 2026 Online Pet Adoption Platform"
        );
        copyright.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        copyright.setForeground(new Color(145, 159, 184));
        copyright.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(copyright);

        outer.add(form);
        return outer;
    }

    private JButton createRoleBadge(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 11));
        button.setForeground(color);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(
                        color.getRed(), color.getGreen(), color.getBlue(), 100
                )),
                new EmptyBorder(8, 5, 8, 5)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setToolTipText("Enter your account credentials to sign in");
        return button;
    }

    private void addFieldLabel(JPanel form, String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 11));
        label.setForeground(ink);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(label);
        form.add(Box.createVerticalStrut(9));
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setForeground(ink);
        field.setBackground(Color.WHITE);
        field.setCaretColor(blue);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(207, 222, 244)),
                new EmptyBorder(12, 13, 12, 13)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        field.setPreferredSize(new Dimension(420, 50));
    }

    // DATABASE LOGIN AND ROLE-BASED NAVIGATION
    private void login() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter your email and password.");
            return;
        }

        signInButton.setEnabled(false);
        signInButton.setText("Signing in...");

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT user_id, name, role FROM users "
                             + "WHERE email = ? AND password = ? "
                             + "AND status = 'Active'"
             )) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Session.userId = rs.getInt("user_id");
                    Session.name = rs.getString("name");
                    Session.role = rs.getString("role");

                    String role = Session.role == null
                            ? "" : Session.role.trim();

                    dispose();

                    switch (role.toLowerCase()) {
                        case "admin":
                            new AdminDashboard();
                            break;
                        case "shelter":
                            new ShelterDashboard();
                            break;
                        case "adopter":
                            new AdopterDashboard();
                            break;
                        default:
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Unknown account role: " + role,
                                    "Login Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            new Login();
                    }
                } else {
                    statusLabel.setText(
                            "Invalid credentials or inactive account."
                    );
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to sign in.\n" + ex.getMessage(),
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            if (isDisplayable()) {
                signInButton.setEnabled(true);
                signInButton.setText("Sign In     →");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}