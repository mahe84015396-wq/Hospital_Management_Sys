import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static Connection conn;

    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                // Change username and password if needed
                String url = "jdbc:mysql://localhost:3306/hospitaldb";
                String user = "root";
                String pass = ""; // your MySQL password

                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(url, user, pass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
