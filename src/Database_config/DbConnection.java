
package Database_config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private final String URL = "jdbc:mysql://localhost:3306/inventory_office_supplies_db";
    private final String USERNAME = "root";
    private final String PASSWORD = "P@ssw0rd!";
//    private final String PASSWORD = "Dashataran1122@";
    
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
