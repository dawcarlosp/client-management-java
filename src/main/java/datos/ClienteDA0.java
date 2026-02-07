package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Cliente;

/**
 *
 * @author cpere
 */
public class ClienteDA0 {

    private static final String SQL_SELECT
            = "SELECT id_cliente, nombre, apellido, email, telefono, saldo FROM clientes";
    private static final String SQL_INSERT
            = "INSERT INTO clientes(nombre, apellido ,email, telefono, saldo) VALUES(?,?,?,?,?)";
    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conn = Conexion.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(SQL_SELECT);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getDouble("saldo")
                );
                clientes.add(cliente);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return clientes;
    }

    public void insertar(Cliente nuevoCliente) {
       int rows = 0;
       try(Connection conn = Conexion.getConnection(); PreparedStatement){
           
       }
    }
}
