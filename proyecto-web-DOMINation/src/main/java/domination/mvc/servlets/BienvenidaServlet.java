/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domination.mvc.servlets;

import domination.DAO.ClienteDAO;
import domination.DAO.DAO;
import domination.DAO.DomicilioDAO;
import domination.DAO.PrestadorDAO;
import domination.DAO.SedeDAO;
import domination.DAO.UsuarioDAO;
import domination.mvc.model.Domicilio;
import domination.mvc.model.Sede;
import domination.mvc.model.Usuario;
import domination.mvc.model.UsuarioCliente;
import domination.mvc.model.UsuarioPrestador;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author giann
 */
public class BienvenidaServlet extends HttpServlet{
    
    private DAO<UsuarioPrestador,Integer> prestDAO;
    private DAO<UsuarioCliente,Integer> cliDAO;
    private DAO<Sede,Integer> sedeDAO;
    private DAO<Domicilio,Integer> domDAO;
    private DAO<Usuario,Integer> userDAO;
    
    @Override
    public void init() throws ServletException{
        prestDAO = new PrestadorDAO();
        cliDAO = new ClienteDAO();
        sedeDAO = new SedeDAO();
        domDAO = new DomicilioDAO();
        userDAO = new UsuarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{

            req.getRequestDispatcher("pages/inicio.jsp").forward(req, resp);
            req.getSession().setAttribute("Exito", false);
            List<Sede> lasSedesUsuario = new LinkedList();
            List<Domicilio> domiciliosSedes = new LinkedList();
            List<Usuario> usuarios = new LinkedList();

            try {
                for (Sede sede : sedeDAO.getAll()) {
                    lasSedesUsuario.add(sede);
                }
                for (Domicilio domicilioSede : domDAO.getAll()) {
                    domiciliosSedes.add(domicilioSede);
                }
                for (Usuario usuario : userDAO.getAll()) {
                    for (UsuarioCliente cliente : cliDAO.getAll()) {
                        if (usuario.getIdUsuario()==cliente.getIdUsuario()) {
                            usuarios.add(cliente);
                        }
                    }
                    for (UsuarioPrestador prestador : prestDAO.getAll()) {
                        if (usuario.getIdUsuario()==prestador.getIdUsuario()) {
                            usuarios.add(prestador);
                        }
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            req.getSession().setAttribute("sedesDelUsuario", lasSedesUsuario);
            req.getSession().setAttribute("domiciliosDeSedes", domiciliosSedes);
            req.getSession().setAttribute("usuarios", usuarios);
        }catch(Exception ex){
            resp.sendError(500,"En BienvenidaServlet hay un error");
        }
    }

}
