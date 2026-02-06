package datos;
/*
import jakarta.naming.Context;
import jakarta.naming.InitialContext;
import jakarta.naming.NamingException;
*/
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Conexion {

    private static DataSource dataSource;

    static {
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("jdbc/controlClientes");
        } catch (NamingException e) {
            throw new RuntimeException("Error al obtener el DataSource", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
