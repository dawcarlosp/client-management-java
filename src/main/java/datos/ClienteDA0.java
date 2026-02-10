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
            = "INSERT INTO clientes(nombre, apellido ,email, telefono, saldo) VALUES(?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_PAGINADO
            = "SELECT * FROM clientes LIMIT ? OFFSET ?";
    private static final String SQL_SELECT_BY_ID
            = "SELECT id_cliente, nombre, apellido, email, telefono, saldo FROM clientes WHERE id_cliente = ? ";
    private static final String SQL_UPDATE 
            = "UPDATE clientes SET nombre=?, apellido=?, email=?, telefono=?, saldo=? WHERE id_cliente=?";
    private static final String SQL_DELETE
            = "DELETE FROM clientes WHERE id_cliente = ?";
    public List<Cliente> listarPaginado(int limite, int offset) {
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_PAGINADO)) {

            stmt.setInt(1, limite);
            stmt.setInt(2, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    clientes.add(new Cliente(
                            rs.getInt("id_cliente"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("email"),
                            rs.getString("telefono"),
                            rs.getDouble("saldo")
                    ));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return clientes;
    }

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

    public int insertar(Cliente nuevoCliente) {
        int rows = 0;
        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            stmt.setString(1, nuevoCliente.getNombre());
            stmt.setString(2, nuevoCliente.getApellido());
            stmt.setString(3, nuevoCliente.getEmail());
            stmt.setString(4, nuevoCliente.getTelefono());
            stmt.setDouble(5, nuevoCliente.getSaldo());
            rows = stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return rows;
    }

    public Cliente encontrarCliente(Cliente cliente) {
        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            stmt.setInt(1, cliente.getIdCliente());
            try (ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setApellido("apellido");
                    cliente.setEmail(rs.getString("email"));
                    cliente.setTelefono(rs.getString("telefono"));
                    cliente.setSaldo(rs.getDouble("saldo"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return cliente;
    }
    public int actualizar(Cliente cliente) {
        int rows = 0;
        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefono());
            stmt.setDouble(5, cliente.getSaldo());
            stmt.setDouble(6, cliente.getIdCliente());
            rows = stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return rows;
    }
    public int eliminar(Cliente cliente){
        int row = 0;
        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            stmt.setDouble(1, cliente.getIdCliente());
            row = stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return row;
    }
}