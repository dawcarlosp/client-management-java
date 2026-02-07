
package web;
import datos.ClienteDA0;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import modelo.Cliente;

/**
 *
 * @author cpere
 */
@WebServlet(name = "ServletControlador", urlPatterns = {"/ServletControlador"})
public class ServletControlador extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      String accion = Optional.ofNullable(request.getParameter("accion")).orElse("listar");
      switch(accion){
          case "listar" -> this.listarClientes(request, response );
          default -> this.listarClientes(request, response);
      }
    }
    private void listarClientes(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
         List<Cliente> clientes = new ClienteDA0().listar();
         System.out.println("Clientes: " + clientes);
         //Obtener sesión
         HttpSession sesion = request.getSession();
         sesion.setAttribute("clientes", clientes);
         sesion.setAttribute("totalClientes", clientes.size());
         sesion.setAttribute("saldoTotal", this.calcularSaldoTotal(clientes));
         //Pasar la respuesta al jsp de clientes
         request.getRequestDispatcher("clientes.jsp").forward(request, response);
    }
    private Double calcularSaldoTotal(List<Cliente> clientes){
        return clientes.stream().mapToDouble(Cliente::getSaldo).sum();
    }
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         String accion = Optional.ofNullable(request.getParameter("accion")).orElse("listar");
      switch(accion){
          case "insertar" -> this.insertarCliente(request, response );
          default -> this.listarClientes(request, response);
      }
    }
    private void insertarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        double saldo = Double.parseDouble(request.getParameter("saldo"));
        Cliente nuevoCliente = new Cliente(nombre, apellido, email, telefono, saldo);
        new ClienteDA0().insertar(nuevoCliente);
        this.listarClientes(request, response);
    }
}
