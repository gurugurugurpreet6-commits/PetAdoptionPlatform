package petadoption;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/pet_adoption_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    // Keep the password configured locally; do not commit/share it publicly.
    private static final String PASSWORD = "Guru@1313";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                    "MySQL JDBC Driver is missing. Reload the Maven dependency " +
                    "com.mysql:mysql-connector-j and try again.", e
            );
        }

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database Connected!");
            return con;
        } catch (SQLException e) {
            System.out.println("Database Connection Failed!");
            throw e;
        }
    }
}
