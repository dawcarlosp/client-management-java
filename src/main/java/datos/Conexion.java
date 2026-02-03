package datos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author cpere
 */
public class Conexion {
        
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Driver MySQL no encontrado", ex);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            System.getProperty("DB_URL"),
            System.getProperty("DB_USER"),
            System.getProperty("DB_PASSWORD")
        );
    }
}
