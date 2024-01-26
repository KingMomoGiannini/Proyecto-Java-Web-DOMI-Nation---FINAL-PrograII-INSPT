/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domination.mvc.servlets;

import domination.DAO.ClienteDAO;
import domination.DAO.DAO;
import domination.DAO.DomicilioDAO;
import domination.DAO.PrestadorDAO;
import domination.DAO.ReservaDAO;
import domination.DAO.SalaDAO;
import domination.DAO.SedeDAO;
import domination.DAO.UsuarioDAO;
import domination.mvc.model.Domicilio;
import domination.mvc.model.Reserva;
import domination.mvc.model.SalaEnsayo;
import domination.mvc.model.Sede;
import domination.mvc.model.Usuario;
import domination.mvc.model.UsuarioCliente;
import domination.mvc.model.UsuarioPrestador;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Seba
 */
public class UsuarioServlet extends HttpServlet  {
    
    private DAO<UsuarioPrestador,Integer> prestDAO;
    private DAO<UsuarioCliente,Integer> cliDAO;
    private DAO<Usuario,Integer> userDAO;
    private DAO<Sede,Integer> sedeDAO;
    private DAO<Domicilio,Integer> domDAO;
    private DAO<SalaEnsayo,Integer> salaDAO;
    private DAO<Reserva,Integer> reservaDAO;
    
    @Override
    public void init() throws ServletException{
        prestDAO = new PrestadorDAO();
        cliDAO = new ClienteDAO();
        userDAO = new UsuarioDAO();
        sedeDAO = new SedeDAO();
        domDAO = new DomicilioDAO();
        salaDAO = new SalaDAO();
        reservaDAO = new ReservaDAO();
        
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        try{
            String destino="/";
            String pathInfo = req.getPathInfo(); // Obtiene la parte de la URL después de "/usuarios"
            pathInfo = pathInfo == null ? "" : pathInfo;
            //System.out.println("pathinfo: " + pathInfo);
        switch (pathInfo) {
            
            case "/edit":
                int idUserEdit = Integer.parseInt(req.getParameter("id")); 
                Usuario elUserEdit = userDAO.getByID(idUserEdit);
                req.setAttribute("elUser", elUserEdit);
                req.setAttribute("action", "update");
                destino += "pages/formUser.jsp";
                break;
            case "/delete":
                int idUserDelete = Integer.parseInt(req.getParameter("id"));
                Usuario elUserDelete = userDAO.getByID(idUserDelete);
                for (UsuarioCliente cliente : cliDAO.getAll()) {
                    if (cliente.getIdUsuario()==elUserDelete.getIdUsuario()) {
                        elUserDelete = cliente;
                    }
                }
                for (UsuarioPrestador prestador : prestDAO.getAll()) {
                    if (prestador.getIdUsuario()==elUserDelete.getIdUsuario()) {
                        elUserDelete = prestador;
                    }
                }
                req.setAttribute("elUser", elUserDelete);
                req.setAttribute("action", "delete");
                destino += "pages/formUser.jsp";
                break;
            default:
                List<Usuario> usuarios = userDAO.getAll();
                req.setAttribute("usuarios", usuarios);
                destino += "pages/inicio.jsp";
                break;
        }
            req.getRequestDispatcher(destino).forward(req, resp);//redirige el servlet primero a el JSP del form de registro.

        }catch (Exception ex){
            resp.sendError(500,"Hay un error en doGet UsuarioServlet.");
        }
    
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String cancelar = null;
            String action = req.getParameter("action");
            switch (action) {

                case "update":
                    cancelar = req.getParameter("cancelDelete");
                    if (cancelar != null && cancelar.equals("true")) {
                        req.getSession().setAttribute("Exito", false);
                        break;
                    }
                    else{
                        updateUsuario(req);
                    }
                    break;
                case "delete":
                    cancelar = req.getParameter("cancelDelete");
                    if (cancelar != null && cancelar.equals("true")) {
                        req.getSession().setAttribute("Exito", false);
                        break;
                    }
                    else{
                        deleteUsuario(req);                    
                    }
                    break;
            }

            resp.sendRedirect(req.getServletContext().getContextPath()+"/inicio");

        } catch (Exception ex) {
            resp.sendError(500,"Hay error en doPost UsuarioServlet");
        }
        
    }
    
    private void updateUsuario(HttpServletRequest req) throws Exception {
        int idUser = Integer.parseInt(req.getParameter("idUser"));//Obtengo el id de la sede en el formulario
        Usuario elUsuario = obtenerUserDesdeRequest(req); // obtengo la sede desde el formulario de edicion
        elUsuario.setIdUsuario(idUser); //Seteamos el id del usuario obtenido
        userDAO.update(elUsuario);

        setAttributesForSuccess(req, "El usuario ha sido editado exitosamente", elUsuario);
    }

    private void deleteUsuario(HttpServletRequest req) throws Exception {
        int idUser = Integer.parseInt(req.getParameter("idUser"));//Obtengo el id del usuario
        String rolUser = req.getParameter("rolUser");
        if (rolUser.equals("prestador")) {
            int idPrestador = Integer.parseInt(req.getParameter("idPrestador"));
            /*Para borrar a un prestador, hay que conseguir sus sedes, y los domicilios de las mismas
            Primero hay que borrar los domicilios y luego todas las sedes que el prestador posea*/
            for (Sede sede : sedeDAO.getAll()) {
                if (sede.getIdPrestador() == idPrestador) {
                    for (SalaEnsayo salaEnsayo : salaDAO.getAll()) {
                        if (salaEnsayo.getIdSede()==sede.getIdSede()) {
                            for (Reserva reserva : reservaDAO.getAll()) {
                                if (reserva.getIdSala()==salaEnsayo.getIdSala()) {
                                    reservaDAO.delete(reserva.getIdReserva());
                                }
                            }
                            salaDAO.delete(salaEnsayo.getIdSala());
                        }
                    }
                    for (Domicilio dom : domDAO.getAll()) {
                        if (dom.getIdSucursal()==sede.getIdSede()) {
                            domDAO.delete(dom.getId());
                        }
                    }
                    
                    sedeDAO.delete(sede.getIdSede());
                }
            }
            prestDAO.delete(idPrestador);
        }
        else{
            int idCliente = Integer.parseInt(req.getParameter("idCliente"));// Si el cliente tiene una reserva de sala, primero hay que eliminar la reserva, luego al cliente.
            for (Reserva reserva : reservaDAO.getAll()) {
                if (reserva.getIdCliente()==idCliente) {
                    reservaDAO.delete(reserva.getIdReserva());
                }
            }
            cliDAO.delete(idCliente);
        }
        userDAO.delete(idUser);
        setAttributesForSuccess(req, "El usuario ha sido eliminado exitosamente", null);
    }

    private Usuario obtenerUserDesdeRequest(HttpServletRequest req) {
        
        String nombre = req.getParameter("nombre");
        String apellido = req.getParameter("apellido");
        String celular = req.getParameter("celular");
        String email = req.getParameter("email");
        String nomUsuario = req.getParameter("user");
        String password = req.getParameter("pass");
        String rol = req.getParameter("rol");
        
        return new Usuario(nomUsuario, nombre, apellido, email, password, celular,rol);
    }
    
    private void setAttributesForSuccess(HttpServletRequest req, String mensaje, Usuario elUser) throws Exception {
        List<Usuario> usuarios = new LinkedList();
        List<UsuarioPrestador> prestadores = new LinkedList();
        List<UsuarioCliente> clientes = new LinkedList();
        List<Reserva> reservas = new LinkedList();
        List<Sede> sedes = new LinkedList();
        req.getSession().setAttribute("Exito", true);
        req.getSession().setAttribute("mensajeExito", mensaje);
        
        for (Usuario elUsuario : userDAO.getAll()) {
            usuarios.add(elUsuario);
        }
        for (UsuarioPrestador prestador : prestDAO.getAll()) {
            prestadores.add(prestador);
        }
        for (UsuarioCliente cliente : cliDAO.getAll()) {
            clientes.add(cliente);
        }
        for (Sede sede : sedeDAO.getAll()) {
            sedes.add(sede);
        }
        for (Reserva reserva : reservaDAO.getAll()) {
            reservas.add(reserva);
        }
        
        req.getSession().setAttribute("lasReservas",reservas);
        req.getSession().setAttribute("sedesDelUsuario",sedes);
        req.getSession().setAttribute("usuarios",usuarios);
        req.getSession().setAttribute("prestadores",prestadores);
        req.getSession().setAttribute("clientes",clientes);
        
    }
}