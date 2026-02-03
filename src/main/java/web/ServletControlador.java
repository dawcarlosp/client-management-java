
package web;
import datos.ClienteDA0;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
         //Pasar la respuesta al jsp de clientes
         request.getRequestDispatcher("clientes.jsp").forward(request, response);
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
        
    }
}
