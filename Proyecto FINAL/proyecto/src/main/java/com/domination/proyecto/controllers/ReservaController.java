package com.domination.proyecto.controllers;

import com.domination.proyecto.exceptions.ObjectNotFoundException;
import com.domination.proyecto.models.*;
import com.domination.proyecto.repositories.PrestadorRepository;
import com.domination.proyecto.services.*;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String createReservaForm(@RequestParam("idSala") int idSala, Model model) {
        Sala sala = salaService.findById(idSala).orElseThrow(() -> new ObjectNotFoundException("Sala no encontrada"));
        Sucursal sucursal = sucursalService.findByIdSucursal(sala.getSucursal().getIdSucursal())
                                           .orElseThrow(() -> new ObjectNotFoundException("Sucursal no encontrada"));
        model.addAttribute("sucursal", sucursal);
        model.addAttribute("sala", sala);
        model.addAttribute("reserva", new Reserva());
        model.addAttribute("action", "create");
        return "formReservas";
    }

    @GetMapping("/listaReservas")// lista de reservas hechas en las sucursales del prestador
    public String listReservasSucursal(@RequestParam("idPrestador") int idPrestador, HttpSession sesion, Model model) {

        Prestador prestador = prestadorService.findByIdPrestador(idPrestador)
                                    .orElseThrow(() -> new ObjectNotFoundException("Prestador no encontrado"));
        List<Reserva> reservas = new LinkedList<>();
        List<Sucursal> sucursales = prestador.getSucursales();
        for (Sucursal sucursal : sucursales) {
            List<Sala> salas = sucursal.getSalas();
            for (Sala sala : salas) {
                for (Reserva reserva : sala.getReservas()) {
                    reservas.add(reserva);
                }
            }
        }
        model.addAttribute("reservas", reservas);
        return "listaReservas";
    }

    @GetMapping("/admin/listaReservas")// lista de todas las reservas
    public String listReservasTotales(@RequestParam("idAdministrador") int idAdministrador, Model model) {
        List<Reserva> reservas = reservaService.findAll();
        model.addAttribute("reservas", reservas);
        return "listaReservas";
    }


    @GetMapping("/misReservas")
    public String listReservasCliente(@RequestParam("idCliente") int idCliente, Model model) {
        Cliente cliente = clienteService.getClienteById(idCliente)
                                        .orElseThrow(() -> new ObjectNotFoundException("Cliente no encontrada"));
        List<Reserva> reservas = cliente.getReservas();
        model.addAttribute("reservas", reservas);
        model.addAttribute("action", "read");
        return "listaReservas";
    }
    
    @GetMapping("/edit")
    public String editReservaForm(@RequestParam("idReserva") int idReserva, Model model) {
        Reserva reserva = reservaService.findByIdReserva(idReserva)
                                        .orElseThrow(() -> new ObjectNotFoundException("Reserva no encontrada"));
        Sala sala = reserva.getSala();
        Sucursal sucursal = sala.getSucursal();
        Cliente cliente = reserva.getCliente();
        model.addAttribute("reserva", reserva);
        model.addAttribute("sala", sala);
        model.addAttribute("sucursal", sucursal);
        model.addAttribute("cliente", cliente);
        model.addAttribute("action", "update");
        return "formReservas";
    }
    
    @GetMapping("/delete")
    public String deleteReservaForm(@RequestParam("idReserva") int idReserva, Model model) {
        Reserva reserva = reservaService.findByIdReserva(idReserva)
                                        .orElseThrow(() -> new ObjectNotFoundException("Reserva no encontrada"));
        Sala sala = reserva.getSala();
        Sucursal sucursal = sala.getSucursal();
        Cliente cliente = reserva.getCliente();
        model.addAttribute("reserva", reserva);
        model.addAttribute("sala", sala);
        model.addAttribute("sucursal", sucursal);
        model.addAttribute("cliente", cliente);
        model.addAttribute("action", "delete");
        return "formReservas"; // Asegúrate de que esta vista maneje la confirmación de eliminación
    }
    
    @PostMapping("/create")
    public String createReserva(HttpServletRequest req, Model model) throws Exception {
        Reserva reserva = obtenerReservaDesdeRequest(req);
        System.out.println("id de cliente: "+reserva.getCliente().getIdCliente());
        System.out.println("duracion: "+ reserva.getDuracion());
        System.out.println("hora inicio: "+reserva.getHoraInicio());
        System.out.println("hora fin: "+ reserva.getHoraFin());
        System.out.println("monto: "+ reserva.getMonto());
        System.out.println("");
        reservaService.saveReserva(reserva);
        return "redirect:/reservas/misReservas?idCliente=" + reserva.getCliente().getIdCliente();
    }

    @PostMapping("/update")
    public String updateReserva(HttpServletRequest req, Model model) throws Exception {
        Reserva reserva = obtenerReservaDesdeRequest(req);
        reservaService.saveReserva(reserva);
        return "redirect:/reservas/misReservas?idCliente=" + reserva.getCliente().getIdCliente();
    }

    @PostMapping("/delete")
    public String deleteReserva(@RequestParam("idReserva") int idReserva,@RequestParam("idCliente") int idCliente ,Model model) {
        reservaService.deleteByIdReserva(idReserva);
        model.addAttribute("message", "La reserva ha sido eliminada exitosamente");
        return "redirect:/reservas/misReservas?idCliente="+ idCliente;
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
//        double duracion = duracionReserva.toHours();

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

}
