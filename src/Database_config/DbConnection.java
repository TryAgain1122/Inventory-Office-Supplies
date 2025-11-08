
package Database_config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private final String URL = "jdbc:mysql://localhost:3306/db_gproject";
    private final String USERNAME = "root";
<<<<<<< Updated upstream
    private final String PASSWORD = "";
//    private final String PASSWORD = "Dashataran1122@";
=======
    private final String PASSWORD = "Dashataran1122@";
>>>>>>> Stashed changes
    
    private Connection conn;
    
    public DbConnection () {
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Database Connected successfully");
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }
    
    public Connection getConnection () {
        return conn;
    }
    
    public void closeConnection () {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database connection closed");
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
    
    
}
