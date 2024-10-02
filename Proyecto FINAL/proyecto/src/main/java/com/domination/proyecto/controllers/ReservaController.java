package com.domination.proyecto.controllers;

import com.domination.proyecto.exceptions.ObjectNotFoundException;
import com.domination.proyecto.models.*;
import com.domination.proyecto.services.*;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reservas")
public class ReservaController {
    
    private final ReservaService reservaService;
    private final SucursalService sucursalService;
    private final SalaService salaService;
    private final ClienteService clienteService;
    private final PrestadorService prestadorService;

    @Autowired
    public ReservaController(ReservaService reservaService, SucursalService sucursalService, SalaService salaService, ClienteService clienteService, PrestadorService prestadorService) {
        this.reservaService = reservaService;
        this.sucursalService = sucursalService;
        this.salaService = salaService;
        this.clienteService = clienteService;
        this.prestadorService = prestadorService;
    }
    
    @GetMapping("/create")
    public String createReservaForm(@RequestParam("idSala") int idSala, Model model, HttpSession session) {
        try {
            Usuario usuario = (Usuario) session.getAttribute("userLogueado");
            Sala sala = salaService.findById(idSala).orElseThrow(() -> new ObjectNotFoundException("Sala no encontrada"));
            Sucursal sucursal = sala.getSucursal();
            model.addAttribute("sucursal", sucursal);
            model.addAttribute("sala", sala);
            model.addAttribute("reserva", new Reserva());
            model.addAttribute("action", "create");
            return "formReservas";
        }catch (ObjectNotFoundException e){
            session.setAttribute("Exito", false);
            session.setAttribute("mensajeExito", e.getMessage());
            return "redirect:/inicio";
        }
    }

    @GetMapping("/listaReservas")// lista de reservas hechas en las sucursales del prestador
    public String listReservasSucursal(@RequestParam("idPrestador") int idPrestador, HttpSession sesion, Model model) {
        try {
            Prestador prestador = (Prestador) sesion.getAttribute("userLogueado");
            if (prestador.getIdPrestador() != idPrestador) {
                return "redirect:/reservas/listaReservas?idPrestador=" + prestador.getIdPrestador();
            }
            List<Reserva> reservas = reservaService.findByPrestador(prestador);
            model.addAttribute("reservas", reservas);
            return "listaReservas";
        }
        catch (ObjectNotFoundException e) {
            sesion.setAttribute("Exito", false);
            sesion.setAttribute("mensajeExito", e.getMessage());
            return "redirect:/inicio";
        }
    }

    @GetMapping("/admin/listaReservas")// lista de todas las reservas
    public String listReservasTotales(@RequestParam("idAdministrador") int idAdministrador, Model model, HttpSession session) {
        try {
            Administrador administrador = (Administrador) session.getAttribute("userLogueado");
            if (administrador.getIdAdministrador() != idAdministrador) {
                return "redirect:/reservas/admin/listaReservas?idAdministrador=" + administrador.getIdAdministrador();
            }
            List<Reserva> reservas = reservaService.findAll();
            model.addAttribute("reservas", reservas);
            return "listaReservas";
        }
        catch (ObjectNotFoundException e) {
            session.setAttribute("Exito", false);
            session.setAttribute("mensajeExito", e.getMessage());
            return "redirect:/inicio";
        }

    }


    @GetMapping("/misReservas")
    public String listReservasCliente(@RequestParam("idCliente") int idCliente, Model model, HttpSession session) {
        try{
            Cliente usuarioSesion = (Cliente) session.getAttribute("userLogueado");
            if (usuarioSesion.getIdCliente() != idCliente) {
                return "redirect:/reservas/misReservas?idCliente=" + usuarioSesion.getIdCliente();
            }
            Cliente cliente = clienteService.getClienteById(idCliente)
                                            .orElseThrow(() -> new ObjectNotFoundException("Cliente no encontrado"));
            List<Reserva> reservas = cliente.getReservas();
            model.addAttribute("reservas", reservas);
            model.addAttribute("action", "read");
            return "listaReservas";
        }
        catch (ObjectNotFoundException e) {
            session.setAttribute("Exito", false);
            session.setAttribute("mensajeExito", e.getMessage());
            return "redirect:/inicio";
        }
    }
    
    @GetMapping("/edit")
    public String editReservaForm(@RequestParam("idReserva") int idReserva, Model model, HttpSession session) {
        try {
            setAtributosEditReserva(model, idReserva, "update");
            return "formReservas";
        }
        catch (ObjectNotFoundException e) {
            session.setAttribute("Exito", false);
            session.setAttribute("mensajeExito", e.getMessage());
            return "redirect:/inicio";
        }
    }
    
    @GetMapping("/delete")
    public String deleteReservaForm(@RequestParam("idReserva") int idReserva, Model model, HttpSession session) {
        try {
            setAtributosEditReserva(model, idReserva, "delete");
            return "formReservas";
        }
        catch (ObjectNotFoundException e) {
            session.setAttribute("Exito", false);
            session.setAttribute("mensajeExito", e.getMessage());
            return "redirect:/inicio";
        }
    }
    
    @PostMapping("/create")
    public String createReserva(HttpSession session, HttpServletRequest req, Model model) throws Exception {
        try {
            String redireccion = redirSegunUsuario(session);
            Reserva reserva = obtenerReservaDesdeRequest(req);
            Sala sala = reserva.getSala();
            Sucursal sucursal = sala.getSucursal();
            validarReserva(reserva, sucursal);
            reservaService.saveReserva(reserva);
            session.setAttribute("Exito", true);
            session.setAttribute("mensaje", "La reserva ha sido creada exitosamente");
            return redireccion;
        } catch (Exception e) {
            session.setAttribute("Exito", false);
            session.setAttribute("mensaje", e.getMessage());
            session.setAttribute("error", e.getMessage());
            return "redirect:/reservas/create?idSala=" + req.getParameter("idSala");
        }
    }

    @PostMapping("/update")
    public String updateReserva(HttpSession session, HttpServletRequest req, Model model){
        try {
            String redireccion = redirSegunUsuario(session);
            Reserva reserva = obtenerReservaDesdeRequest(req);
            Sala sala = reserva.getSala();
            Sucursal sucursal = sala.getSucursal();
            validarReserva(reserva, sucursal);
            reservaService.saveReserva(reserva);
            session.setAttribute("Exito", true);
            session.setAttribute("mensaje", "La reserva ha sido actualizada exitosamente");
            return redireccion;
        } catch (Exception e) {
            session.setAttribute("Exito", false);
            session.setAttribute("mensaje", e.getMessage());
            session.setAttribute("error", e.getMessage());
            return "redirect:/reservas/edit?idReserva=" + req.getParameter("idReserva");
        }
    }

    @PostMapping("/delete")
    public String deleteReserva(HttpSession session, @RequestParam("idReserva") int idReserva,@RequestParam("idCliente") int idCliente ,Model model) {
        try {
            String redireccion = redirSegunUsuario(session);
            reservaService.deleteByIdReserva(idReserva);
            session.setAttribute("Exito", true);
            session.setAttribute("mensaje", "La reserva ha sido eliminada exitosamente");
            return redireccion;
        } catch (Exception e) {
            session.setAttribute("Exito", false);
            session.setAttribute("mensaje", "Error al eliminar la reserva");
            return "redirect:/reservas/delete?idReserva=" + idReserva;
        }
    }
    
    private Reserva obtenerReservaDesdeRequest(HttpServletRequest req) throws Exception {
        //Primero obtengo fecha y las horas del formulario en formato string
        String fechaStr = req.getParameter("fecha");
        String horaInicioStr = req.getParameter("horaInicio");
        String horaFinStr = req.getParameter("horaFin");
        //despues lo transformo a LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime horaInicio = LocalDateTime.parse(fechaStr + " " + horaInicioStr, formatter);
        LocalDateTime horaFin = LocalDateTime.parse(fechaStr + " " + horaFinStr, formatter);
        //calculamos la duración entre la hora de inicio y de fín 
        Duration duracionReserva = Duration.between(horaInicio, horaFin);
        //pasamos la duración a minutos
        long minutos = duracionReserva.toMinutes();
        double duracion = (double) minutos / 60;
        //double duracion = duracionReserva.toHours();

        //Seguimos obteniendo datos del formulario
        int idSala = Integer.parseInt(req.getParameter("idSala"));
        int idCliente = Integer.parseInt(req.getParameter("idCliente"));
        Cliente cliente = clienteService.getClienteById(idCliente)
                                        .orElseThrow(() -> new ObjectNotFoundException("cliente no encontrado"));
        //calculamos el monto total de la reserva
        Sala laSala = salaService.findById(idSala)
                                 .orElseThrow(() -> new ObjectNotFoundException("sala no encontrada"));
        double monto = (laSala.getValorHora() * duracion);//multiplicamos el valor hora de la sala por el tiempo en horas.
        Reserva reserva = new Reserva(duracion,horaInicio,horaFin,monto,laSala,cliente);
        if (req.getParameter("idReserva") != null) {
            int idReserva = Integer.parseInt(req.getParameter("idReserva"));
            reserva.setIdReserva(idReserva);
        }
        return reserva;
    }

    public void validarReserva(Reserva reserva, Sucursal sucursal) throws Exception {
        LocalTime horaApertura = sucursal.getHoraInicio();
        LocalTime horaCierre = sucursal.getHoraFin();
        if (reserva.getHoraInicio().isAfter(reserva.getHoraFin())) {
            throw new Exception("La hora de inicio no puede ser posterior a la hora de fin");
        }
        if (reserva.getHoraInicio().isBefore(LocalDateTime.now())) {
            throw new Exception("La fecha y hora de inicio no puede ser anterior a la hora actual");
        }
        if (reserva.getHoraInicio().toLocalTime().isBefore(horaApertura) || reserva.getHoraFin().toLocalTime().isAfter(horaCierre)) {
            throw new Exception("La hora de inicio y fin deben estar dentro del horario de atención de la sucursal");
        }
        //tengo que tener acceso a todas las reservas de la sala para verificar si hay una en el horario que quiero reservar
        List<Reserva> reservasExistentes = reserva.getSala().getReservas();

        // buscamos entre todas las reservas de la sala
        for (Reserva reservaExistente : reservasExistentes) {
            // si la nueva reserva se hace en un horario que se solapa con los horarios de una reserva existente, mandamos una excepción a menos que se edite una reserva existente
            if (reserva.getIdReserva()!= reservaExistente.getIdReserva()){
                if ((reserva.getHoraInicio().isAfter(reservaExistente.getHoraInicio()) && reserva.getHoraInicio().isBefore(reservaExistente.getHoraFin())) ||
                        (reserva.getHoraFin().isAfter(reservaExistente.getHoraInicio()) && reserva.getHoraFin().isBefore(reservaExistente.getHoraFin()))) {
                    throw new Exception("La sala se encuentra ocupada entre las horas " + reservaExistente.getHoraInicio() + " y " + reservaExistente.getHoraFin() + " por otra reserva.");
                }
                //Si la nueva reserva es igual a alguna reserva existente, mandamos otra excepción
                if (reserva.getHoraInicio().isEqual(reservaExistente.getHoraInicio()) && reserva.getHoraFin().isEqual(reservaExistente.getHoraFin())) {
                    throw new Exception("Ya se realizó una reserva en ese horario para esta sala.");
                }
            }
        }
    }

    public void setAtributosEditReserva(Model model, int idReserva, String action) {
        Reserva reserva = reservaService.findByIdReserva(idReserva)
                .orElseThrow(() -> new ObjectNotFoundException("Reserva no encontrada"));
        Sala sala = reserva.getSala();
        Sucursal sucursal = sala.getSucursal();
        Cliente cliente = reserva.getCliente();
        model.addAttribute("reserva", reserva);
        model.addAttribute("sala", sala);
        model.addAttribute("sucursal", sucursal);
        model.addAttribute("cliente", cliente);
        model.addAttribute("action", action);
    }

    public String redirSegunUsuario(HttpSession session){
        String redireccion = null;
        if (session.getAttribute("userLogueado") instanceof Cliente) {
            Cliente cliente = (Cliente) session.getAttribute("userLogueado");
            redireccion = "redirect:/reservas/misReservas?idCliente="+cliente.getIdCliente();
        }
        else if (session.getAttribute("userLogueado") instanceof Prestador) {
            Prestador prestador = (Prestador) session.getAttribute("userLogueado");
            redireccion = "redirect:/reservas/listaReservas?idPrestador="+ prestador.getIdPrestador();
        }
        else if(session.getAttribute("userLogueado") instanceof Administrador){
            Administrador administrador = (Administrador) session.getAttribute("userLogueado");
            redireccion = "redirect:/reservas/admin/listaReservas?idAdministrador="+administrador.getIdAdministrador();
        }
        return redireccion;
    }

}