/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domination.mvc.servlets;

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
import domination.mvc.model.UsuarioPrestador;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Seba
 */
public class SedeServlet extends HttpServlet {
    
    
    private DAO<Sede, Integer> laSedeDAO;
    private DAO<Domicilio, Integer> elDomDAO;
    //ACA TAMBIEN
    private DAO<Usuario, Integer> userDAO;
    private DAO<UsuarioPrestador, Integer> prestDAO;
    private DAO<SalaEnsayo,Integer> salaDAO;
    private DAO<Reserva,Integer> reservaDAO;

    @Override
    public void init() throws ServletException {
         laSedeDAO = new SedeDAO();
         elDomDAO = new DomicilioDAO();
         //ACA TAMBIEN
         userDAO = new UsuarioDAO();
         prestDAO = new PrestadorDAO();
         salaDAO = new SalaDAO();
         reservaDAO = new ReservaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

                String destino="/";
                String pathInfo = req.getPathInfo(); // Obtiene la parte de la URL después de "/sedes"
                pathInfo = pathInfo == null ? "" : pathInfo;

            switch (pathInfo) {
                case "/create":
                    req.setAttribute("action", "create");
                    req.setAttribute("laSede", new Sede()); // Crea una nueva instancia de Sede para el formulario de creación
                    destino += "pages/formSedes.jsp";
                    break;
                case "/edit":
                    int idSedeEdit = Integer.parseInt(req.getParameter("id")); 
                    int idDomEdit =  Integer.parseInt(req.getParameter("idDom"));
                    Sede laSedeEdit = laSedeDAO.getByID(idSedeEdit);
                    Domicilio elDomEdit = elDomDAO.getByID(idDomEdit);
                    req.setAttribute("laSede", laSedeEdit);
                    req.setAttribute("elDom", elDomEdit);
                    req.setAttribute("action", "update");
                    destino += "pages/formSedes.jsp";
                    break;
                case "/delete":
                    int idSedeDelete = Integer.parseInt(req.getParameter("id")); 
                    int idDomDelete =  Integer.parseInt(req.getParameter("idDom"));
                    Sede laSedeDelete = laSedeDAO.getByID(idSedeDelete);
                    Domicilio elDomDelete = elDomDAO.getByID(idDomDelete);
                    //A PARTIR DE AHORA ARRANCAN LOS CAMBIOS.
                    Usuario elPrestador = null;
                    for (Usuario usuario : userDAO.getAll()) {
                        for (UsuarioPrestador prestador : prestDAO.getAll()) {
                            if (prestador.getIdUsuario() == usuario.getIdUsuario()) {
                                elPrestador = prestador;
                            }
                        }
                    }
                    req.setAttribute("elPrestador", elPrestador);
                    //HASTA ACÁ SON LOS CAMBIOS
                    req.setAttribute("laSede", laSedeDelete);
                    req.setAttribute("elDom", elDomDelete);
                    req.setAttribute("action", "delete");
                    destino += "pages/formSedes.jsp"; // Utiliza el mismo formulario que el de creación
                    break;
                default:
                    List<Sede> sedes = laSedeDAO.getAll();
                    req.setAttribute("sedesDelUsuario", sedes);
                    destino += "pages/inicio.jsp";
                    break;
            }

            req.getRequestDispatcher(destino).forward(req, resp);
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.sendError(500, ex.getMessage());
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String cancelar = null;
            String action = req.getParameter("action");

            switch (action) {
                case "create":
                    cancelar = req.getParameter("cancelDelete");
                    if (cancelar != null && cancelar.equals("true")) {
                        req.getSession().setAttribute("Exito", false);
                        break;
                    }
                    else{
                        createSede(req);
                    }
                    break;
                case "update":
                    cancelar = req.getParameter("cancelDelete");
                    if (cancelar != null && cancelar.equals("true")) {
                        req.getSession().setAttribute("Exito", false);
                        break;
                    }
                    else{
                        updateSede(req);
                    }
                    break;
                case "delete":
                    cancelar = req.getParameter("cancelDelete");
                    if (cancelar != null && cancelar.equals("true")) {
                        req.getSession().setAttribute("Exito", false);
                        break;
                    }
                    else{
                        deleteSede(req);                    
                    }
                    break;
            }

            resp.sendRedirect(req.getServletContext().getContextPath()+"/inicio");

        } catch (Exception ex) {
            resp.sendError(500, "error en doPost SedeServlet");
        }
    }
    
    private void createSede(HttpServletRequest req) throws Exception {
        Sede laSede = obtenerSedeDesdeRequest(req);
        System.out.println(laSede.getNombre());
        laSedeDAO.create(laSede);
        Domicilio elDom = obtenerDomicilioDesdeRequest(req, laSede.getIdSede());
        elDomDAO.create(elDom);
        setAttributesForSuccess(req, "La sede ha sido creada exitosamente", laSede,elDom);
    }

    private void updateSede(HttpServletRequest req) throws Exception {
        int idSede = Integer.parseInt(req.getParameter("idSede"));//Obtengo el id de la sede en el formulario
        int idDom = Integer.parseInt(req.getParameter("idDom"));//Obtengo el id del domicilio en el formulario
        Sede laSede = obtenerSedeDesdeRequest(req); // obtengo la sede desde el formulario de edicion
        laSede.setIdSede(idSede); //Seteamos el id de la sede obtenida
        laSedeDAO.update(laSede);
        Domicilio elDom = obtenerDomicilioDesdeRequest(req, idSede);
        elDom.setId(idDom);
        elDomDAO.update(elDom);
        setAttributesForSuccess(req, "La sede ha sido actualizada exitosamente", laSede,elDom);
    }

    private void deleteSede(HttpServletRequest req) throws Exception {
        int idSede = Integer.parseInt(req.getParameter("idSede"));//Obtengo el id de la sede en el formulario
        int idDom = Integer.parseInt(req.getParameter("idDom"));//Obtengo el id del domicilio en el formulario
        for (SalaEnsayo salaEnsayo : salaDAO.getAll()) {
            if (salaEnsayo.getIdSede()==idSede) {
                for (Reserva reserva : reservaDAO.getAll()) {
                    if (salaEnsayo.getIdSala()==reserva.getIdSala()) {
                        reservaDAO.delete(reserva.getIdReserva());
                    }
                }
                salaDAO.delete(salaEnsayo.getIdSala());
            }
        }
        elDomDAO.delete(idDom);
        laSedeDAO.delete(idSede);
        setAttributesForSuccess(req, "La sede ha sido eliminada exitosamente", null,null);
    }

    private Sede obtenerSedeDesdeRequest(HttpServletRequest req) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String horaInicioString = req.getParameter("horaInicio");
        String horaCierreString = req.getParameter("horaCierre");
        String nomSede = req.getParameter("nomSede");
        int cantSalas = Integer.parseInt(req.getParameter("salas"));
        String telefono = req.getParameter("celular");
        LocalTime horaInicio =  LocalTime.parse(horaInicioString, formatter);
        LocalTime horaCierre = LocalTime.parse(horaCierreString, formatter);
        int idPrestador = Integer.parseInt(req.getParameter("idPrestador"));//Recupero el ID del prestador

        return new Sede(nomSede, cantSalas, idPrestador, horaInicio, horaCierre, telefono);
    }
        
    private Domicilio obtenerDomicilioDesdeRequest(HttpServletRequest req, int idSede) {
        String provincia = req.getParameter("provincia");
        String localidad = req.getParameter("localidad");
        String partido = req.getParameter("partido");
        String calle = req.getParameter("calle");
        String altura = req.getParameter("altura");

        return new Domicilio(provincia, localidad, partido, calle, altura, idSede);
    }
    
    private void setAttributesForSuccess(HttpServletRequest req, String mensaje, Sede laSede, Domicilio elDom) throws Exception {
        List<Sede> lasSedesUsuario = new LinkedList();
        List<Domicilio> domiciliosSedes = new LinkedList();
        List<Reserva> reservas = new LinkedList();
        req.getSession().setAttribute("Exito", true);
        req.getSession().setAttribute("mensajeExito", mensaje);
        req.getSession().setAttribute("HaySede", true);
        req.getSession().setAttribute("sede", laSede);
        req.getSession().setAttribute("domicilio", elDom);
        for (Sede sede : laSedeDAO.getAll()) {
            lasSedesUsuario.add(sede);
        }
        for (Domicilio domicilioSede : elDomDAO.getAll()) {
            domiciliosSedes.add(domicilioSede);
        }
        for (Reserva reserva : reservaDAO.getAll()) {
            reservas.add(reserva);
        }
        
        req.getSession().setAttribute("lasReservas",reservas);
        req.getSession().setAttribute("sedesDelUsuario",lasSedesUsuario);
        req.getSession().setAttribute("domiciliosDeSedes",domiciliosSedes);
        
    }
}
