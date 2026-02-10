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
        switch (accion) {
            case "listar" ->
                this.listarClientes(request, response);
            case "editar" ->
                this.editarCliente(request, response);
            case "eliminar" ->
                this.eliminarCliente(request, response);

            default ->
                this.listarClientes(request, response);
        }
    }

    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        int row = new ClienteDA0().eliminar(new Cliente(idCliente));
        this.listarClientes(request, response);
    }

    private void editarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        Cliente cliente = new ClienteDA0().encontrarCliente(new Cliente(idCliente));
        request.setAttribute("cliente", cliente);
        String jspEditar = "/WEB-INF/paginas/cliente/editarCliente.jsp";
        request.getRequestDispatcher(jspEditar).forward(request, response);
    }

    private void listarClientes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1. Configuración de paginación
        int pagina = 1;
        int registrosPorPagina = 5; // Puedes cambiar esto
        if (request.getParameter("pagina") != null) {
            pagina = Integer.parseInt(request.getParameter("pagina"));
        }

        ClienteDA0 dao = new ClienteDA0();

        // 2. Obtener datos segmentados
        int offset = (pagina - 1) * registrosPorPagina;
        List<Cliente> clientesPaginados = dao.listarPaginado(registrosPorPagina, offset);

        // 3. Obtener total para calcular número de páginas
        List<Cliente> todosLosClientes = dao.listar(); // O crea un método dao.getTotal()
        int totalRegistros = todosLosClientes.size();
        int totalPaginas = (int) Math.ceil((double) totalRegistros / registrosPorPagina);

        // 4. Setear atributos
        HttpSession sesion = request.getSession();
        sesion.setAttribute("clientes", clientesPaginados);
        sesion.setAttribute("totalClientes", totalRegistros);
        sesion.setAttribute("saldoTotal", this.calcularSaldoTotal(todosLosClientes));

        // Atributos para el control de la paginación en el JSP
        request.setAttribute("paginaActual", pagina);
        request.setAttribute("totalPaginas", totalPaginas);

        request.getRequestDispatcher("clientes.jsp").forward(request, response);
    }

    private Double calcularSaldoTotal(List<Cliente> clientes) {
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
        switch (accion) {
            case "insertar" ->
                this.insertarCliente(request, response);
            case "modificar" ->
                this.modificarCliente(request, response);
            default ->
                this.listarClientes(request, response);
        }
    }

    private void modificarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        double saldo = Double.parseDouble(request.getParameter("saldo"));
        Cliente cliente = new Cliente(idCliente, nombre, apellido, email, telefono, saldo);
        new ClienteDA0().actualizar(cliente);
        this.listarClientes(request, response);
    }

    private void insertarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
