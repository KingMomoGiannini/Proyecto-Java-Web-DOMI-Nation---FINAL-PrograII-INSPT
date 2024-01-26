/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domination.mvc.servlets;
import domination.DAO.ClienteDAO;
import domination.DAO.DAO;
import domination.DAO.PrestadorDAO;
import domination.mvc.model.UsuarioCliente;
import domination.mvc.model.UsuarioPrestador;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 *
 * @author giann
 */
/*@WebServlet(name = "RegistroServlet", value = "/registrarse")*/
public class RegistroServlet extends HttpServlet  {
    
    private DAO<UsuarioPrestador,Integer> prestDAO;
    private DAO<UsuarioCliente,Integer> cliDAO;
    
    @Override
    public void init() throws ServletException{
        prestDAO = new PrestadorDAO();
        cliDAO = new ClienteDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        try{
       
            req.getRequestDispatcher("pages/registro.jsp").forward(req, resp);//redirige el servlet primero a el JSP del form de registro.

        }catch (Exception ex){
            resp.sendError(500);
        }
    
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try{
            String nombre = req.getParameter("nomCliente");
            String apellido = req.getParameter("apeCliente");
            String celular = req.getParameter("celular");
            String email = req.getParameter("email");
            String username = req.getParameter("user");
            String password = req.getParameter("pass");
            String tipoUsuario = req.getParameter("tipoUsuario");
            
            if ("prestador".equals(tipoUsuario)) {
                // Si el checkbox se encuentra tildado, crea un usuario de tipo prestador
                UsuarioPrestador elPrestador = new UsuarioPrestador(username, nombre, apellido, email, password, celular);
                prestDAO.create(elPrestador);
                req.setAttribute("elUsuario", elPrestador);
            } else {
                // Por defecto, se crea un usuario de tipo cliente
                UsuarioCliente elUsuario = new UsuarioCliente(username, nombre, apellido, email, password, celular);
                cliDAO.create(elUsuario);
                req.setAttribute("elUsuario", elUsuario);
            }

            req.getRequestDispatcher("pages/felicitacion.jsp").forward(req, resp); //Sin iniciar sesión, muestra el nombre del usuario creado y lo felicita.
            
        } catch (Exception ex){
            resp.sendError(500,"no anda bien esto en RegistroServlet\n"+ ex.getMessage());
        }
        
    }
    
}
